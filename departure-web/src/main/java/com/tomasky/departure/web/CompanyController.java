package com.tomasky.departure.web;

import com.tomasky.departure.bo.CreateCompanyBo;
import com.tomasky.departure.bo.IncumbentsCountBo;
import com.tomasky.departure.bo.JoinCompanyBo;
import com.tomasky.departure.bo.UserAuditBo;
import com.tomasky.departure.common.utils.CommonUtils;
import com.tomasky.departure.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by sam on 2019-08-05.16:01
 */
@RestController
@RequestMapping("/company/")
public class CompanyController extends BaseController {
    @Autowired
    private CompanyService companyService;

    /**
     * 根据用户ID和搜索关键字查询用户尚未加入的公司列表
     * @param userId
     * @param keyWords
     * @return
     */
    @RequestMapping(value = "getCompanyList", method = RequestMethod.GET)
    public Map<String, Object> getCompanyList(@RequestParam Integer userId, @RequestParam String keyWords) {
        return companyService.findCompanyInfo(userId, keyWords);
    }

    /**
     * 创建公司
     * @param createCompanyBo
     * @return
     */
    @RequestMapping(value = "newCompany", method = RequestMethod.POST)
    public Map<String, Object> newCompany(@RequestBody CreateCompanyBo createCompanyBo) {
        try {
            return CommonUtils.setSuccessInfo(companyService.createCompany(createCompanyBo));
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
    }

    /**
     * 加入公司
     * @param joinCompanyBo
     * @return
     */
    @RequestMapping(value = "join", method = RequestMethod.POST)
    public Map<String, Object> joinCompany(@RequestBody JoinCompanyBo joinCompanyBo) {
        try {
            companyService.joinCompany(joinCompanyBo);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
        return CommonUtils.setSuccessInfo();
    }

    /**
     * 根据公司ID查询待加入公司的待审批人员列表
     * @param companyId
     * @param auditStatus,0:申请加入，1：已加入
     * @param nickName 员工昵称
     * @return
     */
    @GetMapping(value = "getUserList")
    public Map<String, Object> getUserList(@RequestParam Integer companyId, @RequestParam String auditStatus, String nickName, String mode) {
        return companyService.getAuditUserList(companyId, auditStatus, nickName, mode);
    }

    /**
     * 根据用户ID、公司ID、审批结果，修改用户加入公司的审批状态
     * @param userAuditBo
     * @return
     */
    @PostMapping("audit")
    public Map<String, Object> auditUser(@RequestBody UserAuditBo userAuditBo) {
        try {
            companyService.auditUser(userAuditBo);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
        return CommonUtils.setSuccessInfo();
    }

    /**
     * 邀请加入公司
     * @param userAuditBo
     * @return
     */
    @PostMapping("invite")
    public Map<String, Object> inviteUser(@RequestBody UserAuditBo userAuditBo) {
        try {
            companyService.inviteUser(userAuditBo);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
        return CommonUtils.setSuccessInfo();
    }


    /**
     * 更新员工角色信息
     * @param userAuditBo
     * @return
     */
    @PostMapping("updateRole")
    public Map<String, Object> updateRole(@RequestBody UserAuditBo userAuditBo) {
        try {
            companyService.updateRole(userAuditBo);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
        return CommonUtils.setSuccessInfo();
    }

    /**
     * 校验员工姓名在企业中的唯一性
     * @param companyId
     * @param nickName
     * @param employeeId
     * @return
     */
    @GetMapping(value = "checkNameUnique")
    public Map<String, Object> checkNameUnique(@RequestParam Integer companyId, @RequestParam String nickName, @RequestParam Integer employeeId) {
        return companyService.checkNameUnique(companyId, nickName, employeeId);
    }

    /**
     * 根据公司ID生成公司的小程序码
     * @param companyId
     * @return
     */
    @GetMapping(value = "getCompanyImage")
    public ResponseEntity<byte[]> getCompanyImage(@RequestParam Integer companyId) {
        try {
            return companyService.findCompanyImage(companyId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据公司ID查询公司详情
     * @param companyId
     * @return
     */
    @GetMapping(value = "getCompanyInfo")
    public Map<String, Object> getCompanyInfo(@RequestParam Integer companyId) {
        return companyService.findCompanyInfo(companyId);
    }

    /**
     * 设置主企业
     * @param joinCompanyBo
     * @return
     */
    @RequestMapping(value = "setDefault", method = RequestMethod.POST)
    public Map<String, Object> setDefault(@RequestBody JoinCompanyBo joinCompanyBo) {
        try {
            companyService.setDefault(joinCompanyBo);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
        return CommonUtils.setSuccessInfo();
    }

    /**
     * 修改在职人数
     * @param incumbentsCountBo
     * @return
     */
    @RequestMapping(value = "updateIncumbentsCount", method = RequestMethod.POST)
    public Map<String, Object> updateIncumbentsCount(@RequestBody IncumbentsCountBo incumbentsCountBo) {
        try {
            companyService.modifyIncumbentsCount(incumbentsCountBo);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
        return CommonUtils.setSuccessInfo();
    }

}
