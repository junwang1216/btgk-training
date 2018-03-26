package com.training.core.repo;

import com.training.core.repo.po.OrgSystemLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrgSystemLogMapper {

    int deleteByOrgId(@Param("orgId") Integer orgId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    int insert(OrgSystemLog record);

    List<OrgSystemLog> queryAllLog(@Param("orgId") Integer orgId, @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    int queryAllLogCount(@Param("orgId") Integer orgId);
}