package com.tomasky.departure.enums;

/**
 * 邮箱类型
 * Created by sam on 2020-01-02.10:36
 */
public enum MailTypeEnum {
    QQ_EMAIL("0", "QQ邮箱"),
    TENCENT_CORPORATE_EMAIL("1", "腾讯企业邮箱"),
    OTHER("9", "其他邮箱");

    private String value;
    private String name;

    MailTypeEnum(String value, String name) {
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
