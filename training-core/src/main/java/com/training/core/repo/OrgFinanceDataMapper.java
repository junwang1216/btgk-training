package com.training.core.repo;

import com.training.core.repo.po.OrgFinanceData;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrgFinanceDataMapper {
    int deleteByPrimaryKey(String businessNo);

    int insert(OrgFinanceData record);

    List<OrgFinanceData> queryAll(@Param("businessType") Integer businessType, @Param("venueId") Integer venueId, @Param("userId") Integer userId,
                                  @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    int queryAllCount(@Param("businessType") Integer businessType, @Param("venueId") Integer venueId, @Param("userId") Integer userId,
                                  @Param("startTime") String startTime, @Param("endTime") String endTime);

    OrgFinanceData selectByPrimaryKey(String businessNo);

    int updateByPrimaryKey(OrgFinanceData record);
}