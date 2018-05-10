package com.training.core.service.impl;

import com.training.core.repo.OrgFinanceGoalsMapper;
import com.training.core.repo.po.OrgFinanceGoals;
import com.training.core.service.OrgFinanceGoalsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgFinanceGoalsServiceImpl implements OrgFinanceGoalsService {

    @Resource
    private OrgFinanceGoalsMapper orgFinanceGoalsMapper;

    @Override
    public int addOrgFinanceGoals(OrgFinanceGoals orgFinanceGoals) {
        return orgFinanceGoalsMapper.insert(orgFinanceGoals);
    }

    @Override
    public int addOrgFinanceGoalsBatch(List<OrgFinanceGoals> orgFinanceGoalsList) {
        return orgFinanceGoalsMapper.insertBatch(orgFinanceGoalsList);
    }

    @Override
    public int saveOrgFinanceGoals(OrgFinanceGoals orgFinanceGoals) {
        return orgFinanceGoalsMapper.updateByPrimaryKey(orgFinanceGoals);
    }

    @Override
    public List<OrgFinanceGoals> queryOrgFinanceGoalsList(Integer busType, Integer goalType, Integer venueId, Integer year, Integer userId) {
        busType = busType > 0 ? busType : null;
        goalType = goalType > 0 ? goalType : null;
        venueId = venueId > 0 ? venueId : null;
        year = year > 0 ? year : null;
        userId = userId > 0 ? userId : null;

        return orgFinanceGoalsMapper.queryAll(busType, goalType, venueId, year, userId);
    }

    @Override
    public OrgFinanceGoals getOrgFinanceGoals(Integer goalId) {
        return orgFinanceGoalsMapper.selectByPrimaryKey(goalId);
    }
}
