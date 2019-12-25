package com.tomasky.departure.common.enums;

/**
 * Created by sam on 2019-08-07.15:17
 */
public enum OfficialDepartureReasonEnum {
    COMPANY_REASON("0", "公司原因"),
    PERSONAL_REASON("1", "个人原因");

    private String value;
    private String name;

    OfficialDepartureReasonEnum(String value, String name) {
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
