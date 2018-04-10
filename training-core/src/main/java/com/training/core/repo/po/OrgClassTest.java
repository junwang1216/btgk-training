package com.training.core.repo.po;

import java.io.Serializable;

/**
 * @author 
 */
public class OrgClassTest implements Serializable {
    private Integer id;

    /**
     * 评测名称
     */
    private String testName;

    /**
     * 评测描述
     */
    private String testNote;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 评测班级
     */
    private Integer classId;

    /**
     * 评测时间
     */
    private Integer classDate;

    /**
     * 评测状态
     */
    private Integer status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestNote() {
        return testNote;
    }

    public void setTestNote(String testNote) {
        this.testNote = testNote;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getClassDate() {
        return classDate;
    }

    public void setClassDate(Integer classDate) {
        this.classDate = classDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}