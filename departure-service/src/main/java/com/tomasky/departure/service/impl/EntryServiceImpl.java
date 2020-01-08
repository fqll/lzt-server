package com.tomasky.departure.service.impl;

import com.alibaba.fastjson.JSON;
import com.tomasky.departure.bo.AddEmailBo;
import com.tomasky.departure.bo.DelayEntryBo;
import com.tomasky.departure.bo.SendEntryMailBo;
import com.tomasky.departure.bo.SendEntryNoticeBo;
import com.tomasky.departure.common.utils.BaseModelUtils;
import com.tomasky.departure.common.utils.CommonUtils;
import com.tomasky.departure.common.utils.DateUtils;
import com.tomasky.departure.common.utils.SymmetricEncryptionUtils;
import com.tomasky.departure.cons.Constants;
import com.tomasky.departure.enums.DepartureAuditStatusEnum;
import com.tomasky.departure.enums.EmployeeJobStatus;
import com.tomasky.departure.helper.EntryHelper;
import com.tomasky.departure.helper.GuideHelper;
import com.tomasky.departure.helper.SendMailHelper;
import com.tomasky.departure.mapper.CompanyInfoMapper;
import com.tomasky.departure.mapper.DepartureInfoMapper;
import com.tomasky.departure.mapper.EntryNoticeMapper;
import com.tomasky.departure.mapper.UserRoleInfoMapper;
import com.tomasky.departure.model.CompanyInfo;
import com.tomasky.departure.model.DepartureInfo;
import com.tomasky.departure.model.EntryNotice;
import com.tomasky.departure.model.UserRoleInfo;
import com.tomasky.departure.service.EntryService;
import com.tomasky.departure.vo.DelayEntryVo;
import com.tomasky.departure.vo.EmployeeCheckVo;
import com.tomasky.departure.vo.EntryNoticeVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by sam on 2019-08-16.15:10
 */
@Service
public class EntryServiceImpl implements EntryService {

    private static Logger logger = LoggerFactory.getLogger(EntryServiceImpl.class);

    @Resource
    private UserRoleInfoMapper userRoleInfoMapper;
    @Resource
    private DepartureInfoMapper departureInfoMapper;
    @Resource
    private CompanyInfoMapper companyInfoMapper;
    @Resource
    private GuideHelper guideHelper;
    @Resource
    private EntryHelper entryHelper;
    @Resource
    private EntryNoticeMapper entryNoticeMapper;
    @Resource
    private SendMailHelper sendMailHelper;

    @Override
    @Transactional(rollbackFor={RuntimeException.class, Exception.class})
    public void delayEntry(DelayEntryBo delayEntryBo) {
        logger.info("预备入职接口入参：" + JSON.toJSONString(delayEntryBo));
        if(delayEntryBo.isGuideMode()) {
            return;
        }
        checkDelayEntryBo(delayEntryBo);
        Integer departureId = delayEntryBo.getDepartureId();
        Integer companyId = delayEntryBo.getCompanyId();
        // 离职表单对象
        DepartureInfo departureInfo = departureInfoMapper.selectByPrimaryKey(departureId);
        if (departureInfo == null) {
            throw new RuntimeException("离职表单不存在");
        }
        String auditStatus = departureInfo.getAuditStatus();
        if(! DepartureAuditStatusEnum.FINISH.getValue().equals(auditStatus)) {
            throw new RuntimeException("离职表单状态不是已办结");
        }
        Integer userId = delayEntryBo.getUserId();
        departureInfo.setFollowUserId(userId);
        // 修改离职表单状态为待入职
        departureInfo.setAuditStatus(DepartureAuditStatusEnum.DELAY_ENTRY.getValue());
        departureInfo.setNextCompanyId(companyId);
        departureInfo.setDelayEntryDate(new Date());
        new BaseModelUtils<>().buildModifiyEntity(departureInfo, userId);
        departureInfoMapper.updateByPrimaryKeySelective(departureInfo);
        logger.info("预备入职流程完成");
    }

    @Override
    @Transactional(rollbackFor={RuntimeException.class, Exception.class})
    public void join(DelayEntryBo delayEntryBo) {
        logger.info("入职接口入参：" + JSON.toJSONString(delayEntryBo));
        if(delayEntryBo.isGuideMode()) {
            return;
        }
        checkDelayEntryBo(delayEntryBo);
        Integer departureId = delayEntryBo.getDepartureId();
        Integer userId = delayEntryBo.getUserId();
        // 离职表单对象
        DepartureInfo departureInfo = departureInfoMapper.selectByPrimaryKey(departureId);
        if (departureInfo == null) {
            throw new RuntimeException("离职表单不存在");
        }
        String auditStatus = departureInfo.getAuditStatus();
        if(! DepartureAuditStatusEnum.DELAY_ENTRY.getValue().equals(auditStatus) && ! DepartureAuditStatusEnum.FINISH.getValue().equals(auditStatus)) {
            throw new RuntimeException("离职表单状态不是已办结或预备入职状态");
        }
        departureInfo.setAuditStatus(DepartureAuditStatusEnum.ENTRY.getValue());
        Integer followUserId = departureInfo.getFollowUserId();
        if(followUserId == null) {
            departureInfo.setFollowUserId(userId);
        }
        // 设置入职时间
        departureInfo.setEntryDate(DateUtils.format(new Date()));
        new BaseModelUtils<>().buildModifiyEntity(departureInfo, userId);
        departureInfoMapper.updateByPrimaryKeySelective(departureInfo);
        Integer companyId = delayEntryBo.getCompanyId();
        CompanyInfo companyInfo = companyInfoMapper.selectByPrimaryKey(companyId);
        if (companyInfo == null) {
            throw new RuntimeException("公司不存在");
        }
        Integer incumbentsCount = companyInfo.getIncumbentsCount();
        companyInfo.setIncumbentsCount(incumbentsCount + 1);
        new BaseModelUtils<>().buildModifiyEntity(companyInfo, userId);
        companyInfoMapper.updateByPrimaryKeySelective(companyInfo);
        logger.info("入职流程完成");
    }

