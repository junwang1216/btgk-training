package com.training.uk.response;

import com.training.core.common.enums.BusinessChannelTypeEnum;
import com.training.core.common.enums.BusinessTypeEnum;

import java.util.List;

/**
 * Created by wangjun on 2018/5/4.
 */
public class OrgFinanceDataResponse {

    // 业务编号
    private String businessNo;
    // 业务日期
    private String businessDate;
    // 业务类型 BusinessTypeEnum
    private Integer businessType;

    public String getBusinessNo() {
        return businessNo;
    }
    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getBusinessDate() {
        return businessDate;
    }
    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate;
    }

    public Integer getBusinessType() {
        return businessType;
    }
    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getBusinessTitle() {
        return BusinessTypeEnum.forValue(businessType).getDesc();
    }

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

    // 渠道 BusinessChannelTypeEnum
    private Integer channelType;

    public Integer getChannelType() {
        return channelType;
    }
    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    public String getChannelName() {
        return BusinessChannelTypeEnum.forValue(channelType).getDesc();
    }

    // 流水
    private Integer pipelineValue = 0;
    private Integer pipelineTarget = 0;
    private Integer pipelineChallenge = 0;

    public Integer getPipelineValue() {
        return pipelineValue;
    }
    public void setPipelineValue(Integer pipelineValue) {
        this.pipelineValue = pipelineValue;
    }

    public Integer getPipelineTarget() {
        return pipelineTarget;
    }
    public void setPipelineTarget(Integer pipelineTarget) {
        this.pipelineTarget = pipelineTarget;
    }

    public Double getPipelineTargetPercent() {
        if (pipelineTarget == 0) {
            return 0.00;
        }
        return (double)pipelineValue / pipelineTarget;
    }

    public Integer getPipelineChallenge() {
        return pipelineChallenge;
    }
    public void setPipelineChallenge(Integer pipelineChallenge) {
        this.pipelineChallenge = pipelineChallenge;
    }

    public Double getPipelineChallengePercent() {
        if (pipelineChallenge == 0) {
            return 0.00;
        }
        return (double)pipelineValue / pipelineChallenge;
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

    // 体验人数
    private Integer accessCount = 0;
    private Integer businessCount = 0;

    public Integer getAccessCount() {
        return accessCount;
    }
    public void setAccessCount(Integer accessCount) {
        this.accessCount = accessCount;
    }

    public Integer getBusinessCount() {
        return businessCount;
    }
    public void setBusinessCount(Integer businessCount) {
        this.businessCount = businessCount;
    }

    public Double getBusinessCountPercent() {
        if (accessCount == 0) {
            return 0.00;
        }
        return (double)businessCount / accessCount;
    }

    // 空/忙时段
    private Integer nullCount = 0;
    private Integer nullTotalCount = 0;
    private Integer hotCount = 0;
    private Integer hotTotalCount = 0;

    public Integer getNullCount() {
        return nullCount;
    }
    public void setNullCount(Integer nullCount) {
        this.nullCount = nullCount;
    }

    public Integer getNullTotalCount() {
        return nullTotalCount;
    }
    public void setNullTotalCount(Integer nullTotalCount) {
        this.nullTotalCount = nullTotalCount;
    }

    public Double getNullCountPercent() {
        if (nullTotalCount == 0) {
            return 0.00;
        }
        return (double)nullCount / nullTotalCount;
    }

    public Integer getHotTotalCount() {
        return hotTotalCount;
    }
    public void setHotTotalCount(Integer hotTotalCount) {
        this.hotTotalCount = hotTotalCount;
    }

    public Integer getHotCount() {
        return hotCount;
    }
    public void setHotCount(Integer hotCount) {
        this.hotCount = hotCount;
    }

    public Double getHotCountPercent() {
        if (hotTotalCount == 0) {
            return 0.00;
        }
        return (double)hotCount / hotTotalCount;
    }


    // 渠道数据
    private List<OrgFinanceDataResponse> orgFinanceDataResponseChannelList;

    public List<OrgFinanceDataResponse> getOrgFinanceDataResponseChannelList() {
        return orgFinanceDataResponseChannelList;
    }
    public void setOrgFinanceDataResponseChannelList(List<OrgFinanceDataResponse> orgFinanceDataResponseChannelList) {
        this.orgFinanceDataResponseChannelList = orgFinanceDataResponseChannelList;
    }
}
