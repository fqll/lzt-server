package com.tomasky.departure.service.impl;

import com.alibaba.fastjson.JSON;
import com.tomasky.departure.bo.wx.response.AccessTokenResponse;
import com.tomasky.departure.bo.wx.response.UnreadMessagePushResponse;
import com.tomasky.departure.common.config.WxConfig;
import com.tomasky.departure.common.utils.DateUtils;
import com.tomasky.departure.common.utils.HttpClientUtil;
import com.tomasky.departure.service.AuthorizationService;
import com.tomasky.departure.service.PushService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sam on 2019-12-10.09:24
 */
@Service
public class PushServiceImpl implements PushService {

    @Resource
    private WxConfig wxConfig;
    @Resource
    private AuthorizationService authorizationService;

    @Override
    public UnreadMessagePushResponse pushUnreadMessage() {
        AccessTokenResponse accessToken = authorizationService.getAccessToken();
        Map<String, Object> dataMap = new HashMap<>();
        // 设置openId
        dataMap.put("touser", "oQ4YF5vBivwCXQbtpOcwPvcV0KgM");
        dataMap.put("template_id", "ePVSz7E6Wkio1tUr7ym2vIU8Sct5jgsv8NK2rb0p5r0");
        dataMap.put("page", "/tool_index/tool_index");
        Map<String, Object> data1 = new HashMap<>();
        Map<String, Object> name1Data = new HashMap<>();
        name1Data.put("value", "离职通");
        Map<String, Object> time2Data = new HashMap<>();
        time2Data.put("value", DateUtils.format(new Date()));
        Map<String, Object> thing3Data = new HashMap<>();
        thing3Data.put("value", "您有一条未读消息待查看，请打开离职通小程");
        data1.put("name1", name1Data);
        data1.put("time2", time2Data);
        data1.put("thing3", thing3Data);
        dataMap.put("data", data1);
        Map<String, String> requestHeader = new HashMap<>();
        requestHeader.put("Content-Type", "application/json;charset=utf-8");
        String content = HttpClientUtil.getResponseInfoByPost("responseStr", getSendUrl(accessToken.getAccessToken()), JSON.toJSONString(dataMap), requestHeader);
        System.out.println(content);
        return null;
    }

    /**
     * 获取微信推送接口的完整地址
     * https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=ACCESS_TOKEN
     *
     * @return
     */
    private String getSendUrl(String accessToken) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(wxConfig.getDomain());
        urlBuilder.append("/cgi-bin/message/subscribe/send?access_token=" + accessToken);
        return urlBuilder.toString();
    }
}
