package com.training.in.request;

import com.training.core.repo.po.OrgClassSchedule;

import java.util.List;

/**
 * Created by wangjun on 2017/12/28.
 */
public class OrgClassScheduleRequest {

    private int classId;
    private List<OrgClassSchedule> orgClassScheduleList;

    public int getClassId() {
        return classId;
    }
    public void setClassId(int classId) {
        this.classId = classId;
    }

    public List<OrgClassSchedule> getOrgClassScheduleList() {
        return orgClassScheduleList;
    }
    public void setOrgClassScheduleList(List<OrgClassSchedule> orgClassScheduleList) {
        this.orgClassScheduleList = orgClassScheduleList;
    }

}
