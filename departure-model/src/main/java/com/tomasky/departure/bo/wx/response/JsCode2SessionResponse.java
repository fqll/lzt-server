package com.tomasky.departure.bo.wx.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by sam on 2019-08-02.09:13
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsCode2SessionResponse extends WxBaseResponseBo {
    private String unionid;
    private String openid;
    @JsonIgnore
    private String session_key;

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }
}
