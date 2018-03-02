package com.training.h5.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.bean.ResponseBean;
import com.training.core.common.constant.IPlatformConstant;
import com.training.core.common.enums.ClassStatusEnum;
import com.training.core.common.enums.RoleEnum;
import com.training.core.common.exception.MessageException;
import com.training.core.common.servlet.HttpServlets;
import com.training.core.common.util.DateUtil;
import com.training.core.repo.po.*;
import com.training.core.service.*;
import com.training.h5.response.OrgClassResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by wangjun on 2017/5/2.
 */
@Controller
@RequestMapping("/student/center")
public class StudentCenterController extends BaseController {

    @Resource
    private OrgClassScheduleService orgClassScheduleService;

    @Resource
    private OrgCoursesService orgCoursesService;

    @Resource
    private OrgClassService orgClassService;

    @Resource
    private OrgCoachesService orgCoachesService;

    @Resource
    private OrgStudentsService orgStudentsService;

    @Resource
    private OrgClassStudentsService orgClassStudentsService;

    @Resource
    private OrgAttendanceService orgAttendanceService;

    private ModelAndView setModelAndView(ModelAndView modelAndView) {
        return modelAndView.addObject("User", super.getRequest().getSession().getAttribute(IPlatformConstant.LOGIN_USER));
    }

    @Desc("个人中心")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView renderCenterList() {

        ModelAndView modelAndView = new ModelAndView("Student/Center/Index");

        return setModelAndView(modelAndView);
    }

    @Desc("修改信息")
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView renderCenterProfile() {

        ModelAndView modelAndView = new ModelAndView("Student/Center/Profile");

        return setModelAndView(modelAndView);
    }

