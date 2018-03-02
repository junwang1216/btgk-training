package com.training.core.repo;

import com.training.core.repo.po.OrgInformation;

public interface OrgInformationMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrgInformation record);

    OrgInformation selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrgInformation record);
}