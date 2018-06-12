package com.training.core.repo.po;

import java.io.Serializable;

/**
 * @author 
 */
public class OrgFinanceDataIncome implements Serializable {
    private Integer id;

    /**
     * 流水号
     */
    private String businessNo;

    /**
     * 流水日期
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
     * 基地员工
     */
    private Integer userId;

    /**
     * 收入类型
     */
    private String incomeType;

    /**
     * 确认收入值
     */
    private Integer incomeValue;

    /**
     * 收入单价
     */
    private Integer incomePerValue;

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

    public String getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }

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