package com.training.uk.response;

/**
 * Created by wangjun on 2018/5/4.
 */
public class OrgFinanceDataChannelResponse {

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

    private String channelName;

    public String getChannelName() {
        return channelName;
    }
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    // 流水
    private Integer pipelineValue = 0;
    private Integer pipelineTarget = 0;
    private Integer pipelineChallenge = 0;

    public Integer getPipelineValue() {
        return pipelineValue;
    }
    public void setPipelineValue(Integer pipelineValue) {
        this.pipelineValue = pipelineValue;
    }

    public Integer getPipelineTarget() {
        return pipelineTarget;
    }
    public void setPipelineTarget(Integer pipelineTarget) {
        this.pipelineTarget = pipelineTarget;
    }

    public Double getPipelineTargetPercent() {
        if (pipelineTarget == 0) {
            return 0.00;
        }
        return (double)pipelineValue / pipelineTarget;
    }

    public Integer getPipelineChallenge() {
        return pipelineChallenge;
    }
    public void setPipelineChallenge(Integer pipelineChallenge) {
        this.pipelineChallenge = pipelineChallenge;
    }

    public Double getPipelineChallengePercent() {
        if (pipelineChallenge == 0) {
            return 0.00;
        }
        return (double)pipelineValue / pipelineChallenge;
    }

    // 体验人数
    private Integer accessCount = 0;
    private Integer businessCount = 0;

    public Integer getAccessCount() {
        return accessCount;
    }
    public void setAccessCount(Integer accessCount) {
        this.accessCount = accessCount;
    }

    public Integer getBusinessCount() {
        return businessCount;
    }
    public void setBusinessCount(Integer businessCount) {
        this.businessCount = businessCount;
    }

    public Double getBusinessCountPercent() {
        if (accessCount == 0) {
            return 0.00;
        }
        return (double)businessCount / accessCount;
    }
}
