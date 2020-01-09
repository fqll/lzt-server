package com.tomasky.departure.bo;

import javax.validation.constraints.NotNull;

/**
 * Created by sam on 2019-12-27.15:47
 */
public class BaseUserCompanyBo {
    /**
     * 创建人
     */
    @NotNull(message = "用户ID不能为空")
    private Integer userId;
    /**
     * 公司ID
     */
    @NotNull(message = "公司ID不能为空")
    private Integer companyId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
