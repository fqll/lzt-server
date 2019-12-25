package com.tomasky.departure.model;

import com.tomasky.departure.BaseModel;
import com.tomasky.departure.bo.AuditUserInfoBo;

import java.io.Serializable;

/**
 * Created by sam on 2019-08-05.11:16
 */

public class DepartureAudit extends BaseModel implements Serializable {

    /** 离职表单ID */
    private Integer departureId;

    /** 用户ID */
    private Integer userId;

    /** 操作类型，0：待审批，1：审批中，2：同意离职，3：拒绝离职 */
    private String operateType;

    /** 审批顺序 */
    private Integer auditOrder;

    /** 审批结果，0：不通过，1：通过 */
    private String auditResult;

    /** 审批意见 */
    private String auditOpinions;

    /** 审批角色类型：0：抄送人，1：审批人，2：发起人，3：撤回人 */
    private String auditRoleType;

    /** 已读状态：0：未读，1：已读 */
    private String readStatus;

    /** 是否关注：0：未关注，1：关注 */
    private String followStatus;

    public DepartureAudit(Integer departureId, Integer userId, String auditRoleType, Integer auditOrder) {
        this.departureId = departureId;
        this.userId = userId;
        this.auditRoleType = auditRoleType;
        this.auditOrder = auditOrder;
    }

    public DepartureAudit(AuditUserInfoBo auditUserInfoBo, Integer departureId, String operateType, String auditRoleType) {
        this.departureId = departureId;
        this.userId = auditUserInfoBo.getUserId();
        this.operateType = operateType;
        this.auditOrder = auditUserInfoBo.getAuditOrder();
        this.auditRoleType = auditRoleType;
    }

    public DepartureAudit() {
    }


    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String getAuditRoleType() {
        return auditRoleType;
    }

    public void setAuditRoleType(String auditRoleType) {
        this.auditRoleType = auditRoleType;
    }

    private static final long serialVersionUID = 1L;

    public String getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult;
    }

    public Integer getDepartureId() {
        return departureId;
    }

    public void setDepartureId(Integer departureId) {
        this.departureId = departureId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAuditOrder() {
        return auditOrder;
    }

    public void setAuditOrder(Integer auditOrder) {
        this.auditOrder = auditOrder;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getAuditOpinions() {
        return auditOpinions;
    }

    public void setAuditOpinions(String auditOpinions) {
        this.auditOpinions = auditOpinions;
    }

    public String getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(String followStatus) {
        this.followStatus = followStatus;
    }
}