package com.tomasky.departure.vo;

/**
 * Created by sam on 2020-01-08.10:07
 */
public class EntryNoticeVo {
    /**
     * 通知的主键ID
     */
    private Integer id;
    /**
     * 入职人姓名
     */
    private String entryEmployeeName;
    /**
     * 入职职位
     */
    private String entryPosition;
    /**
     * 发送时间
     */
    private String createdTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEntryEmployeeName() {
        return entryEmployeeName;
    }

    public void setEntryEmployeeName(String entryEmployeeName) {
        this.entryEmployeeName = entryEmployeeName;
    }

    public String getEntryPosition() {
        return entryPosition;
    }

    public void setEntryPosition(String entryPosition) {
        this.entryPosition = entryPosition;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
