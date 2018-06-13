package com.training.core.service.impl;

import com.training.core.repo.OrgFinanceDataIncomeMapper;
import com.training.core.repo.po.OrgFinanceDataIncome;
import com.training.core.service.OrgFinanceDataIncomeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgFinanceDataIncomeServiceImpl implements OrgFinanceDataIncomeService {

    @Resource
    private OrgFinanceDataIncomeMapper orgFinanceDataIncomeMapper;

    @Override
    public OrgFinanceDataIncome getOrgFinanceDataIncome(String businessNo) {
        return orgFinanceDataIncomeMapper.selectByPrimaryKey(businessNo);
    }

    @Override
    public int addOrgFinanceDataIncome(OrgFinanceDataIncome orgFinanceDataIncome) {
        return orgFinanceDataIncomeMapper.insert(orgFinanceDataIncome);
    }

    @Override
    public int saveOrgFinanceDataIncome(OrgFinanceDataIncome orgFinanceDataIncome) {
        return orgFinanceDataIncomeMapper.updateByPrimaryKey(orgFinanceDataIncome);
    }

    @Override
    public List<OrgFinanceDataIncome> queryOrgFinanceDataIncomeList(Integer businessType, Integer venueId, Integer userId,
                                                        String startTime, String endTime, Integer start, Integer pageSize) {
        businessType = businessType > 0 ? businessType : null;
        venueId = venueId > 0 ? venueId : null;
        userId = userId > 0 ? userId : null;

        return orgFinanceDataIncomeMapper.queryAll(businessType, venueId, userId, startTime, endTime, start, pageSize);
    }

    @Override
    public int queryOrgFinanceDataIncomeCount(Integer businessType, Integer venueId, Integer userId, String startTime, String endTime) {
        businessType = businessType > 0 ? businessType : null;
        venueId = venueId > 0 ? venueId : null;
        userId = userId > 0 ? userId : null;

        return orgFinanceDataIncomeMapper.queryAllCount(businessType, venueId, userId, startTime, endTime);
    }

    @Override
    public int deleteOrgFinanceDataIncome(String businessNo) {
        return orgFinanceDataIncomeMapper.deleteByPrimaryKey(businessNo);
    }

}


