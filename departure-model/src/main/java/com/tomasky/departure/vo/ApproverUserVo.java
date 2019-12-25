package com.tomasky.departure.vo;

/**
 * Created by sam on 2019-08-28.14:27
 */
public class ApproverUserVo extends BaseUserVo {
    /** 申请加入时间*/
    private String applyTime;
    /** 员工昵称*/
    private String nickName;
    /** 微信昵称*/
    private String wxNickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getWxNickName() {
        return wxNickName;
    }

    public void setWxNickName(String wxNickName) {
        this.wxNickName = wxNickName;
    }
}
