package com.training.core.repo.po;

import java.io.Serializable;

/**
 * @author 
 */
public class OrgClass implements Serializable {
    private Integer id;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 班级价格
     */
    private Integer classPrice;

    /**
     * 上课扣除课时
     */
    private Integer classHours;

    /**
     * 所属场馆
     */
    private Integer venueId;

    /**
     * 授课内容ID
     */
    private Integer courseId;

    /**
     * 执教教练ID
     */
    private Integer coachId;

    /**
     * 状态 1：上课中 2：已完结
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getClassPrice() {
        return classPrice;
    }

    public void setClassPrice(Integer classPrice) {
        this.classPrice = classPrice;
    }

    public Integer getClassHours() {
        return classHours;
    }

    public void setClassHours(Integer classHours) {
        this.classHours = classHours;
    }

    public Integer getVenueId() {
        return venueId;
    }

    public void setVenueId(Integer venueId) {
        this.venueId = venueId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getCoachId() {
        return coachId;
    }

    public void setCoachId(Integer coachId) {
        this.coachId = coachId;
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