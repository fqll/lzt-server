package com.tomasky.departure.vo;

/**
 * Created by sam on 2019-09-23.15:53
 */
public class AuthorityVo {
    private Integer id;
    /**
     * 权限编码
     */
    private String authorityCode;
    /**
     * 权限名称
     */
    private String authorityName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
}
