package com.training.core.repo;

import com.training.core.repo.po.OrgAttendance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrgAttendanceMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrgAttendance record);

    OrgAttendance selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrgAttendance record);

    List<OrgAttendance> queryUserSignLog(@Param("inUserId") Integer inUserId, @Param("inClassID") Integer inClassID, @Param("inRole") Integer inRole);

    List<OrgAttendance> queryClassSignLog(@Param("inClassID") Integer inClassID, @Param("inRole") Integer inRole);
}