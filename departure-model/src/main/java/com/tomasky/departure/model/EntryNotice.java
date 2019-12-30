package com.tomasky.departure.model;

import com.tomasky.departure.BaseModel;
import java.io.Serializable;

/**
 * Created by sam on 2019-12-27.14:26
 */

public class EntryNotice extends BaseModel implements Serializable {
    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 公司ID
     */
    private Integer companyId;

    /**
     * 入职人姓名
     */
    private String entryEmployeeName;

    /**
     * 入职职位
     */
    private String entryPosition;

    /**
     * 报到时间
     */
    private String reportDate;

    /**
     * 报到地点
     */
    private String reportLocation;

    /**
     * 接待人员
     */
    private String receptionPersonnel;

    /**
     * 联系人电话
     */
    private String contactNumber;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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