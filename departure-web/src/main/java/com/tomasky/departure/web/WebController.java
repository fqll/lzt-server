package com.tomasky.departure.web;

import com.tomasky.departure.service.DepartureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by sam on 2019-08-12.09:08
 */
@Controller
@RequestMapping("/web/")
public class WebController {
    @Autowired
    private DepartureService departureService;

    /**
     * 员工填写离职表单前查询表单详情
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("fill")
    public String fillDeparture(@RequestParam Integer id, Model model) {
        Map<String, Object> fillDepartureInfo = departureService.findWebDepartureInfo(id);
        model.addAttribute("fillDepartureInfo", fillDepartureInfo);
        return departureService.findForwardPath(id);
    }

    /**
     * 处理成功页面
     *
     * @param message
     * @param model
     * @return
     */
    @GetMapping("success")
    public String toSuccess(String message, Model model) {
        model.addAttribute("message", message);
        return "success";
    }

    /**
     * 获取小程序码的二进制流数据
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "getImage/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Integer id) {
        try {
            return departureService.getDeparturegetWxacode(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
