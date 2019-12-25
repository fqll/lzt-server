package com.tomasky.departure.vo;

/**
 * Created by sam on 2019-08-09.10:52
 */
public class SelectVo {
    /** 下拉列表的值*/
    private String value;
    /** 下拉列表的展示name*/
    private String name;

    public SelectVo(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public SelectVo() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
