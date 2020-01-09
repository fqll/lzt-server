package com.tomasky.departure.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.tomasky.departure.bo.AuditBo;
import com.tomasky.departure.bo.AuditUserInfoBo;
import com.tomasky.departure.bo.BaseDepartureBo;
import com.tomasky.departure.bo.CancelDepartureBo;
import com.tomasky.departure.bo.DepartureInfoBo;
import com.tomasky.departure.bo.FollowBo;
import com.tomasky.departure.bo.RemoveDepartureInfoBo;
import com.tomasky.departure.bo.ShareDepartureInfo;
import com.tomasky.departure.common.utils.BaseModelUtils;
import com.tomasky.departure.common.utils.CommonUtils;
import com.tomasky.departure.common.utils.DateUtils;
import com.tomasky.departure.common.utils.ShortMessageHelper;
import com.tomasky.departure.cons.Constants;
import com.tomasky.departure.enums.AuditResultEnum;
import com.tomasky.departure.enums.AuditRoleTypeEnum;
import com.tomasky.departure.enums.CheckResultEnum;
import com.tomasky.departure.enums.DepartureAuditStatusEnum;
import com.tomasky.departure.enums.DepartureReasonEnum;
import com.tomasky.departure.enums.DepartureSearchTypeEnum;
import com.tomasky.departure.enums.EmployeeAuditStatus;
import com.tomasky.departure.enums.EmployeeJobStatus;
import com.tomasky.departure.enums.FollowStatusEnum;
import com.tomasky.departure.enums.OfficialDepartureReasonEnum;
import com.tomasky.departure.enums.OperateTypeEnum;
import com.tomasky.departure.enums.PersonalDepartureReasonEnum;
import com.tomasky.departure.enums.ReadStatusEnum;
import com.tomasky.departure.enums.SendTypeEnum;
import com.tomasky.departure.enums.SmsChannel;
import com.tomasky.departure.helper.EntryHelper;
import com.tomasky.departure.helper.GuideHelper;
import com.tomasky.departure.mapper.CompanyInfoMapper;
import com.tomasky.departure.mapper.DepartureAuditMapper;
import com.tomasky.departure.mapper.DepartureInfoMapper;
import com.tomasky.departure.mapper.UserRoleInfoMapper;
import com.tomasky.departure.model.CompanyInfo;
import com.tomasky.departure.model.DepartureAudit;
import com.tomasky.departure.model.DepartureInfo;
import com.tomasky.departure.model.UserRoleInfo;
import com.tomasky.departure.service.AuthorizationService;
import com.tomasky.departure.service.DepartureService;
import com.tomasky.departure.service.MailService;
import com.tomasky.departure.utils.GenerateAlphanum;
import com.tomasky.departure.vo.ApproverLogVo;
import com.tomasky.departure.vo.CompanyDepartureRateVo;
import com.tomasky.departure.vo.DepartureInfoListVo;
import com.tomasky.departure.vo.DepartureRateVo;
import com.tomasky.departure.vo.DraftListVo;
import com.tomasky.departure.vo.MonthDepartureRateVo;
import com.tomasky.departure.vo.QuitEmployeeVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sam on 2019-08-07.15:07
 */
@Service
public class DepartureServiceImpl implements DepartureService {

    private static Logger logger = LoggerFactory.getLogger(DepartureServiceImpl.class);
    @Resource
    private MailService mailService;
    @Resource
    private DepartureInfoMapper departureInfoMapper;
    @Resource
    private CompanyInfoMapper companyInfoMapper;
    @Resource
    private DepartureAuditMapper departureAuditMapper;
    @Resource
    private AuthorizationService authorizationService;
    @Resource
    private UserRoleInfoMapper userRoleInfoMapper;
    @Resource
    private GuideHelper guideHelper;
    @Resource
    private EntryHelper entryHelper;

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Integer createDeparture(BaseDepartureBo baseDepartureBo) {
        logger.info("创建离职表单请求参数：" + JSON.toJSONString(baseDepartureBo));
        if (baseDepartureBo.isGuideMode()) {
            return 0;
        }
        DepartureInfo departureInfo = baseDepartureBo.getDepartureInfo();
        // 根据ID是否为空来判断进行新增还是更新操作
        Integer departureId = departureInfo.getId();
        // 初始化离职表单对象
        initDepartureInfo(departureInfo, baseDepartureBo);
        if (departureId == null) {
            // 初始化审计字段
            new BaseModelUtils<>().buildCreateEntity(departureInfo, baseDepartureBo.getUserId());
            // 保存离职表单
            departureInfoMapper.insert(departureInfo);
            departureId = departureInfo.getId();
        } else {
            DepartureInfo departureInfoExist = departureInfoMapper.selectByPrimaryKey(departureId);
            if (departureInfoExist == null) {
                throw new RuntimeException("离职表单不存在");
            }
            // 清空审批人和抄送人数据
            departureAuditMapper.deleteDepartureAuditList(departureId);

            BeanUtils.copyProperties(departureInfo, departureInfoExist);
            departureInfoMapper.updateByPrimaryKeySelective(departureInfoExist);
        }
        // 保存审批人
        saveDepartureAuditList(baseDepartureBo, departureId);
        // 保存抄送人
        saveDepartureCopyList(baseDepartureBo, departureId);
        logger.info("创建离职表单成功");
        return departureInfo.getId();
    }

    /**
     * 保存审批人信息
     *
     * @param baseDepartureBo
     * @param departureId
     */
    private void saveDepartureAuditList(BaseDepartureBo baseDepartureBo, Integer departureId) {
        Integer userId = baseDepartureBo.getUserId();
        // 保存创建审批记录，默认审批顺序为0
        DepartureAudit departureAuditCreated = new DepartureAudit(departureId, baseDepartureBo.getUserId(), AuditRoleTypeEnum.CREATED.getValue(), Constants.DEPARTURE_CREATED_ORDER);
        departureAuditCreated.setLastModifyTime(new Date());
        // 0.2.3版本，创建人默认关注离职人
        departureAuditCreated.setFollowStatus(FollowStatusEnum.FOLLOW.getValue());
        saveDepartureAudit(departureAuditCreated, userId);
        // 保存审批人
        List<AuditUserInfoBo> auditUserList = baseDepartureBo.getAuditUserList();
        if (!CollectionUtils.isEmpty(auditUserList)) {
            for (AuditUserInfoBo auditUserInfoBo : auditUserList) {
                OperateTypeEnum operateTypeEnum = OperateTypeEnum.WAIT_AUDIT;
                // 如果是第一审批顺位
                if (auditUserInfoBo.getAuditOrder().equals(Constants.DEPARTURE_FIRST_ORDER)) {
                    operateTypeEnum = OperateTypeEnum.IN_AUDIT;
                }
                DepartureAudit departureAudit = new DepartureAudit(auditUserInfoBo, departureId, operateTypeEnum.getValue(), AuditRoleTypeEnum.AUDIT.getValue());
                // 默认为未读
                departureAudit.setReadStatus(ReadStatusEnum.UNREAD.getValue());
                saveDepartureAudit(departureAudit, userId);
            }
        }
    }

