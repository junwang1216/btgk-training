package com.training.in.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.bean.ResponseBean;
import com.training.core.common.constant.IPlatformConstant;
import com.training.core.common.enums.LogTypeEnum;
import com.training.core.common.enums.OrderStatusEnum;
import com.training.core.common.enums.OrderTypeEnum;
import com.training.core.common.enums.PayTypeEnum;
import com.training.core.common.exception.MessageException;
import com.training.core.common.util.*;
import com.training.core.repo.po.*;
import com.training.core.service.*;
import com.training.in.request.OrgBalanceSettingsRequest;
import com.training.in.request.OrgDataRequest;
import com.training.in.request.OrgFinanceLogRequest;
import com.training.in.response.OrgBalanceDataResponse;
import com.training.in.response.OrgFinanceResponse;
import org.apache.commons.lang3.EnumUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

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

    @Resource
    private OrgFinanceEnumsService orgFinanceEnumsService;

    @Resource
    private OrgFinanceService orgFinanceService;

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

    private int getStringValue(Object value) {
        return value != null ? Integer.parseInt(value.toString()) : 0;
    }

    private String[] getStartEndTime(String typeTime) throws Exception {
        String startTime, endTime;

        switch (typeTime) {
            case "prev_day":
            case "day":
                String baseTime;

                if (typeTime.equals("prev_day")) {
                    baseTime = DateUtil.getAddDay(DateUtil.getNowDate(), -1);
                } else {
                    baseTime = DateUtil.getNowDate();
                }

                startTime = baseTime.replaceAll("(\\d{4})-(\\d{2})-(\\d{2})\\s(\\d{2}):(\\d{2}):(\\d{2})", "$1-$2-$3 00:00:00");
                endTime = baseTime.replaceAll("(\\d{4})-(\\d{2})-(\\d{2})\\s(\\d{2}):(\\d{2}):(\\d{2})", "$1-$2-$3 23:59:59");
                break;
            case "prev_week":
            case "week":
                String startBaseTime, endBaseTime;

                if (typeTime.equals("prev_week")) {
                    startBaseTime = DateUtil.getAddDay(DateUtil.getTimesWeekmorningStr(), -7);
                    endBaseTime = DateUtil.getAddDay(DateUtil.getTimesWeeknightStr(),-7);
                } else {
                    startBaseTime = DateUtil.getTimesWeekmorningStr();
                    endBaseTime = DateUtil.getTimesWeeknightStr();
                }

                startTime = startBaseTime.replaceAll("(\\d{4})-(\\d{2})-(\\d{2})\\s(\\d{2}):(\\d{2}):(\\d{2})", "$1-$2-$3 00:00:00");
                endTime = endBaseTime.replaceAll("(\\d{4})-(\\d{2})-(\\d{2})\\s(\\d{2}):(\\d{2}):(\\d{2})", "$1-$2-$3 23:59:59");
                break;
            case "prev_month":
            case "month":
                List<String> monthDate;

                if (typeTime.equals("prev_month")) {
                    monthDate = DateUtil.getMonthDate(DateUtil.getAddMonth(-1) + "-01");
                } else {
                    monthDate = DateUtil.getMonthDate(DateUtil.getAddMonth(0) + "-01");
                }

                startTime = monthDate.get(0) + " 00:00:00";
                endTime = monthDate.get(monthDate.size() - 1) + " 23:59:59";
                break;
            case "prev_period":
            case "period":
                int month = DateUtil.getCurrentMonth() - 1;
                int sep = typeTime.equals("prev_period") ? -3 - month % 3 : -month % 3;

                String startMonthTime = DateUtil.getAddMonth(sep) + "-01";
                String endMonthTime = DateUtil.getAddMonth(sep + 2) + "-31";

                startTime = startMonthTime + " 00:00:00";
                endTime = endMonthTime + " 23:59:59";
                break;
            case "prev_year":
            case "year":
            default:
                String baseYearTime = typeTime.equals("prev_year") ? DateUtil.getAddYear(-1) : DateUtil.getAddYear(0);

                startTime = baseYearTime + "-01-01 00:00:00";
                endTime = baseYearTime + "-12-31 23:59:59";
        }

        return new String[]{startTime, endTime};
    }

    private List<OrgFinanceResponse> formatOrgFinance(List<OrgFinance> orgFinanceList) {
        List<OrgFinanceResponse> orgFinanceResponseList = new ArrayList<>();

        Map checkTrainingData = new HashMap();
        Map checkVenueData = new HashMap();
        int i = 0, j = 0;
        for (OrgFinance orgFinance : orgFinanceList) {
            OrgFinanceResponse orgFinanceResponse;

            if (orgFinance.getBusinessType() == 1) {
                if (checkTrainingData.get(orgFinance.getRealName()) == null) {
                    checkTrainingData.put(orgFinance.getRealName(), i);

                    orgFinanceResponse = new OrgFinanceResponse();

                    orgFinanceResponse.setBaseType(orgFinance.getBaseType());
                    orgFinanceResponse.setRealName(orgFinance.getRealName());
                    orgFinanceResponse.setChannelType(orgFinance.getChannelType());
                    orgFinanceResponse.setBusinessType(orgFinance.getBusinessType());

                    // 流水
                    orgFinanceResponse.setPipelineValue(orgFinance.getPipelineValue());
                    orgFinanceResponse.setPipelineTarget(orgFinance.getPipelineTarget());
                    orgFinanceResponse.setPipelineChallenge(orgFinance.getPipelineChallenge());

                    // 收入
                    orgFinanceResponse.setIncomeValue(orgFinance.getIncomeValue());
                    orgFinanceResponse.setIncomeTarget(orgFinance.getIncomeTarget());
                    orgFinanceResponse.setIncomeChallenge(orgFinance.getIncomeChallenge());

                    // 人数
                    orgFinanceResponse.setRegisterCount(orgFinance.getRegisterCount());
                    orgFinanceResponse.setClassCount(orgFinance.getClassCount());

                    // 体验
                    orgFinanceResponse.setAccessCount(orgFinance.getAccessCount());
                    orgFinanceResponse.setBusinessCount(orgFinance.getBusinessCount());

                    orgFinanceResponseList.add(orgFinanceResponse);
                    i++;
                } else {
                    int iIndex = Integer.parseInt(checkTrainingData.get(orgFinance.getRealName()).toString());

                    orgFinanceResponse = orgFinanceResponseList.get(iIndex);

                    // 流水
                    orgFinanceResponse.setPipelineValue(orgFinanceResponse.getPipelineValue() + orgFinance.getPipelineValue());
                    orgFinanceResponse.setPipelineTarget(orgFinanceResponse.getPipelineTarget() + orgFinance.getPipelineTarget());
                    orgFinanceResponse.setPipelineChallenge(orgFinanceResponse.getPipelineChallenge() + orgFinance.getPipelineChallenge());

                    // 收入
                    orgFinanceResponse.setIncomeValue(orgFinanceResponse.getIncomeValue() + orgFinance.getIncomeValue());
                    orgFinanceResponse.setIncomeTarget(orgFinanceResponse.getIncomeTarget() + orgFinance.getIncomeTarget());
                    orgFinanceResponse.setIncomeChallenge(orgFinanceResponse.getIncomeChallenge() + orgFinance.getIncomeChallenge());

                    // 人数
                    orgFinanceResponse.setRegisterCount(orgFinanceResponse.getRegisterCount() + orgFinance.getRegisterCount());
                    orgFinanceResponse.setClassCount(orgFinanceResponse.getClassCount() + orgFinance.getClassCount());

                    // 体验
                    orgFinanceResponse.setAccessCount(orgFinanceResponse.getRegisterCount() + orgFinance.getAccessCount());
                    orgFinanceResponse.setBusinessCount(orgFinanceResponse.getClassCount() + orgFinance.getBusinessCount());
                }
            } else {
                if (checkTrainingData.get(orgFinance.getRealName()) == null) {
                    checkVenueData.put(orgFinance.getRealName(), j);

                    orgFinanceResponse = new OrgFinanceResponse();

                    orgFinanceResponse.setBaseType(orgFinance.getBaseType());
                    orgFinanceResponse.setRealName(orgFinance.getRealName());
                    orgFinanceResponse.setChannelType(orgFinance.getChannelType());

                    // 流水
                    orgFinanceResponse.setPipelineValue(orgFinance.getPipelineValue());
                    orgFinanceResponse.setPipelineTarget(orgFinance.getPipelineTarget());
                    orgFinanceResponse.setPipelineChallenge(orgFinance.getPipelineChallenge());

                    // 收入
                    orgFinanceResponse.setIncomeValue(orgFinance.getIncomeValue());
                    orgFinanceResponse.setIncomeTarget(orgFinance.getIncomeTarget());
                    orgFinanceResponse.setIncomeChallenge(orgFinance.getIncomeChallenge());

                    // 忙时段
                    orgFinanceResponse.setHotCount(orgFinance.getHotCount());
                    orgFinanceResponse.setHotTotalCount(orgFinance.getHotTotalCount());

                    // 闲时段
                    orgFinanceResponse.setNullCount(orgFinance.getNullCount());
                    orgFinanceResponse.setNullTotalCount(orgFinance.getNullTotalCount());

                    orgFinanceResponseList.add(orgFinanceResponse);
                    j++;
                } else {
                    int jIndex = Integer.parseInt(checkVenueData.get(orgFinance.getRealName()).toString());

                    orgFinanceResponse = orgFinanceResponseList.get(jIndex);

                    // 流水
                    orgFinanceResponse.setPipelineValue(orgFinanceResponse.getPipelineValue() + orgFinance.getPipelineValue());
                    orgFinanceResponse.setPipelineTarget(orgFinanceResponse.getPipelineTarget() + orgFinance.getPipelineTarget());
                    orgFinanceResponse.setPipelineChallenge(orgFinanceResponse.getPipelineChallenge() + orgFinance.getPipelineChallenge());

                    // 收入
                    orgFinanceResponse.setIncomeValue(orgFinanceResponse.getIncomeValue() + orgFinance.getIncomeValue());
                    orgFinanceResponse.setIncomeTarget(orgFinanceResponse.getIncomeTarget() + orgFinance.getIncomeTarget());
                    orgFinanceResponse.setIncomeChallenge(orgFinanceResponse.getIncomeChallenge() + orgFinance.getIncomeChallenge());

                    // 忙时段
                    orgFinanceResponse.setHotCount(orgFinanceResponse.getHotCount() + orgFinance.getHotCount());
                    orgFinanceResponse.setHotTotalCount(orgFinanceResponse.getHotTotalCount() + orgFinance.getHotTotalCount());

                    // 闲时段
                    orgFinanceResponse.setNullCount(orgFinanceResponse.getNullCount() + orgFinance.getNullCount());
                    orgFinanceResponse.setNullTotalCount(orgFinanceResponse.getNullTotalCount() + orgFinance.getNullTotalCount());
                }
            }
        }

        return orgFinanceResponseList;
    }

    @Desc("运用财务")
    @RequestMapping(value = "/operation/finance", method = RequestMethod.GET)
    public ModelAndView renderDataOperationFinance(OrgDataRequest orgDataRequest) throws Exception {

        ModelAndView modelAndView = new ModelAndView("Data/OperationFinance");

        if (orgDataRequest.getTypeTime() == null) {
            orgDataRequest.setTypeTime("year");
        }

        modelAndView.addObject("typeTime", orgDataRequest.getTypeTime());

        List<OrgFinanceEnums> orgFinanceChannelList = orgFinanceEnumsService.queryOrgFinanceEnumsList("GROUP_CHANNEL");
        modelAndView.addObject("orgFinanceChannelList", orgFinanceChannelList);

        List<OrgFinanceEnums> orgFinanceBusinessList = orgFinanceEnumsService.queryOrgFinanceEnumsList("GROUP_BUSINESS");
        modelAndView.addObject("orgFinanceBusinessList", orgFinanceBusinessList);

        List<OrgFinanceEnums> orgFinanceBaseList = orgFinanceEnumsService.queryOrgFinanceEnumsList("GROUP_BASE");
        modelAndView.addObject("orgFinanceBaseList", orgFinanceBaseList);

        String typePeriod[] = getStartEndTime(orgDataRequest.getTypeTime());
        String startTime = typePeriod[0];
        String endTime = typePeriod[1];

        int total = orgFinanceService.queryOrgFinanceCount(startTime, endTime);
        List<OrgFinance> orgFinanceList = orgFinanceService.queryOrgFinanceList(startTime, endTime, 0, total);

        List<Map> financeList = new ArrayList<>();
        Map checkMap = new HashMap();
        int i = 0;
        for (OrgFinance orgFinance : orgFinanceList) {
            Map map;

            if (checkMap.get(orgFinance.getRealName()) == null) {
                checkMap.put(orgFinance.getRealName(), i);

                map = new HashMap();

                map.put("baseType", orgFinance.getBaseType());
                map.put("realName", orgFinance.getRealName());

                // 流水
                map.put("pipelineValue", orgFinance.getPipelineValue());
                map.put("pipelineTarget", orgFinance.getPipelineTarget());
                map.put("pipelineChallenge", orgFinance.getPipelineChallenge());

                map.put("pipelineValue" + orgFinance.getChannelType(), orgFinance.getPipelineValue());

                // 收入
                map.put("incomeValue", orgFinance.getIncomeValue());
                map.put("incomeTarget", orgFinance.getIncomeTarget());
                map.put("incomeChallenge", orgFinance.getIncomeChallenge());

                map.put("incomeValue" + orgFinance.getChannelType(), orgFinance.getIncomeValue());

                // 人数
                map.put("registerCount", orgFinance.getRegisterCount());
                map.put("classCount", orgFinance.getClassCount());

                map.put("registerCount" + orgFinance.getChannelType(), orgFinance.getRegisterCount());
                map.put("classCount" + orgFinance.getChannelType(), orgFinance.getClassCount());

                // 体验
                map.put("accessCount", orgFinance.getAccessCount());
                map.put("businessCount", orgFinance.getBusinessCount());

                map.put("accessCount" + orgFinance.getChannelType(), orgFinance.getAccessCount());
                map.put("businessCount" + orgFinance.getChannelType(), orgFinance.getBusinessCount());

                financeList.add(map);
            } else {
                i = Integer.parseInt(checkMap.get(orgFinance.getRealName()).toString());

                map = financeList.get(i);

                // 流水
                map.put("pipelineValue", getStringValue(map.get("pipelineValue")) + orgFinance.getPipelineValue());
                map.put("pipelineTarget", getStringValue(map.get("pipelineTarget")) + orgFinance.getPipelineTarget());
                map.put("pipelineChallenge", getStringValue(map.get("pipelineChallenge")) + orgFinance.getPipelineChallenge());


                map.put("pipelineValue" + orgFinance.getChannelType(), getStringValue(map.get("pipelineValue" + orgFinance.getChannelType())) + orgFinance.getPipelineValue());

                // 收入
                map.put("incomeValue", getStringValue(map.get("incomeValue")) + orgFinance.getIncomeValue());
                map.put("incomeTarget", getStringValue(map.get("incomeTarget")) + orgFinance.getIncomeTarget());
                map.put("incomeChallenge", getStringValue(map.get("incomeChallenge")) + orgFinance.getIncomeChallenge());

                map.put("incomeValue" + orgFinance.getChannelType(), getStringValue(map.get("incomeValue" + orgFinance.getChannelType())) + orgFinance.getIncomeValue());

                // 人数
                map.put("registerCount", getStringValue(map.get("registerCount")) + orgFinance.getRegisterCount());
                map.put("classCount", getStringValue(map.get("classCount")) + orgFinance.getClassCount());

                map.put("registerCount" + orgFinance.getChannelType(), getStringValue(map.get("registerCount" + orgFinance.getChannelType())) + orgFinance.getRegisterCount());
                map.put("classCount" + orgFinance.getChannelType(), getStringValue(map.get("classCount" + orgFinance.getChannelType())) + orgFinance.getClassCount());

                // 体验
                map.put("accessCount", getStringValue(map.get("accessCount")) + orgFinance.getAccessCount());
                map.put("businessCount", getStringValue(map.get("businessCount")) + orgFinance.getBusinessCount());

                map.put("accessCount" + orgFinance.getChannelType(), getStringValue(map.get("accessCount" + orgFinance.getChannelType())) + orgFinance.getAccessCount());
                map.put("businessCount" + orgFinance.getChannelType(), getStringValue(map.get("businessCount" + orgFinance.getChannelType())) + orgFinance.getBusinessCount());
            }

            i++;
        }

        for (Map map : financeList) {
            map.put("pipelineTargetPercent", (double)getStringValue(map.get("pipelineValue")) / getStringValue(map.get("pipelineTarget")));
            map.put("pipelineChallengePercent", (double)getStringValue(map.get("pipelineValue")) / getStringValue(map.get("pipelineChallenge")));

            map.put("incomeTargetPercent", (double)getStringValue(map.get("incomeValue")) / getStringValue(map.get("incomeTarget")));
            map.put("incomeChallengePercent", (double)getStringValue(map.get("incomeValue")) / getStringValue(map.get("incomeChallenge")));

            map.put("businessCountPercent", (double)getStringValue(map.get("businessCount")) / getStringValue(map.get("accessCount")));

            int accessCountPerson = 0;
            int businessCountPerson = 0;
            for (OrgFinanceEnums orgFinanceEnums : orgFinanceChannelList) {
                String accessCount = "accessCount" + orgFinanceEnums.getEnumValue();
                String businessCount = "businessCount" + orgFinanceEnums.getEnumValue();

                if (map.get(accessCount) != null) {
                    map.put(businessCount + "Percent", (double)getStringValue(map.get(businessCount)) / getStringValue(map.get(accessCount)));
                } else {
                    map.put(accessCount, 0);
                    map.put(businessCount, 0);
                    map.put(businessCount + "Percent", 1);
                }

                if (!orgFinanceEnums.getEnumName().equals("company")) {
                    accessCountPerson += getStringValue(map.get(accessCount));
                    businessCountPerson += getStringValue(map.get(businessCount));
                }

                String registerCount = "registerCount" + orgFinanceEnums.getEnumValue();
                String classCount = "classCount" + orgFinanceEnums.getEnumValue();
                if (map.get(registerCount) == null) {
                    map.put(registerCount, 0);
                    map.put(classCount, 0);
                }

                String pipelineValue = "pipelineValue" + orgFinanceEnums.getEnumValue();
                String incomeValue = "incomeValue" + orgFinanceEnums.getEnumValue();
                if (map.get(pipelineValue) == null) {
                    map.put(pipelineValue, 0);
                }
                if (map.get(incomeValue) == null) {
                    map.put(incomeValue, 0);
                }
            }
            map.put("businessCountPerson", (double)businessCountPerson / accessCountPerson);
        }

        modelAndView.addObject("orgFinanceList", financeList);

        return setModelAndView(modelAndView);
    }

    /**
     * 导出运营报表
     * @return
     */
    @RequestMapping(value = "/operation/finance/export")
    @ResponseBody
    public void export(OrgDataRequest orgDataRequest, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 青训
        String[] trainingTitle = {
                "基地名字", "姓名",
                "流水完成", "流水目标", "流水挑战目标",
                "确认收入完成", "确认收入目标", "确认收入挑战目标",
                "在册人数", "到课人数",
                "个人渠道体验数", "个人渠道成交数", "个人渠道转化率",
                "转介绍渠道体验数", "转介绍渠道成交数", "转介绍渠道转化率",
                "非公司渠转化率",
                "公司渠道体验数", "公司渠道成交数", "公司渠道转化率",
                "整体转化率"};
        String trainingSheetName = "青少年培训";

        // 场租标题
        String[] venueTitle = {
                "基地名字", "姓名",
                "流水完成", "流水目标", "流水挑战目标",
                "确认收入完成", "确认收入目标", "确认收入挑战目标",
                "闲时段数", "闲时段总数",
                "忙时段数", "忙时段总数"};
        String venueSheetName = "场地租赁";

        if (orgDataRequest.getTypeTime() == null) {
            orgDataRequest.setTypeTime("year");
        }

        List<OrgFinanceEnums> orgFinanceBaseList = orgFinanceEnumsService.queryOrgFinanceEnumsList("GROUP_BASE");

        String typePeriod[] = getStartEndTime(orgDataRequest.getTypeTime());
        String startTime = typePeriod[0];
        String endTime = typePeriod[1];

        int total = orgFinanceService.queryOrgFinanceCount(startTime, endTime);
        List<OrgFinance> orgFinanceList = orgFinanceService.queryOrgFinanceList(startTime, endTime, 0, total);

        List<OrgFinanceResponse> orgFinanceResponseList = formatOrgFinance(orgFinanceList);
        int trainingCount = 0, venueCount = 0;
        for (OrgFinanceResponse orgFinanceResponse : orgFinanceResponseList) {
            if (orgFinanceResponse.getBusinessType() == 1) {
                trainingCount++;
            } else {
                venueCount++;
            }
        }

        String [][]trainingContent = new String[trainingCount][trainingTitle.length];
        String [][]venueContent = new String[venueCount][trainingTitle.length];
        int i = 0, j = 0;
        for (OrgFinanceResponse orgFinanceResponse : orgFinanceResponseList) {
            String pipelineTargetPercent = StrUtil.roundKeepTwo((double)orgFinanceResponse.getPipelineValue() / orgFinanceResponse.getPipelineTarget() * 100) + "%";
            String pipelineChallengePercent = StrUtil.roundKeepTwo((double)orgFinanceResponse.getPipelineValue() / orgFinanceResponse.getPipelineChallenge() * 100) + "%";

            String incomeTargetPercent = StrUtil.roundKeepTwo((double)orgFinanceResponse.getIncomeValue() / orgFinanceResponse.getIncomeTarget() * 100) + "%";
            String incomeChallengePercent = StrUtil.roundKeepTwo((double)orgFinanceResponse.getIncomeValue() / orgFinanceResponse.getIncomeChallenge() * 100) + "%";

            String businessCountPercent = StrUtil.roundKeepTwo((double)orgFinanceResponse.getBusinessCount() / orgFinanceResponse.getAccessCount() * 100) + "%";

            String baseName = "--";
            for (OrgFinanceEnums orgFinanceEnums : orgFinanceBaseList) {
                int baseValue = orgFinanceResponse.getBaseType();

                if (baseValue == orgFinanceEnums.getEnumValue()) {
                    baseName = orgFinanceEnums.getEnumNote();
                    break;
                }
            }

            int accessCount1 = 0, businessCount1 = 0;
            int accessCount2 = 0, businessCount2 = 0;
            int accessCount3 = 0, businessCount3 = 0;
            int accessCount4 = 0, businessCount4 = 0;
            int nullCount1 = 0, nullTotalCount1 = 0, hotCount1 = 0, hotTotalCount1 = 0;
            int nullCount2 = 0, nullTotalCount2 = 0, hotCount2 = 0, hotTotalCount2 = 0;
            int nullCount3 = 0, nullTotalCount3 = 0, hotCount3 = 0, hotTotalCount3 = 0;
            int nullCount4 = 0, nullTotalCount4 = 0, hotCount4 = 0, hotTotalCount4 = 0;
            for (OrgFinance orgFinance : orgFinanceList) {
                if (!orgFinance.getRealName().equals(orgFinanceResponse.getRealName())) {
                    continue;
                }

                if (orgFinance.getBusinessType() == 1) {
                    String accessCount = "accessCount" + orgFinance.getChannelType();

                    if (accessCount.equals("accessCount1")) {
                        accessCount1 += orgFinance.getAccessCount();
                        businessCount1 += orgFinance.getBusinessCount();
                    } else if (accessCount.equals("accessCount2")) {
                        accessCount2 += orgFinance.getAccessCount();
                        businessCount2 += orgFinance.getBusinessCount();
                    } else if (accessCount.equals("accessCount3")) {
                        accessCount3 += orgFinance.getAccessCount();
                        businessCount3 += orgFinance.getBusinessCount();
                    } else if (accessCount.equals("accessCount4")) {
                        accessCount4 += orgFinance.getAccessCount();
                        businessCount4 += orgFinance.getBusinessCount();
                    }
                } else {
                    String nullCount = "nullCount" + orgFinance.getChannelType();

                    if (nullCount.equals("nullCount1")) {
                        nullCount1 += orgFinance.getNullCount();
                        nullTotalCount1 += orgFinance.getNullTotalCount();
                        hotCount1 += orgFinance.getHotCount();
                        hotTotalCount1 += orgFinance.getHotTotalCount();
                    } else if (nullCount.equals("nullCount2")) {
                        nullCount2 += orgFinance.getNullCount();
                        nullTotalCount2 += orgFinance.getNullTotalCount();
                        hotCount2 += orgFinance.getHotCount();
                        hotTotalCount2 += orgFinance.getHotTotalCount();
                    } else if (nullCount.equals("nullCount3")) {
                        nullCount3 += orgFinance.getNullCount();
                        nullTotalCount3 += orgFinance.getNullTotalCount();
                        hotCount3 += orgFinance.getHotCount();
                        hotTotalCount3 += orgFinance.getHotTotalCount();
                    } else if (nullCount.equals("nullCount4")) {
                        nullCount4 += orgFinance.getNullCount();
                        nullTotalCount4 += orgFinance.getNullTotalCount();
                        hotCount4 += orgFinance.getHotCount();
                        hotTotalCount4 += orgFinance.getHotTotalCount();
                    }
                }
            }

            if (orgFinanceResponse.getBusinessType() == 1) {
                String businessCount1Percent = accessCount1 > 0 ? StrUtil.roundKeepTwo((double)businessCount1 / accessCount1 * 100) + "%" : "100%";
                String businessCount2Percent = accessCount2 > 0 ? StrUtil.roundKeepTwo((double)businessCount2 / accessCount2 * 100) + "%" : "100%";
                String businessCount3Percent = accessCount3 > 0 ? StrUtil.roundKeepTwo((double)businessCount3 / accessCount3 * 100) + "%" : "100%";
                String businessCount4Percent = accessCount4 > 0 ? StrUtil.roundKeepTwo((double)businessCount4 / accessCount4 * 100) + "%" : "100%";
                String businessCount234Percent = (accessCount2 + accessCount3 + accessCount4) > 0 ? StrUtil.roundKeepTwo((double)(businessCount2 + businessCount3 + businessCount4) / (accessCount2 + accessCount3 + accessCount4) * 100) + "%" : "100%";

                trainingContent[j][0] = baseName;
                trainingContent[j][1] = orgFinanceResponse.getRealName();
                trainingContent[j][2] = orgFinanceResponse.getPipelineValue().toString();
                trainingContent[j][3] = orgFinanceResponse.getPipelineTarget().toString() + "，" + pipelineTargetPercent;
                trainingContent[j][4] = orgFinanceResponse.getPipelineChallenge().toString() + "，" + pipelineChallengePercent;
                trainingContent[j][5] = orgFinanceResponse.getIncomeValue().toString();
                trainingContent[j][6] = orgFinanceResponse.getIncomeTarget().toString() + "，" + incomeTargetPercent;
                trainingContent[j][7] = orgFinanceResponse.getIncomeChallenge().toString() + "，" + incomeChallengePercent;
                trainingContent[j][8] = orgFinanceResponse.getRegisterCount().toString();
                trainingContent[j][9] = orgFinanceResponse.getClassCount().toString();
                trainingContent[j][10] = accessCount2 + "";
                trainingContent[j][11] = businessCount2 + "";
                trainingContent[j][12] = businessCount2Percent;
                trainingContent[j][13] = accessCount3 + "";
                trainingContent[j][14] = businessCount3 + "";
                trainingContent[j][15] = businessCount3Percent;
                trainingContent[j][16] = businessCount234Percent;
                trainingContent[j][17] = accessCount1 + "";
                trainingContent[j][18] = businessCount1 + "";
                trainingContent[j][19] = businessCount1Percent;
                trainingContent[j][20] = businessCountPercent;

                j++;
            } else {
                trainingContent[i][0] = baseName;
                trainingContent[i][1] = orgFinanceResponse.getRealName();
                trainingContent[i][2] = orgFinanceResponse.getPipelineValue().toString();
                trainingContent[i][3] = orgFinanceResponse.getPipelineTarget().toString() + "，" + pipelineTargetPercent;
                trainingContent[i][4] = orgFinanceResponse.getPipelineChallenge().toString() + "，" + pipelineChallengePercent;
                trainingContent[i][5] = orgFinanceResponse.getIncomeValue().toString();
                trainingContent[i][6] = orgFinanceResponse.getIncomeTarget().toString() + "，" + incomeTargetPercent;
                trainingContent[i][7] = orgFinanceResponse.getIncomeChallenge().toString() + "，" + incomeChallengePercent;
                trainingContent[i][8] = nullCount1 + nullCount2 + nullCount3 + nullCount4 + "";
                trainingContent[i][9] = nullTotalCount1 + nullTotalCount2 + nullTotalCount3 + nullTotalCount4 + "";
                trainingContent[i][10] = hotCount1 + hotCount2 + hotCount3 + hotCount4 + "";
                trainingContent[i][11] = hotTotalCount1 + hotTotalCount2 + hotTotalCount3 + hotTotalCount4 + "";

                i++;
            }
        }

        HSSFWorkbook trainingBook = ExcelUtil.getHSSFWorkbook(trainingSheetName, trainingTitle, trainingContent, null);
        HSSFWorkbook venueBook = ExcelUtil.getHSSFWorkbook(venueSheetName, venueTitle, venueContent, trainingBook);

        // 文件名
        String fileName = "运营财务报表_" + orgDataRequest.getTypeTime() + "_" + System.currentTimeMillis() + ".xls";


        //响应到客户端
        try {
            this.setResponseHeader(response, fileName);

            OutputStream os = response.getOutputStream();
            trainingBook.write(os);
            venueBook.write(os);

            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //发送响应流方法
    private void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Desc("运用财务编辑")
    @RequestMapping(value = "/operation/finance/edit", method = RequestMethod.GET)
    public ModelAndView renderDataOperationFinanceEdit(String businessNo) {

        ModelAndView modelAndView = new ModelAndView("Data/OperationFinanceEdit");

        OrgFinance orgFinance = new OrgFinance();
        if (businessNo != null) {
            orgFinance = orgFinanceService.getOrgFinance(businessNo);
        }
        modelAndView.addObject("orgFinance", orgFinance);

        List<OrgFinanceEnums> orgFinanceChannelList = orgFinanceEnumsService.queryOrgFinanceEnumsList("GROUP_CHANNEL");
        modelAndView.addObject("orgFinanceChannelList", orgFinanceChannelList);

        List<OrgFinanceEnums> orgFinanceBusinessList = orgFinanceEnumsService.queryOrgFinanceEnumsList("GROUP_BUSINESS");
        modelAndView.addObject("orgFinanceBusinessList", orgFinanceBusinessList);

        List<OrgFinanceEnums> orgFinanceBaseList = orgFinanceEnumsService.queryOrgFinanceEnumsList("GROUP_BASE");
        modelAndView.addObject("orgFinanceBaseList", orgFinanceBaseList);

        return setModelAndView(modelAndView);
    }

    @Desc("运用财务编辑提交")
    @ResponseBody
    @RequestMapping(value = "/saveOperationFinance", method = RequestMethod.POST)
    public ResponseBean saveOperationFinance(OrgFinance orgFinance) {
        try {

            int result;
;
            orgFinance.setOperatorId(getLoginUser().getId());

            if (orgFinance.getBusinessNo() != null && !orgFinance.getBusinessNo().equals("")) {
                orgFinance.setUpdateTime(DateUtil.getNowDate());
                result = orgFinanceService.saveOrgFinance(orgFinance);
            }
            else {
                orgFinance.setBusinessNo(StrUtil.getUUID());
                orgFinance.setCreateTime(DateUtil.getNowDate());
                orgFinance.setUpdateTime(DateUtil.getNowDate());
                result = orgFinanceService.addOrgFinance(orgFinance);
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

    @Desc("运用财务日志")
    @RequestMapping(value = "/operation/finance/log", method = RequestMethod.GET)
    public ModelAndView renderDataOperationFinanceLog(OrgFinanceLogRequest orgFinanceLogRequest) {

        ModelAndView modelAndView = new ModelAndView("Data/OperationFinanceLog");

        List<OrgFinanceEnums> orgFinanceChannelList = orgFinanceEnumsService.queryOrgFinanceEnumsList("GROUP_CHANNEL");
        modelAndView.addObject("orgFinanceChannelList", orgFinanceChannelList);

        List<OrgFinanceEnums> orgFinanceBusinessList = orgFinanceEnumsService.queryOrgFinanceEnumsList("GROUP_BUSINESS");
        modelAndView.addObject("orgFinanceBusinessList", orgFinanceBusinessList);

        List<OrgFinanceEnums> orgFinanceBaseList = orgFinanceEnumsService.queryOrgFinanceEnumsList("GROUP_BASE");
        modelAndView.addObject("orgFinanceBaseList", orgFinanceBaseList);

        int total = orgFinanceService.queryOrgFinanceCount(null, null);
        int start = orgFinanceLogRequest.getPage() < 1 ? 0 : orgFinanceLogRequest.getPage() - 1;
        int pageSize = 10;
        List<OrgFinance> orgFinanceList = orgFinanceService.queryOrgFinanceList(null, null, start * pageSize, pageSize);
        modelAndView.addObject("orgFinanceList", orgFinanceList);

        Page page = new Page(pageSize, total);
        page.setPage(orgFinanceLogRequest.getPage());

        modelAndView.addObject("total", total);
        modelAndView.addObject("pageURL", "/admin/data/operation/finance/log?_t=" + DateUtil.getNowDate());
        modelAndView.addObject("page", page);

        return setModelAndView(modelAndView);
    }

    @Desc("运用财务图表")
    @RequestMapping(value = "/operation/finance/chart", method = RequestMethod.GET)
    public ModelAndView renderDataOperationFinanceChart(OrgFinanceLogRequest orgFinanceLogRequest) {

        ModelAndView modelAndView = new ModelAndView("Data/OperationFinanceChart");

        return setModelAndView(modelAndView);
    }

    @Desc("运用财务参数设置")
    @RequestMapping(value = "/operation/finance/settings", method = RequestMethod.GET)
    public ModelAndView renderDataOperationFinanceSettings(OrgFinanceLogRequest orgFinanceLogRequest) {

        ModelAndView modelAndView = new ModelAndView("Data/OperationFinanceSettings");

        return setModelAndView(modelAndView);
    }

}
