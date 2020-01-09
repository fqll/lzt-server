package com.tomasky.departure.service;

import java.util.Map;

/**
 * Created by sam on 2019-11-15.11:05
 */
public interface GuideService {

    /**
     * 构造引导待办理预入职的离职表单
     *
     * @return
     */
    Map<String, Object> findEntryDepartureInfoDetail();
}
