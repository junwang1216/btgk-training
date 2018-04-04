package com.training.in.response;

import java.io.Serializable;

public class OrgBalanceDataResponse implements Serializable {

    /**
     * 费用流水编号
     */
    private String balanceNo;

    /**
     * 费用类型
     */
    private Integer balanceType;

    /**
     * 费用支付类型
     */
    private Integer balancePayType;

    /**
     * 费用金额
     */
    private Integer balanceAmount;

    /**
     * 产生费用的用户
     */
    private Integer balanceUserId;

    /**
     * 产生费用的时间
     */
    private String balanceTime;

    /**
     * 收入支出来源
     */
    private Integer balanceSource;

    /**
     * 审核人
     */
    private Integer reviewId;

    /**
     * 审核时间
     */
    private String reviewTime;

    /**
     * 操作人编号
     */
    private Integer operateId;

    public String getBalanceNo() {
        return balanceNo;
    }

    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    public Integer getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(Integer balanceType) {
        this.balanceType = balanceType;
    }

    public Integer getBalancePayType() {
        return balancePayType;
    }

    public void setBalancePayType(Integer balancePayType) {
        this.balancePayType = balancePayType;
    }

    public Integer getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Integer balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Integer getBalanceUserId() {
        return balanceUserId;
    }

    public void setBalanceUserId(Integer balanceUserId) {
        this.balanceUserId = balanceUserId;
    }

    public String getBalanceTime() {
        return balanceTime;
    }

    public void setBalanceTime(String balanceTime) {
        this.balanceTime = balanceTime;
    }

    public Integer getBalanceSource() {
        return balanceSource;
    }

    public void setBalanceSource(Integer balanceSource) {
        this.balanceSource = balanceSource;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
    }

    public Integer getOperateId() {
        return operateId;
    }

    public void setOperateId(Integer operateId) {
        this.operateId = operateId;
    }

}