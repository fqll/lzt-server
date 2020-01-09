package com.tomasky.departure.service;

import com.tomasky.departure.bo.AuditBo;
import com.tomasky.departure.bo.BaseDepartureBo;
import com.tomasky.departure.bo.CancelDepartureBo;
import com.tomasky.departure.bo.DepartureInfoBo;
import com.tomasky.departure.bo.FollowBo;
import com.tomasky.departure.bo.RemoveDepartureInfoBo;
import com.tomasky.departure.bo.ShareDepartureInfo;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * Created by sam on 2019-08-07.14:54
 */
public interface DepartureService {
    /**
     * 创建离职表单
     *
     * @param baseDepartureBo
     */
    Integer createDeparture(BaseDepartureBo baseDepartureBo);

    /**
     * 根据表单ID查询待填写的表单信息
     *
     * @param id
     * @return
     */
    Map<String, Object> findFillDepartureInfo(Integer id, String type, String openId);

    /**
     * 员工填写离职表单
     *
     * @param departureInfoBo
     */
    void submitDeparture(DepartureInfoBo departureInfoBo);

    /**
     * 审批离职表单
     *
     * @param auditBo
     */
    void auditDeparture(AuditBo auditBo);

    /**
     * H5页面查看员工离职表单详情
     *
     * @param id
     * @return
     */
    Map<String, Object> findWebDepartureInfo(Integer id);

    /**
     * 关注员工
     *
     * @param followBo
     */
    void followDeparture(FollowBo followBo);

    /**
     * 根据离职表单状态判断跳转页面地址
     *
     * @param departureId
     * @return
     */
    String findForwardPath(Integer departureId);

    /**
     * 根据离职表单ID生成小程序二维码图片的二进制流
     *
     * @param departureId
     * @return
     */
    ResponseEntity<byte[]> getDeparturegetWxacode(Integer departureId);

    /**
     * 根据用户ID查询我的离职证明列表
     *
     * @param userId
     * @return
     */
    Map<String, Object> findDepartureList(Integer userId);

    /**
     * 根据离职表单ID查询离职表单详情
     *
     * @param id
     * @param userId
     * @return
     */
    Map<String, Object> findDepartureInfo(Integer id, Integer userId, String mode);

    /**
     * 撤回离职表单
     *
     * @param cancelDepartureBo
     * @return
     */
    void cancelDepartureInfo(CancelDepartureBo cancelDepartureBo);

    /**
     * 编辑离职证明
     *
     * @param baseDepartureBo
     */
    void editDeparture(BaseDepartureBo baseDepartureBo);

    /**
     * 分享离职证明
     *
     * @param shareDepartureInfo
     */
    void shareDepartureInfo(ShareDepartureInfo shareDepartureInfo);

    /**
     * 查询离职员工库
     *
     * @param companyId
     * @return
     */
    Map<String, Object> findQuitEmployeeList(Integer companyId, String nickName);

    /**
     * 查询公司离职率
     *
     * @param companyId
     * @return
     */
    Map<String, Object> findDepartureRate(Integer companyId);

    /**
     * 根据公司ID查询离职原因分析
     *
     * @param companyId
     * @return
     */
    Map<String, Object> findDepartureReasonInfo(Integer companyId);

    /**
     * 查询草稿列表
     *
     * @param userId
     * @param companyId
     * @return
     */
    Map<String, Object> findDraftList(Integer userId, Integer companyId);

    /**
     * 根据表单ID，用户ID和公司ID删除离职表单
     *
     * @param removeDepartureInfoBo
     */
    void removeDeparture(RemoveDepartureInfoBo removeDepartureInfoBo);
}
