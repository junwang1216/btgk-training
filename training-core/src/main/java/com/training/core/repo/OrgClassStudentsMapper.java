package com.training.core.repo;

import com.training.core.repo.po.OrgClassStudents;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrgClassStudentsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrgClassStudents record);

    int insertBatch(List<OrgClassStudents> recordList);

    int delete(OrgClassStudents record);

    int deleteBatch(List<OrgClassStudents> recordList);

    OrgClassStudents selectByPrimaryKey(Integer id);

    OrgClassStudents getOrgClassStudents(@Param("classId") Integer classId, @Param("studentId") Integer studentId, @Param("status") Integer status);

    int updateByPrimaryKey(OrgClassStudents record);

    List<OrgClassStudents> queryOrgClassStudentsList(@Param("classId") Integer classId, @Param("studentId") Integer studentId, @Param("orderNo") String orderNo, @Param("status") Integer status);

    List<OrgClassStudents> queryOrgClassStudentsListByDate(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("status") Integer status);

    int totalAllStudentsCount(@Param("startTime") String startTime, @Param("endTime") String endTime);

    int setStatus(OrgClassStudents record);
}