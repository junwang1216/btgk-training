package com.training.core.service;

import com.training.core.repo.po.OrgClassStudents;

import java.util.List;

public interface OrgClassStudentsService {

    List<OrgClassStudents> queryOrgClassStudentsListByStudentId(Integer studentId);

    List<OrgClassStudents> queryOrgClassStudentsListByClassId(Integer classId);

    OrgClassStudents getOrgClassStudents(Integer id);

    int totalAllStudentsCount(String startTime, String endTime);

    int addOrgClassStudents(OrgClassStudents orgClassStudents);

    int delOrgClassStudents(OrgClassStudents orgClassStudents);

}
