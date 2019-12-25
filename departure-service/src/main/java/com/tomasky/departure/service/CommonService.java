package com.tomasky.departure.service;

import java.util.Map;

/**
 * Created by sam on 2019-08-09.10:45
 */
public interface CommonService {
    /**
     * 根据下拉列表的类型查询下拉列表的全部值集合
     * @param type
     * @return
     */
    Map<String, Object> findSelectList(String type);
}
