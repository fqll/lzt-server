package com.tomasky.departure.bo;

/**
 * Created by sam on 2019-11-27.15:18
 */
public class BaseOfferBo {
    /** 入住员工姓名*/
    private String employeeName;
    /** 工作岗位*/
    private String post;
    /** 报到时间*/
    private String entryTime;
    /** 报道地址*/
    private String entryAddress;
    /** 接待人员姓名*/
    private String receptionName;
    /** 联系人电话*/
    private String contact;
    /** 补充事项*/
    private String supplement;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getEntryAddress() {
        return entryAddress;
    }

    public void setEntryAddress(String entryAddress) {
        this.entryAddress = entryAddress;
    }

    public String getReceptionName() {
        return receptionName;
    }

    public void setReceptionName(String receptionName) {
        this.receptionName = receptionName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getSupplement() {
        return supplement;
    }

    public void setSupplement(String supplement) {
        this.supplement = supplement;
    }
}
