package com.training.in.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.bean.ResponseBean;
import com.training.core.common.constant.IPlatformConstant;
import com.training.core.common.enums.ClassStatusEnum;
import com.training.core.common.exception.MessageException;
import com.training.core.common.util.DateUtil;
import com.training.core.common.util.Page;
import com.training.core.repo.po.*;
import com.training.core.service.*;
import com.training.in.request.OrgStudentsRequest;
import com.training.in.response.OrgClassResponse;
import com.training.in.response.OrgStudentsResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangjun on 2017/5/1.
 */
@Controller
@RequestMapping("/admin/students")
public class StudentsController extends BaseController {

    @Resource
    private OrgClassService orgClassService;

    @Resource
    private OrgSportsService orgSportsService;

    @Resource
    private OrgCoursesService orgCoursesService;

    @Resource
    private OrgVenuesService orgVenuesService;

    @Resource
    private OrgStudentsService orgStudentsService;

    @Resource
    private OrgClassStudentsService orgClassStudentsService;

    private ModelAndView setModelAndView(ModelAndView modelAndView) {
        return modelAndView.addObject("Admin", super.getRequest().getSession().getAttribute(IPlatformConstant.LOGIN_USER));
    }

    @Desc("学生列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView renderStudentsList(OrgStudentsRequest orgStudentsRequest) {

        ModelAndView modelAndView = new ModelAndView("Students/List");

        modelAndView.addObject("conditions", orgStudentsRequest);

        List<OrgClass> orgClassList = orgClassService.queryOrgClassList();

        List<OrgClassResponse> orgClassResponseList = new ArrayList<>();
        for (OrgClass orgClass : orgClassList) {
            if (orgClass.getStatus() == ClassStatusEnum.STATUS_END.getCode()) {
                continue;
            }

            OrgClassResponse orgClassResponse = new OrgClassResponse();

            OrgCourses orgCourses = orgCoursesService.getOrgCourses(orgClass.getCourseId());

            orgClassResponse.setOrgClass(orgClass);
            orgClassResponse.setOrgCourses(orgCourses);
            orgClassResponse.setOrgVenues(orgVenuesService.getOrgVenues(orgClass.getVenueId()));
            orgClassResponse.setOrgSports(orgSportsService.getOrgSports(orgCourses.getSportId()));

            orgClassResponseList.add(orgClassResponse);
        }
        modelAndView.addObject("orgClassList", orgClassResponseList);

        if (orgStudentsRequest.getClassId() != 0) {
            List<Integer> studentIds = new ArrayList<>();
            List<OrgClassStudents> orgClassStudentsList = orgClassStudentsService.queryOrgClassStudentsListByClassId(orgStudentsRequest.getClassId());
            for (OrgClassStudents orgClassStudents : orgClassStudentsList) {
                studentIds.add(orgClassStudents.getStudentId());
            }
            orgStudentsRequest.setStudentIds(studentIds);
        }

        List<OrgStudents> orgStudentsList = orgStudentsService.queryOrgStudentsList();

        List<OrgStudentsResponse> orgStudentsResponseList = new ArrayList<>();
        for (OrgStudents orgStudents : orgStudentsList) {
            OrgStudentsResponse orgStudentsResponse = new OrgStudentsResponse();

            orgStudentsResponse.setOrgStudents(orgStudents);
            List<OrgClassStudents> orgClassStudentsList = orgClassStudentsService.queryOrgClassStudentsListByStudentId(orgStudents.getId());

            if (orgClassStudentsList.size() <= 0) {
                orgStudentsResponse.setOrgClass(null);
                orgStudentsResponseList.add(orgStudentsResponse);
                continue;
            }

            orgStudentsResponse.setOrgClass(null);
            for (OrgClassStudents orgClassStudents : orgClassStudentsList) {
                OrgClass orgClass = orgClassService.getOrgClass(orgClassStudents.getClassId());

                if (orgClass.getStatus() == ClassStatusEnum.STATUS_END.getCode()) {
                    continue;
                }

                orgStudentsResponse.setOrgClass(orgClass);
                orgStudentsResponseList.add(orgStudentsResponse);
                break;
            }
        }
        modelAndView.addObject("orgStudentsList", orgStudentsResponseList);

        int total = orgStudentsResponseList.size();
        Page page = new Page(10, total);
        page.setPage(1);

        modelAndView.addObject("total", total);
        modelAndView.addObject("pageURL", "/admin/students/list?realName=&mobile=&classId=0");
        modelAndView.addObject("page", page);

        return setModelAndView(modelAndView);
    }

    @Desc("学生编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView renderStudentsEdit(String studentId) {
        ModelAndView modelAndView = new ModelAndView("Students/Edit");

        OrgStudents orgStudents = orgStudentsService.getOrgStudents(Integer.parseInt(studentId));
        modelAndView.addObject("orgStudents", orgStudents);

        return setModelAndView(modelAndView);
    }

    @Desc("学生添加")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView renderStudentsAdd() {

        ModelAndView modelAndView = new ModelAndView("Students/Add");

        List<OrgClass> orgClassList = orgClassService.queryOrgClassList();

        List<OrgClassResponse> orgClassResponseList = new ArrayList<>();
        for (OrgClass orgClass : orgClassList) {
            OrgClassResponse orgClassResponse = new OrgClassResponse();

            OrgCourses orgCourses = orgCoursesService.getOrgCourses(orgClass.getCourseId());

            orgClassResponse.setOrgClass(orgClass);
            orgClassResponse.setOrgCourses(orgCourses);
            orgClassResponse.setOrgVenues(orgVenuesService.getOrgVenues(orgClass.getVenueId()));
            orgClassResponse.setOrgSports(orgSportsService.getOrgSports(orgCourses.getSportId()));

            orgClassResponseList.add(orgClassResponse);
        }
        modelAndView.addObject("orgClassList", orgClassResponseList);

        return setModelAndView(modelAndView);
    }

    @Desc("保存学生")
    @ResponseBody
    @RequestMapping(value = "/saveStudents", method = RequestMethod.POST)
    public ResponseBean saveStudents(OrgStudents orgStudents) {
        try {

            int result;

            if (orgStudents.getId() != null) {
                orgStudents.setUpdateTime(DateUtil.getNowDate());
                result = orgStudentsService.saveOrgStudents(orgStudents);
            }
            else {
                orgStudents.setCreateTime(DateUtil.getNowDate());
                orgStudents.setUpdateTime(DateUtil.getNowDate());
                result = orgStudentsService.addOrgStudents(orgStudents);
            }

            Map<String, Object> map = new HashMap<>();
            map.put("studentId", result > 0 ? orgStudents.getId() : 0);

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("保存学生")
    @ResponseBody
    @RequestMapping(value = "/saveClassStudents", method = RequestMethod.POST)
    public ResponseBean saveStudents(OrgClassStudents orgClassStudents) {
        try {

            int result;

            orgClassStudents.setCreateTime(DateUtil.getNowDate());
            result = orgClassStudentsService.addOrgClassStudents(orgClassStudents);

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("学生批量添加")
    @RequestMapping(value = "/batch", method = RequestMethod.GET)
    public String renderStudentsBatch() {
        return "Students/Batch";
    }

}
