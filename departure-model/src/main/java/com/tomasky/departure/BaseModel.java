package com.tomasky.departure;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * Created by sam on 2019-08-05.09:39
 */
public class BaseModel {
    /**
     * 数据库主键
     */
    private Integer id;
    /**
     * 创建人ID
     */
    @JsonIgnore
    private Integer createdId;

    /**
     * 最后修改人的ID
     */
    @JsonIgnore
    private Integer lastModifyId;

    /**
     * 创建时间
     */
    @JsonIgnore
    private Date createdTime;

    /**
     * 最后修改时间
     */
    @JsonIgnore
    private Date lastModifyTime;

    /**
     * 版本号
     */
    @JsonIgnore
    private Integer versionNum;

    /**
     * 是否逻辑删除
     */
    @JsonIgnore
    private String deleted;

    /**
     * 是否有效
     */
    @JsonIgnore
    private String isValid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreatedId() {
        return createdId;
    }

    public void setCreatedId(Integer createdId) {
        this.createdId = createdId;
    }

    public Integer getLastModifyId() {
        return lastModifyId;
    }

    public void setLastModifyId(Integer lastModifyId) {
        this.lastModifyId = lastModifyId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }
}
