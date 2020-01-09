package com.tomasky.departure.vo;

/**
 * Created by sam on 2019-08-08.14:09
 */
public class DepartureRateVo {
    /**
     * 离职原因
     */
    private String personalDepartureReason;
    /**
     * 离职人数
     */
    private Integer departureCount;

    public DepartureRateVo(String personalDepartureReason, Integer departureCount) {
        this.personalDepartureReason = personalDepartureReason;
        this.departureCount = departureCount;
    }

    public DepartureRateVo(String personalDepartureReason) {
        this.personalDepartureReason = personalDepartureReason;
        this.departureCount = 0;
    }

    public DepartureRateVo() {
    }

    public String getPersonalDepartureReason() {
        return personalDepartureReason;
    }

    public void setPersonalDepartureReason(String personalDepartureReason) {
        this.personalDepartureReason = personalDepartureReason;
    }

    public Integer getDepartureCount() {
        return departureCount;
    }

    public void setDepartureCount(Integer departureCount) {
        this.departureCount = departureCount;
    }
}
