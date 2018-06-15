package com.training.uk.response;

import com.training.core.common.enums.BusinessTypeEnum;

import java.util.List;

/**
 * Created by wangjun on 2018/5/4.
 */
public class OrgFinanceDataTimesResponse {

    // 场馆
    private Integer venueId;
    private String venueName;

    public Integer getVenueId() {
        return venueId;
    }
    public void setVenueId(Integer venueId) {
        this.venueId = venueId;
    }

    public String getVenueName() {
        return venueName;
    }
    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    // 用户
    private Integer userId;
    private String realName;

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }

    // 空/忙时段
    private Integer nullCount = 0;
    private Integer nullTotalCount = 0;
    private Integer hotCount = 0;
    private Integer hotTotalCount = 0;

    public Integer getNullCount() {
        return nullCount;
    }
    public void setNullCount(Integer nullCount) {
        this.nullCount = nullCount;
    }

    public Integer getNullTotalCount() {
        return nullTotalCount;
    }
    public void setNullTotalCount(Integer nullTotalCount) {
        this.nullTotalCount = nullTotalCount;
    }

    public Double getNullCountPercent() {
        if (nullTotalCount == 0) {
            return 0.00;
        }
        return (double)nullCount / nullTotalCount;
    }

    public Integer getHotTotalCount() {
        return hotTotalCount;
    }
    public void setHotTotalCount(Integer hotTotalCount) {
        this.hotTotalCount = hotTotalCount;
    }

    public Integer getHotCount() {
        return hotCount;
    }
    public void setHotCount(Integer hotCount) {
        this.hotCount = hotCount;
    }

    public Double getHotCountPercent() {
        if (hotTotalCount == 0) {
            return 0.00;
        }
        return (double)hotCount / hotTotalCount;
    }
}
