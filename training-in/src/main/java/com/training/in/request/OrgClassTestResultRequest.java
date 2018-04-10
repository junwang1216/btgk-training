package com.training.in.request;

import com.training.core.repo.po.OrgClassTestResult;

import java.util.List;

/**
 * Created by wangjun on 2018/3/15.
 */
public class OrgClassTestResultRequest {

    private int studentId;
    private String testRemark;
    private int testId;
    private List<OrgClassTestResult> orgClassTestResultList;

    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getTestId() {
        return testId;
    }
    public void setTestId(int testId) {
        this.testId = testId;
    }

    public String getTestRemark() {
        return testRemark;
    }
    public void setTestRemark(String testRemark) {
        this.testRemark = testRemark;
    }

    public List<OrgClassTestResult> getOrgClassTestResultList() {
        return orgClassTestResultList;
    }
    public void setOrgClassTestResultList(List<OrgClassTestResult> orgClassTestResultList) {
        this.orgClassTestResultList = orgClassTestResultList;
    }
}
