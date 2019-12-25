package com.tomasky.departure.common.enums;

/**
 * Created by sam on 2019-08-09.14:52
 */
public enum OperateTypeEnum {
    COPY("0", "抄送人"),
    AUDIT("1", "审批人");

    private String value;
    private String name;

    OperateTypeEnum(String value, String name) {
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
