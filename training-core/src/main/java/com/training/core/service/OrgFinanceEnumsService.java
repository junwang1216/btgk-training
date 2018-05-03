package com.training.core.service;

import com.training.core.repo.po.OrgFinanceEnums;

import java.util.List;

public interface OrgFinanceEnumsService {

    List<OrgFinanceEnums> queryOrgFinanceEnumsList(String enumGroup);

}
