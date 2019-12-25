package com.tomasky.departure.enums;

/**
 * Created by sam on 2019-08-09.17:16
 */
public enum DepartureSearchTypeEnum {
    FILL("fill", "填写查看"),
    READ("read", "线下填写查看"),
    AUDIT("audit", "审批查看");

    private String value;
    private String name;

    DepartureSearchTypeEnum(String value, String name) {
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
