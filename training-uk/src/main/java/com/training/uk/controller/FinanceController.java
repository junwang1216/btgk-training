package com.training.uk.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.bean.ResponseBean;
import com.training.core.common.config.WebConfig;
import com.training.core.common.enums.*;
import com.training.core.common.exception.MessageException;
import com.training.core.common.util.*;
import com.training.core.repo.po.*;
import com.training.core.service.*;
import com.training.uk.request.OrgFinanceLogRequest;
import com.training.uk.request.OrgFinanceParamSettingsRequest;
import com.training.uk.response.*;
import org.apache.commons.lang3.EnumUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by wangjun on 2018/5/7.
 */
@Controller
@RequestMapping("/admin/finance")
public class FinanceController extends BaseController {

    @Resource
    private OrgFinanceVenuesService orgFinanceVenuesService;

    @Resource
    private OrgFinanceUsersService orgFinanceUsersService;

    @Resource
    private OrgFinanceGoalsService orgFinanceGoalsService;

    @Resource
    private OrgFinanceDataService orgFinanceDataService;

    @Resource
    private OrgFinanceDataFlowService orgFinanceDataFlowService;

    @Resource
    private OrgFinanceDataBusinessService orgFinanceDataBusinessService;

    @Resource
    private OrgFinanceDataIncomeService orgFinanceDataIncomeService;

    @Resource
    private OrgFinanceDataAttendanceService orgFinanceDataAttendanceService;

    @Resource
    private OrgFinanceDataTimesService orgFinanceDataTimesService;

    @Resource
    private OrgFinanceEnumsService orgFinanceEnumsService;

    private ModelAndView setModelAndView(ModelAndView modelAndView) {
        return modelAndView.addObject("Admin", getLoginUser());
    }

    /*** 组织关系管理 start ***/

    @Desc("组织关系管理")
    @RequestMapping(value = "/relationship", method = RequestMethod.GET)
    public ModelAndView renderFinanceRelationship(OrgFinanceLogRequest orgFinanceLogRequest) {

        ModelAndView modelAndView = new ModelAndView("Finance/Relationship");

        List<OrgFinanceVenues> orgFinanceVenuesList = orgFinanceVenuesService.queryOrgFinanceVenuesList();
        modelAndView.addObject("orgFinanceVenuesList", orgFinanceVenuesList);

        int total = orgFinanceUsersService.queryOrgFinanceUsersCount();
        int start = orgFinanceLogRequest.getPage() < 1 ? 0 : orgFinanceLogRequest.getPage() - 1;
        int pageSize = 10;
        List<OrgFinanceUsers> orgFinanceUsersList = orgFinanceUsersService.queryOrgFinanceUsersList(start * pageSize, pageSize);
        modelAndView.addObject("orgFinanceUsersList", orgFinanceUsersList);

        Page page = new Page(pageSize, total);
        page.setPage(orgFinanceLogRequest.getPage());

        modelAndView.addObject("total", total);
        modelAndView.addObject("pageURL", "/admin/finance/relationship?venueId=-1");
        modelAndView.addObject("page", page);

        List<OrgFinanceUsers> orgFinanceUsersAllList = orgFinanceUsersService.queryOrgFinanceUsersList(0, total);
        List<Map> venuesList = new ArrayList<>();
        for (OrgFinanceVenues orgFinanceVenues : orgFinanceVenuesList) {
            Map map = new HashMap();

            map.put("orgFinanceVenues", orgFinanceVenues);
            List<OrgFinanceUsers> userList = new ArrayList<>();
            for (OrgFinanceUsers orgFinanceUsers : orgFinanceUsersAllList) {
                if (orgFinanceUsers.getVenueId().equals(orgFinanceVenues.getId()) &&
                        orgFinanceUsers.getStatus() == StatusEnum.STATUS_OK.getCode()) {
                    userList.add(orgFinanceUsers);
                }
            }
            map.put("orgFinanceUsersList", userList);

            venuesList.add(map);
        }
        modelAndView.addObject("orgFinanceVenuesAllList", venuesList);

        return setModelAndView(modelAndView);
    }

