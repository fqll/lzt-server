package com.tomasky.departure.model;

import com.tomasky.departure.BaseModel;
import com.tomasky.departure.enums.DepartureAuditStatusEnum;
import com.tomasky.departure.enums.DepartureReasonEnum;
import com.tomasky.departure.enums.OfficialDepartureReasonEnum;
import com.tomasky.departure.enums.PersonalDepartureReasonEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by sam on 2019-08-05.11:16
 */

public class DepartureInfo extends BaseModel implements Serializable {
    /**
     * 公司ID
     */
    private Integer companyId;

    /**
     * 雇员ID
     */
    private Integer employeeId;

    /**
     * 当前审批状态，0：创建，1：待审批，2：审批通过，3：审批退回，4：已办结
     */
    private String auditStatus;

    private String auditStatusDesc;

    /**
     * 当前审批人ID
     */
    private Integer auditUserId;

    /**
     * 离职原因，0：公司原因，1：个人原因
     */
    private String departureReason;

    private String departureReasonDesc;

    /**
     * 公司离职原因，0：能力欠缺，1：缺乏团队精神，2：价值观不符，3：业务调整
     */
    private String officialDepartureReason;

    private String officialDepartureReasonDesc;

    /**
     * 个人离职原因，0：行业前景，1：职业发展，2：薪酬福利，3：企业文化，4：工作环境，5：工作强度，6：同事关系，7：家庭因素，8：其他
     */
    private String personalDepartureReason;

    private String personalDepartureReasonDesc;

    /**
     * 姓名
     */
    private String employeeName;

    /**
     * 性别
     */
    private String gender;

    /**
     * 身份证号码
     */
    private String idCardNo;

    /**
     * 所属部门
     */
    private String department;

    /**
     * 岗位
     */
    private String employeePost;

    /**
     * 入职时间
     */
    private String entryDate;

    /**
     * 离职时间
     */
    private String departureDate;

    /**
     * 公司评价
     */
    private String officialEvaluate;

    /**
     * 个人评价
     */
    private String personalEvaluate;

    /**
     * 提交离职时间
     */
    private String submitDate;

    /**
     * 是否核验，0：未核验，1：已核验
     */
    private String isCheck;

    /**
     * 核验码
     */
    private String code;

    /**
     * 离职备注
     */
    private String remark;

    /**
     * 下家公司ID
     */
    private Integer nextCompanyId;
    /**
     * 预入职时间
     */
    private Date delayEntryDate;
    /**
     * 办理预入职或者办理入职的人员ID，决定是否可以聊天
     */
    private Integer followUserId;

    private String followStatus;

    /**
     * 背调阶段，0：未开始背调，1：开始聊天背调
     */
    private String checkStage;


    private static final long serialVersionUID = 1L;

    public String getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    public String getDepartureReason() {
        return departureReason;
    }

    public void setDepartureReason(String departureReason) {
        this.departureReason = departureReason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DepartureInfo() {
    }

    public String getDepartureReasonDesc() {
        return DepartureReasonEnum.getNameFromValue(this.departureReason);
    }

    public void setDepartureReasonDesc(String departureReasonDesc) {
        this.departureReasonDesc = departureReasonDesc;
    }

    public String getAuditStatusDesc() {
        return DepartureAuditStatusEnum.getNameFromValue(this.auditStatus);
    }

    public void setAuditStatusDesc(String auditStatusDesc) {
        this.auditStatusDesc = auditStatusDesc;
    }

    public String getOfficialDepartureReasonDesc() {
        return OfficialDepartureReasonEnum.getNameFromValue(this.officialDepartureReason);
    }

    public void setOfficialDepartureReasonDesc(String officialDepartureReasonDesc) {
        this.officialDepartureReasonDesc = officialDepartureReasonDesc;
    }

    public String getPersonalDepartureReasonDesc() {
        return PersonalDepartureReasonEnum.getNameFromValue(this.personalDepartureReason);
    }

    public void setPersonalDepartureReasonDesc(String personalDepartureReasonDesc) {
        this.personalDepartureReasonDesc = personalDepartureReasonDesc;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(Integer auditUserId) {
        this.auditUserId = auditUserId;
    }

    public String getOfficialDepartureReason() {
        return officialDepartureReason;
    }

    public void setOfficialDepartureReason(String officialDepartureReason) {
        this.officialDepartureReason = officialDepartureReason;
    }

    public String getPersonalDepartureReason() {
        return personalDepartureReason;
    }

    public void setPersonalDepartureReason(String personalDepartureReason) {
        this.personalDepartureReason = personalDepartureReason;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmployeePost() {
        return employeePost;
    }

    public void setEmployeePost(String employeePost) {
        this.employeePost = employeePost;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getOfficialEvaluate() {
        return officialEvaluate;
    }

    public void setOfficialEvaluate(String officialEvaluate) {
        this.officialEvaluate = officialEvaluate;
    }

    public String getPersonalEvaluate() {
        return personalEvaluate;
    }

    public void setPersonalEvaluate(String personalEvaluate) {
        this.personalEvaluate = personalEvaluate;
    }

    public Integer getNextCompanyId() {
        return nextCompanyId;
    }

    public void setNextCompanyId(Integer nextCompanyId) {
        this.nextCompanyId = nextCompanyId;
    }

    public Date getDelayEntryDate() {
        return delayEntryDate;
    }

    public void setDelayEntryDate(Date delayEntryDate) {
        this.delayEntryDate = delayEntryDate;
    }

    public Integer getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId(Integer followUserId) {
        this.followUserId = followUserId;
    }

    public String getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(String followStatus) {
        this.followStatus = followStatus;
    }

    public String getCheckStage() {
        return checkStage;
    }

    public void setCheckStage(String checkStage) {
        this.checkStage = checkStage;
    }
}