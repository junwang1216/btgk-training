package com.training.core.service.impl;

import com.training.core.repo.OrgSportsSkillsMapper;
import com.training.core.repo.po.OrgSports;
import com.training.core.repo.po.OrgSportsSkills;
import com.training.core.service.OrgSportsSkillsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgSportsSkillsServiceImpl implements OrgSportsSkillsService {

    @Resource
    private OrgSportsSkillsMapper orgSportsSkillsMapper;

    @Override
    public int addOrgSportsSkillsBatch(List<OrgSportsSkills> orgSportsSkillsList) {
        return orgSportsSkillsMapper.insertBatch(orgSportsSkillsList);
    }

    @Override
    public int saveOrgSportsSkills(OrgSportsSkills orgSportsSkills) {
        return orgSportsSkillsMapper.updateByPrimaryKey(orgSportsSkills);
    }

    @Override
    public int deleteOrgSportsSkills(int skillId) {
        return orgSportsSkillsMapper.deleteByPrimaryKey(skillId);
    }

    @Override
    public List<OrgSportsSkills> queryOrgSportsSkillsList(int sportId) {
        return orgSportsSkillsMapper.queryAllBySportId(sportId);
    }

    @Override
    public int clearAllBySportId(int sportId) {
        return orgSportsSkillsMapper.clearAllBySportId(sportId);
    }

}


