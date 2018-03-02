package com.training.core.service;

import com.training.core.repo.po.OrgVenues;

import java.util.List;

public interface OrgVenuesService {

    int addOrgVenues(OrgVenues orgVenues);

    int saveOrgVenues(OrgVenues orgVenues);

    int closeOrgVenues(OrgVenues orgVenues);

    List<OrgVenues> queryOrgVenuesList(int orgId);

    OrgVenues getOrgVenues(Integer id);

}
