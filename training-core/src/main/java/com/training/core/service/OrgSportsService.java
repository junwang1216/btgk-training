package com.training.core.service;

import com.training.core.repo.po.OrgSports;

import java.util.List;

public interface OrgSportsService {

    int addOrgSports(OrgSports orgSports);

    int saveOrgSports(OrgSports orgSports);

    int addOrgSportsBatch(List<OrgSports> orgSportsList);

    List<OrgSports> queryOrgSportsList(int orgId);

    OrgSports getOrgSports(Integer id);

}
