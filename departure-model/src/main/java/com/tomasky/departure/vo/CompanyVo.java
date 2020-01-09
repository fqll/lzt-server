package com.tomasky.departure.vo;

import com.tomasky.departure.model.CompanyInfo;

/**
 * Created by sam on 2019-09-30.15:10
 */
public class CompanyVo {
    /**
     * 公司详情
     */
    private CompanyInfo companyInfo;

    /**
     * 公司ID对应的邀请码
     */
    private String shareCode;

    public CompanyVo() {
    }

    public CompanyVo(CompanyInfo companyInfo, String shareCode) {
        this.companyInfo = companyInfo;
        this.shareCode = shareCode;
    }

    public CompanyInfo getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(CompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }
}
