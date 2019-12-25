package com.tomasky.departure.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.tomasky.departure.bo.AuditReadBo;
import com.tomasky.departure.bo.wx.WxUserInfoBo;
import com.tomasky.departure.bo.wx.response.JsCode2SessionResponse;
import com.tomasky.departure.common.utils.BaseModelUtils;
import com.tomasky.departure.common.utils.CommonUtils;
import com.tomasky.departure.cons.Constants;
import com.tomasky.departure.enums.EmployeeJobStatus;
import com.tomasky.departure.enums.ReadStatusEnum;
import com.tomasky.departure.helper.EntryHelper;
import com.tomasky.departure.helper.GuideHelper;
import com.tomasky.departure.mapper.AuthorityInfoMapper;
import com.tomasky.departure.mapper.ChatLogMapper;
import com.tomasky.departure.mapper.CompanyInfoMapper;
import com.tomasky.departure.mapper.DepartureAuditMapper;
import com.tomasky.departure.mapper.DepartureInfoMapper;
import com.tomasky.departure.mapper.RoleInfoMapper;
import com.tomasky.departure.mapper.UserAuthorityInfoMapper;
import com.tomasky.departure.mapper.UserInfoMapper;
import com.tomasky.departure.mapper.UserRoleInfoMapper;
import com.tomasky.departure.model.DepartureAudit;
import com.tomasky.departure.model.DepartureInfo;
import com.tomasky.departure.model.RoleInfo;
import com.tomasky.departure.model.UserInfo;
import com.tomasky.departure.service.AuthorizationService;
import com.tomasky.departure.service.UserService;
import com.tomasky.departure.vo.ApproverVo;
import com.tomasky.departure.vo.AuditDepartureVo;
import com.tomasky.departure.vo.AuthorityVo;
import com.tomasky.departure.vo.CompanyInfoVo;
import com.tomasky.departure.vo.RoleAuthorityVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sam on 2019-08-02.09:14
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private AuthorizationService authorizationService;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private CompanyInfoMapper companyInfoMapper;
    @Resource
    private RoleInfoMapper roleInfoMapper;
    @Resource
    private DepartureInfoMapper departureInfoMapper;
    @Resource
    private ChatLogMapper chatLogMapper;
    @Resource
    private AuthorityInfoMapper authorityInfoMapper;
    @Resource
    private UserAuthorityInfoMapper userAuthorityInfoMapper;
    @Resource
    private DepartureAuditMapper departureAuditMapper;
    @Resource
    private UserRoleInfoMapper userRoleInfoMapper;
    @Resource
    private GuideHelper guideHelper;
    @Resource
    private EntryHelper entryHelper;

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public Map<String, Object> findUserOpenId(String jsCode) {
        String openId = null;
        try {
            logger.info("通过jsCode获取openId请求参数：" + jsCode);
            JsCode2SessionResponse code2SessionResponse = authorizationService.getSessionKeyByJsCode(jsCode);
            openId = code2SessionResponse.getOpenid();
            logger.info("通过jsCode获取openId响应结果：" + openId);
        } catch (Exception e) {
            return CommonUtils.setErrorInfo(e);
        }
        return CommonUtils.setSuccessInfo(openId);
    }

    @Override
    @Transactional(rollbackFor={RuntimeException.class, Exception.class})
    public Map<String, Object> findUserInfo(WxUserInfoBo wxUserInfoBo) {
        logger.info("通过openId获取用户信息请求参数：" + JSON.toJSONString(wxUserInfoBo));
        String openId = wxUserInfoBo.getOpenId();
        // openId非空验证
        if(StringUtils.isBlank(openId)) {
            throw new RuntimeException("openId不能为空");
        }
        UserInfo userInfo = null;
        List<CompanyInfoVo> companyInfoList = null;
        try {
            userInfo = userInfoMapper.selectByOpenId(openId);
            if (userInfo == null) {
                userInfo = new UserInfo(openId);
                new BaseModelUtils<>().buildBaseEntity(userInfo);
                userInfoMapper.insert(userInfo);
            } else {
                companyInfoList = getCompanyInfoVoList(userInfo.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
        Map<String, Object> result = Maps.newHashMap();
        result.put("userInfo", userInfo);
        result.put("companyInfoList", companyInfoList);
        logger.info("通过openId获取用户信息响应结果：" + JSON.toJSONString(result));
        return CommonUtils.setSuccessInfo(result);
    }

    @Override
    public Map<String, Object> completedUserInfo(WxUserInfoBo wxUserInfoBo) {
        logger.info("完善用户信息请求参数：" + JSON.toJSONString(wxUserInfoBo));
        Integer userId = wxUserInfoBo.getUserId();
        if(userId == null) {
            throw new RuntimeException("userId不能为空");
        }
        String portraitUrl = wxUserInfoBo.getPortraitUrl();
        String nickName = wxUserInfoBo.getNickName();
        if(StringUtils.isBlank(portraitUrl) || StringUtils.isBlank(nickName)) {
            throw new RuntimeException("微信头像或微信昵称不得为空");
        }
        UserInfo userInfo = null;
        List<CompanyInfoVo> companyInfoList = null;
        try {
            userInfo = userInfoMapper.selectByPrimaryKey(userId);
            if (userInfo == null) {
                throw new RuntimeException("用户不存在");
            }
            userInfo.setPortraitUrl(portraitUrl);
            userInfo.setNickName(nickName);
            userInfoMapper.updateByPrimaryKeySelective(userInfo);
            companyInfoList = getCompanyInfoVoList(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
        Map<String, Object> result = Maps.newHashMap();
        result.put("userInfo", userInfo);
        result.put("companyInfoList", companyInfoList);
        logger.info("完善用户信息响应结果：" + JSON.toJSONString(result));
        return CommonUtils.setSuccessInfo(result);
    }

    /**
     * 根据用户ID查询全部所属公司ID
     * @param userId
     * @return
     */
    private List<CompanyInfoVo> getCompanyInfoVoList(Integer userId) {
        List<CompanyInfoVo> companyInfoList = companyInfoMapper.selectByUserId(userId, EmployeeJobStatus.INCUMBENCY.getValue());
        if(! CollectionUtils.isEmpty(companyInfoList)) {
            for(CompanyInfoVo companyInfoVo: companyInfoList) {
                Integer companyId = companyInfoVo.getCompanyId();
                List<AuthorityVo> authorityVoList = userAuthorityInfoMapper.selectUserAuthorityInfo(userId, companyId);
                companyInfoVo.setAuthorityList(authorityVoList);
            }
        }
        return companyInfoList;
    }

    @Override
    public Map<String, Object> findCurrentUserInfo(Integer userId, Integer companyId, String mode) {
        logger.info("获取当前用户和公司的权限信息请求参数：userId=" + userId + ",companyId=" + companyId + ",mode=" + mode);
        if(Constants.MODE_GUIDE.equals(mode)) {
            return CommonUtils.setSuccessInfo(guideHelper.getCurrentUserInfoData());
        }
        UserInfo userInfo;
        CompanyInfoVo companyInfo;
        try {
            userInfo = userInfoMapper.selectByPrimaryKey(userId);
            if (userInfo == null) {
                throw new RuntimeException("用户不存在");
            }
            companyInfo = companyInfoMapper.selectByUserIdAndCompanyId(userId, companyId);
            if (companyInfo == null) {
                throw new RuntimeException("公司不存在");
            }
            List<AuthorityVo> authorityVoList = userAuthorityInfoMapper.selectUserAuthorityInfo(userId, companyId);
            companyInfo.setAuthorityList(authorityVoList);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
        Map<String, Object> result = Maps.newHashMap();
        result.put("userInfo", userInfo);
        result.put("companyInfo", companyInfo);
        logger.info("获取当前用户和公司的权限信息响应结果：" + JSON.toJSONString(result));
        return CommonUtils.setSuccessInfo(result);
    }

    @Override
    public Map<String, Object> findRoleList() {
        List<RoleAuthorityVo> roleAuthorityVoList = null;
        try {
            List<RoleInfo> roleInfoList = roleInfoMapper.selectAll();
            if(! CollectionUtils.isEmpty(roleInfoList)) {
                roleAuthorityVoList = new ArrayList<>();
                for(RoleInfo roleInfo : roleInfoList) {
                    Integer roleId = roleInfo.getId();
                    List<AuthorityVo> authorityVoList = roleInfoMapper.selectAuthorityListByRoleId(roleId);
                    roleAuthorityVoList.add(new RoleAuthorityVo(roleInfo, authorityVoList));
                }
            }
        } catch (Exception e) {
            return CommonUtils.setErrorInfo(e);
        }
        logger.info("查询角色列表响应结果：" + JSON.toJSONString(roleAuthorityVoList));
        return CommonUtils.setSuccessInfo(roleAuthorityVoList);
    }

    @Override
    public Map<String, Object> findManageUserList(Integer companyId) {
        List<ApproverVo> userInfoList = null;
        try {
            userInfoList = userInfoMapper.selectManageUserList(companyId);
        } catch (Exception e) {
            return CommonUtils.setErrorInfo(e);
        }
        return CommonUtils.setSuccessInfo(userInfoList);
    }

    @Override
    public Map<String, Object> findAuditInfo(Integer userId, Integer companyId, String mode) {
        if(Constants.MODE_GUIDE.equals(mode)) {
            return CommonUtils.setSuccessInfo(guideHelper.getAuditInfoGuideData());
        }
        logger.info("查询审批信息请求参数：userId=" + userId + "，companyId="  + companyId + ",mode=" + mode);
        // 待我审批的
        List<AuditDepartureVo> auditDepartureVoList;
        // 我已审批的
        List<AuditDepartureVo> auditedDepartureVoList;
        // 我发起的
        List<AuditDepartureVo> createdDepartureVoList;
        // 抄送我的
        List<AuditDepartureVo> copyDepartureVoList;
        try {
            auditDepartureVoList = departureInfoMapper.selectAuditDepartureVoList(userId, companyId);
            auditedDepartureVoList = departureInfoMapper.selectAuditedDepartureVoList(userId, companyId);
            buildChatAble(auditedDepartureVoList, userId);
            createdDepartureVoList = departureInfoMapper.selectCreatedDepartureVoList(userId, companyId);
            buildChatAble(createdDepartureVoList, userId);
            copyDepartureVoList = departureInfoMapper.selectCopyDepartureVoList(userId, companyId);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
        Map<String, Object> result = Maps.newHashMap();
        result.put("auditInfo", auditDepartureVoList);
        result.put("auditedInfo", auditedDepartureVoList);
        result.put("createdInfo", createdDepartureVoList);
        result.put("copyInfo", copyDepartureVoList);
        logger.info("查询审批信息接口返回：" + JSON.toJSONString(result));
        return CommonUtils.setSuccessInfo(result);
    }

    private void buildChatAble(List<AuditDepartureVo> auditedDepartureVoList, Integer userId) {
        if(! CollectionUtils.isEmpty(auditedDepartureVoList)) {
            for(AuditDepartureVo auditDepartureVo : auditedDepartureVoList) {
                Integer departureId = auditDepartureVo.getDepartureId();
                boolean chatAble = false;
                int chatCount = chatLogMapper.selectChatLogListSize(departureId);
                DepartureInfo departureInfo = departureInfoMapper.selectByPrimaryKey(departureId);
                boolean canChat = entryHelper.isChatAble(departureInfo, userId);
                if(chatCount > 0 && canChat) {
                    chatAble = true;
                }
                auditDepartureVo.setChatAble(chatAble);
            }
        }
    }

    @Override
    public Map<String, Object> findAuthorityList() {
        List<AuthorityVo> roleAuthorityVoList = null;
        try {
            roleAuthorityVoList = authorityInfoMapper.selectAuthorityVoList();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("出现异常：" + e.getMessage());
            return CommonUtils.setErrorInfo(e);
        }
        logger.info("查询角色列表响应结果：" + JSON.toJSONString(roleAuthorityVoList));
        return CommonUtils.setSuccessInfo(roleAuthorityVoList);
    }

    @Override
    @Transactional(rollbackFor={RuntimeException.class, Exception.class})
    public void setAlreadyRead(AuditReadBo auditReadBo) {
        logger.info("设置审批信息为已读请求参数：" + JSON.toJSONString(auditReadBo));
        Integer userId = auditReadBo.getUserId();
        if (userId == null) {
            throw new RuntimeException("用户ID不能为空");
        }
        Integer departureId = auditReadBo.getDepartureId();
        if (departureId == null) {
            throw new RuntimeException("离职表单ID不能为空");
        }
        String auditRoleType = auditReadBo.getAuditRoleType();
        if(StringUtils.isBlank(auditRoleType)) {
            throw new RuntimeException("操作类型不能为空");
        }
        DepartureInfo departureInfo = departureInfoMapper.selectByPrimaryKey(departureId);
        if (departureInfo == null) {
            throw new RuntimeException("离职表单不存在");
        }
        DepartureAudit departureAudit = departureAuditMapper.selectCurrentAudit(departureId, userId, auditRoleType);
        if (departureAudit == null) {
            throw new RuntimeException("审批类型不能为空");
        }
        new BaseModelUtils<>().buildModifiyEntity(departureAudit, userId);
        // 设置为已读
        departureAudit.setReadStatus(ReadStatusEnum.ALREADY_READ.getValue());
        departureAuditMapper.updateByPrimaryKeySelective(departureAudit);
        logger.info("设置审批信息为已读处理完成");
    }

    @Override
    public Map<String, Object> findScheduleList(Integer userId, Integer companyId) {
        logger.info("查询首页待办事项红点请求参数：userId=" + userId + ",companyId=" + companyId);
        List<AuthorityVo> authorityVoList = authorityInfoMapper.selectAuthorityVoList();
        if(CollectionUtils.isEmpty(authorityVoList)) {
            throw new RuntimeException("权限数据初始化错误");
        }
        Map<String, Object> result = Maps.newHashMap();
        for(AuthorityVo authorityVo : authorityVoList) {
            String authorityCode = authorityVo.getAuthorityCode();
            boolean showUnread = false;
            // 我的审批中是否有需要我审批，或者我关注的
            if(Constants.AUTHORITY_KEY_APPROVAL.equals(authorityCode)) {
                showUnread = getApproval(userId, companyId);
            } else if(Constants.AUTHORITY_KEY_AUTHORITY.equals(authorityCode)) {
                // 员工是否有未处理的加入公司申请
                showUnread = getAuthority(companyId);
            }
            result.put(authorityCode, showUnread);
        }
        logger.info("查询首页待办事项红点接口返回：" + JSON.toJSONString(result));
        return CommonUtils.setSuccessInfo(result);
    }

    /**
     * 我的审批中是否有需要我审批，或者我关注的
     * @param userId
     * @param companyId
     * @return
     */
    private boolean getApproval(Integer userId, Integer companyId) {
        int approvalCount = departureInfoMapper.selectUnreadApprovalCount(userId, companyId);
        int copyCount = departureInfoMapper.selectUnreadCopyCount(userId, companyId);
        if(approvalCount > 0 || copyCount > 0) {
            return true;
        }
        return false;
    }

    /**
     * 员工是否有未处理的加入公司申请
     * @param companyId
     * @return
     */
    private boolean getAuthority(Integer companyId) {
        int authorityCount = userRoleInfoMapper.selectAuthorityCount(companyId);
        if(authorityCount > 0) {
            return true;
        }
        return false;
    }
}
