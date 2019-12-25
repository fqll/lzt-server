package com.tomasky.departure.enums;

/**
 * Created by sam on 2019-08-08.10:44
 */
public enum RoleEnum {
    BOSS(1, "老板"),
    HR(2, "人事"),
    LEADER(3, "经理"),
    DIRECTOR(4, "总监"),
    EMPLOYEE(5, "员工");

    private Integer value;
    private String name;

    RoleEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
