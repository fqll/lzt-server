package com.tomasky.departure.common.utils;

import com.tomasky.departure.cons.Constants;

import java.util.Optional;

public class MiniAppUtil {


    /**
     * 调用微信接口返回是否成功
     *
     * @param errCode
     * @param errMsg
     * @param desc
     * @return
     */
    public static boolean isWxResponseSuccess(Integer errCode, String errMsg, String desc) {
        boolean result = false;
        Integer errcode = Optional.ofNullable(errCode).orElse(-1);
        if (Constants.COMMON_NO != errcode) {
            LogUtil.error(MiniAppUtil.class, desc + "出错: " + errCode + "|" + errMsg);
        } else {
            result = true;
            LogUtil.success(MiniAppUtil.class, desc);
        }
        return result;
    }

}
