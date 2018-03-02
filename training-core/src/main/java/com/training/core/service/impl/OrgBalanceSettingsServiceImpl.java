package com.training.core.service.impl;

import com.training.core.repo.OrgBalanceSettingsMapper;
import com.training.core.repo.po.OrgBalanceSettings;
import com.training.core.service.OrgBalanceSettingsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgBalanceSettingsServiceImpl implements OrgBalanceSettingsService {

    @Resource
    private OrgBalanceSettingsMapper orgBalanceSettingsMapper;

    @Override
    public int addOrgBalanceSettingsBatch(List<OrgBalanceSettings> orgBalanceSettingsList) {
        return orgBalanceSettingsMapper.insertBatch(orgBalanceSettingsList);
    }

    @Override
    public int saveOrgBalanceSettings(OrgBalanceSettings orgBalanceSettings) {
        return orgBalanceSettingsMapper.updateByPrimaryKey(orgBalanceSettings);
    }

    @Override
    public List<OrgBalanceSettings> queryOrgBalanceSettingsList(int balanceType) {
        return orgBalanceSettingsMapper.queryAllByType(balanceType);
    }

    @Override
    public int clearAllByBalanceType(int balanceType) {
        return orgBalanceSettingsMapper.clearAllByType(balanceType);
    }
}


