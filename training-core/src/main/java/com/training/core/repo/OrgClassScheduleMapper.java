package com.training.core.repo;

import com.training.core.repo.po.OrgClassSchedule;

import java.util.List;

public interface OrgClassScheduleMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrgClassSchedule record);

    OrgClassSchedule selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrgClassSchedule record);

    List<OrgClassSchedule> queryAllByClassId(Integer classId);

    int clearAllByClassId(Integer classId);

    int insertBatch(List<OrgClassSchedule> list);
}