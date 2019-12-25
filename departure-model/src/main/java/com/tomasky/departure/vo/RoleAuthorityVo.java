package com.tomasky.departure.vo;

import com.tomasky.departure.model.RoleInfo;

import java.util.List;

/**
 * Created by sam on 2019-09-23.15:52
 */
public class RoleAuthorityVo {
    /** 角色*/
    private RoleInfo roleInfo;
    /** 权限列表*/
    private List<AuthorityVo> authorityVoList;

    public RoleAuthorityVo() {
    }

    public RoleAuthorityVo(RoleInfo roleInfo, List<AuthorityVo> authorityVoList) {
        this.roleInfo = roleInfo;
        this.authorityVoList = authorityVoList;
    }

    public RoleInfo getRoleInfo() {
        return roleInfo;
    }

    public void setRoleInfo(RoleInfo roleInfo) {
        this.roleInfo = roleInfo;
    }

    public List<AuthorityVo> getAuthorityVoList() {
        return authorityVoList;
    }

    public void setAuthorityVoList(List<AuthorityVo> authorityVoList) {
        this.authorityVoList = authorityVoList;
    }
}
