package com.tomasky.departure.bo;

/**
 * Created by sam on 2019-10-12.16:26
 */
public class ShareDepartureInfo extends BaseBo {
    /**
     * 离职表单ID
     */
    private Integer id;
    /**
     * 发送方式，参考：SendTypeEnum
     */
    private String sendType;
    /**
     * 发送的地址
     */
    private String address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
