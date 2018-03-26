package com.training.in.request;

/**
 * Created by wangjun on 2018/3/15.
 */
public class OrgSystemLogRequest {
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

    /** 条件检索 **/
    // TODO
}
