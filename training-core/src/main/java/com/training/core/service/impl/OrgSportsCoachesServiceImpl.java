package com.training.core.service.impl;

import com.training.core.repo.OrgSportsCoachesMapper;
import com.training.core.repo.po.OrgSportsCoaches;
import com.training.core.service.OrgSportsCoachesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgSportsCoachesServiceImpl implements OrgSportsCoachesService {

    @Resource
    private OrgSportsCoachesMapper orgSportsCoachesMapper;

    @Override
    public int addOrgSportsCoaches(OrgSportsCoaches orgSportsCoaches) {
        return orgSportsCoachesMapper.insert(orgSportsCoaches);
    }

    @Override
    public int saveOrgSportsCoaches(OrgSportsCoaches orgSportsCoaches) {
        return orgSportsCoachesMapper.updateByPrimaryKey(orgSportsCoaches);
    }

    @Override
    public List<OrgSportsCoaches> queryOrgSportsCoachesList(int sportId, int coachId) {
        return orgSportsCoachesMapper.queryAll(sportId, coachId);
    }

    @Override
    public int addOrgSportsCoachesBatch(List<OrgSportsCoaches> orgSportsCoachesList) {
        return orgSportsCoachesMapper.insertBatch(orgSportsCoachesList);
    }

    @Override
    public int clearAllByCoachId(int coachId) {
        return orgSportsCoachesMapper.clearAllByCoachId(coachId);
    }

}


