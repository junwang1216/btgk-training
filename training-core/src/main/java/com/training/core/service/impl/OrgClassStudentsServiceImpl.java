package com.training.core.service.impl;

import com.training.core.common.enums.StatusEnum;
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
        return orgClassStudentsMapper.queryOrgClassStudentsList(0, studentId, null, StatusEnum.STATUS_OK.getCode());
    }

    @Override
    public List<OrgClassStudents> queryOrgClassStudentsListByClassId(Integer classId) {
        return orgClassStudentsMapper.queryOrgClassStudentsList(classId, 0, null, StatusEnum.STATUS_OK.getCode());
    }

    @Override
    public List<OrgClassStudents> queryOrgClassStudentsListByOrderNo(String orderNo) {
        return orgClassStudentsMapper.queryOrgClassStudentsList(0, 0, orderNo, null);
    }

    @Override
    public List<OrgClassStudents> queryOrgClassStudentsListByDate(String startTime, String endTime) {
        return orgClassStudentsMapper.queryOrgClassStudentsListByDate(startTime, endTime, null);
    }

    @Override
    public OrgClassStudents getOrgClassStudents(Integer id) {
        return orgClassStudentsMapper.selectByPrimaryKey(id);
    }

    @Override
    public OrgClassStudents getOrgClassStudents(Integer classId, Integer studentId) {
        return orgClassStudentsMapper.getOrgClassStudents(classId, studentId, StatusEnum.STATUS_OK.getCode());
    }

    @Override
    public int totalAllStudentsCount(String startTime, String endTime) {
        return orgClassStudentsMapper.totalAllStudentsCount(startTime, endTime);
    }

    @Override
    public int addOrgClassStudents(OrgClassStudents orgClassStudents) {
        return orgClassStudentsMapper.insert(orgClassStudents);
    }

    @Override
    public int addOrgClassStudentsBath(List<OrgClassStudents> orgClassStudentsList) {
        return orgClassStudentsMapper.insertBatch(orgClassStudentsList);
    }

    @Override
    public int delOrgClassStudents(OrgClassStudents orgClassStudents) {
        return orgClassStudentsMapper.delete(orgClassStudents);
    }

    @Override
    public int delOrgClassStudentsBatch(List<OrgClassStudents> orgClassStudentsList) {
        return orgClassStudentsMapper.deleteBatch(orgClassStudentsList);
    }

    @Override
    public int saveOrgClassStudents(OrgClassStudents orgClassStudents) {
        return orgClassStudentsMapper.setStatus(orgClassStudents);
    }
}


