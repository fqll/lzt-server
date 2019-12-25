package com.tomasky.departure.bo;

/**
 * Created by sam on 2019-08-16.15:13
 */
public class DelayEntryBo extends BaseBo {
    /** 离职表单ID */
    private Integer departureId;
    /** 公司ID*/
    private Integer companyId;

    public Integer getDepartureId() {
        return departureId;
    }

    public void setDepartureId(Integer departureId) {
        this.departureId = departureId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

}
