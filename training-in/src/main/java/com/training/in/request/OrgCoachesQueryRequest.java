package com.training.in.request;

/**
 * Created by wangjun on 2018/3/15.
 */
public class OrgCoachesQueryRequest {
    /** 教练信息 **/
    private String realName;
    private String mobile;
    private String idCard;

    public String getRealName() {
        return realName == null || realName.equals("") ? null : realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile == null || mobile.equals("") ? null : mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdCard() {
        return idCard == null || idCard.equals("") ? null : idCard;
    }
    public void setIdCard(String idCard) {
        this.idCard = idCard;
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
