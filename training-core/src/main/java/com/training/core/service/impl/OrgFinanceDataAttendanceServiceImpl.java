package com.training.core.service.impl;

import com.training.core.repo.OrgFinanceDataAttendanceMapper;
import com.training.core.repo.po.OrgFinanceDataAttendance;
import com.training.core.service.OrgFinanceDataAttendanceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgFinanceDataAttendanceServiceImpl implements OrgFinanceDataAttendanceService {

    @Resource
    private OrgFinanceDataAttendanceMapper orgFinanceDataAttendanceMapper;

    @Override
    public OrgFinanceDataAttendance getOrgFinanceDataAttendance(String businessNo) {
        return orgFinanceDataAttendanceMapper.selectByPrimaryKey(businessNo);
    }

    @Override
    public int addOrgFinanceDataAttendance(OrgFinanceDataAttendance orgFinanceDataAttendance) {
        return orgFinanceDataAttendanceMapper.insert(orgFinanceDataAttendance);
    }

    @Override
    public int saveOrgFinanceDataAttendance(OrgFinanceDataAttendance orgFinanceDataAttendance) {
        return orgFinanceDataAttendanceMapper.updateByPrimaryKey(orgFinanceDataAttendance);
    }

    @Override
    public List<OrgFinanceDataAttendance> queryOrgFinanceDataAttendanceList(Integer businessType, Integer venueId, Integer userId,
                                                        String startTime, String endTime, Integer start, Integer pageSize) {
        businessType = businessType > 0 ? businessType : null;
        venueId = venueId > 0 ? venueId : null;
        userId = userId > 0 ? userId : null;

        return orgFinanceDataAttendanceMapper.queryAll(businessType, venueId, userId, startTime, endTime, start, pageSize);
    }

    @Override
    public int queryOrgFinanceDataAttendanceCount(Integer businessType, Integer venueId, Integer userId, String startTime, String endTime) {
        businessType = businessType > 0 ? businessType : null;
        venueId = venueId > 0 ? venueId : null;
        userId = userId > 0 ? userId : null;

        return orgFinanceDataAttendanceMapper.queryAllCount(businessType, venueId, userId, startTime, endTime);
    }

    @Override
    public int deleteOrgFinanceDataAttendance(String businessNo) {
        return orgFinanceDataAttendanceMapper.deleteByPrimaryKey(businessNo);
    }

}


