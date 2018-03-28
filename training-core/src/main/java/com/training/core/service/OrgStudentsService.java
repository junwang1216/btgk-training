package com.training.core.service;

import com.training.core.repo.po.OrgStudents;
import com.training.core.repo.query.OrgStudentRequest;

import java.util.List;

public interface OrgStudentsService {

    int addOrgStudents(OrgStudents orgStudents);

    int saveOrgStudents(OrgStudents orgStudents);

    List<OrgStudents> queryOrgStudentsList(String realName, String mobile, Integer classId, Integer start, Integer pageSize);

    int queryOrgStudentsListCount(String realName, String mobile, Integer classId);

    int totalOrgStudentsCount(String startTime, String endTime);

    List<OrgStudents> queryOrgStudentsListByDate(String startTime, String endTime);

    OrgStudents getOrgStudents(Integer id);

    OrgStudents innerLogin(OrgStudentRequest orgStudentRequest);

    int setLastLoginTime(String lastTime, Integer id);

}
