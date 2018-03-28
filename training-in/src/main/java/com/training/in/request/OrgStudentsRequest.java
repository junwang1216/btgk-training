package com.training.in.request;

import com.training.core.common.annotation.Desc;
import com.training.core.repo.query.OrgPageRequest;

import java.util.List;

/**
 * Created by wangjun on 2018/2/9.
 */
public class OrgStudentsRequest extends OrgPageRequest {

    /** 学生检索信息 **/
    private List<Integer> studentIds;
    private String realName;
    private String mobile;
    private String idCard;

    public String getRealName() {
        return (realName == null || realName.equals("")) ? null : realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return (mobile == null || mobile.equals("")) ? null : mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdCard() {
        return idCard;
    }
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public List<Integer> getStudentIds() {
        return studentIds;
    }
    public void setStudentIds(List<Integer> studentIds) {
        this.studentIds = studentIds;
    }

    /** 班级信息 **/
    private int classId;

    public int getClassId() {
        return classId;
    }
    public void setClassId(int classId) {
        this.classId = classId;
    }

    /** 分页信息 **/
    private int page = 1;
    private int pageSize = 10;

    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
