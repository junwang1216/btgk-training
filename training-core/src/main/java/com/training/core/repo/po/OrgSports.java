package com.training.core.repo.po;

import java.io.Serializable;

/**
 * @author 
 */
public class OrgSports implements Serializable {
    private Integer id;

    /**
     * 机构编号
     */
    private Integer orgId;

    /**
     * 授课项目名称
     */
    private String sportName;

    /**
     * 英文名称
     */
    private String sportNameEn;

    /**
     * 授课项目图标
     */
    private String sportIcon;

    /**
     * 是否支持 1：支持 2：不支持
     */
    private Integer status;

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

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getSportNameEn() {
        return sportNameEn;
    }

    public void setSportNameEn(String sportNameEn) {
        this.sportNameEn = sportNameEn;
    }

    public String getSportIcon() {
        return sportIcon;
    }

    public void setSportIcon(String sportIcon) {
        this.sportIcon = sportIcon;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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