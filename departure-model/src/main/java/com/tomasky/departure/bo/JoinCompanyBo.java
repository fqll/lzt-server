package com.tomasky.departure.bo;

/**
 * Created by sam on 2019-08-07.14:24
 */
public class JoinCompanyBo extends BaseBo {
    /**
     * 公司ID
     */
    private Integer companyId;
    /**
     * 入职状态
     */
    private String jobStatus;
    /**
     * 员工昵称
     */
    private String nickName;
    /**
     * 审批状态
     */
    private String auditStatus;

    public JoinCompanyBo(Integer userId, Integer companyId, String jobStatus, String auditStatus) {
        super.setUserId(userId);
        this.companyId = companyId;
        this.jobStatus = jobStatus;
        this.auditStatus = auditStatus;
    }

    public JoinCompanyBo() {
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }
}
