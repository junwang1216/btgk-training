package com.training.in.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.annotation.NotProtected;
import com.training.core.common.bean.ResponseBean;
import com.training.core.common.enums.RoleEnum;
import com.training.core.common.enums.StatusEnum;
import com.training.core.common.exception.MessageException;
import com.training.core.common.util.DataCryptUtil;
import com.training.core.common.util.DateUtil;
import com.training.core.repo.po.OrgInformation;
import com.training.core.repo.po.OrgOperator;
import com.training.core.repo.po.OrgSports;
import com.training.core.service.OrgInformationService;
import com.training.core.service.OrgOperatorService;
import com.training.core.service.OrgSportsService;
import com.training.in.request.OrgSportsRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangjun on 2018/2/1.
 */
@Controller
@RequestMapping("/admin/init")
public class InitController extends BaseController {

    @Resource
    private OrgOperatorService orgOperatorService;

    @Resource
    private OrgInformationService orgInformationService;

    @Resource
    private OrgSportsService orgSportsService;

    @Desc("系统初始化测试支付")
    @NotProtected
    @RequestMapping(value = "/testpay", method = RequestMethod.GET)
    public ModelAndView renderTestPay() {
        ModelAndView modelAndView = new ModelAndView("Common/testpay");

        return modelAndView;
    }

    @Desc("系统初始化")
    @NotProtected
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView renderInitIndex() {
        ModelAndView modelAndView = new ModelAndView("Init/Index");

        return modelAndView;
    }

    @Desc("保存基础资料")
    @NotProtected
    @ResponseBody
    @RequestMapping(value = "/saveSettings", method = RequestMethod.POST)
    public ResponseBean saveVenueSettings(OrgInformation orgInformation) {
        try {

            orgInformation.setCreateTime(DateUtil.getNowDate());
            orgInformation.setUpdateTime(DateUtil.getNowDate());
            orgInformationService.addOrgInformation(orgInformation);

            Map map = new HashMap();
            map.put("orgId", orgInformation.getId());

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("保存管理员")
    @NotProtected
    @ResponseBody
    @RequestMapping(value = "/saveAdmin", method = RequestMethod.POST)
    public ResponseBean saveSettingsAdmin(OrgOperator orgOperator) {
        try {
            orgOperator.setPassword(DataCryptUtil.encrypt("123456"));
            orgOperator.setStatus(StatusEnum.STATUS_OK.getCode());
            orgOperator.setCreateTime(DateUtil.getNowDate());
            orgOperator.setUpdateTime(DateUtil.getNowDate());
            orgOperator.setRoleId(RoleEnum.ROLE_SUPER_ADMIN.getCode());

            orgOperatorService.addOrgOperator(orgOperator);

            Map map = new HashMap();
            map.put("adminId", orgOperator.getId());

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("保存项目")
    @NotProtected
    @ResponseBody
    @RequestMapping(value = "/saveSports", method = RequestMethod.POST)
    public ResponseBean saveVenueSports(@RequestBody OrgSportsRequest orgSportsRequests) {
        try {
            int result;

            for (OrgSports orgSports : orgSportsRequests.getOrgSportsList()) {
                orgSports.setCreateTime(DateUtil.getNowDate());
                orgSports.setUpdateTime(DateUtil.getNowDate());
                orgSports.setStatus(StatusEnum.STATUS_OK.getCode());
                orgSports.setOrgId(orgSportsRequests.getOrgId());
            }

            result = orgSportsService.addOrgSportsBatch(orgSportsRequests.getOrgSportsList());

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
