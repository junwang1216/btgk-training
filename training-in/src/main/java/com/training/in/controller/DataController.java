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
import com.training.in.request.OrgDataRequest;
import com.training.in.response.OrgBalanceDataResponse;
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
 * Created by wangjun on 2017/5/1.
 */
@Controller
@RequestMapping("/admin/data")
public class DataController extends BaseController {

    @Resource
    private OrgOrdersService orgOrdersService;

    @Resource
    private OrgBalanceSettingsService orgBalanceSettingsService;

    private ModelAndView setModelAndView(ModelAndView modelAndView) {
        return modelAndView.addObject("Admin", super.getRequest().getSession().getAttribute(IPlatformConstant.LOGIN_USER));
    }

    private Map getStartEndTime(OrgDataRequest orgDataRequest) {
        Map map = new HashMap();

        String startTime, endTime;
        switch (orgDataRequest.getTypeTime()) {
            case "year":
                startTime = DateUtil.getCurrentYearEndTime() + "-01-01 00:00:00";
                endTime = DateUtil.getCurrentYearEndTime() + "-12-31 23:59:59";
                break;
            case "month":
                startTime = DateUtil.getTimesMonthmorning();
                endTime = DateUtil.getTimesMonthnight();
                break;
            case "day":
                endTime = DateUtil.getNowDate().replaceAll("(\\d{4})-(\\d{2})-(\\d{2})\\s(\\d{2}):(\\d{2}):(\\d{2})", "$1-$2-$3 23:59:00");
                startTime = DateUtil.getNowDate().replaceAll("(\\d{4})-(\\d{2})-(\\d{2})\\s(\\d{2}):(\\d{2}):(\\d{2})", "$1-$2-$3 00:00:00");
                break;
            default:
                startTime = orgDataRequest.getStartTime() + " 00:00:00";
                endTime = orgDataRequest.getEndTime() + " 23:00:00";
        }

        map.put("startTime", startTime);
        map.put("endTime", endTime);

        return map;
    }

    @Desc("收入流水")
    @RequestMapping(value = "/income", method = RequestMethod.GET)
    public ModelAndView renderDataIncome(OrgDataRequest orgDataRequest) {

        ModelAndView modelAndView = new ModelAndView("Data/Income");

        if ((orgDataRequest.getStartTime() == null || orgDataRequest.getEndTime() == null) && orgDataRequest.getTypeTime() == null) {
            orgDataRequest.setTypeTime("day");
        }

        modelAndView.addObject("condition", orgDataRequest);

        String startTime = getStartEndTime(orgDataRequest).get("startTime").toString();
        String endTime = getStartEndTime(orgDataRequest).get("endTime").toString();

        List<OrgOrders> orgOrdersList = orgOrdersService.queryOrdersByDate(startTime, endTime, null);

        List<OrgBalanceDataResponse> orgBalanceDataResponseList = new ArrayList<>();
        for (OrgOrders orgOrders : orgOrdersList) {
            OrgBalanceDataResponse orgBalanceDataResponse = new OrgBalanceDataResponse();

            orgBalanceDataResponse.setBalanceNo(orgOrders.getOrderNo());
            orgBalanceDataResponse.setBalanceAmount(orgOrders.getPayAmount());
            orgBalanceDataResponse.setBalanceSource(2);
            orgBalanceDataResponse.setBalanceTime(orgOrders.getUpdateTime());
            orgBalanceDataResponse.setBalanceType(OrderTypeEnum.forValue(orgOrders.getOrderType()).getDesc());
            if (orgOrders.getPayType() != null) {
                orgBalanceDataResponse.setBalancePayType(PayTypeEnum.forValue(orgOrders.getPayType()).getDesc());
            } else {
                orgBalanceDataResponse.setBalancePayType("--");
            }

            orgBalanceDataResponseList.add(orgBalanceDataResponse);
        }
        modelAndView.addObject("orgBalanceDataResponseList", orgBalanceDataResponseList);

        modelAndView.addObject("BalanceTypeEnum", EnumUtils.getEnumList(OrderTypeEnum.class));
        modelAndView.addObject("PayTypeEnum", EnumUtils.getEnumList(PayTypeEnum.class));

        return setModelAndView(modelAndView);
    }

