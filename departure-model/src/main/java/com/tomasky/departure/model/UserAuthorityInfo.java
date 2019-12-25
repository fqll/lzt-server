package com.tomasky.departure.model;

import com.tomasky.departure.BaseModel;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by sam on 2019-10-18.14:36
 */

public class UserAuthorityInfo extends BaseModel implements Serializable {
    /**
    * 用户ID
    */
    private Integer userId;

    /**
    * 公司ID
    */
    private Integer companyId;

    /**
    * 权限ID
    */
    private Integer authorityId;

    private static final long serialVersionUID = 1L;

    public UserAuthorityInfo() {
    }

    public UserAuthorityInfo(Integer userId, Integer companyId, Integer authorityId) {
        this.userId = userId;
        this.companyId = companyId;
        this.authorityId = authorityId;
    }

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

    public Integer getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Integer authorityId) {
        this.authorityId = authorityId;
    }

}