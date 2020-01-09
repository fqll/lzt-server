package com.tomasky.departure.enums;

/**
 * Created by sam on 2019-08-09.14:52
 */
public enum OperateTypeEnum {
    WAIT_AUDIT("0", "待审批"),
    IN_AUDIT("1", "审批中"),
    ADOPT("2", "同意离职"),
    REFUSE("3", "拒绝离职");

    private String value;
    private String name;

    OperateTypeEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static String getNameFromValue(String value) {
        for (OperateTypeEnum auditEnum : OperateTypeEnum.values()) {
            if (auditEnum.value.equals(value)) {
                return auditEnum.name;
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
