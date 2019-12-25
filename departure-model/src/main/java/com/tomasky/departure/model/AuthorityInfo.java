package com.tomasky.departure.model;

import com.tomasky.departure.BaseModel;

import java.io.Serializable;

/**
 * Created by sam on 2019-10-18.10:56
 */

public class AuthorityInfo extends BaseModel implements Serializable {
    /** 权限编码 */
    private String authorityCode;

    /** 权限名称 */
    private String authorityName;

    /** 状态，0：无效，1：有效 */
    private String status;

    private static final long serialVersionUID = 1L;

    public String getAuthorityCode() {
        return authorityCode;
    }

    public void setAuthorityCode(String authorityCode) {
        this.authorityCode = authorityCode;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}