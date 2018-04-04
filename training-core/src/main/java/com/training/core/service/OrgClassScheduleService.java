package com.training.core.service;

import com.training.core.repo.po.OrgClassSchedule;

import java.util.List;

public interface OrgClassScheduleService {

    int addOrgClassScheduleBatch(List<OrgClassSchedule> orgClassScheduleList);

    int saveClassSchedule(OrgClassSchedule orgClassSchedule);

    int deleteClassSchedule(int scheduleId);

    List<OrgClassSchedule> queryOrgClassScheduleList(int classId);

    int clearAllByClassId(int classId);

}
