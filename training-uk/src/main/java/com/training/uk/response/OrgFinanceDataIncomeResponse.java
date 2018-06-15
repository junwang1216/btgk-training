package com.training.uk.response;

import com.training.core.common.enums.BusinessTypeEnum;

import java.util.List;

/**
 * Created by wangjun on 2018/5/4.
 */
public class OrgFinanceDataIncomeResponse {

    // 场馆
    private Integer venueId;
    private String venueName;

    public Integer getVenueId() {
        return venueId;
    }
    public void setVenueId(Integer venueId) {
        this.venueId = venueId;
    }

    public String getVenueName() {
        return venueName;
    }
    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    // 用户
    private Integer userId;
    private String realName;

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }

    private String incomeType;

    public String getIncomeType() {
        return incomeType;
    }
    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }

    // 确认收入
    private Integer incomeValue = 0;
    private Integer incomeTarget = 0;
    private Integer incomeChallenge = 0;

    public Integer getIncomeValue() {
        return incomeValue;
    }
    public void setIncomeValue(Integer incomeValue) {
        this.incomeValue = incomeValue;
    }

    public Integer getIncomeTarget() {
        return incomeTarget;
    }
    public void setIncomeTarget(Integer incomeTarget) {
        this.incomeTarget = incomeTarget;
    }

    public Double getIncomeTargetPercent() {
        if (incomeTarget == 0) {
            return 0.00;
        }
        return (double)incomeValue / incomeTarget;
    }

    public Integer getIncomeChallenge() {
        return incomeChallenge;
    }
    public void setIncomeChallenge(Integer incomeChallenge) {
        this.incomeChallenge = incomeChallenge;
    }

    public Double getIncomeChallengePercent() {
        if (incomeChallenge == 0) {
            return 0.00;
        }
        return (double)incomeValue / incomeChallenge;
    }

    // 培训人数
    private Integer registerCount = 0;
    private Integer classCount = 0;

    public Integer getRegisterCount() {
        return registerCount;
    }
    public void setRegisterCount(Integer registerCount) {
        this.registerCount = registerCount;
    }

    public Integer getClassCount() {
        return classCount;
    }
    public void setClassCount(Integer classCount) {
        this.classCount = classCount;
    }

    public Double getClassCountPercent() {
        if (registerCount == 0) {
            return 0.00;
        }
        return (double)classCount / registerCount;
    }
}
