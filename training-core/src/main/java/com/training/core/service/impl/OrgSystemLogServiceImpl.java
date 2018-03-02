package com.training.core.service.impl;

import com.training.core.repo.OrgSystemLogMapper;
import com.training.core.repo.po.OrgSystemLog;
import com.training.core.service.OrgSystemLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgSystemLogServiceImpl implements OrgSystemLogService {

    @Resource
    private OrgSystemLogMapper orgSystemLogMapper;

    @Override
    public int clearSystemLogByOrgId(int orgId, int days) {
        return orgSystemLogMapper.deleteByOrgId(orgId, days);
    }

    @Override
    public int addSystemLog(OrgSystemLog orgSystemLog) {
        return orgSystemLogMapper.insert(orgSystemLog);
    }

    @Override
    public List<OrgSystemLog> queryAllSystemLog(int orgId) {
        return orgSystemLogMapper.queryAllLog(orgId);
    }

}


