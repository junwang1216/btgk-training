package com.training.core.repo;

import com.training.core.repo.po.OrgFinanceDataIncome;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrgFinanceDataIncomeMapper {

    int insert(OrgFinanceDataIncome record);

    int updateByPrimaryKey(OrgFinanceDataIncome record);

    List<OrgFinanceDataIncome> queryAll(@Param("businessType") Integer businessType, @Param("venueId") Integer venueId, @Param("userId") Integer userId,
                                      @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    int queryAllCount(@Param("businessType") Integer businessType, @Param("venueId") Integer venueId, @Param("userId") Integer userId,
                      @Param("startTime") String startTime, @Param("endTime") String endTime);

    OrgFinanceDataIncome selectByPrimaryKey(String businessNo);

    int deleteByPrimaryKey(String businessNo);
}