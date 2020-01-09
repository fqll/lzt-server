package com.tomasky.departure.bo;

import java.util.List;

/**
 * Created by sam on 2019-08-28.14:46
 */
public class UserAuditBo extends BaseBo {
    /**
     * 公司ID
     */
    private Integer companyId;
    /**
     * 1通过，2拒绝
     */
    private String auditResult;
    /**
     * 员工
     */
    private Integer employeeId;
    /**
     * 角色ID
     */
    private List<Integer> authorityList;
    /**
     * 员工昵称
     */
    private String nickName;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public List<Integer> getAuthorityList() {
        return authorityList;
    }

    public void setAuthorityList(List<Integer> authorityList) {
        this.authorityList = authorityList;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
