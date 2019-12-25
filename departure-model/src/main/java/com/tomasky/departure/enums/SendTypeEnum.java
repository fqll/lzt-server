package com.tomasky.departure.enums;

/**
 * Created by sam on 2019-08-07.15:35
 */
public enum SendTypeEnum {
    WECHAT_SEND("0", "微信发送"),
    PHONE_SEND("1", "短信发送"),
    MAIL_SEND("2", "邮件发送");

    private String value;
    private String name;

    SendTypeEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
