package com.tomasky.departure.model;

import com.tomasky.departure.BaseModel;

import java.io.Serializable;

/**
 * Created by sam on 2019-08-05.11:16
 */

public class RoleInfo extends BaseModel implements Serializable {

    /**
     * 角色名称
     */
    private String roleName;

    private static final long serialVersionUID = 1L;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}