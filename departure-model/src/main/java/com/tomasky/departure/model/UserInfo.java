package com.tomasky.departure.model;

import com.tomasky.departure.BaseModel;

import java.io.Serializable;

/**
 * Created by sam on 2019-08-05.11:01
 */

public class UserInfo extends BaseModel implements Serializable {
    /**
     * 微信openId
     */
    private String openId;

    /**
     * 上家公司ID
     */
    private Integer lastCompanyId;

    /**
     * 雇员状态
     */
    private String employeeStatus;

    /**
     * 入职公司ID
     */
    private Integer entryCompanyId;

    /**
     * 入职状态
     */
    private String entryStatus;

    /**
     * 头像地址
     */
    private String portraitUrl;

    /**
     * 微信昵称
     */
    private String nickName;

    public UserInfo() {
    }

    public UserInfo(String openId) {
        this.openId = openId;
    }

    private static final long serialVersionUID = 1L;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getLastCompanyId() {
        return lastCompanyId;
    }

    public void setLastCompanyId(Integer lastCompanyId) {
        this.lastCompanyId = lastCompanyId;
    }

    public String getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(String employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public Integer getEntryCompanyId() {
        return entryCompanyId;
    }

    public void setEntryCompanyId(Integer entryCompanyId) {
        this.entryCompanyId = entryCompanyId;
    }

    public String getEntryStatus() {
        return entryStatus;
    }

    public void setEntryStatus(String entryStatus) {
        this.entryStatus = entryStatus;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}