package com.tomasky.departure.vo;

/**
 * Created by sam on 2019-09-23.17:37
 */
public class DepartureInfoListVo {
    /** 离职表单ID*/
    private Integer id;
    /** 公司名称*/
    private String companyName;
    /** 岗位*/
    private String employeePost;
    /** 离职时间*/
    private String departureDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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
}
