package com.training.core.service.impl;

import com.training.core.repo.OrgFinanceEnumsMapper;;
import com.training.core.repo.po.OrgFinanceEnums;
import com.training.core.service.OrgFinanceEnumsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgFinanceEnumsServiceImpl implements OrgFinanceEnumsService {

    @Resource
    private OrgFinanceEnumsMapper orgFinanceEnumsMapper;

    @Override
    public List<OrgFinanceEnums> queryOrgFinanceEnumsList(String enumGroup) {
        return orgFinanceEnumsMapper.queryAll(enumGroup);
    }

    @Override
    public int addOrgFinanceEnumsBatch(List<OrgFinanceEnums> orgFinanceEnumsList) {
        return orgFinanceEnumsMapper.insertBatch(orgFinanceEnumsList);
    }

    @Override
    public int clearOrgFinanceEnums(String enumGroup) {
        return orgFinanceEnumsMapper.clearAll(enumGroup);
    }

}


