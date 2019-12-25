package com.tomasky.departure.service.impl;

import com.alibaba.fastjson.JSON;
import com.tomasky.departure.bo.CreateCompanyBo;
import com.tomasky.departure.bo.IncumbentsCountBo;
import com.tomasky.departure.bo.JoinCompanyBo;
import com.tomasky.departure.bo.UserAuditBo;
import com.tomasky.departure.common.utils.BaseModelUtils;
import com.tomasky.departure.common.utils.CommonUtils;
import com.tomasky.departure.cons.Constants;
import com.tomasky.departure.enums.EmployeeAuditStatus;
import com.tomasky.departure.enums.EmployeeJobStatus;
import com.tomasky.departure.enums.ProcessModeEnum;
import com.tomasky.departure.helper.GuideHelper;
import com.tomasky.departure.mapper.AuthorityInfoMapper;
import com.tomasky.departure.mapper.CompanyInfoMapper;
import com.tomasky.departure.mapper.UserAuthorityInfoMapper;
import com.tomasky.departure.mapper.UserRoleInfoMapper;
import com.tomasky.departure.model.AuthorityInfo;
import com.tomasky.departure.model.CompanyInfo;
import com.tomasky.departure.model.UserAuthorityInfo;
import com.tomasky.departure.model.UserRoleInfo;
import com.tomasky.departure.service.AuthorizationService;
import com.tomasky.departure.service.CompanyService;
import com.tomasky.departure.utils.ShareCodeUtils;
import com.tomasky.departure.vo.ApproverUserVo;
import com.tomasky.departure.vo.CompanyVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * Created by sam on 2019-08-05.16:05
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Resource
    private CompanyInfoMapper companyInfoMapper;
    @Resource
    private UserRoleInfoMapper userRoleInfoMapper;
    @Resource
    private AuthorizationService authorizationService;
    @Resource
    private AuthorityInfoMapper authorityInfoMapper;
    @Resource
    private UserAuthorityInfoMapper userAuthorityInfoMapper;

    @Resource
    private GuideHelper guideHelper;

    private static Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

    @Override
    @Transactional(rollbackFor={RuntimeException.class, Exception.class})
    public Integer createCompany(CreateCompanyBo createCompanyBo) {
        logger.info("创建公司请求参数：" + JSON.toJSONString(createCompanyBo));
        String processMode = createCompanyBo.getProcessMode();
        if(StringUtils.isBlank(processMode)) {
            // 默认为线下模式
            createCompanyBo.setProcessMode(ProcessModeEnum.OFF_LINE.getValue());
        }
        List<CompanyInfo> companyInfoList = companyInfoMapper.selectByCredit(createCompanyBo.getCompanyName(), createCompanyBo.getCreditCode());
        if(! CollectionUtils.isEmpty(companyInfoList)) {
            throw new RuntimeException("公司已经存在");
        }
        Integer userId = createCompanyBo.getUserId();
        // 创建公司
        CompanyInfo companyInfo = new CompanyInfo(createCompanyBo);
        // 完善审计字段
        new BaseModelUtils<>().buildCreateEntity(companyInfo, userId);
        companyInfoMapper.insert(companyInfo);
        // 创建公司角色关联关系
        UserRoleInfo userRoleInfo = new UserRoleInfo(createCompanyBo, companyInfo.getId());
        new BaseModelUtils<>().buildCreateEntity(userRoleInfo, userId);
        userRoleInfo.setNickName(createCompanyBo.getNickName());
        // 公司创建的状态，默认为审批通过
        userRoleInfo.setAuditStatus(EmployeeAuditStatus.ADOPT.getValue());
        // 员工状态为入职
        userRoleInfo.setJobStatus(EmployeeJobStatus.INCUMBENCY.getValue());
        // 默认不是主企业
        userRoleInfo.setIsDefault(Constants.NOT_DEFAULT);
        userRoleInfoMapper.insert(userRoleInfo);
        Integer companyId = companyInfo.getId();
        // 创建用户权限，默认公司创建者拥有全部功能权限
        initCompanyCreatedAuthority(userId, companyId);
        logger.info("创建公司成功");
        return companyId;
    }

    /**
     * 初始化公司创建者的数据权限
     */
    private void initCompanyCreatedAuthority(Integer userId, Integer companyId) {
        List<AuthorityInfo> authorityInfoList = authorityInfoMapper.selectAllAuthorityInfo();
        if(CollectionUtils.isEmpty(authorityInfoList)) {
            throw new RuntimeException("初始化权限出错");
        }
        for(AuthorityInfo authorityInfo: authorityInfoList) {
            UserAuthorityInfo userAuthorityInfo = new UserAuthorityInfo(userId, companyId, authorityInfo.getId());
            new BaseModelUtils<>().buildCreateEntity(userAuthorityInfo, userId);
            userAuthorityInfoMapper.insert(userAuthorityInfo);
        }
    }

    @Override
    public Map<String, Object> findCompanyInfo(Integer userId, String keyWords) {
        logger.info("查询公司列表请求参数：userId=" + userId + "，keyWords=" + keyWords);
        Long id = ShareCodeUtils.codeToId(keyWords);
        List<CompanyInfo> companyInfoList = null;
        try {
            companyInfoList = companyInfoMapper.selectByKeyWords(userId, id.intValue());
        } catch (Exception e) {
            return CommonUtils.setErrorInfo(e);
        }
        logger.info("查询公司列表响应结果：" + JSON.toJSONString(companyInfoList));
        return CommonUtils.setSuccessInfo(companyInfoList);
    }

    @Override
    @Transactional(rollbackFor={RuntimeException.class, Exception.class})
    public void joinCompany(JoinCompanyBo joinCompanyBo) {
        logger.info("加入公司请求参数：" + JSON.toJSONString(joinCompanyBo));
        Integer companyId = joinCompanyBo.getCompanyId();
        String nickName = joinCompanyBo.getNickName();
        Integer userId = joinCompanyBo.getUserId();
        UserRoleInfo userRoleInfoExit = userRoleInfoMapper.selectByUserIdAndCompanyId(userId, companyId);
        if (userRoleInfoExit != null) {
            throw new RuntimeException("不能重复加入公司");
        }
        List<UserRoleInfo> userRoleInfoList = userRoleInfoMapper.selectUserRoleInfo(companyId, nickName, EmployeeAuditStatus.INVITE.getValue());
        if(CollectionUtils.isEmpty(userRoleInfoList)) {
            int nickNameCount = userRoleInfoMapper.selectNickNameCount(joinCompanyBo.getCompanyId(), joinCompanyBo.getNickName(),joinCompanyBo.getUserId());
            if(nickNameCount != 0) {
                throw new RuntimeException("员工姓名重复");
            }
            joinCompanyBo.setJobStatus(EmployeeJobStatus.INCUMBENCY.getValue());
            List<UserRoleInfo> userRoleInfoList2 = userRoleInfoMapper.selectUserRoleInfoListByCompanyAndUser(joinCompanyBo);
            if(! CollectionUtils.isEmpty(userRoleInfoList2)) {
                throw new RuntimeException("不能重复加入公司");
            }
            // 创建公司角色关联关系
            UserRoleInfo userRoleInfo = new UserRoleInfo(joinCompanyBo);
            new BaseModelUtils<>().buildCreateEntity(userRoleInfo, userId);
            userRoleInfo.setAuditStatus(EmployeeAuditStatus.WAIT.getValue());
            // 员工状态为入职
            userRoleInfo.setJobStatus(EmployeeJobStatus.INCUMBENCY.getValue());
            // 默认不是主企业
            userRoleInfo.setIsDefault(Constants.NOT_DEFAULT);
            userRoleInfoMapper.insert(userRoleInfo);
        } else {
            // 如果存在，且只有一条有效数据，则认为是被邀请加入的员工
            if(userRoleInfoList.size() != 1) {
                throw new RuntimeException("员工姓名重复");
            }
            UserRoleInfo userRoleInfo = userRoleInfoList.get(0);
            userRoleInfo.setUserId(userId);
            userRoleInfo.setAuditStatus(EmployeeAuditStatus.WAIT.getValue());
            // 员工状态为入职
            userRoleInfo.setJobStatus(EmployeeJobStatus.INCUMBENCY.getValue());
            // 默认不是主企业
            userRoleInfo.setIsDefault(Constants.NOT_DEFAULT);
            new BaseModelUtils<>().buildModifiyEntity(userRoleInfo, userId);
            userRoleInfoMapper.updateByPrimaryKeySelective(userRoleInfo);
        }
        logger.info("加入公司成功：");
    }

    @Override
    public Map<String, Object> getAuditUserList(Integer companyId, String auditStatus, String nickName, String mode) {
        logger.info("根据公司ID查询加入公司待审批列表输入参数：companyId=" + companyId + "，auditStatus=" + auditStatus + "，nickName=" + nickName + "，mode=" + mode);
        if(Constants.MODE_GUIDE.equals(mode)) {
            return guideHelper.getAuditUserListData();
        }
        List<ApproverUserVo> approverUserVoList = null;
        try {
            approverUserVoList = companyInfoMapper.selectCompanyUserList(companyId, nickName, auditStatus);
            if(EmployeeAuditStatus.INVITE.getValue().equals(auditStatus)) {
                approverUserVoList = companyInfoMapper.selectInviteList(companyId, nickName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
        logger.info("根据公司ID查询加入公司待审批列表接口返回：" + JSON.toJSONString(approverUserVoList));
        return CommonUtils.setSuccessInfo(approverUserVoList);
    }

    @Override
    @Transactional(rollbackFor={RuntimeException.class, Exception.class})
    public void auditUser(UserAuditBo userAuditBo) {
        logger.info("加入公司审批接口输入参数：" + JSON.toJSONString(userAuditBo));
        // 校验输入参数
        checkUserAuditBo(userAuditBo);
        String auditResult = userAuditBo.getAuditResult();
        if(StringUtils.isBlank(auditResult)) {
            throw new RuntimeException("审批结果不能为空");
        }
        Integer companyId = userAuditBo.getCompanyId();
        UserRoleInfo userRoleInfo = userRoleInfoMapper.selectByUserIdAndCompanyId(userAuditBo.getEmployeeId(), companyId);
        if (userRoleInfo == null) {
            throw new RuntimeException("用户不存在");
        }
        String auditStatus = userRoleInfo.getAuditStatus();
        if(! EmployeeAuditStatus.WAIT.getValue().equals(auditStatus)) {
            throw new RuntimeException("该用户已经审核过了");
        }
        CompanyInfo companyInfo = companyInfoMapper.selectByPrimaryKey(companyId);
        if (companyInfo == null) {
            throw new RuntimeException("公司不存在");
        }
        if(EmployeeAuditStatus.ADOPT.getValue().equals(auditResult)) {
            // 更新用户权限信息
            updateUserAuthority(userAuditBo);
            // 更新用户申请的审批状态
            userRoleInfo.setAuditStatus(EmployeeAuditStatus.ADOPT.getValue());
            Integer userId = userAuditBo.getUserId();
            new BaseModelUtils<>().buildModifiyEntity(userRoleInfo, userId);
            // 更新用户和公司关联关系
            userRoleInfoMapper.updateByPrimaryKeySelective(userRoleInfo);
            String processMode = companyInfo.getProcessMode();
            if(StringUtils.isBlank(processMode) || ProcessModeEnum.OFF_LINE.getValue().equals(processMode)) {
                companyInfo.setProcessMode(ProcessModeEnum.ON_LINE.getValue());
                new BaseModelUtils<>().buildModifiyEntity(companyInfo, userId);
                companyInfoMapper.updateByPrimaryKeySelective(companyInfo);
            }
        } else if(EmployeeAuditStatus.REFUSE.getValue().equals(auditResult)) {
            // 审核拒绝删除数据
            userRoleInfoMapper.deleteRefuseRecord(userAuditBo.getEmployeeId(), companyId);
        } else {
            throw new RuntimeException("未知的审核结果类型");
        }
        logger.info("加入公司审批接口完成");
    }

    @Override
    public void inviteUser(UserAuditBo userAuditBo) {
        logger.info("邀请公司接口输入参数：" + JSON.toJSONString(userAuditBo));
        Integer userId = userAuditBo.getUserId();
        if (userId == null) {
            throw new RuntimeException("用户ID不能为空");
        }
        Integer companyId = userAuditBo.getCompanyId();
        if (companyId == null) {
            throw new RuntimeException("公司ID不能为空");
        }
        String nickName = userAuditBo.getNickName();
        if(StringUtils.isBlank(nickName)) {
            throw new RuntimeException("审批人昵称不能为空");
        }
        int nickNameCount = userRoleInfoMapper.selectByNickName(companyId, nickName);
        if(nickNameCount > 0) {
            throw new RuntimeException("已存在相同名称能的审批人");
        }
        CompanyInfo companyInfo = companyInfoMapper.selectByPrimaryKey(companyId);
        if (companyInfo == null) {
            throw new RuntimeException("公司不存在");
        }
        UserRoleInfo userRoleInfo = new UserRoleInfo(companyId, nickName, EmployeeAuditStatus.INVITE.getValue());
        new BaseModelUtils<>().buildCreateEntity(userRoleInfo, userId);
        userRoleInfoMapper.insert(userRoleInfo);
        logger.info("邀请公司接口完成");
    }

    /**
     * 更新用户权限
     */
    private void updateUserAuthority(UserAuditBo userAuditBo) {
        Integer userId = userAuditBo.getUserId();
        Integer employeeId = userAuditBo.getEmployeeId();
        Integer companyId = userAuditBo.getCompanyId();
        List<Integer> authorityList = userAuditBo.getAuthorityList();
        // 删除全部权限数据
        userAuthorityInfoMapper.deleteUserAuthorityInfo(employeeId, companyId);
        // 新增更新后的权限数据
        if(! CollectionUtils.isEmpty(authorityList)) {
            for(Integer authorityId : authorityList) {
                UserAuthorityInfo userAuthorityInfo = new UserAuthorityInfo(employeeId, companyId, authorityId);
                new BaseModelUtils<>().buildCreateEntity(userAuthorityInfo, userId);
                userAuthorityInfoMapper.insert(userAuthorityInfo);
            }
        }
    }

    @Override
    public Map<String, Object> checkNameUnique(Integer companyId, String nickName, Integer employeeId) {
        logger.info("校验员工昵称唯一性接口请求参数：companyId=" + companyId + "，nickName=" + nickName + ",employeeId=" + employeeId);
        boolean isUnique = isSameNickName(companyId, nickName, employeeId);
        logger.info("校验员工昵称唯一性接口返回：" + isUnique);
        return CommonUtils.setSuccessInfo(isUnique);
    }

    /**
     * 判断员工昵称是否重复
     * @param companyId
     * @param nickName
     * @param employeeId
     * @return
     */
    private boolean isSameNickName(Integer companyId, String nickName, Integer employeeId) {
        int nickNameCount = userRoleInfoMapper.selectNickNameCount(companyId, nickName, employeeId);
        if(nickNameCount == 0) {
            return true;
        }
        return false;
    }

    @Override
    public ResponseEntity<byte[]> findCompanyImage(Integer companyId) {
        logger.info("根据公司ID生成公司的小程序码请求参数=" + companyId) ;
        return authorizationService.getWxacode("pages/join_company/join_company", "id=" + companyId);
    }

    @Override
    @Transactional(rollbackFor={RuntimeException.class, Exception.class})
    public void updateRole(UserAuditBo userAuditBo) {
        logger.info("更新员工角色信息请求参数：" + JSON.toJSONString(userAuditBo));
        if(! isSameNickName(userAuditBo.getCompanyId(), userAuditBo.getNickName(), userAuditBo.getEmployeeId())) {
            throw new RuntimeException("员工昵称重复");
        }
        UserRoleInfo userRoleInfo = userRoleInfoMapper.selectByUserIdAndCompanyId(userAuditBo.getEmployeeId(), userAuditBo.getCompanyId());
        if (userRoleInfo == null) {
            throw new RuntimeException("用户不存在");
        }
        // 更新用户昵称
        userRoleInfo.setNickName(userAuditBo.getNickName());
        // 更新用户权限信息
        updateUserAuthority(userAuditBo);
        new BaseModelUtils<>().buildModifiyEntity(userRoleInfo, userAuditBo.getUserId());
        userRoleInfoMapper.updateByPrimaryKeySelective(userRoleInfo);
        logger.info("更新员工角色信息处理完成");
    }

    @Override
    public Map<String, Object> findCompanyInfo(Integer companyId) {
        logger.info("根据公司ID查询公司详情输入参数：" + companyId);
        CompanyVo companyVo = null;
        try {
            CompanyInfo companyInfo = companyInfoMapper.selectByPrimaryKey(companyId);
            if (companyInfo == null) {
                throw new RuntimeException("公司不存在");
            }
            companyVo = new CompanyVo(companyInfo, ShareCodeUtils.toSerialCode(companyId));
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
        logger.info("根据公司ID查询公司详情接口返回：" + JSON.toJSONString(companyVo));
        return CommonUtils.setSuccessInfo(companyVo);
    }

    @Override
    @Transactional(rollbackFor={RuntimeException.class, Exception.class})
    public void setDefault(JoinCompanyBo joinCompanyBo) {
        logger.info("设置主企业输入参数：" + JSON.toJSONString(joinCompanyBo));
        joinCompanyBo.setJobStatus(EmployeeJobStatus.INCUMBENCY.getValue());
        List<UserRoleInfo> companyInfoList = userRoleInfoMapper.selectCompanyInfoList(joinCompanyBo);
        if(CollectionUtils.isEmpty(companyInfoList)) {
            throw new RuntimeException("用户的公司不存在");
        }
        Integer userId = joinCompanyBo.getUserId();
        Integer companyId = joinCompanyBo.getCompanyId();
        // 设置指定公司为主企业
        userRoleInfoMapper.setDefaultCompany(userId, companyId);
        // 设置指定公司以外的其他企业为非主企业
        userRoleInfoMapper.setNotDefaultCompany(userId, companyId);
        logger.info("设置主企业成功");
    }

    @Override
    @Transactional(rollbackFor={RuntimeException.class, Exception.class})
    public void modifyIncumbentsCount(IncumbentsCountBo incumbentsCountBo) {
        logger.info("修改公司在职人数输入参数：" + JSON.toJSONString(incumbentsCountBo));
        Integer companyId = incumbentsCountBo.getCompanyId();
        if (companyId == null) {
            throw new RuntimeException("公司ID不能为空");
        }
        Integer incumbentsCount = incumbentsCountBo.getIncumbentsCount();
        if (incumbentsCount == null) {
            throw new RuntimeException("在职人数不能为空");
        }
        Integer userId = incumbentsCountBo.getUserId();
        if (userId == null) {
            throw new RuntimeException("用户ID不能为空");
        }
        CompanyInfo companyInfo = companyInfoMapper.selectByPrimaryKey(companyId);
        if (companyInfo == null) {
            throw new RuntimeException("公司不存在");
        }
        new BaseModelUtils<>().buildModifiyEntity(companyInfo, userId);
        // 修改在职人数
        companyInfo.setIncumbentsCount(incumbentsCount);
        companyInfoMapper.updateByPrimaryKeySelective(companyInfo);
        logger.info("修改公司在职人数成功");
    }

    /**
     * 校验参数
     * @param userAuditBo
     */
    private void checkUserAuditBo(UserAuditBo userAuditBo) {
        Integer userId = userAuditBo.getUserId();
        if (userId == null) {
            throw new RuntimeException("用户ID不能为空");
        }
        Integer companyId = userAuditBo.getCompanyId();
        if (companyId == null) {
            throw new RuntimeException("公司ID不能为空");
        }
        Integer employeeId = userAuditBo.getEmployeeId();
        if (employeeId == null) {
            throw new RuntimeException("员工ID不能为空");
        }
    }
}
