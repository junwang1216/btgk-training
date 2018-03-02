package com.training.core.service.impl;

import com.training.core.repo.OrgCoursesMapper;
import com.training.core.repo.po.OrgCourses;
import com.training.core.service.OrgCoursesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgCoursesServiceImpl implements OrgCoursesService {

    @Resource
    private OrgCoursesMapper orgCoursesMapper;

    @Override
    public int addOrgCourses(OrgCourses orgCourses) {
        return orgCoursesMapper.insert(orgCourses);
    }

    @Override
    public int saveOrgCourses(OrgCourses orgCourses) {
        return orgCoursesMapper.updateByPrimaryKey(orgCourses);
    }

    @Override
    public int lockOrgCourses(OrgCourses orgCourses) {
        return orgCoursesMapper.setCourseStatus(orgCourses);
    }

    @Override
    public List<OrgCourses> queryOrgCoursesList(String courseName, Integer sportId) {
        return orgCoursesMapper.queryAll(courseName, sportId, null);
    }

    @Override
    public OrgCourses getOrgCourses(Integer id) {
        return orgCoursesMapper.selectByPrimaryKey(id);
    }
}


