package com.training.core.repo;

import com.training.core.repo.po.OrgClass;

import java.util.List;

public interface OrgClassMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrgClass record);

    OrgClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrgClass record);

    int updateStatusByPrimaryKey(OrgClass record);

    List<OrgClass> queryAll();
}