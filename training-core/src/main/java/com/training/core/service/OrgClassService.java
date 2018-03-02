package com.training.core.service;

import com.training.core.repo.po.OrgClass;

import java.util.List;

public interface OrgClassService {

    int addOrgClass(OrgClass orgClass);

    int saveOrgClass(OrgClass orgClass);

    int saveOrgClassStatus(OrgClass orgClass);

    List<OrgClass> queryOrgClassList();

    OrgClass getOrgClass(Integer id);

}
