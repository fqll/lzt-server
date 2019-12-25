package com.tomasky.departure.enums;

/**
 * Created by sam on 2019-08-07.15:18
 */
public enum PersonalDepartureReasonEnum {
    PROSPECT_REASON("0", "行业前景"),
    DEVELOPMENT_REASON("1", "职业发展"),
    PAY_REASON("2", "薪酬福利"),
    CULTURE_REASON("3", "企业文化"),
    ENVIRONMENT_REASON("4", "工作环境"),
    STRENGTH_REASON("5", "工作强度"),
    RELATIONSHIP_REASON("6", "同事关系"),
    FAMILY_REASON("7", "家庭因素"),
    OTHER_REASON("8", "其他");

    private String value;
    private String name;

    public static String getNameFromValue(String value) {
        for(PersonalDepartureReasonEnum reasonEnum : PersonalDepartureReasonEnum.values()) {
            if(reasonEnum.value.equals(value)) {
                return reasonEnum.name;
            }
        }
        return null;
    }

    PersonalDepartureReasonEnum(String value, String name) {
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
