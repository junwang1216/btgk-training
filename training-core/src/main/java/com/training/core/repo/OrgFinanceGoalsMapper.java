package com.training.core.repo;

import com.training.core.repo.po.OrgFinanceGoals;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrgFinanceGoalsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrgFinanceGoals record);

    int insertBatch(List<OrgFinanceGoals> list);

    List<OrgFinanceGoals> queryAll(@Param("busType") Integer busType, @Param("goalType") Integer goalType,
                                   @Param("venueId") Integer venueId, @Param("year") Integer year, @Param("userId") Integer userId);

    OrgFinanceGoals selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrgFinanceGoals record);
}