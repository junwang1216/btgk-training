package com.training.core.service;

import com.training.core.repo.po.OrgVenueCoaches;

import java.util.List;

public interface OrgVenueCoachesService {

    int addOrgVenueCoaches(OrgVenueCoaches orgSportsCoaches);

    int saveOrgVenueCoaches(OrgVenueCoaches orgSportsCoaches);

    List<OrgVenueCoaches> queryOrgVenueCoachesList(int venueId, int coachId);

    int addOrgVenueCoachesBatch(List<OrgVenueCoaches> orgVenueCoachesList);

    int clearAllByCoachId(int coachId);

}
