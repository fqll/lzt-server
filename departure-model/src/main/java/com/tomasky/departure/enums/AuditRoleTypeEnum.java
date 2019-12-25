package com.tomasky.departure.enums;

/**
 * Created by sam on 2019-09-29.15:30
 */
public enum AuditRoleTypeEnum {
    COPY("0", "抄送人"),
    AUDIT("1", "审批人"),
    CREATED("2", "发起人"),
    CANCEL("3", "撤回人");

    private String value;
    private String name;

    AuditRoleTypeEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static String getNameFromValue(String value) {
        for(AuditRoleTypeEnum roleType : AuditRoleTypeEnum.values()) {
            if(roleType.value.equals(value)) {
                return roleType.name;
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
