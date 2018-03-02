package com.training.core.repo;

import com.training.core.repo.po.OrgSportsSkills;

import java.util.List;

public interface OrgSportsSkillsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrgSportsSkills record);

    OrgSportsSkills selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrgSportsSkills record);

    List<OrgSportsSkills> queryAllBySportId(Integer sportId);

    int clearAllBySportId(Integer sportId);

    int insertBatch(List<OrgSportsSkills> list);
}