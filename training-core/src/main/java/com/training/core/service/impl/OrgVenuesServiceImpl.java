package com.training.core.service.impl;

import com.training.core.repo.OrgVenuesMapper;
import com.training.core.repo.po.OrgVenues;
import com.training.core.service.OrgVenuesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgVenuesServiceImpl implements OrgVenuesService {

    @Resource
    private OrgVenuesMapper orgVenuesMapper;

    @Override
    public int addOrgVenues(OrgVenues orgVenues) {
        return orgVenuesMapper.insert(orgVenues);
    }

    @Override
    public int saveOrgVenues(OrgVenues orgVenues) {
        return orgVenuesMapper.updateByPrimaryKey(orgVenues);
    }

    @Override
    public int closeOrgVenues(OrgVenues orgVenues) {
        return orgVenuesMapper.setVenueStatus(orgVenues);
    }

    @Override
    public List<OrgVenues> queryOrgVenuesList(int orgId) {
        return orgVenuesMapper.queryAll(orgId, null);
    }

    @Override
    public OrgVenues getOrgVenues(Integer id) {
        return orgVenuesMapper.selectByPrimaryKey(id);
    }
}