    @Override
    public Map<String, Object> findDelayEntry(Integer userId, Integer companyId, String type, String mode, String nickName) {
        logger.info("查询离职列表接口入参userId：" + userId + "，companyId：" + companyId + ",type=" + type + ",mode=" + mode + ",nickName=" + nickName);
        try {
            if(Constants.MODE_GUIDE.equals(mode)) {
                return guideHelper.getDelayEntryListData();
            }
            DepartureAuditStatusEnum departureAuditStatusEnum = null;
            if(Constants.DELAY_ENTRY.equals(type)) {
                departureAuditStatusEnum = DepartureAuditStatusEnum.DELAY_ENTRY;
            } else if(Constants.ENTRY.equals(type)) {
                departureAuditStatusEnum = DepartureAuditStatusEnum.ENTRY;
            } else {
                throw new RuntimeException("位置的状态类型");
            }
            UserRoleInfo userRoleInfo = userRoleInfoMapper.selectByUserIdAndCompanyId(userId, companyId);
            if (userRoleInfo == null) {
                throw new RuntimeException("用户不存在");
            }
            if(! EmployeeJobStatus.INCUMBENCY.getValue().equals(userRoleInfo.getJobStatus())) {
                throw new RuntimeException("当前用户在职状态异常");
            }
        List<DelayEntryVo> delayEntryVoList = departureInfoMapper.selectDelayEntryVoList(companyId, departureAuditStatusEnum.getValue(), nickName);
        if(! CollectionUtils.isEmpty(delayEntryVoList)) {
            for(DelayEntryVo delayEntryVo : delayEntryVoList) {
                DepartureInfo departureInfo = departureInfoMapper.selectByPrimaryKey(delayEntryVo.getDepartureId());
                boolean chatAble = entryHelper.isChatAble(departureInfo, userId);
                delayEntryVo.setChatAble(chatAble);
            }
        }
        logger.info("查询离职列表接口结束");
        return CommonUtils.setSuccessInfo(delayEntryVoList);
    } catch (Exception e) {
        e.printStackTrace();
        return CommonUtils.setErrorInfo(e);
    }
}

    /**
     * 校验输入参数
     * @param delayEntryBo
     */
    private void checkDelayEntryBo(DelayEntryBo delayEntryBo) {
        Integer departureId = delayEntryBo.getDepartureId();
        if (departureId == null) {
            throw new RuntimeException("离职表单ID不能为空");
        }
        Integer companyId = delayEntryBo.getCompanyId();
        if (companyId == null) {
            throw new RuntimeException("公司ID不能为空");
        }
        if(departureId.equals(companyId)) {
            throw new RuntimeException("不能再次入职到原公司");
        }
        Integer userId = delayEntryBo.getUserId();
        if (userId == null) {
            throw new RuntimeException("用户ID不能为空");
        }
    }

    @Override
    public Map<String, Object> findEmployeeCheckList(Integer userId, Integer companyId, String type) {
        if(! Constants.CHECK_TYPE_SEND.equals(type) && ! Constants.CHECK_TYPE_JOIN.equals(type)) {
            throw new RuntimeException("未知的背调类型");
        }
        List<EmployeeCheckVo> employeeCheckVoList = departureInfoMapper.selectEmployeeCheckVoList(userId, companyId, type);
        return CommonUtils.setSuccessInfo(employeeCheckVoList);
    }

    @Override
    public Map<String, Object> hasEmail(Integer userId, Integer companyId) {
        logger.info("是否配置邮箱请求参数，userId=" + userId + "，companyId=" + companyId);
        boolean hasEmail = false;
        UserRoleInfo userRoleInfo = userRoleInfoMapper.selectByUserIdAndCompanyId(userId, companyId);
        if (userRoleInfo == null) {
            throw new RuntimeException("公司或者用户不存在");
        }
        String emailAddress = userRoleInfo.getEmailAddress();
        String emailPassword = userRoleInfo.getEmailPassword();
        if(StringUtils.isNoneBlank(emailAddress) && StringUtils.isNoneBlank(emailPassword)) {
            hasEmail = true;
        }
        logger.info("是否配置邮箱接口返回：" + hasEmail);
        return CommonUtils.setSuccessInfo(hasEmail);
    }