    @Desc("获取基地下的员工")
    @ResponseBody
    @RequestMapping(value = "/getEmployeeByVenue", method = RequestMethod.GET)
    public ResponseBean getEmployeeByVenue(int venueId) {
        try {
            Map<String, Object> map = new HashMap<>();

            int count = orgFinanceUsersService.queryOrgFinanceUsersCount();
            List<OrgFinanceUsers> orgFinanceUsersList = orgFinanceUsersService.queryOrgFinanceUsersList(0, count);

            List<OrgFinanceUsers> orgFinanceUsersList1 = new ArrayList<>();
            for (OrgFinanceUsers orgFinanceUsers : orgFinanceUsersList) {
                if (orgFinanceUsers.getVenueId() == venueId) {
                    orgFinanceUsersList1.add(orgFinanceUsers);
                }
            }
            map.put("orgFinanceUsersList", orgFinanceUsersList1);

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("保存基地")
    @ResponseBody
    @RequestMapping(value = "/saveVenues", method = RequestMethod.POST)
    public ResponseBean saveFinanceVenues(OrgFinanceVenues orgFinanceVenues) {
        try {

            int result;

            if (orgFinanceVenues.getId() != null) {
                orgFinanceVenues.setUpdateTime(DateUtil.getNowDate());
                result = orgFinanceVenuesService.saveOrgFinanceVenues(orgFinanceVenues);
            }
            else {
                orgFinanceVenues.setCreateTime(DateUtil.getNowDate());
                orgFinanceVenues.setUpdateTime(DateUtil.getNowDate());
                result = orgFinanceVenuesService.addOrgFinanceVenues(orgFinanceVenues);
            }

            log(LogTypeEnum.LOG_TYPE_FINANCE_REPORTS, getLoginUser().getOrgId(), "添加或者修改基地[" + orgFinanceVenues.getVenueName() + "]信息");

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("保存员工")
    @ResponseBody
    @RequestMapping(value = "/saveEmployee", method = RequestMethod.POST)
    public ResponseBean saveFinanceEmployee(OrgFinanceUsers orgFinanceUsers) {
        try {

            int result;

            OrgFinanceUsers orgFinanceUsers1 = new OrgFinanceUsers();
            orgFinanceUsers1.setMobile(orgFinanceUsers.getMobile());
            List<OrgFinanceUsers> orgFinanceUsersList = orgFinanceUsersService.getOrgFinanceUsersList(orgFinanceUsers1);
            if (orgFinanceUsersList.size() > 0) {
                return new ResponseBean("500", "手机号重复");
            }

            if (orgFinanceUsers.getId() != null) {
                orgFinanceUsers.setUpdateTime(DateUtil.getNowDate());
                result = orgFinanceUsersService.saveOrgFinanceUsers(orgFinanceUsers);
            }
            else {
                orgFinanceUsers.setCreateTime(DateUtil.getNowDate());
                orgFinanceUsers.setUpdateTime(DateUtil.getNowDate());

                orgFinanceUsers.setStatus(StatusEnum.STATUS_OK.getCode());
                orgFinanceUsers.setPassword(DataCryptUtil.encrypt(WebConfig.getUserDefaultPassword()));
                result = orgFinanceUsersService.addOrgFinanceUsers(orgFinanceUsers);
            }

            log(LogTypeEnum.LOG_TYPE_FINANCE_REPORTS, getLoginUser().getOrgId(), "添加或者修改员工[" + orgFinanceUsers.getRealName() + "]信息");

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("保存员工入职状态")
    @ResponseBody
    @RequestMapping(value = "/enterEmployeeStatus", method = RequestMethod.POST)
    public ResponseBean enterFinanceEmployeeStatus(OrgFinanceUsers orgFinanceUsers) {
        try {

            int result;

            orgFinanceUsers.setUpdateTime(DateUtil.getNowDate());
            orgFinanceUsers.setStatus(StatusEnum.STATUS_OK.getCode());
            result = orgFinanceUsersService.setOrgFinanceUsersStatus(orgFinanceUsers);

            log(LogTypeEnum.LOG_TYPE_FINANCE_REPORTS, getLoginUser().getOrgId(), "入职员工[" + orgFinanceUsers.getId() + "]状态");

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("保存员工入职状态")
    @ResponseBody
    @RequestMapping(value = "/leaveEmployeeStatus", method = RequestMethod.POST)
    public ResponseBean leaveFinanceEmployeeStatus(OrgFinanceUsers orgFinanceUsers) {
        try {

            int result;

            orgFinanceUsers.setUpdateTime(DateUtil.getNowDate());
            orgFinanceUsers.setStatus(StatusEnum.STATUS_ERROR.getCode());
            result = orgFinanceUsersService.setOrgFinanceUsersStatus(orgFinanceUsers);

            log(LogTypeEnum.LOG_TYPE_FINANCE_REPORTS, getLoginUser().getOrgId(), "离职员工[" + orgFinanceUsers.getRealName() + "]状态");

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    /*** 参数设置 start ***/

    @Desc("查询收支参数")
    @RequestMapping(value = "/params/settings", method = RequestMethod.GET)
    public ModelAndView renderDataSettings() {

        ModelAndView modelAndView = new ModelAndView("Finance/ParamSettings");

        String orgFinanceChannelGroup = "GROUP_CHANNEL";
        List<OrgFinanceEnums> orgFinanceChannelEnumsList = orgFinanceEnumsService.queryOrgFinanceEnumsList(orgFinanceChannelGroup);
        modelAndView.addObject("orgFinanceChannelEnumsList", orgFinanceChannelEnumsList);
        modelAndView.addObject("orgFinanceChannelGroup", orgFinanceChannelGroup);

        String orgFinanceClassGroup = "GROUP_CINCOME";
        List<OrgFinanceEnums> orgFinanceClassEnumsList = orgFinanceEnumsService.queryOrgFinanceEnumsList(orgFinanceClassGroup);
        modelAndView.addObject("orgFinanceClassEnumsList", orgFinanceClassEnumsList);
        modelAndView.addObject("orgFinanceClassGroup", orgFinanceClassGroup);

        return setModelAndView(modelAndView);
    }

    @Desc("保存参数设置")
    @ResponseBody
    @RequestMapping(value = "/saveParamsSettings", method = RequestMethod.POST)
    public ResponseBean saveParamsSettings(@RequestBody OrgFinanceParamSettingsRequest orgFinanceParamSettingsRequest) {
        try {

            orgFinanceEnumsService.clearOrgFinanceEnums(orgFinanceParamSettingsRequest.getParamType());

            int result;

            result = orgFinanceEnumsService.addOrgFinanceEnumsBatch(orgFinanceParamSettingsRequest.getOrgFinanceEnumsList());

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    /*** 指标设置 start ***/

    // 指标数据格式化
    private List<OrgFinanceGoalsResponse> formatOrgFinanceGoalsResponseList(List<OrgFinanceGoalsResponse> list) {
        OrgFinanceGoalsResponse orgFinanceGoalsResponse1 = new OrgFinanceGoalsResponse();
        orgFinanceGoalsResponse1.setUserName("第1季度");
        orgFinanceGoalsResponse1.setMinValue(list.get(0).getMinValue() +
                list.get(1).getMinValue() +
                list.get(2).getMinValue());
        orgFinanceGoalsResponse1.setMaxValue(list.get(0).getMaxValue() +
                list.get(1).getMaxValue() +
                list.get(2).getMaxValue());

        OrgFinanceGoalsResponse orgFinanceGoalsResponse2 = new OrgFinanceGoalsResponse();
        orgFinanceGoalsResponse2.setUserName("第2季度");
        orgFinanceGoalsResponse2.setMinValue(list.get(3).getMinValue() +
                list.get(4).getMinValue() +
                list.get(5).getMinValue());
        orgFinanceGoalsResponse2.setMaxValue(list.get(3).getMaxValue() +
                list.get(4).getMaxValue() +
                list.get(5).getMaxValue());

        OrgFinanceGoalsResponse orgFinanceGoalsResponse3 = new OrgFinanceGoalsResponse();
        orgFinanceGoalsResponse3.setUserName("第3季度");
        orgFinanceGoalsResponse3.setMinValue(list.get(6).getMinValue() +
                list.get(7).getMinValue() +
                list.get(8).getMinValue());
        orgFinanceGoalsResponse3.setMaxValue(list.get(6).getMaxValue() +
                list.get(7).getMaxValue() +
                list.get(8).getMaxValue());

        OrgFinanceGoalsResponse orgFinanceGoalsResponse4 = new OrgFinanceGoalsResponse();
        orgFinanceGoalsResponse4.setUserName("第4季度");
        orgFinanceGoalsResponse4.setMinValue(list.get(9).getMinValue() +
                list.get(10).getMinValue() +
                list.get(11).getMinValue());
        orgFinanceGoalsResponse4.setMaxValue(list.get(9).getMaxValue() +
                list.get(10).getMaxValue() +
                list.get(11).getMaxValue());

        OrgFinanceGoalsResponse orgFinanceGoalsResponseAll = new OrgFinanceGoalsResponse();
        orgFinanceGoalsResponseAll.setUserName(list.get(0).getYear() + "年");
        orgFinanceGoalsResponseAll.setMinValue(orgFinanceGoalsResponse1.getMinValue() +
                orgFinanceGoalsResponse2.getMinValue() +
                orgFinanceGoalsResponse3.getMinValue() +
                orgFinanceGoalsResponse4.getMinValue());
        orgFinanceGoalsResponseAll.setMaxValue(orgFinanceGoalsResponse1.getMaxValue() +
                orgFinanceGoalsResponse2.getMaxValue() +
                orgFinanceGoalsResponse3.getMaxValue() +
                orgFinanceGoalsResponse4.getMaxValue());

        list.add(3, orgFinanceGoalsResponse1);
        list.add(7, orgFinanceGoalsResponse2);
        list.add(11, orgFinanceGoalsResponse3);
        list.add(15, orgFinanceGoalsResponse4);
        list.add(orgFinanceGoalsResponseAll);

        return list;
    }

    @Desc("运用财务参数设置")
    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public ModelAndView renderDataOperationFinanceSettings(OrgFinanceLogRequest orgFinanceLogRequest) {

        ModelAndView modelAndView = new ModelAndView("Finance/Settings");

        modelAndView.addObject("BusinessGoalTypeEnumList", EnumUtils.getEnumList(BusinessGoalTypeEnum.class));

        List<OrgFinanceVenues> orgFinanceVenuesList = orgFinanceVenuesService.queryOrgFinanceVenuesList();
        if (orgFinanceLogRequest.getVenueId() == 0) {
            orgFinanceLogRequest.setVenueId(orgFinanceVenuesList.get(0).getId());
        }
        modelAndView.addObject("orgFinanceVenuesList", orgFinanceVenuesList);

        int []year = {Integer.parseInt(DateUtil.getAddYear(-2)), Integer.parseInt(DateUtil.getAddYear(-1)),
                Integer.parseInt(DateUtil.getAddYear(0)),
                Integer.parseInt(DateUtil.getAddYear(1)), Integer.parseInt(DateUtil.getAddYear(2))};
        if (orgFinanceLogRequest.getYear() == 0 || Arrays.binarySearch(year, orgFinanceLogRequest.getYear()) < 0) {
            orgFinanceLogRequest.setYear(year[2]);
        }
        modelAndView.addObject("yearList", year);

        int count = orgFinanceUsersService.queryOrgFinanceUsersCount();
        List<OrgFinanceUsers> orgFinanceUsersList = orgFinanceUsersService.queryOrgFinanceUsersList(0, count);

        String userName = "";
        List<OrgFinanceUsers> orgFinanceUsersList1 = new ArrayList<>();
        for (OrgFinanceUsers orgFinanceUsers : orgFinanceUsersList) {
            if (orgFinanceUsers.getVenueId() == orgFinanceLogRequest.getVenueId()) {
                orgFinanceUsersList1.add(orgFinanceUsers);
            }
            if (orgFinanceLogRequest.getUserId() != 0 && orgFinanceUsers.getId() == orgFinanceLogRequest.getUserId()) {
                userName = orgFinanceUsers.getRealName();
            }
        }
        modelAndView.addObject("orgFinanceUsersList", orgFinanceUsersList1);

        List<OrgFinanceGoals> orgFinanceGoalsList = orgFinanceGoalsService.queryOrgFinanceGoalsList(null,
                orgFinanceLogRequest.getGoalType(), orgFinanceLogRequest.getVenueId(),
                orgFinanceLogRequest.getYear(), orgFinanceLogRequest.getUserId());

        List<OrgFinanceGoalsResponse> flowGoalsList = new ArrayList<>();
        List<OrgFinanceGoalsResponse> incomeGoalsList = new ArrayList<>();
        Map<String, Integer> checkGoals = new HashMap<>();
        int fIndex = 0, iIndex = 0;
        for (OrgFinanceGoals orgFinanceGoals : orgFinanceGoalsList) {
            OrgFinanceGoalsResponse orgFinanceGoalsResponse = new OrgFinanceGoalsResponse();
            String uniqueKey;

            if (orgFinanceLogRequest.getUserId() != 0) {
                uniqueKey = "goal_" + orgFinanceGoals.getGoalType().toString() + "_" + orgFinanceGoals.getVenueId().toString() + "_" +
                        orgFinanceGoals.getUserId().toString() + "_" + orgFinanceGoals.getYear().toString() + "_" + orgFinanceGoals.getMonth().toString();
            }
            else {
                uniqueKey = "goal_" + orgFinanceGoals.getGoalType().toString() + "_" + orgFinanceGoals.getVenueId().toString() + "_" +
                        orgFinanceGoals.getYear().toString() + "_" + orgFinanceGoals.getMonth().toString();
            }

            if (checkGoals.get(uniqueKey) != null) {
                int goalIndex = Integer.parseInt(checkGoals.get(uniqueKey).toString());

                if (orgFinanceGoals.getGoalType() == BusinessGoalTypeEnum.FLOW.getCode()) {
                    flowGoalsList.get(goalIndex).setMaxValue(orgFinanceGoals.getMaxValue() + flowGoalsList.get(goalIndex).getMaxValue());
                    flowGoalsList.get(goalIndex).setMinValue(orgFinanceGoals.getMinValue() + flowGoalsList.get(goalIndex).getMinValue());
                }
                else if (orgFinanceGoals.getGoalType() == BusinessGoalTypeEnum.INCOME.getCode()) {
                    incomeGoalsList.get(goalIndex).setMaxValue(orgFinanceGoals.getMaxValue() + incomeGoalsList.get(goalIndex).getMaxValue());
                    incomeGoalsList.get(goalIndex).setMinValue(orgFinanceGoals.getMinValue() + incomeGoalsList.get(goalIndex).getMinValue());
                }
            }
            else {
                orgFinanceGoalsResponse.setGoalType(orgFinanceGoals.getGoalType());
                orgFinanceGoalsResponse.setVenueId(orgFinanceGoals.getVenueId());
                orgFinanceGoalsResponse.setYear(orgFinanceGoals.getYear());
                orgFinanceGoalsResponse.setMonth(orgFinanceGoals.getMonth());
                orgFinanceGoalsResponse.setMinValue(orgFinanceGoals.getMinValue());
                orgFinanceGoalsResponse.setMaxValue(orgFinanceGoals.getMaxValue());

                if (orgFinanceLogRequest.getUserId() != 0) {
                    orgFinanceGoalsResponse.setUserId(orgFinanceGoals.getUserId());
                    orgFinanceGoalsResponse.setUserName(userName);
                    orgFinanceGoalsResponse.setId(orgFinanceGoals.getId());
                }
                else {
                    orgFinanceGoalsResponse.setUserId(-1);
                    orgFinanceGoalsResponse.setUserName(null);
                    orgFinanceGoalsResponse.setId(-1);
                }

                if (orgFinanceGoals.getGoalType() == BusinessGoalTypeEnum.FLOW.getCode()) {
                    checkGoals.put(uniqueKey, fIndex);
                    flowGoalsList.add(orgFinanceGoalsResponse);
                    fIndex++;
                } else if (orgFinanceGoals.getGoalType() == BusinessGoalTypeEnum.INCOME.getCode()) {
                    checkGoals.put(uniqueKey, iIndex);
                    incomeGoalsList.add(orgFinanceGoalsResponse);
                    iIndex++;
                }
            }
        }

        Collections.sort(flowGoalsList, new Comparator<OrgFinanceGoalsResponse> () {
            public int compare(OrgFinanceGoalsResponse arg0, OrgFinanceGoalsResponse arg1) {
                return arg0.getMonth().compareTo(arg1.getMonth());
            }
        });
        Collections.sort(incomeGoalsList, new Comparator<OrgFinanceGoalsResponse> () {
            public int compare(OrgFinanceGoalsResponse arg0, OrgFinanceGoalsResponse arg1) {
                return arg0.getMonth().compareTo(arg1.getMonth());
            }
        });

        if (flowGoalsList.size() == 12) {
            flowGoalsList = formatOrgFinanceGoalsResponseList(flowGoalsList);
        }

        if (incomeGoalsList.size() == 12) {
            incomeGoalsList = formatOrgFinanceGoalsResponseList(incomeGoalsList);
        }
        modelAndView.addObject("flowGoalsList", flowGoalsList);
        modelAndView.addObject("incomeGoalsList", incomeGoalsList);

        modelAndView.addObject("conditions", orgFinanceLogRequest);

        return setModelAndView(modelAndView);
    }

    @Desc("获取日目标")
    @ResponseBody
    @RequestMapping(value = "/getGoalsByMonth", method = RequestMethod.GET)
    public ResponseBean getEmployeeByVenue(OrgFinanceLogRequest orgFinanceLogRequest) {
        try {
            Map<String, Object> map = new HashMap<>();

            OrgFinanceGoals orgFinanceGoals = new OrgFinanceGoals();
            if (orgFinanceLogRequest.getGoalId() > 0) {
                orgFinanceGoals = orgFinanceGoalsService.getOrgFinanceGoals(orgFinanceLogRequest.getGoalId());
            } else {
                List<OrgFinanceGoals> orgFinanceGoalsList = orgFinanceGoalsService.queryOrgFinanceGoalsList(null,
                        orgFinanceLogRequest.getGoalType(), orgFinanceLogRequest.getVenueId(),
                        orgFinanceLogRequest.getYear(), 0);

                orgFinanceGoals.setGoalType(orgFinanceLogRequest.getGoalType());
                orgFinanceGoals.setVenueId(orgFinanceLogRequest.getVenueId());
                orgFinanceGoals.setYear(orgFinanceLogRequest.getYear());
                orgFinanceGoals.setMonth(orgFinanceLogRequest.getMonth());
                orgFinanceGoals.setMinValue(0);
                orgFinanceGoals.setMaxValue(0);
                for (OrgFinanceGoals orgFinanceGoals1 : orgFinanceGoalsList) {
                    if (orgFinanceGoals1.getMonth() == orgFinanceLogRequest.getMonth()) {
                        orgFinanceGoals.setMinValue(orgFinanceGoals.getMinValue() + orgFinanceGoals1.getMinValue());
                        orgFinanceGoals.setMaxValue(orgFinanceGoals.getMaxValue() + orgFinanceGoals1.getMaxValue());
                    }
                }
            }

            int monthDay = DateUtil.getMonthDay(orgFinanceGoals.getYear(), orgFinanceGoals.getMonth());

            String month = ((orgFinanceGoals.getMonth() > 9) ? "" : "0") + orgFinanceGoals.getMonth();
            String monthStart = orgFinanceGoals.getYear() + "-" + month + "-01";
            String monthEnd = orgFinanceGoals.getYear() + "-" + month + "-" + monthDay;

            map.put("month", month);
            map.put("monthDay", monthDay);
            map.put("monthStart", monthStart);
            map.put("monthEnd", monthEnd);

            List<Map> weekDateList = new ArrayList<>();

            map.put("minValue", orgFinanceGoals.getMinValue());
            map.put("maxValue", orgFinanceGoals.getMaxValue());

            int dayMinValue = orgFinanceGoals.getMinValue() / monthDay;
            int dayMaxValue = orgFinanceGoals.getMaxValue() / monthDay;

            map.put("dayMinValue", dayMinValue);
            map.put("dayMaxValue", dayMaxValue);

            String nextDate = monthStart;
            int wIndex = 1;
            while (true) {
                Map map1 = new HashMap();

                List<String> weekDate = DateUtil.getWeekDate(nextDate);
                if (wIndex == 1) {
                    for (int i = 0; i < weekDate.size(); i++) {
                        if (weekDate.get(i).compareTo(monthStart) == 0) {
                            weekDate = weekDate.subList(i, weekDate.size());
                            break;
                        }
                    }
                }

                if (weekDate.get(weekDate.size() - 1).compareTo(monthEnd) > 0) {
                    for (int i = 0; i < weekDate.size(); i++) {
                        if (weekDate.get(i).compareTo(monthEnd) == 0) {
                            weekDate = weekDate.subList(0, i + 1);
                            break;
                        }
                    }
                }

                map1.put("index", wIndex);
                map1.put("weekDate", weekDate);
                map1.put("weekMinValue", weekDate.size() * dayMinValue);
                map1.put("weekMaxValue", weekDate.size() * dayMaxValue);
                weekDateList.add(map1);

                if (weekDate.get(weekDate.size() - 1).compareTo(monthEnd) >= 0) {
                    break;
                }

                nextDate = DateUtil.getAddDay(nextDate, 7);
                wIndex++;
            }

            map.put("weekDateList", weekDateList);

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("保存目标")
    @ResponseBody
    @RequestMapping(value = "/saveGoals", method = RequestMethod.POST)
    public ResponseBean saveFinanceGoals(OrgFinanceGoals orgFinanceGoals) {
        try {

            int result;

            if (orgFinanceGoals.getId() != null) {
                orgFinanceGoals.setUpdateTime(DateUtil.getNowDate());
                result = orgFinanceGoalsService.saveOrgFinanceGoals(orgFinanceGoals);
            }
            else {
                List<Integer> userList = new ArrayList<>();
                if (orgFinanceGoals.getUserId() != 0) {
                    userList.add(orgFinanceGoals.getUserId());
                } else {
                    int count = orgFinanceUsersService.queryOrgFinanceUsersCount();
                    List<OrgFinanceUsers> orgFinanceUsersList = orgFinanceUsersService.queryOrgFinanceUsersList(0, count);

                    for (OrgFinanceUsers orgFinanceUsers : orgFinanceUsersList) {
                        if (orgFinanceUsers.getVenueId().intValue() == orgFinanceGoals.getVenueId().intValue()) {
                            userList.add(orgFinanceUsers.getId());
                        }
                    }
                }

                List<Integer> monthList = new ArrayList<>();
                if (orgFinanceGoals.getMonth() != 0) {
                    monthList.add(orgFinanceGoals.getMonth());
                } else {
                    for (int i = 0; i < 12; i++) {
                        monthList.add(i + 1);
                    }
                }

                List<OrgFinanceGoals> orgFinanceGoalsList = new ArrayList<>();
                for (Integer userId : userList) {
                    for (Integer month : monthList) {
                        OrgFinanceGoals orgFinanceGoals1 = new OrgFinanceGoals();

                        orgFinanceGoals1.setGoalType(orgFinanceGoals.getGoalType());
                        orgFinanceGoals1.setVenueId(orgFinanceGoals.getVenueId());
                        orgFinanceGoals1.setUserId(userId);
                        orgFinanceGoals1.setYear(orgFinanceGoals.getYear());
                        orgFinanceGoals1.setMonth(month);
                        orgFinanceGoals1.setMinValue(orgFinanceGoals.getMinValue());
                        orgFinanceGoals1.setMaxValue(orgFinanceGoals.getMaxValue());
                        orgFinanceGoals1.setCreateTime(DateUtil.getNowDate());
                        orgFinanceGoals1.setUpdateTime(DateUtil.getNowDate());

                        orgFinanceGoalsList.add(orgFinanceGoals1);
                    }
                }

                result = orgFinanceGoalsService.addOrgFinanceGoalsBatch(orgFinanceGoalsList);
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

    @Desc("获取目标")
    @ResponseBody
    @RequestMapping(value = "/getGoals", method = RequestMethod.GET)
    public ResponseBean getFinanceGoals(OrgFinanceLogRequest orgFinanceLogRequest) {
        try {
            Map<String, Object> map = new HashMap<>();

            OrgFinanceGoals orgFinanceGoals = orgFinanceGoalsService.getOrgFinanceGoals(orgFinanceLogRequest.getGoalId());
            map.put("orgFinanceGoals", orgFinanceGoals);

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    /*** 录入 start ***/

    @Desc("运用财务编辑")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView renderFinanceAdd() {

        ModelAndView modelAndView = new ModelAndView("Finance/Add");

        OrgOperator orgOperator = getLoginUser();

        OrgFinanceDataFlow orgFinanceDataFlow = new OrgFinanceDataFlow();
        if (orgOperator.getRoleId() == RoleEnum.ROLE_SUPER_FINANCE_INPUT.getCode()) {
            orgFinanceDataFlow.setUserId(orgOperator.getId());
            orgFinanceDataFlow.setVenueId(Integer.parseInt(orgOperator.getUserNo()));
        }

        modelAndView.addObject("orgFinanceDataFlow", orgFinanceDataFlow);

        modelAndView.addObject("BusinessTypeEnumList", EnumUtils.getEnumList(BusinessTypeEnum.class));
        modelAndView.addObject("BusinessChannelTypeEnumList", orgFinanceEnumsService.queryOrgFinanceEnumsList("GROUP_CHANNEL"));
        modelAndView.addObject("BusinessIncomeTypeEnumList", orgFinanceEnumsService.queryOrgFinanceEnumsList("GROUP_CINCOME"));

        List<OrgFinanceVenues> orgFinanceVenuesList = orgFinanceVenuesService.queryOrgFinanceVenuesList();
        modelAndView.addObject("orgFinanceVenuesList", orgFinanceVenuesList);

        modelAndView.addObject("todayDate", DateUtil.getNowDate());

        return setModelAndView(modelAndView);
    }

    // 设置流水数据
    private int setOrgFinanceDataFlow(OrgFinanceLogResponse orgFinanceLogResponse, boolean isAdd) {
        OrgFinanceDataFlow orgFinanceDataFlow = new OrgFinanceDataFlow();
        boolean isSave = true;

        orgFinanceDataFlow.setBusinessNo(orgFinanceLogResponse.getBusinessNo());
        orgFinanceDataFlow.setBusinessDate(orgFinanceLogResponse.getBusinessDate());
        orgFinanceDataFlow.setBusinessType(orgFinanceLogResponse.getBusinessType());
        orgFinanceDataFlow.setVenueId(orgFinanceLogResponse.getVenueId());
        orgFinanceDataFlow.setUserId(orgFinanceLogResponse.getUserId());
        if (orgFinanceLogResponse.getBusinessType() == BusinessTypeEnum.VENUE_LEASE.getCode()) {
            orgFinanceDataFlow.setChannelType("无");
        }
        else {
            orgFinanceDataFlow.setChannelType(orgFinanceLogResponse.getChannelName());
        }
        if (orgFinanceLogResponse.getPipelineValue() != null && orgFinanceLogResponse.getPipelineValue() > 0) {
            orgFinanceDataFlow.setPipelineValue(orgFinanceLogResponse.getPipelineValue());
        }
        else {
            isSave = false;
        }
        orgFinanceDataFlow.setOperatorId(getLoginUser().getId());
        orgFinanceDataFlow.setUpdateTime(DateUtil.getNowDate());

        if (isAdd) {
            orgFinanceDataFlow.setCreateTime(DateUtil.getNowDate());
            return isSave ? orgFinanceDataFlowService.addOrgFinanceDataFlow(orgFinanceDataFlow) : -1;
        }
        else {
            return isSave ? orgFinanceDataFlowService.saveOrgFinanceDataFlow(orgFinanceDataFlow) : -1;
        }
    }

    // 设置体验数据
    private int setOrgFinanceDataBusiness(OrgFinanceLogResponse orgFinanceLogResponse, boolean isAdd) {
        OrgFinanceDataBusiness orgFinanceDataBusiness = new OrgFinanceDataBusiness();
        boolean isSave = true;

        orgFinanceDataBusiness.setBusinessNo(orgFinanceLogResponse.getBusinessNo());
        orgFinanceDataBusiness.setBusinessDate(orgFinanceLogResponse.getBusinessDate());
        orgFinanceDataBusiness.setBusinessType(orgFinanceLogResponse.getBusinessType());
        orgFinanceDataBusiness.setVenueId(orgFinanceLogResponse.getVenueId());
        orgFinanceDataBusiness.setUserId(orgFinanceLogResponse.getUserId());
        orgFinanceDataBusiness.setChannelType(orgFinanceLogResponse.getChannelName());
        if (orgFinanceLogResponse.getAccessCount() != null && orgFinanceLogResponse.getAccessCount() > 0) {
            orgFinanceDataBusiness.setAccessCount(orgFinanceLogResponse.getAccessCount());
            orgFinanceDataBusiness.setBusinessCount(orgFinanceLogResponse.getBusinessCount());
        }
        else {
            isSave = false;
        }
        orgFinanceDataBusiness.setOperatorId(getLoginUser().getId());
        orgFinanceDataBusiness.setUpdateTime(DateUtil.getNowDate());

        if (isAdd) {
            orgFinanceDataBusiness.setCreateTime(DateUtil.getNowDate());
            return isSave ? orgFinanceDataBusinessService.addOrgFinanceDataBusiness(orgFinanceDataBusiness) : -1;
        }
        else {
            return isSave ? orgFinanceDataBusinessService.saveOrgFinanceDataBusiness(orgFinanceDataBusiness) : -1;
        }
    }

    // 设置确认收入
    private int setOrgFinanceDataIncome(OrgFinanceLogResponse orgFinanceLogResponse, boolean isAdd) {
        OrgFinanceDataIncome orgFinanceDataIncome = new OrgFinanceDataIncome();
        boolean isSave = true;

        orgFinanceDataIncome.setBusinessNo(orgFinanceLogResponse.getBusinessNo());
        orgFinanceDataIncome.setBusinessDate(orgFinanceLogResponse.getBusinessDate());
        orgFinanceDataIncome.setBusinessType(orgFinanceLogResponse.getBusinessType());
        orgFinanceDataIncome.setVenueId(orgFinanceLogResponse.getVenueId());
        orgFinanceDataIncome.setUserId(orgFinanceLogResponse.getUserId());
        if (orgFinanceLogResponse.getBusinessType() == BusinessTypeEnum.VENUE_LEASE.getCode()) {
            orgFinanceDataIncome.setIncomeType("无");
        }
        else {
            orgFinanceDataIncome.setIncomeType(orgFinanceLogResponse.getIncomeType());
        }
        if (orgFinanceLogResponse.getIncomePerValue() != null && orgFinanceLogResponse.getIncomePerValue() > 0
                && orgFinanceLogResponse.getClassCount() != null && orgFinanceLogResponse.getClassCount() > 0) {
            orgFinanceDataIncome.setIncomePerValue(orgFinanceLogResponse.getIncomePerValue());
            orgFinanceDataIncome.setIncomeValue(orgFinanceLogResponse.getClassCount() * orgFinanceLogResponse.getIncomePerValue());
        }
        else if (orgFinanceLogResponse.getIncomeValue() != null && orgFinanceLogResponse.getIncomeValue() > 0) {
            orgFinanceDataIncome.setIncomePerValue(0);
            orgFinanceDataIncome.setIncomeValue(orgFinanceLogResponse.getIncomeValue());
        }
        else {
            isSave = false;
        }
        orgFinanceDataIncome.setOperatorId(getLoginUser().getId());
        orgFinanceDataIncome.setUpdateTime(DateUtil.getNowDate());

        if (isAdd) {
            orgFinanceDataIncome.setCreateTime(DateUtil.getNowDate());
            return isSave ? orgFinanceDataIncomeService.addOrgFinanceDataIncome(orgFinanceDataIncome) : -1;
        }
        else {
            return isSave ? orgFinanceDataIncomeService.saveOrgFinanceDataIncome(orgFinanceDataIncome) : -1;
        }
    }

    // 设置签到数据
    private int setOrgFinanceDataAttendance(OrgFinanceLogResponse orgFinanceLogResponse, boolean isAdd) {
        OrgFinanceDataAttendance orgFinanceDataAttendance = new OrgFinanceDataAttendance();
        boolean isSave = true;

        orgFinanceDataAttendance.setBusinessNo(orgFinanceLogResponse.getBusinessNo());
        orgFinanceDataAttendance.setBusinessDate(orgFinanceLogResponse.getBusinessDate());
        orgFinanceDataAttendance.setBusinessType(orgFinanceLogResponse.getBusinessType());
        orgFinanceDataAttendance.setVenueId(orgFinanceLogResponse.getVenueId());
        orgFinanceDataAttendance.setUserId(orgFinanceLogResponse.getUserId());
        orgFinanceDataAttendance.setIncomeType(orgFinanceLogResponse.getIncomeType());
        if (orgFinanceLogResponse.getRegisterCount() != null && orgFinanceLogResponse.getRegisterCount() > 0
                && orgFinanceLogResponse.getClassCount() != null && orgFinanceLogResponse.getClassCount() > 0) {
            orgFinanceDataAttendance.setRegisterCount(orgFinanceLogResponse.getRegisterCount());
            orgFinanceDataAttendance.setClassCount(orgFinanceLogResponse.getClassCount());
        }
        else {
            isSave = false;
        }
        orgFinanceDataAttendance.setOperatorId(getLoginUser().getId());
        orgFinanceDataAttendance.setUpdateTime(DateUtil.getNowDate());

        if (isAdd) {
            orgFinanceDataAttendance.setCreateTime(DateUtil.getNowDate());
            return isSave ? orgFinanceDataAttendanceService.addOrgFinanceDataAttendance(orgFinanceDataAttendance) : -1;
        }
        else {
            return isSave ? orgFinanceDataAttendanceService.saveOrgFinanceDataAttendance(orgFinanceDataAttendance) : -1;
        }
    }

    // 设置闲忙时段
    private int setOrgFinanceDataTimes(OrgFinanceLogResponse orgFinanceLogResponse, boolean isAdd) {
        OrgFinanceDataTimes orgFinanceDataTimes = new OrgFinanceDataTimes();
        boolean isSave = true;

        orgFinanceDataTimes.setBusinessNo(orgFinanceLogResponse.getBusinessNo());
        orgFinanceDataTimes.setBusinessDate(orgFinanceLogResponse.getBusinessDate());
        orgFinanceDataTimes.setBusinessType(orgFinanceLogResponse.getBusinessType());
        orgFinanceDataTimes.setVenueId(orgFinanceLogResponse.getVenueId());
        orgFinanceDataTimes.setUserId(orgFinanceLogResponse.getUserId());
        if (orgFinanceLogResponse.getNullTotalCount() != null && orgFinanceLogResponse.getNullTotalCount() > 0
                && orgFinanceLogResponse.getNullCount() != null && orgFinanceLogResponse.getNullCount() > 0
                && orgFinanceLogResponse.getHotTotalCount() != null && orgFinanceLogResponse.getHotTotalCount() > 0
                && orgFinanceLogResponse.getHotCount() != null && orgFinanceLogResponse.getHotCount() > 0) {
            orgFinanceDataTimes.setNullTotalCount(orgFinanceLogResponse.getNullTotalCount());
            orgFinanceDataTimes.setNullCount(orgFinanceLogResponse.getNullCount());
            orgFinanceDataTimes.setHotTotalCount(orgFinanceLogResponse.getHotTotalCount());
            orgFinanceDataTimes.setHotCount(orgFinanceLogResponse.getHotCount());
        }
        else {
            isSave = false;
        }
        orgFinanceDataTimes.setOperatorId(getLoginUser().getId());
        orgFinanceDataTimes.setUpdateTime(DateUtil.getNowDate());

        if (isAdd) {
            orgFinanceDataTimes.setCreateTime(DateUtil.getNowDate());
            return isSave ? orgFinanceDataTimesService.addOrgFinanceDataTimes(orgFinanceDataTimes) : -1;
        }
        else {
            return isSave ? orgFinanceDataTimesService.saveOrgFinanceDataTimes(orgFinanceDataTimes) : -1;
        }
    }

    @Desc("运用财务编辑数据提交")
    @ResponseBody
    @RequestMapping(value = "/saveOrgFinanceLogResponse", method = RequestMethod.POST)
    public ResponseBean saveOrgFinanceLogResponse(OrgFinanceLogResponse orgFinanceLogResponse) {
        try {

            Map map = new HashMap();

            int resultFlow;
            int resultBusiness;
            int resultIncome;
            int resultAttendance;
            int resultTimes;

            if (orgFinanceLogResponse.getBusinessNo() != null && !orgFinanceLogResponse.getBusinessNo().equals("")) {
                resultFlow = setOrgFinanceDataFlow(orgFinanceLogResponse, false);
                resultBusiness = setOrgFinanceDataBusiness(orgFinanceLogResponse, false);
                resultIncome = setOrgFinanceDataIncome(orgFinanceLogResponse, false);
                resultAttendance = setOrgFinanceDataAttendance(orgFinanceLogResponse, false);
                resultTimes = setOrgFinanceDataTimes(orgFinanceLogResponse, false);
            } else {
                orgFinanceLogResponse.setBusinessNo(StrUtil.getUUID());

                resultFlow = setOrgFinanceDataFlow(orgFinanceLogResponse, true);
                resultBusiness = setOrgFinanceDataBusiness(orgFinanceLogResponse, true);
                resultIncome = setOrgFinanceDataIncome(orgFinanceLogResponse, true);
                resultAttendance = setOrgFinanceDataAttendance(orgFinanceLogResponse, true);
                resultTimes = setOrgFinanceDataTimes(orgFinanceLogResponse, true);
            }

            map.put("resultFlow", resultFlow);
            map.put("resultBusiness", resultBusiness);
            map.put("resultIncome", resultIncome);
            map.put("resultAttendance", resultAttendance);
            map.put("resultTimes", resultTimes);
            map.put("businessNo", orgFinanceLogResponse.getBusinessNo());

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("流水数据查询")
    @ResponseBody
    @RequestMapping(value = "/getOrgFinanceDataFlow", method = RequestMethod.GET)
    public ResponseBean getOrgFinanceDataFlow(String businessNo) {
        try {
            Map map = new HashMap();

            List<OrgFinanceVenues> orgFinanceVenuesList = orgFinanceVenuesService.queryOrgFinanceVenuesList();

            int count = orgFinanceUsersService.queryOrgFinanceUsersCount();
            List<OrgFinanceUsers> orgFinanceUsersList = orgFinanceUsersService.queryOrgFinanceUsersList(0, count);

            OrgFinanceDataFlow orgFinanceDataFlow = orgFinanceDataFlowService.getOrgFinanceDataFlow(businessNo);

            OrgFinanceLogResponse orgFinanceLogResponse = new OrgFinanceLogResponse();

            orgFinanceLogResponse.setBusinessNo(orgFinanceDataFlow.getBusinessNo());
            orgFinanceLogResponse.setBusinessType(orgFinanceDataFlow.getBusinessType());
            orgFinanceLogResponse.setBusinessDate(orgFinanceDataFlow.getBusinessDate());
            orgFinanceLogResponse.setVenueId(orgFinanceDataFlow.getVenueId());
            orgFinanceLogResponse.setVenueName(getVenueName(orgFinanceDataFlow.getVenueId(), orgFinanceVenuesList));
            orgFinanceLogResponse.setUserId(orgFinanceDataFlow.getUserId());
            orgFinanceLogResponse.setRealName(getRealName(orgFinanceDataFlow.getUserId(), orgFinanceUsersList));
            orgFinanceLogResponse.setChannelName(orgFinanceDataFlow.getChannelType());
            orgFinanceLogResponse.setPipelineValue(orgFinanceDataFlow.getPipelineValue());

            map.put("orgFinanceDataFlow", orgFinanceLogResponse);

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("流水数据删除")
    @ResponseBody
    @RequestMapping(value = "/deleteOrgFinanceDataFlow", method = RequestMethod.POST)
    public ResponseBean deleteOrgFinanceDataFlow(OrgFinanceDataFlow orgFinanceDataFlow) {
        try {
            int result = orgFinanceDataFlowService.deleteOrgFinanceDataFlow(orgFinanceDataFlow.getBusinessNo());

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("体验成交数据查询")
    @ResponseBody
    @RequestMapping(value = "/getOrgFinanceDataBusiness", method = RequestMethod.GET)
    public ResponseBean getOrgFinanceDataBusiness(String businessNo) {
        try {
            Map map = new HashMap();

            List<OrgFinanceVenues> orgFinanceVenuesList = orgFinanceVenuesService.queryOrgFinanceVenuesList();

            int count = orgFinanceUsersService.queryOrgFinanceUsersCount();
            List<OrgFinanceUsers> orgFinanceUsersList = orgFinanceUsersService.queryOrgFinanceUsersList(0, count);

            OrgFinanceDataBusiness orgFinanceDataBusiness = orgFinanceDataBusinessService.getOrgFinanceDataBusiness(businessNo);

            OrgFinanceLogResponse orgFinanceLogResponse = new OrgFinanceLogResponse();

            orgFinanceLogResponse.setBusinessNo(orgFinanceDataBusiness.getBusinessNo());
            orgFinanceLogResponse.setBusinessType(orgFinanceDataBusiness.getBusinessType());
            orgFinanceLogResponse.setBusinessDate(orgFinanceDataBusiness.getBusinessDate());
            orgFinanceLogResponse.setVenueId(orgFinanceDataBusiness.getVenueId());
            orgFinanceLogResponse.setVenueName(getVenueName(orgFinanceDataBusiness.getVenueId(), orgFinanceVenuesList));
            orgFinanceLogResponse.setUserId(orgFinanceDataBusiness.getUserId());
            orgFinanceLogResponse.setRealName(getRealName(orgFinanceDataBusiness.getUserId(), orgFinanceUsersList));
            orgFinanceLogResponse.setChannelName(orgFinanceDataBusiness.getChannelType());
            orgFinanceLogResponse.setAccessCount(orgFinanceDataBusiness.getAccessCount());
            orgFinanceLogResponse.setBusinessCount(orgFinanceDataBusiness.getBusinessCount());

            map.put("orgFinanceDataBusiness", orgFinanceLogResponse);

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("体验成交数据删除")
    @ResponseBody
    @RequestMapping(value = "/deleteOrgFinanceDataBusiness", method = RequestMethod.POST)
    public ResponseBean deleteOrgFinanceDataBusiness(OrgFinanceDataBusiness orgFinanceDataBusiness) {
        try {
            int result = orgFinanceDataBusinessService.deleteOrgFinanceDataBusiness(orgFinanceDataBusiness.getBusinessNo());

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("收入数据查询")
    @ResponseBody
    @RequestMapping(value = "/getOrgFinanceDataIncome", method = RequestMethod.GET)
    public ResponseBean getOrgFinanceDataIncome(String businessNo) {
        try {
            Map map = new HashMap();

            List<OrgFinanceVenues> orgFinanceVenuesList = orgFinanceVenuesService.queryOrgFinanceVenuesList();

            int count = orgFinanceUsersService.queryOrgFinanceUsersCount();
            List<OrgFinanceUsers> orgFinanceUsersList = orgFinanceUsersService.queryOrgFinanceUsersList(0, count);

            OrgFinanceDataIncome orgFinanceDataIncome = orgFinanceDataIncomeService.getOrgFinanceDataIncome(businessNo);

            OrgFinanceLogResponse orgFinanceLogResponse = new OrgFinanceLogResponse();

            orgFinanceLogResponse.setBusinessNo(orgFinanceDataIncome.getBusinessNo());
            orgFinanceLogResponse.setBusinessType(orgFinanceDataIncome.getBusinessType());
            orgFinanceLogResponse.setBusinessDate(orgFinanceDataIncome.getBusinessDate());
            orgFinanceLogResponse.setVenueId(orgFinanceDataIncome.getVenueId());
            orgFinanceLogResponse.setVenueName(getVenueName(orgFinanceDataIncome.getVenueId(), orgFinanceVenuesList));
            orgFinanceLogResponse.setUserId(orgFinanceDataIncome.getUserId());
            orgFinanceLogResponse.setRealName(getRealName(orgFinanceDataIncome.getUserId(), orgFinanceUsersList));
            orgFinanceLogResponse.setIncomeType(orgFinanceDataIncome.getIncomeType());
            orgFinanceLogResponse.setIncomePerValue(orgFinanceDataIncome.getIncomePerValue());
            orgFinanceLogResponse.setIncomeValue(orgFinanceDataIncome.getIncomeValue());

            map.put("orgFinanceDataIncome", orgFinanceLogResponse);

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("收入数据删除")
    @ResponseBody
    @RequestMapping(value = "/deleteOrgFinanceDataIncome", method = RequestMethod.POST)
    public ResponseBean deleteOrgFinanceDataIncome(OrgFinanceDataIncome orgFinanceDataIncome) {
        try {
            int result = orgFinanceDataIncomeService.deleteOrgFinanceDataIncome(orgFinanceDataIncome.getBusinessNo());

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("签到数据查询")
    @ResponseBody
    @RequestMapping(value = "/getOrgFinanceDataAttendance", method = RequestMethod.GET)
    public ResponseBean getOrgFinanceDataAttendance(String businessNo) {
        try {
            Map map = new HashMap();

            List<OrgFinanceVenues> orgFinanceVenuesList = orgFinanceVenuesService.queryOrgFinanceVenuesList();

            int count = orgFinanceUsersService.queryOrgFinanceUsersCount();
            List<OrgFinanceUsers> orgFinanceUsersList = orgFinanceUsersService.queryOrgFinanceUsersList(0, count);

            OrgFinanceDataAttendance orgFinanceDataAttendance = orgFinanceDataAttendanceService.getOrgFinanceDataAttendance(businessNo);

            OrgFinanceLogResponse orgFinanceLogResponse = new OrgFinanceLogResponse();

            orgFinanceLogResponse.setBusinessNo(orgFinanceDataAttendance.getBusinessNo());
            orgFinanceLogResponse.setBusinessType(orgFinanceDataAttendance.getBusinessType());
            orgFinanceLogResponse.setBusinessDate(orgFinanceDataAttendance.getBusinessDate());
            orgFinanceLogResponse.setVenueId(orgFinanceDataAttendance.getVenueId());
            orgFinanceLogResponse.setVenueName(getVenueName(orgFinanceDataAttendance.getVenueId(), orgFinanceVenuesList));
            orgFinanceLogResponse.setUserId(orgFinanceDataAttendance.getUserId());
            orgFinanceLogResponse.setRealName(getRealName(orgFinanceDataAttendance.getUserId(), orgFinanceUsersList));
            orgFinanceLogResponse.setIncomeType(orgFinanceDataAttendance.getIncomeType());
            orgFinanceLogResponse.setRegisterCount(orgFinanceDataAttendance.getRegisterCount());
            orgFinanceLogResponse.setClassCount(orgFinanceDataAttendance.getClassCount());

            map.put("orgFinanceDataAttendance", orgFinanceLogResponse);

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("签到数据删除")
    @ResponseBody
    @RequestMapping(value = "/deleteOrgFinanceDataAttendance", method = RequestMethod.POST)
    public ResponseBean deleteOrgFinanceDataAttendance(OrgFinanceDataAttendance orgFinanceDataAttendance) {
        try {
            int result = orgFinanceDataAttendanceService.deleteOrgFinanceDataAttendance(orgFinanceDataAttendance.getBusinessNo());

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("闲忙时数据查询")
    @ResponseBody
    @RequestMapping(value = "/getOrgFinanceDataTimes", method = RequestMethod.GET)
    public ResponseBean getOrgFinanceDataTimes(String businessNo) {
        try {
            Map map = new HashMap();

            List<OrgFinanceVenues> orgFinanceVenuesList = orgFinanceVenuesService.queryOrgFinanceVenuesList();

            int count = orgFinanceUsersService.queryOrgFinanceUsersCount();
            List<OrgFinanceUsers> orgFinanceUsersList = orgFinanceUsersService.queryOrgFinanceUsersList(0, count);

            OrgFinanceDataTimes orgFinanceDataTimes = orgFinanceDataTimesService.getOrgFinanceDataTimes(businessNo);

            OrgFinanceLogResponse orgFinanceLogResponse = new OrgFinanceLogResponse();

            orgFinanceLogResponse.setBusinessNo(orgFinanceDataTimes.getBusinessNo());
            orgFinanceLogResponse.setBusinessType(orgFinanceDataTimes.getBusinessType());
            orgFinanceLogResponse.setBusinessDate(orgFinanceDataTimes.getBusinessDate());
            orgFinanceLogResponse.setVenueId(orgFinanceDataTimes.getVenueId());
            orgFinanceLogResponse.setVenueName(getVenueName(orgFinanceDataTimes.getVenueId(), orgFinanceVenuesList));
            orgFinanceLogResponse.setUserId(orgFinanceDataTimes.getUserId());
            orgFinanceLogResponse.setRealName(getRealName(orgFinanceDataTimes.getUserId(), orgFinanceUsersList));
            orgFinanceLogResponse.setHotCount(orgFinanceDataTimes.getHotCount());
            orgFinanceLogResponse.setHotTotalCount(orgFinanceDataTimes.getHotTotalCount());
            orgFinanceLogResponse.setNullCount(orgFinanceDataTimes.getNullCount());
            orgFinanceLogResponse.setNullTotalCount(orgFinanceDataTimes.getNullTotalCount());

            map.put("orgFinanceDataTimes", orgFinanceLogResponse);

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("闲忙时数据删除")
    @ResponseBody
    @RequestMapping(value = "/deleteOrgFinanceDataTimes", method = RequestMethod.POST)
    public ResponseBean deleteOrgFinanceDataTimes(OrgFinanceDataTimes orgFinanceDataTimes) {
        try {
            int result = orgFinanceDataTimesService.deleteOrgFinanceDataTimes(orgFinanceDataTimes.getBusinessNo());

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("财务数据列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView renderFinanceList(OrgFinanceLogRequest orgFinanceLogRequest) {

        ModelAndView modelAndView = new ModelAndView("Finance/List");

        OrgOperator orgOperator = getLoginUser();
        int userId = 0;
        if (orgOperator.getRoleId() == RoleEnum.ROLE_SUPER_FINANCE_INPUT.getCode()) {
            userId = orgOperator.getId();
        }

        if (orgFinanceLogRequest.getTabType() < 1) {
            orgFinanceLogRequest.setTabType(1);
        }

        modelAndView.addObject("BusinessTypeEnumList", EnumUtils.getEnumList(BusinessTypeEnum.class));
        modelAndView.addObject("BusinessTypeEnum", EnumUtils.getEnumMap(BusinessTypeEnum.class));

        modelAndView.addObject("conditions", orgFinanceLogRequest);

        List<OrgFinanceVenues> orgFinanceVenuesList = orgFinanceVenuesService.queryOrgFinanceVenuesList();
        modelAndView.addObject("orgFinanceVenuesList", orgFinanceVenuesList);

        int count = orgFinanceUsersService.queryOrgFinanceUsersCount();
        List<OrgFinanceUsers> orgFinanceUsersList = orgFinanceUsersService.queryOrgFinanceUsersList(0, count);

        List<OrgFinanceLogResponse> orgFinanceLogResponseList = new ArrayList<>();
        int total, start, pageSize = 10;

        switch (orgFinanceLogRequest.getTabType()) {
            case 2:
                total = orgFinanceDataBusinessService.queryOrgFinanceDataBusinessCount(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), userId, null, null);
                start = orgFinanceLogRequest.getPage() < 1 ? 0 : orgFinanceLogRequest.getPage() - 1;
                List<OrgFinanceDataBusiness> orgFinanceDataBusinessList = orgFinanceDataBusinessService.queryOrgFinanceDataBusinessList(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), userId, null, null, start * pageSize, pageSize);

                for (OrgFinanceDataBusiness orgFinanceDataBusiness : orgFinanceDataBusinessList) {
                    OrgFinanceLogResponse orgFinanceLogResponse = new OrgFinanceLogResponse();

                    orgFinanceLogResponse.setBusinessNo(orgFinanceDataBusiness.getBusinessNo());
                    orgFinanceLogResponse.setBusinessType(orgFinanceDataBusiness.getBusinessType());
                    orgFinanceLogResponse.setBusinessDate(orgFinanceDataBusiness.getBusinessDate());
                    orgFinanceLogResponse.setVenueId(orgFinanceDataBusiness.getVenueId());
                    orgFinanceLogResponse.setVenueName(getVenueName(orgFinanceDataBusiness.getVenueId(), orgFinanceVenuesList));
                    orgFinanceLogResponse.setUserId(orgFinanceDataBusiness.getUserId());
                    orgFinanceLogResponse.setRealName(getRealName(orgFinanceDataBusiness.getUserId(), orgFinanceUsersList));
                    orgFinanceLogResponse.setChannelName(orgFinanceDataBusiness.getChannelType());
                    orgFinanceLogResponse.setAccessCount(orgFinanceDataBusiness.getAccessCount());
                    orgFinanceLogResponse.setBusinessCount(orgFinanceDataBusiness.getBusinessCount());

                    orgFinanceLogResponseList.add(orgFinanceLogResponse);
                }

                break;
            case 3:
                total = orgFinanceDataIncomeService.queryOrgFinanceDataIncomeCount(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), userId, null, null);
                start = orgFinanceLogRequest.getPage() < 1 ? 0 : orgFinanceLogRequest.getPage() - 1;
                List<OrgFinanceDataIncome> orgFinanceDataIncomeList = orgFinanceDataIncomeService.queryOrgFinanceDataIncomeList(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), userId, null, null, start * pageSize, pageSize);

                for (OrgFinanceDataIncome orgFinanceDataIncome : orgFinanceDataIncomeList) {
                    OrgFinanceLogResponse orgFinanceLogResponse = new OrgFinanceLogResponse();

                    orgFinanceLogResponse.setBusinessNo(orgFinanceDataIncome.getBusinessNo());
                    orgFinanceLogResponse.setBusinessType(orgFinanceDataIncome.getBusinessType());
                    orgFinanceLogResponse.setBusinessDate(orgFinanceDataIncome.getBusinessDate());
                    orgFinanceLogResponse.setVenueId(orgFinanceDataIncome.getVenueId());
                    orgFinanceLogResponse.setVenueName(getVenueName(orgFinanceDataIncome.getVenueId(), orgFinanceVenuesList));
                    orgFinanceLogResponse.setUserId(orgFinanceDataIncome.getUserId());
                    orgFinanceLogResponse.setRealName(getRealName(orgFinanceDataIncome.getUserId(), orgFinanceUsersList));
                    orgFinanceLogResponse.setIncomeType(orgFinanceDataIncome.getIncomeType());
                    orgFinanceLogResponse.setIncomeValue(orgFinanceDataIncome.getIncomeValue());
                    orgFinanceLogResponse.setIncomePerValue(orgFinanceDataIncome.getIncomePerValue());

                    orgFinanceLogResponseList.add(orgFinanceLogResponse);
                }

                break;
            case 4:
                total = orgFinanceDataAttendanceService.queryOrgFinanceDataAttendanceCount(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), userId, null, null);
                start = orgFinanceLogRequest.getPage() < 1 ? 0 : orgFinanceLogRequest.getPage() - 1;
                List<OrgFinanceDataAttendance> orgFinanceDataAttendanceList = orgFinanceDataAttendanceService.queryOrgFinanceDataAttendanceList(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), userId, null, null, start * pageSize, pageSize);

                for (OrgFinanceDataAttendance orgFinanceDataAttendance : orgFinanceDataAttendanceList) {
                    OrgFinanceLogResponse orgFinanceLogResponse = new OrgFinanceLogResponse();

                    orgFinanceLogResponse.setBusinessNo(orgFinanceDataAttendance.getBusinessNo());
                    orgFinanceLogResponse.setBusinessType(orgFinanceDataAttendance.getBusinessType());
                    orgFinanceLogResponse.setBusinessDate(orgFinanceDataAttendance.getBusinessDate());
                    orgFinanceLogResponse.setVenueId(orgFinanceDataAttendance.getVenueId());
                    orgFinanceLogResponse.setVenueName(getVenueName(orgFinanceDataAttendance.getVenueId(), orgFinanceVenuesList));
                    orgFinanceLogResponse.setUserId(orgFinanceDataAttendance.getUserId());
                    orgFinanceLogResponse.setRealName(getRealName(orgFinanceDataAttendance.getUserId(), orgFinanceUsersList));
                    orgFinanceLogResponse.setIncomeType(orgFinanceDataAttendance.getIncomeType());
                    orgFinanceLogResponse.setRegisterCount(orgFinanceDataAttendance.getRegisterCount());
                    orgFinanceLogResponse.setClassCount(orgFinanceDataAttendance.getClassCount());

                    orgFinanceLogResponseList.add(orgFinanceLogResponse);
                }

                break;
            case 5:
                total = orgFinanceDataTimesService.queryOrgFinanceDataTimesCount(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), userId, null, null);
                start = orgFinanceLogRequest.getPage() < 1 ? 0 : orgFinanceLogRequest.getPage() - 1;
                List<OrgFinanceDataTimes> orgFinanceDataTimesList = orgFinanceDataTimesService.queryOrgFinanceDataTimesList(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), userId, null, null, start * pageSize, pageSize);

                for (OrgFinanceDataTimes orgFinanceDataTimes : orgFinanceDataTimesList) {
                    OrgFinanceLogResponse orgFinanceLogResponse = new OrgFinanceLogResponse();

                    orgFinanceLogResponse.setBusinessNo(orgFinanceDataTimes.getBusinessNo());
                    orgFinanceLogResponse.setBusinessType(orgFinanceDataTimes.getBusinessType());
                    orgFinanceLogResponse.setBusinessDate(orgFinanceDataTimes.getBusinessDate());
                    orgFinanceLogResponse.setVenueId(orgFinanceDataTimes.getVenueId());
                    orgFinanceLogResponse.setVenueName(getVenueName(orgFinanceDataTimes.getVenueId(), orgFinanceVenuesList));
                    orgFinanceLogResponse.setUserId(orgFinanceDataTimes.getUserId());
                    orgFinanceLogResponse.setRealName(getRealName(orgFinanceDataTimes.getUserId(), orgFinanceUsersList));
                    orgFinanceLogResponse.setNullTotalCount(orgFinanceDataTimes.getNullTotalCount());
                    orgFinanceLogResponse.setNullCount(orgFinanceDataTimes.getNullCount());
                    orgFinanceLogResponse.setHotCount(orgFinanceDataTimes.getHotCount());
                    orgFinanceLogResponse.setHotTotalCount(orgFinanceDataTimes.getHotTotalCount());

                    orgFinanceLogResponseList.add(orgFinanceLogResponse);
                }

                break;
            case 1:
            default:
                total = orgFinanceDataFlowService.queryOrgFinanceDataFlowCount(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), userId, null, null);
                start = orgFinanceLogRequest.getPage() < 1 ? 0 : orgFinanceLogRequest.getPage() - 1;
                pageSize = 10;
                List<OrgFinanceDataFlow> orgFinanceDataFlowList = orgFinanceDataFlowService.queryOrgFinanceDataFlowList(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), userId, null, null, start * pageSize, pageSize);

                for (OrgFinanceDataFlow orgFinanceDataFlow : orgFinanceDataFlowList) {
                    OrgFinanceLogResponse orgFinanceLogResponse = new OrgFinanceLogResponse();

                    orgFinanceLogResponse.setBusinessNo(orgFinanceDataFlow.getBusinessNo());
                    orgFinanceLogResponse.setBusinessType(orgFinanceDataFlow.getBusinessType());
                    orgFinanceLogResponse.setBusinessDate(orgFinanceDataFlow.getBusinessDate());
                    orgFinanceLogResponse.setVenueId(orgFinanceDataFlow.getVenueId());
                    orgFinanceLogResponse.setVenueName(getVenueName(orgFinanceDataFlow.getVenueId(), orgFinanceVenuesList));
                    orgFinanceLogResponse.setUserId(orgFinanceDataFlow.getUserId());
                    orgFinanceLogResponse.setRealName(getRealName(orgFinanceDataFlow.getUserId(), orgFinanceUsersList));
                    orgFinanceLogResponse.setChannelName(orgFinanceDataFlow.getChannelType());
                    orgFinanceLogResponse.setPipelineValue(orgFinanceDataFlow.getPipelineValue());

                    orgFinanceLogResponseList.add(orgFinanceLogResponse);
                }
        }

        modelAndView.addObject("orgFinanceDataList", orgFinanceLogResponseList);

        Page page = new Page(pageSize, total);
        page.setPage(orgFinanceLogRequest.getPage());

        modelAndView.addObject("total", total);
        modelAndView.addObject("pageURL", "/admin/finance/list?tabType=" + orgFinanceLogRequest.getTabType());
        modelAndView.addObject("page", page);

        return setModelAndView(modelAndView);
    }

    /*** 业绩汇总 ***/

    @Desc("个人业绩统计")
    @RequestMapping(value = "/performanceSummary", method = RequestMethod.GET)
    public ModelAndView renderFinancePerformanceSummary(OrgFinanceLogRequest orgFinanceLogRequest) throws Exception {

        ModelAndView modelAndView = new ModelAndView("Finance/PerformanceSummary");

        if (orgFinanceLogRequest.getShow().equals("fullscreen")) {
            modelAndView = new ModelAndView("Finance/PerformanceSummaryFull");
        }

        if (orgFinanceLogRequest.getTypeTime() == null) {
            orgFinanceLogRequest.setTypeTime("month");
        }

        modelAndView.addObject("typeTime", orgFinanceLogRequest.getTypeTime());
        modelAndView.addObject("busType", orgFinanceLogRequest.getBusType());

        List<OrgFinanceEnums> orgFinanceChannelEnumsList = orgFinanceEnumsService.queryOrgFinanceEnumsList("GROUP_CHANNEL");
        modelAndView.addObject("BusinessChannelTypeEnumList", orgFinanceChannelEnumsList);
        List<OrgFinanceEnums> orgFinanceIncomeEnumsList = orgFinanceEnumsService.queryOrgFinanceEnumsList("GROUP_CINCOME");
        modelAndView.addObject("BusinessIncomeTypeEnumList", orgFinanceIncomeEnumsList);

        modelAndView.addObject("BusinessTypeEnumList", EnumUtils.getEnumList(BusinessTypeEnum.class));
        modelAndView.addObject("BusinessTypeEnum", EnumUtils.getEnumMap(BusinessTypeEnum.class));

        List<OrgFinanceVenues> orgFinanceVenuesList = orgFinanceVenuesService.queryOrgFinanceVenuesList();
        modelAndView.addObject("orgFinanceVenuesList", orgFinanceVenuesList);

        int count = orgFinanceUsersService.queryOrgFinanceUsersCount();
        List<OrgFinanceUsers> orgFinanceUsersList = orgFinanceUsersService.queryOrgFinanceUsersList(0, count);

        String typePeriod[] = getStartEndTime(orgFinanceLogRequest.getTypeTime());
        String startTime = typePeriod[0].substring(0, 10);
        String endTime = typePeriod[1].substring(0, 10);

        // 流水
        int flowTotal = orgFinanceDataFlowService.queryOrgFinanceDataFlowCount(orgFinanceLogRequest.getBusType(), 0, 0, startTime, endTime);
        List<OrgFinanceDataFlow> orgFinanceDataFlowList = orgFinanceDataFlowService.queryOrgFinanceDataFlowList(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), 0, startTime, endTime, 0, flowTotal);

        // 体验成交
        int businessTotal = orgFinanceDataBusinessService.queryOrgFinanceDataBusinessCount(orgFinanceLogRequest.getBusType(), 0, 0, startTime, endTime);
        List<OrgFinanceDataBusiness> orgFinanceDataBusinessList = orgFinanceDataBusinessService.queryOrgFinanceDataBusinessList(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), 0, startTime, endTime, 0, businessTotal);

        // 确认收入
        int incomeTotal = orgFinanceDataIncomeService.queryOrgFinanceDataIncomeCount(orgFinanceLogRequest.getBusType(), 0, 0, startTime, endTime);
        List<OrgFinanceDataIncome> orgFinanceDataIncomeList = orgFinanceDataIncomeService.queryOrgFinanceDataIncomeList(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), 0, startTime, endTime, 0, incomeTotal);

        // 签到
        int attendanceTotal = orgFinanceDataAttendanceService.queryOrgFinanceDataAttendanceCount(orgFinanceLogRequest.getBusType(), 0, 0, startTime, endTime);
        List<OrgFinanceDataAttendance> orgFinanceDataAttendanceList = orgFinanceDataAttendanceService.queryOrgFinanceDataAttendanceList(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), 0, startTime, endTime, 0, attendanceTotal);

        // 闲忙
        int timesTotal = orgFinanceDataTimesService.queryOrgFinanceDataTimesCount(orgFinanceLogRequest.getBusType(), 0, 0, startTime, endTime);
        List<OrgFinanceDataTimes> orgFinanceDataTimesList = orgFinanceDataTimesService.queryOrgFinanceDataTimesList(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), 0, startTime, endTime, 0, timesTotal);

        // 目标
        List<OrgFinanceGoals> orgFinanceGoalsList = orgFinanceGoalsService.queryOrgFinanceGoalsList(orgFinanceLogRequest.getBusType(), 0, 0, 0, 0);

        List<OrgFinanceDataResponse> orgFinanceDataResponseList = new ArrayList<>();
        for (OrgFinanceUsers orgFinanceUsers : orgFinanceUsersList) {
            OrgFinanceDataResponse orgFinanceDataResponse = new OrgFinanceDataResponse();

            orgFinanceDataResponse.setVenueId(orgFinanceUsers.getVenueId());
            orgFinanceDataResponse.setVenueName(getVenueName(orgFinanceUsers.getVenueId(), orgFinanceVenuesList));
            orgFinanceDataResponse.setUserId(orgFinanceUsers.getId());
            orgFinanceDataResponse.setRealName(orgFinanceUsers.getRealName());

            int pipelineData = calOrgFinanceDataFlow(orgFinanceDataFlowList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), null);
            orgFinanceDataResponse.setPipelineValue(pipelineData);

            int incomeData = calOrgFinanceDataIncome(orgFinanceDataIncomeList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), null);
            orgFinanceDataResponse.setIncomeValue(incomeData);

            int[] businessData = calOrgFinanceDataBusiness(orgFinanceDataBusinessList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), null);
            orgFinanceDataResponse.setAccessCount(businessData[0]);
            orgFinanceDataResponse.setBusinessCount(businessData[1]);

            int[] attendanceData = calOrgFinanceDataAttendance(orgFinanceDataAttendanceList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), null);
            orgFinanceDataResponse.setRegisterCount(attendanceData[0]);
            orgFinanceDataResponse.setClassCount(attendanceData[1]);

            int[] timesData = calOrgFinanceDataTimes(orgFinanceDataTimesList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId());
            orgFinanceDataResponse.setNullCount(timesData[0]);
            orgFinanceDataResponse.setNullTotalCount(timesData[1]);
            orgFinanceDataResponse.setHotCount(timesData[2]);
            orgFinanceDataResponse.setHotTotalCount(timesData[3]);

            if (pipelineData <= 0 && incomeData <= 0 &&
                    businessData[0] <= 0 && businessData[1] <= 0 &&
                    attendanceData[0] <= 0 && attendanceData[1] <= 0 &&
                    timesData[0] <= 0 && timesData[1] <= 0 && timesData[2] <= 0 && timesData[3] <= 0) {
                continue;
            }

            // 渠道
            List<OrgFinanceDataResponse> orgFinanceDataResponseList1 = new ArrayList<>();

            for (OrgFinanceEnums orgFinanceEnums : orgFinanceChannelEnumsList) {
                OrgFinanceDataResponse orgFinanceDataChannelResponse = new OrgFinanceDataResponse();

                orgFinanceDataChannelResponse.setChannelName(orgFinanceEnums.getEnumNote());

                int pipelineChannelData = calOrgFinanceDataFlow(orgFinanceDataFlowList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), orgFinanceEnums.getEnumNote());
                orgFinanceDataChannelResponse.setPipelineValue(pipelineChannelData);

                int[] businessChannelData = calOrgFinanceDataBusiness(orgFinanceDataBusinessList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), orgFinanceEnums.getEnumNote());
                orgFinanceDataChannelResponse.setAccessCount(businessChannelData[0]);
                orgFinanceDataChannelResponse.setBusinessCount(businessChannelData[1]);

                if (pipelineChannelData <= 0 && businessChannelData[0] <= 0 && businessChannelData[1] <= 0) {
                    continue;
                }

                orgFinanceDataResponseList1.add(orgFinanceDataChannelResponse);
            }

            OrgFinanceDataResponse orgFinanceDataChannelResponse1 = new OrgFinanceDataResponse();

            orgFinanceDataChannelResponse1.setChannelName("无");

            int pipelineChannelData = calOrgFinanceDataFlow(orgFinanceDataFlowList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), "无");
            orgFinanceDataChannelResponse1.setPipelineValue(pipelineChannelData);

            int[] businessChannelData = calOrgFinanceDataBusiness(orgFinanceDataBusinessList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), "无");
            orgFinanceDataChannelResponse1.setAccessCount(businessChannelData[0]);
            orgFinanceDataChannelResponse1.setBusinessCount(businessChannelData[1]);

            if (pipelineChannelData > 0 || businessChannelData[0] > 0 || businessChannelData[1] > 0) {
                orgFinanceDataResponseList1.add(orgFinanceDataChannelResponse1);
            }

            // 收入
            for (OrgFinanceEnums orgFinanceEnums : orgFinanceIncomeEnumsList) {
                OrgFinanceDataResponse orgFinanceDataIncomeResponse = new OrgFinanceDataResponse();

                orgFinanceDataIncomeResponse.setIncomeType(orgFinanceEnums.getEnumNote());

                int incomeChannelData = calOrgFinanceDataIncome(orgFinanceDataIncomeList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), orgFinanceEnums.getEnumNote());
                orgFinanceDataIncomeResponse.setIncomeValue(incomeChannelData);

                int[] attendanceChannelData = calOrgFinanceDataAttendance(orgFinanceDataAttendanceList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), orgFinanceEnums.getEnumNote());
                orgFinanceDataIncomeResponse.setRegisterCount(attendanceChannelData[0]);
                orgFinanceDataIncomeResponse.setClassCount(attendanceChannelData[1]);

                if (incomeChannelData <= 0 && attendanceChannelData[0] <= 0 && attendanceChannelData[1] <= 0) {
                    continue;
                }

                orgFinanceDataResponseList1.add(orgFinanceDataIncomeResponse);
            }

            OrgFinanceDataResponse orgFinanceDataIncomeResponse1 = new OrgFinanceDataResponse();

            orgFinanceDataIncomeResponse1.setIncomeType("无");

            int incomeChannelData = calOrgFinanceDataIncome(orgFinanceDataIncomeList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), "无");
            orgFinanceDataIncomeResponse1.setIncomeValue(incomeChannelData);

            int[] attendanceChannelData = calOrgFinanceDataAttendance(orgFinanceDataAttendanceList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), "无");
            orgFinanceDataIncomeResponse1.setRegisterCount(attendanceChannelData[0]);
            orgFinanceDataIncomeResponse1.setClassCount(attendanceChannelData[1]);

            if (incomeChannelData > 0 || attendanceChannelData[0] > 0 || attendanceChannelData[1] > 0) {
                orgFinanceDataResponseList1.add(orgFinanceDataIncomeResponse1);
            }

            orgFinanceDataResponse.setOrgFinanceDataResponseChannelList(orgFinanceDataResponseList1);

            // 目标
            Integer[] financeGoals = getOrgFinanceGoalsForDate(orgFinanceGoalsList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), startTime, typePeriod[2]);
            if (financeGoals != null) {
                orgFinanceDataResponse.setPipelineTarget(financeGoals[0]);
                orgFinanceDataResponse.setPipelineChallenge(financeGoals[1]);
                orgFinanceDataResponse.setIncomeTarget(financeGoals[2]);
                orgFinanceDataResponse.setIncomeChallenge(financeGoals[3]);
            }

            orgFinanceDataResponseList.add(orgFinanceDataResponse);
        }

        Collections.sort(orgFinanceDataResponseList, new Comparator<OrgFinanceDataResponse> () {
            public int compare(OrgFinanceDataResponse arg0, OrgFinanceDataResponse arg1) {
                return arg0.getVenueId().compareTo(arg1.getVenueId());
            }
        });

        modelAndView.addObject("orgFinanceDataResponseList", orgFinanceDataResponseList);

        return setModelAndView(modelAndView);
    }

    // 计算流水
    private int calOrgFinanceDataFlow(List<OrgFinanceDataFlow> orgFinanceDataFlowList, int venueId, int userId, String channelName) {
        int total = 0;
        boolean isTotal = false;

        for (OrgFinanceDataFlow orgFinanceDataFlow : orgFinanceDataFlowList) {
            if (venueId > 0 && orgFinanceDataFlow.getVenueId() == venueId) {
                if (userId > 0 && orgFinanceDataFlow.getUserId() == userId) {
                    if (channelName != null && orgFinanceDataFlow.getChannelType().equals(channelName)) {
                        // 统计场馆/人/渠道
                        isTotal = true;
                    }
                    else if (channelName == null) {
                        // 统计场馆/人
                        isTotal = true;
                    }
                }
                else if (userId == 0) {
                    if (channelName != null && orgFinanceDataFlow.getChannelType().equals(channelName)) {
                        // 统计场馆/渠道
                        isTotal = true;
                    }
                    else if (channelName == null) {
                        // 统计场馆
                        isTotal = true;
                    }
                }
            }
            else if (venueId == 0) {
                isTotal = true;
            }

            if (isTotal) {
                total += orgFinanceDataFlow.getPipelineValue();
                isTotal = false;
            }
        }

        return total;
    }

    // 计算确收
    private int calOrgFinanceDataIncome(List<OrgFinanceDataIncome> orgFinanceDataIncomeList, int venueId, int userId, String incomeType) {
        int total = 0;
        boolean isTotal = false;

        for (OrgFinanceDataIncome orgFinanceDataIncome : orgFinanceDataIncomeList) {
            if (venueId > 0 && orgFinanceDataIncome.getVenueId() == venueId) {
                if (userId > 0 && orgFinanceDataIncome.getUserId() == userId) {
                    if (incomeType != null && orgFinanceDataIncome.getIncomeType().equals(incomeType)) {
                        // 统计场馆/人/渠道
                        isTotal = true;
                    }
                    else if (incomeType == null) {
                        // 统计场馆/人
                        isTotal = true;
                    }
                }
                else if (userId == 0) {
                    if (incomeType != null && orgFinanceDataIncome.getIncomeType().equals(incomeType)) {
                        // 统计场馆/渠道
                        isTotal = true;
                    }
                    else if (incomeType == null) {
                        // 统计场馆
                        isTotal = true;
                    }
                }
            }
            else if (venueId == 0) {
                isTotal = true;
            }

            if (isTotal) {
                total += orgFinanceDataIncome.getIncomeValue();
                isTotal = false;
            }
        }

        return total;
    }

    // 计算体验成交
    private int[] calOrgFinanceDataBusiness(List<OrgFinanceDataBusiness> orgFinanceDataBusinessList, int venueId, int userId, String channelName) {
        int totalAccessCount = 0, totalBusinessCount = 0;
        boolean isTotal = false;

        for (OrgFinanceDataBusiness orgFinanceDataBusiness : orgFinanceDataBusinessList) {
            if (venueId > 0 && orgFinanceDataBusiness.getVenueId() == venueId) {
                if (userId > 0 && orgFinanceDataBusiness.getUserId() == userId) {
                    if (channelName != null && orgFinanceDataBusiness.getChannelType().equals(channelName)) {
                        // 统计场馆/人/渠道
                        isTotal = true;
                    }
                    else if (channelName == null) {
                        // 统计场馆/人
                        isTotal = true;
                    }
                }
                else if (userId == 0) {
                    if (channelName != null && orgFinanceDataBusiness.getChannelType().equals(channelName)) {
                        // 统计场馆/渠道
                        isTotal = true;
                    }
                    else if (channelName == null) {
                        // 统计场馆
                        isTotal = true;
                    }
                }
            }
            else if (venueId == 0) {
                isTotal = true;
            }

            if (isTotal) {
                totalAccessCount += orgFinanceDataBusiness.getAccessCount();
                totalBusinessCount += orgFinanceDataBusiness.getBusinessCount();
                isTotal = false;
            }
        }

        return new int[]{totalAccessCount, totalBusinessCount};
    }

    // 计算在册签到
    private int[] calOrgFinanceDataAttendance(List<OrgFinanceDataAttendance> orgFinanceDataAttendanceList, int venueId, int userId, String incomeType) {
        int totalRegisterCount = 0, totalClassCount = 0;
        boolean isTotal = false;

        for (OrgFinanceDataAttendance orgFinanceDataAttendance : orgFinanceDataAttendanceList) {
            if (venueId > 0 && orgFinanceDataAttendance.getVenueId() == venueId) {
                if (userId > 0 && orgFinanceDataAttendance.getUserId() == userId) {
                    if (incomeType != null && orgFinanceDataAttendance.getIncomeType().equals(incomeType)) {
                        // 统计场馆/人/渠道
                        isTotal = true;
                    }
                    else if (incomeType == null) {
                        // 统计场馆/人
                        isTotal = true;
                    }
                }
                else if (userId == 0) {
                    if (incomeType != null && orgFinanceDataAttendance.getIncomeType().equals(incomeType)) {
                        // 统计场馆/渠道
                        isTotal = true;
                    }
                    else if (incomeType == null) {
                        // 统计场馆
                        isTotal = true;
                    }
                }
            }
            else if (venueId == 0) {
                isTotal = true;
            }

            if (isTotal) {
                totalRegisterCount += orgFinanceDataAttendance.getRegisterCount();
                totalClassCount += orgFinanceDataAttendance.getClassCount();
                isTotal = false;
            }
        }

        return new int[]{totalRegisterCount, totalClassCount};
    }

    // 计算闲忙时段
    private int[] calOrgFinanceDataTimes(List<OrgFinanceDataTimes> orgOrgFinanceDataTimesList, int venueId, int userId) {
        int totalNullCount = 0, totalNullTotalCount = 0, totalHotCount = 0, totalHotTotalCount = 0;
        boolean isTotal = false;

        for (OrgFinanceDataTimes orgFinanceDataTimes : orgOrgFinanceDataTimesList) {
            if (venueId > 0 && orgFinanceDataTimes.getVenueId() == venueId) {
                if (userId > 0 && orgFinanceDataTimes.getUserId() == userId) {
                    isTotal = true;
                }
                else if (userId == 0) {
                    isTotal = true;
                }
            }
            else if (venueId == 0) {
                isTotal = true;
            }

            if (isTotal) {
                totalNullCount += orgFinanceDataTimes.getNullCount();
                totalNullTotalCount += orgFinanceDataTimes.getNullTotalCount();
                totalHotCount += orgFinanceDataTimes.getHotCount();
                totalHotTotalCount += orgFinanceDataTimes.getHotTotalCount();
                isTotal = false;
            }
        }

        return new int[]{totalNullCount, totalNullTotalCount, totalHotCount, totalHotTotalCount};
    }

    /**
     * 导出运营报表
     * @return
     */
    @RequestMapping(value = "/performanceSummary/export")
    @ResponseBody
    public void exportPerformanceSummary(OrgFinanceLogRequest orgFinanceLogRequest, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] sheetTitle = {
                "基地名字", "姓名",
                "流水完成", "流水目标", "流水挑战目标",
                "确认收入完成", "确认收入目标", "确认收入挑战目标",
                "在册人数", "到课人数",
                "个人渠道体验数", "个人渠道成交数", "个人渠道转化率",
                "转介绍渠道体验数", "转介绍渠道成交数", "转介绍渠道转化率",
                "非公司渠转化率",
                "公司渠道体验数", "公司渠道成交数", "公司渠道转化率",
                "整体转化率",
                "闲时段数", "闲时段总数",
                "忙时段数", "忙时段总数"};

        String sheetName = "财务数据";

        if (orgFinanceLogRequest.getTypeTime() == null) {
            orgFinanceLogRequest.setTypeTime("month");
        }

        List<OrgFinanceVenues> orgFinanceVenuesList = orgFinanceVenuesService.queryOrgFinanceVenuesList();

        int count = orgFinanceUsersService.queryOrgFinanceUsersCount();
        List<OrgFinanceUsers> orgFinanceUsersList = orgFinanceUsersService.queryOrgFinanceUsersList(0, count);

        String typePeriod[] = getStartEndTime(orgFinanceLogRequest.getTypeTime());
        String startTime = typePeriod[0].substring(0, 10);
        String endTime = typePeriod[1].substring(0, 10);

        List<OrgFinanceEnums> orgFinanceChannelEnumsList = orgFinanceEnumsService.queryOrgFinanceEnumsList("GROUP_CHANNEL");

        List<OrgFinanceEnums> orgFinanceIncomeEnumsList = orgFinanceEnumsService.queryOrgFinanceEnumsList("GROUP_CINCOME");

        // 流水
        int flowTotal = orgFinanceDataFlowService.queryOrgFinanceDataFlowCount(0, 0, 0, startTime, endTime);
        List<OrgFinanceDataFlow> orgFinanceDataFlowList = orgFinanceDataFlowService.queryOrgFinanceDataFlowList(0, 0, 0, startTime, endTime, 0, flowTotal);

        // 体验成交
        int businessTotal = orgFinanceDataBusinessService.queryOrgFinanceDataBusinessCount(0, 0, 0, startTime, endTime);
        List<OrgFinanceDataBusiness> orgFinanceDataBusinessList = orgFinanceDataBusinessService.queryOrgFinanceDataBusinessList(0, 0, 0, startTime, endTime, 0, businessTotal);

        // 确认收入
        int incomeTotal = orgFinanceDataIncomeService.queryOrgFinanceDataIncomeCount(0, 0, 0, startTime, endTime);
        List<OrgFinanceDataIncome> orgFinanceDataIncomeList = orgFinanceDataIncomeService.queryOrgFinanceDataIncomeList(0, 0, 0, startTime, endTime, 0, incomeTotal);

        // 签到
        int attendanceTotal = orgFinanceDataAttendanceService.queryOrgFinanceDataAttendanceCount(0, 0, 0, startTime, endTime);
        List<OrgFinanceDataAttendance> orgFinanceDataAttendanceList = orgFinanceDataAttendanceService.queryOrgFinanceDataAttendanceList(0, 0, 0, startTime, endTime, 0, attendanceTotal);

        // 闲忙
        int timesTotal = orgFinanceDataTimesService.queryOrgFinanceDataTimesCount(orgFinanceLogRequest.getBusType(), 0, 0, startTime, endTime);
        List<OrgFinanceDataTimes> orgFinanceDataTimesList = orgFinanceDataTimesService.queryOrgFinanceDataTimesList(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), 0, startTime, endTime, 0, timesTotal);

        // 目标
        List<OrgFinanceGoals> orgFinanceGoalsList = orgFinanceGoalsService.queryOrgFinanceGoalsList(0, 0, 0, 0, 0);

        List<OrgFinanceDataResponse> orgFinanceDataResponseList = new ArrayList<>();
        for (OrgFinanceUsers orgFinanceUsers : orgFinanceUsersList) {
            OrgFinanceDataResponse orgFinanceDataResponse = new OrgFinanceDataResponse();

            orgFinanceDataResponse.setVenueId(orgFinanceUsers.getVenueId());
            orgFinanceDataResponse.setVenueName(getVenueName(orgFinanceUsers.getVenueId(), orgFinanceVenuesList));
            orgFinanceDataResponse.setRealName(orgFinanceUsers.getRealName());

            int pipelineData = calOrgFinanceDataFlow(orgFinanceDataFlowList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), null);
            orgFinanceDataResponse.setPipelineValue(pipelineData);

            int incomeData = calOrgFinanceDataIncome(orgFinanceDataIncomeList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), null);
            orgFinanceDataResponse.setIncomeValue(incomeData);

            int[] businessData = calOrgFinanceDataBusiness(orgFinanceDataBusinessList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), null);
            orgFinanceDataResponse.setAccessCount(businessData[0]);
            orgFinanceDataResponse.setBusinessCount(businessData[1]);

            int[] attendanceData = calOrgFinanceDataAttendance(orgFinanceDataAttendanceList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), null);
            orgFinanceDataResponse.setRegisterCount(attendanceData[0]);
            orgFinanceDataResponse.setClassCount(attendanceData[1]);

            int[] timesData = calOrgFinanceDataTimes(orgFinanceDataTimesList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId());
            orgFinanceDataResponse.setNullCount(timesData[0]);
            orgFinanceDataResponse.setNullTotalCount(timesData[1]);
            orgFinanceDataResponse.setHotCount(timesData[2]);
            orgFinanceDataResponse.setHotTotalCount(timesData[3]);

            if (pipelineData <= 0 && incomeData <= 0 &&
                    businessData[0] <= 0 && businessData[1] <= 0 &&
                    attendanceData[0] <= 0 && attendanceData[1] <= 0 &&
                    timesData[0] <= 0 && timesData[1] <= 0 && timesData[2] <= 0 && timesData[3] <= 0) {
                continue;
            }

            // 渠道
            List<OrgFinanceDataResponse> orgFinanceDataResponseList1 = new ArrayList<>();

            for (OrgFinanceEnums orgFinanceEnums : orgFinanceChannelEnumsList) {
                OrgFinanceDataResponse orgFinanceDataChannelResponse = new OrgFinanceDataResponse();

                orgFinanceDataChannelResponse.setChannelName(orgFinanceEnums.getEnumNote());

                int[] businessChannelData = calOrgFinanceDataBusiness(orgFinanceDataBusinessList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), orgFinanceEnums.getEnumNote());
                orgFinanceDataChannelResponse.setAccessCount(businessChannelData[0]);
                orgFinanceDataChannelResponse.setBusinessCount(businessChannelData[1]);

                if (businessChannelData[0] <= 0 && businessChannelData[1] <= 0) {
                    continue;
                }

                orgFinanceDataResponseList1.add(orgFinanceDataChannelResponse);
            }

            OrgFinanceDataResponse orgFinanceDataChannelResponse1 = new OrgFinanceDataResponse();

            orgFinanceDataChannelResponse1.setChannelName("无");

            int[] businessChannelData = calOrgFinanceDataBusiness(orgFinanceDataBusinessList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), "无");
            orgFinanceDataChannelResponse1.setAccessCount(businessChannelData[0]);
            orgFinanceDataChannelResponse1.setBusinessCount(businessChannelData[1]);

            if (businessChannelData[0] > 0 || businessChannelData[1] > 0) {
                orgFinanceDataResponseList1.add(orgFinanceDataChannelResponse1);
            }

            orgFinanceDataResponse.setOrgFinanceDataResponseChannelList(orgFinanceDataResponseList1);

            // 目标
            Integer[] financeGoals = getOrgFinanceGoalsForDate(orgFinanceGoalsList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), startTime, typePeriod[2]);
            if (financeGoals != null) {
                orgFinanceDataResponse.setPipelineTarget(financeGoals[0]);
                orgFinanceDataResponse.setPipelineChallenge(financeGoals[1]);
                orgFinanceDataResponse.setIncomeTarget(financeGoals[2]);
                orgFinanceDataResponse.setIncomeChallenge(financeGoals[3]);
            }

            orgFinanceDataResponseList.add(orgFinanceDataResponse);
        }

        Collections.sort(orgFinanceDataResponseList, new Comparator<OrgFinanceDataResponse> () {
            public int compare(OrgFinanceDataResponse arg0, OrgFinanceDataResponse arg1) {
                return arg0.getVenueId().compareTo(arg1.getVenueId());
            }
        });

        int j = 0;
        String [][]trainingContent = new String[orgFinanceDataResponseList.size()][sheetTitle.length];
        for (OrgFinanceDataResponse orgFinanceDataResponse : orgFinanceDataResponseList) {
            trainingContent[j][0] = orgFinanceDataResponse.getVenueName();
            trainingContent[j][1] = orgFinanceDataResponse.getRealName();
            trainingContent[j][2] = orgFinanceDataResponse.getPipelineValue().toString();
            trainingContent[j][3] = orgFinanceDataResponse.getPipelineTarget().toString() + "，" + StrUtil.roundKeepTwo(orgFinanceDataResponse.getPipelineTargetPercent() * 100) + "%";
            trainingContent[j][4] = orgFinanceDataResponse.getPipelineChallenge().toString() + "，" + StrUtil.roundKeepTwo(orgFinanceDataResponse.getPipelineChallengePercent() * 100) + "%";
            trainingContent[j][5] = orgFinanceDataResponse.getIncomeValue().toString();
            trainingContent[j][6] = orgFinanceDataResponse.getIncomeTarget().toString() + "，" + StrUtil.roundKeepTwo(orgFinanceDataResponse.getIncomeTargetPercent() * 100) + "%";
            trainingContent[j][7] = orgFinanceDataResponse.getIncomeChallenge().toString() + "，" + StrUtil.roundKeepTwo(orgFinanceDataResponse.getIncomeChallengePercent() * 100) + "%";
            trainingContent[j][8] = orgFinanceDataResponse.getRegisterCount().toString();
            trainingContent[j][9] = orgFinanceDataResponse.getClassCount().toString();

            Integer accessCountPerson = 0, businessCountPerson = 0;
            trainingContent[j][10] = "0";
            trainingContent[j][11] = "0";
            trainingContent[j][12] = "0.00%";

            Integer accessCountFriend = 0, businessCountFriend = 0;
            trainingContent[j][13] = "0";
            trainingContent[j][14] = "0";
            trainingContent[j][15] = "0.00%";

            Integer accessCountMember = 0, businessCountMember = 0;

            trainingContent[j][17] = "0";
            trainingContent[j][18] = "0";
            trainingContent[j][19] = "0.00%";
            for (OrgFinanceDataResponse orgFinanceDataResponse1 : orgFinanceDataResponse.getOrgFinanceDataResponseChannelList()) {
                if (orgFinanceDataResponse1.getChannelName().equals("个人")) {
                    accessCountPerson = orgFinanceDataResponse1.getAccessCount();
                    businessCountPerson = orgFinanceDataResponse1.getBusinessCount();

                    trainingContent[j][10] = orgFinanceDataResponse1.getAccessCount() + "";
                    trainingContent[j][11] = orgFinanceDataResponse1.getBusinessCount() + "";
                    trainingContent[j][12] = StrUtil.roundKeepTwo(orgFinanceDataResponse1.getBusinessCountPercent() * 100) + "%";
                }
                if (orgFinanceDataResponse1.getChannelName().equals("转介绍")) {
                    accessCountFriend = orgFinanceDataResponse1.getAccessCount();
                    businessCountFriend = orgFinanceDataResponse1.getBusinessCount();

                    trainingContent[j][13] = orgFinanceDataResponse1.getAccessCount() + "";
                    trainingContent[j][14] = orgFinanceDataResponse1.getBusinessCount() + "";
                    trainingContent[j][15] = StrUtil.roundKeepTwo(orgFinanceDataResponse1.getBusinessCountPercent() * 100) + "%";
                }
                if (orgFinanceDataResponse1.getChannelName().equals("续费")) {
                    accessCountMember = orgFinanceDataResponse1.getAccessCount();
                    businessCountMember = orgFinanceDataResponse1.getBusinessCount();
                }
                if (orgFinanceDataResponse1.getChannelName().equals("公司")) {
                    trainingContent[j][17] = orgFinanceDataResponse1.getAccessCount() + "";
                    trainingContent[j][18] = orgFinanceDataResponse1.getBusinessCount() + "";
                    trainingContent[j][19] = StrUtil.roundKeepTwo(orgFinanceDataResponse1.getBusinessCountPercent() * 100) + "%";
                }
            }

            trainingContent[j][16] = "0.00%";
            if ((accessCountFriend + accessCountMember + accessCountPerson) > 0) {
                trainingContent[j][16] = StrUtil.roundKeepTwo(((double)(businessCountPerson + businessCountFriend + businessCountMember) / (accessCountFriend + accessCountMember + accessCountPerson)) * 100) + "%";
            }

            trainingContent[j][20] = StrUtil.roundKeepTwo(orgFinanceDataResponse.getBusinessCountPercent() * 100) + "%";

            trainingContent[j][21] = orgFinanceDataResponse.getNullCount().toString();
            trainingContent[j][22] = orgFinanceDataResponse.getNullTotalCount().toString() + "，" + StrUtil.roundKeepTwo(orgFinanceDataResponse.getNullCountPercent() * 100) + "%";
            trainingContent[j][23] = orgFinanceDataResponse.getHotCount().toString();
            trainingContent[j][24] = orgFinanceDataResponse.getHotTotalCount().toString() + "，" + StrUtil.roundKeepTwo(orgFinanceDataResponse.getHotCountPercent() * 100) + "%";

            j++;
        }

        HSSFWorkbook trainingBook = ExcelUtil.getHSSFWorkbook(sheetName, sheetTitle, trainingContent, null);

        // 文件名
        String fileName = "运营财务报表_" + typePeriod[2] + "_" + System.currentTimeMillis() + ".xls";

        //响应到客户端
        try {
            this.setResponseHeader(response, fileName);

            OutputStream os = response.getOutputStream();

            trainingBook.write(os);

            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*** 图表 start ***/

    @Desc("个人业绩统计")
    @RequestMapping(value = "/performance", method = RequestMethod.GET)
    public ModelAndView renderFinancePerformance(OrgFinanceLogRequest orgFinanceLogRequest) {

        if (orgFinanceLogRequest.getTypeTime() == null) {
            orgFinanceLogRequest.setTypeTime("month");
        }

        ModelAndView modelAndView;
        if (orgFinanceLogRequest.getVenueId() == -1) {
            modelAndView = new ModelAndView("Finance/PerformanceVenues");
        }
        else {
            modelAndView = new ModelAndView("Finance/PerformanceUsers");
        }

        modelAndView.addObject("conditions", orgFinanceLogRequest);

        List<OrgFinanceVenues> orgFinanceVenuesList = orgFinanceVenuesService.queryOrgFinanceVenuesList();
        modelAndView.addObject("orgFinanceVenuesList", orgFinanceVenuesList);

        modelAndView.addObject("BusinessTypeEnumList", EnumUtils.getEnumList(BusinessTypeEnum.class));
        modelAndView.addObject("BusinessTypeEnum", EnumUtils.getEnumMap(BusinessTypeEnum.class));

        return setModelAndView(modelAndView);
    }

    @Desc("场馆业绩渠道相关")
    @ResponseBody
    @RequestMapping(value = "/getFinancePerformanceChannelForVenues", method = RequestMethod.GET)
    public ResponseBean getFinancePerformanceChannelForVenues(OrgFinanceLogRequest orgFinanceLogRequest) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();

            if (orgFinanceLogRequest.getTypeTime() == null) {
                orgFinanceLogRequest.setTypeTime("month");
            }

            List<OrgFinanceVenues> orgFinanceVenuesList = orgFinanceVenuesService.queryOrgFinanceVenuesList();
            List<OrgFinanceEnums> orgFinanceChannelEnumsList = orgFinanceEnumsService.queryOrgFinanceEnumsList("GROUP_CHANNEL");

            String typePeriod[] = getStartEndTime(orgFinanceLogRequest.getTypeTime());
            String startTime = typePeriod[0].substring(0, 10);
            String endTime = typePeriod[1].substring(0, 10);

            // 流水
            int flowTotal = orgFinanceDataFlowService.queryOrgFinanceDataFlowCount(orgFinanceLogRequest.getBusType(), 0, 0, startTime, endTime);
            List<OrgFinanceDataFlow> orgFinanceDataFlowList = orgFinanceDataFlowService.queryOrgFinanceDataFlowList(orgFinanceLogRequest.getBusType(), 0, 0, startTime, endTime, 0, flowTotal);
            for (OrgFinanceDataFlow orgFinanceDataFlow : orgFinanceDataFlowList) {
                orgFinanceDataFlow.setUserId(-1);
            }

            // 体验成交
            int businessTotal = orgFinanceDataBusinessService.queryOrgFinanceDataBusinessCount(orgFinanceLogRequest.getBusType(), 0, 0, startTime, endTime);
            List<OrgFinanceDataBusiness> orgFinanceDataBusinessList = orgFinanceDataBusinessService.queryOrgFinanceDataBusinessList(orgFinanceLogRequest.getBusType(), 0, 0, startTime, endTime, 0, businessTotal);
            for (OrgFinanceDataBusiness orgFinanceDataBusiness : orgFinanceDataBusinessList) {
                orgFinanceDataBusiness.setUserId(-1);
            }

            // 目标
            List<OrgFinanceGoals> orgFinanceGoalsList = orgFinanceGoalsService.queryOrgFinanceGoalsList(orgFinanceLogRequest.getBusType(), 0, 0, 0, 0);

            List<OrgFinanceDataChannelResponse> orgFinanceDataVenueList = new ArrayList<>();
            for (OrgFinanceVenues orgFinanceVenues : orgFinanceVenuesList) {
                OrgFinanceDataChannelResponse orgFinanceDataResponse = new OrgFinanceDataChannelResponse();

                orgFinanceDataResponse.setVenueId(orgFinanceVenues.getId());
                orgFinanceDataResponse.setVenueName(orgFinanceVenues.getVenueName());

                int pipelineData = calOrgFinanceDataFlow(orgFinanceDataFlowList, orgFinanceVenues.getId(), 0, null);
                orgFinanceDataResponse.setPipelineValue(pipelineData);

                int[] businessData = calOrgFinanceDataBusiness(orgFinanceDataBusinessList, orgFinanceVenues.getId(), 0, null);
                orgFinanceDataResponse.setAccessCount(businessData[0]);
                orgFinanceDataResponse.setBusinessCount(businessData[1]);

                if (pipelineData <= 0 && businessData[0] <= 0) {
                    continue;
                }

                Integer[] financeGoals = getOrgFinanceGoalsForDate(orgFinanceGoalsList, orgFinanceVenues.getId(), 0, startTime, typePeriod[2]);
                if (financeGoals != null) {
                    orgFinanceDataResponse.setPipelineTarget(financeGoals[0]);
                    orgFinanceDataResponse.setPipelineChallenge(financeGoals[1]);
                }

                orgFinanceDataVenueList.add(orgFinanceDataResponse);
            }

            List<OrgFinanceDataChannelResponse> orgFinanceDataChannelList = new ArrayList<>();
            for (OrgFinanceVenues orgFinanceVenues : orgFinanceVenuesList) {
                for (OrgFinanceEnums orgFinanceEnums : orgFinanceChannelEnumsList) {
                    OrgFinanceDataChannelResponse orgFinanceDataChannelResponse = new OrgFinanceDataChannelResponse();

                    orgFinanceDataChannelResponse.setVenueId(orgFinanceVenues.getId());
                    orgFinanceDataChannelResponse.setVenueName(orgFinanceVenues.getVenueName());

                    orgFinanceDataChannelResponse.setChannelName(orgFinanceEnums.getEnumNote());

                    int pipelineData = calOrgFinanceDataFlow(orgFinanceDataFlowList, orgFinanceVenues.getId(), 0, orgFinanceEnums.getEnumNote());
                    orgFinanceDataChannelResponse.setPipelineValue(pipelineData);

                    int[] businessChannelData = calOrgFinanceDataBusiness(orgFinanceDataBusinessList, orgFinanceVenues.getId(), 0, orgFinanceEnums.getEnumNote());
                    orgFinanceDataChannelResponse.setAccessCount(businessChannelData[0]);
                    orgFinanceDataChannelResponse.setBusinessCount(businessChannelData[1]);

                    if (pipelineData <= 0 && businessChannelData[0] <= 0) {
                        continue;
                    }

                    orgFinanceDataChannelList.add(orgFinanceDataChannelResponse);
                }

                OrgFinanceDataChannelResponse orgFinanceDataChannelResponse1 = new OrgFinanceDataChannelResponse();

                orgFinanceDataChannelResponse1.setVenueId(orgFinanceVenues.getId());
                orgFinanceDataChannelResponse1.setVenueName(orgFinanceVenues.getVenueName());

                orgFinanceDataChannelResponse1.setChannelName("无");

                int pipelineData = calOrgFinanceDataFlow(orgFinanceDataFlowList, orgFinanceVenues.getId(), 0, "无");
                orgFinanceDataChannelResponse1.setPipelineValue(pipelineData);

                int[] businessChannelData = calOrgFinanceDataBusiness(orgFinanceDataBusinessList, orgFinanceVenues.getId(), 0, "无");
                orgFinanceDataChannelResponse1.setAccessCount(businessChannelData[0]);
                orgFinanceDataChannelResponse1.setBusinessCount(businessChannelData[1]);

                if (pipelineData > 0 || businessChannelData[0] > 0 || businessChannelData[1] > 0) {
                    orgFinanceDataChannelList.add(orgFinanceDataChannelResponse1);
                }
            }

            map.put("orgFinanceVenuesList", orgFinanceVenuesList);
            map.put("orgFinanceChannelList", orgFinanceChannelEnumsList);
            map.put("orgFinanceDataVenueList", orgFinanceDataVenueList);
            map.put("orgFinanceDataChannelList", orgFinanceDataChannelList);

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("场馆业绩与收入有关")
    @ResponseBody
    @RequestMapping(value = "/getFinancePerformanceIncomeForVenues", method = RequestMethod.GET)
    public ResponseBean getFinancePerformanceIncomeForVenues(OrgFinanceLogRequest orgFinanceLogRequest) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();

            if (orgFinanceLogRequest.getTypeTime() == null) {
                orgFinanceLogRequest.setTypeTime("month");
            }

            List<OrgFinanceVenues> orgFinanceVenuesList = orgFinanceVenuesService.queryOrgFinanceVenuesList();
            List<OrgFinanceEnums> orgFinanceIncomeEnumsList = orgFinanceEnumsService.queryOrgFinanceEnumsList("GROUP_CINCOME");

            String typePeriod[] = getStartEndTime(orgFinanceLogRequest.getTypeTime());
            String startTime = typePeriod[0].substring(0, 10);
            String endTime = typePeriod[1].substring(0, 10);

            // 确认收入
            int incomeTotal = orgFinanceDataIncomeService.queryOrgFinanceDataIncomeCount(orgFinanceLogRequest.getBusType(), 0, 0, startTime, endTime);
            List<OrgFinanceDataIncome> orgFinanceDataIncomeList = orgFinanceDataIncomeService.queryOrgFinanceDataIncomeList(orgFinanceLogRequest.getBusType(), 0, 0, startTime, endTime, 0, incomeTotal);
            for (OrgFinanceDataIncome orgFinanceDataIncome : orgFinanceDataIncomeList) {
                orgFinanceDataIncome.setUserId(-1);
            }

            // 签到
            int attendanceTotal = orgFinanceDataAttendanceService.queryOrgFinanceDataAttendanceCount(orgFinanceLogRequest.getBusType(), 0, 0, startTime, endTime);
            List<OrgFinanceDataAttendance> orgFinanceDataAttendanceList = orgFinanceDataAttendanceService.queryOrgFinanceDataAttendanceList(orgFinanceLogRequest.getBusType(), 0, 0, startTime, endTime, 0, attendanceTotal);
            for (OrgFinanceDataAttendance orgFinanceDataAttendance : orgFinanceDataAttendanceList) {
                orgFinanceDataAttendance.setUserId(-1);
            }

            // 目标
            List<OrgFinanceGoals> orgFinanceGoalsList = orgFinanceGoalsService.queryOrgFinanceGoalsList(orgFinanceLogRequest.getBusType(), 0, 0, 0, 0);

            List<OrgFinanceDataIncomeResponse> orgFinanceDataVenueList = new ArrayList<>();
            for (OrgFinanceVenues orgFinanceVenues : orgFinanceVenuesList) {
                OrgFinanceDataIncomeResponse orgFinanceDataResponse = new OrgFinanceDataIncomeResponse();

                orgFinanceDataResponse.setVenueId(orgFinanceVenues.getId());
                orgFinanceDataResponse.setVenueName(orgFinanceVenues.getVenueName());

                int incomeData = calOrgFinanceDataIncome(orgFinanceDataIncomeList, orgFinanceVenues.getId(), 0, null);
                orgFinanceDataResponse.setIncomeValue(incomeData);

                int[] attendanceData = calOrgFinanceDataAttendance(orgFinanceDataAttendanceList, orgFinanceVenues.getId(), 0, null);
                orgFinanceDataResponse.setRegisterCount(attendanceData[0]);
                orgFinanceDataResponse.setClassCount(attendanceData[1]);

                if (incomeData <= 0 && attendanceData[0] <= 0 && attendanceData[1] <= 0) {
                    continue;
                }

                Integer[] financeGoals = getOrgFinanceGoalsForDate(orgFinanceGoalsList, orgFinanceVenues.getId(), 0, startTime, typePeriod[2]);
                if (financeGoals != null) {
                    orgFinanceDataResponse.setIncomeTarget(financeGoals[2]);
                    orgFinanceDataResponse.setIncomeChallenge(financeGoals[3]);
                }

                orgFinanceDataVenueList.add(orgFinanceDataResponse);
            }

            List<OrgFinanceDataIncomeResponse> orgFinanceDataIncomeTypeList = new ArrayList<>();
            for (OrgFinanceVenues orgFinanceVenues : orgFinanceVenuesList) {
                for (OrgFinanceEnums orgFinanceEnums : orgFinanceIncomeEnumsList) {
                    OrgFinanceDataIncomeResponse orgFinanceDataChannelResponse = new OrgFinanceDataIncomeResponse();

                    orgFinanceDataChannelResponse.setVenueId(orgFinanceVenues.getId());
                    orgFinanceDataChannelResponse.setVenueName(orgFinanceVenues.getVenueName());

                    orgFinanceDataChannelResponse.setIncomeType(orgFinanceEnums.getEnumNote());

                    int incomeChannelData = calOrgFinanceDataIncome(orgFinanceDataIncomeList, orgFinanceVenues.getId(), 0, orgFinanceEnums.getEnumNote());
                    orgFinanceDataChannelResponse.setIncomeValue(incomeChannelData);

                    int[] attendanceChannelData = calOrgFinanceDataAttendance(orgFinanceDataAttendanceList, orgFinanceVenues.getId(), 0, orgFinanceEnums.getEnumNote());
                    orgFinanceDataChannelResponse.setRegisterCount(attendanceChannelData[0]);
                    orgFinanceDataChannelResponse.setClassCount(attendanceChannelData[1]);

                    if (incomeChannelData <= 0 && attendanceChannelData[0] <= 0 && attendanceChannelData[1] <= 0) {
                        continue;
                    }

                    orgFinanceDataIncomeTypeList.add(orgFinanceDataChannelResponse);
                }

                OrgFinanceDataIncomeResponse orgFinanceDataChannelResponse1 = new OrgFinanceDataIncomeResponse();

                orgFinanceDataChannelResponse1.setVenueId(orgFinanceVenues.getId());
                orgFinanceDataChannelResponse1.setVenueName(orgFinanceVenues.getVenueName());

                orgFinanceDataChannelResponse1.setIncomeType("无");

                int incomeChannelData = calOrgFinanceDataIncome(orgFinanceDataIncomeList, orgFinanceVenues.getId(), 0, "无");
                orgFinanceDataChannelResponse1.setIncomeValue(incomeChannelData);

                int[] attendanceChannelData = calOrgFinanceDataAttendance(orgFinanceDataAttendanceList, orgFinanceVenues.getId(), 0, "无");
                orgFinanceDataChannelResponse1.setRegisterCount(attendanceChannelData[0]);
                orgFinanceDataChannelResponse1.setClassCount(attendanceChannelData[1]);

                if (incomeChannelData > 0 || attendanceChannelData[0] > 0 || attendanceChannelData[1] > 0) {
                    orgFinanceDataIncomeTypeList.add(orgFinanceDataChannelResponse1);
                }
            }

            map.put("orgFinanceVenuesList", orgFinanceVenuesList);
            map.put("orgFinanceIncomeList", orgFinanceIncomeEnumsList);
            map.put("orgFinanceDataVenueList", orgFinanceDataVenueList);
            map.put("orgFinanceDataIncomeTypeList", orgFinanceDataIncomeTypeList);

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("场馆业绩与时段有关")
    @ResponseBody
    @RequestMapping(value = "/getFinancePerformanceTimesForVenues", method = RequestMethod.GET)
    public ResponseBean getFinancePerformanceTimesForVenues(OrgFinanceLogRequest orgFinanceLogRequest) {
        try {
            Map map = new HashMap();

            if (orgFinanceLogRequest.getTypeTime() == null) {
                orgFinanceLogRequest.setTypeTime("month");
            }

            List<OrgFinanceVenues> orgFinanceVenuesList = orgFinanceVenuesService.queryOrgFinanceVenuesList();
            map.put("orgFinanceVenuesList", orgFinanceVenuesList);

            String typePeriod[] = getStartEndTime(orgFinanceLogRequest.getTypeTime());
            String startTime = typePeriod[0].substring(0, 10);
            String endTime = typePeriod[1].substring(0, 10);

            // 闲忙
            int timesTotal = orgFinanceDataTimesService.queryOrgFinanceDataTimesCount(orgFinanceLogRequest.getBusType(), 0, 0, startTime, endTime);
            List<OrgFinanceDataTimes> orgFinanceDataTimesList = orgFinanceDataTimesService.queryOrgFinanceDataTimesList(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), 0, startTime, endTime, 0, timesTotal);
            for (OrgFinanceDataTimes orgFinanceDataTimes : orgFinanceDataTimesList) {
                orgFinanceDataTimes.setUserId(-1);
            }

            List<OrgFinanceDataTimesResponse> orgFinanceDataVenueList = new ArrayList<>();
            for (OrgFinanceVenues orgFinanceVenues : orgFinanceVenuesList) {
                OrgFinanceDataTimesResponse orgFinanceDataResponse = new OrgFinanceDataTimesResponse();

                orgFinanceDataResponse.setVenueId(orgFinanceVenues.getId());
                orgFinanceDataResponse.setVenueName(orgFinanceVenues.getVenueName());

                int[] timesData = calOrgFinanceDataTimes(orgFinanceDataTimesList, orgFinanceVenues.getId(), 0);
                orgFinanceDataResponse.setNullCount(timesData[0]);
                orgFinanceDataResponse.setNullTotalCount(timesData[1]);
                orgFinanceDataResponse.setHotCount(timesData[2]);
                orgFinanceDataResponse.setHotTotalCount(timesData[3]);

                if (timesData[0] <= 0 && timesData[1] <= 0 && timesData[2] <= 0 && timesData[3] <= 0) {
                    continue;
                }

                orgFinanceDataVenueList.add(orgFinanceDataResponse);
            }

            map.put("orgFinanceDataVenueList", orgFinanceDataVenueList);

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("场馆业绩渠道相关")
    @ResponseBody
    @RequestMapping(value = "/getFinancePerformanceChannelForUsers", method = RequestMethod.GET)
    public ResponseBean getFinancePerformanceChannelForUsers(OrgFinanceLogRequest orgFinanceLogRequest) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();

            if (orgFinanceLogRequest.getTypeTime() == null) {
                orgFinanceLogRequest.setTypeTime("month");
            }

            int count = orgFinanceUsersService.queryOrgFinanceUsersCount();
            List<OrgFinanceUsers> orgFinanceUsersList = orgFinanceUsersService.queryOrgFinanceUsersList(0, count);
            List<OrgFinanceUsers> orgFinanceUsersList1 = new ArrayList<>();
            for (OrgFinanceUsers orgFinanceUsers : orgFinanceUsersList) {
                if (orgFinanceLogRequest.getVenueId() > 0 && orgFinanceLogRequest.getVenueId() != orgFinanceUsers.getVenueId()) {
                    continue;
                }
                orgFinanceUsersList1.add(orgFinanceUsers);
            }
            map.put("orgFinanceUsersList", orgFinanceUsersList1);

            List<OrgFinanceEnums> orgFinanceChannelEnumsList = orgFinanceEnumsService.queryOrgFinanceEnumsList("GROUP_CHANNEL");

            String typePeriod[] = getStartEndTime(orgFinanceLogRequest.getTypeTime());
            String startTime = typePeriod[0].substring(0, 10);
            String endTime = typePeriod[1].substring(0, 10);

            // 流水
            int flowTotal = orgFinanceDataFlowService.queryOrgFinanceDataFlowCount(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), 0, startTime, endTime);
            List<OrgFinanceDataFlow> orgFinanceDataFlowList = orgFinanceDataFlowService.queryOrgFinanceDataFlowList(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), 0, startTime, endTime, 0, flowTotal);

            // 体验成交
            int businessTotal = orgFinanceDataBusinessService.queryOrgFinanceDataBusinessCount(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), 0, startTime, endTime);
            List<OrgFinanceDataBusiness> orgFinanceDataBusinessList = orgFinanceDataBusinessService.queryOrgFinanceDataBusinessList(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), 0, startTime, endTime, 0, businessTotal);

            // 目标
            List<OrgFinanceGoals> orgFinanceGoalsList = orgFinanceGoalsService.queryOrgFinanceGoalsList(orgFinanceLogRequest.getBusType(), 0, orgFinanceLogRequest.getVenueId(), 0, 0);

            List<OrgFinanceDataChannelResponse> orgFinanceDataVenueList = new ArrayList<>();
            for (OrgFinanceUsers orgFinanceUsers : orgFinanceUsersList) {
                if (orgFinanceLogRequest.getVenueId() > 0 && orgFinanceLogRequest.getVenueId() != orgFinanceUsers.getVenueId()) {
                    continue;
                }

                OrgFinanceDataChannelResponse orgFinanceDataResponse = new OrgFinanceDataChannelResponse();

                orgFinanceDataResponse.setUserId(orgFinanceUsers.getId());
                orgFinanceDataResponse.setRealName(orgFinanceUsers.getRealName());

                int pipelineData = calOrgFinanceDataFlow(orgFinanceDataFlowList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), null);
                orgFinanceDataResponse.setPipelineValue(pipelineData);

                int[] businessData = calOrgFinanceDataBusiness(orgFinanceDataBusinessList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), null);
                orgFinanceDataResponse.setAccessCount(businessData[0]);
                orgFinanceDataResponse.setBusinessCount(businessData[1]);

                if (pipelineData <= 0 && businessData[0] <= 0) {
                    continue;
                }

                Integer[] financeGoals = getOrgFinanceGoalsForDate(orgFinanceGoalsList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), startTime, typePeriod[2]);
                if (financeGoals != null) {
                    orgFinanceDataResponse.setPipelineTarget(financeGoals[0]);
                    orgFinanceDataResponse.setPipelineChallenge(financeGoals[1]);
                }

                orgFinanceDataVenueList.add(orgFinanceDataResponse);
            }

