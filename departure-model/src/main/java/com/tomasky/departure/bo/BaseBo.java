package com.tomasky.departure.bo;

import com.tomasky.departure.cons.Constants;

/**
 * Created by sam on 2019-08-07.15:09
 */
public class BaseBo {
    /** 创建人*/
    private Integer userId;
    /** 模式：引导模式不创建真实数据*/
    private String mode;

    /**
     * 判断是否是引导模式
     * @return
     */
    public boolean isGuideMode() {
        if(mode != null && Constants.MODE_GUIDE.equals(mode)) {
            return true;
        }
        return false;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
