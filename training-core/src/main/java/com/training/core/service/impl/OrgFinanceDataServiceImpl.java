package com.training.core.service.impl;

import com.training.core.repo.OrgFinanceDataMapper;
import com.training.core.repo.po.OrgFinanceData;
import com.training.core.service.OrgFinanceDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgFinanceDataServiceImpl implements OrgFinanceDataService {

    @Resource
    private OrgFinanceDataMapper orgFinanceDataMapper;

    @Override
    public OrgFinanceData getOrgFinanceData(String businessNo) {
        return orgFinanceDataMapper.selectByPrimaryKey(businessNo);
    }

    @Override
    public int addOrgFinanceData(OrgFinanceData orgFinanceData) {
        return orgFinanceDataMapper.insert(orgFinanceData);
    }

    @Override
    public int saveOrgFinanceData(OrgFinanceData orgFinanceData) {
        return orgFinanceDataMapper.updateByPrimaryKey(orgFinanceData);
    }

    @Override
    public List<OrgFinanceData> queryOrgFinanceDataList(Integer businessType, Integer venueId, Integer userId,
                                                        String startTime, String endTime, Integer start, Integer pageSize) {
        businessType = businessType > 0 ? businessType : null;
        venueId = venueId > 0 ? venueId : null;
        userId = userId > 0 ? userId : null;

        return orgFinanceDataMapper.queryAll(businessType, venueId, userId, startTime, endTime, start, pageSize);
    }

    @Override
    public int queryOrgFinanceDataCount(Integer businessType, Integer venueId, Integer userId, String startTime, String endTime) {
        businessType = businessType > 0 ? businessType : null;
        venueId = venueId > 0 ? venueId : null;
        userId = userId > 0 ? userId : null;

        return orgFinanceDataMapper.queryAllCount(businessType, venueId, userId, startTime, endTime);
    }

}


