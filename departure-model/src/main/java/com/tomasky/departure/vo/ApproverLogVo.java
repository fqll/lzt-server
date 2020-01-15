package com.tomasky.departure.vo;

import com.tomasky.departure.enums.AuditResultEnum;
import com.tomasky.departure.enums.AuditRoleTypeEnum;
import com.tomasky.departure.enums.OperateTypeEnum;

/**
 * Created by sam on 2019-09-27.11:10
 */
public class ApproverLogVo extends BaseUserVo {
    /**
     * 审批顺序
     */
    private Integer auditOrder;
    /**
     * 审批结果
     */
    private String auditResult;
    /**
     * 审批结果描述
     */
    private String auditResultDesc;
    /**
     * 审批意见
     */
    private String auditOpinions;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 审批人所处的阶段值
     */
    private String operateType;
    /**
     * 审批人所处的阶段值描述
     */
    private String operateTypeDesc;
    /**
     * 操作时间
     */
    private String operateTime;
    /**
     * 审批角色类型：0：抄送人，1：审批人，2：发起人，3：撤回人
     */
    private String auditRoleType;
    private String auditRoleTypeDesc;

    private String auditStage;

    public ApproverLogVo() {
    }

    public String getAuditStage() {
        // 如果是发起人
        if (AuditRoleTypeEnum.CREATED.getValue().equals(auditRoleType)) {
            return "发起申请";
        } else if (AuditRoleTypeEnum.CANCEL.getValue().equals(auditRoleType)) {
            return "已撤回";
        } else if (AuditRoleTypeEnum.AUDIT.getValue().equals(auditRoleType)) {
            if (OperateTypeEnum.WAIT_AUDIT.getValue().equals(operateType)) {
                return "待审批";
            } else if (OperateTypeEnum.IN_AUDIT.getValue().equals(operateType)) {
                return "审批中";
            } else if (OperateTypeEnum.ADOPT.getValue().equals(operateType)) {
                return "同意离职";
            } else if (OperateTypeEnum.REFUSE.getValue().equals(operateType)) {
                return "拒绝离职";
            }
        }
        return auditStage;
    }

    public void setAuditStage(String auditStage) {
        this.auditStage = auditStage;
    }

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

    public String getAuditOpinions() {
        return auditOpinions;
    }

    public void setAuditOpinions(String auditOpinions) {
        this.auditOpinions = auditOpinions;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateTypeDesc() {
        if (operateType == null) {
            return "";
        } else {
            return OperateTypeEnum.getNameFromValue(operateType);
        }
    }

    public void setOperateTypeDesc(String operateTypeDesc) {
        this.operateTypeDesc = operateTypeDesc;
    }

    public String getAuditRoleType() {
        return auditRoleType;
    }

    public void setAuditRoleType(String auditRoleType) {
        this.auditRoleType = auditRoleType;
    }

    public String getAuditRoleTypeDesc() {
        if (auditRoleType == null) {
            return "";
        } else {
            return AuditRoleTypeEnum.getNameFromValue(auditRoleType);
        }
    }

    public void setAuditRoleTypeDesc(String auditRoleTypeDesc) {
        this.auditRoleTypeDesc = auditRoleTypeDesc;
    }
}
