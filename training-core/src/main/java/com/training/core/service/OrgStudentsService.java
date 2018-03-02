package com.training.core.service;

import com.training.core.repo.po.OrgStudents;
import com.training.core.repo.query.OrgStudentRequest;

import java.util.List;

public interface OrgStudentsService {

    int addOrgStudents(OrgStudents orgStudents);

    int saveOrgStudents(OrgStudents orgStudents);

    List<OrgStudents> queryOrgStudentsList();

    OrgStudents getOrgStudents(Integer id);

    OrgStudents innerLogin(OrgStudentRequest orgStudentRequest);

    int setLastLoginTime(String lastTime, Integer id);

}
