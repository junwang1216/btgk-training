package com.training.in.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.bean.ResponseBean;
import com.training.core.common.constant.IPlatformConstant;
import com.training.core.common.enums.BusinessChannelTypeEnum;
import com.training.core.common.enums.BusinessGoalTypeEnum;
import com.training.core.common.enums.BusinessTypeEnum;
import com.training.core.common.enums.StatusEnum;
import com.training.core.common.exception.MessageException;
import com.training.core.common.util.*;
import com.training.core.repo.po.*;
import com.training.core.service.*;
import com.training.in.request.OrgFinanceLogRequest;
import com.training.in.response.OrgFinanceDataResponse;
import com.training.in.response.OrgFinanceGoalsResponse;
import com.training.in.response.OrgFinanceResponse;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
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

    private ModelAndView setModelAndView(ModelAndView modelAndView) {
        return modelAndView.addObject("Admin", super.getRequest().getSession().getAttribute(IPlatformConstant.LOGIN_USER));
    }

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
        modelAndView.addObject("pageURL", "/admin/finance/relationship?venueId=");
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

            if (orgFinanceUsers.getId() != null) {
                orgFinanceUsers.setUpdateTime(DateUtil.getNowDate());
                result = orgFinanceUsersService.saveOrgFinanceUsers(orgFinanceUsers);
            }
            else {
                orgFinanceUsers.setCreateTime(DateUtil.getNowDate());
                orgFinanceUsers.setUpdateTime(DateUtil.getNowDate());

                orgFinanceUsers.setStatus(StatusEnum.STATUS_OK.getCode());
                orgFinanceUsers.setPassword(DataCryptUtil.encrypt("123456"));
                result = orgFinanceUsersService.addOrgFinanceUsers(orgFinanceUsers);
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

        if (orgFinanceLogRequest.getBusType() == 0) {
            orgFinanceLogRequest.setBusType(BusinessTypeEnum.TRAINING_YOUNG.getCode());
        }

        modelAndView.addObject("BusinessTypeEnumList", EnumUtils.getEnumList(BusinessTypeEnum.class));
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
        List<OrgFinanceUsers> orgFinanceUsersList1 = new ArrayList<>();
        String userName = "";
        for (OrgFinanceUsers orgFinanceUsers : orgFinanceUsersList) {
            if (orgFinanceUsers.getVenueId() == orgFinanceLogRequest.getVenueId()) {
                orgFinanceUsersList1.add(orgFinanceUsers);
            }
            if (orgFinanceLogRequest.getUserId() != 0 && orgFinanceUsers.getId() == orgFinanceLogRequest.getUserId()) {
                userName = orgFinanceUsers.getRealName();
            }
        }
        modelAndView.addObject("orgFinanceUsersList", orgFinanceUsersList1);

        List<OrgFinanceGoals> orgFinanceGoalsList = orgFinanceGoalsService.queryOrgFinanceGoalsList(orgFinanceLogRequest.getBusType(),
                orgFinanceLogRequest.getGoalType(), orgFinanceLogRequest.getVenueId(),
                orgFinanceLogRequest.getYear(), orgFinanceLogRequest.getUserId());

        List<OrgFinanceGoalsResponse> flowGoalsList = new ArrayList<>();
        List<OrgFinanceGoalsResponse> incomeGoalsList = new ArrayList<>();
        Map checkGoals = new HashMap();
        int fIndex = 0, iIndex = 0;
        for (OrgFinanceGoals orgFinanceGoals : orgFinanceGoalsList) {
            OrgFinanceGoalsResponse orgFinanceGoalsResponse = new OrgFinanceGoalsResponse();

            String checkKey = "goal_" +
                    orgFinanceGoals.getBusType().toString() + "_" +
                    orgFinanceGoals.getGoalType().toString() + "_" +
                    orgFinanceGoals.getVenueId().toString() + "_" +
                    orgFinanceGoals.getYear().toString() + "_" +
                    orgFinanceGoals.getMonth().toString();

            if (orgFinanceLogRequest.getUserId() != 0) {
                orgFinanceGoalsResponse.setBusType(orgFinanceGoals.getBusType());
                orgFinanceGoalsResponse.setGoalType(orgFinanceGoals.getGoalType());
                orgFinanceGoalsResponse.setVenueId(orgFinanceGoals.getVenueId());
                orgFinanceGoalsResponse.setUserId(orgFinanceGoals.getUserId());
                orgFinanceGoalsResponse.setUserName(userName);
                orgFinanceGoalsResponse.setYear(orgFinanceGoals.getYear());
                orgFinanceGoalsResponse.setMonth(orgFinanceGoals.getMonth());
                orgFinanceGoalsResponse.setMinValue(orgFinanceGoals.getMinValue());
                orgFinanceGoalsResponse.setMaxValue(orgFinanceGoals.getMaxValue());
                orgFinanceGoalsResponse.setId(orgFinanceGoals.getId());

                if (orgFinanceGoals.getGoalType() == BusinessGoalTypeEnum.FLOW.getCode()) {
                    flowGoalsList.add(orgFinanceGoalsResponse);
                } else if (orgFinanceGoals.getGoalType() == BusinessGoalTypeEnum.INCOME.getCode()) {
                    incomeGoalsList.add(orgFinanceGoalsResponse);
                }
            } else {
                if (checkGoals.get(checkKey) != null) {
                    int goalIndex = Integer.parseInt(checkGoals.get(checkKey).toString());

                    if (orgFinanceGoals.getGoalType() == BusinessGoalTypeEnum.FLOW.getCode()) {
                        flowGoalsList.get(goalIndex).setMaxValue(orgFinanceGoals.getMaxValue() + flowGoalsList.get(goalIndex).getMaxValue());
                        flowGoalsList.get(goalIndex).setMinValue(orgFinanceGoals.getMinValue() + flowGoalsList.get(goalIndex).getMinValue());
                    } else if (orgFinanceGoals.getGoalType() == BusinessGoalTypeEnum.INCOME.getCode()) {
                        incomeGoalsList.get(goalIndex).setMaxValue(orgFinanceGoals.getMaxValue() + incomeGoalsList.get(goalIndex).getMaxValue());
                        incomeGoalsList.get(goalIndex).setMinValue(orgFinanceGoals.getMinValue() + incomeGoalsList.get(goalIndex).getMinValue());
                    }
                } else {
                    orgFinanceGoalsResponse.setBusType(orgFinanceGoals.getBusType());
                    orgFinanceGoalsResponse.setGoalType(orgFinanceGoals.getGoalType());
                    orgFinanceGoalsResponse.setVenueId(orgFinanceGoals.getVenueId());
                    orgFinanceGoalsResponse.setUserId(-1);
                    orgFinanceGoalsResponse.setUserName(null);
                    orgFinanceGoalsResponse.setYear(orgFinanceGoals.getYear());
                    orgFinanceGoalsResponse.setMonth(orgFinanceGoals.getMonth());
                    orgFinanceGoalsResponse.setMinValue(orgFinanceGoals.getMinValue());
                    orgFinanceGoalsResponse.setMaxValue(orgFinanceGoals.getMaxValue());
                    orgFinanceGoalsResponse.setId(-1);

                    if (orgFinanceGoals.getGoalType() == BusinessGoalTypeEnum.FLOW.getCode()) {
                        checkGoals.put(checkKey, fIndex);

                        flowGoalsList.add(orgFinanceGoalsResponse);
                        fIndex++;
                    } else if (orgFinanceGoals.getGoalType() == BusinessGoalTypeEnum.INCOME.getCode()) {
                        checkGoals.put(checkKey, iIndex);

                        incomeGoalsList.add(orgFinanceGoalsResponse);
                        iIndex++;
                    }
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
                List<OrgFinanceGoals> orgFinanceGoalsList = orgFinanceGoalsService.queryOrgFinanceGoalsList(orgFinanceLogRequest.getBusType(),
                        orgFinanceLogRequest.getGoalType(), orgFinanceLogRequest.getVenueId(),
                        orgFinanceLogRequest.getYear(), 0);

                orgFinanceGoals.setBusType(orgFinanceLogRequest.getBusType());
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

                        orgFinanceGoals1.setBusType(orgFinanceGoals.getBusType());
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

    @Desc("运用财务编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView renderFinanceEdit(String businessNo) {

        ModelAndView modelAndView = new ModelAndView("Finance/Edit");

        OrgFinanceData orgFinanceData = new OrgFinanceData();
        if (businessNo != null) {
            orgFinanceData = orgFinanceDataService.getOrgFinanceData(businessNo);
        }
        modelAndView.addObject("orgFinanceData", orgFinanceData);

        modelAndView.addObject("BusinessTypeEnumList", EnumUtils.getEnumList(BusinessTypeEnum.class));
        modelAndView.addObject("BusinessChannelTypeEnumList", EnumUtils.getEnumList(BusinessChannelTypeEnum.class));

        List<OrgFinanceVenues> orgFinanceVenuesList = orgFinanceVenuesService.queryOrgFinanceVenuesList();
        modelAndView.addObject("orgFinanceVenuesList", orgFinanceVenuesList);

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

        modelAndView.addObject("BusinessTypeEnumList", EnumUtils.getEnumList(BusinessTypeEnum.class));
        modelAndView.addObject("BusinessTypeEnum", EnumUtils.getEnumMap(BusinessTypeEnum.class));

        modelAndView.addObject("conditions", orgFinanceLogRequest);

        List<OrgFinanceVenues> orgFinanceVenuesList = orgFinanceVenuesService.queryOrgFinanceVenuesList();
        modelAndView.addObject("orgFinanceVenuesList", orgFinanceVenuesList);

        int count = orgFinanceUsersService.queryOrgFinanceUsersCount();
        List<OrgFinanceUsers> orgFinanceUsersList = orgFinanceUsersService.queryOrgFinanceUsersList(0, count);

        int total = orgFinanceDataService.queryOrgFinanceDataCount(orgFinanceLogRequest.getBusType(),
        orgFinanceLogRequest.getVenueId(), 0, null, null);
        int start = orgFinanceLogRequest.getPage() < 1 ? 0 : orgFinanceLogRequest.getPage() - 1;
        int pageSize = 10;
        List<OrgFinanceData> orgFinanceDataList = orgFinanceDataService.queryOrgFinanceDataList(orgFinanceLogRequest.getBusType(),
                orgFinanceLogRequest.getVenueId(), 0, null, null,
                start * pageSize, pageSize);

        List<OrgFinanceDataResponse> orgFinanceDataResponseList = new ArrayList<>();
        for (OrgFinanceData orgFinanceData : orgFinanceDataList) {
            orgFinanceDataResponseList.add(formatOrgFinanceData(orgFinanceData, orgFinanceVenuesList, orgFinanceUsersList));
        }
        modelAndView.addObject("orgFinanceDataList", orgFinanceDataResponseList);

        Page page = new Page(pageSize, total);
        page.setPage(orgFinanceLogRequest.getPage());

        modelAndView.addObject("total", total);
        modelAndView.addObject("pageURL", "/admin/finance/log?busType=" + orgFinanceLogRequest.getBusType() +
                "&venueId=" + orgFinanceLogRequest.getVenueId() + "&userId=" + orgFinanceLogRequest.getUserId());
        modelAndView.addObject("page", page);

        return setModelAndView(modelAndView);
    }

    @Desc("个人业绩统计")
    @RequestMapping(value = "/performance", method = RequestMethod.GET)
    public ModelAndView renderFinancePerformance(OrgFinanceLogRequest orgFinanceLogRequest) {

        ModelAndView modelAndView = new ModelAndView("Finance/Performance");

        return setModelAndView(modelAndView);
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
            orgFinanceLogRequest.setTypeTime("year");
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
        String startTime = typePeriod[0];
        String endTime = typePeriod[1];

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
                String monthStart = year + "-" + (startTime.substring(5, 7)) + "-01";
                String checkDate = startTime.substring(10);
                String nextDate = monthStart;
                int wIndex = 1;
                boolean isChecked = false;
                while (!isChecked) {
                    List<String> weekDate = DateUtil.getWeekDate(nextDate);

                    for (int i = 0; i < weekDate.size(); i++) {
                        if (weekDate.get(i).compareTo(checkDate) == 0) {
                            isChecked = true;
                            break;
                        }
                    }

                    if (wIndex > 5) {
                        isChecked = true;
                    }

                    nextDate = DateUtil.getAddDay(nextDate, 7);
                    wIndex++;
                }

                financeGoals = getOrgFinanceGoalsForWeek(orgFinanceGoalsList, venueId, userId, year, month, wIndex);
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
                if (orgFinanceGoalsTmp.getBusType() == BusinessGoalTypeEnum.FLOW.getCode()) {
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
    private Integer[] getOrgFinanceGoalsForWeek(List<OrgFinanceGoals> orgFinanceGoalsList, int venueId, int userId, int year, int month, int week) throws Exception {
        int pipelineTarget = 0;
        int pipelineChallenge = 0;
        int incomeTarget = 0;
        int incomeChallenge = 0;

        Integer[] goals = getOrgFinanceGoalsForDay(orgFinanceGoalsList, venueId, userId, year, month);

        int monthDay = DateUtil.getMonthDay(year, month);
        String monthStr = ((month > 9) ? "" : "0") + month;
        String monthStart = year + "-" + monthStr + "-01";
        String monthEnd = year + "-" + monthStr + "-" + monthDay;

        String nextDate = monthStart;
        int wIndex = 1;
        while (true) {
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

            if (week == wIndex) {
                pipelineTarget += weekDate.size() * goals[0];
                pipelineChallenge += weekDate.size() * goals[1];
                incomeTarget += weekDate.size() * goals[2];
                incomeChallenge += weekDate.size() * goals[3];
                break;
            }

            if (weekDate.get(weekDate.size() - 1).compareTo(monthEnd) >= 0) {
                break;
            }

            nextDate = DateUtil.getAddDay(nextDate, 7);
            wIndex++;
        }

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

}
