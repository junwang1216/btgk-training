package com.training.core.repo;

import com.training.core.repo.po.OrgClassTestResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrgClassTestResultMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrgClassTestResult record);

    OrgClassTestResult selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrgClassTestResult record);

    List<OrgClassTestResult> queryAll(@Param("testId") Integer testId, @Param("studentId") Integer studentId);

    int insertBatch(List<OrgClassTestResult> orgClassTestResultList);

    int clearTestResultByStudentId(@Param("studentId") Integer studentId);
}