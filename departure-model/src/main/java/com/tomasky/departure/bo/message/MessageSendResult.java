package com.tomasky.departure.bo.message;

/**
 * 消息发送后的响应对象
 * Created by sam on 2019-08-07.15:51
 */
public class MessageSendResult {
    private String code;
    private String info;
    private boolean status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
