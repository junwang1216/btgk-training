package com.training.core.service.impl;

import com.training.core.common.util.DateUtil;
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
    public int clearSystemLogByOrgId(Integer orgId, Integer days) {
        String endTime, startTime = null;
        switch (days) {
            case 30:
            default:
                endTime = DateUtil.getTimesMonthmorning();
        }

        return orgSystemLogMapper.deleteByOrgId(orgId, startTime, endTime);
    }

    @Override
    public int addSystemLog(OrgSystemLog orgSystemLog) {
        return orgSystemLogMapper.insert(orgSystemLog);
    }

    @Override
    public List<OrgSystemLog> queryAllSystemLog(Integer orgId, Integer start, Integer pageSize) {
        return orgSystemLogMapper.queryAllLog(orgId, start, pageSize);
    }

    @Override
    public int queryAllSystemLogCount(Integer orgId) {
        return orgSystemLogMapper.queryAllLogCount(orgId);
    }

}


