package com.training.core.repo;

import com.training.core.repo.po.OrgFinanceUsers;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrgFinanceUsersMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrgFinanceUsers record);

    List<OrgFinanceUsers> queryAll(@Param("start") Integer start, @Param("pageSize") Integer pageSize);

    int queryAllCount();

    OrgFinanceUsers selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrgFinanceUsers record);

    List<OrgFinanceUsers> selectAll(OrgFinanceUsers record);

    int updateStatus(OrgFinanceUsers record);
}