            List<OrgFinanceDataChannelResponse> orgFinanceDataChannelList = new ArrayList<>();
            for (OrgFinanceUsers orgFinanceUsers : orgFinanceUsersList) {
                if (orgFinanceLogRequest.getVenueId() > 0 && orgFinanceLogRequest.getVenueId() != orgFinanceUsers.getVenueId()) {
                    continue;
                }

                for (OrgFinanceEnums orgFinanceEnums : orgFinanceChannelEnumsList) {
                    OrgFinanceDataChannelResponse orgFinanceDataChannelResponse = new OrgFinanceDataChannelResponse();

                    orgFinanceDataChannelResponse.setUserId(orgFinanceUsers.getId());
                    orgFinanceDataChannelResponse.setRealName(orgFinanceUsers.getRealName());

                    orgFinanceDataChannelResponse.setChannelName(orgFinanceEnums.getEnumNote());

                    int pipelineData = calOrgFinanceDataFlow(orgFinanceDataFlowList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), orgFinanceEnums.getEnumNote());
                    orgFinanceDataChannelResponse.setPipelineValue(pipelineData);

                    int[] businessChannelData = calOrgFinanceDataBusiness(orgFinanceDataBusinessList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), orgFinanceEnums.getEnumNote());
                    orgFinanceDataChannelResponse.setAccessCount(businessChannelData[0]);
                    orgFinanceDataChannelResponse.setBusinessCount(businessChannelData[1]);

