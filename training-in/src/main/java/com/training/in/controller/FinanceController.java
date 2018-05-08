package com.training.in.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.bean.ResponseBean;
import com.training.core.common.constant.IPlatformConstant;
import com.training.core.common.enums.BusinessGoalTypeEnum;
import com.training.core.common.enums.BusinessTypeEnum;
import com.training.core.common.enums.StatusEnum;
import com.training.core.common.exception.MessageException;
import com.training.core.common.util.*;
import com.training.core.repo.po.OrgFinanceGoals;
import com.training.core.repo.po.OrgFinanceUsers;
import com.training.core.repo.po.OrgFinanceVenues;
import com.training.core.service.OrgFinanceGoalsService;
import com.training.core.service.OrgFinanceUsersService;
import com.training.core.service.OrgFinanceVenuesService;
import com.training.in.request.OrgFinanceLogRequest;
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
        modelAndView.addObject("orgFinanceVenuesList", orgFinanceVenuesList);

        if (orgFinanceLogRequest.getVenueId() == 0) {
            orgFinanceLogRequest.setVenueId(orgFinanceVenuesList.get(0).getId());
        }

        int []year = {
                Integer.parseInt(DateUtil.getAddYear(-2)), Integer.parseInt(DateUtil.getAddYear(-1)),
                Integer.parseInt(DateUtil.getAddYear(0)),
                Integer.parseInt(DateUtil.getAddYear(1)), Integer.parseInt(DateUtil.getAddYear(2))
        };
        modelAndView.addObject("yearList", year);
        if (orgFinanceLogRequest.getYear() == 0 || Arrays.binarySearch(year, orgFinanceLogRequest.getYear()) < 0) {
            orgFinanceLogRequest.setYear(year[2]);
        }

        modelAndView.addObject("conditions", orgFinanceLogRequest);

        List<OrgFinanceGoals> orgFinanceGoalsList = orgFinanceGoalsService.queryOrgFinanceGoalsList(orgFinanceLogRequest.getBusType(),
                orgFinanceLogRequest.getGoalType(), orgFinanceLogRequest.getVenueId(),
                orgFinanceLogRequest.getYear(), orgFinanceLogRequest.getUserId());

        List<OrgFinanceGoals> flowGoalsList = new ArrayList<>();
        List<OrgFinanceGoals> incomeGoalsList = new ArrayList<>();
        int fIndex = 0, iIndex = 0;
        for (OrgFinanceGoals orgFinanceGoals : orgFinanceGoalsList) {

            if (orgFinanceGoals.getGoalType() == BusinessGoalTypeEnum.FLOW.getCode()) {
                if (orgFinanceGoals.getMonth() != (fIndex + 1)) {
                    int count1 = orgFinanceGoals.getMonth() - (fIndex + 1);
                    for (int i = 0; i < count1; i++) {
                        OrgFinanceGoals orgFinanceGoals1 = new OrgFinanceGoals();
                        orgFinanceGoals1.setMonth(fIndex + 1);
                        orgFinanceGoals1.setUserId(orgFinanceGoals.getUserId());
                        orgFinanceGoals1.setMinValue(0);
                        orgFinanceGoals1.setMaxValue(0);
                        orgFinanceGoals1.setId(0);

                        flowGoalsList.add(orgFinanceGoals1);
                        fIndex++;
                    }
                }
                flowGoalsList.add(orgFinanceGoals);
                fIndex++;
            } else if (orgFinanceGoals.getBusType() == BusinessGoalTypeEnum.INCOME.getCode()) {
                if (orgFinanceGoals.getMonth() != (iIndex + 1)) {
                    int count2 = orgFinanceGoals.getMonth() - (iIndex + 1);
                    for (int j = 0; j < count2; j++) {
                        OrgFinanceGoals orgFinanceGoals2 = new OrgFinanceGoals();
                        orgFinanceGoals2.setMonth(iIndex + 1);
                        orgFinanceGoals2.setUserId(orgFinanceGoals.getUserId());
                        orgFinanceGoals2.setMinValue(0);
                        orgFinanceGoals2.setMaxValue(0);
                        orgFinanceGoals2.setId(0);

                        incomeGoalsList.add(orgFinanceGoals2);
                        iIndex++;
                    }
                }
                incomeGoalsList.add(orgFinanceGoals);
                iIndex++;
            }
        }
        modelAndView.addObject("flowGoalsList", flowGoalsList);
        modelAndView.addObject("incomeGoalsList", incomeGoalsList);

        int count = orgFinanceUsersService.queryOrgFinanceUsersCount();
        List<OrgFinanceUsers> orgFinanceUsersList = orgFinanceUsersService.queryOrgFinanceUsersList(0, count);

        List<OrgFinanceUsers> orgFinanceUsersList1 = new ArrayList<>();
        for (OrgFinanceUsers orgFinanceUsers : orgFinanceUsersList) {
            if (orgFinanceUsers.getVenueId() == orgFinanceLogRequest.getVenueId()) {
                orgFinanceUsersList1.add(orgFinanceUsers);
            }
        }
        modelAndView.addObject("orgFinanceUsersList", orgFinanceUsersList1);

        return setModelAndView(modelAndView);
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
                orgFinanceGoals.setCreateTime(DateUtil.getNowDate());
                orgFinanceGoals.setUpdateTime(DateUtil.getNowDate());
                result = orgFinanceGoalsService.addOrgFinanceGoals(orgFinanceGoals);
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

    @Desc("个人业绩统计")
    @RequestMapping(value = "/performance", method = RequestMethod.GET)
    public ModelAndView renderFinancePerformance(OrgFinanceLogRequest orgFinanceLogRequest) {

        ModelAndView modelAndView = new ModelAndView("Finance/Performance");

        return setModelAndView(modelAndView);
    }
}
