package com.tomasky.departure.enums;

/**
 * Created by sam on 2019-08-07.17:57
 */
public enum DepartureAuditStatusEnum {
    DRAFT("-1", "草稿"),
    CANCEL("0", "撤回"),
    AUDIT("1", "待审批"),
    AUDIT_REFUSE("2", "审批退回"),
    FINISH("3", "已办结"),
    DELAY_ENTRY("4", "待入职"),
    ENTRY("5", "已入职"),
    ;

    private String value;
    private String name;

    DepartureAuditStatusEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static String getNameFromValue(String value) {
        for (DepartureAuditStatusEnum auditEnum : DepartureAuditStatusEnum.values()) {
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
