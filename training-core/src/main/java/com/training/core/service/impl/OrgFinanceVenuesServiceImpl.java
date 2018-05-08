package com.training.core.service.impl;

import com.training.core.repo.OrgFinanceVenuesMapper;
import com.training.core.repo.po.OrgFinanceVenues;
import com.training.core.service.OrgFinanceVenuesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgFinanceVenuesServiceImpl implements OrgFinanceVenuesService {

    @Resource
    private OrgFinanceVenuesMapper orgFinanceVenuesMapper;

    @Override
    public int addOrgFinanceVenues(OrgFinanceVenues orgFinanceVenues) {
        return orgFinanceVenuesMapper.insert(orgFinanceVenues);
    }

    @Override
    public int saveOrgFinanceVenues(OrgFinanceVenues orgFinanceVenues) {
        return orgFinanceVenuesMapper.updateByPrimaryKey(orgFinanceVenues);
    }

    @Override
    public List<OrgFinanceVenues> queryOrgFinanceVenuesList() {
        return orgFinanceVenuesMapper.queryAll();
    }

    @Override
    public OrgFinanceVenues getOrgFinanceVenues(Integer venueId) {
        return orgFinanceVenuesMapper.selectByPrimaryKey(venueId);
    }
}
