package com.tomasky.departure.enums;

/**
 * Created by sam on 2019-08-07.15:17
 */
public enum OfficialDepartureReasonEnum {
    INCAPACITY("0", "试用期内辞退"),
    NOT_SOCIABLE("1", "工作态度"),
    NOT_MATCH_VALUES("2", "工作能力"),
    BUSINESS_ADJUSTMENT("3", "违反公司条例"),
    LAYOFFS_REASON("4", "组织调整/裁员"),
    PERFORMANCE_REASON("5", "绩效不达标"),
    EXPIRATION_CONTRACT("6", "合同到期"),
    OTHER_REASON("7", "其他");

    private String value;
    private String name;

    OfficialDepartureReasonEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static String getNameFromValue(String value) {
        for (OfficialDepartureReasonEnum reasonEnum : OfficialDepartureReasonEnum.values()) {
            if (reasonEnum.value.equals(value)) {
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
