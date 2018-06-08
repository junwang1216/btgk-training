package com.training.uk.request;

/**
 * @author 
 */
public class OrgOperatorRequest {
    private Integer id;
    private String oldPassword;
    private String newPassword;
    private String againPassword;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getOldPassword() {
        return oldPassword;
    }
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getAgainPassword() {
        return againPassword;
    }
    public void setAgainPassword(String againPassword) {
        this.againPassword = againPassword;
    }
}