    @Override
    public void saveEmail(AddEmailBo addEmailBo) {
        logger.info("保存邮箱地址和密码请求参数" + JSON.toJSONString(addEmailBo));
        // 校验输入参数
        checkAddEmailBo(addEmailBo);
        boolean authLogin = sendMailHelper.authLogin(new SendEntryMailBo(addEmailBo));
        if(! authLogin) {
            throw new RuntimeException("用户名或者密码错误！");
        }
        Integer userId = addEmailBo.getUserId();
        UserRoleInfo userRoleInfo = userRoleInfoMapper.selectByUserIdAndCompanyId(userId, addEmailBo.getCompanyId());
        if (userRoleInfo == null) {
            throw new RuntimeException("公司或者用户不存在");
        }
        // 邮箱密码加密保存
        String encryptEmailPassword;
        try {
            encryptEmailPassword = SymmetricEncryptionUtils.encrypt(addEmailBo.getEmailPassword());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("加密过程出现异常");
        }
        userRoleInfo.setEmailAddress(addEmailBo.getEmailAddress());
        userRoleInfo.setEmailPassword(encryptEmailPassword);
        userRoleInfo.setMailType(addEmailBo.getMailType());
        new BaseModelUtils<UserRoleInfo>().buildModifiyEntity(userRoleInfo, userId);
        userRoleInfoMapper.updateByPrimaryKeySelective(userRoleInfo);
        logger.info("保存邮箱地址和密码流程结束...");
    }

    /**
     * 校验输入参数
     * @param addEmailBo
     */
    private void checkAddEmailBo(AddEmailBo addEmailBo) {
        Integer userId = addEmailBo.getUserId();
        if (userId == null) {
            throw new RuntimeException("用户ID不能为空");
        }
        Integer companyId = addEmailBo.getCompanyId();
        if (companyId == null) {
            throw new RuntimeException("公司ID不能为空");
        }
        String emailAddress = addEmailBo.getEmailAddress();
        if(StringUtils.isBlank(emailAddress)) {
            throw new RuntimeException("邮箱地址不能为空");
        }
        String emailPassword = addEmailBo.getEmailPassword();
        if(StringUtils.isBlank(emailPassword)) {
            throw new RuntimeException("邮箱密码不能为空");
        }
    }

    @Override
    public void sendEntryNotice(SendEntryNoticeBo sendEntryNoticeBo) {
        logger.info("开始发送入职通知，请求参数：" + JSON.toJSONString(sendEntryNoticeBo));
        UserRoleInfo userRoleInfo = userRoleInfoMapper.selectByUserIdAndCompanyId(sendEntryNoticeBo.getUserId(), sendEntryNoticeBo.getCompanyId());
        if (userRoleInfo == null) {
            throw new RuntimeException("公司或者用户不存在");
        }
        Integer companyId = sendEntryNoticeBo.getCompanyId();
        CompanyInfo companyInfo = companyInfoMapper.selectByPrimaryKey(companyId);
        if (companyInfo == null) {
            throw new RuntimeException("公司不存在");
        }
        String encryptEmailPassword = userRoleInfo.getEmailPassword();
        String emailPassword;
        // 对密码进行解密
        try {
            emailPassword = SymmetricEncryptionUtils.decrypt(encryptEmailPassword);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("解密过程出现异常");
        }
        boolean result = sendMailHelper.sendEmail(new SendEntryMailBo(sendEntryNoticeBo, userRoleInfo, companyInfo, emailPassword, getEnclosurePath()));
        if(result) {
            EntryNotice entryNotice = new EntryNotice(sendEntryNoticeBo);
            new BaseModelUtils<EntryNotice>().buildCreateEntity(entryNotice, sendEntryNoticeBo.getUserId());
            entryNoticeMapper.insert(entryNotice);
        } else {
            throw new RuntimeException("邮件发送失败");
        }
        logger.info("发送入职通知结束");
    }

    /**
     * 获取附件地址
     */
    private String getEnclosurePath() {
        return ClassUtils.getDefaultClassLoader().getResource("static/image").getPath() + "/enclosure.jpg";
    }

    @Override
    public Map<String, Object> findEntryNoticeList(Integer userId, Integer companyId) {
        logger.info("查询入职通知列表请求参数：userId=" + userId + "，companyId=" + companyId);
        List<EntryNoticeVo> entryNoticeVoList = entryNoticeMapper.selectEntryNoticeVoList(userId, companyId);
        logger.info("查询入职通知列表接口返回：" + JSON.toJSONString(entryNoticeVoList));
        return CommonUtils.setSuccessInfo(entryNoticeVoList);
    }

    @Override
    public Map<String, Object> findEntryNoticeDetail(Integer id) {
        logger.info("根据ID查询入职通知详情请求参数：id=" + id);
        EntryNotice entryNotice = entryNoticeMapper.selectByPrimaryKey(id);
        logger.info("根据ID查询入职通知详情返回：" + JSON.toJSONString(entryNotice));
        return CommonUtils.setSuccessInfo(entryNotice);
    }
}
