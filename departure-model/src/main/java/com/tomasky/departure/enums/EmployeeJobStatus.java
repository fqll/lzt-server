package com.tomasky.departure.enums;

/**
 * Created by sam on 2019-08-16.14:41
 */
public enum EmployeeJobStatus {
    INCUMBENCY("0", "在职"),
    QUIT("1", "离职"),
    STANDBY("2", "待入职");

    private String value;
    private String name;

    EmployeeJobStatus(String value, String name) {
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
