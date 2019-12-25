package com.tomasky.departure.vo;

import com.tomasky.departure.model.CompanyInfo;
import com.tomasky.departure.utils.ShareCodeUtils;

import java.math.BigDecimal;

/**
 * Created by sam on 2019-08-08.10:52
 */
public class EmployeeInfoVo {
    /** 公司ID*/
    private Integer companyId;
    /** 公司名称 */
    private String companyName;
    /** 在职人数 */
    private Integer incumbentsCount;
    /** log图片地址 */
    private String logUrl;
    /** 年离职率*/
    private BigDecimal annualTurnoverRate;
    /** 月离职率*/
    private BigDecimal monthlyTurnoverRate;
    /** 公司ID对应的邀请码*/
    private String shareCode;

    public EmployeeInfoVo(CompanyInfo companyInfo, BigDecimal annualTurnoverRate, BigDecimal monthlyTurnoverRate) {
        this.companyId = companyInfo.getId();
        this.companyName = companyInfo.getCompanyName();
        this.incumbentsCount = companyInfo.getIncumbentsCount();
        this.logUrl = companyInfo.getLogUrl();
        this.annualTurnoverRate = annualTurnoverRate;
        this.monthlyTurnoverRate = monthlyTurnoverRate;
    }

    public EmployeeInfoVo() {
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getIncumbentsCount() {
        return incumbentsCount;
    }

    public void setIncumbentsCount(Integer incumbentsCount) {
        this.incumbentsCount = incumbentsCount;
    }

    public String getLogUrl() {
        return logUrl;
    }

    public void setLogUrl(String logUrl) {
        this.logUrl = logUrl;
    }

    public BigDecimal getAnnualTurnoverRate() {
        return annualTurnoverRate;
    }

    public void setAnnualTurnoverRate(BigDecimal annualTurnoverRate) {
        this.annualTurnoverRate = annualTurnoverRate;
    }

    public BigDecimal getMonthlyTurnoverRate() {
        return monthlyTurnoverRate;
    }

    public void setMonthlyTurnoverRate(BigDecimal monthlyTurnoverRate) {
        this.monthlyTurnoverRate = monthlyTurnoverRate;
    }

    public String getShareCode() {
        Integer companyId = getCompanyId();
        if (companyId != null) {
            return ShareCodeUtils.toSerialCode(companyId);
        }
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }
}
