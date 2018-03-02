package com.training.core.repo;

import com.training.core.repo.po.OrgSystemLog;

import java.util.List;

public interface OrgSystemLogMapper {

    int deleteByOrgId(Integer orgId, Integer days);

    int insert(OrgSystemLog record);

    List<OrgSystemLog> queryAllLog(Integer orgId);
}