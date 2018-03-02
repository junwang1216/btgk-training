package com.training.core.repo;

import com.training.core.repo.po.OrgSportsCoaches;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrgSportsCoachesMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrgSportsCoaches record);

    OrgSportsCoaches selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrgSportsCoaches record);

    List<OrgSportsCoaches> queryAll(@Param("sportId") Integer sportId, @Param("coachId") Integer coachId);

    int insertBatch(List<OrgSportsCoaches> list);

    int clearAllByCoachId(Integer coachId);
}