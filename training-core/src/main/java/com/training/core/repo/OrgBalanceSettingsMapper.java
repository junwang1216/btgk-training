package com.training.core.repo;

import com.training.core.repo.po.OrgBalanceSettings;

import java.util.List;

public interface OrgBalanceSettingsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrgBalanceSettings record);

    OrgBalanceSettings selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrgBalanceSettings record);

    List<OrgBalanceSettings> queryAllByType(Integer balanceType);

    int clearAllByType(Integer balanceType);

    int insertBatch(List<OrgBalanceSettings> list);
}