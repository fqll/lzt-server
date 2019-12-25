package com.tomasky.departure.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tomasky.departure.mapper.UserInfoMapper;
import com.tomasky.departure.model.UserInfo;
import com.tomasky.departure.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by sam on 2019-07-25.17:02
 */
@Service
public class TestServiceImpl implements TestService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestServiceImpl.class);

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public PageInfo<UserInfo> findInnConfigList() {
        PageHelper.startPage(1,10);
        LOGGER.info("123123123");
        List<UserInfo> list= userInfoMapper.selectUserInfoList();
        PageInfo<UserInfo> pageInfo = new PageInfo<UserInfo>(list);
        return pageInfo;
    }
}
