package com.training.in.response;

import com.training.core.repo.po.*;

import java.io.Serializable;

public class OrgOrdersResponse implements Serializable {

    private OrgOrders orgOrders;

    private OrgOperator orgOperator;

    private OrgStudents orgStudents;

    public OrgOrders getOrgOrders() {
        return orgOrders;
    }
    public void setOrgOrders(OrgOrders orgOrders) {
        this.orgOrders = orgOrders;
    }

    public OrgOperator getOrgOperator() {
        return orgOperator;
    }
    public void setOrgOperator(OrgOperator orgOperator) {
        this.orgOperator = orgOperator;
    }

    public OrgStudents getOrgStudents() {
        return orgStudents;
    }
    public void setOrgStudents(OrgStudents orgStudents) {
        this.orgStudents = orgStudents;
    }

}