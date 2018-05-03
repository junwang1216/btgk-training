package com.training.core.repo.po;

import java.io.Serializable;

/**
 * @author 
 */
public class OrgFinance implements Serializable {
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
    private Integer baseType;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 渠道来源
     */
    private Integer channelType;

    /**
     * 流水情况的完成值
     */
    private Integer pipelineValue;

    /**
     * 流水情况的目标值
     */
    private Integer pipelineTarget;

    /**
     * 流水情况的挑战值
     */
    private Integer pipelineChallenge;

    /**
     * 确认收入的完成值
     */
    private Integer incomeValue;

    /**
     * 确认收入的目标值
     */
    private Integer incomeTarget;

    /**
     * 确认收入的挑战值
     */
    private Integer incomeChallenge;

    /**
     * 在册人数
     */
    private Integer registerCount;

    /**
     * 到课人数
     */
    private Integer classCount;

    /**
     * 体验数
     */
    private Integer accessCount;

    /**
     * 成交数
     */
    private Integer businessCount;

    /**
     * 闲时段占用数
     */
    private Integer nullCount;

    /**
     * 闲时段总数
     */
    private Integer nullTotalCount;

    /**
     * 忙时段总数
     */
    private Integer hotTotalCount;

    /**
     * 忙时段占用数
     */
    private Integer hotCount;

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

    public Integer getBaseType() {
        return baseType;
    }

    public void setBaseType(Integer baseType) {
        this.baseType = baseType;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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

    public Integer getPipelineTarget() {
        return pipelineTarget;
    }

    public void setPipelineTarget(Integer pipelineTarget) {
        this.pipelineTarget = pipelineTarget;
    }

    public Integer getPipelineChallenge() {
        return pipelineChallenge;
    }

    public void setPipelineChallenge(Integer pipelineChallenge) {
        this.pipelineChallenge = pipelineChallenge;
    }

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

    public Integer getIncomeChallenge() {
        return incomeChallenge;
    }

    public void setIncomeChallenge(Integer incomeChallenge) {
        this.incomeChallenge = incomeChallenge;
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