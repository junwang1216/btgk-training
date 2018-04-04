package com.training.in.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.bean.ResponseBean;
import com.training.core.common.constant.IPlatformConstant;
import com.training.core.common.enums.*;
import com.training.core.common.exception.MessageException;
import com.training.core.common.util.DateUtil;
import com.training.core.common.util.Page;
import com.training.core.repo.po.*;
import com.training.core.service.*;
import com.training.in.request.OrgClassStudentsRequest;
import com.training.in.request.OrgStudentsRequest;
import com.training.in.response.OrgClassResponse;
import com.training.in.response.OrgStudentsResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;

/**
 * 学生管理
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

    @Resource
    private OrgOrdersService orgOrdersService;

    private ModelAndView setModelAndView(ModelAndView modelAndView) {
        return modelAndView.addObject("Admin", super.getRequest().getSession().getAttribute(IPlatformConstant.LOGIN_USER));
    }

    @Desc("学生列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView renderStudentsList(OrgStudentsRequest orgStudentsRequest) {

        ModelAndView modelAndView = new ModelAndView("Students/List");

        modelAndView.addObject("conditions", orgStudentsRequest);

        int orgClassCount = orgClassService.queryOrgClassCount(null, null);
        List<OrgClass> orgClassList = orgClassService.queryOrgClassList(null, null, 0, orgClassCount);

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

        int total = orgStudentsService.queryOrgStudentsListCount(orgStudentsRequest.getRealName(), orgStudentsRequest.getMobile(), orgStudentsRequest.getClassId());
        int start = orgStudentsRequest.getPage() < 1 ? 0 : orgStudentsRequest.getPage() - 1;
        int pageSize = 10;
        List<OrgStudents> orgStudentsList = orgStudentsService.queryOrgStudentsList(orgStudentsRequest.getRealName(), orgStudentsRequest.getMobile(), orgStudentsRequest.getClassId(), start * pageSize, pageSize);

        List<OrgStudentsResponse> orgStudentsResponseList = new ArrayList<>();
        for (OrgStudents orgStudents : orgStudentsList) {
            OrgStudentsResponse orgStudentsResponse = new OrgStudentsResponse();

            orgStudentsResponse.setOrgStudents(orgStudents);
            List<OrgClassStudents> orgClassStudentsList = orgClassStudentsService.queryOrgClassStudentsListByStudentId(orgStudents.getId());

            if (orgClassStudentsList.size() <= 0) {
                orgStudentsResponse.setOrgClass(null);
                orgStudentsResponse.setOrgClassList(null);
                orgStudentsResponseList.add(orgStudentsResponse);
                continue;
            }

            orgStudentsResponse.setOrgClass(null);
            List<OrgClass> orgStudentsClassList = new ArrayList<>();
            for (OrgClassStudents orgClassStudents : orgClassStudentsList) {
                OrgClass orgClass = orgClassService.getOrgClass(orgClassStudents.getClassId());

                orgStudentsClassList.add(orgClass);

//                if (orgClass.getStatus() == ClassStatusEnum.STATUS_END.getCode()) {
//                    continue;
//                }

                orgStudentsResponse.setOrgClass(orgClass);
            }
            orgStudentsResponse.setOrgClassList(orgStudentsClassList);
            orgStudentsResponseList.add(orgStudentsResponse);
        }
        modelAndView.addObject("orgStudentsList", orgStudentsResponseList);

        Page page = new Page(pageSize, total);
        page.setPage(orgStudentsRequest.getPage());

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

        List<OrgClass> orgClassList = orgClassService.queryOrgClassList(null, null, 0, 1000);

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

            log(LogTypeEnum.LOG_TYPE_STUDENTS_SETTINGS, getLoginUser().getOrgId(), "添加或者修改学员[" + orgStudents.getRealName() + "]信息");

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
    @ResponseBody
    @RequestMapping(value = "/saveClassStudents", method = RequestMethod.POST)
    public ResponseBean saveClassStudents(@RequestBody OrgClassStudentsRequest orgClassStudentsRequest) {
        try {

            int result;

            OrgStudents orgStudents = orgStudentsService.getOrgStudents(orgClassStudentsRequest.getStudentId());
            List<OrgClassStudents> orgClassStudentsList = new ArrayList<>();
            int amount = 0;
            String orderNo = UUID.randomUUID().toString();

            for (String classId : orgClassStudentsRequest.getClassIds().split(",")) {
                OrgClassStudents orgClassStudents = new OrgClassStudents();
                OrgClass orgClass = orgClassService.getOrgClass(Integer.parseInt(classId));

                orgClassStudents.setClassId(Integer.parseInt(classId));
                orgClassStudents.setStudentId(orgClassStudentsRequest.getStudentId());
                orgClassStudents.setCreateTime(DateUtil.getNowDate());
                orgClassStudents.setOrderNo(orderNo);
                orgClassStudents.setStatus(StatusEnum.STATUS_OK.getCode());

                orgClassStudentsList.add(orgClassStudents);
                amount += orgClass.getClassPrice();
            }

            OrgOrders orgOrders = new OrgOrders();

            orgOrders.setOrderNo(orderNo);
            orgOrders.setOrderAmount(amount);
            orgOrders.setPayAmount(0);
            orgOrders.setPayUserId(orgClassStudentsRequest.getStudentId());
            orgOrders.setOperateId(getLoginUser().getId());
            orgOrders.setCreateTime(DateUtil.getNowDate());
            orgOrders.setUpdateTime(DateUtil.getNowDate());

            if (orgClassStudentsRequest.isState()) {
                result = orgClassStudentsService.addOrgClassStudentsBath(orgClassStudentsList);

                log(LogTypeEnum.LOG_TYPE_STUDENTS_SETTINGS, getLoginUser().getOrgId(), "学员[" + orgStudents.getRealName() + "]分班");

                if (result > 0) {
                    orgOrders.setOrderType(OrderTypeEnum.ORDER_TYPE_CLASS.getCode());
                    orgOrders.setOrderStatus(OrderStatusEnum.ORDER_STATUS_UNPAID.getCode());
                    orgOrdersService.addOrgOrders(orgOrders);
                }
            } else {
                // TODO 废弃
                result = orgClassStudentsService.delOrgClassStudentsBatch(orgClassStudentsList);

                log(LogTypeEnum.LOG_TYPE_STUDENTS_SETTINGS, getLoginUser().getOrgId(), "学员[" + orgStudents.getRealName() + "]退费");

                if (result > 0) {
                    orgOrders.setOrderType(OrderTypeEnum.ORDER_TYPE_REFUND.getCode());
                    orgOrders.setOrderStatus(OrderStatusEnum.ORDER_STATUS_REFUND.getCode());
                    orgOrdersService.addOrgOrders(orgOrders);
                }
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

    @Desc("学生批量添加")
    @RequestMapping(value = "/batch", method = RequestMethod.GET)
    public String renderStudentsBatch() {
        return "Students/Batch";
    }

}
