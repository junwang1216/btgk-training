package com.training.core.service;

import com.training.core.repo.po.OrgOperator;
import com.training.core.repo.query.OrgOperatorRequest;

import java.util.List;

public interface OrgOperatorService {

    OrgOperator selectByPrimaryKey(Integer id);

    OrgOperator innerLogin(OrgOperatorRequest orgOperatorRequest);

    int setLastLoginTime(String lastTime, Integer id);

    List<OrgOperator> queryOrgOperatorList(Integer orgId, Integer roleId);

    int addOrgOperator(OrgOperator orgOperator);

    int saveOrgOperator(OrgOperator orgOperator);

    int modifyPassword(OrgOperator orgOperator);
}
