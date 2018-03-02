package com.training.core.repo.po;

import java.io.Serializable;

/**
 * @author 
 */
public class OrgSystemLog implements Serializable {
    private Integer id;

    /**
     * 机构编号
     */
    private Integer orgId;

    /**
     * 日志编号
     */
    private String logNo;

    /**
     * 日志类型
     */
    private Integer logType;

    /**
     * 日志类型
     */
    private String logContent;

    /**
     * IP地址
     */
    private String ip;

    /**
     * MAC地址
     */
    private String mac;

    /**
     * 创建时间
     */
    private String createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogNo() {
        return logNo;
    }

    public void setLogNo(String logNo) {
        this.logNo = logNo;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}