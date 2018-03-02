package com.training.core.repo.po;

import java.io.Serializable;

/**
 * @author 
 */
public class OrgDatabaseBackup implements Serializable {
    private Integer id;

    /**
     * 备份编号
     */
    private String bkNo;

    /**
     * 备份文件名称
     */
    private String bkName;

    /**
     * 备份路径
     */
    private String bkPath;

    /**
     * 备份文件大小
     */
    private Integer bkSize;

    /**
     * 备份人
     */
    private Integer operatorId;

    /**
     * 备份时间
     */
    private String createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBkNo() {
        return bkNo;
    }

    public void setBkNo(String bkNo) {
        this.bkNo = bkNo;
    }

    public String getBkName() {
        return bkName;
    }

    public void setBkName(String bkName) {
        this.bkName = bkName;
    }

    public String getBkPath() {
        return bkPath;
    }

    public void setBkPath(String bkPath) {
        this.bkPath = bkPath;
    }

    public Integer getBkSize() {
        return bkSize;
    }

    public void setBkSize(Integer bkSize) {
        this.bkSize = bkSize;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}