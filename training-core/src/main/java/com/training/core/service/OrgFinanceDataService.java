package com.training.core.service;

import com.training.core.repo.po.OrgFinanceData;

import java.util.List;

public interface OrgFinanceDataService {

    OrgFinanceData getOrgFinanceData(String businessNo);

    int addOrgFinanceData(OrgFinanceData orgFinanceData);

    int saveOrgFinanceData(OrgFinanceData orgFinanceData);

    List<OrgFinanceData> queryOrgFinanceDataList(Integer businessType, Integer venueId, Integer userId, String startTime, String endTime, Integer start, Integer pageSize);

    int queryOrgFinanceDataCount(Integer businessType, Integer venueId, Integer userId, String startTime, String endTime);

}
