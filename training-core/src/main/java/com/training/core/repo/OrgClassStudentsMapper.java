package com.training.core.repo;

import com.training.core.repo.po.OrgClassStudents;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrgClassStudentsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrgClassStudents record);

    OrgClassStudents selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrgClassStudents record);

    List<OrgClassStudents> queryOrgClassStudentsList(@Param("classId") Integer classId, @Param("studentId") Integer studentId);
}