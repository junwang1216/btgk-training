package com.training.core.service.impl;

import com.training.core.repo.OrgClassScheduleMapper;
import com.training.core.repo.po.OrgClassSchedule;
import com.training.core.service.OrgClassScheduleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgClassScheduleServiceImpl implements OrgClassScheduleService {

    @Resource
    private OrgClassScheduleMapper orgClassScheduleMapper;

    @Override
    public int addOrgClassScheduleBatch(List<OrgClassSchedule> orgClassScheduleList) {
        return orgClassScheduleMapper.insertBatch(orgClassScheduleList);
    }

    @Override
    public int saveClassSchedule(OrgClassSchedule orgClassSchedule) {
        return orgClassScheduleMapper.updateByPrimaryKey(orgClassSchedule);
    }

    @Override
    public int deleteClassSchedule(int scheduleId) {
        return orgClassScheduleMapper.deleteByPrimaryKey(scheduleId);
    }

    @Override
    public List<OrgClassSchedule> queryOrgClassScheduleList(int classId) {
        return orgClassScheduleMapper.queryAllByClassId(classId);
    }

    @Override
    public int clearAllByClassId(int classId) {
        return orgClassScheduleMapper.clearAllByClassId(classId);
    }

    @Override
    public OrgClassSchedule getClassSchedule(int scheduleId) {
        return orgClassScheduleMapper.selectByPrimaryKey(scheduleId);
    }

}


