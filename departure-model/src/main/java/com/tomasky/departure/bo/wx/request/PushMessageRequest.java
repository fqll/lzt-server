package com.tomasky.departure.bo.wx.request;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by sam on 2019-12-09.14:55
 */
public class PushMessageRequest {
    @JSONField(name = "touser")
    private String openId;
    @JSONField(name = "template_id")
    private String templateId;
    @JSONField(name = "page")
    private String page;
    @JSONField(name = "data")
    private Object data;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
