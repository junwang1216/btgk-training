package com.training.in.request;

/**
 * Created by wangjun on 2018/3/15.
 */
public class OrgClassQueryRequest {
    /** 班级信息 **/
    private String className;
    private int status;

    public String getClassName() {
        return (className == null || className.equals("")) ? null : className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
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
