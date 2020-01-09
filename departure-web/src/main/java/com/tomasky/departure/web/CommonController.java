package com.tomasky.departure.web;

import com.tomasky.departure.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by sam on 2019-08-09.10:45
 */
@RestController
@RequestMapping("/common/")
public class CommonController extends BaseController {

    @Autowired
    private CommonService commonService;

    /**
     * 查询下拉列表的值
     *
     * @param type
     * @return
     */
    @RequestMapping(value = "getSelectList", method = RequestMethod.GET)
    public Map<String, Object> getSelectList(@RequestParam String type) {
        return commonService.findSelectList(type);
    }
}
