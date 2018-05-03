package com.training.core.repo;

import com.training.core.repo.po.OrgFinance;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrgFinanceMapper {

    int deleteByPrimaryKey(String businessNo);

    int insert(OrgFinance record);

    OrgFinance selectByPrimaryKey(String businessNo);

    int updateByPrimaryKey(OrgFinance record);

    List<OrgFinance> queryAll(@Param("start") Integer start, @Param("pageSize") Integer pageSize);

    int queryAllCount();
}