package com.training.core.service;

import com.training.core.repo.po.OrgCoachesRoles;

import java.util.List;

public interface OrgCoachesRolesService {

    int addOrgCoachesRolesBatch(List<OrgCoachesRoles> orgCoachesRolesList);

    List<OrgCoachesRoles> queryOrgCoachesRolesList(int coachId);

    int clearAllByCoachId(int coachId);

}
