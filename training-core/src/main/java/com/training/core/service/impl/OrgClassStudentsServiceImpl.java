package com.training.core.service.impl;

import com.training.core.repo.OrgClassStudentsMapper;
import com.training.core.repo.po.OrgClassStudents;
import com.training.core.service.OrgClassStudentsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgClassStudentsServiceImpl implements OrgClassStudentsService {

    @Resource
    private OrgClassStudentsMapper orgClassStudentsMapper;

    @Override
    public List<OrgClassStudents> queryOrgClassStudentsListByStudentId(Integer studentId) {
        return orgClassStudentsMapper.queryOrgClassStudentsList(0, studentId);
    }

    @Override
    public List<OrgClassStudents> queryOrgClassStudentsListByClassId(Integer classId) {
        return orgClassStudentsMapper.queryOrgClassStudentsList(classId, 0);
    }

    @Override
    public int addOrgClassStudents(OrgClassStudents orgClassStudents) {
        return orgClassStudentsMapper.insert(orgClassStudents);
    }

}


