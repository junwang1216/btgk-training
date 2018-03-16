package com.training.in.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.bean.ResponseBean;
import com.training.core.common.constant.IPlatformConstant;
import com.training.core.common.enums.ClassStatusEnum;
import com.training.core.common.enums.RoleEnum;
import com.training.core.common.enums.StatusEnum;
import com.training.core.common.exception.MessageException;
import com.training.core.common.util.DateUtil;
import com.training.core.repo.po.*;
import com.training.core.service.*;
import com.training.in.request.OrgClassScheduleRequest;
import com.training.in.response.OrgAttendanceResponse;
import com.training.in.response.OrgClassResponse;
import com.training.in.response.OrgStudentsResponse;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by wangjun on 2017/5/1.
 */
@Controller
@RequestMapping("/admin/class")
public class ClassController extends BaseController {

    @Resource
    private OrgClassService orgClassService;

    @Resource
    private OrgClassScheduleService orgClassScheduleService;

    @Resource
    private OrgVenuesService orgVenuesService;

    @Resource
    private OrgCoursesService orgCoursesService;

    @Resource
    private OrgCoachesService orgCoachesService;

    @Resource
    private OrgSportsCoachesService orgSportsCoachesService;

    @Resource
    private OrgAttendanceService orgAttendanceService;

    @Resource
    private OrgStudentsService orgStudentsService;

    @Resource
    private OrgClassStudentsService orgClassStudentsService;

    private ModelAndView setModelAndView(ModelAndView modelAndView) {
        return modelAndView.addObject("Admin", super.getRequest().getSession().getAttribute(IPlatformConstant.LOGIN_USER));
    }

    @Desc("班级设置")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView renderClassAdd() {

        ModelAndView modelAndView = new ModelAndView("Class/Add");

        List<OrgVenues> orgVenuesList = orgVenuesService.queryOrgVenuesList(1);
        modelAndView.addObject("orgVenuesList", orgVenuesList);

        List<OrgCourses> orgCoursesList = orgCoursesService.queryOrgCoursesList(null, 0, 0, 10);
        modelAndView.addObject("orgCoursesList", orgCoursesList);

        List<OrgCoaches> orgCoachesList = orgCoachesService.queryOrgCoachesList(null, null, 0, 10);
        modelAndView.addObject("orgCoachesList", orgCoachesList);

        return setModelAndView(modelAndView);
    }

