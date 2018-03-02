package com.training.in.request;

import com.training.core.repo.po.OrgSportsSkills;

import java.util.List;

/**
 * Created by wangjun on 2017/12/28.
 */
public class OrgSportsSkillsRequest {

    private int sportId;
    private List<OrgSportsSkills> orgSportsSkillsList;

    public int getSportId() {
        return sportId;
    }
    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public List<OrgSportsSkills> getOrgSportsSkillsList() {
        return orgSportsSkillsList;
    }
    public void setOrgSportsSkillsList(List<OrgSportsSkills> orgSportsSkillsList) {
        this.orgSportsSkillsList = orgSportsSkillsList;
    }

}
