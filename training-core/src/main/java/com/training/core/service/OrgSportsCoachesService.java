package com.training.core.service;

import com.training.core.repo.po.OrgSportsCoaches;

import java.util.List;

public interface OrgSportsCoachesService {

    int addOrgSportsCoaches(OrgSportsCoaches orgSportsCoaches);

    int saveOrgSportsCoaches(OrgSportsCoaches orgSportsCoaches);

    List<OrgSportsCoaches> queryOrgSportsCoachesList(int sportId, int coachId);

    int addOrgSportsCoachesBatch(List<OrgSportsCoaches> orgSportsCoachesList);

    int clearAllByCoachId(int coachId);


}
