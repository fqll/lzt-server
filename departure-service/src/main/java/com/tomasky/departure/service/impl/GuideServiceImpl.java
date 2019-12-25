package com.tomasky.departure.service.impl;

import com.tomasky.departure.common.utils.CommonUtils;
import com.tomasky.departure.helper.GuideHelper;
import com.tomasky.departure.service.GuideService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by sam on 2019-11-15.11:08
 */
@Service
public class GuideServiceImpl implements GuideService {

    @Resource
    private GuideHelper guideHelper;

    @Override
    public Map<String, Object> findEntryDepartureInfoDetail() {
        return CommonUtils.setSuccessInfo(guideHelper.getDelayEntryDepartureInfo());
    }
}
