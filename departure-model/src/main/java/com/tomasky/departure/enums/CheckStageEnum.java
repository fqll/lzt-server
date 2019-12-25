package com.tomasky.departure.enums;

/**
 * 背景调查所处的阶段
 * Created by sam on 2019-12-12.15:19
 */
public enum CheckStageEnum {
    NOT_BEGUN("0", "未开始背调"),
    IN_CHAT("1", "开始聊天背调");

    private String value;
    private String name;

    CheckStageEnum(String value, String name) {
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
