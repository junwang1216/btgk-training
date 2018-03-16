package com.training.core.service;

import com.training.core.repo.po.OrgSystemLog;

import java.util.List;

public interface OrgSystemLogService {

    int clearSystemLogByOrgId(int orgId, int days);

    int addSystemLog(OrgSystemLog orgSystemLog);

    List<OrgSystemLog> queryAllSystemLog(int orgId, int start, int pageSize);

}
