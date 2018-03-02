package com.training.core.service;

import com.training.core.repo.po.OrgSportsSkills;

import java.util.List;

public interface OrgSportsSkillsService {

    int addOrgSportsSkillsBatch(List<OrgSportsSkills> orgSportsSkillsList);

    int saveOrgSportsSkills(OrgSportsSkills orgSportsSkills);

    List<OrgSportsSkills> queryOrgSportsSkillsList(int sportId);

    int clearAllBySportId(int sportId);

}
