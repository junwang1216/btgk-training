package com.training.core.service;

import com.training.core.repo.po.OrgFinanceDataAttendance;

import java.util.List;

public interface OrgFinanceDataAttendanceService {

    OrgFinanceDataAttendance getOrgFinanceDataAttendance(String businessNo);

    int addOrgFinanceDataAttendance(OrgFinanceDataAttendance orgFinanceDataAttendance);

    int saveOrgFinanceDataAttendance(OrgFinanceDataAttendance orgFinanceDataAttendance);

    List<OrgFinanceDataAttendance> queryOrgFinanceDataAttendanceList(Integer businessType, Integer venueId, Integer userId, String startTime, String endTime, Integer start, Integer pageSize);

    int queryOrgFinanceDataAttendanceCount(Integer businessType, Integer venueId, Integer userId, String startTime, String endTime);

    int deleteOrgFinanceDataAttendance(String businessNo);

}
