package com.tomasky.departure.service.impl;

import com.alibaba.fastjson.JSON;
import com.tomasky.departure.bo.wx.response.AccessTokenResponse;
import com.tomasky.departure.bo.wx.response.JsCode2SessionResponse;
import com.tomasky.departure.common.config.WxConfig;
import com.tomasky.departure.common.exception.ServiceException;
import com.tomasky.departure.common.utils.HttpClientUtil;
import com.tomasky.departure.common.utils.LogUtil;
import com.tomasky.departure.common.utils.MiniAppUtil;
import com.tomasky.departure.service.AuthorizationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sam on 2019-08-02.09:16
 */
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Resource
    private WxConfig wxConfig;

    @Override
    public JsCode2SessionResponse getSessionKeyByJsCode(String jsCode) {
        String desc = "调用wx通过jscode换取用户的openid以及sessionkey接口";
        if (StringUtils.isBlank(jsCode)) {
            throw new ServiceException(desc + "异常：jscode不能为空!");
        }
        String url =  getAuthorizationUrl(jsCode);
        String content = HttpClientUtil.getResponseInfoByGet(url);
        LogUtil.info(this.getClass(), desc + "response", content);
        JsCode2SessionResponse result = JSON.parseObject(content, JsCode2SessionResponse.class);
        if (null == result) {
            throw new ServiceException(desc + "错误：返回内容为空!");
        } else if (!MiniAppUtil.isWxResponseSuccess(result.getErrcode(), result.getErrmsg(), desc)) {
            throw new ServiceException(desc + "错误：" + result.getErrcode() + "|" + result.getErrmsg());
        }
        return result;
    }

    @Override
    public AccessTokenResponse getAccessToken() {
        String desc = "获取accessToken接口";
        String url =  getAccessTokenUrl();
        String content = HttpClientUtil.getResponseInfoByGet(url);
        AccessTokenResponse result = JSON.parseObject(content, AccessTokenResponse.class);
        if (null == result) {
            throw new ServiceException(desc + "错误：返回内容为空!");
        } else if (!MiniAppUtil.isWxResponseSuccess(result.getErrcode(), result.getErrmsg(), desc)) {
            throw new ServiceException(desc + "错误：" + result.getErrcode() + "|" + result.getErrmsg());
        }
        return result;
    }

    @Override
    public ResponseEntity<byte[]> getWxacode(String page, String scene) {
        AccessTokenResponse accessToken = getAccessToken();
        String url = getWxacodeUrl(accessToken.getAccessToken());
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("scene", scene);
        paramMap.put("page", page);
        paramMap.put("width", 280);
        paramMap.put("auto_color", true);
        RestTemplate rest = new RestTemplate();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        HttpEntity requestEntity = new HttpEntity(JSON.toJSONString(paramMap), headers);
        return rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
    }

    private String getWxacodeUrl(String accessToken) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(wxConfig.getDomain());
        urlBuilder.append("/wxa/getwxacodeunlimit?access_token=");
        urlBuilder.append(accessToken);
        return urlBuilder.toString();
    }

    private String getAccessTokenUrl() {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(wxConfig.getDomain());
        urlBuilder.append("/cgi-bin/token?grant_type=client_credential&appid=");
        urlBuilder.append(wxConfig.getAppid());
        urlBuilder.append("&secret=");
        urlBuilder.append(wxConfig.getSecret());
        return urlBuilder.toString();
    }

    private String getAuthorizationUrl(String jsCode) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(wxConfig.getDomain());
        urlBuilder.append("/sns/jscode2session?appid=");
        urlBuilder.append(wxConfig.getAppid());
        urlBuilder.append("&secret=");
        urlBuilder.append(wxConfig.getSecret());
        urlBuilder.append("&js_code=");
        urlBuilder.append(jsCode);
        urlBuilder.append("&grant_type=authorization_code");
        return urlBuilder.toString();
    }

}
