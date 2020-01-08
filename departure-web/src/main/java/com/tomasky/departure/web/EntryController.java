package com.tomasky.departure.web;

import com.tomasky.departure.bo.AddEmailBo;
import com.tomasky.departure.bo.DelayEntryBo;
import com.tomasky.departure.bo.SendEntryNoticeBo;
import com.tomasky.departure.common.utils.CommonUtils;
import com.tomasky.departure.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

/**
 * Created by sam on 2019-08-16.15:09
 */
@RestController
@RequestMapping("/entry/")
public class EntryController {
    @Autowired
    private EntryService entryService;

    /**
     * 预备入职
     * @param delayEntryBo
     * @return
     */
    @PostMapping(value = "delayEntry")
    public Map<String, Object> delayEntry(@RequestBody DelayEntryBo delayEntryBo) {
        try {
            entryService.delayEntry(delayEntryBo);
            return CommonUtils.setSuccessInfo();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
    }

    /**
     * 根据用户ID和公司ID查询待入职列表
     * @param userId
     * @param companyId
     * @param type，0：预入职，1：已入职
     * @return
     */
    @GetMapping(value = "getDelayEntry")
    public Map<String, Object> getDelayEntry(@RequestParam Integer userId, @RequestParam Integer companyId, @RequestParam String type, String mode, String nickName) {
        return entryService.findDelayEntry(userId, companyId, type, mode, nickName);
    }

    /**
     * 员工背调后正式入职
     * @param delayEntryBo
     * @return
     */
    @PostMapping(value = "join")
    public Map<String, Object> join(@RequestBody DelayEntryBo delayEntryBo) {
        try {
            entryService.join(delayEntryBo);
            return CommonUtils.setSuccessInfo();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
    }

    /**
     * 查询聊天背调的员工列表
     * @param userId
     * @param companyId
     * @param type 0:发起背调，1：配合背调
     * @return
     */
    @GetMapping(value = "getEmployeeCheckList")
    public Map<String, Object> getEmployeeCheckList(@RequestParam Integer userId, @RequestParam Integer companyId, @RequestParam String type) {
        return entryService.findEmployeeCheckList(userId, companyId, type);
    }

    /**
     * 查询已发送入职通知列表
     * @param userId
     * @param companyId
     * @return
     */
    @GetMapping(value = "getEntryNoticeList")
    public Map<String, Object> getEntryNoticeList(@RequestParam Integer userId, @RequestParam Integer companyId) {
        return entryService.findEntryNoticeList(userId, companyId);
    }

    /**
     * 根据ID查询入职通知详情
     * @param id
     * @return
     */
    @GetMapping(value = "getEntryNoticeDetail")
    public Map<String, Object> getEntryNoticeDetail(@RequestParam Integer id) {
        return entryService.findEntryNoticeDetail(id);
    }

    /**
     * 是否配置邮箱
     * @param userId
     * @param companyId
     * @return
     */
    @GetMapping(value = "hasEmail")
    public Map<String, Object> hasEmail(@RequestParam Integer userId, @RequestParam Integer companyId) {
        return entryService.hasEmail(userId, companyId);
    }

    /**
     * 保存邮箱地址和密码
     * @param addEmailBo
     * @return
     */
    @PostMapping(value = "saveEmail")
    public Map<String, Object> saveEmail(@Valid @RequestBody AddEmailBo addEmailBo) {
        try {
            entryService.saveEmail(addEmailBo);
            return CommonUtils.setSuccessInfo();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
    }

    /**
     * 发送入职通知
     * @param sendEntryNoticeBo
     * @return
     */
    @PostMapping(value = "sendEntryNotice")
    public Map<String, Object> sendEntryNotice(@Valid @RequestBody SendEntryNoticeBo sendEntryNoticeBo) {
        try {
            entryService.sendEntryNotice(sendEntryNoticeBo);
            return CommonUtils.setSuccessInfo();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
    }


}
