package com.training.in.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.bean.ResponseBean;
import com.training.core.common.constant.IPlatformConstant;
import com.training.core.common.enums.OrderStatusEnum;
import com.training.core.common.enums.OrderTypeEnum;
import com.training.core.common.enums.PayTypeEnum;
import com.training.core.common.exception.MessageException;
import com.training.core.common.util.DateUtil;
import com.training.core.repo.po.*;
import com.training.core.service.*;
import com.training.in.request.OrgBalanceSettingsRequest;
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
import java.util.List;

/**
 * Created by wangjun on 2017/5/1.
 */
@Controller
@RequestMapping("/admin/data")
public class DataController extends BaseController {

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

    @Resource
    private OrgBalanceSettingsService orgBalanceSettingsService;

    private ModelAndView setModelAndView(ModelAndView modelAndView) {
        return modelAndView.addObject("Admin", super.getRequest().getSession().getAttribute(IPlatformConstant.LOGIN_USER));
    }

    @Desc("我的订单")
    @RequestMapping(value = "/orders", method = RequestMethod.GET)
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
    @RequestMapping(value = "/orders/detail", method = RequestMethod.GET)
    public ModelAndView renderDataOrdersDetail(int orderId) {

        ModelAndView modelAndView = new ModelAndView("Data/OrdersDetail");

        OrgOrders orgOrders = orgOrdersService.getOrgOrders(orderId);
        modelAndView.addObject("orgOrders", orgOrders);

        OrgStudents orgStudents = orgStudentsService.getOrgStudents(orgOrders.getPayUserId());
        modelAndView.addObject("orgStudents", orgStudents);

        OrgOperator orgOperator = orgOperatorService.selectByPrimaryKey(orgOrders.getOperateId());
        modelAndView.addObject("orgOperator", orgOperator);

        OrgClassStudents orgClassStudents = orgClassStudentsService.getOrgClassStudents(orgOrders.getPayNo());
        OrgClass orgClass = orgClassService.getOrgClass(orgClassStudents.getClassId());
        modelAndView.addObject("orgClass", orgClass);

        OrgCoaches orgCoaches = orgCoachesService.getOrgCoaches(orgClass.getCoachId());
        modelAndView.addObject("orgCoaches", orgCoaches);

        OrgVenues orgVenues = orgVenuesService.getOrgVenues(orgClass.getVenueId());
        modelAndView.addObject("orgVenues", orgVenues);

        OrgCourses orgCourses = orgCoursesService.getOrgCourses(orgClass.getCourseId());
        modelAndView.addObject("orgCourses", orgCourses);

        modelAndView.addObject("OrderTypeEnum", EnumUtils.getEnumList(OrderTypeEnum.class));
        modelAndView.addObject("OrderStatusEnum", EnumUtils.getEnumList(OrderStatusEnum.class));
        modelAndView.addObject("PayTypeEnum", EnumUtils.getEnumList(PayTypeEnum.class));

        return setModelAndView(modelAndView);
    }


    @Desc("收入流水")
    @RequestMapping(value = "/income", method = RequestMethod.GET)
    public String renderDataIncome() {
        return "Data/Income";
    }

    @Desc("支出流水")
    @RequestMapping(value = "/expend", method = RequestMethod.GET)
    public String renderDataExpend() {
        return "Data/Expend";
    }

    @Desc("收支统计")
    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    public String renderDataSummary() {
        return "Data/Summary";
    }

    @Desc("收支类型设置")
    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public ModelAndView renderDataSettings() {

        ModelAndView modelAndView = new ModelAndView("Data/Settings");

        List<OrgBalanceSettings> orgBalanceIncomeSettingsList = orgBalanceSettingsService.queryOrgBalanceSettingsList(1);
        modelAndView.addObject("incomeSettingList", orgBalanceIncomeSettingsList);

        List<OrgBalanceSettings> orgBalanceExpendSettingsList = orgBalanceSettingsService.queryOrgBalanceSettingsList(2);
        modelAndView.addObject("expendSettingList", orgBalanceExpendSettingsList);

        return setModelAndView(modelAndView);
    }

    @Desc("保存学生")
    @ResponseBody
    @RequestMapping(value = "/saveBalanceSettings", method = RequestMethod.POST)
    public ResponseBean saveBalanceSettings(@RequestBody OrgBalanceSettingsRequest orgBalanceSettingsRequest) {
        try {

            orgBalanceSettingsService.clearAllByBalanceType(orgBalanceSettingsRequest.getBalanceType());

            int result;

            for (OrgBalanceSettings orgBalanceSettings : orgBalanceSettingsRequest.getOrgBalanceSettingsList()) {
                orgBalanceSettings.setCreateTime(DateUtil.getNowDate());
                orgBalanceSettings.setUpdateTime(DateUtil.getNowDate());
                orgBalanceSettings.setBalanceType(orgBalanceSettingsRequest.getBalanceType());
                orgBalanceSettings.setStatus(1);
            }
            result = orgBalanceSettingsService.addOrgBalanceSettingsBatch(orgBalanceSettingsRequest.getOrgBalanceSettingsList());

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
