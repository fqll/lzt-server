package com.tomasky.departure.bo;

import java.util.List;

/**
 * Created by sam on 2019-11-26.14:17
 */
public class SendEntryMailBo extends BaseBo {
    /** 公司ID*/
    private Integer companyId;
    /** 模板ID*/
    private Integer templateId;
    /** 发送邮箱*/
    private String from;
    /** 密码*/
    private String password;
    /** 接收邮件的邮箱*/
    private String to;
    /** 枚举类型，参考枚举类：MailTypeEnum，邮箱类型，1：腾讯企业邮箱*/
    private String mailType;
    /** 邮件消息类型*/
    private MimeMessageDTO message;
    /** 是否是群发*/
    private boolean isGroup;
    /** 附件文件路径集合*/
    private List<String> filepath;

    public SendEntryMailBo() {
    }

    public SendEntryMailBo(AddEmailBo addEmailBo) {
        this.from = addEmailBo.getEmailAddress();
        this.password = addEmailBo.getEmailPassword();
        this.mailType = addEmailBo.getMailType();
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMailType() {
        return mailType;
    }

    public void setMailType(String mailType) {
        this.mailType = mailType;
    }

    public MimeMessageDTO getMessage() {
        return message;
    }

    public void setMessage(MimeMessageDTO message) {
        this.message = message;
    }

    public boolean getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(boolean group) {
        isGroup = group;
    }

    public List<String> getFilepath() {
        return filepath;
    }

    public void setFilepath(List<String> filepath) {
        this.filepath = filepath;
    }
}
