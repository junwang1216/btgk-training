package com.training.core.repo.po;

import java.io.Serializable;

/**
 * @author 
 */
public class OrgAttendance implements Serializable {
    private Integer id;

    /**
     * 签到时间
     */
    private String inDate;

    /**
     * 签到方 1：教练  2：学员
     */
    private Integer inRole;

    /**
     * 签到人ID
     */
    private Integer inUserId;

    /**
     * 签到班级ID
     */
    private Integer inClassID;

    /**
     * 上课排期ID
     */
    private Integer inScheduleId;

    /**
     * 创建时间
     */
    private String createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public Integer getInRole() {
        return inRole;
    }

    public void setInRole(Integer inRole) {
        this.inRole = inRole;
    }

    public Integer getInUserId() {
        return inUserId;
    }

    public void setInUserId(Integer inUserId) {
        this.inUserId = inUserId;
    }

    public Integer getInClassID() {
        return inClassID;
    }

    public void setInClassID(Integer inClassID) {
        this.inClassID = inClassID;
    }

    public Integer getInScheduleId() {
        return inScheduleId;
    }

    public void setInScheduleId(Integer inScheduleId) {
        this.inScheduleId = inScheduleId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}