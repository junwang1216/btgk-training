package com.training.core.repo;

import com.training.core.repo.po.OrgCoaches;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrgCoachesMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrgCoaches record);

    int setCoachStatus(OrgCoaches record);

    OrgCoaches selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrgCoaches record);

    List<OrgCoaches> queryAll(@Param("realName") String realName, @Param("mobile") String mobile, @Param("status") Integer status,
                              @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    int queryAllCount(@Param("realName") String realName, @Param("mobile") String mobile, @Param("status") Integer status);
}