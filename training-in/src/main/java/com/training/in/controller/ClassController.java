package com.training.in.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.bean.ResponseBean;
import com.training.core.common.constant.IPlatformConstant;
import com.training.core.common.enums.ClassStatusEnum;
import com.training.core.common.enums.LogTypeEnum;
import com.training.core.common.enums.RoleEnum;
import com.training.core.common.enums.StatusEnum;
import com.training.core.common.exception.MessageException;
import com.training.core.common.util.DateUtil;
import com.training.core.common.util.Page;
import com.training.core.repo.po.*;
import com.training.core.service.*;
import com.training.in.request.OrgClassQueryRequest;
import com.training.in.request.OrgClassScheduleRequest;
import com.training.in.request.OrgClassTestQueryRequest;
import com.training.in.request.OrgClassTestResultRequest;
import com.training.in.response.OrgAttendanceResponse;
import com.training.in.response.OrgClassResponse;
import com.training.in.response.OrgClassTestResponse;
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
 * 设置班级信息
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
    private OrgSportsSkillsService orgSportsSkillsService;

    @Resource
    private OrgAttendanceService orgAttendanceService;

    @Resource
    private OrgStudentsService orgStudentsService;

    @Resource
    private OrgClassStudentsService orgClassStudentsService;

    @Resource
    private OrgClassTestService orgClassTestService;

    @Resource
    private OrgClassTestResultService orgClassTestResultService;

    private ModelAndView setModelAndView(ModelAndView modelAndView) {
        return modelAndView.addObject("Admin", super.getRequest().getSession().getAttribute(IPlatformConstant.LOGIN_USER));
    }

    /** 班级设置 **/

    @Desc("班级设置")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView renderClassAdd() {

        ModelAndView modelAndView = new ModelAndView("Class/Add");

        List<OrgVenues> orgVenuesList = orgVenuesService.queryOrgVenuesList(1);
        modelAndView.addObject("orgVenuesList", orgVenuesList);

        int orgCoursesCount = orgCoursesService.queryOrgCoursesCount(null, 0);
        List<OrgCourses> orgCoursesList = orgCoursesService.queryOrgCoursesList(null, 0, 0, orgCoursesCount);
        modelAndView.addObject("orgCoursesList", orgCoursesList);

        int orgCoachesCount = orgCoachesService.queryOrgCoachesCount(null, null);
        List<OrgCoaches> orgCoachesList = orgCoachesService.queryOrgCoachesList(null, null, 0, orgCoachesCount);
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
    public ModelAndView renderClassList(OrgClassQueryRequest orgClassQueryRequest) {

        ModelAndView modelAndView = new ModelAndView("Class/List");

        modelAndView.addObject("className", orgClassQueryRequest.getClassName());
        modelAndView.addObject("status", orgClassQueryRequest.getStatus());

        List<OrgVenues> orgVenuesList = orgVenuesService.queryOrgVenuesList(1);
        modelAndView.addObject("orgVenuesList", orgVenuesList);

        int orgCoursesCount = orgCoursesService.queryOrgCoursesCount(null, 0);
        List<OrgCourses> orgCoursesList = orgCoursesService.queryOrgCoursesList(null, 0, 0, orgCoursesCount);
        modelAndView.addObject("orgCoursesList", orgCoursesList);

        int orgCoachesCount = orgCoachesService.queryOrgCoachesCount(null, null);
        List<OrgCoaches> orgCoachesList = orgCoachesService.queryOrgCoachesList(null, null, 0, orgCoachesCount);
        modelAndView.addObject("orgCoachesList", orgCoachesList);

        modelAndView.addObject("ClassStatusEnum", EnumUtils.getEnumList(ClassStatusEnum.class));

        int total = orgClassService.queryOrgClassCount(orgClassQueryRequest.getClassName(), orgClassQueryRequest.getStatus());
        int start = orgClassQueryRequest.getPage() < 1 ? 0 : orgClassQueryRequest.getPage() - 1;
        int pageSize = 10;
        List<OrgClass> orgClassList = orgClassService.queryOrgClassList(orgClassQueryRequest.getClassName(), orgClassQueryRequest.getStatus(), start * pageSize, pageSize);
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

        Page page = new Page(pageSize, total);
        page.setPage(orgClassQueryRequest.getPage());

        modelAndView.addObject("total", total);
        modelAndView.addObject("pageURL", "/admin/class/list?className=" + orgClassQueryRequest.getClassName() + "&status=" + orgClassQueryRequest.getStatus());
        modelAndView.addObject("page", page);

        return setModelAndView(modelAndView);
    }

    @Desc("班级编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView renderClassEdit(String classId) {

        ModelAndView modelAndView = new ModelAndView("Class/Edit");

        List<OrgVenues> orgVenuesList = orgVenuesService.queryOrgVenuesList(1);
        modelAndView.addObject("orgVenuesList", orgVenuesList);

        int orgCoursesCount = orgCoursesService.queryOrgCoursesCount(null, 0);
        List<OrgCourses> orgCoursesList = orgCoursesService.queryOrgCoursesList(null, 0, 0, orgCoursesCount);
        modelAndView.addObject("orgCoursesList", orgCoursesList);

        int orgCoachesCount = orgCoachesService.queryOrgCoachesCount(null, null);
        List<OrgCoaches> orgCoachesList = orgCoachesService.queryOrgCoachesList(null, null, 0, orgCoachesCount);
        modelAndView.addObject("orgCoachesList", orgCoachesList);

        OrgClass orgClass = orgClassService.getOrgClass(Integer.parseInt(classId));
        modelAndView.addObject("orgClass", orgClass);

        return setModelAndView(modelAndView);
    }

    @Desc("班级排期")
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
        List<OrgClassSchedule> orgClassScheduleList1 = new ArrayList<>();
        for (OrgClassSchedule orgClassSchedule : orgClassScheduleList) {
            if (orgClass.getStatus() == ClassStatusEnum.STATUS_WORKING.getCode() && orgClassSchedule.getClassDate() == null) {
                continue;
            }
            orgClassScheduleList1.add(orgClassSchedule);
        }

        if (orgClassScheduleList1.size() == 0) {
            OrgClassSchedule orgClassSchedule = new OrgClassSchedule();
            map.put("startDate", orgClassSchedule.getStartDate());
            map.put("endDate", orgClassSchedule.getEndDate());
            map.put("classSchedule", "week");
            map.put("classWeekStyle", "display:block;");
            map.put("classDateStyle", "display:none;");
        }
        else {
            OrgClassSchedule orgClassSchedule = orgClassScheduleList1.get(0);
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
        }

        modelAndView.addObject("orgClassSchedule", map);
        modelAndView.addObject("orgClassScheduleList", orgClassScheduleList1);


        OrgCourses orgCourses = orgCoursesService.getOrgCourses(orgClass.getCourseId());
        List<OrgSportsCoaches> orgSportsCoachesList = orgSportsCoachesService.queryOrgSportsCoachesList(orgCourses.getSportId(), 0);

        List<OrgCoaches> orgCoachesList = new ArrayList<>();
        for (OrgSportsCoaches orgSportsCoaches : orgSportsCoachesList) {
            OrgCoaches orgCoaches = orgCoachesService.getOrgCoaches(orgSportsCoaches.getCoachId());

            if (orgCoaches != null && orgCoaches.getStatus() == StatusEnum.STATUS_OK.getCode()) {
                orgCoachesList.add(orgCoaches);
            }
        }
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
            map.put("classId", result > 0 ? orgClass.getId() : 0);

            log(LogTypeEnum.LOG_TYPE_CLASS_SETTINGS, getLoginUser().getOrgId(), "添加或者修改班级[" + orgClass.getClassName() + "]信息");

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
    public ResponseBean saveClassStatus(OrgClass orgClass) throws Exception {
        try {

            int result;
            OrgClass orgClass1 = orgClassService.getOrgClass(orgClass.getId());

            if (orgClass.getStatus().intValue() == orgClass1.getStatus().intValue()) {
                // 未做改动
                return new ResponseBean(false);
            }

            if (orgClass1.getStatus() == ClassStatusEnum.STATUS_WORKING.getCode() && orgClass.getStatus() == ClassStatusEnum.STATUS_START.getCode()) {
                // 上课中 -> 已开班
                return new ResponseBean(false);
            }

            if (orgClass1.getStatus() == ClassStatusEnum.STATUS_END.getCode() && orgClass.getStatus() == ClassStatusEnum.STATUS_START.getCode()) {
                // 已完结 -> 已开班
                return new ResponseBean(false);
            }

            if (orgClass1.getStatus() == ClassStatusEnum.STATUS_END.getCode() && orgClass.getStatus() == ClassStatusEnum.STATUS_WORKING.getCode()) {
                // 已完结 -> 上课中
                return new ResponseBean(false);
            }

            orgClass.setUpdateTime(DateUtil.getNowDate());
            result = orgClassService.saveOrgClassStatus(orgClass);

            log(LogTypeEnum.LOG_TYPE_CLASS_SETTINGS, getLoginUser().getOrgId(), "切换班级[" + orgClass1.getClassName() + "]状态");

            if (orgClass.getStatus() == ClassStatusEnum.STATUS_WORKING.getCode() && result > 0) {
                List<OrgClassSchedule> orgClassScheduleList = orgClassScheduleService.queryOrgClassScheduleList(orgClass.getId());
                List<OrgClassSchedule> orgClassScheduleAllList = new ArrayList<>();
                for (OrgClassSchedule orgClassSchedule : orgClassScheduleList) {
                    if (orgClassSchedule.getClassDate() != null) {
                        orgClassScheduleAllList.clear();
                        break;
                    }

                    for (String week : DateUtil.getDateScopeWeekList(orgClassSchedule.getStartDate(), orgClassSchedule.getEndDate(), orgClassSchedule.getClassWeek())) {
                        OrgClassSchedule orgClassSchedule1 = new OrgClassSchedule();

                        orgClassSchedule1.setClassId(orgClassSchedule.getClassId());
                        orgClassSchedule1.setClassDate(week);
                        orgClassSchedule1.setStartTime(orgClassSchedule.getStartTime());
                        orgClassSchedule1.setEndTime(orgClassSchedule.getEndTime());
                        orgClassSchedule1.setCoachId(orgClassSchedule.getCoachId());
                        orgClassSchedule1.setCreateTime(DateUtil.getNowDate());
                        orgClassSchedule1.setClassWeek(null);
                        orgClassSchedule1.setStartDate(null);
                        orgClassSchedule1.setEndDate(null);

                        orgClassScheduleAllList.add(orgClassSchedule1);
                    }
                }

                if (orgClassScheduleAllList.size() > 0) {
                    Collections.sort(orgClassScheduleAllList, new Comparator<OrgClassSchedule> () {
                        public int compare(OrgClassSchedule arg0, OrgClassSchedule arg1) {
                            return arg0.getClassDate().compareTo(arg1.getClassDate());
                        }
                    });

                    result = orgClassScheduleService.addOrgClassScheduleBatch(orgClassScheduleAllList);
                }
            }

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("删除排班")
    @ResponseBody
    @RequestMapping(value = "/deleteClassSchedule", method = RequestMethod.POST)
    public ResponseBean deleteClassSchedule(@RequestBody OrgClassSchedule orgClassSchedule) {
        try {
            int result = 1;

            OrgClass orgClass = orgClassService.getOrgClass(orgClassSchedule.getClassId());
            if (orgClass.getStatus() == ClassStatusEnum.STATUS_WORKING.getCode()) {
                result = orgClassScheduleService.deleteClassSchedule(orgClassSchedule.getId());
            }

            log(LogTypeEnum.LOG_TYPE_CLASS_SETTINGS, getLoginUser().getOrgId(), "删除班级[" + orgClass.getClassName() + "]排期计划");

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
            //orgClassScheduleService.clearAllByClassId(orgClassScheduleRequest.getClassId());

            int result;

            OrgClass orgClass = orgClassService.getOrgClass(orgClassScheduleRequest.getClassId());
            List<OrgClassSchedule> orgClassScheduleList = new ArrayList<>();
            for (OrgClassSchedule orgClassSchedule : orgClassScheduleRequest.getOrgClassScheduleList()) {
                orgClassSchedule.setCreateTime(DateUtil.getNowDate());
                orgClassSchedule.setClassId(orgClassScheduleRequest.getClassId());

                if (orgClassSchedule.getId() == null) {
                    orgClassScheduleList.add(orgClassSchedule);
                }
            }

            if (orgClass.getStatus() == ClassStatusEnum.STATUS_WORKING.getCode()) {
                result = orgClassScheduleService.addOrgClassScheduleBatch(orgClassScheduleList);
            } else {
                orgClassScheduleService.clearAllByClassId(orgClassScheduleRequest.getClassId());

                result = orgClassScheduleService.addOrgClassScheduleBatch(orgClassScheduleRequest.getOrgClassScheduleList());
            }

            log(LogTypeEnum.LOG_TYPE_CLASS_SETTINGS, getLoginUser().getOrgId(), "设置班级[" + orgClass.getClassName() + "]排期计划");

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    /** 其他班级设置 **/

    private OrgClass getOrgClass(Integer classId, List<OrgClass> orgClassList) {
        for (OrgClass orgClass : orgClassList) {
            if (orgClass.getId().intValue() == classId.intValue()) {
                return orgClass;
            }
        }

        return null;
    }

    @Desc("班级评测")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ModelAndView renderClassTest(OrgClassTestQueryRequest orgClassTestQueryRequest) {

        ModelAndView modelAndView = new ModelAndView("Class/Test");

        int totalOrgClassCount = orgClassService.queryOrgClassCount(null, ClassStatusEnum.STATUS_WORKING.getCode());
        List<OrgClass> orgClassList = orgClassService.queryOrgClassList(null, ClassStatusEnum.STATUS_WORKING.getCode(), 0, totalOrgClassCount);

        modelAndView.addObject("orgClassList", orgClassList);

        int total = orgClassTestService.queryOrgClassTestCount(orgClassTestQueryRequest.getClassId(), StatusEnum.STATUS_OK.getCode());
        int start = orgClassTestQueryRequest.getPage() < 1 ? 0 : orgClassTestQueryRequest.getPage() - 1;
        int pageSize = 10;
        List<OrgClassTest> orgClassTestList = orgClassTestService.queryOrgClassTestList(orgClassTestQueryRequest.getClassId(), StatusEnum.STATUS_OK.getCode(), start * pageSize, pageSize);

        List<OrgClassTestResponse> orgClassTestResponseList = new ArrayList<>();
        for (OrgClassTest orgClassTest : orgClassTestList) {
            OrgClassTestResponse orgClassTestResponse = new OrgClassTestResponse();

            orgClassTestResponse.setOrgClassTest(orgClassTest);

            OrgClass orgClass = getOrgClass(orgClassTest.getClassId(), orgClassList);
            if (orgClass != null) {
                orgClassTestResponse.setOrgClass(orgClass);

                OrgCoaches orgCoaches = orgCoachesService.getOrgCoaches(orgClass.getCoachId());
                orgClassTestResponse.setOrgCoaches(orgCoaches);

                OrgClassSchedule orgClassSchedule = orgClassScheduleService.getClassSchedule(orgClassTest.getClassDate());
                orgClassTestResponse.setOrgClassSchedule(orgClassSchedule);
            }

            orgClassTestResponseList.add(orgClassTestResponse);
        }

        modelAndView.addObject("orgClassTestList", orgClassTestResponseList);

        Page page = new Page(pageSize, total);
        page.setPage(orgClassTestQueryRequest.getPage());

        modelAndView.addObject("total", total);
        modelAndView.addObject("pageURL", "/admin/class/test?classId=" + orgClassTestQueryRequest.getClassId() + "&status=" + orgClassTestQueryRequest.getStatus());
        modelAndView.addObject("page", page);

        return setModelAndView(modelAndView);
    }

    @Desc("获取某个班级的评测日期")
    @ResponseBody
    @RequestMapping(value = "/getClassDateByClassId", method = RequestMethod.GET)
    public ResponseBean getClassDateByClassId(String classId) {
        try {
            Map<String, Object> map = new HashMap<>();

            if (classId == null) {
                return new ResponseBean(false);
            }

            List<OrgClassSchedule> orgClassScheduleList = orgClassScheduleService.queryOrgClassScheduleList(Integer.parseInt(classId));
            map.put("orgClassScheduleList", orgClassScheduleList);

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("获取评测结果")
    @ResponseBody
    @RequestMapping(value = "/getClassTestResult", method = RequestMethod.GET)
    public ResponseBean getClassTestResult(String testId, String classId, boolean isView) {
        try {
            Map<String, Object> map = new HashMap<>();

            if (testId == null || classId == null) {
                return new ResponseBean(false);
            }

            OrgClass orgClass = orgClassService.getOrgClass(Integer.parseInt(classId));
            OrgCourses orgCourses = orgCoursesService.getOrgCourses(orgClass.getCourseId());

            List<OrgSportsSkills> orgSportsSkillsList = orgSportsSkillsService.queryOrgSportsSkillsList(orgCourses.getSportId());
            map.put("orgSportsSkillsList", orgSportsSkillsList);

            List<OrgClassStudents> orgClassStudentsList = orgClassStudentsService.queryOrgClassStudentsListByClassId(Integer.parseInt(classId));
            List<OrgStudents> orgStudentsList = new ArrayList<>();
            List<Map> scoreList = new ArrayList<>();
            for (OrgClassStudents orgClassStudents : orgClassStudentsList) {
                OrgStudents orgStudents = orgStudentsService.getOrgStudents(orgClassStudents.getStudentId());

                if (isView) {
                    List<OrgClassTestResult> orgClassTestResultList = orgClassTestResultService.queryOrgClassTestResultList(Integer.parseInt(testId), orgStudents.getId());
                    Map scoreItem = new HashMap();
                    int totalValue = 0;
                    int totalMaxValue = 0;
                    for (OrgClassTestResult orgClassTestResult : orgClassTestResultList) {
                        for (OrgSportsSkills orgSportsSkills : orgSportsSkillsList) {
                            if (orgSportsSkills.getId().intValue() == orgClassTestResult.getSkillId()) {
                                totalValue += orgClassTestResult.getTestScore();
                                totalMaxValue += orgSportsSkills.getMaxValue();
                                scoreItem.put(orgSportsSkills.getSkillName(), orgClassTestResult.getTestScore().toString());
                            }
                        }
                        if (orgClassTestResult.getTestRemark() != null) {
                            scoreItem.put("testRemark", orgClassTestResult.getTestRemark());
                        }
                    }
                    scoreItem.put("总计", totalValue + "/" + totalMaxValue);
                    scoreItem.put("学员", orgStudents.getRealName());
                    scoreItem.put("totalValue", totalValue);
                    scoreItem.put("totalMaxValue", totalMaxValue);
                    scoreItem.put("studentId", orgStudents.getId());
                    scoreItem.put("classId", classId);
                    scoreItem.put("testId", testId);
                    scoreList.add(scoreItem);
                }

                orgStudentsList.add(orgStudents);
            }
            map.put("orgStudentsList", orgStudentsList);
            map.put("scoreList", scoreList);

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("保存班级评测")
    @ResponseBody
    @RequestMapping(value = "/saveClassTest", method = RequestMethod.POST)
    public ResponseBean saveClassTest(OrgClassTest orgClassTest) {
        try {
            int result;

            if (orgClassTest.getId() != null) {
                result = orgClassTestService.saveOrgClassTest(orgClassTest);
            } else {
                orgClassTest.setCreateTime(DateUtil.getNowDate());
                orgClassTest.setStatus(StatusEnum.STATUS_OK.getCode());

                result = orgClassTestService.addOrgClassTest(orgClassTest);
            }

            log(LogTypeEnum.LOG_TYPE_CLASS_SETTINGS, getLoginUser().getOrgId(), "添加或者保存班级评测[" + orgClassTest.getTestName() + "]");

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("删除班级评测")
    @ResponseBody
    @RequestMapping(value = "/deleteClassTest", method = RequestMethod.POST)
    public ResponseBean deleteClassTest(@RequestBody OrgClassTest orgClassTest) {
        try {
            int result;

            OrgClassTest orgClassTest1 = orgClassTestService.getOrgClassTest(orgClassTest.getId());
            orgClassTest1.setStatus(StatusEnum.STATUS_ERROR.getCode());

            result = orgClassTestService.saveOrgClassTestStatus(orgClassTest1);

            log(LogTypeEnum.LOG_TYPE_CLASS_SETTINGS, getLoginUser().getOrgId(), "删除班级评测[" + orgClassTest1.getTestName() + "]");

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("保存班级评测结果")
    @ResponseBody
    @RequestMapping(value = "/saveClassTestResult", method = RequestMethod.POST)
    public ResponseBean saveClassTestResult(@RequestBody OrgClassTestResultRequest orgClassTestResultRequest) {
        try {

            int result;

            orgClassTestResultService.clearTestResultByStudentId(orgClassTestResultRequest.getStudentId());

            for (OrgClassTestResult orgClassTestResult : orgClassTestResultRequest.getOrgClassTestResultList()) {
                orgClassTestResult.setCreateTime(DateUtil.getNowDate());
                orgClassTestResult.setUpdateTime(DateUtil.getNowDate());
                orgClassTestResult.setStudentId(orgClassTestResultRequest.getStudentId());
                orgClassTestResult.setTestId(orgClassTestResultRequest.getTestId());
                orgClassTestResult.setTestRemark(orgClassTestResultRequest.getTestRemark());
            }

            result = orgClassTestResultService.addOrgClassTestResultBatch(orgClassTestResultRequest.getOrgClassTestResultList());

            log(LogTypeEnum.LOG_TYPE_CLASS_SETTINGS, getLoginUser().getOrgId(), "添加或者保存学元评测结果[" + orgClassTestResultRequest.getStudentId() + "]");

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    private int checkClassSign(List<OrgAttendance> orgAttendanceList, int userId, String inDate, int inRole) {
        int total = 0;

        for (OrgAttendance orgAttendance : orgAttendanceList) {
            if (inRole == RoleEnum.ROLE_STUDENT.getCode()) {
                if (orgAttendance.getInDate().equals(inDate) && orgAttendance.getInRole() == inRole) {
                    total++;
                }
            } else if (inRole == RoleEnum.ROLE_COACH.getCode()) {
                if (orgAttendance.getInDate().equals(inDate) && orgAttendance.getInRole() == inRole && orgAttendance.getInUserId() == userId) {
                    total++;
                }
            }
        }

        return total;
    }

    @Desc("上课进度")
    @RequestMapping(value = "/progress", method = RequestMethod.GET)
    public ModelAndView renderClassProgress(String classId, String status) throws Exception {

        ModelAndView modelAndView = new ModelAndView("Class/Progress");

        modelAndView.addObject("status", status);

        int totalOrgClassCount = orgClassService.queryOrgClassCount(null, null);
        List<OrgClass> orgClassList = orgClassService.queryOrgClassList(null, null, 0, totalOrgClassCount);
        modelAndView.addObject("orgClassList", orgClassList);

        if (classId == null) {
            return setModelAndView(modelAndView);
        }

        String currentDate = DateUtil.dateToString(new Date(), "");

        OrgClass orgClass = orgClassService.getOrgClass(Integer.parseInt(classId));

        modelAndView.addObject("orgClass", orgClass);

        List<OrgClassSchedule> orgClassScheduleList = orgClassScheduleService.queryOrgClassScheduleList(orgClass.getId());
        List<OrgAttendance> orgAttendanceListStudents = orgAttendanceService.queryClassSignLog(orgClass.getId(), RoleEnum.ROLE_STUDENT.getCode());
        List<OrgAttendance> orgAttendanceListCoach = orgAttendanceService.queryClassSignLog(orgClass.getId(), RoleEnum.ROLE_COACH.getCode());
        List<OrgClassStudents> orgClassStudentsList = orgClassStudentsService.queryOrgClassStudentsListByClassId(orgClass.getId());

        List<OrgClassSchedule> orgClassScheduleAllList = new ArrayList<>();
        List<OrgClassSchedule> orgClassScheduleStartList = new ArrayList<>();
        List<OrgClassSchedule> orgClassScheduleEndList = new ArrayList<>();
        for (OrgClassSchedule orgClassSchedule : orgClassScheduleList) {
            if (orgClassSchedule.getClassDate() != null) {
                if (orgClass.getStatus() == ClassStatusEnum.STATUS_START.getCode()) {
                    orgClassSchedule.setCount(orgClassStudentsList.size());

                    orgClassScheduleStartList.add(orgClassSchedule);
                } else if (orgClass.getStatus() == ClassStatusEnum.STATUS_END.getCode()) {
                    orgClassSchedule.setHasSigned(checkClassSign(orgAttendanceListCoach, orgClassSchedule.getCoachId(), orgClassSchedule.getClassDate(), RoleEnum.ROLE_COACH.getCode()));
                    orgClassSchedule.setCount(checkClassSign(orgAttendanceListStudents, 0, orgClassSchedule.getClassDate(), RoleEnum.ROLE_STUDENT.getCode()));

                    orgClassScheduleEndList.add(orgClassSchedule);
                } else {
                    if (orgClassSchedule.getClassDate().compareTo(currentDate) <= 0) {
                        orgClassSchedule.setHasSigned(checkClassSign(orgAttendanceListCoach, orgClassSchedule.getCoachId(), orgClassSchedule.getClassDate(), RoleEnum.ROLE_COACH.getCode()));
                        orgClassSchedule.setCount(checkClassSign(orgAttendanceListStudents, 0, orgClassSchedule.getClassDate(), RoleEnum.ROLE_STUDENT.getCode()));

                        orgClassScheduleEndList.add(orgClassSchedule);
                    } else {
                        orgClassSchedule.setCount(orgClassStudentsList.size());

                        orgClassScheduleStartList.add(orgClassSchedule);
                    }
                }

                orgClassScheduleAllList.add(orgClassSchedule);
            }
            else {
                if (orgClass.getStatus() == ClassStatusEnum.STATUS_WORKING.getCode()) {
                    continue;
                }
                for (String week : DateUtil.getDateScopeWeekList(orgClassSchedule.getStartDate(), orgClassSchedule.getEndDate(), orgClassSchedule.getClassWeek())) {
                    OrgClassSchedule orgClassSchedule1 = new OrgClassSchedule();

                    orgClassSchedule1.setClassDate(week);
                    orgClassSchedule1.setClassId(orgClassSchedule.getClassId());
                    orgClassSchedule1.setClassWeek(orgClassSchedule.getClassWeek());
                    orgClassSchedule1.setCoachId(orgClassSchedule.getCoachId());
                    orgClassSchedule1.setStartDate(orgClassSchedule.getStartDate());
                    orgClassSchedule1.setEndDate(orgClassSchedule.getEndDate());
                    orgClassSchedule1.setId(orgClassSchedule.getId());

                    if (orgClass.getStatus() == ClassStatusEnum.STATUS_START.getCode()) {
                        orgClassSchedule1.setCount(orgClassStudentsList.size());

                        orgClassScheduleStartList.add(orgClassSchedule1);
                    } else if (orgClass.getStatus() == ClassStatusEnum.STATUS_END.getCode()) {
                        orgClassSchedule1.setHasSigned(checkClassSign(orgAttendanceListCoach, orgClassSchedule1.getCoachId(), orgClassSchedule1.getClassDate(), RoleEnum.ROLE_COACH.getCode()));
                        orgClassSchedule1.setCount(checkClassSign(orgAttendanceListStudents, 0, orgClassSchedule1.getClassDate(), RoleEnum.ROLE_STUDENT.getCode()));

                        orgClassScheduleEndList.add(orgClassSchedule);
                    } else {
                        if (orgClassSchedule1.getClassDate().compareTo(currentDate) <= 0) {
                            orgClassSchedule1.setHasSigned(checkClassSign(orgAttendanceListCoach, orgClassSchedule1.getCoachId(), orgClassSchedule1.getClassDate(), RoleEnum.ROLE_COACH.getCode()));
                            orgClassSchedule1.setCount(checkClassSign(orgAttendanceListStudents, 0, orgClassSchedule1.getClassDate(), RoleEnum.ROLE_STUDENT.getCode()));

                            orgClassScheduleEndList.add(orgClassSchedule1);
                        } else {
                            orgClassSchedule1.setCount(orgClassStudentsList.size());

                            orgClassScheduleStartList.add(orgClassSchedule1);
                        }
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

    @Desc("查询班级签到")
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

            OrgClass orgClass = orgClassService.getOrgClass(orgAttendance.getInClassID());
            if (orgClass.getStatus() == ClassStatusEnum.STATUS_END.getCode() || orgClass.getStatus() == ClassStatusEnum.STATUS_START.getCode()) {
                return new ResponseBean(false);
            }

            int result = orgAttendanceService.toSignin(orgAttendance);

            log(LogTypeEnum.LOG_TYPE_CLASS_SETTINGS, getLoginUser().getOrgId(), orgAttendance.getCreateTime() + "，班级[" + orgClass.getClassName() + "]签到");

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

        int classCount = orgClassService.queryOrgClassCount(null, null);
        List<OrgClass> classList = orgClassService.queryOrgClassList(null, null, 0, classCount);
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

        int classCount = orgClassService.queryOrgClassCount(null, null);
        List<OrgClass> classList = orgClassService.queryOrgClassList(null, null, 0, classCount);
        modelAndView.addObject("classList", classList);

        List<OrgVenues> orgVenuesList = orgVenuesService.queryOrgVenuesList(1);
        modelAndView.addObject("orgVenuesList", orgVenuesList);

        return setModelAndView(modelAndView);
    }

}
