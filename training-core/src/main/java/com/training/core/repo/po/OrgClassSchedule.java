package com.training.core.repo.po;

import java.io.Serializable;

/**
 * @author 
 */
public class OrgClassSchedule implements Serializable {
    private Integer id;

    /**
     * 班级Id
     */
    private Integer classId;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 上课日期
     */
    private String classDate;

    /**
     * 上课开始日期
     */
    private String startDate;

    /**
     * 上课结束日期
     */
    private String endDate;

    /**
     * 上课星期
     */
    private String classWeek;

    /**
     * 上课教练
     */
    private Integer coachId;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 签到
     */
    private Integer hasSigned;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getClassDate() {
        return classDate;
    }

    public void setClassDate(String classDate) {
        this.classDate = classDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getClassWeek() {
        return classWeek;
    }

    public void setClassWeek(String classWeek) {
        this.classWeek = classWeek;
    }

    public Integer getCoachId() {
        return coachId;
    }

    public void setCoachId(Integer coachId) {
        this.coachId = coachId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getHasSigned() {
        return hasSigned;
    }

    public void setHasSigned(Integer hasSigned) {
        this.hasSigned = hasSigned;
    }
}