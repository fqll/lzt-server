package com.tomasky.departure.vo;

/**
 * Created by sam on 2019-10-25.10:28
 */
public class DraftListVo {
    /**
     * 离职表单ID
     */
    private Integer id;
    /**
     * 姓名
     */
    private String employeeName;
    /**
     * 创建时间
     */
    private String createdTime;

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

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
