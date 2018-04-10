package com.training.core.repo;

import com.training.core.repo.po.OrgClassTest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrgClassTestMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrgClassTest record);

    OrgClassTest selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrgClassTest record);

    int updateStatusByPrimaryKey(OrgClassTest record);

    List<OrgClassTest> queryAll(@Param("classId") Integer classId, @Param("status") Integer status, @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    int queryAllCount(@Param("classId") Integer classId, @Param("status") Integer status);
}