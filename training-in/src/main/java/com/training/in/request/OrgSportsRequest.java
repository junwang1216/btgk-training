package com.training.in.request;

import com.training.core.repo.po.OrgSports;

import java.util.List;

/**
 * Created by wangjun on 2017/12/28.
 */
public class OrgSportsRequest {

    private int orgId;
    private List<OrgSports> orgSportsList;

    public int getOrgId() {
        return orgId;
    }
    public void setOrgId(int sportId) {
        this.orgId = orgId;
    }

    public List<OrgSports> getOrgSportsList () {
        return orgSportsList;
    }
    public void setOrgSportsList(List<OrgSports> orgSportsList) {
        this.orgSportsList = orgSportsList;
    }

}
