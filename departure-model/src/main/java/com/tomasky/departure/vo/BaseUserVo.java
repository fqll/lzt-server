package com.tomasky.departure.vo;

/**
 * Created by sam on 2019-08-28.14:26
 */
public class BaseUserVo {
    /** 用户ID*/
    private Integer userId;
    /** 头像地址*/
    private String portraitUrl;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

}
