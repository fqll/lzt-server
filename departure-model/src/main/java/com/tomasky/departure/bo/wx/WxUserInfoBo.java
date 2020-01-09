package com.tomasky.departure.bo.wx;

/**
 * Created by sam on 2019-08-08.16:29
 */
public class WxUserInfoBo {
    /**
     * 微信的openid
     */
    private String openId;
    /**
     * 微信头像地址
     */
    private String portraitUrl;
    /**
     * 微信昵称
     */
    private String nickName;
    /**
     * 用户ID
     */
    private Integer userId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
