package com.training.in.response;

import com.training.core.repo.po.*;

import java.io.Serializable;

public class OrgClassTestResponse implements Serializable {

    private OrgClass orgClass;

    private OrgCoaches orgCoaches;

    private OrgClassTest orgClassTest;

    private OrgClassSchedule orgClassSchedule;

    private int orgClassStudentsLength;

    public OrgClass getOrgClass() {
        return orgClass;
    }
    public void setOrgClass(OrgClass orgClass) {
        this.orgClass = orgClass;
    }

    public OrgCoaches getOrgCoaches() {
        return orgCoaches;
    }
    public void setOrgCoaches(OrgCoaches orgCoaches) {
        this.orgCoaches = orgCoaches;
    }

    public OrgClassTest getOrgClassTest() {
        return orgClassTest;
    }
    public void setOrgClassTest(OrgClassTest orgClassTest) {
        this.orgClassTest = orgClassTest;
    }

    public OrgClassSchedule getOrgClassSchedule() {
        return orgClassSchedule;
    }
    public void setOrgClassSchedule(OrgClassSchedule orgClassSchedule) {
        this.orgClassSchedule = orgClassSchedule;
    }

    public int getOrgClassStudentsLength() {
        return orgClassStudentsLength;
    }
    public void setOrgClassStudentsLength(int orgClassStudentsLength) {
        this.orgClassStudentsLength = orgClassStudentsLength;
    }

}