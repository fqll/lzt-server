package com.tomasky.departure.enums;

/**
 * Created by sam on 2019-08-07.15:17
 */
public enum OfficialDepartureReasonEnum {
    INCAPACITY("0", "能力欠缺"),
    NOT_SOCIABLE("1", "缺乏团队精神"),
    NOT_MATCH_VALUES("2", "价值观不符"),
    BUSINESS_ADJUSTMENT("3", "业务调整");

    private String value;
    private String name;

    OfficialDepartureReasonEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static String getNameFromValue(String value) {
        for(OfficialDepartureReasonEnum reasonEnum : OfficialDepartureReasonEnum.values()) {
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
