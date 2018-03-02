package com.training.core.repo.po;

import java.io.Serializable;

/**
 * @author 
 */
public class OrgStudents implements Serializable {
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
     * 出生日期
     */
    private String birthday;

    /**
     * 性别 1：男 2：女
     */
    private Integer sex;

    /**
     * 身高（cm）
     */
    private Integer height;

    /**
     * 体重（kg）
     */
    private Integer weight;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 最进一次登录时间
     */
    private String lastLoginTime;

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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
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

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}