package com.training.core.service;

import com.training.core.repo.po.OrgClassTestResult;

import java.util.List;

public interface OrgClassTestResultService {

    int addOrgClassTestResult(OrgClassTestResult orgClassTestResult);

    int addOrgClassTestResultBatch(List<OrgClassTestResult> orgClassTestResultList);

    int saveOrgClassTestResult(OrgClassTestResult orgClassTestResult);

    List<OrgClassTestResult> queryOrgClassTestResultList(Integer testId, Integer studentId);

    OrgClassTestResult getOrgClassTestResult(Integer id);

    int clearTestResultByStudentId(Integer studentId);

}
