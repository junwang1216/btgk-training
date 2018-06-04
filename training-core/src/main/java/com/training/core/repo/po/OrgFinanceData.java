package com.training.core.repo.po;

import java.io.Serializable;

/**
 * @author 
 */
public class OrgFinanceData implements Serializable {
    /**
     * 业务编号
     */
    private Integer id;

    /**
     * 业务编号
     */
    private String businessNo;

    /**
     * 业务日期
     */
    private String businessDate;

    /**
     * 业务类型
     */
    private Integer businessType;

    /**
     * 所属基地
     */
    private Integer venueId;

    /**
     * 真实姓名
     */
    private Integer userId;

    /**
     * 渠道来源
     */
    private Integer channelType;

    /**
     * 流水完成值
     */
    private Integer pipelineValue = 0;

    /**
     * 确认收入完成值
     */
    private Integer incomeValue = 0;

    /**
     * 在册人数
     */
    private Integer registerCount = 0;

    /**
     * 到课人数
     */
    private Integer classCount = 0;

    /**
     * 体验数
     */
    private Integer accessCount = 0;

    /**
     * 成交数
     */
    private Integer businessCount = 0;

    /**
     * 闲时段占用数
     */
    private Integer nullCount = 0;

    /**
     * 闲时段总数
     */
    private Integer nullTotalCount = 0;

    /**
     * 忙时段总数
     */
    private Integer hotTotalCount = 0;

    /**
     * 忙时段占用数
     */
    private Integer hotCount = 0;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 操作人
     */
    private Integer operatorId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getVenueId() {
        return venueId;
    }

    public void setVenueId(Integer venueId) {
        this.venueId = venueId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getChannelType() {
        return channelType;
    }

    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    public Integer getPipelineValue() {
        return pipelineValue;
    }

    public void setPipelineValue(Integer pipelineValue) {
        this.pipelineValue = pipelineValue;
    }

    public Integer getIncomeValue() {
        return incomeValue;
    }

    public void setIncomeValue(Integer incomeValue) {
        this.incomeValue = incomeValue;
    }

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }
}