    /**
     * 保存抄送人列表
     *
     * @param baseDepartureBo
     * @param departureId
     */
    private void saveDepartureCopyList(BaseDepartureBo baseDepartureBo, Integer departureId) {
        // 保存抄送人
        List<AuditUserInfoBo> copyUserList = baseDepartureBo.getCopyUserList();
        if (!CollectionUtils.isEmpty(copyUserList)) {
            for (AuditUserInfoBo auditUserInfoBo : copyUserList) {
                DepartureAudit departureAudit = new DepartureAudit(departureId, auditUserInfoBo.getUserId(), AuditRoleTypeEnum.COPY.getValue(), auditUserInfoBo.getAuditOrder());
                // 默认为未读
                departureAudit.setReadStatus(ReadStatusEnum.UNREAD.getValue());
                saveDepartureAudit(departureAudit, baseDepartureBo.getUserId());
            }
        }
    }

    /**
     * 保存审批对象
     *
     * @param departureAudit
     * @param userId
     */
    private void saveDepartureAudit(DepartureAudit departureAudit, Integer userId) {
        new BaseModelUtils<>().buildCreateEntity(departureAudit, userId);
        departureAuditMapper.insert(departureAudit);
    }

    /**
     * 初始化离职表单对象
     *
     * @param departureInfo
     * @param baseDepartureBo
     */
    private void initDepartureInfo(DepartureInfo departureInfo, BaseDepartureBo baseDepartureBo) {
        String saveType = baseDepartureBo.getSaveType();
        // 默认审批状态为'待审批'
        DepartureAuditStatusEnum departureAuditStatus = DepartureAuditStatusEnum.AUDIT;
        if (Constants.SAVE_TYPE_DRAFT.equals(saveType)) {
            departureAuditStatus = DepartureAuditStatusEnum.DRAFT;
        } else if (Constants.SAVE_TYPE_CONFIRM.equals(saveType)) {
            // 校验离职表单内容
            checkDepartureInfo(departureInfo);
            // 获得第一顺位审批人
            Integer currentAuditUserId = getCurrentAuditUserId(baseDepartureBo);
            if (currentAuditUserId != null) {
                // 设置当前审批人
                departureInfo.setAuditUserId(currentAuditUserId);
            } else {
                // 没有审批人，则认为是无需审批的离职表单
                departureAuditStatus = DepartureAuditStatusEnum.FINISH;
            }
        } else {
            throw new RuntimeException("未知的保存操作类型");
        }
        departureInfo.setAuditStatus(departureAuditStatus.getValue());
        Integer departureId = departureInfo.getId();
        if (departureId == null) {
            // 默认为未核验
            departureInfo.setIsCheck(CheckResultEnum.UNCHECK.getValue());
            // 生成核验码
            departureInfo.setCode(getDepartureCode());
        }
        // 根据名称查询是否是线上员工
        String employeeName = departureInfo.getEmployeeName();
        List<UserRoleInfo> userRoleInfoList = userRoleInfoMapper.selectUserRoleInfo(departureInfo.getCompanyId(), employeeName, EmployeeAuditStatus.ADOPT.getValue());
        if (!CollectionUtils.isEmpty(userRoleInfoList)) {
            UserRoleInfo userRoleInfo = userRoleInfoList.get(0);
            departureInfo.setEmployeeId(userRoleInfo.getUserId());
        }
    }

    /**
     * 随机生成核验码，如果和数据库中重复，则重新生成一次
     *
     * @return
     */
    private String getDepartureCode() {
        while (true) {
            // 初始化核验码
            String departureCode = GenerateAlphanum.getAlphanumString(Constants.DEPARTURE_CODE_LENGTH);
            int sameCodeCount = departureInfoMapper.selectDepartureCode(departureCode);
            if (sameCodeCount >= 1) {
                continue;
            } else {
                return departureCode;
            }
        }
    }

    /**
     * 校验新建离职表单信息是否有效
     *
     * @param departureInfo
     */
    private void checkDepartureInfo(DepartureInfo departureInfo) {
        Integer companyId = departureInfo.getCompanyId();
        if (companyId == null) {
            throw new RuntimeException("公司ID不能为空");
        }
        String employeeName = departureInfo.getEmployeeName();
        if (StringUtils.isBlank(employeeName)) {
            throw new RuntimeException("员工姓名不能为空");
        }
        String gender = departureInfo.getGender();
        if (StringUtils.isBlank(gender)) {
            throw new RuntimeException("性别不能为空");
        }
        String idCardNo = departureInfo.getIdCardNo();
        if (StringUtils.isBlank(idCardNo)) {
            throw new RuntimeException("身份证号不能为空");
        }
        String department = departureInfo.getDepartment();
        if (StringUtils.isBlank(department)) {
            throw new RuntimeException("部门不能为空");
        }
        String employeePost = departureInfo.getEmployeePost();
        if (StringUtils.isBlank(employeePost)) {
            throw new RuntimeException("岗位不能为空");
        }
        String entryDate = departureInfo.getEntryDate();
        if (StringUtils.isBlank(entryDate)) {
            throw new RuntimeException("入职时间不能为空");
        }
        String submitDate = departureInfo.getSubmitDate();
        if (StringUtils.isBlank(submitDate)) {
            throw new RuntimeException("提出离职时间不能为空");
        }
        String departureDate = departureInfo.getDepartureDate();
        if (StringUtils.isBlank(departureDate)) {
            throw new RuntimeException("离职时间不能为空");
        }
        String departureReason = departureInfo.getDepartureReason();
        if (StringUtils.isBlank(departureReason)) {
            throw new RuntimeException("离职原因类型不能为空");
        }
        if (DepartureReasonEnum.COMPANY_REASON.getValue().equals(departureReason)) {
            String officialDepartureReason = departureInfo.getOfficialDepartureReason();
            if (StringUtils.isBlank(officialDepartureReason)) {
                throw new RuntimeException("公司离职原因不能为空");
            }
        } else if (DepartureReasonEnum.PERSONAL_REASON.getValue().equals(departureReason)) {
            String personalDepartureReason = departureInfo.getPersonalDepartureReason();
            if (StringUtils.isBlank(personalDepartureReason)) {
                throw new RuntimeException("个人离职原因不能为空");
            }
        } else {
            throw new RuntimeException("未知的离职原因类型");
        }

    }


