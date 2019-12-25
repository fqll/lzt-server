package com.tomasky.departure.bo.wx.response;

/**
 * @Auther: sam
 */
public class WxBaseResponseBo {

    private int errcode;
    private String errmsg;

    public WxBaseResponseBo() {
    }

    public WxBaseResponseBo(int errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

}
