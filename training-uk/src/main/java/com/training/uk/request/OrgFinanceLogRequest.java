package com.training.uk.request;

/**
 * Created by wangjun on 2018/5/2.
 */
public class OrgFinanceLogRequest {
    /** 检索参数 **/
    private int goalId;
    private int busType;
    private int goalType;
    private int venueId;
    private int year;
    private int month;
    private int userId;

    public int getGoalId() {
        return goalId;
    }
    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }

    public int getBusType() {
        return busType;
    }
    public void setBusType(int busType) {
        this.busType = busType;
    }

    public int getGoalType() {
        return goalType;
    }
    public void setGoalType(int goalType) {
        this.goalType = goalType;
    }

    public int getVenueId() {
        return venueId;
    }
    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** 时间 **/
    private String startTime;
    private String endTime;
    private String typeTime;

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

    public String getTypeTime() {
        return typeTime;
    }
    public void setTypeTime(String typeTime) {
        this.typeTime = typeTime;
    }

    /** 分页信息 **/
    private int page = 1;
    private int pageSize = 10;

    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
