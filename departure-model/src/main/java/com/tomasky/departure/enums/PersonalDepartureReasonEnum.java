package com.tomasky.departure.enums;

/**
 * Created by sam on 2019-08-07.15:18
 */
public enum PersonalDepartureReasonEnum {
    FAMILY_REASON("0", "家庭原因"),
    BODY_REASON("1", "身体原因"),
    PAY_REASON("2", "薪资福利"),
    TRAFFIC_INCONVENIENCE("3", "交通不便"),
    ENVIRONMENT_REASON("4", "工作压力"),
    STRENGTH_REASON("5", "管理问题"),
    RELATIONSHIP_REASON("6", "工作环境"),
    INDUSTRY_PROSPECT("7", "行业前景"),
    NO_PROMOTION_OPPORTUNITIES("8", "无晋升机会"),
    CAREER_PLANNING("9", "职业规划"),
    EXPIRATION_CONTRACT("10", "合同到期放弃续签"),
    OTHER_REASON("11", "其他");

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
