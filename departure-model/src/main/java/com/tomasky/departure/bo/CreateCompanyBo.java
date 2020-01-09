package com.tomasky.departure.bo;

/**
 * Created by sam on 2019-08-05.15:57
 */
public class CreateCompanyBo extends BaseBo {
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
     * 员工姓名
     */
    private String nickName;
    /**
     * 运营模式，0：线下模式，1：线上模式
     */
    private String processMode;

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProcessMode() {
        return processMode;
    }

    public void setProcessMode(String processMode) {
        this.processMode = processMode;
    }
}
