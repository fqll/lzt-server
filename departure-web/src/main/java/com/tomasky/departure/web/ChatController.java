package com.tomasky.departure.web;

import com.tomasky.departure.bo.ChatBo;
import com.tomasky.departure.common.utils.CommonUtils;
import com.tomasky.departure.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by sam on 2019-08-19.14:09
 */
@RestController
@RequestMapping("/chat/")
public class ChatController extends BaseController {
    @Autowired
    private ChatService chatService;

    /**
     * 查询聊天记录列表
     *
     * @param userId
     * @param companyId
     * @param departureId
     * @return
     */
    @GetMapping(value = "getChatList")
    public Map<String, Object> getChatList(@RequestParam Integer userId, @RequestParam Integer companyId, @RequestParam Integer departureId, String mode) {
        try {
            return CommonUtils.setSuccessInfo(chatService.findChatList(userId, companyId, departureId, mode));
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
    }

    /**
     * 发送聊天记录
     *
     * @param chatBo
     * @return
     */
    @PostMapping(value = "sendMsg")
    public Map<String, Object> sendMsg(@RequestBody ChatBo chatBo) {
        try {
            chatService.sendMsg(chatBo);
            return CommonUtils.setSuccessInfo();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
    }
}
