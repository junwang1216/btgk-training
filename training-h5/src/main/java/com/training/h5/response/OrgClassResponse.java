package com.training.h5.response;

import com.training.core.repo.po.*;

import java.io.Serializable;

public class OrgClassResponse implements Serializable {

    private OrgClass orgClass;

    private OrgVenues orgVenues;

    private OrgCoaches orgCoaches;

    private OrgCourses orgCourses;

    private OrgSports orgSports;

    private int orgClassStudentsLength;

    public OrgClass getOrgClass() {
        return orgClass;
    }
    public void setOrgClass(OrgClass orgClass) {
        this.orgClass = orgClass;
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

    public OrgSports getOrgSports() {
        return orgSports;
    }
    public void setOrgSports(OrgSports orgSports) {
        this.orgSports = orgSports;
    }

    public OrgCourses getOrgCourses() {
        return orgCourses;
    }
    public void setOrgCourses(OrgCourses orgCourses) {
        this.orgCourses = orgCourses;
    }

    public int getOrgClassStudentsLength() {
        return orgClassStudentsLength;
    }
    public void setOrgClassStudentsLength(int orgClassStudentsLength) {
        this.orgClassStudentsLength = orgClassStudentsLength;
    }

}