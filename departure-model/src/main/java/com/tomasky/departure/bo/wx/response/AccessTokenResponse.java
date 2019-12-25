package com.tomasky.departure.bo.wx.response;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by sam on 2019-08-22.15:28
 */
public class AccessTokenResponse extends WxBaseResponseBo {
    /**
     * access_token : ACCESS_TOKEN
     * expires_in : 7200
     */

    @JSONField(name = "access_token")
    private String accessToken;
    @JSONField(name = "expires_in")
    private int expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
