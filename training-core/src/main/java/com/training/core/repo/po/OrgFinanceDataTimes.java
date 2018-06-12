package com.training.core.repo.po;

import java.io.Serializable;

/**
 * @author 
 */
public class OrgFinanceDataTimes implements Serializable {
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
     * 闲时占用
     */
    private Integer nullCount;

    /**
     * 闲时总
     */
    private Integer nullTotalCount;

    /**
     * 忙时占用
     */
    private Integer hotCount;

    /**
     * 忙时总
     */
    private Integer hotTotalCount;

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

    public Integer getHotCount() {
        return hotCount;
    }

    public void setHotCount(Integer hotCount) {
        this.hotCount = hotCount;
    }

    public Integer getHotTotalCount() {
        return hotTotalCount;
    }

    public void setHotTotalCount(Integer hotTotalCount) {
        this.hotTotalCount = hotTotalCount;
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