package com.tomasky.departure.web;

import com.tomasky.departure.bo.AuditReadBo;
import com.tomasky.departure.bo.wx.WxUserInfoBo;
import com.tomasky.departure.common.utils.CommonUtils;
import com.tomasky.departure.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by sam on 2019-08-01.17:56
 */
@RestController
@RequestMapping("/user/")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    /**
     * 微信首次登录系统获取openId
     * @param jsCode
     * @return
     */
    @RequestMapping(value = "openid/get", method = RequestMethod.GET)
    public Map<String, Object> getUserOpenId(@RequestParam String jsCode) {
        return userService.findUserOpenId(jsCode);
    }

    /**
     * 根据openId获取用户信息和用户所属的公司信息
     * @param wxUserInfoBo
     * @return
     */
    @RequestMapping(value = "getUserInfo", method = RequestMethod.POST)
    public Map<String, Object> getUserInfo(@RequestBody WxUserInfoBo wxUserInfoBo) {
        return userService.findUserInfo(wxUserInfoBo);
    }

    /**
     * 完善用户信息
     * @param wxUserInfoBo
     * @return
     */
    @RequestMapping(value = "completedUserInfo", method = RequestMethod.POST)
    public Map<String, Object> completedUserInfo(@RequestBody WxUserInfoBo wxUserInfoBo) {
        return userService.completedUserInfo(wxUserInfoBo);
    }


    /**
     * 获取当前用户和公司的权限信息
     * @param userId
     * @param companyId
     * @return
     */
    @RequestMapping(value = "getCurrentUserInfo", method = RequestMethod.GET)
    public Map<String, Object> getCurrentUserInfo(@RequestParam Integer userId, @RequestParam Integer companyId, String mode) {
        return userService.findCurrentUserInfo(userId, companyId, mode);
    }

    /**
     * 查询全部角色信息
     * @return
     */
//    @RequestMapping(value = "getRoleList", method = RequestMethod.GET)
    public Map<String, Object> getRoleList() {
        return userService.findRoleList();
    }

    /**
     * 查询全部权限信息
     * @return
     */
    @RequestMapping(value = "getAuthorityList", method = RequestMethod.GET)
    public Map<String, Object> getAuthorityList() {
        return userService.findAuthorityList();
    }


    /**
     * 根据公司ID获取公司的管理人员
     * @param companyId
     * @return
     */
//    @RequestMapping(value = "getManageUserList", method = RequestMethod.GET)
    public Map<String, Object> getManageUserList(@RequestParam Integer companyId) {
        return userService.findManageUserList(companyId);
    }

    /**
     * 根据公司ID和用户ID查询审批列表（待我审批的、我已审批的、我发起的、抄送我的）
     * @param userId
     * @param companyId
     * @return
     */
    @GetMapping(value = "getAuditInfo")
    public Map<String, Object> getAuditInfo(@RequestParam Integer userId, @RequestParam Integer companyId, String mode) {
        return userService.findAuditInfo(userId, companyId, mode);
    }

    /**
     * 设置审批信息为已读
     * @param auditReadBo
     * @return
     */
    @RequestMapping(value = "setAlreadyRead", method = RequestMethod.POST)
    public Map<String, Object> setAlreadyRead(@RequestBody AuditReadBo auditReadBo) {
        try {
            userService.setAlreadyRead(auditReadBo);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
        return CommonUtils.setSuccessInfo();
    }

    /**
     * 工作台首页展示待办事项红点
     * @param userId
     * @param companyId
     * @return
     */
    @GetMapping(value = "index")
    public Map<String, Object> index(@RequestParam Integer userId, @RequestParam Integer companyId) {
        return userService.findScheduleList(userId, companyId);
    }

}
