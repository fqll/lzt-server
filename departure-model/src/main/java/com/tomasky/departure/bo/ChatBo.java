package com.tomasky.departure.bo;

/**
 * Created by sam on 2019-08-19.16:11
 */
public class ChatBo extends BaseBo {
    /** 离职表单ID */
    private Integer departureId;
    /** 发送聊天内容*/
    private String chatContent;

    public Integer getDepartureId() {
        return departureId;
    }

    public void setDepartureId(Integer departureId) {
        this.departureId = departureId;
    }

    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
    }
}
