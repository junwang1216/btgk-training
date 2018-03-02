package com.training.core.repo.po;

import java.io.Serializable;

/**
 * @author 
 */
public class OrgCoaches implements Serializable {
    private Integer id;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 联系邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 课程状态
     */
    private Integer status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobileSensitive() {
        return mobile.replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1****$3");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdCardSensitive() {
        return idCard.replaceAll("(\\d{6})(\\d{8})(\\d{4})", "$1********$3");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}