    @Desc("修改信息")
    @ResponseBody
    @RequestMapping(value = "/saveProfile", method = RequestMethod.POST)
    public ResponseBean saveProfile(OrgStudents orgStudents) {
        try {

            orgStudents.setUpdateTime(DateUtil.getNowDate());
            int result = orgStudentsService.saveOrgStudents(orgStudents);

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("班级详情")
    @RequestMapping(value = "/class/detail", method = RequestMethod.GET)
    public String renderCenterClassDetail(int type, int classId) {

        HttpServletRequest httpServletRequest = HttpServlets.getRequest();

        httpServletRequest.setAttribute("type", type);
        httpServletRequest.setAttribute("classId", classId);

        return "Student/Center/ClassDetail" + type;
    }

    @Desc("签到记录")
    @RequestMapping(value = "/class/log", method = RequestMethod.GET)
    public ModelAndView renderCenterClassLog(String classId) throws Exception {

        ModelAndView modelAndView = new ModelAndView("Student/Center/ClassLog");

        OrgStudents orgStudents = (OrgStudents)super.getRequest().getSession().getAttribute(IPlatformConstant.LOGIN_USER);

        List<OrgClass> orgClassList = new ArrayList<>();

        List<OrgClassStudents> orgClassStudentsList = orgClassStudentsService.queryOrgClassStudentsListByStudentId(orgStudents.getId());
        for (OrgClassStudents orgClassStudents : orgClassStudentsList) {
            OrgClass orgClass = orgClassService.getOrgClass(orgClassStudents.getClassId());
            orgClassList.add(orgClass);

           if (classId != null && classId.equals(orgClass.getId().toString())) {
               modelAndView.addObject("orgClass", orgClass);
           }
        }
        modelAndView.addObject("orgClassList", orgClassList);

        if (classId == null) {
            classId = orgClassList.get(0).getId().toString();
            modelAndView.addObject("orgClass", orgClassList.get(0));
        }

        List<OrgAttendance> orgAttendanceList = orgAttendanceService.queryStudentSignLog(orgStudents.getId(), Integer.parseInt(classId));
        modelAndView.addObject("orgAttendanceList", orgAttendanceList);

        List<OrgClassSchedule> orgClassScheduleList = orgClassScheduleService.queryOrgClassScheduleList(Integer.parseInt(classId));

        List<OrgClassSchedule> mapList = new ArrayList<>();
        for (OrgClassSchedule orgClassSchedule : orgClassScheduleList) {
            if (orgClassSchedule.getClassDate() != null) {
                mapList.add(orgClassSchedule);
            }
            else {
                for (String week : DateUtil.getDateScopeWeekList(orgClassSchedule.getStartDate(), orgClassSchedule.getEndDate(), orgClassSchedule.getClassWeek())) {
                    OrgClassSchedule orgClassSchedule1 = new OrgClassSchedule();

                    orgClassSchedule1.setClassDate(week);
                    orgClassSchedule1.setClassId(orgClassSchedule.getClassId());
                    orgClassSchedule1.setClassWeek(orgClassSchedule.getClassWeek());
                    orgClassSchedule1.setCoachId(orgClassSchedule.getCoachId());
                    orgClassSchedule1.setStartDate(orgClassSchedule.getStartDate());
                    orgClassSchedule1.setEndDate(orgClassSchedule.getEndDate());
                    orgClassSchedule1.setId(orgClassSchedule.getId());
                    orgClassSchedule1.setHasSigned(0);

                    for (OrgAttendance orgAttendance : orgAttendanceList) {
                        if (orgAttendance.getInScheduleId().intValue() == orgClassSchedule1.getId().intValue() &&
                                orgAttendance.getInDate().equals(orgClassSchedule1.getClassDate())) {
                            orgClassSchedule1.setHasSigned(1);
                            break;
                        }
                    }

                    mapList.add(orgClassSchedule1);
                }
            }
        }

        Collections.sort(mapList, new Comparator<OrgClassSchedule> () {
            public int compare(OrgClassSchedule arg0, OrgClassSchedule arg1) {
                return arg0.getClassDate().compareTo(arg1.getClassDate());
            }
        });

        modelAndView.addObject("orgClassScheduleList", mapList);

        return setModelAndView(modelAndView);
    }

    @Desc("签到上课")
    @ResponseBody
    @RequestMapping(value = "/signClass", method = RequestMethod.POST)
    public ResponseBean signClass(OrgAttendance orgAttendance) {
        try {
            orgAttendance.setInClassID(1);
            orgAttendance.setInDate(orgAttendance.getInDate());
            orgAttendance.setInRole(RoleEnum.ROLE_STUDENT.getCode());
            orgAttendance.setInUserId(1);
            orgAttendance.setInScheduleId(orgAttendance.getInScheduleId());
            orgAttendance.setCreateTime(DateUtil.getNowDate());

            int result = orgAttendanceService.toSignin(orgAttendance);

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("我的班级")
    @RequestMapping(value = "/class", method = RequestMethod.GET)
    public ModelAndView renderCenterClass() {

        ModelAndView modelAndView = new ModelAndView("Student/Center/Class");

        OrgStudents orgStudents = (OrgStudents)super.getRequest().getSession().getAttribute(IPlatformConstant.LOGIN_USER);

        List<OrgClassResponse> orgClassStartList = new ArrayList<>();
        List<OrgClassResponse> orgClassGoingList = new ArrayList<>();
        List<OrgClassResponse> orgClassEndList = new ArrayList<>();

        List<OrgClassStudents> orgClassStudentsList = orgClassStudentsService.queryOrgClassStudentsListByStudentId(orgStudents.getId());
        for (OrgClassStudents orgClassStudents : orgClassStudentsList) {
            OrgClassResponse orgClassResponse = new OrgClassResponse();

            OrgClass orgClass = orgClassService.getOrgClass(orgClassStudents.getClassId());
            orgClassResponse.setOrgClass(orgClass);

            OrgCourses orgCourses = orgCoursesService.getOrgCourses(orgClass.getCourseId());
            orgClassResponse.setOrgCourses(orgCourses);

            OrgCoaches orgCoaches = orgCoachesService.getOrgCoaches(orgClass.getCoachId());
            orgClassResponse.setOrgCoaches(orgCoaches);

            if (ClassStatusEnum.STATUS_START.getCode() == orgClass.getStatus()) {
                orgClassStartList.add(orgClassResponse);
            }
            if (ClassStatusEnum.STATUS_WORKING.getCode() == orgClass.getStatus()) {
                orgClassGoingList.add(orgClassResponse);
            }
            if (ClassStatusEnum.STATUS_END.getCode() == orgClass.getStatus()) {
                orgClassEndList.add(orgClassResponse);
            }
        }
        modelAndView.addObject("orgClassStartList", orgClassStartList);
        modelAndView.addObject("orgClassGoingList", orgClassGoingList);
        modelAndView.addObject("orgClassEndList", orgClassEndList);

        return setModelAndView(modelAndView);
    }

    @Desc("我的签到")
    @RequestMapping(value = "/attendance", method = RequestMethod.GET)
    public String renderCenterAttendance() {
        return "Student/Center/Attendance";
    }

}
