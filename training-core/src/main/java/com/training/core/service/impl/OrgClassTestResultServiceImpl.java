package com.training.core.service.impl;

import com.training.core.repo.OrgClassTestResultMapper;
import com.training.core.repo.po.OrgClassTest;
import com.training.core.repo.po.OrgClassTestResult;
import com.training.core.service.OrgClassTestResultService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgClassTestResultServiceImpl implements OrgClassTestResultService {

    @Resource
    private OrgClassTestResultMapper orgClassTestResultMapper;

    @Override
    public int addOrgClassTestResult(OrgClassTestResult orgClassTestResult) {
        return orgClassTestResultMapper.insert(orgClassTestResult);
    }

    @Override
    public int addOrgClassTestResultBatch(List<OrgClassTestResult> orgClassTestResultList) {
        return orgClassTestResultMapper.insertBatch(orgClassTestResultList);
    }

    @Override
    public int saveOrgClassTestResult(OrgClassTestResult orgClassTestResult) {
        return orgClassTestResultMapper.updateByPrimaryKey(orgClassTestResult);
    }

    @Override
    public List<OrgClassTestResult> queryOrgClassTestResultList(Integer testId, Integer studentId) {
        return orgClassTestResultMapper.queryAll(testId, studentId);
    }

    @Override
    public OrgClassTestResult getOrgClassTestResult(Integer id) {
        return orgClassTestResultMapper.selectByPrimaryKey(id);
    }

    @Override
    public int clearTestResultByStudentId(Integer studentId) {
        return orgClassTestResultMapper.clearTestResultByStudentId(studentId);
    }
}


