package com.training.in.request;

import com.training.core.repo.po.OrgCoaches;

/**
 * Created by wangjun on 2017/12/28.
 */
public class OrgCoachesRequest {

    private OrgCoaches orgCoaches;
    private String roleId;
    private String sportId;
    private String venueId;

    public OrgCoaches getOrgCoaches() {
        return orgCoaches;
    }
    public void setOrgCoaches(OrgCoaches orgCoaches) {
        this.orgCoaches = orgCoaches;
    }

    public String getRoleId() {
        return roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public String[] getRoleIds() {
        return this.roleId.split(",");
    }

    public String getSportId() {
        return sportId;
    }
    public void setSportId(String sportId) {
        this.sportId = sportId;
    }
    public String[] getSportIds() {
        return this.sportId.split(",");
    }

    public String getVenueId() {
        return venueId;
    }
    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }
    public String[] getVenueIds() {
        return this.venueId.split(",");
    }

}
