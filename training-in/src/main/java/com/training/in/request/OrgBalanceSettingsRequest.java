package com.training.in.request;

import com.training.core.repo.po.OrgBalanceSettings;

import java.util.List;

/**
 * Created by wangjun on 2017/12/28.
 */
public class OrgBalanceSettingsRequest {

    private int balanceType;
    private List<OrgBalanceSettings> orgBalanceSettingsList;

    public int getBalanceType() {
        return balanceType;
    }
    public void setBalanceType(int balanceType) {
        this.balanceType = balanceType;
    }

    public List<OrgBalanceSettings> getOrgBalanceSettingsList() {
        return orgBalanceSettingsList;
    }
    public void setOrgBalanceSettingsList(List<OrgBalanceSettings> orgBalanceSettingsList) {
        this.orgBalanceSettingsList = orgBalanceSettingsList;
    }

}
