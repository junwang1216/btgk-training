package com.training.core.service;

import com.training.core.repo.po.OrgBalanceSettings;

import java.util.List;

public interface OrgBalanceSettingsService {

    int addOrgBalanceSettingsBatch(List<OrgBalanceSettings> orgBalanceSettingsList);

    int saveOrgBalanceSettings(OrgBalanceSettings orgBalanceSettings);

    List<OrgBalanceSettings> queryOrgBalanceSettingsList(int balanceType);

    int clearAllByBalanceType(int balanceType);

}
