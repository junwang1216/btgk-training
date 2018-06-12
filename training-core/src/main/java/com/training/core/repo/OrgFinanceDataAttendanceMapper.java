package com.training.core.repo;

import com.training.core.repo.po.OrgFinanceDataAttendance;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrgFinanceDataAttendanceMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrgFinanceDataAttendance record);

    OrgFinanceDataAttendance selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrgFinanceDataAttendance record);

    List<OrgFinanceDataAttendance> queryAll(@Param("businessType") Integer businessType, @Param("venueId") Integer venueId, @Param("userId") Integer userId,
                                        @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    int queryAllCount(@Param("businessType") Integer businessType, @Param("venueId") Integer venueId, @Param("userId") Integer userId,
                      @Param("startTime") String startTime, @Param("endTime") String endTime);

    OrgFinanceDataAttendance selectByPrimaryKey(String businessNo);
}