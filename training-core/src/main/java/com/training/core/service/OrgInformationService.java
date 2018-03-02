package com.training.core.service;

import com.training.core.repo.po.OrgInformation;

public interface OrgInformationService {

    OrgInformation selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrgInformation record);

    int addOrgInformation(OrgInformation record);

}
