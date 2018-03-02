package com.training.core.service.impl;

import com.training.core.repo.OrgAttendanceMapper;
import com.training.core.repo.po.OrgAttendance;
import com.training.core.service.OrgAttendanceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgAttendanceServiceImpl implements OrgAttendanceService {

    @Resource
    private OrgAttendanceMapper orgAttendanceMapper;

    @Override
    public int toSignin(OrgAttendance orgAttendance) {
        return orgAttendanceMapper.insert(orgAttendance);
    }

    @Override
    public List<OrgAttendance> queryStudentSignLog(int studentId, int classId) {
        return orgAttendanceMapper.queryUserSignLog(studentId, classId, 3);
    }

    @Override
    public List<OrgAttendance> queryCoachSignLog(int coachId, int classId) {
        return orgAttendanceMapper.queryUserSignLog(coachId, classId, 1);
    }

    @Override
    public List<OrgAttendance> queryClassSignLog(int classId) {
        return orgAttendanceMapper.queryClassSignLog(classId, 3);
    }
}


