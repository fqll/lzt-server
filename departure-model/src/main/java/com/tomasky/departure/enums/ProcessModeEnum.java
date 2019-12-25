package com.tomasky.departure.enums;

/**
 * 公司审批的模式
 * Created by sam on 2019-12-12.14:12
 */
public enum ProcessModeEnum {
    OFF_LINE("0", "线下模式"),
    ON_LINE("1", "线上模式");

    private String value;
    private String name;

    ProcessModeEnum(String value, String name) {
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
