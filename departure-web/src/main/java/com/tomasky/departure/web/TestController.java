package com.tomasky.departure.web;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.tomasky.departure.bo.SendEntryMailBo;
import com.tomasky.departure.common.utils.CommonUtils;
import com.tomasky.departure.model.UserInfo;
import com.tomasky.departure.service.MailService;
import com.tomasky.departure.service.PushService;
import com.tomasky.departure.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by sam on 2019-07-25.15:43
 */
@RestController
@RequestMapping("/test/")
public class TestController {

    @Autowired
    private TestService testService;
    @Autowired
    private MailService mailService;
    @Autowired
    private PushService pushService;

    @GetMapping(value = "send")
    public Map<String, Object> send () {
        pushService.pushUnreadMessage();
        return CommonUtils.setSuccessInfo();
}

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String say() {
        PageInfo<UserInfo> innConfigList = testService.findInnConfigList();
        List<UserInfo> list = innConfigList.getList();

        return JSON.toJSONString(list);
    }

    @PostMapping(value = "mail")
    public Map<String, Object> sendMail(@RequestBody SendEntryMailBo sendEntryMailBo) {
        try {
            mailService.sendEntryMail(sendEntryMailBo);
        } catch (Exception e) {
            return CommonUtils.setErrorInfo(e);
        }
        return CommonUtils.setSuccessInfo();
    }
}
