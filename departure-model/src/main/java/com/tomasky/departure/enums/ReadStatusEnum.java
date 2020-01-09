package com.tomasky.departure.enums;

/**
 * 是否已读状态
 * Created by sam on 2019-10-22.13:24
 */
public enum ReadStatusEnum {
    UNREAD("0", "未读"),
    ALREADY_READ("1", "已读");

    private String value;
    private String name;

    ReadStatusEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static String getNameFromValue(String value) {
        for (ReadStatusEnum readStatusEnum : ReadStatusEnum.values()) {
            if (readStatusEnum.value.equals(value)) {
                return readStatusEnum.name;
            }
        }
        return null;
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
