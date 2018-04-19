package com.training.h5.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.annotation.NotProtected;
import com.training.core.common.bean.ResponseBean;
import com.training.core.common.config.WebConfig;
import com.training.core.common.constant.IPlatformConstant;
import com.training.core.common.enums.*;
import com.training.core.common.exception.MessageException;
import com.training.core.common.util.DataCryptUtil;
import com.training.core.common.util.DateUtil;
import com.training.core.repo.po.*;
import com.training.core.service.*;
import com.training.h5.response.OrgClassResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by wangjun on 2017/5/2.
 */
@Controller
@RequestMapping("/student/apply")
public class StudentApplyController extends BaseController {

    @Resource
    private OrgClassService orgClassService;

    @Resource
    private OrgClassStudentsService orgClassStudentsService;

    @Resource
    private OrgCoachesService orgCoachesService;

    @Resource
    private OrgCoursesService orgCoursesService;

    @Resource
    private OrgVenuesService orgVenuesService;

    @Resource
    private OrgClassScheduleService orgClassScheduleService;

    @Resource
    private OrgStudentsService orgStudentsService;

    @Resource
    private OrgOrdersService orgOrdersService;

    private OrgStudents getLoginStudents() {
        return (OrgStudents)(super.getRequest().getSession().getAttribute(IPlatformConstant.LOGIN_USER));
    }

    @Desc("课程列表")
    @NotProtected
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView renderApplyList() {

        ModelAndView modelAndView = new ModelAndView("Student/Apply/List");

        int count = orgClassService.queryOrgClassCount(null, ClassStatusEnum.STATUS_START.getCode());
        List<OrgClass> orgClassList = orgClassService.queryOrgClassList(null, ClassStatusEnum.STATUS_START.getCode(), 0, count);

        List<OrgClassResponse> orgClassResponseList = new ArrayList<>();
        for (OrgClass orgClass : orgClassList) {
            OrgClassResponse orgClassResponse = new OrgClassResponse();

            orgClassResponse.setOrgClass(orgClass);
            orgClassResponse.setOrgCoaches(orgCoachesService.getOrgCoaches(orgClass.getCoachId()));
            orgClassResponse.setOrgClassStudentsLength(orgClassStudentsService.queryOrgClassStudentsListByClassId(orgClass.getId()).size());

            orgClassResponseList.add(orgClassResponse);
        }

        modelAndView.addObject("orgClassList", orgClassResponseList);
        modelAndView.addObject("orgClassListLength", orgClassResponseList.size());

        return modelAndView;
    }

