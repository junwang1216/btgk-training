package com.training.in.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.annotation.NotProtected;
import com.training.core.common.bean.ResponseBean;
import com.training.core.common.constant.IPlatformConstant;
import com.training.core.common.exception.MessageException;
import com.training.core.common.util.DateUtil;
import com.training.core.repo.po.OrgBalanceSettings;
import com.training.core.service.OrgBalanceSettingsService;
import com.training.in.request.OrgBalanceSettingsRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
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
    private OrgBalanceSettingsService orgBalanceSettingsService;

    private ModelAndView setModelAndView(ModelAndView modelAndView) {
        return modelAndView.addObject("Admin", super.getRequest().getSession().getAttribute(IPlatformConstant.LOGIN_USER));
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
