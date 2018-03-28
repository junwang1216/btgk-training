package com.training.in.request;

/**
 * Created by wangjun on 2018/2/9.
 */
public class OrgClassStudentsRequest {

    private int classId;
    private String classIds;
    private int studentId;
    private boolean state;

    public int getClassId() {
        return classId;
    }
    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassIds() {
        return classIds;
    }
    public void setClassIds(String classIds) {
        this.classIds = classIds;
    }

    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public boolean isState() {
        return state;
    }
    public void setState(boolean state) {
        this.state = state;
    }
}
