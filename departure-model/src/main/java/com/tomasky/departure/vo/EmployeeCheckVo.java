package com.tomasky.departure.vo;

/**
 * Created by sam on 2019-12-12.15:43
 */
public class EmployeeCheckVo {
    private Integer id;
    /**
     * 姓名
     */
    private String employeeName;
    /**
     * 离职时间
     */
    private String departureDate;
    /**
     * 入职时间
     */
    private String entryDate;
    /**
     * 预入职时间
     */
    private String delayEntryDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getDelayEntryDate() {
        return delayEntryDate;
    }

    public void setDelayEntryDate(String delayEntryDate) {
        this.delayEntryDate = delayEntryDate;
    }
}
