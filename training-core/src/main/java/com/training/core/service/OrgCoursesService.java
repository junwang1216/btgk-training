package com.training.core.service;

import com.training.core.repo.po.OrgCourses;

import java.util.List;

public interface OrgCoursesService {

    int addOrgCourses(OrgCourses orgCourses);

    int saveOrgCourses(OrgCourses orgCourses);

    int lockOrgCourses(OrgCourses orgCourses);

    List<OrgCourses> queryOrgCoursesList(String courseName, Integer sportId);

    OrgCourses getOrgCourses(Integer id);

}
