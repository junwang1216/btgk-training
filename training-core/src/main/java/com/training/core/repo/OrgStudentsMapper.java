package com.training.core.repo;

import com.training.core.repo.po.OrgStudents;
import com.training.core.repo.query.OrgStudentRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrgStudentsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrgStudents record);

    OrgStudents selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrgStudents record);

    List<OrgStudents> queryAll(@Param("realName") String realName, @Param("mobile") String mobile, @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    int queryAllCount(@Param("realName") String realName, @Param("mobile") String mobile);

    int totalAllCount(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<OrgStudents> queryAllByDate(@Param("startTime") String startTime, @Param("endTime") String endTime);

    int setLastLoginTime(@Param("lastTime") String lastTime, @Param("id") Integer id);

    OrgStudents innerLogin(OrgStudentRequest orgOperatorRequest);
}