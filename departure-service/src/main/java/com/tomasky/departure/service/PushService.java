package com.tomasky.departure.service;

import com.tomasky.departure.bo.wx.response.UnreadMessagePushResponse;

/**
 * 用于处理消息推送服务
 * Created by sam on 2019-12-09.14:51
 */
public interface PushService {
    UnreadMessagePushResponse pushUnreadMessage();
}
