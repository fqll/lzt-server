package com.tomasky.departure.bo;

/**
 * Created by sam on 2019-08-08.11:09
 */
public class DepartureReasonBo {
    /** 离职原因*/
    private String personalDepartureReason;
    /** 离职人数*/
    private int departureEmployeeCount;

    public String getPersonalDepartureReason() {
        return personalDepartureReason;
    }

    public void setPersonalDepartureReason(String personalDepartureReason) {
        this.personalDepartureReason = personalDepartureReason;
    }

    public int getDepartureEmployeeCount() {
        return departureEmployeeCount;
    }

    public void setDepartureEmployeeCount(int departureEmployeeCount) {
        this.departureEmployeeCount = departureEmployeeCount;
    }
}
