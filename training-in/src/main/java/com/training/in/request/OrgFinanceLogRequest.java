package com.training.in.request;

/**
 * Created by wangjun on 2018/5/2.
 */
public class OrgFinanceLogRequest {
    /** 检索参数 **/
    private int busType;
    private int goalType;
    private int venueId;
    private int year;
    private int userId;

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

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
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
