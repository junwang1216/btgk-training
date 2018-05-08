package com.training.core.repo;

import com.training.core.repo.po.OrgFinanceVenues;
import java.util.List;

public interface OrgFinanceVenuesMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrgFinanceVenues record);

    OrgFinanceVenues selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrgFinanceVenues record);

    List<OrgFinanceVenues> queryAll();
}