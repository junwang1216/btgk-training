package com.training.uk.request;

import com.training.core.repo.po.OrgFinanceEnums;

import java.util.List;

/**
 * Created by wangjun on 2017/12/28.
 */
public class OrgFinanceParamSettingsRequest {

    private String paramType;
    private List<OrgFinanceEnums> orgFinanceEnumsList;

    public String getParamType() {
        return paramType;
    }
    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public List<OrgFinanceEnums> getOrgFinanceEnumsList() {
        return orgFinanceEnumsList;
    }
    public void setOrgFinanceEnumsList(List<OrgFinanceEnums> orgFinanceEnumsList) {
        this.orgFinanceEnumsList = orgFinanceEnumsList;
    }

}
