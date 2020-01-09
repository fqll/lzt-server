package com.tomasky.departure.service;

import com.tomasky.departure.bo.wx.response.AccessTokenResponse;
import com.tomasky.departure.bo.wx.response.JsCode2SessionResponse;
import org.springframework.http.ResponseEntity;

/**
 * Created by sam on 2019-08-02.09:15
 */
public interface AuthorizationService {

    /**
     * 通过jscode换取用户的openid以及sessionkey
     *
     * @param jsCode
     * @return
     */
    JsCode2SessionResponse getSessionKeyByJsCode(String jsCode);

    /**
     * 获取微信的AccessToken
     *
     * @return
     */
    AccessTokenResponse getAccessToken();

    /**
     * 根据accessToken和业务参数生成
     *
     * @param page  页面路径
     * @param scene 参数
     * @return
     */
    ResponseEntity<byte[]> getWxacode(String page, String scene);
}
