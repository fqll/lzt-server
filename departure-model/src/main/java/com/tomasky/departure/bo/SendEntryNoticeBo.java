package com.tomasky.departure.bo;

import javax.validation.constraints.NotEmpty;

/**
 * Created by sam on 2020-01-02.15:28
 */
public class SendEntryNoticeBo extends BaseUserCompanyBo {
    /**
     * 目标邮箱地址
     */
    @NotEmpty(message = "目标邮箱地址不能为空")
    private String targetMail;

    /**
     * 入职人姓名
     */
    @NotEmpty(message = "入职人姓名不能为空")
    private String entryEmployeeName;

    /**
     * 入职职位
     */
    @NotEmpty(message = "入职职位不能为空")
    private String entryPosition;

    /**
     * 报到时间
     */
    @NotEmpty(message = "报到时间不能为空")
    private String reportDate;

    /**
     * 报到地点
     */
    @NotEmpty(message = "报到地点不能为空")
    private String reportLocation;

    /**
     * 接待人员
     */
    @NotEmpty(message = "接待人员不能为空")
    private String receptionPersonnel;

    /**
     * 联系人电话
     */
    @NotEmpty(message = "联系人电话不能为空")
    private String contactNumber;

    public String getTargetMail() {
        return targetMail;
    }

    public void setTargetMail(String targetMail) {
        this.targetMail = targetMail;
    }

    public String getEntryEmployeeName() {
        return entryEmployeeName;
    }

    public void setEntryEmployeeName(String entryEmployeeName) {
        this.entryEmployeeName = entryEmployeeName;
    }

    public String getEntryPosition() {
        return entryPosition;
    }

    public void setEntryPosition(String entryPosition) {
        this.entryPosition = entryPosition;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportLocation() {
        return reportLocation;
    }

    public void setReportLocation(String reportLocation) {
        this.reportLocation = reportLocation;
    }

    public String getReceptionPersonnel() {
        return receptionPersonnel;
    }

    public void setReceptionPersonnel(String receptionPersonnel) {
        this.receptionPersonnel = receptionPersonnel;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