    @Desc("课程详情")
    @NotProtected
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ModelAndView renderApplyDetail(String classId) throws Exception {

        ModelAndView modelAndView = new ModelAndView("Student/Apply/Detail");

        if (classId == null) {
            classId = "0";
        }

        OrgClass orgClass = orgClassService.getOrgClass(Integer.parseInt(classId));
        modelAndView.addObject("orgClass", orgClass);

        OrgCoaches orgCoaches = orgCoachesService.getOrgCoaches(orgClass.getCoachId());
        modelAndView.addObject("orgCoaches", orgCoaches);

        OrgCourses orgCourses = orgCoursesService.getOrgCourses(orgClass.getCourseId());
        modelAndView.addObject("orgCourses", orgCourses);

        OrgVenues orgVenues = orgVenuesService.getOrgVenues(orgClass.getVenueId());
        modelAndView.addObject("orgVenues", orgVenues);

        List<OrgClassStudents> orgClassStudentsList = orgClassStudentsService.queryOrgClassStudentsListByClassId(orgClass.getId());
        modelAndView.addObject("orgClassStudentsLength", orgClassStudentsList.size());

        OrgStudents orgStudents = getLoginStudents();
        modelAndView.addObject("orgStudents", orgStudents);

        if (orgStudents != null) {
            OrgClassStudents orgClassStudents = orgClassStudentsService.getOrgClassStudents(orgClass.getId(), orgStudents.getId());
            modelAndView.addObject("isClassStudent", orgClassStudents != null);
        }

        List<OrgClassSchedule> orgClassScheduleList = orgClassScheduleService.queryOrgClassScheduleList(orgClass.getId());
        if (orgClassScheduleList.size() == 0) {
            modelAndView.addObject("orgClassScheduleAllList", orgClassScheduleList);

            return modelAndView;
        }

        List<OrgClassSchedule> orgClassScheduleAllList = new ArrayList<>();
        for (OrgClassSchedule orgClassSchedule : orgClassScheduleList) {
            if (orgClassSchedule.getClassDate() != null) {
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

        return modelAndView;
    }

    @Desc("课程确认")
    @NotProtected
    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public ModelAndView renderApplyConfirm(String classId) {

        ModelAndView modelAndView = new ModelAndView("Student/Apply/Confirm");

        if (classId == null) {
            classId = "0";
        }

        OrgStudents orgStudents = getLoginStudents();
        modelAndView.addObject("orgStudents", orgStudents);

        OrgClass orgClass = orgClassService.getOrgClass(Integer.parseInt(classId));
        modelAndView.addObject("orgClass", orgClass);

        if (orgStudents != null) {
            OrgClassStudents orgClassStudents = orgClassStudentsService.getOrgClassStudents(orgClass.getId(), orgStudents.getId());
            modelAndView.addObject("isClassStudent", orgClassStudents != null);
        }

        return modelAndView;
    }

    @Desc("保存学生")
    @NotProtected
    @ResponseBody
    @RequestMapping(value = "/addStudents", method = RequestMethod.POST)
    public ResponseBean addStudents(OrgStudents orgStudents) {
        try {
            int count = orgStudentsService.queryOrgStudentsListCount(null, orgStudents.getMobile(), null);
            if (count > 0) {
                return new ResponseBean("该手机号已经被占用");
            }

            orgStudents.setPassword(DataCryptUtil.encrypt(WebConfig.getUserDefaultPassword()));
            orgStudents.setCreateTime(DateUtil.getNowDate());
            orgStudents.setUpdateTime(DateUtil.getNowDate());
            int result = orgStudentsService.addOrgStudents(orgStudents);

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

    @Desc("保存学生分班")
    @NotProtected
    @ResponseBody
    @RequestMapping(value = "/addClassStudents", method = RequestMethod.POST)
    public ResponseBean addClassStudents(OrgClassStudents orgClassStudents) {
        try {

            int result;
            String orderNo = UUID.randomUUID().toString();

            OrgClass orgClass = orgClassService.getOrgClass(orgClassStudents.getClassId());
            int amount = orgClass.getClassPrice();

            orgClassStudents.setOrderNo(orderNo);
            orgClassStudents.setStatus(StatusEnum.STATUS_OK.getCode());
            orgClassStudents.setCreateTime(DateUtil.getNowDate());
            result = orgClassStudentsService.addOrgClassStudents(orgClassStudents);

            if (result > 0) {
                OrgOrders orgOrders = new OrgOrders();

                orgOrders.setOrderNo(orderNo);
                orgOrders.setOrderAmount(amount);
                orgOrders.setPayAmount(0);
                orgOrders.setPayUserId(orgClassStudents.getStudentId());
                orgOrders.setOperateId(-1);
                orgOrders.setCreateTime(DateUtil.getNowDate());
                orgOrders.setUpdateTime(DateUtil.getNowDate());

                orgOrders.setOrderType(OrderTypeEnum.ORDER_TYPE_CLASS.getCode());
                orgOrders.setOrderStatus(OrderStatusEnum.ORDER_STATUS_UNPAID.getCode());
                result = orgOrdersService.addOrgOrders(orgOrders);

                if (result <= 0) {
                    return new ResponseBean("生成支付订单失败");
                }
            } else {
                return new ResponseBean("分配班级失败");
            }

            Map map = new HashMap();
            map.put("orderNo", orderNo);
            map.put("orderAmount", amount);

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("订单支付")
    @NotProtected
    @ResponseBody
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public ResponseBean renderOrderPay(OrgOrders orgOrders) {
        try {
            orgOrders.setUpdateTime(DateUtil.getNowDate());
            orgOrders.setOrderStatus(OrderStatusEnum.ORDER_STATUS_PAID.getCode());
            orgOrders.setOperateId(-1);

            int result = orgOrdersService.saveOrgOrders(orgOrders);

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("课程状态")
    @NotProtected
    @RequestMapping(value = "/state", method = RequestMethod.GET)
    public ModelAndView renderApplyState(String classId) {

        ModelAndView modelAndView = new ModelAndView("Student/Apply/State");

        if (classId == null) {
            classId = "0";
        }

        OrgClass orgClass = orgClassService.getOrgClass(Integer.parseInt(classId));
        modelAndView.addObject("orgClass", orgClass);

        return modelAndView;
    }

}
