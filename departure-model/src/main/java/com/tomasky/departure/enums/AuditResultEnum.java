package com.tomasky.departure.enums;

/**
 * Created by sam on 2019-08-23.15:41
 */
public enum AuditResultEnum {
    REFUSE("0", "不通过"),
    ADOPT("1", "通过");

    private String value;
    private String name;

    AuditResultEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static String getNameFromValue(String value) {
        for(AuditResultEnum auditEnum : AuditResultEnum.values()) {
            if(auditEnum.value.equals(value)) {
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