    /**
     * 获取当前审批人ID
     *
     * @param baseDepartureBo
     * @return
     */
    private Integer getCurrentAuditUserId(BaseDepartureBo baseDepartureBo) {
        List<AuditUserInfoBo> auditUserList = baseDepartureBo.getAuditUserList();
        if (!CollectionUtils.isEmpty(auditUserList)) {
            Integer currentAuditUserId = null;
            for (AuditUserInfoBo auditUserInfoBo : auditUserList) {
                if (auditUserInfoBo.getAuditOrder().equals(1)) {
                    currentAuditUserId = auditUserInfoBo.getUserId();
                    return currentAuditUserId;
                }
            }
            if (currentAuditUserId == null) {
                throw new RuntimeException("缺少第一顺位审批人");
            }
        }
        return null;
    }

    /**
     * 构造短信内容
     *
     * @param companyInfo
     * @return
     */
    private String buildMessageContent(CompanyInfo companyInfo, Integer id) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("您收到一条");
        messageBuilder.append(companyInfo.getCompanyName());
        messageBuilder.append(" HR发送的离职证明，");
        messageBuilder.append("https://departure.fanqiele.com/web/fill?id=" + id);
        return messageBuilder.toString();
    }

    /**
     * 构造邮件内容
     *
     * @param companyInfo
     * @return
     */
    private String buildMailContent(CompanyInfo companyInfo, Integer id) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("您收到一条");
        messageBuilder.append(companyInfo.getCompanyName());
        messageBuilder.append(" HR发送的离职证明，");
        messageBuilder.append("<a href=\"https://departure.fanqiele.com/web/fill?id=" + id + "\">马上填写</a>");
        return messageBuilder.toString();
    }

    @Override
    public Map<String, Object> findFillDepartureInfo(Integer id, String type, String openId) {
        logger.info("根据表单ID查询待填写的表单信息请求参数：id" + id + ",type=" + type + ",openId=" + openId);
        // 查询表单详情
        DepartureInfo departureInfo = null;
        List<ApproverLogVo> departureAuditList = null;
        List<ApproverLogVo> departureCopyList = null;
        boolean modifiable;
        try {
            departureInfo = departureInfoMapper.selectByPrimaryKey(id);
            if (departureInfo == null) {
                throw new RuntimeException("离职表单不存在");
            }
            modifiable = getModifiable(departureInfo);
            if (DepartureSearchTypeEnum.FILL.getValue().equals(type)) {
                // 只有创建状态的表单才可以被填写
                if (!DepartureAuditStatusEnum.AUDIT.getValue().equals(departureInfo.getAuditStatus())) {
                    throw new RuntimeException("离职表单状态异常");
                }
            } else if (DepartureSearchTypeEnum.AUDIT.getValue().equals(type)) {
                // 只有待审批状态的表单才可以被审批
                if (!DepartureAuditStatusEnum.AUDIT.getValue().equals(departureInfo.getAuditStatus())) {
                    throw new RuntimeException("离职表单状态异常");
                }
            }
            departureCopyList = departureAuditMapper.selectDepartureCopyList(id);
            departureAuditList = departureAuditMapper.selectDepartureAuditList(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("出现异常：" + e.getMessage());
            return CommonUtils.setErrorInfo(e);
        }
        Map<String, Object> result = Maps.newHashMap();
        result.put("modifiable", modifiable);
        result.put("departureInfo", departureInfo);
        result.put("departureAuditList", departureAuditList);
        result.put("departureCopyList", departureCopyList);
        logger.info("根据表单ID查询待填写的表单信息响应结果：" + JSON.toJSONString(result));
        return CommonUtils.setSuccessInfo(result);
    }

    /**
     * 判断离职表单是否可以修改
     *
     * @param departureInfo
     * @return
     */
    private boolean getModifiable(DepartureInfo departureInfo) {
        boolean modifiable = false;
        String auditStatus = departureInfo.getAuditStatus();
        if (DepartureAuditStatusEnum.AUDIT_REFUSE.getValue().equals(auditStatus)) {
            modifiable = true;
        } else if (DepartureAuditStatusEnum.AUDIT.getValue().equals(auditStatus)) {
            // 如果是审批中，需要判断是否有人审批
            List<DepartureAudit> auditedDepartureList = departureAuditMapper.selectAuditedDepartureList(departureInfo.getId());
            if (CollectionUtils.isEmpty(auditedDepartureList)) {
                modifiable = true;
            }
        }
        return modifiable;
    }


    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public void submitDeparture(DepartureInfoBo departureInfoBo) {
        logger.info("员工填写离职表单请求参数：" + JSON.toJSONString(departureInfoBo));
        checkDepartureInfoBo(departureInfoBo);
        Integer id = departureInfoBo.getId();
        DepartureInfo departureInfo = departureInfoMapper.selectByPrimaryKey(id);
        if (departureInfo == null) {
            throw new RuntimeException("离职表单不存在");
        }
        Integer userId = departureInfoBo.getUserId();
        Integer companyId = departureInfo.getCompanyId();
        // 判断员工是否加入公司，未加入自动加入
        UserRoleInfo userRoleInfoTmp = userRoleInfoMapper.selectByUserIdAndCompanyId(userId, companyId);
        if (userRoleInfoTmp == null) {
            UserRoleInfo userRoleInfo = new UserRoleInfo();
            userRoleInfo.setCompanyId(companyId);
            userRoleInfo.setUserId(userId);
            // 入职状态为待入职
            userRoleInfo.setJobStatus(EmployeeJobStatus.INCUMBENCY.getValue());
            // 审批状态为待审批
            userRoleInfo.setAuditStatus(EmployeeAuditStatus.ADOPT.getValue());
            // 默认为员工角色
//            userRoleInfo.setRoleId(RoleEnum.EMPLOYEE.getValue());
            new BaseModelUtils<>().buildCreateEntity(userRoleInfo, userId);
            userRoleInfoMapper.insert(userRoleInfo);
        }
        boolean modifiable = getModifiable(departureInfo);
        if (!modifiable) {
            throw new RuntimeException("离职表单状态异常");
        }
        BeanUtils.copyProperties(departureInfoBo, departureInfo);
        if (userId != null) {
            departureInfo.setEmployeeId(userId);
        }
        new BaseModelUtils<>().buildModifiyEntity(departureInfo, userId);
        // 更改状态为待审批
        departureInfo.setAuditStatus(DepartureAuditStatusEnum.AUDIT.getValue());
        departureInfoMapper.updateByPrimaryKeySelective(departureInfo);
        logger.info("员工填写离职表单成功");
    }

    private void checkDepartureInfoBo(DepartureInfoBo departureInfoBo) {
        Integer id = departureInfoBo.getId();
        if (id == null) {
            throw new RuntimeException("id不能为空");
        }
        String gender = departureInfoBo.getGender();
        if (StringUtils.isBlank(gender)) {
            throw new RuntimeException("性别不能为空");
        }
        String idCardNo = departureInfoBo.getIdCardNo();
        if (StringUtils.isBlank(idCardNo)) {
            throw new RuntimeException("身份证号不能为空");
        }
        String department = departureInfoBo.getDepartment();
        if (StringUtils.isBlank(department)) {
            throw new RuntimeException("部门不能为空");
        }
        String employeePost = departureInfoBo.getEmployeePost();
        if (StringUtils.isBlank(employeePost)) {
            throw new RuntimeException("岗位不能为空");
        }
        String entryDate = departureInfoBo.getEntryDate();
        if (StringUtils.isBlank(entryDate)) {
            throw new RuntimeException("入职时间不能为空");
        }
        String departureDate = departureInfoBo.getDepartureDate();
        if (StringUtils.isBlank(departureDate)) {
            throw new RuntimeException("离职时间不能为空");
        }
        String personalDepartureReason = departureInfoBo.getPersonalDepartureReason();
        if (StringUtils.isBlank(personalDepartureReason)) {
            throw new RuntimeException("离职原因不能为空");
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public void auditDeparture(AuditBo auditBo) {
        logger.info("审批离职表单请求参数：" + JSON.toJSONString(auditBo));
        // 审批结果，0：不通过，1：通过
        String auditResult = auditBo.getAuditResult();
        if (StringUtils.isBlank(auditResult)) {
            throw new RuntimeException("审批意见不能为空");
        }
        Integer departureId = auditBo.getDepartureId();
        DepartureInfo departureInfo = departureInfoMapper.selectByPrimaryKey(departureId);
        if (departureInfo == null) {
            throw new RuntimeException("离职表单不存在");
        }
        // 只有待审批状态的表单才可以被审批
        if (!DepartureAuditStatusEnum.AUDIT.getValue().equals(departureInfo.getAuditStatus())) {
            throw new RuntimeException("离职表单状态异常");
        }
        Integer auditUserId = departureInfo.getAuditUserId();
        Integer userId = auditBo.getUserId();
        if (!userId.equals(auditUserId)) {
            throw new RuntimeException("您没有审批权限");
        }
        // 更新当前审批人的审批记录
        DepartureAudit departureAudit = departureAuditMapper.selectCurrentAudit(departureId, userId, AuditRoleTypeEnum.AUDIT.getValue());
        departureAudit.setAuditResult(auditResult);
        departureAudit.setAuditOpinions(auditBo.getAuditOpinions());
        departureAudit.setOperateType(getOperateTypeEnumValue(auditResult));
        new BaseModelUtils<>().buildModifiyEntity(departureAudit, userId);
        departureAuditMapper.updateByPrimaryKeySelective(departureAudit);

        // 如果是审批通过
        if (AuditResultEnum.ADOPT.getValue().equals(auditResult)) {
            // 查询下一审批人信息
            DepartureAudit nextDepartureAudit = departureAuditMapper.selectNextAudit(departureId, departureAudit.getAuditOrder() + 1);
            // 如果审批流程里没有下一审批人，则离职表单状态变为已办结，并更新公司在职人员数据
            if (nextDepartureAudit == null) {
                departureInfo.setAuditStatus(DepartureAuditStatusEnum.FINISH.getValue());
                Integer companyId = departureInfo.getCompanyId();
                CompanyInfo companyInfo = companyInfoMapper.selectByPrimaryKey(companyId);
                Integer incumbentsCount = companyInfo.getIncumbentsCount();
                companyInfo.setIncumbentsCount(incumbentsCount - 1);
                new BaseModelUtils<>().buildModifiyEntity(companyInfo, userId);
                companyInfoMapper.updateByPrimaryKeySelective(companyInfo);
            } else {
                nextDepartureAudit.setOperateType(OperateTypeEnum.IN_AUDIT.getValue());
                // 更新审批状态为审批中
                departureAuditMapper.updateByPrimaryKeySelective(nextDepartureAudit);
                // 设置离职表单的当前审批人为下一审批顺位审批人
                departureInfo.setAuditUserId(nextDepartureAudit.getUserId());
            }
        } else if (AuditResultEnum.REFUSE.getValue().equals(auditResult)) {
            // 审批节点回滚
//            DepartureAudit preDepartureAudit = departureAuditMapper.selectNextAudit(departureId, 1);
//            departureInfo.setAuditUserId(preDepartureAudit.getUserId());
            departureInfo.setAuditStatus(DepartureAuditStatusEnum.AUDIT_REFUSE.getValue());
            // 清空审批意见
//            departureAuditMapper.updateDepartureAuditResult(departureId);
        } else {
            throw new RuntimeException("异常审批意见");
        }
        new BaseModelUtils<>().buildModifiyEntity(departureInfo, userId);
        departureInfoMapper.updateByPrimaryKeySelective(departureInfo);
        logger.info("审批离职表单成功");
    }

    /**
     * 根据审批结果判断审批流转状态
     *
     * @param auditResult
     * @return
     */
    private String getOperateTypeEnumValue(String auditResult) {
        if (AuditResultEnum.ADOPT.getValue().equals(auditResult)) {
            return OperateTypeEnum.ADOPT.getValue();
        } else if (AuditResultEnum.REFUSE.getValue().equals(auditResult)) {
            return OperateTypeEnum.REFUSE.getValue();
        } else {
            throw new RuntimeException("审批结果类型异常");
        }
    }

    @Override
    public Map<String, Object> findWebDepartureInfo(Integer id) {
        logger.info("H5页面查询待填写的表单信息请求参数：id=" + id);
        DepartureInfo departureInfo = departureInfoMapper.selectByPrimaryKey(id);
        if (departureInfo == null) {
            throw new RuntimeException("离职表单不存在");
        }
        Integer companyId = departureInfo.getCompanyId();
        CompanyInfo companyInfo = companyInfoMapper.selectByPrimaryKey(companyId);
        List<ApproverLogVo> departureCopyList = departureAuditMapper.selectDepartureCopyList(id);
        List<ApproverLogVo> departureAuditList = departureAuditMapper.selectDepartureAuditList(id);
        Map<String, Object> result = Maps.newHashMap();
        result.put("departureInfo", departureInfo);
        result.put("companyInfo", companyInfo);
        result.put("departureAuditList", departureAuditList);
        result.put("departureCopyList", departureCopyList);
        result.put("styleList", getStyleList(departureCopyList, departureAuditList));
        logger.info("H5页面查询待填写的表单信息响应结果：" + JSON.toJSONString(result));
        return result;
    }

    private List<Integer> getStyleList(List<ApproverLogVo> departureCopyList, List<ApproverLogVo> departureAuditList) {
        List<Integer> styleList = new ArrayList<>();
        int departureCopyListSize = CollectionUtils.isEmpty(departureCopyList) ? 0 : departureCopyList.size();
        int departureAuditListSize = CollectionUtils.isEmpty(departureAuditList) ? 0 : departureAuditList.size();
        int styleSize = departureCopyListSize + departureAuditListSize - 1;
        if (styleSize > 0) {
            for (int i = 0; i < styleSize; i++) {
                styleList.add(i);
            }
        }
        return styleList;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public void followDeparture(FollowBo followBo) {
        logger.info("关注员工接口输入参数：" + JSON.toJSONString(followBo));
        Integer departureId = followBo.getDepartureId();
        if (departureId == null) {
            throw new RuntimeException("离职表单ID不能为空");
        }
        Integer userId = followBo.getUserId();
        if (userId == null) {
            throw new RuntimeException("用户ID不能为空");
        }
        DepartureInfo departureInfo = departureInfoMapper.selectByPrimaryKey(departureId);
        if (departureInfo == null) {
            throw new RuntimeException("离职表单不存在");
        }
        DepartureAudit departureAudit = departureAuditMapper.selectCurrentAudit(departureId, userId, AuditRoleTypeEnum.AUDIT.getValue());
        if (departureAudit == null) {
            throw new RuntimeException("离职审批人信息不存在");
        }
        String followStatus = departureAudit.getFollowStatus();
        if (FollowStatusEnum.FOLLOW.getValue().equals(followStatus)) {
            throw new RuntimeException("已关注");
        }
        // 设置为已关注
        departureAudit.setFollowStatus(FollowStatusEnum.FOLLOW.getValue());
        new BaseModelUtils<>().buildModifiyEntity(departureAudit, userId);
        departureAuditMapper.updateByPrimaryKeySelective(departureAudit);
        logger.info("关注员工成功");
    }

    @Override
    public String findForwardPath(Integer departureId) {
        DepartureInfo departureInfo = departureInfoMapper.selectByPrimaryKey(departureId);
        String auditStatus = departureInfo.getAuditStatus();
        if (DepartureAuditStatusEnum.AUDIT.getValue().equals(auditStatus)) {
            return "employee/fill_departure";
        } else if (DepartureAuditStatusEnum.AUDIT.getValue().equals(auditStatus)) {
            return "forward:/web/success?message=离职证明还在" + DepartureAuditStatusEnum.getNameFromValue(auditStatus) + "状态";
        } else {
            return "employee/export_certificate";
        }
    }

    @Override
    public ResponseEntity<byte[]> getDeparturegetWxacode(Integer departureId) {
        DepartureInfo departureInfo = departureInfoMapper.selectByPrimaryKey(departureId);
        if (departureInfo == null) {
            throw new RuntimeException("离职表单不存在");
        }
        return authorizationService.getWxacode("pages/departure_detail/departure_detail", getScene(departureInfo));
    }

    private String getScene(DepartureInfo departureInfo) {
        StringBuilder sceneBuilder = new StringBuilder();
        sceneBuilder.append("i=" + departureInfo.getId());
        sceneBuilder.append("&t=share");
        return sceneBuilder.toString();
    }

    @Override
    public Map<String, Object> findDepartureList(Integer userId) {
        logger.info("根据用户ID查询我的离职证明列表：userId=" + userId);
        List<DepartureInfoListVo> departureInfoList = null;
        try {
            departureInfoList = departureInfoMapper.selectValidDepartureInfoList(userId);
        } catch (Exception e) {
            return CommonUtils.setErrorInfo(e);
        }
        logger.info("根据用户ID查询我的离职证明列表：" + JSON.toJSONString(departureInfoList));
        return CommonUtils.setSuccessInfo(departureInfoList);
    }

    @Override
    public Map<String, Object> findDepartureInfo(Integer id, Integer userId, String mode) {
        logger.info("根据表单ID查询离职表单详情接口请求参数：id=" + id + ",userId=" + userId + ",mode=" + mode);
        if (Constants.MODE_GUIDE.equals(mode)) {
            return CommonUtils.setSuccessInfo(guideHelper.getDepartureInfoDetailGuideData());
        }
        DepartureInfo departureInfo = null;
        List<ApproverLogVo> departureAuditList = null;
        List<ApproverLogVo> departureCopyList = null;
        // 是否可修改
        boolean modifiable;
        // 是否可撤销
        boolean cancelable;
        // 是否已关注
        boolean followStatus = false;
        boolean chatAble = false;
        CompanyInfo companyInfo = null;
        try {
            departureInfo = departureInfoMapper.selectByPrimaryKey(id);
            if (departureInfo == null) {
                throw new RuntimeException("离职表单不存在");
            }
            Integer companyId = departureInfo.getCompanyId();
            companyInfo = companyInfoMapper.selectByPrimaryKey(companyId);
            modifiable = getModifiable(departureInfo, userId);
            cancelable = getCancelable(departureInfo, userId);
            DepartureAudit departureAudit = departureAuditMapper.selectCurrentAudit(id, userId, AuditRoleTypeEnum.AUDIT.getValue());
            if (departureAudit != null) {
                String followStatus1 = departureAudit.getFollowStatus();
                if (FollowStatusEnum.FOLLOW.getValue().equals(followStatus1)) {
                    followStatus = true;
                }
            }
            chatAble = entryHelper.isChatAble(departureInfo, userId);
            departureCopyList = departureAuditMapper.selectDepartureCopyList(id);
            List<ApproverLogVo> tmpDepartureAuditList = departureAuditMapper.selectDepartureAuditList(id);
            String auditStatus = departureInfo.getAuditStatus();
            int size = tmpDepartureAuditList.size();
            // 如果是审批退回，需要把退回后的节点屏蔽掉
            if (DepartureAuditStatusEnum.AUDIT_REFUSE.getValue().equals(auditStatus)) {
                int subEndIndex = size;
                for (int i = 0; i < size; i++) {
                    ApproverLogVo approverLogVo = tmpDepartureAuditList.get(i);
                    String auditRoleType = approverLogVo.getAuditRoleType();
                    String operateType = approverLogVo.getOperateType();
                    if (AuditRoleTypeEnum.AUDIT.getValue().equals(auditRoleType) && OperateTypeEnum.REFUSE.getValue().equals(operateType)) {
                        subEndIndex = i;
                    }
                }
                departureAuditList = tmpDepartureAuditList.subList(0, subEndIndex + 1);
            } else if (DepartureAuditStatusEnum.CANCEL.getValue().equals(auditStatus)) {
                int subEndIndex = size;
                for (int i = 0; i < size; i++) {
                    ApproverLogVo approverLogVo = tmpDepartureAuditList.get(i);
                    String auditRoleType = approverLogVo.getAuditRoleType();
                    if (AuditRoleTypeEnum.CANCEL.getValue().equals(auditRoleType)) {
                        subEndIndex = i;
                    }
                }
                departureAuditList = tmpDepartureAuditList.subList(0, subEndIndex + 1);
            } else if (DepartureAuditStatusEnum.DRAFT.getValue().equals(auditStatus)) {
                if (size > 0) {
                    departureAuditList = tmpDepartureAuditList.subList(1, tmpDepartureAuditList.size());
                }
            } else {
                departureAuditList = tmpDepartureAuditList;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("出现异常：" + e.getMessage());
            return CommonUtils.setErrorInfo(e);
        }
        Map<String, Object> result = Maps.newHashMap();
        result.put("companyInfo", companyInfo);
        result.put("modifiable", modifiable);
        result.put("cancelable", cancelable);
        result.put("chatAble", chatAble);
        result.put("followStatus", followStatus);
        result.put("departureInfo", departureInfo);
        result.put("departureAuditList", departureAuditList);
        result.put("departureCopyList", departureCopyList);
        logger.info("根据表单ID查询离职表单详情接口返回接口：" + JSON.toJSONString(result));
        return CommonUtils.setSuccessInfo(result);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public void cancelDepartureInfo(CancelDepartureBo cancelDepartureBo) {
        logger.info("撤回离职表单接口请求参数：" + JSON.toJSONString(cancelDepartureBo));
        Integer id = cancelDepartureBo.getId();
        Integer userId = cancelDepartureBo.getUserId();
        if (id == null) {
            throw new RuntimeException("离职表单ID不能为空");
        }
        if (userId == null) {
            throw new RuntimeException("用户ID不能为空");
        }
        DepartureInfo departureInfo = departureInfoMapper.selectByPrimaryKey(id);
        if (departureInfo == null) {
            throw new RuntimeException("离职表单不存在");
        }
        // 只有待审批状态的表单才可以被撤回
        if (!DepartureAuditStatusEnum.AUDIT.getValue().equals(departureInfo.getAuditStatus())) {
            throw new RuntimeException("离职表单状态异常");
        }
        Integer createdId = departureInfo.getCreatedId();
        if (!userId.equals(createdId)) {
            throw new RuntimeException("只有创建人才可退回");
        }
        // 修改离职表单状态为撤回
        departureInfo.setAuditStatus(DepartureAuditStatusEnum.CANCEL.getValue());
        // 添加撤回的审批记录
        DepartureAudit departureAuditCreated = new DepartureAudit(id, userId, AuditRoleTypeEnum.CANCEL.getValue(), Constants.DEPARTURE_CANCEL_ORDER);
        departureAuditCreated.setLastModifyTime(new Date());
        saveDepartureAudit(departureAuditCreated, userId);
        new BaseModelUtils<>().buildModifiyEntity(departureInfo, userId);
        departureInfoMapper.updateByPrimaryKeySelective(departureInfo);
        logger.info("撤回离职表单接口请求成功");
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public void editDeparture(BaseDepartureBo baseDepartureBo) {
        logger.info("编辑离职表单接口请求参数：" + JSON.toJSONString(baseDepartureBo));
        DepartureInfo departureInfoParam = baseDepartureBo.getDepartureInfo();
        if (departureInfoParam == null) {
            throw new RuntimeException("离职表单对象不能为空");
        }
        Integer departureId = departureInfoParam.getId();
        DepartureInfo departureInfoExist = departureInfoMapper.selectByPrimaryKey(departureId);
        if (departureInfoExist == null) {
            throw new RuntimeException("离职表单不存在");
        }
        Integer userId = baseDepartureBo.getUserId();
        boolean modifiable = getModifiable(departureInfoExist, userId);
        if (!modifiable) {
            throw new RuntimeException("离职表单不能被修改");
        }
        BeanUtils.copyProperties(departureInfoParam, departureInfoExist);

        new BaseModelUtils<>().buildModifiyEntity(departureInfoExist, userId);
        String saveType = baseDepartureBo.getSaveType();
        DepartureAuditStatusEnum auditStatus = null;
        if (Constants.SAVE_TYPE_DRAFT.equals(saveType)) {
            auditStatus = DepartureAuditStatusEnum.DRAFT;
        } else if (Constants.SAVE_TYPE_CONFIRM.equals(saveType)) {
            auditStatus = DepartureAuditStatusEnum.AUDIT;
        } else {
            throw new RuntimeException("未知的保存操作类型");
        }
        // 修改离职表单的状态
        departureInfoExist.setAuditStatus(auditStatus.getValue());

        // 保存审批人
        saveDepartureAuditList(baseDepartureBo, departureId);
        // 保存抄送人
        saveDepartureCopyList(baseDepartureBo, departureId);
        // 重置审批
        departureInfoMapper.updateByPrimaryKeySelective(departureInfoExist);
        logger.info("编辑离职表单接口请求成功");
    }

    @Override
    public void shareDepartureInfo(ShareDepartureInfo shareDepartureInfo) {
        logger.info("分享离职表单接口请求参数：" + JSON.toJSONString(shareDepartureInfo));
        // 离职表单ID
        Integer id = shareDepartureInfo.getId();
        if (id == null) {
            throw new RuntimeException("离职表单ID不能为空");
        }
        DepartureInfo departureInfo = departureInfoMapper.selectByPrimaryKey(id);
        if (departureInfo == null) {
            throw new RuntimeException("离职表单不存在");
        }
        CompanyInfo companyInfo = companyInfoMapper.selectByPrimaryKey(departureInfo.getCompanyId());
        if (companyInfo == null) {
            throw new RuntimeException("公司不存在");
        }
        String address = shareDepartureInfo.getAddress();
        String sendType = shareDepartureInfo.getSendType();
        // 发送短信
        if (SendTypeEnum.PHONE_SEND.getValue().equals(sendType)) {
            new ShortMessageHelper().sendShortMessage(address, buildMessageContent(companyInfo, departureInfo.getId()), SmsChannel.SEND_TYPE_VIP);
            logger.info("短信发送成功");
        } else if (SendTypeEnum.MAIL_SEND.getValue().equals(sendType)) {
            try {
                mailService.sendHtmlMail(address, "离职表单", buildMailContent(companyInfo, departureInfo.getId()));
                logger.info("邮件发送成功");
            } catch (MessagingException e) {
                throw new RuntimeException("邮件发送失败");
            }
        } else {
            logger.info("微信分享离职表单链接");
        }
    }

    @Override
    public Map<String, Object> findQuitEmployeeList(Integer companyId, String nickName) {
        logger.info("查询离职员工库接口请求参数：companyId=" + companyId);
        List<QuitEmployeeVo> quitEmployeeVoList = null;
        try {
            quitEmployeeVoList = departureInfoMapper.selectQuitEmployeeVoList(companyId, nickName);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("出现异常：" + e.getMessage());
            return CommonUtils.setErrorInfo(e);
        }
        logger.info("查询离职员工库接口返回：" + JSON.toJSONString(quitEmployeeVoList));
        return CommonUtils.setSuccessInfo(quitEmployeeVoList);
    }

    @Override
    public Map<String, Object> findDepartureRate(Integer companyId) {
        logger.info("查询公司离职率接口请求参数：companyId=" + companyId);
        CompanyInfo companyInfo = companyInfoMapper.selectByPrimaryKey(companyId);
        if (companyInfo == null) {
            throw new RuntimeException("公司不存在");
        }
        CompanyDepartureRateVo companyDepartureRateVo = new CompanyDepartureRateVo();
        // 当前在职人数
        Integer incumbentsCount = companyInfo.getIncumbentsCount();
        // 设置当前在职人数
        companyDepartureRateVo.setIncumbentsCount(incumbentsCount);
        try {
            // 查询公司本年度离职人数
            int annualTurnoverCount = departureInfoMapper.selectDepartureCount(companyId, DateUtils.getFirstDayOfCurrentYear());
            BigDecimal annualTurnoverRate = calcDepartureRate(incumbentsCount, annualTurnoverCount);
            companyDepartureRateVo.setCurrentYearDepartureRate(annualTurnoverRate);
            // 查询公司本月离职人数
            int monthlyTurnoverCount = departureInfoMapper.selectDepartureCount(companyId, DateUtils.getFirstDayOfCurrentMonth());
            BigDecimal monthlyTurnoverRate = calcDepartureRate(incumbentsCount, monthlyTurnoverCount);
            companyDepartureRateVo.setCurrentMonthDepartureRate(monthlyTurnoverRate);
            List<MonthDepartureRateVo> monthDepartureRateVoList = departureInfoMapper.selectMonthDepartureRateVoList(companyId, DateUtils.getFirstDayOfCurrentYear(), DateUtils.getLastDayOfCurrentYear());
            Map<String, Integer> monthDepartureMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(monthDepartureRateVoList)) {
                for (MonthDepartureRateVo monthDepartureRateVo : monthDepartureRateVoList) {
                    monthDepartureMap.put(monthDepartureRateVo.getMonthValue(), monthDepartureRateVo.getDepartureCount());
                }
            }
            List<String> currentYearMonthStrList = DateUtils.getCurrentYearMonthStrList();
            List<MonthDepartureRateVo> monthDepartureRateVos = new ArrayList<>();
            // 月末在职人数，
            Integer currentMonthIncumbentsCount = incumbentsCount;
            for (int i = 0; i < currentYearMonthStrList.size(); i++) {
                String yearMonth = currentYearMonthStrList.get(i);
                if (i > 0) {
                    String lastMonth = DateUtils.getLastMonth(yearMonth);
                    // 上月离职人数
                    Integer lastMonthDepartureCount = monthDepartureMap.get(lastMonth) == null ? 0 : monthDepartureMap.get(lastMonth);
                    currentMonthIncumbentsCount = currentMonthIncumbentsCount + lastMonthDepartureCount;
                }
                // 当月离职人数
                Integer currentDepartureCount = 0;

                // 当月离职人数
                Integer departureCount = monthDepartureMap.get(yearMonth);
                if (departureCount != null) {
                    currentDepartureCount = departureCount;
                }
                MonthDepartureRateVo monthDepartureRateVo = new MonthDepartureRateVo(yearMonth, currentDepartureCount, currentMonthIncumbentsCount);
                monthDepartureRateVos.add(monthDepartureRateVo);
            }
            companyDepartureRateVo.setMonthDepartureRateList(monthDepartureRateVos);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("出现异常：" + e.getMessage());
            return CommonUtils.setErrorInfo(e);
        }
        logger.info("查询公司离职率接口返回：" + JSON.toJSONString(companyDepartureRateVo));
        return CommonUtils.setSuccessInfo(companyDepartureRateVo);
    }

    @Override
    public Map<String, Object> findDepartureReasonInfo(Integer companyId) {
        logger.info("根据公司ID查询离职原因分析请求参数：companyId=" + companyId);
        List<DepartureRateVo> personalDepartureRateVoList = null;
        List<DepartureRateVo> companyDepartureReasonInfo = null;
        try {
            CompanyInfo companyInfo = companyInfoMapper.selectByPrimaryKey(companyId);
            if (companyInfo == null) {
                throw new RuntimeException("公司不存在");
            }
            // 如果当前用户角色是公司老板，需要返回离职原因的饼状图
            personalDepartureRateVoList = getPersonalDepartureRateVoList(companyId);
            companyDepartureReasonInfo = getCompanyDepartureRateVoList(companyId);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
        Map<String, Object> result = Maps.newHashMap();
        result.put("departureReasonInfo", personalDepartureRateVoList);
        result.put("companyDepartureReasonInfo", companyDepartureReasonInfo);
        logger.info("根据公司ID查询离职原因分析返回：" + JSON.toJSONString(result));
        return CommonUtils.setSuccessInfo(result);
    }

    /**
     * 获取个人离职原因饼状图数据
     *
     * @param companyId
     * @return
     */
    private List<DepartureRateVo> getPersonalDepartureRateVoList(Integer companyId) {
        List<DepartureRateVo> departureRateVoList = departureInfoMapper.selectDepartureRateVoList(companyId);
        Map<String, DepartureRateVo> dataMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(departureRateVoList)) {
            for (DepartureRateVo departureRateVo : departureRateVoList) {
                dataMap.put(departureRateVo.getPersonalDepartureReason(), departureRateVo);
            }
        }
        List<DepartureRateVo> tempDepartureRateVo = new ArrayList<>();
        for (PersonalDepartureReasonEnum reasonEnum : PersonalDepartureReasonEnum.values()) {
            String reasonEnumValue = reasonEnum.getValue();
            DepartureRateVo temp = dataMap.get(reasonEnumValue);
            if (temp == null) {
                tempDepartureRateVo.add(new DepartureRateVo(PersonalDepartureReasonEnum.getNameFromValue(reasonEnumValue)));
            } else {
                tempDepartureRateVo.add(new DepartureRateVo(PersonalDepartureReasonEnum.getNameFromValue(reasonEnumValue), temp.getDepartureCount()));
            }
        }
        return tempDepartureRateVo;
    }

    /**
     * 获取公司离职原因饼状图数据
     *
     * @param companyId
     * @return
     */
    private List<DepartureRateVo> getCompanyDepartureRateVoList(Integer companyId) {
        List<DepartureRateVo> departureRateVoList = departureInfoMapper.selectCompanyDepartureRateVoList(companyId);
        Map<String, DepartureRateVo> dataMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(departureRateVoList)) {
            for (DepartureRateVo departureRateVo : departureRateVoList) {
                dataMap.put(departureRateVo.getPersonalDepartureReason(), departureRateVo);
            }
        }
        List<DepartureRateVo> tempDepartureRateVo = new ArrayList<>();
        for (OfficialDepartureReasonEnum reasonEnum : OfficialDepartureReasonEnum.values()) {
            String reasonEnumValue = reasonEnum.getValue();
            DepartureRateVo temp = dataMap.get(reasonEnumValue);
            if (temp == null) {
                tempDepartureRateVo.add(new DepartureRateVo(OfficialDepartureReasonEnum.getNameFromValue(reasonEnumValue)));
            } else {
                tempDepartureRateVo.add(new DepartureRateVo(OfficialDepartureReasonEnum.getNameFromValue(reasonEnumValue), temp.getDepartureCount()));
            }
        }
        return tempDepartureRateVo;
    }

    @Override
    public Map<String, Object> findDraftList(Integer userId, Integer companyId) {
        logger.info("查询草稿列表请求参数：companyId=" + companyId + ",userId=" + userId);
        List<DraftListVo> draftListVoList = departureInfoMapper.selectDraftLis(userId, companyId);
        Map<String, Object> result = Maps.newHashMap();
        result.put("draftList", draftListVoList);
        logger.info("查询草稿列表接口返回" + JSON.toJSONString(result));
        return CommonUtils.setSuccessInfo(result);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public void removeDeparture(RemoveDepartureInfoBo removeDepartureInfoBo) {
        logger.info("删除离职表单接口请求参数：" + JSON.toJSONString(removeDepartureInfoBo));
        Integer id = removeDepartureInfoBo.getId();
        if (id == null) {
            throw new RuntimeException("离职表单ID不能为空");
        }
        DepartureInfo departureInfo = departureInfoMapper.selectByPrimaryKey(id);
        if (departureInfo == null) {
            throw new RuntimeException("离职表单不存在");
        }
        if (!DepartureAuditStatusEnum.DRAFT.getValue().equals(departureInfo.getAuditStatus())) {
            throw new RuntimeException("只有草稿状态的离职表单才可以删除");
        }
        Integer userId = removeDepartureInfoBo.getUserId();
        if (userId == null) {
            throw new RuntimeException("用户ID不能为空");
        }
        Integer companyId = removeDepartureInfoBo.getCompanyId();
        if (companyId == null) {
            throw new RuntimeException("公司ID不能为空");
        }
        if (!userId.equals(departureInfo.getCreatedId()) || !companyId.equals(departureInfo.getCompanyId())) {
            throw new RuntimeException("您没有删除权限");
        }
        departureInfoMapper.deleteByPrimaryKey(id);
        logger.info("删除离职表单接口请求成功");
    }

    /**
     * 根据在职人数和离职人数，计算离职率
     *
     * @param incumbentsCount 当前在职人数
     * @param departureCount  本期离职人数
     * @return
     */
    private BigDecimal calcDepartureRate(Integer incumbentsCount, Integer departureCount) {
        if (departureCount == null || departureCount.equals(0)) {
            return BigDecimal.ZERO;
        }
        if (incumbentsCount == null || incumbentsCount.equals(0)) {
            return BigDecimal.ZERO;
        }
        // 离职率=离职人数/（离职人数+期末数）×100%。
        BigDecimal departureCountDec = new BigDecimal(departureCount);
        BigDecimal incumbentsCountDec = new BigDecimal(incumbentsCount);
        return departureCountDec.multiply(new BigDecimal(100)).divide((departureCountDec.add(incumbentsCountDec)), 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 判断离职表单是否可以修改
     *
     * @param departureInfo
     * @param userId
     * @return
     */
    private boolean getModifiable(DepartureInfo departureInfo, Integer userId) {
        Integer createdId = departureInfo.getCreatedId();
        String auditStatus = departureInfo.getAuditStatus();
        if (DepartureAuditStatusEnum.AUDIT_REFUSE.getValue().equals(auditStatus) && userId.equals(createdId)) {
            return true;
        }
        return false;
    }

    /**
     * 判断离职表单是否可以撤销
     *
     * @param departureInfo
     * @param userId
     * @return
     */
    private boolean getCancelable(DepartureInfo departureInfo, Integer userId) {
        Integer createdId = departureInfo.getCreatedId();
        String auditStatus = departureInfo.getAuditStatus();
        // 当前用户是离职表单的创建者，并且离职表单状态是审批中才可以撤销
        if (userId.equals(createdId) && DepartureAuditStatusEnum.AUDIT.getValue().equals(auditStatus)) {
            return true;
        }
        return false;
    }
}
