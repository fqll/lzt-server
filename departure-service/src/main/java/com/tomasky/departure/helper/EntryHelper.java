package com.tomasky.departure.helper;

import com.tomasky.departure.model.DepartureInfo;
import org.springframework.stereotype.Component;

/**
 * Created by sam on 2019-12-06.15:44
 */
@Component
public class EntryHelper {
    /**
     * 判断是否可以聊天
     * @param departureInfo
     * @param userId
     * @return
     */
    public boolean isChatAble(DepartureInfo departureInfo, Integer userId) {
        if(departureInfo != null && userId != null) {
            Integer createdId = departureInfo.getCreatedId();
            Integer followUserId = departureInfo.getFollowUserId();
            if(userId.equals(createdId) || userId.equals(followUserId)) {
                return true;
            }
        }
        return false;
    }
}
