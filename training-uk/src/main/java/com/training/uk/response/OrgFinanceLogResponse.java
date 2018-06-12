package com.training.uk.response;

import com.training.core.common.enums.BusinessChannelTypeEnum;
import com.training.core.common.enums.BusinessTypeEnum;

import java.util.List;

/**
 * Created by wangjun on 2018/5/4.
 */
public class OrgFinanceLogResponse {

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

    // 渠道
    private String channelName;

    public String getChannelName() {
        return channelName;
    }
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    // 收入
    private String incomeType;

    public String getIncomeType() {
        return incomeType;
    }
    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }

    // 流水
    private Integer pipelineValue = 0;

    public Integer getPipelineValue() {
        return pipelineValue;
    }
    public void setPipelineValue(Integer pipelineValue) {
        this.pipelineValue = pipelineValue;
    }

    // 确认收入
    private Integer incomePerValue = 0;
    private Integer incomeValue = 0;

    public Integer getIncomeValue() {
        return incomeValue;
    }
    public void setIncomeValue(Integer incomeValue) {
        this.incomeValue = incomeValue;
    }

    public Integer getIncomePerValue() {
        return incomePerValue;
    }
    public void setIncomePerValue(Integer incomePerValue) {
        this.incomePerValue = incomePerValue;
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
}
