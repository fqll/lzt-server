package com.tomasky.departure.bo;

/**
 * Created by sam on 2019-10-16.17:01
 */
public class IncumbentsCountBo extends BaseBo {
    /**
     * 公司ID
     */
    private Integer companyId;

    /**
     * 在职人数
     */
    private Integer incumbentsCount;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getIncumbentsCount() {
        return incumbentsCount;
    }

    public void setIncumbentsCount(Integer incumbentsCount) {
        this.incumbentsCount = incumbentsCount;
    }
}
