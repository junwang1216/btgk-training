package com.training.core.service;

import com.training.core.repo.po.OrgAttendance;

import java.util.List;

public interface OrgAttendanceService {

    int toSignin(OrgAttendance orgAttendance);

    List<OrgAttendance> queryStudentSignLog(int studentId, int classId);

    List<OrgAttendance> queryCoachSignLog(int coachId, int classId);

    List<OrgAttendance> queryClassSignLog(int classId, int inRole);

}