                    if (pipelineData <= 0 && businessChannelData[0] <= 0) {
                        continue;
                    }

                    orgFinanceDataChannelList.add(orgFinanceDataChannelResponse);
                }

                OrgFinanceDataChannelResponse orgFinanceDataChannelResponse1 = new OrgFinanceDataChannelResponse();

                orgFinanceDataChannelResponse1.setUserId(orgFinanceUsers.getId());
                orgFinanceDataChannelResponse1.setRealName(orgFinanceUsers.getRealName());

                orgFinanceDataChannelResponse1.setChannelName("无");

                int pipelineData = calOrgFinanceDataFlow(orgFinanceDataFlowList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), "无");
                orgFinanceDataChannelResponse1.setPipelineValue(pipelineData);

                int[] businessChannelData = calOrgFinanceDataBusiness(orgFinanceDataBusinessList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), "无");
                orgFinanceDataChannelResponse1.setAccessCount(businessChannelData[0]);
                orgFinanceDataChannelResponse1.setBusinessCount(businessChannelData[1]);

                if (pipelineData > 0 || businessChannelData[0] > 0) {
                    orgFinanceDataChannelList.add(orgFinanceDataChannelResponse1);
                }
            }

            map.put("orgFinanceChannelList", orgFinanceChannelEnumsList);
            map.put("orgFinanceDataVenueList", orgFinanceDataVenueList);
            map.put("orgFinanceDataChannelList", orgFinanceDataChannelList);

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("场馆业绩与收入有关")
    @ResponseBody
    @RequestMapping(value = "/getFinancePerformanceIncomeForUsers", method = RequestMethod.GET)
    public ResponseBean getFinancePerformanceIncomeForUsers(OrgFinanceLogRequest orgFinanceLogRequest) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();

            if (orgFinanceLogRequest.getTypeTime() == null) {
                orgFinanceLogRequest.setTypeTime("month");
            }

            int count = orgFinanceUsersService.queryOrgFinanceUsersCount();
            List<OrgFinanceUsers> orgFinanceUsersList = orgFinanceUsersService.queryOrgFinanceUsersList(0, count);
            List<OrgFinanceUsers> orgFinanceUsersList1 = new ArrayList<>();
            for (OrgFinanceUsers orgFinanceUsers : orgFinanceUsersList) {
                if (orgFinanceLogRequest.getVenueId() > 0 && orgFinanceLogRequest.getVenueId() != orgFinanceUsers.getVenueId()) {
                    continue;
                }
                orgFinanceUsersList1.add(orgFinanceUsers);
            }
            map.put("orgFinanceUsersList", orgFinanceUsersList1);

            List<OrgFinanceEnums> orgFinanceIncomeEnumsList = orgFinanceEnumsService.queryOrgFinanceEnumsList("GROUP_CINCOME");
            map.put("orgFinanceIncomeList", orgFinanceIncomeEnumsList);

            String typePeriod[] = getStartEndTime(orgFinanceLogRequest.getTypeTime());
            String startTime = typePeriod[0].substring(0, 10);
            String endTime = typePeriod[1].substring(0, 10);

            // 确认收入
            int incomeTotal = orgFinanceDataIncomeService.queryOrgFinanceDataIncomeCount(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), 0, startTime, endTime);
            List<OrgFinanceDataIncome> orgFinanceDataIncomeList = orgFinanceDataIncomeService.queryOrgFinanceDataIncomeList(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), 0, startTime, endTime, 0, incomeTotal);

            // 签到
            int attendanceTotal = orgFinanceDataAttendanceService.queryOrgFinanceDataAttendanceCount(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), 0, startTime, endTime);
            List<OrgFinanceDataAttendance> orgFinanceDataAttendanceList = orgFinanceDataAttendanceService.queryOrgFinanceDataAttendanceList(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), 0, startTime, endTime, 0, attendanceTotal);

            // 目标
            List<OrgFinanceGoals> orgFinanceGoalsList = orgFinanceGoalsService.queryOrgFinanceGoalsList(orgFinanceLogRequest.getBusType(), 0, orgFinanceLogRequest.getVenueId(), 0, 0);

            List<OrgFinanceDataIncomeResponse> orgFinanceDataVenueList = new ArrayList<>();
            for (OrgFinanceUsers orgFinanceUsers : orgFinanceUsersList) {
                if (orgFinanceLogRequest.getVenueId() > 0 && orgFinanceLogRequest.getVenueId() != orgFinanceUsers.getVenueId()) {
                    continue;
                }

                OrgFinanceDataIncomeResponse orgFinanceDataResponse = new OrgFinanceDataIncomeResponse();

                orgFinanceDataResponse.setUserId(orgFinanceUsers.getId());
                orgFinanceDataResponse.setRealName(orgFinanceUsers.getRealName());

                int incomeData = calOrgFinanceDataIncome(orgFinanceDataIncomeList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), null);
                orgFinanceDataResponse.setIncomeValue(incomeData);

                int[] attendanceData = calOrgFinanceDataAttendance(orgFinanceDataAttendanceList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), null);
                orgFinanceDataResponse.setRegisterCount(attendanceData[0]);
                orgFinanceDataResponse.setClassCount(attendanceData[1]);

                if (incomeData <= 0 && attendanceData[0] <= 0 && attendanceData[1] <= 0) {
                    continue;
                }

                Integer[] financeGoals = getOrgFinanceGoalsForDate(orgFinanceGoalsList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), startTime, typePeriod[2]);
                if (financeGoals != null) {
                    orgFinanceDataResponse.setIncomeTarget(financeGoals[2]);
                    orgFinanceDataResponse.setIncomeChallenge(financeGoals[3]);
                }

                orgFinanceDataVenueList.add(orgFinanceDataResponse);
            }

            List<OrgFinanceDataIncomeResponse> orgFinanceDataIncomeTypeList = new ArrayList<>();
            for (OrgFinanceUsers orgFinanceUsers : orgFinanceUsersList) {
                if (orgFinanceLogRequest.getVenueId() > 0 && orgFinanceLogRequest.getVenueId() != orgFinanceUsers.getVenueId()) {
                    continue;
                }

                for (OrgFinanceEnums orgFinanceEnums : orgFinanceIncomeEnumsList) {
                    OrgFinanceDataIncomeResponse orgFinanceDataChannelResponse = new OrgFinanceDataIncomeResponse();

                    orgFinanceDataChannelResponse.setUserId(orgFinanceUsers.getId());
                    orgFinanceDataChannelResponse.setRealName(orgFinanceUsers.getRealName());

                    orgFinanceDataChannelResponse.setIncomeType(orgFinanceEnums.getEnumNote());

                    int incomeChannelData = calOrgFinanceDataIncome(orgFinanceDataIncomeList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), orgFinanceEnums.getEnumNote());
                    orgFinanceDataChannelResponse.setIncomeValue(incomeChannelData);

                    int[] attendanceChannelData = calOrgFinanceDataAttendance(orgFinanceDataAttendanceList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), orgFinanceEnums.getEnumNote());
                    orgFinanceDataChannelResponse.setRegisterCount(attendanceChannelData[0]);
                    orgFinanceDataChannelResponse.setClassCount(attendanceChannelData[1]);

                    if (incomeChannelData <= 0 && attendanceChannelData[0] <= 0 && attendanceChannelData[1] <= 0) {
                        continue;
                    }

                    orgFinanceDataIncomeTypeList.add(orgFinanceDataChannelResponse);
                }

                OrgFinanceDataIncomeResponse orgFinanceDataChannelResponse1 = new OrgFinanceDataIncomeResponse();

                orgFinanceDataChannelResponse1.setUserId(orgFinanceUsers.getId());
                orgFinanceDataChannelResponse1.setRealName(orgFinanceUsers.getRealName());

                orgFinanceDataChannelResponse1.setIncomeType("无");

                int incomeChannelData = calOrgFinanceDataIncome(orgFinanceDataIncomeList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), "无");
                orgFinanceDataChannelResponse1.setIncomeValue(incomeChannelData);

                int[] attendanceChannelData = calOrgFinanceDataAttendance(orgFinanceDataAttendanceList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId(), "无");
                orgFinanceDataChannelResponse1.setRegisterCount(attendanceChannelData[0]);
                orgFinanceDataChannelResponse1.setClassCount(attendanceChannelData[1]);

                if (incomeChannelData > 0 || attendanceChannelData[0] > 0 || attendanceChannelData[1] > 0) {
                    orgFinanceDataIncomeTypeList.add(orgFinanceDataChannelResponse1);
                }
            }

            map.put("orgFinanceDataVenueList", orgFinanceDataVenueList);
            map.put("orgFinanceDataIncomeTypeList", orgFinanceDataIncomeTypeList);

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("场馆业绩与时段有关")
    @ResponseBody
    @RequestMapping(value = "/getFinancePerformanceTimesForUsers", method = RequestMethod.GET)
    public ResponseBean getFinancePerformanceTimesForUsers(OrgFinanceLogRequest orgFinanceLogRequest) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();

            if (orgFinanceLogRequest.getTypeTime() == null) {
                orgFinanceLogRequest.setTypeTime("month");
            }

            int count = orgFinanceUsersService.queryOrgFinanceUsersCount();
            List<OrgFinanceUsers> orgFinanceUsersList = orgFinanceUsersService.queryOrgFinanceUsersList(0, count);
            List<OrgFinanceUsers> orgFinanceUsersList1 = new ArrayList<>();
            for (OrgFinanceUsers orgFinanceUsers : orgFinanceUsersList) {
                if (orgFinanceLogRequest.getVenueId() > 0 && orgFinanceLogRequest.getVenueId() != orgFinanceUsers.getVenueId()) {
                    continue;
                }
                orgFinanceUsersList1.add(orgFinanceUsers);
            }
            map.put("orgFinanceUsersList", orgFinanceUsersList1);

            String typePeriod[] = getStartEndTime(orgFinanceLogRequest.getTypeTime());
            String startTime = typePeriod[0].substring(0, 10);
            String endTime = typePeriod[1].substring(0, 10);

            // 闲忙
            int timesTotal = orgFinanceDataTimesService.queryOrgFinanceDataTimesCount(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), 0, startTime, endTime);
            List<OrgFinanceDataTimes> orgFinanceDataTimesList = orgFinanceDataTimesService.queryOrgFinanceDataTimesList(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), 0, startTime, endTime, 0, timesTotal);

            List<OrgFinanceDataTimesResponse> orgFinanceDataVenueList = new ArrayList<>();
            for (OrgFinanceUsers orgFinanceUsers : orgFinanceUsersList) {
                if (orgFinanceLogRequest.getVenueId() > 0 && orgFinanceLogRequest.getVenueId() != orgFinanceUsers.getVenueId()) {
                    continue;
                }

                OrgFinanceDataTimesResponse orgFinanceDataResponse = new OrgFinanceDataTimesResponse();

                orgFinanceDataResponse.setUserId(orgFinanceUsers.getId());
                orgFinanceDataResponse.setRealName(orgFinanceUsers.getRealName());

                int[] timesData = calOrgFinanceDataTimes(orgFinanceDataTimesList, orgFinanceUsers.getVenueId(), orgFinanceUsers.getId());
                orgFinanceDataResponse.setNullCount(timesData[0]);
                orgFinanceDataResponse.setNullTotalCount(timesData[1]);
                orgFinanceDataResponse.setHotCount(timesData[2]);
                orgFinanceDataResponse.setHotTotalCount(timesData[3]);

                if (timesData[0] <= 0 && timesData[1] <= 0 && timesData[2] <= 0 && timesData[3] <= 0) {
                    continue;
                }

                orgFinanceDataVenueList.add(orgFinanceDataResponse);
            }

            map.put("orgFinanceDataVenueList", orgFinanceDataVenueList);

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    // TODO

    @Desc("运用财务编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView renderFinanceEdit(String businessNo) {

        ModelAndView modelAndView = new ModelAndView("Finance/Edit");

        OrgOperator orgOperator = getLoginUser();

        OrgFinanceData orgFinanceData = new OrgFinanceData();
        if (orgOperator.getRoleId() == RoleEnum.ROLE_SUPER_FINANCE_INPUT.getCode()) {
            orgFinanceData.setUserId(orgOperator.getId());
            orgFinanceData.setVenueId(Integer.parseInt(orgOperator.getUserNo()));
        }

        if (businessNo != null) {
            orgFinanceData = orgFinanceDataService.getOrgFinanceData(businessNo);
        }
        modelAndView.addObject("orgFinanceData", orgFinanceData);

        modelAndView.addObject("BusinessTypeEnumList", EnumUtils.getEnumList(BusinessTypeEnum.class));
        modelAndView.addObject("BusinessChannelTypeEnumList", EnumUtils.getEnumList(BusinessChannelTypeEnum.class));

        List<OrgFinanceVenues> orgFinanceVenuesList = orgFinanceVenuesService.queryOrgFinanceVenuesList();
        modelAndView.addObject("orgFinanceVenuesList", orgFinanceVenuesList);

        modelAndView.addObject("todayDate", DateUtil.getNowDate());

        return setModelAndView(modelAndView);
    }

    @Desc("运用财务编辑提交")
    @ResponseBody
    @RequestMapping(value = "/saveFinance", method = RequestMethod.POST)
    public ResponseBean saveFinance(OrgFinanceData orgFinanceData) {
        try {

            Map map = new HashMap();
            int result;
            String businessNo;

            orgFinanceData.setOperatorId(getLoginUser().getId());

            if (orgFinanceData.getBusinessNo() != null && !orgFinanceData.getBusinessNo().equals("")) {
                orgFinanceData.setUpdateTime(DateUtil.getNowDate());
                businessNo = orgFinanceData.getBusinessNo();

                result = orgFinanceDataService.saveOrgFinanceData(orgFinanceData);
            }
            else {
                businessNo = StrUtil.getUUID();

                orgFinanceData.setBusinessNo(businessNo);
                orgFinanceData.setCreateTime(DateUtil.getNowDate());
                orgFinanceData.setUpdateTime(DateUtil.getNowDate());
                result = orgFinanceDataService.addOrgFinanceData(orgFinanceData);
            }

            if (result > 0) {
                map.put("businessNo", businessNo);
            }

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    private OrgFinanceDataResponse formatOrgFinanceData(OrgFinanceData orgFinanceData, List<OrgFinanceVenues> orgFinanceVenuesList, List<OrgFinanceUsers> orgFinanceUsersList) {
        OrgFinanceDataResponse orgFinanceDataResponse = new OrgFinanceDataResponse();

        orgFinanceDataResponse.setBusinessNo(orgFinanceData.getBusinessNo());
        orgFinanceDataResponse.setBusinessType(orgFinanceData.getBusinessType());
        orgFinanceDataResponse.setBusinessDate(orgFinanceData.getBusinessDate());
        orgFinanceDataResponse.setVenueId(orgFinanceData.getVenueId());
        orgFinanceDataResponse.setVenueName(getVenueName(orgFinanceData.getVenueId(), orgFinanceVenuesList));
        orgFinanceDataResponse.setUserId(orgFinanceData.getUserId());
        orgFinanceDataResponse.setRealName(getRealName(orgFinanceData.getUserId(), orgFinanceUsersList));
        orgFinanceDataResponse.setChannelType(orgFinanceData.getChannelType());
        orgFinanceDataResponse.setPipelineValue(orgFinanceData.getPipelineValue());
        orgFinanceDataResponse.setIncomeValue(orgFinanceData.getIncomeValue());
        orgFinanceDataResponse.setRegisterCount(orgFinanceData.getRegisterCount());
        orgFinanceDataResponse.setClassCount(orgFinanceData.getClassCount());
        orgFinanceDataResponse.setAccessCount(orgFinanceData.getAccessCount());
        orgFinanceDataResponse.setBusinessCount(orgFinanceData.getBusinessCount());
        orgFinanceDataResponse.setHotTotalCount(orgFinanceData.getHotTotalCount());
        orgFinanceDataResponse.setHotCount(orgFinanceData.getHotCount());
        orgFinanceDataResponse.setNullTotalCount(orgFinanceData.getNullTotalCount());
        orgFinanceDataResponse.setNullCount(orgFinanceData.getNullCount());

        return orgFinanceDataResponse;
    }

    @Desc("运用财务编辑提交")
    @ResponseBody
    @RequestMapping(value = "/getFinance", method = RequestMethod.GET)
    public ResponseBean getFinance(String businessNo) {
        try {
            Map map = new HashMap();

            List<OrgFinanceVenues> orgFinanceVenuesList = orgFinanceVenuesService.queryOrgFinanceVenuesList();

            int count = orgFinanceUsersService.queryOrgFinanceUsersCount();
            List<OrgFinanceUsers> orgFinanceUsersList = orgFinanceUsersService.queryOrgFinanceUsersList(0, count);

            map.put("orgFinanceData", formatOrgFinanceData(orgFinanceDataService.getOrgFinanceData(businessNo), orgFinanceVenuesList, orgFinanceUsersList));

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("数据日志")
    @RequestMapping(value = "/log", method = RequestMethod.GET)
    public ModelAndView renderFinanceLog(OrgFinanceLogRequest orgFinanceLogRequest) {

        ModelAndView modelAndView = new ModelAndView("Finance/Log");

        if (orgFinanceLogRequest.getBusType() == 0) {
            orgFinanceLogRequest.setBusType(BusinessTypeEnum.TRAINING_YOUNG.getCode());
        }

        OrgOperator orgOperator = getLoginUser();
        int userId = 0;
        if (orgOperator.getRoleId() == RoleEnum.ROLE_SUPER_FINANCE_INPUT.getCode()) {
            userId = orgOperator.getId();
        }


        modelAndView.addObject("BusinessTypeEnumList", EnumUtils.getEnumList(BusinessTypeEnum.class));
        modelAndView.addObject("BusinessTypeEnum", EnumUtils.getEnumMap(BusinessTypeEnum.class));

        modelAndView.addObject("conditions", orgFinanceLogRequest);

        List<OrgFinanceVenues> orgFinanceVenuesList = orgFinanceVenuesService.queryOrgFinanceVenuesList();
        modelAndView.addObject("orgFinanceVenuesList", orgFinanceVenuesList);

        int count = orgFinanceUsersService.queryOrgFinanceUsersCount();
        List<OrgFinanceUsers> orgFinanceUsersList = orgFinanceUsersService.queryOrgFinanceUsersList(0, count);

        int total = orgFinanceDataService.queryOrgFinanceDataCount(orgFinanceLogRequest.getBusType(),
        orgFinanceLogRequest.getVenueId(), userId, null, null);
        int start = orgFinanceLogRequest.getPage() < 1 ? 0 : orgFinanceLogRequest.getPage() - 1;
        int pageSize = 10;
        List<OrgFinanceData> orgFinanceDataList = orgFinanceDataService.queryOrgFinanceDataList(orgFinanceLogRequest.getBusType(),
                orgFinanceLogRequest.getVenueId(), userId, null, null,
                start * pageSize, pageSize);

        List<OrgFinanceDataResponse> orgFinanceDataResponseList = new ArrayList<>();
        for (OrgFinanceData orgFinanceData : orgFinanceDataList) {
            orgFinanceDataResponseList.add(formatOrgFinanceData(orgFinanceData, orgFinanceVenuesList, orgFinanceUsersList));
        }
        modelAndView.addObject("orgFinanceDataList", orgFinanceDataResponseList);

        Page page = new Page(pageSize, total);
        page.setPage(orgFinanceLogRequest.getPage());

        modelAndView.addObject("total", total);
        modelAndView.addObject("pageURL", "/admin/finance/log?busType=" + orgFinanceLogRequest.getBusType() + "&venueId=" + orgFinanceLogRequest.getVenueId());
        modelAndView.addObject("page", page);

        return setModelAndView(modelAndView);
    }

    // TODO

//    @Desc("场馆业绩")
//    @ResponseBody
//    @RequestMapping(value = "/getFinancePerformanceForVenues", method = RequestMethod.GET)
//    public ResponseBean getFinancePerformanceForVenues(OrgFinanceLogRequest orgFinanceLogRequest) {
//        try {
//            Map map = new HashMap();
//
//            if (orgFinanceLogRequest.getTypeTime() == null) {
//                orgFinanceLogRequest.setTypeTime("month");
//            }
//
////            if (orgFinanceLogRequest.getBusType() == 0) {
////                orgFinanceLogRequest.setBusType(BusinessTypeEnum.TRAINING_YOUNG.getCode());
////            }
//
//            List<OrgFinanceVenues> orgFinanceVenuesList = orgFinanceVenuesService.queryOrgFinanceVenuesList();
//
//            int count = orgFinanceUsersService.queryOrgFinanceUsersCount();
//            List<OrgFinanceUsers> orgFinanceUsersList = orgFinanceUsersService.queryOrgFinanceUsersList(0, count);
//
//            String typePeriod[] = getStartEndTime(orgFinanceLogRequest.getTypeTime());
//            String startTime = typePeriod[0].substring(0, 10);
//            String endTime = typePeriod[1].substring(0, 10);
//
//            // 流水
//            int flowTotal = orgFinanceDataFlowService.queryOrgFinanceDataFlowCount(orgFinanceLogRequest.getBusType(), 0, 0, startTime, endTime);
//            List<OrgFinanceDataFlow> orgFinanceDataFlowList = orgFinanceDataFlowService.queryOrgFinanceDataFlowList(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), 0, startTime, endTime, 0, flowTotal);
//            for (OrgFinanceDataFlow orgFinanceDataFlow : orgFinanceDataFlowList) {
//                orgFinanceDataFlow.setUserId(-1);
//            }
//
//            // 体验成交
//            int businessTotal = orgFinanceDataBusinessService.queryOrgFinanceDataBusinessCount(orgFinanceLogRequest.getBusType(), 0, 0, startTime, endTime);
//            List<OrgFinanceDataBusiness> orgFinanceDataBusinessList = orgFinanceDataBusinessService.queryOrgFinanceDataBusinessList(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), 0, startTime, endTime, 0, businessTotal);
//            for (OrgFinanceDataBusiness orgFinanceDataBusiness : orgFinanceDataBusinessList) {
//                orgFinanceDataBusiness.setUserId(-1);
//            }
//
//            // 确认收入
//            int incomeTotal = orgFinanceDataIncomeService.queryOrgFinanceDataIncomeCount(orgFinanceLogRequest.getBusType(), 0, 0, startTime, endTime);
//            List<OrgFinanceDataIncome> orgFinanceDataIncomeList = orgFinanceDataIncomeService.queryOrgFinanceDataIncomeList(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), 0, startTime, endTime, 0, incomeTotal);
//            for (OrgFinanceDataIncome orgFinanceDataIncome : orgFinanceDataIncomeList) {
//                orgFinanceDataIncome.setUserId(-1);
//            }
//
//            // 签到
//            int attendanceTotal = orgFinanceDataAttendanceService.queryOrgFinanceDataAttendanceCount(orgFinanceLogRequest.getBusType(), 0, 0, startTime, endTime);
//            List<OrgFinanceDataAttendance> orgFinanceDataAttendanceList = orgFinanceDataAttendanceService.queryOrgFinanceDataAttendanceList(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), 0, startTime, endTime, 0, attendanceTotal);
//            for (OrgFinanceDataAttendance orgFinanceDataAttendance : orgFinanceDataAttendanceList) {
//                orgFinanceDataAttendance.setUserId(-1);
//            }
//
//            // 闲忙
//            int timesTotal = orgFinanceDataTimesService.queryOrgFinanceDataTimesCount(orgFinanceLogRequest.getBusType(), 0, 0, startTime, endTime);
//            List<OrgFinanceDataTimes> orgFinanceDataTimesList = orgFinanceDataTimesService.queryOrgFinanceDataTimesList(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), 0, startTime, endTime, 0, timesTotal);
//            for (OrgFinanceDataTimes orgFinanceDataTimes : orgFinanceDataTimesList) {
//                orgFinanceDataTimes.setUserId(-1);
//            }
//
//            // 目标
//            List<OrgFinanceGoals> orgFinanceGoalsList = orgFinanceGoalsService.queryOrgFinanceGoalsList(orgFinanceLogRequest.getBusType(), 0, 0, 0, 0);
//
//            int total = orgFinanceDataService.queryOrgFinanceDataCount(orgFinanceLogRequest.getBusType(), 0, 0, startTime, endTime);
//            List<OrgFinanceData> orgFinanceDataList = orgFinanceDataService.queryOrgFinanceDataList(orgFinanceLogRequest.getBusType(), 0, 0, startTime, endTime, 0, total);
//
//            for (OrgFinanceData orgFinanceData : orgFinanceDataList) {
//                orgFinanceData.setUserId(-1);
//            }
//
//            List<OrgFinanceDataResponse> orgFinanceDataResponseList1 = formatOrgFinanceDataChannelSummary(orgFinanceDataList, orgFinanceVenuesList, orgFinanceUsersList, true);
//            List<OrgFinanceDataResponse> orgFinanceDataResponseList2 = formatOrgFinanceDataChannelSummary(orgFinanceDataList, orgFinanceVenuesList, orgFinanceUsersList, false);
//
//            List<OrgFinanceGoals> orgFinanceGoalsList = orgFinanceGoalsService.queryOrgFinanceGoalsList(orgFinanceLogRequest.getBusType(), 0, 0, 0, 0);
//
//            for (OrgFinanceDataResponse orgFinanceDataResponse : orgFinanceDataResponseList2) {
//                Integer[] financeGoals = getOrgFinanceGoalsForDate(orgFinanceGoalsList, orgFinanceDataResponse.getVenueId(), 0, startTime, typePeriod[2]);
//
//                if (financeGoals != null) {
//                    orgFinanceDataResponse.setPipelineTarget(financeGoals[0]);
//                    orgFinanceDataResponse.setPipelineChallenge(financeGoals[1]);
//                    orgFinanceDataResponse.setIncomeTarget(financeGoals[2]);
//                    orgFinanceDataResponse.setIncomeChallenge(financeGoals[3]);
//                }
//            }
//
//            map.put("orgFinanceDataChannelList", orgFinanceDataResponseList1);
//            map.put("orgFinanceDataVenueList", orgFinanceDataResponseList2);
//            map.put("orgFinanceVenuesList", orgFinanceVenuesList);
//            map.put("orgFinanceChannelList", BusinessChannelTypeEnum.getEnumList());
//
//            return new ResponseBean(map);
//        } catch (MessageException e) {
//            e.printStackTrace();
//            return new ResponseBean(e.getMessage());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseBean(false);
//        }
//    }

    @Desc("个人业绩")
    @ResponseBody
    @RequestMapping(value = "/getFinancePerformanceForUsers", method = RequestMethod.GET)
    public ResponseBean getFinancePerformanceForUsers(OrgFinanceLogRequest orgFinanceLogRequest) {
        try {
            Map map = new HashMap();

            if (orgFinanceLogRequest.getTypeTime() == null) {
                orgFinanceLogRequest.setTypeTime("month");
            }

            if (orgFinanceLogRequest.getBusType() == 0) {
                orgFinanceLogRequest.setBusType(BusinessTypeEnum.TRAINING_YOUNG.getCode());
            }

            List<OrgFinanceVenues> orgFinanceVenuesList = orgFinanceVenuesService.queryOrgFinanceVenuesList();

            int count = orgFinanceUsersService.queryOrgFinanceUsersCount();
            List<OrgFinanceUsers> orgFinanceUsersList1 = orgFinanceUsersService.queryOrgFinanceUsersList(0, count);

            List<OrgFinanceUsers> orgFinanceUsersList = new ArrayList<>();
            for (OrgFinanceUsers orgFinanceUsers : orgFinanceUsersList1) {
                orgFinanceUsers.setPassword(null);
                if (orgFinanceLogRequest.getVenueId() > 0 && orgFinanceLogRequest.getVenueId() == orgFinanceUsers.getVenueId()) {
                    orgFinanceUsersList.add(orgFinanceUsers);
                }
                else if (orgFinanceLogRequest.getVenueId() == 0) {
                    orgFinanceUsersList.add(orgFinanceUsers);
                }
            }

            String typePeriod[] = getStartEndTime(orgFinanceLogRequest.getTypeTime());
            String startTime = typePeriod[0].substring(0, 10);
            String endTime = typePeriod[1].substring(0, 10);

            int total = orgFinanceDataService.queryOrgFinanceDataCount(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), 0, startTime, endTime);
            List<OrgFinanceData> orgFinanceDataList = orgFinanceDataService.queryOrgFinanceDataList(orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(), 0, startTime, endTime, 0, total);

            List<OrgFinanceDataResponse> orgFinanceDataResponseList1 = formatOrgFinanceDataChannelSummary(orgFinanceDataList, orgFinanceVenuesList, orgFinanceUsersList, true);
            List<OrgFinanceDataResponse> orgFinanceDataResponseList2 = formatOrgFinanceDataChannelSummary(orgFinanceDataList, orgFinanceVenuesList, orgFinanceUsersList, false);

            List<OrgFinanceGoals> orgFinanceGoalsList = orgFinanceGoalsService.queryOrgFinanceGoalsList(orgFinanceLogRequest.getBusType(), 0, orgFinanceLogRequest.getVenueId(), 0, 0);

            String venueName = "所有基地";
            for (OrgFinanceDataResponse orgFinanceDataResponse : orgFinanceDataResponseList2) {

                if (orgFinanceLogRequest.getVenueId() > 0) {
                    venueName = orgFinanceDataResponse.getVenueName();
                }

                Integer[] financeGoals = getOrgFinanceGoalsForDate(orgFinanceGoalsList, orgFinanceDataResponse.getVenueId(), orgFinanceDataResponse.getUserId(), startTime, typePeriod[2]);

                if (financeGoals != null) {
                    orgFinanceDataResponse.setPipelineTarget(financeGoals[0]);
                    orgFinanceDataResponse.setPipelineChallenge(financeGoals[1]);
                    orgFinanceDataResponse.setIncomeTarget(financeGoals[2]);
                    orgFinanceDataResponse.setIncomeChallenge(financeGoals[3]);
                }
            }

            map.put("venueName", venueName);
            map.put("orgFinanceDataChannelList", orgFinanceDataResponseList1);
            map.put("orgFinanceDataUserList", orgFinanceDataResponseList2);
            map.put("orgFinanceUsersList", orgFinanceUsersList);
            map.put("orgFinanceChannelList", BusinessChannelTypeEnum.getEnumList());

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
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

        return new String[]{startTime, endTime, typeTime};
    }

    private String getRealName(Integer userId, List<OrgFinanceUsers> orgFinanceUsersList) {
        for (OrgFinanceUsers orgFinanceUsers : orgFinanceUsersList) {
            if (orgFinanceUsers.getId() == userId) {
                return orgFinanceUsers.getRealName();
            }
        }

        return "";
    }

    private String getVenueName(Integer venueId, List<OrgFinanceVenues> orgFinanceVenuesList) {
        for (OrgFinanceVenues orgFinanceVenues : orgFinanceVenuesList) {
            if (orgFinanceVenues.getId() == venueId) {
                return orgFinanceVenues.getVenueName();
            }
        }

        return "";
    }

    @Desc("个人业绩统计")
    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    public ModelAndView renderFinanceSummary(OrgFinanceLogRequest orgFinanceLogRequest) throws Exception {

        ModelAndView modelAndView = new ModelAndView("Finance/Summary");

        if (orgFinanceLogRequest.getTypeTime() == null) {
            orgFinanceLogRequest.setTypeTime("month");
        }

        if (orgFinanceLogRequest.getBusType() == 0) {
            orgFinanceLogRequest.setBusType(BusinessTypeEnum.TRAINING_YOUNG.getCode());
        }

        modelAndView.addObject("typeTime", orgFinanceLogRequest.getTypeTime());
        modelAndView.addObject("busType", orgFinanceLogRequest.getBusType());

        modelAndView.addObject("BusinessChannelTypeEnumList", EnumUtils.getEnumList(BusinessChannelTypeEnum.class));
        modelAndView.addObject("BusinessChannelTypeEnum", EnumUtils.getEnumMap(BusinessChannelTypeEnum.class));

        modelAndView.addObject("BusinessTypeEnumList", EnumUtils.getEnumList(BusinessTypeEnum.class));
        modelAndView.addObject("BusinessTypeEnum", EnumUtils.getEnumMap(BusinessTypeEnum.class));

        List<OrgFinanceVenues> orgFinanceVenuesList = orgFinanceVenuesService.queryOrgFinanceVenuesList();
        modelAndView.addObject("orgFinanceVenuesList", orgFinanceVenuesList);

        int count = orgFinanceUsersService.queryOrgFinanceUsersCount();
        List<OrgFinanceUsers> orgFinanceUsersList = orgFinanceUsersService.queryOrgFinanceUsersList(0, count);

        String typePeriod[] = getStartEndTime(orgFinanceLogRequest.getTypeTime());
        String startTime = typePeriod[0].substring(0, 10);
        String endTime = typePeriod[1].substring(0, 10);

        int total = orgFinanceDataService.queryOrgFinanceDataCount(orgFinanceLogRequest.getBusType(), 0, 0, startTime, endTime);
        List<OrgFinanceData> orgFinanceDataList = orgFinanceDataService.queryOrgFinanceDataList(
                orgFinanceLogRequest.getBusType(), orgFinanceLogRequest.getVenueId(),
                0, startTime, endTime, 0, total);

        List<OrgFinanceDataResponse> orgFinanceDataResponseList = formatOrgFinanceDataSummary(
                orgFinanceDataList, orgFinanceVenuesList, orgFinanceUsersList);
        List<OrgFinanceDataResponse> orgFinanceDataResponseChannelList = formatOrgFinanceDataChannelSummary(
                orgFinanceDataList, orgFinanceVenuesList, orgFinanceUsersList, true);

        List<OrgFinanceGoals> orgFinanceGoalsList = orgFinanceGoalsService.queryOrgFinanceGoalsList(orgFinanceLogRequest.getBusType(),
                0, orgFinanceLogRequest.getVenueId(), 0, 0);

        for (OrgFinanceDataResponse orgFinanceDataResponse : orgFinanceDataResponseList) {
            Integer[] financeGoals = getOrgFinanceGoalsForDate(orgFinanceGoalsList, orgFinanceDataResponse.getVenueId(),
                    orgFinanceDataResponse.getUserId(), startTime, typePeriod[2]);

            if (financeGoals != null) {
                orgFinanceDataResponse.setPipelineTarget(financeGoals[0]);
                orgFinanceDataResponse.setPipelineChallenge(financeGoals[1]);
                orgFinanceDataResponse.setIncomeTarget(financeGoals[2]);
                orgFinanceDataResponse.setIncomeChallenge(financeGoals[3]);
            }

            List<OrgFinanceDataResponse> orgFinanceDataResponseChannelList1 = new ArrayList<>();
            for (OrgFinanceDataResponse orgFinanceDataResponseChannel : orgFinanceDataResponseChannelList) {
                if (orgFinanceDataResponse.getVenueId().intValue() == orgFinanceDataResponseChannel.getVenueId()
                        && orgFinanceDataResponse.getUserId().intValue() == orgFinanceDataResponseChannel.getUserId()
                        && orgFinanceDataResponse.getBusinessType().intValue() == orgFinanceDataResponseChannel.getBusinessType()) {

                    orgFinanceDataResponseChannelList1.add(orgFinanceDataResponseChannel);
                }
            }
            orgFinanceDataResponse.setOrgFinanceDataResponseChannelList(orgFinanceDataResponseChannelList1);
        }

        modelAndView.addObject("orgFinanceDataResponseList", orgFinanceDataResponseList);

        return setModelAndView(modelAndView);
    }

    // 人/馆目标
    private Integer[] getOrgFinanceGoalsForDate(List<OrgFinanceGoals> orgFinanceGoalsList, int venueId, int userId, String startTime, String typeTime) throws Exception {
        Integer[] financeGoals = null;

        int year = Integer.parseInt(startTime.substring(0, 4));
        int month = Integer.parseInt(startTime.substring(5, 7));
        int period = month <= 3 ? 1 : (month <= 6 ? 2: (month <= 9 ? 3 : 4));

        switch (typeTime) {
            case "year":
            case "prev_year":
                financeGoals = getOrgFinanceGoalsForYear(orgFinanceGoalsList, venueId, userId, year);
                break;
            case "period":
            case "prev_period":
                financeGoals = getOrgFinanceGoalsForPeriod(orgFinanceGoalsList, venueId, userId, year, period);
                break;
            case "month":
            case "prev_month":
                financeGoals = getOrgFinanceGoalsForMonth(orgFinanceGoalsList, venueId, userId, year, month);
                break;
            case "week":
            case "prev_week":
                financeGoals = getOrgFinanceGoalsForWeek(orgFinanceGoalsList, venueId, userId, year, month);
                break;
            case "day":
            case "prev_day":
                financeGoals = getOrgFinanceGoalsForDay(orgFinanceGoalsList, venueId, userId, year, month);
                break;
        }

        return financeGoals;
    }

    // 人/馆年目标
    private Integer[] getOrgFinanceGoalsForYear(List<OrgFinanceGoals> orgFinanceGoalsList, int venueId, int userId, int year) {
        int pipelineTarget = 0;
        int pipelineChallenge = 0;
        int incomeTarget = 0;
        int incomeChallenge = 0;

        for (int i = 1; i <= 12; i++) {
            Integer[] goals =  getOrgFinanceGoalsForMonth(orgFinanceGoalsList, venueId, userId, year, i);

            pipelineTarget += goals[0];
            pipelineChallenge += goals[1];
            incomeTarget += goals[2];
            incomeChallenge += goals[3];
        }

        return new Integer[] {pipelineTarget, pipelineChallenge, incomeTarget, incomeChallenge};
    }

    // 人/馆季目标
    private Integer[] getOrgFinanceGoalsForPeriod(List<OrgFinanceGoals> orgFinanceGoalsList, int venueId, int userId, int year, int period) {
        int pipelineTarget = 0;
        int pipelineChallenge = 0;
        int incomeTarget = 0;
        int incomeChallenge = 0;

        Integer[] goals1, goals2, goals3;
        switch (period) {
            case 1:
                goals1 =  getOrgFinanceGoalsForMonth(orgFinanceGoalsList, venueId, userId, year, 1);
                goals2 =  getOrgFinanceGoalsForMonth(orgFinanceGoalsList, venueId, userId, year, 2);
                goals3 =  getOrgFinanceGoalsForMonth(orgFinanceGoalsList, venueId, userId, year, 3);
                break;
            case 2:
                goals1 =  getOrgFinanceGoalsForMonth(orgFinanceGoalsList, venueId, userId, year, 4);
                goals2 =  getOrgFinanceGoalsForMonth(orgFinanceGoalsList, venueId, userId, year, 5);
                goals3 =  getOrgFinanceGoalsForMonth(orgFinanceGoalsList, venueId, userId, year, 6);
                break;
            case 3:
                goals1 =  getOrgFinanceGoalsForMonth(orgFinanceGoalsList, venueId, userId, year, 7);
                goals2 =  getOrgFinanceGoalsForMonth(orgFinanceGoalsList, venueId, userId, year, 8);
                goals3 =  getOrgFinanceGoalsForMonth(orgFinanceGoalsList, venueId, userId, year, 9);
                break;
            default:
                goals1 =  getOrgFinanceGoalsForMonth(orgFinanceGoalsList, venueId, userId, year, 10);
                goals2 =  getOrgFinanceGoalsForMonth(orgFinanceGoalsList, venueId, userId, year, 11);
                goals3 =  getOrgFinanceGoalsForMonth(orgFinanceGoalsList, venueId, userId, year, 12);
        }

        pipelineTarget += goals1[0] + goals2[0] + goals3[0];
        pipelineChallenge += goals1[1] + goals2[1] + goals3[1];
        incomeTarget += goals1[2] + goals2[2] + goals3[2];
        incomeChallenge += goals1[3] + goals2[3] + goals3[3];

        return new Integer[] {pipelineTarget, pipelineChallenge, incomeTarget, incomeChallenge};
    }

    // 人/馆月目标
    private Integer[] getOrgFinanceGoalsForMonth(List<OrgFinanceGoals> orgFinanceGoalsList, int venueId, int userId, int year, int month) {
        int pipelineTarget = 0;
        int pipelineChallenge = 0;
        int incomeTarget = 0;
        int incomeChallenge = 0;

        for (OrgFinanceGoals orgFinanceGoals : orgFinanceGoalsList) {

            OrgFinanceGoals orgFinanceGoalsTmp = null;
            if (userId > 0 && venueId > 0) {
                if (orgFinanceGoals.getUserId() == userId && orgFinanceGoals.getVenueId() == venueId
                        && orgFinanceGoals.getYear() == year && orgFinanceGoals.getMonth() == month) {
                    orgFinanceGoalsTmp = orgFinanceGoals;
                }
            }
            else if (venueId > 0) {
                if (orgFinanceGoals.getVenueId() == venueId
                        && orgFinanceGoals.getYear() == year && orgFinanceGoals.getMonth() == month) {
                    orgFinanceGoalsTmp = orgFinanceGoals;
                }
            }

            if (orgFinanceGoalsTmp != null) {
                if (orgFinanceGoalsTmp.getGoalType() == BusinessGoalTypeEnum.FLOW.getCode()) {
                    pipelineTarget += orgFinanceGoalsTmp.getMinValue();
                    pipelineChallenge += orgFinanceGoalsTmp.getMaxValue();
                } else {
                    incomeTarget += orgFinanceGoalsTmp.getMinValue();
                    incomeChallenge += orgFinanceGoalsTmp.getMaxValue();
                }
            }
        }

        return new Integer[] {pipelineTarget, pipelineChallenge, incomeTarget, incomeChallenge};
    }

    // 人/馆周目标
    private Integer[] getOrgFinanceGoalsForWeek(List<OrgFinanceGoals> orgFinanceGoalsList, int venueId, int userId, int year, int month) {
        int pipelineTarget = 0;
        int pipelineChallenge = 0;
        int incomeTarget = 0;
        int incomeChallenge = 0;

        Integer[] goals = getOrgFinanceGoalsForMonth(orgFinanceGoalsList, venueId, userId, year, month);
        int monthDay = DateUtil.getMonthDay(year, month);

        pipelineTarget = goals[0] / monthDay * 7;
        pipelineChallenge = goals[1] / monthDay * 7;
        incomeTarget = goals[2] / monthDay * 7;
        incomeChallenge = goals[3] / monthDay * 7;

        return new Integer[] {pipelineTarget, pipelineChallenge, incomeTarget, incomeChallenge};
    }

    // 人/馆日目标
    private Integer[] getOrgFinanceGoalsForDay(List<OrgFinanceGoals> orgFinanceGoalsList, int venueId, int userId, int year, int month) {
        int pipelineTarget = 0;
        int pipelineChallenge = 0;
        int incomeTarget = 0;
        int incomeChallenge = 0;

        Integer[] goals = getOrgFinanceGoalsForMonth(orgFinanceGoalsList, venueId, userId, year, month);
        int monthDay = DateUtil.getMonthDay(year, month);

        pipelineTarget = goals[0] / monthDay;
        pipelineChallenge = goals[1] / monthDay;
        incomeTarget = goals[2] / monthDay;
        incomeChallenge = goals[3] / monthDay;

        return new Integer[] {pipelineTarget, pipelineChallenge, incomeTarget, incomeChallenge};
    }

    // 人/馆/业务类型 汇总数据
    private List<OrgFinanceDataResponse> formatOrgFinanceDataSummary(List<OrgFinanceData> orgFinanceDataList, List<OrgFinanceVenues> orgFinanceVenuesList, List<OrgFinanceUsers> orgFinanceUsersList) {
        return formatOrgFinanceDataChannelSummary(orgFinanceDataList, orgFinanceVenuesList, orgFinanceUsersList, false);
    }

    // 人/馆/业务类型/渠道类型 汇总数据
    private List<OrgFinanceDataResponse> formatOrgFinanceDataChannelSummary(List<OrgFinanceData> orgFinanceDataList, List<OrgFinanceVenues> orgFinanceVenuesList, List<OrgFinanceUsers> orgFinanceUsersList, boolean isTotalChannel) {
        List<OrgFinanceDataResponse> orgFinanceDataResponseList = new ArrayList<>();

        Map checkTrainingData = new HashMap();
        Map checkVenueData = new HashMap();
        String uniqueKey;
        int i = 0, j = 0;
        for (OrgFinanceData orgFinanceData : orgFinanceDataList) {
            OrgFinanceDataResponse orgFinanceDataResponse;

            if (!isTotalChannel) {
                uniqueKey = "user_" + orgFinanceData.getVenueId() + "_" + orgFinanceData.getUserId() + "_" + orgFinanceData.getBusinessType();
            } else {
                uniqueKey = "user_" + orgFinanceData.getVenueId() + "_" + orgFinanceData.getUserId() + "_" + orgFinanceData.getBusinessType() + "_" + orgFinanceData.getChannelType();
            }

            if (orgFinanceData.getBusinessType() == BusinessTypeEnum.TRAINING_YOUNG.getCode()) {
                if (checkTrainingData.get(uniqueKey) == null) {
                    checkTrainingData.put(uniqueKey, i);

                    orgFinanceDataResponse = new OrgFinanceDataResponse();

                    // 场馆
                    orgFinanceDataResponse.setVenueId(orgFinanceData.getVenueId());
                    orgFinanceDataResponse.setVenueName(getVenueName(orgFinanceData.getVenueId(), orgFinanceVenuesList));

                    // 用户
                    orgFinanceDataResponse.setUserId(orgFinanceData.getUserId());
                    orgFinanceDataResponse.setRealName(getRealName(orgFinanceData.getUserId(), orgFinanceUsersList));

                    // 渠道
                    orgFinanceDataResponse.setChannelType(orgFinanceData.getChannelType());

                    // 业务类型
                    orgFinanceDataResponse.setBusinessType(orgFinanceData.getBusinessType());

                    // 流水
                    orgFinanceDataResponse.setPipelineValue(orgFinanceData.getPipelineValue());
                    //orgFinanceDataResponse.setPipelineTarget(orgFinanceData.getPipelineTarget());
                    //orgFinanceDataResponse.setPipelineChallenge(orgFinanceData.getPipelineChallenge());

                    // 收入
                    orgFinanceDataResponse.setIncomeValue(orgFinanceData.getIncomeValue());
                    //orgFinanceDataResponse.setIncomeTarget(orgFinanceData.getIncomeTarget());
                    //orgFinanceDataResponse.setIncomeChallenge(orgFinanceData.getIncomeChallenge());

                    // 人数
                    orgFinanceDataResponse.setRegisterCount(orgFinanceData.getRegisterCount());
                    orgFinanceDataResponse.setClassCount(orgFinanceData.getClassCount());

                    // 体验
                    orgFinanceDataResponse.setAccessCount(orgFinanceData.getAccessCount());
                    orgFinanceDataResponse.setBusinessCount(orgFinanceData.getBusinessCount());

                    orgFinanceDataResponseList.add(orgFinanceDataResponse);
                    i++;
                } else {
                    int iIndex = Integer.parseInt(checkTrainingData.get(uniqueKey).toString());

                    orgFinanceDataResponse = orgFinanceDataResponseList.get(iIndex);

                    // 流水
                    orgFinanceDataResponse.setPipelineValue(orgFinanceDataResponse.getPipelineValue() + orgFinanceData.getPipelineValue());
                    //orgFinanceDataResponse.setPipelineTarget(orgFinanceDataResponse.getPipelineTarget() + orgFinanceData.getPipelineTarget());
                    //orgFinanceDataResponse.setPipelineChallenge(orgFinanceDataResponse.getPipelineChallenge() + orgFinanceData.getPipelineChallenge());

                    // 收入
                    orgFinanceDataResponse.setIncomeValue(orgFinanceDataResponse.getIncomeValue() + orgFinanceData.getIncomeValue());
                    //orgFinanceDataResponse.setIncomeTarget(orgFinanceDataResponse.getIncomeTarget() + orgFinanceData.getIncomeTarget());
                    //orgFinanceDataResponse.setIncomeChallenge(orgFinanceDataResponse.getIncomeChallenge() + orgFinanceData.getIncomeChallenge());

                    // 人数
                    orgFinanceDataResponse.setRegisterCount(orgFinanceDataResponse.getRegisterCount() + orgFinanceData.getRegisterCount());
                    orgFinanceDataResponse.setClassCount(orgFinanceDataResponse.getClassCount() + orgFinanceData.getClassCount());

                    // 体验
                    orgFinanceDataResponse.setAccessCount(orgFinanceDataResponse.getRegisterCount() + orgFinanceData.getAccessCount());
                    orgFinanceDataResponse.setBusinessCount(orgFinanceDataResponse.getClassCount() + orgFinanceData.getBusinessCount());
                }
            } else {
                if (checkTrainingData.get(uniqueKey) == null) {
                    checkVenueData.put(uniqueKey, j);

                    orgFinanceDataResponse = new OrgFinanceDataResponse();

                    // 场馆
                    orgFinanceDataResponse.setVenueId(orgFinanceData.getVenueId());
                    orgFinanceDataResponse.setVenueName(getVenueName(orgFinanceData.getVenueId(), orgFinanceVenuesList));

                    // 用户
                    orgFinanceDataResponse.setUserId(orgFinanceData.getUserId());
                    orgFinanceDataResponse.setRealName(getRealName(orgFinanceData.getUserId(), orgFinanceUsersList));

                    // 渠道
                    orgFinanceDataResponse.setChannelType(orgFinanceData.getChannelType());

                    // 业务类型
                    orgFinanceDataResponse.setBusinessType(orgFinanceData.getBusinessType());

                    // 流水
                    orgFinanceDataResponse.setPipelineValue(orgFinanceData.getPipelineValue());
                    //orgFinanceDataResponse.setPipelineTarget(orgFinanceData.getPipelineTarget());
                    //orgFinanceDataResponse.setPipelineChallenge(orgFinanceData.getPipelineChallenge());

                    // 收入
                    orgFinanceDataResponse.setIncomeValue(orgFinanceData.getIncomeValue());
                    //orgFinanceDataResponse.setIncomeTarget(orgFinanceData.getIncomeTarget());
                    //orgFinanceDataResponse.setIncomeChallenge(orgFinanceData.getIncomeChallenge());

                    // 忙时段
                    orgFinanceDataResponse.setHotCount(orgFinanceData.getHotCount());
                    orgFinanceDataResponse.setHotTotalCount(orgFinanceData.getHotTotalCount());

                    // 闲时段
                    orgFinanceDataResponse.setNullCount(orgFinanceData.getNullCount());
                    orgFinanceDataResponse.setNullTotalCount(orgFinanceData.getNullTotalCount());

                    orgFinanceDataResponseList.add(orgFinanceDataResponse);
                    j++;
                } else {
                    int jIndex = Integer.parseInt(checkVenueData.get(uniqueKey).toString());

                    orgFinanceDataResponse = orgFinanceDataResponseList.get(jIndex);

                    // 流水
                    orgFinanceDataResponse.setPipelineValue(orgFinanceDataResponse.getPipelineValue() + orgFinanceData.getPipelineValue());
                    //orgFinanceDataResponse.setPipelineTarget(orgFinanceDataResponse.getPipelineTarget() + orgFinanceData.getPipelineTarget());
                    //orgFinanceDataResponse.setPipelineChallenge(orgFinanceDataResponse.getPipelineChallenge() + orgFinanceData.getPipelineChallenge());

                    // 收入
                    orgFinanceDataResponse.setIncomeValue(orgFinanceDataResponse.getIncomeValue() + orgFinanceData.getIncomeValue());
                    //orgFinanceDataResponse.setIncomeTarget(orgFinanceDataResponse.getIncomeTarget() + orgFinanceData.getIncomeTarget());
                    //orgFinanceDataResponse.setIncomeChallenge(orgFinanceDataResponse.getIncomeChallenge() + orgFinanceData.getIncomeChallenge());

                    // 忙时段
                    orgFinanceDataResponse.setHotCount(orgFinanceDataResponse.getHotCount() + orgFinanceData.getHotCount());
                    orgFinanceDataResponse.setHotTotalCount(orgFinanceDataResponse.getHotTotalCount() + orgFinanceData.getHotTotalCount());

                    // 闲时段
                    orgFinanceDataResponse.setNullCount(orgFinanceDataResponse.getNullCount() + orgFinanceData.getNullCount());
                    orgFinanceDataResponse.setNullTotalCount(orgFinanceDataResponse.getNullTotalCount() + orgFinanceData.getNullTotalCount());
                }
            }
        }

        return orgFinanceDataResponseList;
    }

    /**
     * 导出运营报表
     * @return
     */
    @RequestMapping(value = "/summary/export")
    @ResponseBody
    public void exportSummary(OrgFinanceLogRequest orgFinanceLogRequest, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (orgFinanceLogRequest.getTypeTime() == null) {
            orgFinanceLogRequest.setTypeTime("month");
        }

        List<OrgFinanceVenues> orgFinanceVenuesList = orgFinanceVenuesService.queryOrgFinanceVenuesList();

        int count = orgFinanceUsersService.queryOrgFinanceUsersCount();
        List<OrgFinanceUsers> orgFinanceUsersList = orgFinanceUsersService.queryOrgFinanceUsersList(0, count);

        String typePeriod[] = getStartEndTime(orgFinanceLogRequest.getTypeTime());
        String startTime = typePeriod[0].substring(0, 10);
        String endTime = typePeriod[1].substring(0, 10);



        int totalForTraining = orgFinanceDataService.queryOrgFinanceDataCount(BusinessTypeEnum.TRAINING_YOUNG.getCode(), 0, 0, startTime, endTime);
        List<OrgFinanceData> orgFinanceDataListForTraining = orgFinanceDataService.queryOrgFinanceDataList(BusinessTypeEnum.TRAINING_YOUNG.getCode(), 0, 0, startTime, endTime, 0, totalForTraining);

        HSSFWorkbook trainingBook = getTrainingHSSFWorkbook(orgFinanceDataListForTraining, orgFinanceVenuesList, orgFinanceUsersList, typePeriod, null);

        int totalForVenue = orgFinanceDataService.queryOrgFinanceDataCount(BusinessTypeEnum.VENUE_LEASE.getCode(), 0, 0, startTime, endTime);
        List<OrgFinanceData> orgFinanceDataListForVenue = orgFinanceDataService.queryOrgFinanceDataList(BusinessTypeEnum.VENUE_LEASE.getCode(), 0, 0, startTime, endTime, 0, totalForVenue);

        HSSFWorkbook venueBook = getVenueHSSFWorkbook(orgFinanceDataListForVenue, orgFinanceVenuesList, orgFinanceUsersList, typePeriod, trainingBook);

        // 文件名
        String fileName = "运营财务报表_" + typePeriod[2] + "_" + System.currentTimeMillis() + ".xls";

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

    // 青训数据导出
    private HSSFWorkbook getTrainingHSSFWorkbook(List<OrgFinanceData> orgFinanceDataList, List<OrgFinanceVenues> orgFinanceVenuesList, List<OrgFinanceUsers> orgFinanceUsersList, String[] typePeriod, HSSFWorkbook hssfWorkbook) throws Exception {
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

        List<OrgFinanceDataResponse> orgFinanceDataResponseList = formatOrgFinanceDataSummary(orgFinanceDataList, orgFinanceVenuesList, orgFinanceUsersList);
        List<OrgFinanceDataResponse> orgFinanceDataResponseChannelList = formatOrgFinanceDataChannelSummary(orgFinanceDataList, orgFinanceVenuesList, orgFinanceUsersList, true);

        List<OrgFinanceGoals> orgFinanceGoalsList = orgFinanceGoalsService.queryOrgFinanceGoalsList(BusinessTypeEnum.TRAINING_YOUNG.getCode(), 0, 0, 0, 0);

        int trainingCount = 0;
        for (OrgFinanceDataResponse orgFinanceDataResponse : orgFinanceDataResponseList) {
            String startTime = typePeriod[0].substring(0, 10);
            Integer[] financeGoals = getOrgFinanceGoalsForDate(orgFinanceGoalsList, orgFinanceDataResponse.getVenueId(), orgFinanceDataResponse.getUserId(), startTime, typePeriod[2]);

            if (financeGoals != null) {
                orgFinanceDataResponse.setPipelineTarget(financeGoals[0]);
                orgFinanceDataResponse.setPipelineChallenge(financeGoals[1]);
                orgFinanceDataResponse.setIncomeTarget(financeGoals[2]);
                orgFinanceDataResponse.setIncomeChallenge(financeGoals[3]);
            }

            List<OrgFinanceDataResponse> orgFinanceDataResponseChannelList1 = new ArrayList<>();
            for (OrgFinanceDataResponse orgFinanceDataResponseChannel : orgFinanceDataResponseChannelList) {
                if (orgFinanceDataResponse.getVenueId().intValue() == orgFinanceDataResponseChannel.getVenueId()
                        && orgFinanceDataResponse.getUserId().intValue() == orgFinanceDataResponseChannel.getUserId()
                        && orgFinanceDataResponse.getBusinessType().intValue() == orgFinanceDataResponseChannel.getBusinessType()) {

                    orgFinanceDataResponseChannelList1.add(orgFinanceDataResponseChannel);
                }
            }
            orgFinanceDataResponse.setOrgFinanceDataResponseChannelList(orgFinanceDataResponseChannelList1);

            trainingCount++;
        }

        String [][]trainingContent = new String[trainingCount][trainingTitle.length];
        int j = 0;
        for (OrgFinanceDataResponse orgFinanceDataResponse : orgFinanceDataResponseList) {
            trainingContent[j][0] = orgFinanceDataResponse.getVenueName();
            trainingContent[j][1] = orgFinanceDataResponse.getRealName();
            trainingContent[j][2] = orgFinanceDataResponse.getPipelineValue().toString();
            trainingContent[j][3] = orgFinanceDataResponse.getPipelineTarget().toString() + "，" + StrUtil.roundKeepTwo(orgFinanceDataResponse.getPipelineTargetPercent() * 100) + "%";
            trainingContent[j][4] = orgFinanceDataResponse.getPipelineChallenge().toString() + "，" + StrUtil.roundKeepTwo(orgFinanceDataResponse.getPipelineChallengePercent() * 100) + "%";
            trainingContent[j][5] = orgFinanceDataResponse.getIncomeValue().toString();
            trainingContent[j][6] = orgFinanceDataResponse.getIncomeTarget().toString() + "，" + StrUtil.roundKeepTwo(orgFinanceDataResponse.getIncomeTargetPercent() * 100) + "%";
            trainingContent[j][7] = orgFinanceDataResponse.getIncomeChallenge().toString() + "，" + StrUtil.roundKeepTwo(orgFinanceDataResponse.getIncomeChallengePercent() * 100) + "%";
            trainingContent[j][8] = orgFinanceDataResponse.getRegisterCount().toString();
            trainingContent[j][9] = orgFinanceDataResponse.getClassCount().toString();

            Integer accessCountPerson = 0, businessCountPerson = 0;
            trainingContent[j][10] = "0";
            trainingContent[j][11] = "0";
            trainingContent[j][12] = "0.00%";

            Integer accessCountFriend = 0, businessCountFriend = 0;
            trainingContent[j][13] = "0";
            trainingContent[j][14] = "0";
            trainingContent[j][15] = "0.00%";

            Integer accessCountMember = 0, businessCountMember = 0;

            trainingContent[j][17] = "0";
            trainingContent[j][18] = "0";
            trainingContent[j][19] = "0.00%";
            for (OrgFinanceDataResponse orgFinanceDataResponse1 : orgFinanceDataResponse.getOrgFinanceDataResponseChannelList()) {
                if (orgFinanceDataResponse1.getChannelType() == BusinessChannelTypeEnum.CHANNEL_PERSON.getCode()) {
                    accessCountPerson = orgFinanceDataResponse1.getAccessCount();
                    businessCountPerson = orgFinanceDataResponse1.getBusinessCount();

                    trainingContent[j][10] = orgFinanceDataResponse1.getAccessCount() + "";
                    trainingContent[j][11] = orgFinanceDataResponse1.getBusinessCount() + "";
                    trainingContent[j][12] = StrUtil.roundKeepTwo(orgFinanceDataResponse1.getBusinessCountPercent() * 100) + "%";
                }
                if (orgFinanceDataResponse1.getChannelType() == BusinessChannelTypeEnum.CHANNEL_FRIEND.getCode()) {
                    accessCountFriend = orgFinanceDataResponse1.getAccessCount();
                    businessCountFriend = orgFinanceDataResponse1.getBusinessCount();

                    trainingContent[j][13] = orgFinanceDataResponse1.getAccessCount() + "";
                    trainingContent[j][14] = orgFinanceDataResponse1.getBusinessCount() + "";
                    trainingContent[j][15] = StrUtil.roundKeepTwo(orgFinanceDataResponse1.getBusinessCountPercent() * 100) + "%";
                }
                if (orgFinanceDataResponse1.getChannelType() == BusinessChannelTypeEnum.CHANNEL_MEMBER.getCode()) {
                    accessCountMember = orgFinanceDataResponse1.getAccessCount();
                    businessCountMember = orgFinanceDataResponse1.getBusinessCount();
                }
                if (orgFinanceDataResponse1.getChannelType() == BusinessChannelTypeEnum.CHANNEL_COMPANY.getCode()) {
                    trainingContent[j][17] = orgFinanceDataResponse1.getAccessCount() + "";
                    trainingContent[j][18] = orgFinanceDataResponse1.getBusinessCount() + "";
                    trainingContent[j][19] = StrUtil.roundKeepTwo(orgFinanceDataResponse1.getBusinessCountPercent() * 100) + "%";
                }
            }

            trainingContent[j][16] = "0.00%";
            if ((accessCountFriend + accessCountMember + accessCountPerson) > 0) {
                trainingContent[j][16] = StrUtil.roundKeepTwo(((double)(businessCountPerson + businessCountFriend + businessCountMember) / (accessCountFriend + accessCountMember + accessCountPerson)) * 100) + "%";
            }

            trainingContent[j][20] = StrUtil.roundKeepTwo(orgFinanceDataResponse.getBusinessCountPercent() * 100) + "%";

            j++;
        }

        return ExcelUtil.getHSSFWorkbook(trainingSheetName, trainingTitle, trainingContent, hssfWorkbook);
    }

    // 场租数据导出
    private HSSFWorkbook getVenueHSSFWorkbook(List<OrgFinanceData> orgFinanceDataList, List<OrgFinanceVenues> orgFinanceVenuesList, List<OrgFinanceUsers> orgFinanceUsersList, String[] typePeriod, HSSFWorkbook hssfWorkbook) throws Exception {
        // 场租标题
        String[] venueTitle = {
                "基地名字", "姓名",
                "流水完成", "流水目标", "流水挑战目标",
                "确认收入完成", "确认收入目标", "确认收入挑战目标",
                "闲时段数", "闲时段总数",
                "忙时段数", "忙时段总数"};
        String venueSheetName = "场地租赁";

        List<OrgFinanceDataResponse> orgFinanceDataResponseList = formatOrgFinanceDataSummary(
                orgFinanceDataList, orgFinanceVenuesList, orgFinanceUsersList);
        List<OrgFinanceDataResponse> orgFinanceDataResponseChannelList = formatOrgFinanceDataChannelSummary(
                orgFinanceDataList, orgFinanceVenuesList, orgFinanceUsersList, true);

        List<OrgFinanceGoals> orgFinanceGoalsList = orgFinanceGoalsService.queryOrgFinanceGoalsList(BusinessTypeEnum.VENUE_LEASE.getCode(), 0, 0, 0, 0);

        int venueCount = 0;
        for (OrgFinanceDataResponse orgFinanceDataResponse : orgFinanceDataResponseList) {
            String startTime = typePeriod[0].substring(0, 10);
            Integer[] financeGoals = getOrgFinanceGoalsForDate(orgFinanceGoalsList, orgFinanceDataResponse.getVenueId(), orgFinanceDataResponse.getUserId(), startTime, typePeriod[2]);

            if (financeGoals != null) {
                orgFinanceDataResponse.setPipelineTarget(financeGoals[0]);
                orgFinanceDataResponse.setPipelineChallenge(financeGoals[1]);
                orgFinanceDataResponse.setIncomeTarget(financeGoals[2]);
                orgFinanceDataResponse.setIncomeChallenge(financeGoals[3]);
            }

            List<OrgFinanceDataResponse> orgFinanceDataResponseChannelList1 = new ArrayList<>();
            for (OrgFinanceDataResponse orgFinanceDataResponseChannel : orgFinanceDataResponseChannelList) {
                if (orgFinanceDataResponse.getVenueId().intValue() == orgFinanceDataResponseChannel.getVenueId()
                        && orgFinanceDataResponse.getUserId().intValue() == orgFinanceDataResponseChannel.getUserId()
                        && orgFinanceDataResponse.getBusinessType().intValue() == orgFinanceDataResponseChannel.getBusinessType()) {

                    orgFinanceDataResponseChannelList1.add(orgFinanceDataResponseChannel);
                }
            }
            orgFinanceDataResponse.setOrgFinanceDataResponseChannelList(orgFinanceDataResponseChannelList1);

            venueCount++;
        }

        String [][]venueContent = new String[venueCount][venueTitle.length];
        int i = 0;
        for (OrgFinanceDataResponse orgFinanceDataResponse : orgFinanceDataResponseList) {
            venueContent[i][0] = orgFinanceDataResponse.getVenueName();
            venueContent[i][1] = orgFinanceDataResponse.getRealName();
            venueContent[i][2] = orgFinanceDataResponse.getPipelineValue().toString();
            venueContent[i][3] = orgFinanceDataResponse.getPipelineTarget().toString() + "，" + StrUtil.roundKeepTwo(orgFinanceDataResponse.getPipelineTargetPercent() * 100) + "%";
            venueContent[i][4] = orgFinanceDataResponse.getPipelineChallenge().toString() + "，" + StrUtil.roundKeepTwo(orgFinanceDataResponse.getPipelineChallengePercent() * 100) + "%";
            venueContent[i][5] = orgFinanceDataResponse.getIncomeValue().toString();
            venueContent[i][6] = orgFinanceDataResponse.getIncomeTarget().toString() + "，" + StrUtil.roundKeepTwo(orgFinanceDataResponse.getIncomeTargetPercent() * 100) + "%";
            venueContent[i][7] = orgFinanceDataResponse.getIncomeChallenge().toString() + "，" + StrUtil.roundKeepTwo(orgFinanceDataResponse.getIncomeChallengePercent() * 100) + "%";
            venueContent[i][8] = orgFinanceDataResponse.getNullCount().toString();
            venueContent[i][9] = orgFinanceDataResponse.getNullTotalCount() + "，" + StrUtil.roundKeepTwo(orgFinanceDataResponse.getNullCountPercent() * 100) + "%";
            venueContent[i][10] = orgFinanceDataResponse.getHotCount().toString();
            venueContent[i][11] = orgFinanceDataResponse.getHotTotalCount() + "，" + StrUtil.roundKeepTwo(orgFinanceDataResponse.getHotCountPercent() * 100) + "%";

            i++;
        }

        return ExcelUtil.getHSSFWorkbook(venueSheetName, venueTitle, venueContent, hssfWorkbook);
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

}