    @Desc("支出流水")
    @RequestMapping(value = "/expend", method = RequestMethod.GET)
    public ModelAndView renderDataExpend(OrgDataRequest orgDataRequest) {

        ModelAndView modelAndView = new ModelAndView("Data/Expend");

        if ((orgDataRequest.getStartTime() == null || orgDataRequest.getEndTime() == null) && orgDataRequest.getTypeTime() == null) {
            orgDataRequest.setTypeTime("day");
        }

        modelAndView.addObject("condition", orgDataRequest);

        String startTime = getStartEndTime(orgDataRequest).get("startTime").toString();
        String endTime = getStartEndTime(orgDataRequest).get("endTime").toString();

        List<OrgOrders> orgOrdersList = orgOrdersService.queryOrdersByDate(startTime, endTime, OrderStatusEnum.ORDER_STATUS_REFUND.getCode());

        List<OrgBalanceDataResponse> orgBalanceDataResponseList = new ArrayList<>();
        for (OrgOrders orgOrders : orgOrdersList) {
            OrgBalanceDataResponse orgBalanceDataResponse = new OrgBalanceDataResponse();

            orgBalanceDataResponse.setBalanceNo(orgOrders.getOrderNo());
            orgBalanceDataResponse.setBalanceAmount(orgOrders.getRefundAmount());
            orgBalanceDataResponse.setBalanceSource(2);
            orgBalanceDataResponse.setBalanceTime(orgOrders.getUpdateTime());
            orgBalanceDataResponse.setBalanceType(OrderTypeEnum.forValue(orgOrders.getOrderType()).getDesc());
            if (orgOrders.getPayType() != null) {
                orgBalanceDataResponse.setBalancePayType(PayTypeEnum.forValue(orgOrders.getRefundType()).getDesc());
            } else {
                orgBalanceDataResponse.setBalancePayType("--");
            }

            orgBalanceDataResponseList.add(orgBalanceDataResponse);
        }
        modelAndView.addObject("orgBalanceDataResponseList", orgBalanceDataResponseList);

        modelAndView.addObject("BalanceTypeEnum", EnumUtils.getEnumList(OrderTypeEnum.class));
        modelAndView.addObject("PayTypeEnum", EnumUtils.getEnumList(PayTypeEnum.class));

        return setModelAndView(modelAndView);
    }

