package com.training.core.repo;

import com.training.core.repo.po.OrgSports;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrgSportsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrgSports record);

    OrgSports selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrgSports record);

    List<OrgSports> queryAll(@Param("orgId") Integer orgId);

    int insertBatch(List<OrgSports> list);
}