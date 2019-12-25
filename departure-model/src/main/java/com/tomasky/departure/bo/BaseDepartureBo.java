package com.tomasky.departure.bo;

import com.tomasky.departure.model.DepartureInfo;

import java.util.List;

/**
 * Created by sam on 2019-08-07.15:08
 */
public class BaseDepartureBo extends BaseBo {
    /** 保存操作的类型*/
    private String saveType;
    /** 离职表单内容*/
    private DepartureInfo departureInfo;

    /** 审批人集合*/
    private List<AuditUserInfoBo> auditUserList;

    /** 抄送人集合*/
    private List<AuditUserInfoBo> copyUserList;

    public String getSaveType() {
        return saveType;
    }

    public void setSaveType(String saveType) {
        this.saveType = saveType;
    }

    public DepartureInfo getDepartureInfo() {
        return departureInfo;
    }

    public void setDepartureInfo(DepartureInfo departureInfo) {
        this.departureInfo = departureInfo;
    }

    public List<AuditUserInfoBo> getAuditUserList() {
        return auditUserList;
    }

    public void setAuditUserList(List<AuditUserInfoBo> auditUserList) {
        this.auditUserList = auditUserList;
    }

    public List<AuditUserInfoBo> getCopyUserList() {
        return copyUserList;
    }

    public void setCopyUserList(List<AuditUserInfoBo> copyUserList) {
        this.copyUserList = copyUserList;
    }
}
