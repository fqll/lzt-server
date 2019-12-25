package com.tomasky.departure.common.utils;

import com.tomasky.departure.cons.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sam on 2019-08-05.16:21
 */
public class CommonUtils {
    public static Map<String, Object> setSuccessInfo() {
        return setInfo(null, Constants.SUCCESS, Constants.HTTP_OK);
    }

    public static Map<String, Object> setSuccessInfo(Object data) {
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("data", data);
        return setInfo(retMap, Constants.SUCCESS, Constants.HTTP_OK);
    }

    public static Map<String, Object> setErrorInfo(Exception e) {
        return setInfo(null, e.getMessage(), Constants.HTTP_400);
    }

    public static Map<String, Object> setErrorInfo(String message) {
        return setInfo(null, message, Constants.HTTP_400);
    }

    public static Map<String, Object> setErrorInfo(String message, int httpCode) {
        return setInfo(null, message, httpCode);
    }

    public static Map<String, Object> setErrorInfo(Object data, int httpCode) {
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("data", data);
        return setInfo(retMap, Constants.FAILURE, httpCode);
    }

    public static Map<String, Object> setInfo(Map<String, Object> result, String message, int status) {
        return setMessage(result, message, status);
    }

    private static Map<String, Object> setMessage(Map<String, Object> result, String message, int status) {
        if (result == null) {
            result = new HashMap<>();
        }
        if (message != null) {
            result.put(Constants.MESSAGE, message);
        }
        result.put(Constants.STATUS, status);
        return result;
    }
}
