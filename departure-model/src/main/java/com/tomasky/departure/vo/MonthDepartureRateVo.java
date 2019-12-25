package com.tomasky.departure.vo;

import java.math.BigDecimal;

/**
 * Created by sam on 2019-10-16.10:28
 */
public class MonthDepartureRateVo {
    /** 月份*/
    private String monthValue;
    /** 离职人数*/
    private Integer departureCount;
    /** 月离职率*/
    private BigDecimal monthDepartureRate;
    /** 月末在职人数*/
    private Integer incumbentsCount;

    public MonthDepartureRateVo() {
    }

    public MonthDepartureRateVo(String monthValue, Integer departureCount, Integer incumbentsCount) {
        this.monthValue = monthValue;
        this.departureCount = departureCount;
        this.incumbentsCount = incumbentsCount;
    }

    public String getMonthValue() {
        return monthValue;
    }

    public void setMonthValue(String monthValue) {
        this.monthValue = monthValue;
    }

    public Integer getDepartureCount() {
        return departureCount;
    }

    public void setDepartureCount(Integer departureCount) {
        this.departureCount = departureCount;
    }

    public BigDecimal getMonthDepartureRate() {
        if(incumbentsCount == null || incumbentsCount.equals(0)) {
            return BigDecimal.ZERO;
        }
        if(departureCount == null || departureCount.equals(0)) {
            return BigDecimal.ZERO;
        }
        // 离职率=离职人数/（离职人数+期末数）×100%。
        BigDecimal departureCountDec = new BigDecimal(departureCount);
        BigDecimal incumbentsCountDec = new BigDecimal(incumbentsCount);
        return departureCountDec.multiply(new BigDecimal(100)).divide((departureCountDec.add(incumbentsCountDec)), 2, BigDecimal.ROUND_HALF_UP);
    }

    public void setMonthDepartureRate(BigDecimal monthDepartureRate) {
        this.monthDepartureRate = monthDepartureRate;
    }

    public Integer getIncumbentsCount() {
        return incumbentsCount;
    }

    public void setIncumbentsCount(Integer incumbentsCount) {
        this.incumbentsCount = incumbentsCount;
    }
}
