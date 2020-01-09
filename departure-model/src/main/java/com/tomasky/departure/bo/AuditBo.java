package com.tomasky.departure.bo;

/**
 * Created by sam on 2019-08-09.15:27
 */
public class AuditBo extends BaseBo {
    /**
     * 离职表单ID
     */
    private Integer departureId;
    /**
     * 审批结果，0：不通过，1：通过
     */
    private String auditResult;
    /**
     * 审批意见
     */
    private String auditOpinions;

    public Integer getDepartureId() {
        return departureId;
    }

    public void setDepartureId(Integer departureId) {
        this.departureId = departureId;
    }

    public String getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult;
    }

    public String getAuditOpinions() {
        return auditOpinions;
    }

    public void setAuditOpinions(String auditOpinions) {
        this.auditOpinions = auditOpinions;
    }
}
