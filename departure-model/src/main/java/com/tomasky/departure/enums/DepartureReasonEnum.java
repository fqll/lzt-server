package com.tomasky.departure.enums;

/**
 * Created by sam on 2019-09-26.14:08
 */
public enum DepartureReasonEnum {
    COMPANY_REASON("0", "公司原因"),
    PERSONAL_REASON("1", "个人原因");

    private String value;
    private String name;

    DepartureReasonEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static String getNameFromValue(String value) {
        for(DepartureReasonEnum reasonEnum : DepartureReasonEnum.values()) {
            if(reasonEnum.value.equals(value)) {
                return reasonEnum.name;
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
