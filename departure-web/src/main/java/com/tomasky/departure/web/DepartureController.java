package com.tomasky.departure.web;

import com.tomasky.departure.bo.AuditBo;
import com.tomasky.departure.bo.BaseDepartureBo;
import com.tomasky.departure.bo.CancelDepartureBo;
import com.tomasky.departure.bo.DepartureInfoBo;
import com.tomasky.departure.bo.FollowBo;
import com.tomasky.departure.bo.RemoveDepartureInfoBo;
import com.tomasky.departure.bo.ShareDepartureInfo;
import com.tomasky.departure.common.utils.CommonUtils;
import com.tomasky.departure.service.DepartureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by sam on 2019-08-07.14:48
 */
@RestController
@RequestMapping("/departure/")
public class DepartureController extends BaseController {

    @Autowired
    private DepartureService departureService;

    /**
     * 创建离职表单
     * @param baseDepartureBo
     * @return
     */
    @RequestMapping(value = "newDeparture", method = RequestMethod.POST)
    public Map<String, Object> newDeparture(@RequestBody BaseDepartureBo baseDepartureBo) {
        try {
            return CommonUtils.setSuccessInfo(departureService.createDeparture(baseDepartureBo));
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
    }

    /**
     * 根据表单ID查询待填写的表单信息
     * @param id
     * @return
     */
//    @RequestMapping(value = "getFillDepartureInfo", method = RequestMethod.GET)
    public Map<String, Object> getFillDepartureInfo(@RequestParam Integer id, @RequestParam String type, String openId) {
        return departureService.findFillDepartureInfo(id, type, openId);
    }

    /***
     * 员工填写离职表单
     * @param departureInfoBo
     * @return
     */
//    @RequestMapping(value = "submitDeparture", method = RequestMethod.POST)
    public Map<String, Object> submitDeparture(@RequestBody DepartureInfoBo departureInfoBo) {
        try {
            departureService.submitDeparture(departureInfoBo);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
        return CommonUtils.setSuccessInfo();
    }

    /**
     * 审批离职表单
     * @param auditBo
     * @return
     */
    @RequestMapping(value = "auditDeparture", method = RequestMethod.POST)
    public Map<String, Object> auditDeparture(@RequestBody AuditBo auditBo) {
        try {
            departureService.auditDeparture(auditBo);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
        return CommonUtils.setSuccessInfo();
    }

    /**
     * 关注员工，0.2.3版本暂时屏蔽关注功能
     * @param followBo
     * @return
     */
//    @RequestMapping(value = "follow", method = RequestMethod.POST)
    public Map<String, Object> follow(@RequestBody FollowBo followBo) {
        try {
            departureService.followDeparture(followBo);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
        return CommonUtils.setSuccessInfo();
    }

    /**
     * 根据用户ID查询我的离职证明列表
     * @param userId
     * @return
     */
    @GetMapping(value = "getDepartureList")
    public Map<String, Object> getDepartureList(@RequestParam Integer userId) {
        return departureService.findDepartureList(userId);
    }

    /**
     * 根据离职表单ID查询离职表单详情
     * @param id
     * @param userId
     * @return
     */
    @GetMapping(value = "getDepartureInfo")
    public Map<String, Object> getDepartureInfo(@RequestParam Integer id, @RequestParam Integer userId, String mode) {
        return departureService.findDepartureInfo(id, userId, mode);
    }

    /**
     * 撤回离职表单
     * @param cancelDepartureBo
     * @return
     */
    @PostMapping(value = "cancelDepartureInfo")
    public Map<String, Object> cancelDepartureInfo(@RequestBody CancelDepartureBo cancelDepartureBo) {
        try {
            departureService.cancelDepartureInfo(cancelDepartureBo);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
        return CommonUtils.setSuccessInfo();
    }

    /**
     * 编辑离职证明
     * @param baseDepartureBo
     * @return
     */
//    @PostMapping(value = "editDeparture")
    public Map<String, Object> editDeparture(@RequestBody BaseDepartureBo baseDepartureBo) {
        try {
            departureService.editDeparture(baseDepartureBo);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
        return CommonUtils.setSuccessInfo();
    }

    /**
     * 分享离职证明
     * @param shareDepartureInfo
     * @return
     */
    @PostMapping(value = "shareDepartureInfo")
    public Map<String, Object> shareDepartureInfo(@RequestBody ShareDepartureInfo shareDepartureInfo) {
        try {
            departureService.shareDepartureInfo(shareDepartureInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
        return CommonUtils.setSuccessInfo();
    }

    /**
     * 查询离职员工库
     * @param companyId
     * @param nickName
     * @return
     */
    @GetMapping(value = "getQuitEmployeeList")
    public Map<String, Object> getQuitEmployeeList(@RequestParam Integer companyId, String nickName) {
        return departureService.findQuitEmployeeList(companyId, nickName);
    }

    /**
     * 查询公司离职率
     * @param companyId
     * @return
     */
    @GetMapping(value = "getDepartureRate")
    public Map<String, Object> getDepartureRate(@RequestParam Integer companyId) {
        return departureService.findDepartureRate(companyId);
    }

    /**
     * 根据公司ID查询离职原因分析
     * @param companyId
     * @return
     */
    @GetMapping(value = "getDepartureReasonInfo")
    public Map<String, Object> getDepartureReasonInfo(@RequestParam Integer companyId) {
        return departureService.findDepartureReasonInfo(companyId);
    }

    /**
     * 查询草稿列表
     * @param userId
     * @param companyId
     * @return
     */
    @GetMapping(value = "getDraftList")
    public Map<String, Object> getDraftList(@RequestParam Integer userId, @RequestParam Integer companyId) {
        return departureService.findDraftList(userId, companyId);
    }

    /**
     * 根据表单ID，用户ID和公司ID删除离职表单
     * @param removeDepartureInfoBo
     * @return
     */
    @PostMapping(value = "deleteDeparture")
    public Map<String, Object> deleteDeparture(@RequestBody RemoveDepartureInfoBo removeDepartureInfoBo) {
        try {
            departureService.removeDeparture(removeDepartureInfoBo);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
        return CommonUtils.setSuccessInfo();
    }

}
