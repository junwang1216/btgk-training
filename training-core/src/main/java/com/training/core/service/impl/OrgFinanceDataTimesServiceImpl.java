package com.training.core.service.impl;

import com.training.core.repo.OrgFinanceDataTimesMapper;
import com.training.core.repo.po.OrgFinanceDataTimes;
import com.training.core.service.OrgFinanceDataTimesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgFinanceDataTimesServiceImpl implements OrgFinanceDataTimesService {

    @Resource
    private OrgFinanceDataTimesMapper orgFinanceDataTimesMapper;

    @Override
    public OrgFinanceDataTimes getOrgFinanceDataTimes(String businessNo) {
        return orgFinanceDataTimesMapper.selectByPrimaryKey(businessNo);
    }

    @Override
    public int addOrgFinanceDataTimes(OrgFinanceDataTimes orgFinanceDataTimes) {
        return orgFinanceDataTimesMapper.insert(orgFinanceDataTimes);
    }

    @Override
    public int saveOrgFinanceDataTimes(OrgFinanceDataTimes orgFinanceDataTimes) {
        return orgFinanceDataTimesMapper.updateByPrimaryKey(orgFinanceDataTimes);
    }

    @Override
    public List<OrgFinanceDataTimes> queryOrgFinanceDataTimesList(Integer businessType, Integer venueId, Integer userId,
                                                        String startTime, String endTime, Integer start, Integer pageSize) {
        businessType = businessType > 0 ? businessType : null;
        venueId = venueId > 0 ? venueId : null;
        userId = userId > 0 ? userId : null;

        return orgFinanceDataTimesMapper.queryAll(businessType, venueId, userId, startTime, endTime, start, pageSize);
    }

    @Override
    public int queryOrgFinanceDataTimesCount(Integer businessType, Integer venueId, Integer userId, String startTime, String endTime) {
        businessType = businessType > 0 ? businessType : null;
        venueId = venueId > 0 ? venueId : null;
        userId = userId > 0 ? userId : null;

        return orgFinanceDataTimesMapper.queryAllCount(businessType, venueId, userId, startTime, endTime);
    }

    @Override
    public int deleteOrgFinanceDataTimes(String businessNo) {
        return orgFinanceDataTimesMapper.deleteByPrimaryKey(businessNo);
    }

}


