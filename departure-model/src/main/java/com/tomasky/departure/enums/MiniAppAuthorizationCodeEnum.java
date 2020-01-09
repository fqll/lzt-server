package com.tomasky.departure.enums;

import com.tomasky.departure.cons.Constants;

/**
 * 小程序相关授权code/信息枚举
 *
 * @Author momo
 * @Date 2018/9/6 18:23
 */
public enum MiniAppAuthorizationCodeEnum {

    /**
     * 授权code
     */
    VERIFY_TICKET("verify_ticket", "验证token", Constants.CACHE_ONE_HOUR),
    COMPONENT_ACCESS_TOKEN("component_access_token", "调用凭据", Constants.CACHE_TWO_HOUR),
    PRE_AUTH_CODE("pre_auth_code", "预授权码", Constants.CACHE_30_MINUTES),
    AUTHORIZATION_CODE("authorization_code", "授权回调授权码", Constants.CACHE_ONE_HOUR),
    AUTHORIZER_ACCESS_TOKEN("authorizer_access_token", "授权令牌", Constants.CACHE_TWO_HOUR),
    AUTHORIZER_REFRESH_TOKEN("authorizer_refresh_token", "刷新令牌", Constants.CACHE_TWO_HOUR),
    /**
     * 授权信息
     */
    AUTHORIZER_USER_INFO("authorizer_user_info", "授权方信息", Constants.CACHE_MAX_AGE);

    private String code;
    private String name;
    private Integer expires;

    MiniAppAuthorizationCodeEnum(String code, String name, Integer expires) {
        this.code = code;
        this.name = name;
        this.expires = expires;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getExpires() {
        return expires;
    }

    public void setExpires(Integer expires) {
        this.expires = expires;
    }
}
