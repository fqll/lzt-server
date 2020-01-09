package com.tomasky.departure.service;

import com.tomasky.departure.bo.AuditReadBo;
import com.tomasky.departure.bo.wx.WxUserInfoBo;

import java.util.Map;

/**
 * Created by sam on 2019-08-02.09:14
 */
public interface UserService {

    /**
     * 通过jscode获取用户的openid以及sessionkey
     *
     * @param jsCode
     * @return
     */
    Map<String, Object> findUserOpenId(String jsCode);

    /**
     * 根据openId获取用户信息
     *
     * @param wxUserInfoBo
     * @return
     */
    Map<String, Object> findUserInfo(WxUserInfoBo wxUserInfoBo);

    /**
     * 完善用户信息
     *
     * @param wxUserInfoBo
     * @return
     */
    Map<String, Object> completedUserInfo(WxUserInfoBo wxUserInfoBo);

    /**
     * 查询全部角色列表
     *
     * @return
     */
    Map<String, Object> findRoleList();

    /**
     * 根据公司ID获取公司的管理人员
     *
     * @param companyId
     * @return
     */
    Map<String, Object> findManageUserList(Integer companyId);

    /**
     * 根据公司ID和用户ID查询审批列表（待我审批的、我已审批的、我发起的、抄送我的）
     *
     * @param userId
     * @param companyId
     * @return
     */
    Map<String, Object> findAuditInfo(Integer userId, Integer companyId, String mode);

    /**
     * 查询全部权限信息
     *
     * @return
     */
    Map<String, Object> findAuthorityList();

    /**
     * 获取当前用户和公司的权限信息
     *
     * @param userId
     * @param companyId
     * @return
     */
    Map<String, Object> findCurrentUserInfo(Integer userId, Integer companyId, String mode);

    /**
     * 设置审批信息为已读
     *
     * @param auditReadBo
     * @return
     */
    void setAlreadyRead(AuditReadBo auditReadBo);

    /**
     * 工作台首页展示待办事项红点
     *
     * @param userId
     * @param companyId
     * @return
     */
    Map<String, Object> findScheduleList(Integer userId, Integer companyId);
}
