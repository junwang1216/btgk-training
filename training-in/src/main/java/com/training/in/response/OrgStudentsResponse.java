package com.training.in.response;

import com.training.core.repo.po.*;

import java.io.Serializable;

public class OrgStudentsResponse implements Serializable {

    private OrgClass orgClass;

    private OrgStudents orgStudents;

    private OrgAttendance orgAttendance;

    private OrgAttendance orgAttendanceCoach;

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

    public OrgAttendance getOrgAttendance() {
        return orgAttendance;
    }
    public void setOrgAttendance(OrgAttendance orgAttendance) {
        this.orgAttendance = orgAttendance;
    }

    public OrgAttendance getOrgAttendanceCoach() {
        return orgAttendanceCoach;
    }
    public void setOrgAttendanceCoach(OrgAttendance orgAttendanceCoach) {
        this.orgAttendanceCoach = orgAttendanceCoach;
    }

}