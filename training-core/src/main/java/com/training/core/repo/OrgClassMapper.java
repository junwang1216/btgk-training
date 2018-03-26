package com.training.core.repo;

import com.training.core.repo.po.OrgClass;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrgClassMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrgClass record);

    OrgClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrgClass record);

    int updateStatusByPrimaryKey(OrgClass record);

    List<OrgClass> queryAll(@Param("className") String className, @Param("status") Integer status, @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    int queryAllCount(@Param("className") String className, @Param("status") Integer status);

    int totalAllCount(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("status") Integer status);
}