package com.training.core.repo;

import com.training.core.repo.po.OrgFinanceEnums;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrgFinanceEnumsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrgFinanceEnums record);

    OrgFinanceEnums selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrgFinanceEnums record);

    List<OrgFinanceEnums> queryAll(@Param("enumGroup") String enumGroup);

    int clearAll(@Param("enumGroup") String enumGroup);

    int insertBatch(List<OrgFinanceEnums> list);
}