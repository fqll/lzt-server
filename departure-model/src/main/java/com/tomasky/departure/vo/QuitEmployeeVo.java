package com.tomasky.departure.vo;

import com.tomasky.departure.enums.DepartureAuditStatusEnum;

/**
 * 用于展示离职员工库
 * Created by sam on 2019-10-15.09:38
 */
public class QuitEmployeeVo {
    /**
     * 离职表单ID
     */
    private Integer id;
    /**
     * 岗位/职务
     */
    private String employeePost;
    /**
     * 离职时间
     */
    private String departureDate;
    /**
     * 当前审批状态，0：创建，1：待审批，2：审批通过，3：审批退回，4：已办结
     */
    private String auditStatus;
    private String auditStatusDesc;
    /**
     * 员工姓名
     */
    private String nickName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployeePost() {
        return employeePost;
    }

    public void setEmployeePost(String employeePost) {
        this.employeePost = employeePost;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditStatusDesc() {
        return DepartureAuditStatusEnum.getNameFromValue(this.auditStatus);
    }

    public void setAuditStatusDesc(String auditStatusDesc) {
        this.auditStatusDesc = auditStatusDesc;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
