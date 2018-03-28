package com.training.core.service.impl;

import com.training.core.repo.OrgStudentsMapper;
import com.training.core.repo.po.OrgStudents;
import com.training.core.repo.query.OrgStudentRequest;
import com.training.core.service.OrgStudentsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgStudentsServiceImpl implements OrgStudentsService {

    @Resource
    private OrgStudentsMapper orgStudentsMapper;

    @Override
    public int addOrgStudents(OrgStudents orgStudents) {
        return orgStudentsMapper.insert(orgStudents);
    }

    @Override
    public int saveOrgStudents(OrgStudents orgStudents) {
        return orgStudentsMapper.updateByPrimaryKey(orgStudents);
    }

    @Override
    public List<OrgStudents> queryOrgStudentsList(String realName, String mobile, Integer classId, Integer start, Integer pageSize) {
        return orgStudentsMapper.queryAll(realName, mobile, start, pageSize);
    }

    @Override
    public int queryOrgStudentsListCount(String realName, String mobile, Integer classId) {
        return orgStudentsMapper.queryAllCount(realName, mobile);
    }

    @Override
    public int totalOrgStudentsCount(String startTime, String endTime) {
        return orgStudentsMapper.totalAllCount(startTime, endTime);
    }

    @Override
    public List<OrgStudents> queryOrgStudentsListByDate(String startTime, String endTime) {
        return orgStudentsMapper.queryAllByDate(startTime, endTime);
    }

    @Override
    public OrgStudents getOrgStudents(Integer id) {
        return orgStudentsMapper.selectByPrimaryKey(id);
    }

    @Override
    public OrgStudents innerLogin(OrgStudentRequest orgStudentRequest) {
        return orgStudentsMapper.innerLogin(orgStudentRequest);
    }

    @Override
    public int setLastLoginTime(String lastTime, Integer id) {
        return orgStudentsMapper.setLastLoginTime(lastTime, id);
    }
}


