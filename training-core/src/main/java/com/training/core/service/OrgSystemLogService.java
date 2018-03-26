package com.training.core.service;

import com.training.core.repo.po.OrgSystemLog;

import java.util.List;

public interface OrgSystemLogService {

    int clearSystemLogByOrgId(Integer orgId, Integer days);

    int addSystemLog(OrgSystemLog orgSystemLog);

    List<OrgSystemLog> queryAllSystemLog(Integer orgId, Integer start, Integer pageSize);

    int queryAllSystemLogCount(Integer orgId);

}
