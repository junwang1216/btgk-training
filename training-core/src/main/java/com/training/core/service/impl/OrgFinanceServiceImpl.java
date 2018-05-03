package com.training.core.service.impl;

import com.training.core.repo.OrgFinanceMapper;
import com.training.core.repo.po.OrgFinance;
import com.training.core.service.OrgFinanceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgFinanceServiceImpl implements OrgFinanceService {

    @Resource
    private OrgFinanceMapper orgFinanceMapper;

    @Override
    public OrgFinance getOrgFinance(String businessNo) {
        return orgFinanceMapper.selectByPrimaryKey(businessNo);
    }

    @Override
    public int addOrgFinance(OrgFinance orgFinance) {
        return orgFinanceMapper.insert(orgFinance);
    }

    @Override
    public int saveOrgFinance(OrgFinance orgFinance) {
        return orgFinanceMapper.updateByPrimaryKey(orgFinance);
    }

    @Override
    public List<OrgFinance> queryOrgFinanceList(Integer start, Integer pageSize) {
        return orgFinanceMapper.queryAll(start, pageSize);
    }

    @Override
    public int queryOrgFinanceCount() {
        return orgFinanceMapper.queryAllCount();
    }

}


