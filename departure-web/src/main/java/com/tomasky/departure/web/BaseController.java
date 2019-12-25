package com.tomasky.departure.web;

import com.google.common.collect.Maps;
import com.tomasky.departure.cons.Constants;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author sam
 */
@RestController
public class BaseController {

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    public static Map<String, Object> new200Map() {
        Map<String, Object> result = Maps.newHashMap();
        result.put(Constants.STATUS, Constants.HTTP_OK);
        return result;
    }

    public static Map<String, Object> new200ResultMap(Object value) {
        return newResultMap(Constants.HTTP_OK, Constants.RESULT, value);
    }

    public static Map<String, Object> new400Map(Object errorValue) {
        return newResultMap(Constants.HTTP_400, Constants.MESSAGE, errorValue);
    }

    public static Map<String, Object> new400Map(String errorKey, Object errorValue) {
        return newResultMap(Constants.HTTP_400, errorKey, errorValue);
    }

    public static Map<String, Object> new500Map(Object errorValue) {
        return newResultMap(Constants.HTTP_500, Constants.MESSAGE, errorValue);
    }

    public static Map<String, Object> new500Map(String errorKey, Object errorValue) {
        return newResultMap(Constants.HTTP_500, errorKey, errorValue);
    }

    public static Map<String, Object> newResultMap(int statusValue, String key, Object value) {
        Map<String, Object> result = Maps.newHashMap();
        if (null != value) {
            result.put(key, value);
        }
        result.put(Constants.STATUS, statusValue);
        return result;
    }


}