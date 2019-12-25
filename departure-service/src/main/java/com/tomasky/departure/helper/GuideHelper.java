package com.tomasky.departure.helper;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.tomasky.departure.common.utils.DateUtils;
import com.tomasky.departure.vo.AuditDepartureVo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用于用户引导
 * Created by sam on 2019-11-14.16:37
 */
@Component
public class GuideHelper {

    /**
     * 获取查询我的审批引导数据
     *
     * @return
     */
    public Map<String, Object> getAuditInfoGuideData() {
        // 我发起的
        List<AuditDepartureVo> createdDepartureVoList = new ArrayList<>();
        createdDepartureVoList.add(getCreatedDepartureVoGuideData());
        // 待我审批的
        List<AuditDepartureVo> auditDepartureVoList = new ArrayList<>();
        auditDepartureVoList.add(getAuditDepartureVoGuideData());

        Map<String, Object> result = Maps.newHashMap();
        result.put("auditInfo", auditDepartureVoList);
        result.put("auditedInfo", null);
        result.put("createdInfo", createdDepartureVoList);
        result.put("copyInfo", null);
        return result;
    }

    /**
     * 待审批
     * @return
     */
    private AuditDepartureVo getAuditDepartureVoGuideData() {
        String jsonData = "{\"auditStatus\":\"1\",\"auditStatusDesc\":\"待审批\",\"chatAble\":false,\"departureDate\":\"2019-11-13\",\"departureId\":-1,\"departureReason\":\"1\",\"departureReasonDesc\":\"个人原因\",\"employeeName\":\"阿离\",\"followStatusDesc\":\"\",\"readStatusDesc\":\"\",\"submitDate\":\"2019-11-13\"}";
        return JSON.parseObject(jsonData, AuditDepartureVo.class);
    }

    /**
     * 已办结
     * @return
     */
    private AuditDepartureVo getCreatedDepartureVoGuideData() {
        String jsonData = "{\"auditStatus\":\"3\",\"auditStatusDesc\":\"已办结\",\"chatAble\":false,\"departureDate\":\"2019-11-13\",\"departureId\":-1,\"departureReason\":\"1\",\"departureReasonDesc\":\"个人原因\",\"employeeName\":\"阿离\",\"followStatusDesc\":\"\",\"readStatusDesc\":\"\",\"submitDate\":\"2019-11-13\"}";
        return JSON.parseObject(jsonData, AuditDepartureVo.class);
    }

    /**
     * 拼接离职引导的离职表单数据
     *
     * @return
     */
    public Map<String, Object> getDepartureInfoDetailGuideData() {
        String jsonData = "{\"cancelable\":false,\"departureAuditList\":[{\"auditOrder\":0,\"auditResultDesc\":\"\",\"auditRoleType\":\"2\",\"auditRoleTypeDesc\":\"发起人\",\"auditStage\":\"发起申请\",\"nickName\":\"我\",\"operateTime\":\"11-14 15:30\",\"operateTypeDesc\":\"\",\"userId\":74},{\"userId\":56,\"auditOrder\":1,\"auditResult\":\"1\",\"auditResultDesc\":\"\",\"auditOpinions\":\"\",\"nickName\":\"我\",\"operateType\":\"1\",\"operateTypeDesc\":\"审批中\",\"operateTime\":null,\"auditRoleType\":\"1\",\"auditRoleTypeDesc\":\"审批人\",\"auditStage\":\"审批中\"}],\"followStatus\":false,\"chatAble\":false,\"departureCopyList\":[],\"modifiable\":false,\"departureInfo\":{\"auditStatus\":\"3\",\"auditStatusDesc\":\"已办结\",\"code\":\"pT229f\",\"companyId\":81,\"createdId\":74,\"createdTime\":null,\"delayEntryDate\":null,\"deleted\":\"0\",\"department\":\"运营部\",\"departureDate\":\"2019-11-13\",\"departureReason\":\"1\",\"departureReasonDesc\":\"个人原因\",\"employeeName\":\"阿离\",\"employeePost\":\"客服\",\"entryDate\":\"2018-05-14\",\"gender\":\"女\",\"id\":-1,\"idCardNo\":\"510113199209130038\",\"isCheck\":\"0\",\"isValid\":\"1\",\"lastModifyId\":73,\"lastModifyTime\":1573717200094,\"nextCompanyId\":80,\"officialDepartureReason\":\"\",\"personalDepartureReason\":\"0\",\"personalDepartureReasonDesc\":\"行业前景\",\"remark\":\"\",\"submitDate\":\"2019-11-13\",\"versionNum\":2}}";
        return JSON.parseObject(jsonData);
    }

    /**
     * 引导待入职离职表单数据
     * @return
     */
    public Map<String, Object> getDelayEntryDepartureInfo() {
        String jsonData = "{\"cancelable\":false,\"departureAuditList\":[{\"auditOrder\":0,\"auditResultDesc\":\"\",\"auditRoleType\":\"2\",\"auditRoleTypeDesc\":\"发起人\",\"auditStage\":\"发起申请\",\"nickName\":\"老徐\",\"operateTime\":\"11-14 15:30\",\"operateTypeDesc\":\"\",\"userId\":74}],\"followStatus\":false,\"chatAble\":false,\"departureCopyList\":[],\"modifiable\":false,\"departureInfo\":{\"auditStatus\":\"3\",\"auditStatusDesc\":\"已办结\",\"code\":\"pT229f\",\"companyId\":81,\"createdId\":74,\"createdTime\":1573716651174,\"delayEntryDate\":1573717200094,\"deleted\":\"0\",\"department\":\"研发部\",\"departureDate\":\"2019-11-14\",\"departureReason\":\"0\",\"departureReasonDesc\":\"公司原因\",\"employeeName\":\"阿通\",\"employeePost\":\"java研发\",\"entryDate\":\"2017-02-14\",\"gender\":\"男\",\"id\":-1,\"idCardNo\":\"5101073199201230231\",\"isCheck\":\"0\",\"isValid\":\"1\",\"lastModifyId\":73,\"lastModifyTime\":null,\"nextCompanyId\":80,\"officialDepartureReason\":\"\",\"personalDepartureReason\":\"1\",\"personalDepartureReasonDesc\":\"缺乏团队精神\",\"remark\":\"\",\"submitDate\":\"2019-11-13\",\"versionNum\":2}}";
        return JSON.parseObject(jsonData);
    }

