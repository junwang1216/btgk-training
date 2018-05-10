package com.training.core.service;

import com.training.core.repo.po.OrgFinanceGoals;

import java.util.List;

public interface OrgFinanceGoalsService {

    int addOrgFinanceGoals(OrgFinanceGoals orgFinanceGoals);

    int addOrgFinanceGoalsBatch(List<OrgFinanceGoals> orgFinanceGoalsList);

    int saveOrgFinanceGoals(OrgFinanceGoals orgFinanceGoals);

    List<OrgFinanceGoals> queryOrgFinanceGoalsList(Integer busType, Integer goalType, Integer venueId, Integer year, Integer userId);

    OrgFinanceGoals getOrgFinanceGoals(Integer goalId);

}
