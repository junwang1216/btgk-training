package com.training.core.service;

import com.training.core.repo.po.OrgFinanceDataIncome;

import java.util.List;

public interface OrgFinanceDataIncomeService {

    OrgFinanceDataIncome getOrgFinanceDataIncome(String businessNo);

    int addOrgFinanceDataIncome(OrgFinanceDataIncome orgFinanceDataIncome);

    int saveOrgFinanceDataIncome(OrgFinanceDataIncome orgFinanceDataIncome);

    List<OrgFinanceDataIncome> queryOrgFinanceDataIncomeList(Integer businessType, Integer venueId, Integer userId, String startTime, String endTime, Integer start, Integer pageSize);

    int queryOrgFinanceDataIncomeCount(Integer businessType, Integer venueId, Integer userId, String startTime, String endTime);

    int deleteOrgFinanceDataIncome(String businessNo);

}
