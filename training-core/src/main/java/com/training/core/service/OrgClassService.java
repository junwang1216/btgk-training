package com.training.core.service;

import com.training.core.repo.po.OrgClass;

import java.util.List;

public interface OrgClassService {

    int addOrgClass(OrgClass orgClass);

    int saveOrgClass(OrgClass orgClass);

    int saveOrgClassStatus(OrgClass orgClass);

    List<OrgClass> queryOrgClassList(String className, Integer status, Integer start, Integer pageSize);

    int queryOrgClassCount(String className, Integer status);

    int totalOrgClassCount(String startTime, String endTime, Integer status);

    OrgClass getOrgClass(Integer id);

}
