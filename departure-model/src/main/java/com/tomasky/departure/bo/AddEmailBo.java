package com.tomasky.departure.bo;

/**
 * Created by sam on 2019-12-27.15:51
 */
public class AddEmailBo extends BaseUserCompanyBo {
    /** 电子邮箱地址 */
    private String emailAddress;

    /** 邮箱密码或者授权码 */
    private String emailPassword;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }
}