    @Desc("获取某教练可允许的授课内容")
    @ResponseBody
    @RequestMapping(value = "/getCoachByCourse", method = RequestMethod.GET)
    public ResponseBean getCoachByCourse(String courseId) {
        try {
            Map<String, Object> map = new HashMap<>();

            if (courseId == null) {
                courseId = "0";
            }

            OrgCourses orgCourses = orgCoursesService.getOrgCourses(Integer.parseInt(courseId));
            List<OrgSportsCoaches> orgSportsCoachesList = orgSportsCoachesService.queryOrgSportsCoachesList(orgCourses.getSportId(), 0);

            List<OrgCoaches> orgCoachesList = new ArrayList<>();
            for (OrgSportsCoaches orgSportsCoaches : orgSportsCoachesList) {
                OrgCoaches orgCoaches = orgCoachesService.getOrgCoaches(orgSportsCoaches.getCoachId());

                if (orgCoaches != null && orgCoaches.getStatus() == StatusEnum.STATUS_OK.getCode()) {
                    orgCoachesList.add(orgCoaches);
                }
            }
            map.put("orgCoachesList", orgCoachesList);

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("班级列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView renderClassList() {

        ModelAndView modelAndView = new ModelAndView("Class/List");

        List<OrgVenues> orgVenuesList = orgVenuesService.queryOrgVenuesList(1);
        modelAndView.addObject("orgVenuesList", orgVenuesList);

        List<OrgCourses> orgCoursesList = orgCoursesService.queryOrgCoursesList(null, 0, 0, 10);
        modelAndView.addObject("orgCoursesList", orgCoursesList);

        List<OrgCoaches> orgCoachesList = orgCoachesService.queryOrgCoachesList(null, null, 0, 10);
        modelAndView.addObject("orgCoachesList", orgCoachesList);

        modelAndView.addObject("ClassStatusEnum", EnumUtils.getEnumList(ClassStatusEnum.class));

        List<OrgClass> orgClassList = orgClassService.queryOrgClassList();
        List<OrgClassResponse> orgClassResponseList = new ArrayList<>();
        for (OrgClass orgClass : orgClassList) {
            OrgClassResponse orgClassResponse = new OrgClassResponse();

            orgClassResponse.setOrgClass(orgClass);

            OrgVenues orgVenues = orgVenuesService.getOrgVenues(orgClass.getVenueId());
            orgClassResponse.setOrgVenues(orgVenues);

            OrgCoaches orgCoaches = orgCoachesService.getOrgCoaches(orgClass.getCoachId());
            orgClassResponse.setOrgCoaches(orgCoaches);

            OrgCourses orgCourses = orgCoursesService.getOrgCourses(orgClass.getCourseId());
            orgClassResponse.setOrgCourses(orgCourses);

            List<OrgClassStudents> orgClassStudentsList = orgClassStudentsService.queryOrgClassStudentsListByClassId(orgClass.getId());
            orgClassResponse.setOrgClassStudentsLength(orgClassStudentsList.size());

            orgClassResponseList.add(orgClassResponse);
        }
        modelAndView.addObject("orgClassList", orgClassResponseList);

        return setModelAndView(modelAndView);
    }

    @Desc("班级编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView renderClassEdit(String classId) {

        ModelAndView modelAndView = new ModelAndView("Class/Edit");

        List<OrgVenues> orgVenuesList = orgVenuesService.queryOrgVenuesList(1);
        modelAndView.addObject("orgVenuesList", orgVenuesList);

        List<OrgCourses> orgCoursesList = orgCoursesService.queryOrgCoursesList(null, 0, 0, 10);
        modelAndView.addObject("orgCoursesList", orgCoursesList);

        List<OrgCoaches> orgCoachesList = orgCoachesService.queryOrgCoachesList(null, null, 0, 10);
        modelAndView.addObject("orgCoachesList", orgCoachesList);

        OrgClass orgClass = orgClassService.getOrgClass(Integer.parseInt(classId));
        modelAndView.addObject("orgClass", orgClass);

        return setModelAndView(modelAndView);
    }

    @Desc("班级编辑")
    @RequestMapping(value = "/schedule", method = RequestMethod.GET)
    public ModelAndView renderClassSchedule(String classId) {

        ModelAndView modelAndView = new ModelAndView("Class/Schedule");

        modelAndView.addObject("classId", classId);

        OrgClass orgClass = orgClassService.getOrgClass(Integer.parseInt(classId));
        modelAndView.addObject("orgClass", orgClass);
        modelAndView.addObject("orgClassStart", ClassStatusEnum.STATUS_START.getCode() == orgClass.getStatus());
        modelAndView.addObject("orgClassWorking", ClassStatusEnum.STATUS_WORKING.getCode() == orgClass.getStatus());
        modelAndView.addObject("orgClassEnd", ClassStatusEnum.STATUS_END.getCode() == orgClass.getStatus());

        Map<String, Object> map = new HashMap<>();
        List<OrgClassSchedule> orgClassScheduleList = orgClassScheduleService.queryOrgClassScheduleList(Integer.parseInt(classId));
        for (OrgClassSchedule orgClassSchedule : orgClassScheduleList) {
            map.put("startDate", orgClassSchedule.getStartDate());
            map.put("endDate", orgClassSchedule.getEndDate());
            if (orgClassSchedule.getClassDate() == null) {
                map.put("classSchedule", "week");
                map.put("classWeekStyle", "display:block;");
                map.put("classDateStyle", "display:none;");
            }
            else {
                map.put("classSchedule", "date");
                map.put("classWeekStyle", "display:none;");
                map.put("classDateStyle", "display:block;");
            }
            break;
        }
        modelAndView.addObject("orgClassSchedule", map);
        modelAndView.addObject("orgClassScheduleList", orgClassScheduleList);

        List<OrgCoaches> orgCoachesList = orgCoachesService.queryOrgCoachesList(null, null, 0, 10);
        modelAndView.addObject("orgCoachesList", orgCoachesList);

        return setModelAndView(modelAndView);
    }

    @Desc("保存班级")
    @ResponseBody
    @RequestMapping(value = "/saveClass", method = RequestMethod.POST)
    public ResponseBean saveClass(OrgClass orgClass) {
        try {

            int result;

            orgClass.setStatus(1);
            if (orgClass.getId() != null) {
                orgClass.setUpdateTime(DateUtil.getNowDate());
                result = orgClassService.saveOrgClass(orgClass);
            }
            else {
                orgClass.setCreateTime(DateUtil.getNowDate());
                orgClass.setUpdateTime(DateUtil.getNowDate());
                orgClass.setStatus(ClassStatusEnum.STATUS_START.getCode());
                result = orgClassService.addOrgClass(orgClass);
            }

            Map<String, Object> map = new HashMap<>();
            map.put("classId", result);

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("保存班级状态")
    @ResponseBody
    @RequestMapping(value = "/saveClassStatus", method = RequestMethod.POST)
    public ResponseBean saveClassStatus(OrgClass orgClass) {
        try {

            int result;

            orgClass.setUpdateTime(DateUtil.getNowDate());
            result = orgClassService.saveOrgClassStatus(orgClass);

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("保存排班")
    @ResponseBody
    @RequestMapping(value = "/saveClassSchedule", method = RequestMethod.POST)
    public ResponseBean saveClassSchedule(@RequestBody OrgClassScheduleRequest orgClassScheduleRequest) {
        try {
            orgClassScheduleService.clearAllByClassId(orgClassScheduleRequest.getClassId());

            int result;

            for (OrgClassSchedule orgClassSchedule : orgClassScheduleRequest.getOrgClassScheduleList()) {
                orgClassSchedule.setCreateTime(DateUtil.getNowDate());
                orgClassSchedule.setClassId(orgClassScheduleRequest.getClassId());
            }
            result = orgClassScheduleService.addOrgClassScheduleBatch(orgClassScheduleRequest.getOrgClassScheduleList());

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("班级评测")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String renderClassTest() {
        return "Class/Test";
    }

    @Desc("上课进度")
    @RequestMapping(value = "/progress", method = RequestMethod.GET)
    public ModelAndView renderClassProgress(String classId) throws Exception {

        ModelAndView modelAndView = new ModelAndView("Class/Progress");

        if (classId == null) {
            return setModelAndView(modelAndView);
        }

        String currentDate = DateUtil.dateToString(new Date(), "");

        OrgClass orgClass = orgClassService.getOrgClass(Integer.parseInt(classId));

        modelAndView.addObject("orgClass", orgClass);

        List<OrgClassSchedule> orgClassScheduleList = orgClassScheduleService.queryOrgClassScheduleList(Integer.parseInt(classId));

        List<OrgClassSchedule> orgClassScheduleAllList = new ArrayList<>();
        List<OrgClassSchedule> orgClassScheduleStartList = new ArrayList<>();
        List<OrgClassSchedule> orgClassScheduleEndList = new ArrayList<>();
        for (OrgClassSchedule orgClassSchedule : orgClassScheduleList) {
            if (orgClassSchedule.getClassDate() != null) {
                if (orgClassSchedule.getClassDate().compareTo(currentDate) <= 0) {
                    orgClassScheduleEndList.add(orgClassSchedule);
                } else {
                    orgClassScheduleStartList.add(orgClassSchedule);
                }

                orgClassScheduleAllList.add(orgClassSchedule);
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

                    if (orgClassSchedule1.getClassDate().compareTo(currentDate) <= 0) {
                        orgClassScheduleEndList.add(orgClassSchedule1);
                    } else {
                        orgClassScheduleStartList.add(orgClassSchedule1);
                    }

                    orgClassScheduleAllList.add(orgClassSchedule1);
                }
            }
        }

        Collections.sort(orgClassScheduleAllList, new Comparator<OrgClassSchedule> () {
            public int compare(OrgClassSchedule arg0, OrgClassSchedule arg1) {
                return arg0.getClassDate().compareTo(arg1.getClassDate());
            }
        });
        modelAndView.addObject("orgClassScheduleAllList", orgClassScheduleAllList);

        Collections.sort(orgClassScheduleStartList, new Comparator<OrgClassSchedule> () {
            public int compare(OrgClassSchedule arg0, OrgClassSchedule arg1) {
                return arg0.getClassDate().compareTo(arg1.getClassDate());
            }
        });
        modelAndView.addObject("orgClassScheduleStartList", orgClassScheduleStartList);

        Collections.sort(orgClassScheduleEndList, new Comparator<OrgClassSchedule> () {
            public int compare(OrgClassSchedule arg0, OrgClassSchedule arg1) {
                return arg0.getClassDate().compareTo(arg1.getClassDate());
            }
        });
        modelAndView.addObject("orgClassScheduleEndList", orgClassScheduleEndList);

        return setModelAndView(modelAndView);
    }

    @Desc("查询某管理员")
    @ResponseBody
    @RequestMapping(value = "/getClassAttendance", method = RequestMethod.GET)
    public ResponseBean getClassAttendance(int classId, String classDate) {
        try {

            Map<String, Object> map = new HashMap<>();
            List<OrgStudentsResponse> orgStudentsResponseList = new ArrayList<>();

            OrgClass orgClass = orgClassService.getOrgClass(classId);

            List<OrgClassStudents> orgClassStudentsList = orgClassStudentsService.queryOrgClassStudentsListByClassId(classId);
            for (OrgClassStudents orgClassStudents : orgClassStudentsList) {
                OrgStudentsResponse orgStudentsResponse = new OrgStudentsResponse();

                OrgStudents orgStudents = orgStudentsService.getOrgStudents(orgClassStudents.getStudentId());
                orgStudentsResponse.setOrgStudents(orgStudents);

                List<OrgAttendance> orgAttendanceStudentList = orgAttendanceService.queryStudentSignLog(orgClassStudents.getStudentId(), classId);
                orgStudentsResponse.setOrgAttendance(null);
                for (OrgAttendance orgAttendance : orgAttendanceStudentList) {
                    if (orgAttendance.getInDate().compareTo(classDate) == 0) {
                        orgStudentsResponse.setOrgAttendance(orgAttendance);
                        break;
                    }
                }

                List<OrgAttendance> orgAttendanceCoachList = orgAttendanceService.queryCoachSignLog(orgClass.getCoachId(), classId);
                orgStudentsResponse.setOrgAttendanceCoach(null);
                for (OrgAttendance orgAttendance : orgAttendanceCoachList) {
                    if (orgAttendance.getInDate().compareTo(classDate) == 0) {
                        orgStudentsResponse.setOrgAttendanceCoach(orgAttendance);
                        break;
                    }
                }

                orgStudentsResponseList.add(orgStudentsResponse);
            }

            map.put("orgStudentsResponseList", orgStudentsResponseList);

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("签到上课")
    @ResponseBody
    @RequestMapping(value = "/signClassAttendance", method = RequestMethod.POST)
    public ResponseBean signClassAttendance(OrgAttendance orgAttendance) {
        try {
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

    @Desc("教练签到")
    @RequestMapping(value = "/coach/sign", method = RequestMethod.GET)
    public ModelAndView renderClassCoachSign(String venueId, String classId) {
        ModelAndView modelAndView = new ModelAndView("Class/CoachSign");

        List<OrgAttendanceResponse> orgAttendanceResponseList = new ArrayList<>();

        List<OrgAttendance> orgAttendanceList = orgAttendanceService.queryCoachSignLog(0, 0);
        for (OrgAttendance orgAttendance : orgAttendanceList) {
            OrgAttendanceResponse orgAttendanceResponse = new OrgAttendanceResponse();

            OrgClass orgClass = orgClassService.getOrgClass(orgAttendance.getInClassID());
            OrgCoaches orgCoaches = orgCoachesService.getOrgCoaches(orgAttendance.getInUserId());
            OrgVenues orgVenues = orgVenuesService.getOrgVenues(orgClass.getVenueId());

            orgAttendanceResponse.setOrgAttendance(orgAttendance);
            orgAttendanceResponse.setOrgClass(orgClass);
            orgAttendanceResponse.setOrgVenues(orgVenues);
            orgAttendanceResponse.setOrgCoaches(orgCoaches);

            orgAttendanceResponseList.add(orgAttendanceResponse);
        }
        modelAndView.addObject("orgAttendanceResponseList", orgAttendanceResponseList);

        List<OrgClass> classList = orgClassService.queryOrgClassList();
        modelAndView.addObject("classList", classList);

        List<OrgVenues> orgVenuesList = orgVenuesService.queryOrgVenuesList(1);
        modelAndView.addObject("orgVenuesList", orgVenuesList);

        return setModelAndView(modelAndView);
    }

    @Desc("学生签到")
    @RequestMapping(value = "/student/sign", method = RequestMethod.GET)
    public ModelAndView renderClassStudentSign(String venueId, String classId) {

        ModelAndView modelAndView = new ModelAndView("Class/StudentSign");

        List<OrgAttendanceResponse> orgAttendanceResponseList = new ArrayList<>();

        List<OrgAttendance> orgAttendanceList = orgAttendanceService.queryStudentSignLog(0, 0);
        for (OrgAttendance orgAttendance : orgAttendanceList) {
            OrgAttendanceResponse orgAttendanceResponse = new OrgAttendanceResponse();

            OrgClass orgClass = orgClassService.getOrgClass(orgAttendance.getInClassID());
            OrgStudents orgStudents = orgStudentsService.getOrgStudents(orgAttendance.getInUserId());
            OrgVenues orgVenues = orgVenuesService.getOrgVenues(orgClass.getVenueId());
            OrgCoaches orgCoaches = orgCoachesService.getOrgCoaches(orgClass.getCoachId());

            orgAttendanceResponse.setOrgAttendance(orgAttendance);
            orgAttendanceResponse.setOrgClass(orgClass);
            orgAttendanceResponse.setOrgStudents(orgStudents);
            orgAttendanceResponse.setOrgVenues(orgVenues);
            orgAttendanceResponse.setOrgCoaches(orgCoaches);

            orgAttendanceResponseList.add(orgAttendanceResponse);
        }
        modelAndView.addObject("orgAttendanceResponseList", orgAttendanceResponseList);

        List<OrgClass> classList = orgClassService.queryOrgClassList();
        modelAndView.addObject("classList", classList);

        List<OrgVenues> orgVenuesList = orgVenuesService.queryOrgVenuesList(1);
        modelAndView.addObject("orgVenuesList", orgVenuesList);

        return setModelAndView(modelAndView);
    }

}
