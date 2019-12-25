package com.tomasky.departure.model;

import com.tomasky.departure.BaseModel;
import com.tomasky.departure.bo.CreateCompanyBo;

import java.io.Serializable;

/**
 * Created by sam on 2019-08-05.11:16
 */

public class CompanyInfo extends BaseModel implements Serializable {
    /**
    * 公司名称
    */
    private String companyName;

    /**
    * 在职人数
    */
    private Integer incumbentsCount;

    /**
    * 社会统一信用代码
    */
    private String creditCode;

    /**
    * log图片地址
    */
    private String logUrl;

    /**
     * 运营模式，0：线下模式，1：线上模式
     */
    private String processMode;

    private static final long serialVersionUID = 1L;

    public CompanyInfo(CreateCompanyBo createCompanyBo) {
        this.companyName = createCompanyBo.getCompanyName();
        this.incumbentsCount = createCompanyBo.getIncumbentsCount();
        this.creditCode = createCompanyBo.getCreditCode();
        this.logUrl = createCompanyBo.getLogUrl();
        this.processMode = createCompanyBo.getProcessMode();
    }

    public CompanyInfo() {
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

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getLogUrl() {
        return logUrl;
    }

    public void setLogUrl(String logUrl) {
        this.logUrl = logUrl;
    }

    public String getProcessMode() {
        return processMode;
    }

    public void setProcessMode(String processMode) {
        this.processMode = processMode;
    }
}