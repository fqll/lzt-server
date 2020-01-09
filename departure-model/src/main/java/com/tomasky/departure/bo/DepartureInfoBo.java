package com.tomasky.departure.bo;

/**
 * Created by sam on 2019-08-09.11:13
 */
public class DepartureInfoBo extends BaseBo {
    /**
     * 离职表单的ID
     */
    private Integer id;
    /**
     * 性别，0：女，1：男
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
     * 个人离职原因，0：行业前景，1：职业发展，2：薪酬福利，3：企业文化，4：工作环境，5：工作强度，6：同事关系，7：家庭因素，8：其他
     */
    private String personalDepartureReason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPersonalDepartureReason() {
        return personalDepartureReason;
    }

    public void setPersonalDepartureReason(String personalDepartureReason) {
        this.personalDepartureReason = personalDepartureReason;
    }
}
