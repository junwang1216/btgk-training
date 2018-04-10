package com.training.core.service;

import com.training.core.repo.po.OrgClassTest;

import java.util.List;

public interface OrgClassTestService {

    int addOrgClassTest(OrgClassTest orgClassTest);

    int saveOrgClassTest(OrgClassTest orgClassTest);

    int saveOrgClassTestStatus(OrgClassTest orgClassTest);

    List<OrgClassTest> queryOrgClassTestList(Integer classId, Integer status, Integer start, Integer pageSize);

    int queryOrgClassTestCount(Integer classId, Integer status);

    OrgClassTest getOrgClassTest(Integer id);

}
