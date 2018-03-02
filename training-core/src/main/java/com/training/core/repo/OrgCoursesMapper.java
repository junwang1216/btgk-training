package com.training.core.repo;

import com.training.core.repo.po.OrgCourses;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrgCoursesMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrgCourses record);

    int setCourseStatus(OrgCourses record);

    OrgCourses selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrgCourses record);

    List<OrgCourses> queryAll(@Param("courseName") String courseName, @Param("sportId") Integer sportId, @Param("status") Integer status);
}