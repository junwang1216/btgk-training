package com.training.core.repo;

import com.training.core.repo.po.OrgFinanceDataTimes;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrgFinanceDataTimesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrgFinanceDataTimes record);

    OrgFinanceDataTimes selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrgFinanceDataTimes record);

    List<OrgFinanceDataTimes> queryAll(@Param("businessType") Integer businessType, @Param("venueId") Integer venueId, @Param("userId") Integer userId,
                                      @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    int queryAllCount(@Param("businessType") Integer businessType, @Param("venueId") Integer venueId, @Param("userId") Integer userId,
                      @Param("startTime") String startTime, @Param("endTime") String endTime);

    OrgFinanceDataTimes selectByPrimaryKey(String businessNo);
}