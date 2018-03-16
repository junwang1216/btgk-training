package com.training.in.request;

/**
 * Created by wangjun on 2018/3/15.
 */
public class OrgCourseRequest {
    /** 课程信息 **/
    private String courseName;

    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /** 项目信息 **/
    private int sportId;

    public int getSportId() {
        return sportId;
    }
    public void setSportId(int sportId) {
        this.sportId = sportId;
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
