package com.tomasky.departure.model;

import com.tomasky.departure.BaseModel;
import com.tomasky.departure.bo.CreateCompanyBo;
import com.tomasky.departure.bo.JoinCompanyBo;

import java.io.Serializable;

/**
 * Created by sam on 2019-12-27.15:34
 */

public class UserRoleInfo extends BaseModel implements Serializable {
    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 公司ID
     */
    private Integer companyId;

    /**
     * 审核状态，0：待审核，1：审核通过
     */
    private String auditStatus;

    /**
     * 在职状态，0：在职，1：离职，2：待入职
     */
    private String jobStatus;

    /**
     * 是否是主企业，0不是，1是
     */
    private String isDefault;

    /**
     * 员工昵称
     */
    private String nickName;

    /**
     * 电子邮箱地址
     */
    private String emailAddress;

    /**
     * 邮箱密码或者授权码
     */
    private String emailPassword;

    public UserRoleInfo() {

    }

    public UserRoleInfo(Integer companyId, String nickName, String auditStatus) {
        this.companyId = companyId;
        this.nickName = nickName;
        this.auditStatus = auditStatus;
    }

    public UserRoleInfo(JoinCompanyBo joinCompanyBo) {
        this.userId = joinCompanyBo.getUserId();
        this.companyId = joinCompanyBo.getCompanyId();
        this.nickName = joinCompanyBo.getNickName();
    }

    public UserRoleInfo(CreateCompanyBo createCompanyBo, Integer companyId) {
        this.userId = createCompanyBo.getUserId();
        this.companyId = companyId;
    }

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }
}