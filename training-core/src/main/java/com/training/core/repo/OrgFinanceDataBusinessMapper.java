package com.training.core.repo;

import com.training.core.repo.po.OrgFinanceDataBusiness;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrgFinanceDataBusinessMapper {

    int insert(OrgFinanceDataBusiness record);

    int updateByPrimaryKey(OrgFinanceDataBusiness record);

    List<OrgFinanceDataBusiness> queryAll(@Param("businessType") Integer businessType, @Param("venueId") Integer venueId, @Param("userId") Integer userId,
                                      @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    int queryAllCount(@Param("businessType") Integer businessType, @Param("venueId") Integer venueId, @Param("userId") Integer userId,
                      @Param("startTime") String startTime, @Param("endTime") String endTime);

    OrgFinanceDataBusiness selectByPrimaryKey(String businessNo);

    int deleteByPrimaryKey(String businessNo);
}