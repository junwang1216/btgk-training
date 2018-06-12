package com.training.core.service;

import com.training.core.repo.po.OrgFinanceDataBusiness;

import java.util.List;

public interface OrgFinanceDataBusinessService {

    OrgFinanceDataBusiness getOrgFinanceDataBusiness(String businessNo);

    int addOrgFinanceDataBusiness(OrgFinanceDataBusiness orgFinanceDataBusiness);

    int saveOrgFinanceDataBusiness(OrgFinanceDataBusiness orgFinanceDataBusiness);

    List<OrgFinanceDataBusiness> queryOrgFinanceDataBusinessList(Integer businessType, Integer venueId, Integer userId,
                                                                 String startTime, String endTime, Integer start, Integer pageSize);

    int queryOrgFinanceDataBusinessCount(Integer businessType, Integer venueId, Integer userId, String startTime, String endTime);

}
