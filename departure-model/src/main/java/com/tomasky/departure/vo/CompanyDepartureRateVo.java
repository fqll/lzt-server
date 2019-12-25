package com.tomasky.departure.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 公司离职率
 * Created by sam on 2019-10-16.10:25
 */
public class CompanyDepartureRateVo {
    /** 在职人数 */
    private Integer incumbentsCount;
    /** 当年离职率*/
    private BigDecimal currentYearDepartureRate;
    /** 当月离职率*/
    private BigDecimal currentMonthDepartureRate;
    /** 月离职率*/
    private List<MonthDepartureRateVo> monthDepartureRateList;

    public Integer getIncumbentsCount() {
        return incumbentsCount;
    }

    public void setIncumbentsCount(Integer incumbentsCount) {
        this.incumbentsCount = incumbentsCount;
    }

    public BigDecimal getCurrentYearDepartureRate() {
        return currentYearDepartureRate;
    }

    public void setCurrentYearDepartureRate(BigDecimal currentYearDepartureRate) {
        this.currentYearDepartureRate = currentYearDepartureRate;
    }

    public BigDecimal getCurrentMonthDepartureRate() {
        return currentMonthDepartureRate;
    }

    public void setCurrentMonthDepartureRate(BigDecimal currentMonthDepartureRate) {
        this.currentMonthDepartureRate = currentMonthDepartureRate;
    }

    public List<MonthDepartureRateVo> getMonthDepartureRateList() {
        return monthDepartureRateList;
    }

    public void setMonthDepartureRateList(List<MonthDepartureRateVo> monthDepartureRateList) {
        this.monthDepartureRateList = monthDepartureRateList;
    }
}
