package com.tomasky.departure.vo;

import com.tomasky.departure.cons.Constants;

import java.util.List;

/**
 * Created by sam on 2019-08-08.09:22
 */
public class CompanyInfoVo {
    /** 公司ID */
    private Integer companyId;

    /** 公司名称 */
    private String companyName;

    /** log图片地址 */
    private String logUrl;

    /**
     * 是否是主企业，0不是，1是
     */
    private String isDefault;
    /** 当前用户在当前公司的功能权限列表*/
    private List<AuthorityVo> authorityList;

    /** 是否有预入职权限*/
    private boolean entryAble;

    /** 是否有关注权限*/
    private boolean followAble;

    /** 运营模式，0：线下模式，1：线上模式*/
    private String processMode;

    public boolean getEntryAble() {
        if(authorityList != null && authorityList.size() > 0) {
            for(AuthorityVo authorityVo : authorityList) {
                if(Constants.AUTHORITY_VERIFICATION_DEPARTURE.equals(authorityVo.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setEntryAble(boolean entryAble) {
        this.entryAble = entryAble;
    }

    public boolean getFollowAble() {
        if(authorityList != null && authorityList.size() > 0) {
            for(AuthorityVo authorityVo : authorityList) {
                if(Constants.AUTHORITY_CREATE_DEPARTURE.equals(authorityVo.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setFollowAble(boolean followAble) {
        this.followAble = followAble;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLogUrl() {
        return logUrl;
    }

    public void setLogUrl(String logUrl) {
        this.logUrl = logUrl;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public List<AuthorityVo> getAuthorityList() {
        return authorityList;
    }

    public void setAuthorityList(List<AuthorityVo> authorityList) {
        this.authorityList = authorityList;
    }

    public String getProcessMode() {
        return processMode;
    }

    public void setProcessMode(String processMode) {
        this.processMode = processMode;
    }
}
