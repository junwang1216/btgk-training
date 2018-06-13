package com.training.core.service;

import com.training.core.repo.po.OrgFinanceDataTimes;

import java.util.List;

public interface OrgFinanceDataTimesService {

    OrgFinanceDataTimes getOrgFinanceDataTimes(String businessNo);

    int addOrgFinanceDataTimes(OrgFinanceDataTimes orgFinanceDataTimes);

    int saveOrgFinanceDataTimes(OrgFinanceDataTimes orgFinanceDataTimes);

    List<OrgFinanceDataTimes> queryOrgFinanceDataTimesList(Integer businessType, Integer venueId, Integer userId, String startTime, String endTime, Integer start, Integer pageSize);

    int queryOrgFinanceDataTimesCount(Integer businessType, Integer venueId, Integer userId, String startTime, String endTime);

    int deleteOrgFinanceDataTimes(String businessNo);

}
