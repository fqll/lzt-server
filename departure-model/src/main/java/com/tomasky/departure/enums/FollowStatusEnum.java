package com.tomasky.departure.enums;

/**
 * Created by sam on 2019-08-12.17:40
 */
public enum FollowStatusEnum {
    FOLLOW("1", "关注"),
    UN_FOLLOW("0", "未被关注");

    private String value;
    private String name;

    FollowStatusEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static String getNameFromValue(String value) {
        for (FollowStatusEnum statusEnum : FollowStatusEnum.values()) {
            if (statusEnum.value.equals(value)) {
                return statusEnum.name;
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
