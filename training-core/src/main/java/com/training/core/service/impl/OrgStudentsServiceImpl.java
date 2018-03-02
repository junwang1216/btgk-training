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
    public List<OrgStudents> queryOrgStudentsList() {
        return orgStudentsMapper.queryAll(null, null, null, null);
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


