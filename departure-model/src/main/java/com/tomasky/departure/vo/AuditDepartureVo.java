package com.tomasky.departure.vo;

import com.tomasky.departure.enums.DepartureAuditStatusEnum;
import com.tomasky.departure.enums.DepartureReasonEnum;
import com.tomasky.departure.enums.FollowStatusEnum;
import com.tomasky.departure.enums.ReadStatusEnum;

/**
 * Created by sam on 2019-08-08.14:28
 */
public class AuditDepartureVo {
    /** 离职表单ID*/
    private Integer departureId;
    /** 姓名 */
    private String employeeName;
    /** 离职表单提交时间*/
    private String submitDate;
    /** 离职表单审批状态*/
    private String auditStatus;
    /** 离职表单审批状态描述*/
    private String auditStatusDesc;
    /** 是否已经关注，0：未关注，1：已关注*/
    private String followStatus;
    private String followStatusDesc;
    /** 是否可聊天*/
    private boolean chatAble;
    /** 离职原因 */
    private String departureReason;
    /** 离职原因描述，0：公司原因，1：个人原因 */
    private String departureReasonDesc;
    /** 离职时间 */
    private String departureDate;
    /** 已读未读状态*/
    private String readStatus;
    private String readStatusDesc;

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditStatusDesc() {
        if (auditStatus == null) {
            return "";
        } else {
            return DepartureAuditStatusEnum.getNameFromValue(auditStatus);
        }
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String getReadStatusDesc() {
        if (readStatus == null) {
            return "";
        } else {
            return ReadStatusEnum.getNameFromValue(readStatus);
        }
    }

    public void setReadStatusDesc(String readStatusDesc) {
        this.readStatusDesc = readStatusDesc;
    }

    public void setAuditStatusDesc(String auditStatusDesc) {
        this.auditStatusDesc = auditStatusDesc;
    }

    public String getDepartureReason() {
        return departureReason;
    }

    public void setDepartureReason(String departureReason) {
        this.departureReason = departureReason;
    }

    public Integer getDepartureId() {
        return departureId;
    }

    public void setDepartureId(Integer departureId) {
        this.departureId = departureId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    public String getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(String followStatus) {
        this.followStatus = followStatus;
    }

    public String getFollowStatusDesc() {
        if(followStatus == null) {
            return "";
        } else {
            return FollowStatusEnum.getNameFromValue(followStatus);
        }
    }

    public void setFollowStatusDesc(String followStatusDesc) {
        this.followStatusDesc = followStatusDesc;
    }

    public boolean getChatAble() {
        return chatAble;
    }

    public void setChatAble(boolean chatAble) {
        this.chatAble = chatAble;
    }

    public String getDepartureReasonDesc() {
        if (departureReason == null) {
            return "";
        } else {
            return DepartureReasonEnum.getNameFromValue(departureReason);
        }
    }

    public void setDepartureReasonDesc(String departureReasonDesc) {
        this.departureReasonDesc = departureReasonDesc;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }
}
