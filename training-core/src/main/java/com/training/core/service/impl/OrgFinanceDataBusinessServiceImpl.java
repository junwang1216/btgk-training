package com.training.core.service.impl;

import com.training.core.repo.OrgFinanceDataBusinessMapper;
import com.training.core.repo.po.OrgFinanceDataBusiness;
import com.training.core.service.OrgFinanceDataBusinessService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgFinanceDataBusinessServiceImpl implements OrgFinanceDataBusinessService {

    @Resource
    private OrgFinanceDataBusinessMapper orgFinanceDataBusinessMapper;

    @Override
    public OrgFinanceDataBusiness getOrgFinanceDataBusiness(String businessNo) {
        return orgFinanceDataBusinessMapper.selectByPrimaryKey(businessNo);
    }

    @Override
    public int addOrgFinanceDataBusiness(OrgFinanceDataBusiness orgFinanceDataBusiness) {
        return orgFinanceDataBusinessMapper.insert(orgFinanceDataBusiness);
    }

    @Override
    public int saveOrgFinanceDataBusiness(OrgFinanceDataBusiness orgFinanceDataBusiness) {
        return orgFinanceDataBusinessMapper.updateByPrimaryKey(orgFinanceDataBusiness);
    }

    @Override
    public List<OrgFinanceDataBusiness> queryOrgFinanceDataBusinessList(Integer businessType, Integer venueId, Integer userId,
                                                        String startTime, String endTime, Integer start, Integer pageSize) {
        businessType = businessType > 0 ? businessType : null;
        venueId = venueId > 0 ? venueId : null;
        userId = userId > 0 ? userId : null;

        return orgFinanceDataBusinessMapper.queryAll(businessType, venueId, userId, startTime, endTime, start, pageSize);
    }

    @Override
    public int queryOrgFinanceDataBusinessCount(Integer businessType, Integer venueId, Integer userId, String startTime, String endTime) {
        businessType = businessType > 0 ? businessType : null;
        venueId = venueId > 0 ? venueId : null;
        userId = userId > 0 ? userId : null;

        return orgFinanceDataBusinessMapper.queryAllCount(businessType, venueId, userId, startTime, endTime);
    }

    @Override
    public int deleteOrgFinanceDataBusiness(String businessNo) {
        return orgFinanceDataBusinessMapper.deleteByPrimaryKey(businessNo);
    }

}


