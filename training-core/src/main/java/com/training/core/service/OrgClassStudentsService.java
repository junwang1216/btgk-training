package com.training.core.service;

import com.training.core.repo.po.OrgClassStudents;

import java.util.List;

public interface OrgClassStudentsService {

    List<OrgClassStudents> queryOrgClassStudentsListByStudentId(Integer studentId);

    List<OrgClassStudents> queryOrgClassStudentsListByClassId(Integer classId);

    int addOrgClassStudents(OrgClassStudents orgClassStudents);

}
