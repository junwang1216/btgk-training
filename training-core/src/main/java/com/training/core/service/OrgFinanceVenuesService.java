package com.training.core.service;

import com.training.core.repo.po.OrgFinanceVenues;

import java.util.List;

public interface OrgFinanceVenuesService {

    int addOrgFinanceVenues(OrgFinanceVenues orgFinanceVenues);

    int saveOrgFinanceVenues(OrgFinanceVenues orgFinanceVenues);

    List<OrgFinanceVenues> queryOrgFinanceVenuesList();

    OrgFinanceVenues getOrgFinanceVenues(Integer venueId);

}
