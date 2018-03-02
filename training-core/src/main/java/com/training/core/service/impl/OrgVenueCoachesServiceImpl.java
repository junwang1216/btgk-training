package com.training.core.service.impl;

import com.training.core.repo.OrgVenueCoachesMapper;
import com.training.core.repo.po.OrgVenueCoaches;
import com.training.core.service.OrgVenueCoachesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgVenueCoachesServiceImpl implements OrgVenueCoachesService {

    @Resource
    private OrgVenueCoachesMapper orgVenueCoachesMapper;

    @Override
    public int addOrgVenueCoaches(OrgVenueCoaches orgSportsCoaches) {
        return orgVenueCoachesMapper.insert(orgSportsCoaches);
    }

    @Override
    public int saveOrgVenueCoaches(OrgVenueCoaches orgSportsCoaches) {
        return orgVenueCoachesMapper.updateByPrimaryKey(orgSportsCoaches);
    }

    @Override
    public List<OrgVenueCoaches> queryOrgVenueCoachesList(int venueId, int coachId) {
        return orgVenueCoachesMapper.queryAll(venueId, coachId);
    }

    @Override
    public int addOrgVenueCoachesBatch(List<OrgVenueCoaches> orgVenueCoachesList) {
        return orgVenueCoachesMapper.insertBatch(orgVenueCoachesList);
    }

    @Override
    public int clearAllByCoachId(int coachId) {
        return orgVenueCoachesMapper.clearAllByCoachId(coachId);
    }

}


