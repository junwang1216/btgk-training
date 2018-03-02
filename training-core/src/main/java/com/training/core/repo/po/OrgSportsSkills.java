package com.training.core.repo.po;

import java.io.Serializable;

/**
 * @author 
 */
public class OrgSportsSkills implements Serializable {
    private Integer id;

    /**
     * 技能名称
     */
    private String skillName;

    /**
     * 授课项目ID
     */
    private Integer sportId;

    /**
     * 技能描述
     */
    private String skillNote;

    /**
     * 最高值 10或者100
     */
    private Integer maxValue;

    /**
     * 是否显示 1：显示 2：显示
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

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public Integer getSportId() {
        return sportId;
    }

    public void setSportId(Integer sportId) {
        this.sportId = sportId;
    }

    public String getSkillNote() {
        return skillNote;
    }

    public void setSkillNote(String skillNote) {
        this.skillNote = skillNote;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
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