package com.tomasky.departure.vo;

/**
 * Created by sam on 2019-08-16.16:35
 */
public class DelayEntryVo {
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 离职表单ID
     */
    private Integer departureId;
    /**
     * 员工姓名
     */
    private String employeeName;
    /**
     * 预备入职时间
     */
    private String delayEntryDate;
    /**
     * 是否可聊天
     */
    private boolean chatAble;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDepartureId() {
        return departureId;
    }

    public void setDepartureId(Integer departureId) {
        this.departureId = departureId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDelayEntryDate() {
        return delayEntryDate;
    }

    public void setDelayEntryDate(String delayEntryDate) {
        this.delayEntryDate = delayEntryDate;
    }

    public boolean getChatAble() {
        return chatAble;
    }

    public void setChatAble(boolean chatAble) {
        this.chatAble = chatAble;
    }
}