    @Desc("收支统计")
    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    public ModelAndView renderDataSummary(OrgDataRequest orgDataRequest) {

        ModelAndView modelAndView = new ModelAndView("Data/Summary");

        if (orgDataRequest.getTypeTime() == null) {
            orgDataRequest.setTypeTime("month");
        }

        modelAndView.addObject("condition", orgDataRequest);

        String startTime;
        String endTime;
        List<OrgOrders> orgOrdersList;

        String titleShow;
        List<String> labelList = new ArrayList<>();
        int valuePaymentTotal = 0;
        List<Integer> valuePaymentList = new ArrayList<>();

        int valueRefundTotal = 0;
        List<Integer> valueRefundList = new ArrayList<>();
        switch (orgDataRequest.getTypeTime()) {
            case "prev_month":
            case "month":
                List<String> labelList3;

                if (orgDataRequest.getTypeTime().equals("prev_month")) {
                    labelList3 = DateUtil.getMonthDate(DateUtil.getAddMonth(-1) + "-01");
                } else {
                    labelList3 = DateUtil.getMonthDate(DateUtil.getAddMonth(0) + "-01");
                }

                startTime = labelList3.get(0) + " 00:00:00";
                endTime = labelList3.get(labelList3.size() - 1) + " 23:59:59";
                titleShow = labelList3.get(0).replaceAll("^(\\d{4})-(\\d{2})-(\\d{2})$", "$1年$2月$3日") + " 至 " + labelList3.get(labelList3.size() - 1).replaceAll("^(\\d{4})-(\\d{2})-(\\d{2})$", "$1年$2月$3日");

                orgOrdersList = orgOrdersService.queryOrdersByDate(startTime, endTime, null);

                for (String date : labelList3) {
                    String startDayTime = date + " 00:00:00";
                    String endDayTime = date + " 23:59:59";

                    labelList.add(date.replaceAll("^(\\d{4})-(\\d{2})-(\\d{2})$", "$3日"));

                    int payAmount = 0;
                    int refundAmount = 0;
                    for (OrgOrders orgOrders : orgOrdersList) {
                        if (orgOrders.getUpdateTime().compareTo(startDayTime) > 0 && orgOrders.getUpdateTime().compareTo(endDayTime) < 0) {
                            if (orgOrders.getPayAmount() != null) {
                                payAmount += orgOrders.getPayAmount();
                            }

                            if (orgOrders.getRefundAmount() != null) {
                                refundAmount += orgOrders.getRefundAmount();
                            }
                        }
                    }

                    valuePaymentList.add(payAmount);
                    valuePaymentTotal += payAmount;

                    valueRefundList.add(refundAmount);
                    valueRefundTotal += refundAmount;
                }

                break;
            case "prev_year":
            case "year":
            default:
                labelList.add("01月");
                labelList.add("02月");
                labelList.add("03月");
                labelList.add("04月");
                labelList.add("05月");
                labelList.add("06月");
                labelList.add("07月");
                labelList.add("08月");
                labelList.add("09月");
                labelList.add("10月");
                labelList.add("11月");
                labelList.add("12月");

                List<String> labelList2 = new ArrayList<>();

                if (orgDataRequest.getTypeTime().equals("prev_year")) {
                    labelList2.add(DateUtil.getAddYear(-1) + "-01");
                    labelList2.add(DateUtil.getAddYear(-1) + "-02");
                    labelList2.add(DateUtil.getAddYear(-1) + "-03");
                    labelList2.add(DateUtil.getAddYear(-1) + "-04");
                    labelList2.add(DateUtil.getAddYear(-1) + "-05");
                    labelList2.add(DateUtil.getAddYear(-1) + "-06");
                    labelList2.add(DateUtil.getAddYear(-1) + "-07");
                    labelList2.add(DateUtil.getAddYear(-1) + "-08");
                    labelList2.add(DateUtil.getAddYear(-1) + "-09");
                    labelList2.add(DateUtil.getAddYear(-1) + "-10");
                    labelList2.add(DateUtil.getAddYear(-1) + "-11");
                    labelList2.add(DateUtil.getAddYear(-1) + "-12");
                } else {
                    labelList2.add(DateUtil.getAddYear(0) + "-01");
                    labelList2.add(DateUtil.getAddYear(0) + "-02");
                    labelList2.add(DateUtil.getAddYear(0) + "-03");
                    labelList2.add(DateUtil.getAddYear(0) + "-04");
                    labelList2.add(DateUtil.getAddYear(0) + "-05");
                    labelList2.add(DateUtil.getAddYear(0) + "-06");
                    labelList2.add(DateUtil.getAddYear(0) + "-07");
                    labelList2.add(DateUtil.getAddYear(0) + "-08");
                    labelList2.add(DateUtil.getAddYear(0) + "-09");
                    labelList2.add(DateUtil.getAddYear(0) + "-10");
                    labelList2.add(DateUtil.getAddYear(0) + "-11");
                    labelList2.add(DateUtil.getAddYear(0) + "-12");
                }

                startTime = labelList2.get(0) + "-01 00:00:00";
                endTime = labelList2.get(labelList2.size() - 1) + "-31 23:59:59";
                titleShow = (labelList2.get(0) + "-01").replaceAll("^(\\d{4})-(\\d{2})-(\\d{2})$", "$1年$2月$3日") + " 至 " + (labelList2.get(labelList2.size() - 1) + "-31").replaceAll("^(\\d{4})-(\\d{2})-(\\d{2})$", "$1年$2月$3日");

                orgOrdersList = orgOrdersService.queryOrdersByDate(startTime, endTime, null);

                for (String date : labelList2) {
                    String startDayTime = date + "-01 00:00:00";
                    String endDayTime = date + "-31 23:59:59";

                    int payAmount = 0;
                    int refundAmount = 0;
                    for (OrgOrders orgOrders : orgOrdersList) {
                        if (orgOrders.getUpdateTime().compareTo(startDayTime) > 0 && orgOrders.getUpdateTime().compareTo(endDayTime) < 0) {
                            if (orgOrders.getPayAmount() != null) {
                                payAmount += orgOrders.getPayAmount();
                            }

                            if (orgOrders.getRefundAmount() != null) {
                                refundAmount += orgOrders.getRefundAmount();
                            }
                        }
                    }

                    valuePaymentList.add(payAmount);
                    valuePaymentTotal += payAmount;

                    valueRefundList.add(refundAmount);
                    valueRefundTotal += refundAmount;
                }
        }

        modelAndView.addObject("labelList", labelList);

        modelAndView.addObject("valuePaymentTotal", valuePaymentTotal);
        modelAndView.addObject("valuePaymentList", valuePaymentList);

        modelAndView.addObject("valueRefundTotal", valueRefundTotal);
        modelAndView.addObject("valueRefundList", valueRefundList);

        modelAndView.addObject("startTime", startTime);
        modelAndView.addObject("endTime", endTime);

        modelAndView.addObject("titleShow", titleShow);

        modelAndView.addObject("BalanceTypeEnum", EnumUtils.getEnumList(OrderTypeEnum.class));
        modelAndView.addObject("PayTypeEnum", EnumUtils.getEnumList(PayTypeEnum.class));

        return setModelAndView(modelAndView);
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
