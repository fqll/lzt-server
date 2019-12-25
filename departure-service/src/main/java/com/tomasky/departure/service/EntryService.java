package com.tomasky.departure.service;

import com.tomasky.departure.bo.DelayEntryBo;

import java.util.Map;

/**
 * Created by sam on 2019-08-16.15:10
 */
public interface EntryService {
    /**
     * 预备入职
     * @param delayEntryBo
     */
    void delayEntry(DelayEntryBo delayEntryBo);

    /**
     * 员工背调后正式入职
     * @param delayEntryBo
     */
    void join(DelayEntryBo delayEntryBo);

    /**
     * 根据用户ID和公司ID查询待入职列表
     * @param userId
     * @param companyId
     * @param type ，0：预入职，1：已入职
     */
    Map<String, Object> findDelayEntry(Integer userId, Integer companyId, String type, String mode, String nickName);

    /**
     * 查询聊天背调的员工列表
     * @param userId
     * @param companyId
     * @param type 0:发起背调，1：配合背调
     * @return
     */
    Map<String, Object> findEmployeeCheckList(Integer userId, Integer companyId, String type);
}
