package com.training.core.repo.po;

import java.io.Serializable;

/**
 * @author 
 */
public class OrgClassStudents implements Serializable {
    private Integer id;

    /**
     * 班级ID
     */
    private Integer classId;

    /**
     * 学生ID
     */
    private Integer studentId;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 关联订单No
     */
    private String orderNo;

    /**
     * 有效分班
     */
    private Integer status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}