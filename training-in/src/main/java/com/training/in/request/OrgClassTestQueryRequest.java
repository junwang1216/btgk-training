package com.training.in.request;

/**
 * Created by wangjun on 2018/3/15.
 */
public class OrgClassTestQueryRequest {
    /** 评测信息 **/
    private Integer classId;
    private int status;

    public Integer getClassId() {
        return classId;
    }
    public void setClassId(Integer classId) {
        this.classId = classId;
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
