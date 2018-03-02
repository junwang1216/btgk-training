package com.training.core.repo;

import com.training.core.repo.po.OrgOperator;
import com.training.core.repo.query.OrgOperatorRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrgOperatorMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrgOperator record);

    OrgOperator selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrgOperator record);

    int setLastLoginTime(@Param("lastTime") String lastTime, @Param("id") Integer id);

    OrgOperator innerLogin(OrgOperatorRequest orgOperatorRequest);

    List<OrgOperator> queryAll(@Param("orgId") Integer orgId, @Param("roleId") Integer roleId);
}