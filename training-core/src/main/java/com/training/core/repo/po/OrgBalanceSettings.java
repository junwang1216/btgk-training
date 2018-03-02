package com.training.core.repo.po;

import java.io.Serializable;

/**
 * @author 
 */
public class OrgBalanceSettings implements Serializable {
    private Integer id;

    /**
     * 收支项名称
     */
    private String balanceName;

    /**
     * 类型 1：收入 2：支出
     */
    private Integer balanceType;

    /**
     * 状态 1：可用 2：不可用
     */
    private Integer status;

    /**
     * 优先级1-n
     */
    private Integer priority;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBalanceName() {
        return balanceName;
    }

    public void setBalanceName(String balanceName) {
        this.balanceName = balanceName;
    }

    public Integer getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(Integer balanceType) {
        this.balanceType = balanceType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
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
}