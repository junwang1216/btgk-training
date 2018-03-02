package com.training.in.response;

import com.training.core.repo.po.*;

import java.io.Serializable;

public class OrgAttendanceResponse implements Serializable {

    private OrgAttendance orgAttendance;

    private OrgClass orgClass;

    private OrgStudents orgStudents;

    private OrgVenues orgVenues;

    private OrgCoaches orgCoaches;

    public OrgAttendance getOrgAttendance() {
        return orgAttendance;
    }
    public void setOrgAttendance(OrgAttendance orgAttendance) {
        this.orgAttendance = orgAttendance;
    }

    public OrgClass getOrgClass() {
        return orgClass;
    }
    public void setOrgClass(OrgClass orgClass) {
        this.orgClass = orgClass;
    }

    public OrgStudents getOrgStudents() {
        return orgStudents;
    }
    public void setOrgStudents(OrgStudents orgStudents) {
        this.orgStudents = orgStudents;
    }

    public OrgVenues getOrgVenues() {
        return orgVenues;
    }
    public void setOrgVenues(OrgVenues orgVenues) {
        this.orgVenues = orgVenues;
    }

    public OrgCoaches getOrgCoaches() {
        return orgCoaches;
    }
    public void setOrgCoaches(OrgCoaches orgCoaches) {
        this.orgCoaches = orgCoaches;
    }

}