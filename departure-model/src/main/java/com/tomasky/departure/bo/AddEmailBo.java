package com.tomasky.departure.bo;

/**
 * Created by sam on 2019-12-27.15:51
 */
public class AddEmailBo extends BaseUserCompanyBo {
    /** 电子邮箱地址 */
    private String emailAddress;

    /** 邮箱密码或者授权码 */
    private String emailPassword;

    /** 枚举类型，参考枚举类：MailTypeEnum，邮箱类型，1：腾讯企业邮箱*/
    private String mailType;

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

    public String getMailType() {
        return mailType;
    }

    public void setMailType(String mailType) {
        this.mailType = mailType;
    }
}
