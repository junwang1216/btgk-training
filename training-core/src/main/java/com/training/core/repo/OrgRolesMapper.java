package com.training.core.repo;

import com.training.core.repo.po.OrgRoles;

public interface OrgRolesMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrgRoles record);

    OrgRoles selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrgRoles record);
}