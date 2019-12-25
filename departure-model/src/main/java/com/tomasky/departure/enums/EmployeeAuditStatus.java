package com.tomasky.departure.enums;

/**
 * Created by sam on 2019-08-16.14:40
 */
public enum EmployeeAuditStatus {
    WAIT("0", "待审核"),
    ADOPT("1", "审核通过"),
    REFUSE("2", "审核拒绝"),
    INVITE("3", "已邀请");

    private String value;
    private String name;

    EmployeeAuditStatus(String value, String name) {
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
