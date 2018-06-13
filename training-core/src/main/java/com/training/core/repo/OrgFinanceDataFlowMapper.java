package com.training.core.repo;

import com.training.core.repo.po.OrgFinanceDataFlow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrgFinanceDataFlowMapper {

    int insert(OrgFinanceDataFlow record);

    int updateByPrimaryKey(OrgFinanceDataFlow record);

    List<OrgFinanceDataFlow> queryAll(@Param("businessType") Integer businessType, @Param("venueId") Integer venueId, @Param("userId") Integer userId,
                                      @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    int queryAllCount(@Param("businessType") Integer businessType, @Param("venueId") Integer venueId, @Param("userId") Integer userId,
                      @Param("startTime") String startTime, @Param("endTime") String endTime);

    OrgFinanceDataFlow selectByPrimaryKey(String businessNo);

    int deleteByPrimaryKey(String businessNo);

}