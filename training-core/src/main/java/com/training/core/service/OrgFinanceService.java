package com.training.core.service;

import com.training.core.repo.po.OrgFinance;

import java.util.List;

public interface OrgFinanceService {

    OrgFinance getOrgFinance(String businessNo);

    int addOrgFinance(OrgFinance orgFinance);

    int saveOrgFinance(OrgFinance orgFinance);

    List<OrgFinance> queryOrgFinanceList(String startTime, String endTime, Integer start, Integer pageSize);

    int queryOrgFinanceCount(String startTime, String endTime);

}
