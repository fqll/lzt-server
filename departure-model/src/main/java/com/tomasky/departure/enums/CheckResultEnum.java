package com.tomasky.departure.enums;

/**
 * Created by sam on 2019-08-23.15:41
 */
public enum CheckResultEnum {
    UNCHECK("0", "未核验"),
    CHECK("1", "已核验");

    private String value;
    private String name;

    CheckResultEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static String getNameFromValue(String value) {
        for (CheckResultEnum auditEnum : CheckResultEnum.values()) {
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
