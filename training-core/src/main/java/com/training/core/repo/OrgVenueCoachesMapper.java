package com.training.core.repo;

import com.training.core.repo.po.OrgVenueCoaches;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrgVenueCoachesMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrgVenueCoaches record);

    OrgVenueCoaches selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrgVenueCoaches record);

    List<OrgVenueCoaches> queryAll(@Param("venueId") Integer venueId, @Param("coachId") Integer coachId);

    int insertBatch(List<OrgVenueCoaches> list);

    int clearAllByCoachId(Integer coachId);
}