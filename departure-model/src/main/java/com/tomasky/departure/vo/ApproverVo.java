package com.tomasky.departure.vo;

import com.tomasky.departure.enums.AuditResultEnum;

/**
 * Created by sam on 2019-08-09.10:00
 */
public class ApproverVo extends BaseUserVo {
    /** 审批顺序 */
    private Integer auditOrder;
    /** 审批结果*/
    private String auditResult;
    /** 审批结果描述*/
    private String auditResultDesc;

    public Integer getAuditOrder() {
        return auditOrder;
    }

    public void setAuditOrder(Integer auditOrder) {
        this.auditOrder = auditOrder;
    }

    public String getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult;
    }

    public String getAuditResultDesc() {
        if (auditResult == null) {
            return "";
        } else {
            return AuditResultEnum.getNameFromValue(auditResult);
        }
    }

    public void setAuditResultDesc(String auditResultDesc) {
        this.auditResultDesc = auditResultDesc;
    }
}
