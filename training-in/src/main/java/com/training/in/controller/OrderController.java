package com.training.in.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.bean.ResponseBean;
import com.training.core.common.constant.IPlatformConstant;
import com.training.core.common.enums.OrderStatusEnum;
import com.training.core.common.enums.OrderTypeEnum;
import com.training.core.common.enums.PayTypeEnum;
import com.training.core.common.enums.StatusEnum;
import com.training.core.common.exception.MessageException;
import com.training.core.common.util.DateUtil;
import com.training.core.repo.po.*;
import com.training.core.service.*;
import com.training.in.response.OrgOrdersResponse;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
 * Created by wangjun on 2018/2/1.
 */
@Controller
@RequestMapping("/admin/order")
public class OrderController extends BaseController {

    @Resource
    private OrgOrdersService orgOrdersService;

    @Resource
    private OrgOperatorService orgOperatorService;

    @Resource
    private OrgStudentsService orgStudentsService;

    @Resource
    private OrgClassService orgClassService;

    @Resource
    private OrgVenuesService orgVenuesService;

    @Resource
    private OrgCoursesService orgCoursesService;

    @Resource
    private OrgCoachesService orgCoachesService;

    @Resource
    private OrgClassStudentsService orgClassStudentsService;

    private ModelAndView setModelAndView(ModelAndView modelAndView) {
        return modelAndView.addObject("Admin", super.getRequest().getSession().getAttribute(IPlatformConstant.LOGIN_USER));
    }

    @Desc("我的订单")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView renderDataOrders() {

        ModelAndView modelAndView = new ModelAndView("Data/Orders");

        List<OrgOrders> orgOrdersList = orgOrdersService.queryOrders();

        List<OrgOrdersResponse> orgOrdersResponseList = new ArrayList<>();
        for (OrgOrders orgOrders : orgOrdersList) {
            OrgOrdersResponse orgOrdersResponse = new OrgOrdersResponse();

            orgOrdersResponse.setOrgOrders(orgOrders);
            orgOrdersResponse.setOrgStudents(orgStudentsService.getOrgStudents(orgOrders.getPayUserId()));

            orgOrdersResponseList.add(orgOrdersResponse);
        }
        modelAndView.addObject("orgOrdersList", orgOrdersResponseList);

        modelAndView.addObject("OrderTypeEnum", EnumUtils.getEnumList(OrderTypeEnum.class));
        modelAndView.addObject("OrderStatusEnum", EnumUtils.getEnumList(OrderStatusEnum.class));
        modelAndView.addObject("PayTypeEnum", EnumUtils.getEnumList(PayTypeEnum.class));

        return setModelAndView(modelAndView);
    }

    @Desc("我的订单详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ModelAndView renderDataOrdersDetail(int orderId) {

        ModelAndView modelAndView = new ModelAndView("Data/OrdersDetail");

        OrgOrders orgOrders = orgOrdersService.getOrgOrders(orderId);
        modelAndView.addObject("orgOrders", orgOrders);

        OrgStudents orgStudents = orgStudentsService.getOrgStudents(orgOrders.getPayUserId());
        modelAndView.addObject("orgStudents", orgStudents);

        OrgOperator orgOperator = orgOperatorService.selectByPrimaryKey(orgOrders.getOperateId());
        modelAndView.addObject("orgOperator", orgOperator);

        List<OrgClassStudents> orgClassStudentsList = orgClassStudentsService.queryOrgClassStudentsListByOrderNo(orgOrders.getOrderNo());
        List<Map> mapList = new ArrayList<>();
        for (OrgClassStudents orgClassStudents : orgClassStudentsList) {
            Map map = new HashMap();

            OrgClass orgClass = orgClassService.getOrgClass(orgClassStudents.getClassId());
            OrgCoaches orgCoaches = orgCoachesService.getOrgCoaches(orgClass.getCoachId());
            OrgVenues orgVenues = orgVenuesService.getOrgVenues(orgClass.getVenueId());
            OrgCourses orgCourses = orgCoursesService.getOrgCourses(orgClass.getCourseId());

            map.put("orgClass", orgClass);
            map.put("orgCoaches", orgCoaches);
            map.put("orgVenues", orgVenues);
            map.put("orgCourses", orgCourses);
            mapList.add(map);
        }
        modelAndView.addObject("orgClassList", mapList);

        modelAndView.addObject("OrderTypeEnum", EnumUtils.getEnumList(OrderTypeEnum.class));
        modelAndView.addObject("OrderStatusEnum", EnumUtils.getEnumList(OrderStatusEnum.class));
        modelAndView.addObject("PayTypeEnum", EnumUtils.getEnumList(PayTypeEnum.class));

        return setModelAndView(modelAndView);
    }

    @Desc("订单支付")
    @ResponseBody
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public ResponseBean renderOrderPay(OrgOrders orgOrders) {
        try {
            orgOrders.setUpdateTime(DateUtil.getNowDate());
            orgOrders.setOrderStatus(OrderStatusEnum.ORDER_STATUS_PAID.getCode());
            orgOrders.setOperateId(getLoginUser().getId());

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

    @Desc("订单取消")
    @ResponseBody
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public ResponseBean renderOrderCancel(@RequestBody OrgOrders orgOrders) {
        try {

            orgOrders.setUpdateTime(DateUtil.getNowDate());
            orgOrders.setOrderStatus(OrderStatusEnum.ORDER_STATUS_CANCEL.getCode());
            orgOrders.setOperateId(getLoginUser().getId());

            int result = orgOrdersService.saveOrgOrders(orgOrders);

            if (result > 0) {
                OrgClassStudents orgClassStudents = new OrgClassStudents();
                orgClassStudents.setStatus(StatusEnum.STATUS_ERROR.getCode());
                orgClassStudents.setOrderNo(orgOrders.getOrderNo());

                result = orgClassStudentsService.saveOrgClassStudents(orgClassStudents);
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

    @Desc("订单退款")
    @ResponseBody
    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    public ResponseBean renderOrderRefund(OrgOrders orgOrders) {
        try {
            orgOrders.setUpdateTime(DateUtil.getNowDate());
            orgOrders.setOrderStatus(OrderStatusEnum.ORDER_STATUS_REFUND.getCode());
            orgOrders.setOperateId(getLoginUser().getId());

            int result = orgOrdersService.saveOrgOrders(orgOrders);

            if (result > 0) {
                OrgClassStudents orgClassStudents = new OrgClassStudents();
                orgClassStudents.setStatus(StatusEnum.STATUS_ERROR.getCode());
                orgClassStudents.setOrderNo(orgOrders.getOrderNo());

                result = orgClassStudentsService.saveOrgClassStudents(orgClassStudents);
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
}
