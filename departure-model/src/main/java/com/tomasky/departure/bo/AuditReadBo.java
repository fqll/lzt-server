package com.tomasky.departure.bo;

/**
 * Created by sam on 2019-10-22.15:51
 */
public class AuditReadBo extends BaseBo {
    /**
     * 离职表单ID
     */
    private Integer departureId;
    /**
     * 操作类型，0：抄送人，1：审批人
     */
    private String auditRoleType;

    public Integer getDepartureId() {
        return departureId;
    }

    public void setDepartureId(Integer departureId) {
        this.departureId = departureId;
    }

    public String getAuditRoleType() {
        return auditRoleType;
    }

    public void setAuditRoleType(String auditRoleType) {
        this.auditRoleType = auditRoleType;
    }
}
