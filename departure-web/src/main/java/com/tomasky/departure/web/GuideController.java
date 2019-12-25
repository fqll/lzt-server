package com.tomasky.departure.web;

import com.tomasky.departure.service.GuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by sam on 2019-11-15.11:02
 */
@RestController
@RequestMapping("/guide/")
public class GuideController {

    @Autowired
    private GuideService guideService;

    /**
     * 构造引导待办理预入职的离职表单
     * @return
     */
    @GetMapping(value = "getEntryDepartureInfoDetail")
    public Map<String, Object> getEntryDepartureInfoDetail() {
        return guideService.findEntryDepartureInfoDetail();
    }
}
