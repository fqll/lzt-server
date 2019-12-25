package com.tomasky.departure.model;

import com.tomasky.departure.BaseModel;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sam on 2019-08-16.14:35
 */

public class ChatLog extends BaseModel implements Serializable {
    /** 离职表单ID */
    private Integer departureId;

    /** 用户ID */
    private Integer userId;

    /** 聊天内容 */
    private String chatContent;

    /** 聊天发送时间*/
    private String chatTime;

    public ChatLog() {
    }

    public ChatLog(Integer departureId, Integer userId, String chatContent) {
        this.departureId = departureId;
        this.userId = userId;
        this.chatContent = chatContent;
    }

    private static final long serialVersionUID = 1L;

    public Integer getDepartureId() {
        return departureId;
    }

    public void setDepartureId(Integer departureId) {
        this.departureId = departureId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
    }

    public String getChatTime() {
        Date createdTime = super.getCreatedTime();
        if (createdTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(createdTime);
        }
        return chatTime;
    }

    public void setChatTime(String chatTime) {
        this.chatTime = chatTime;
    }

}