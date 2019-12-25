package com.tomasky.departure.bo;

/**
 * Created by sam on 2019-08-07.15:41
 */
public class AuditUserInfoBo extends BaseBo {
    /** 审批人排序*/
    private Integer auditOrder;

    public Integer getAuditOrder() {
        return auditOrder;
    }

    public void setAuditOrder(Integer auditOrder) {
        this.auditOrder = auditOrder;
    }
}
