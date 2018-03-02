package com.training.core.service.impl;

import com.training.core.repo.OrgSportsMapper;
import com.training.core.repo.po.OrgSports;
import com.training.core.service.OrgSportsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgSportsServiceImpl implements OrgSportsService {

    @Resource
    private OrgSportsMapper orgSportsMapper;

    @Override
    public int addOrgSports(OrgSports orgSports) {
        return orgSportsMapper.insert(orgSports);
    }

    @Override
    public int addOrgSportsBatch(List<OrgSports> orgSportsList) {
        return orgSportsMapper.insertBatch(orgSportsList);
    }

    @Override
    public int saveOrgSports(OrgSports orgSports) {
        return orgSportsMapper.updateByPrimaryKey(orgSports);
    }

    @Override
    public List<OrgSports> queryOrgSportsList(int orgId) {
        return orgSportsMapper.queryAll(orgId);
    }

    @Override
    public OrgSports getOrgSports(Integer id) {
        return orgSportsMapper.selectByPrimaryKey(id);
    }
}


