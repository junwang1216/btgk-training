package com.training.core.service;

import com.training.core.repo.po.OrgFinanceDataFlow;

import java.util.List;

public interface OrgFinanceDataFlowService {

    OrgFinanceDataFlow getOrgFinanceDataFlow(String businessNo);

    int addOrgFinanceDataFlow(OrgFinanceDataFlow orgFinanceDataFlow);

    int saveOrgFinanceDataFlow(OrgFinanceDataFlow orgFinanceDataFlow);

    List<OrgFinanceDataFlow> queryOrgFinanceDataFlowList(Integer businessType, Integer venueId, Integer userId, String startTime, String endTime, Integer start, Integer pageSize);

    int queryOrgFinanceDataFlowCount(Integer businessType, Integer venueId, Integer userId, String startTime, String endTime);

}
