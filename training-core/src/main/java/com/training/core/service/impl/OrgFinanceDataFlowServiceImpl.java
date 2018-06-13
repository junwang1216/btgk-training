package com.training.core.service.impl;

import com.training.core.repo.OrgFinanceDataFlowMapper;
import com.training.core.repo.po.OrgFinanceDataFlow;
import com.training.core.service.OrgFinanceDataFlowService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgFinanceDataFlowServiceImpl implements OrgFinanceDataFlowService {

    @Resource
    private OrgFinanceDataFlowMapper orgFinanceDataFlowMapper;

    @Override
    public OrgFinanceDataFlow getOrgFinanceDataFlow(String businessNo) {
        return orgFinanceDataFlowMapper.selectByPrimaryKey(businessNo);
    }

    @Override
    public int addOrgFinanceDataFlow(OrgFinanceDataFlow orgFinanceDataFlow) {
        return orgFinanceDataFlowMapper.insert(orgFinanceDataFlow);
    }

    @Override
    public int saveOrgFinanceDataFlow(OrgFinanceDataFlow orgFinanceDataFlow) {
        return orgFinanceDataFlowMapper.updateByPrimaryKey(orgFinanceDataFlow);
    }

    @Override
    public List<OrgFinanceDataFlow> queryOrgFinanceDataFlowList(Integer businessType, Integer venueId, Integer userId,
                                                        String startTime, String endTime, Integer start, Integer pageSize) {
        businessType = businessType > 0 ? businessType : null;
        venueId = venueId > 0 ? venueId : null;
        userId = userId > 0 ? userId : null;

        return orgFinanceDataFlowMapper.queryAll(businessType, venueId, userId, startTime, endTime, start, pageSize);
    }

    @Override
    public int queryOrgFinanceDataFlowCount(Integer businessType, Integer venueId, Integer userId, String startTime, String endTime) {
        businessType = businessType > 0 ? businessType : null;
        venueId = venueId > 0 ? venueId : null;
        userId = userId > 0 ? userId : null;

        return orgFinanceDataFlowMapper.queryAllCount(businessType, venueId, userId, startTime, endTime);
    }

    @Override
    public int deleteOrgFinanceDataFlow(String businessNo) {
        return orgFinanceDataFlowMapper.deleteByPrimaryKey(businessNo);
    }

}


