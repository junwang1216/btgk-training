package com.training.core.repo;

import com.training.core.repo.po.OrgCoachesRoles;

import java.util.List;

public interface OrgCoachesRolesMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrgCoachesRoles record);

    OrgCoachesRoles selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrgCoachesRoles record);

    List<OrgCoachesRoles> queryAllByCoachId(Integer coachId);

    int clearAllByCoachId(Integer coachId);

    int insertBatch(List<OrgCoachesRoles> list);
}