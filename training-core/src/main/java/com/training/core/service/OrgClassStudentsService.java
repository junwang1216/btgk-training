package com.training.core.service;

import com.training.core.repo.po.OrgClassStudents;

import java.util.List;

public interface OrgClassStudentsService {

    List<OrgClassStudents> queryOrgClassStudentsListByStudentId(Integer studentId);

    List<OrgClassStudents> queryOrgClassStudentsListByClassId(Integer classId);

    List<OrgClassStudents> queryOrgClassStudentsListByOrderNo(String orderNo);

    OrgClassStudents getOrgClassStudents(Integer id);

    OrgClassStudents getOrgClassStudents(Integer classId, Integer studentId);

    int totalAllStudentsCount(String startTime, String endTime);

    List<OrgClassStudents> queryOrgClassStudentsListByDate(String startTime, String endTime);

    int addOrgClassStudents(OrgClassStudents orgClassStudents);

    int addOrgClassStudentsBath(List<OrgClassStudents> orgClassStudentsList);

    int delOrgClassStudents(OrgClassStudents orgClassStudents);

    int delOrgClassStudentsBatch(List<OrgClassStudents> orgClassStudentsList);

    int saveOrgClassStudents(OrgClassStudents orgClassStudents);

}
