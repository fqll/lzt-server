package com.tomasky.departure.service;

import com.tomasky.departure.bo.ChatBo;

import java.util.Map;

/**
 * Created by sam on 2019-08-19.15:38
 */
public interface ChatService {

    /**
     * 查询聊天记录列表
     *
     * @param userId
     * @param companyId
     * @param departureId
     * @return
     */
    Map<String, Object> findChatList(Integer userId, Integer companyId, Integer departureId, String mode);

    /**
     * 发送聊天记录
     *
     * @param chatBo
     */
    void sendMsg(ChatBo chatBo);
}
