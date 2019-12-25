package com.tomasky.departure.service;

import com.github.pagehelper.PageInfo;
import com.tomasky.departure.model.UserInfo;

/**
 * Created by sam on 2019-07-25.17:01
 */
public interface TestService {
    PageInfo<UserInfo> findInnConfigList();

}
