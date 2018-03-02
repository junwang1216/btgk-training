package com.training.core.repo;

import com.training.core.repo.po.OrgVenues;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrgVenuesMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrgVenues record);

    OrgVenues selectByPrimaryKey(Integer id);

    List<OrgVenues> queryAll(@Param("orgId") Integer orgId, @Param("status") Integer status);

    int updateByPrimaryKey(OrgVenues record);

    int setVenueStatus(OrgVenues record);
}