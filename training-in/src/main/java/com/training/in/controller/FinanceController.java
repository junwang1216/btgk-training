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
import com.training.in.response.OrgFinanceGoalsResponse;
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
                    flowGoalsList.get(goalIndex).setMaxValue(orgFinanceGoals.getMaxValue() + flowGoalsList.get(goalIndex).getMaxValue());
                    flowGoalsList.get(goalIndex).setMinValue(orgFinanceGoals.getMinValue() + flowGoalsList.get(goalIndex).getMinValue());
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

    @Desc("运用财务编辑提交")
    @ResponseBody
    @RequestMapping(value = "/getFinance", method = RequestMethod.GET)
    public ResponseBean getFinance(String businessNo) {
        try {
            Map map = new HashMap();

            map.put("orgFinanceData", orgFinanceDataService.getOrgFinanceData(businessNo));

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

        return setModelAndView(modelAndView);
    }

    @Desc("个人业绩统计")
    @RequestMapping(value = "/performance", method = RequestMethod.GET)
    public ModelAndView renderFinancePerformance(OrgFinanceLogRequest orgFinanceLogRequest) {

        ModelAndView modelAndView = new ModelAndView("Finance/Performance");

        return setModelAndView(modelAndView);
    }
}