    /**
     * 查询预入职列表
     * @return
     */
    public Map<String, Object> getDelayEntryListData() {
        String jsonData = "{\"data\":[{\"userId\":-1,\"employeeName\":\"阿通\",\"delayEntryDate\":\"" + DateUtils.format(new Date()) + "\",\"chatAble\":true}],\"message\":\"success\",\"status\":200}";
        return JSON.parseObject(jsonData);
    }

    /**
     * 获取引导的聊天列表
     * @return
     */
    public Map<String, Object> getChatListData() {
        String jsonData = "{\"chatTitle\":\"成都番茄来了科技有限公司\",\"otherInfo\":[{\"id\":59,\"openId\":\"oQ4YF5l9Dnn8exGTrxvraRstJsMo\",\"lastCompanyId\":null,\"employeeStatus\":null,\"entryCompanyId\":null,\"entryStatus\":null,\"portraitUrl\":\"\"},{\"id\":58,\"openId\":\"oQ4YF5k8MjVpru-aRpWacNpbsAkw\",\"lastCompanyId\":null,\"employeeStatus\":null,\"entryCompanyId\":null,\"entryStatus\":null,\"portraitUrl\":\"https://wx.qlogo.cn/mmopen/vi_32/pTD4R57a71HYOSPCposzd2wf2HPokaksoQ02PaeB4R7MXNAxwPThQLWiadhYMYFEs9aicvGqV0nK3Zb8RdznLcFw/132\"}],\"selfInfo\":{\"id\":56,\"openId\":\"oQ4YF5gQS9Hw39RMjsuedRfWL518\",\"lastCompanyId\":null,\"employeeStatus\":null,\"entryCompanyId\":null,\"entryStatus\":null,\"portraitUrl\":\"https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoVU91BNnic8csOmBDpo7SaDRyyeX5RneLSm4QyUGrN7icsj1nHNY7Vwxzhialjx4w6vDWd3KsrDcBNg/132\"},\"chatList\":[{\"id\":1,\"departureId\":116,\"userId\":56,\"chatContent\":\"请问阿通是在贵司java研发岗位工作吗？\",\"chatTime\":\"2019-10-28 15:53:31\"},{\"id\":2,\"departureId\":116,\"userId\":58,\"chatContent\":\"是的\",\"chatTime\":\"2019-10-28 15:53:35\"},{\"id\":3,\"departureId\":116,\"userId\":59,\"chatContent\":\"是的，在我们部门\",\"chatTime\":\"2019-10-28 15:53:38\"},{\"id\":4,\"departureId\":116,\"userId\":56,\"chatContent\":\"我看到离职是公司原因，请问具体是什么原因离职的呢？\",\"chatTime\":\"2019-10-28 16:02:58\"},{\"id\":5,\"departureId\":116,\"userId\":59,\"chatContent\":\"是的，因为缺乏团队精神，不能和公司还有团队共同进步\",\"chatTime\":\"2019-10-28 15:53:38\"}]}";
        return JSON.parseObject(jsonData);
    }

    /**
     * 获取当前登录用户信息
     * @return
     */
    public Map<String, Object> getCurrentUserInfoData() {
        String jsonData = "{\"userInfo\":{\"createdId\":-1,\"createdTime\":null,\"deleted\":\"0\",\"id\":74,\"isValid\":\"1\",\"openId\":\"oQ4YF5l9Dnn8exGTrxvraRstJsMo\",\"portraitUrl\":\"\",\"versionNum\":1},\"companyInfo\":{\"authorityList\":[{\"authorityCode\":\"create_departure\",\"authorityName\":\"开具离职证明\",\"id\":1},{\"authorityCode\":\"my_approval\",\"authorityName\":\"我的审批\",\"id\":2},{\"authorityCode\":\"employee_list\",\"authorityName\":\"离职员工库\",\"id\":4},{\"authorityCode\":\"analysis_departure\",\"authorityName\":\"离职分析\",\"id\":5},{\"authorityCode\":\"employee_authority\",\"authorityName\":\"员工权限\",\"id\":6},{\"authorityCode\":\"created_info\",\"authorityName\":\"企业专属编码\",\"id\":7},{\"authorityCode\":\"verification_departure\",\"authorityName\":\"入职背调\",\"id\":3}],\"companyId\":-1,\"companyName\":\"成都番茄来了科技有限公司\",\"entryAble\":true,\"followAble\":true,\"isDefault\":\"0\",\"logUrl\":\"\"}}";
        return JSON.parseObject(jsonData);
    }

    /**
     * 获取引导的审批人列表
     * @return
     */
    public Map<String, Object> getAuditUserListData() {
        String jsonData = "{\"data\":[{\"userId\":57,\"portraitUrl\":\"\",\"applyTime\":\"09-17\",\"nickName\":\"我\"}],\"message\":\"success\",\"status\":200}";
        return JSON.parseObject(jsonData);
    }
}
