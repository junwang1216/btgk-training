package com.training.in.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.bean.ResponseBean;
import com.training.core.common.constant.IPlatformConstant;
import com.training.core.common.enums.StatusEnum;
import com.training.core.common.exception.MessageException;
import com.training.core.common.util.DataCryptUtil;
import com.training.core.common.util.DateUtil;
import com.training.core.common.util.Page;
import com.training.core.repo.po.OrgFinanceUsers;
import com.training.core.repo.po.OrgFinanceVenues;
import com.training.core.service.OrgFinanceUsersService;
import com.training.core.service.OrgFinanceVenuesService;
import com.training.in.request.OrgFinanceLogRequest;
import org.springframework.stereotype.Controller;
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
 * Created by wangjun on 2018/5/7.
 */
@Controller
@RequestMapping("/admin/finance")
public class FinanceController extends BaseController {

    @Resource
    private OrgFinanceVenuesService orgFinanceVenuesService;

    @Resource
    private OrgFinanceUsersService orgFinanceUsersService;

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

        return setModelAndView(modelAndView);
    }

    @Desc("个人业绩统计")
    @RequestMapping(value = "/performance", method = RequestMethod.GET)
    public ModelAndView renderFinancePerformance(OrgFinanceLogRequest orgFinanceLogRequest) {

        ModelAndView modelAndView = new ModelAndView("Finance/Performance");

        return setModelAndView(modelAndView);
    }
}
