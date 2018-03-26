package com.training.in.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.bean.ResponseBean;
import com.training.core.common.enums.ClassStatusEnum;
import com.training.core.common.enums.StatusEnum;
import com.training.core.common.exception.MessageException;
import com.training.core.common.util.DateUtil;
import com.training.core.service.OrgClassService;
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
 * Created by wangjun on 2017/4/25.
 */
@Controller
@RequestMapping("/admin/dashboard")
public class DashboardController extends BaseController {

    @Resource
    private OrgClassService orgClassService;

    private ModelAndView setModelAndView(ModelAndView modelAndView) {
        return modelAndView.addObject("Admin", getLoginUser());
    }

    @Desc("工作面板")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView renderDashboardIndex() {

        ModelAndView modelAndView = new ModelAndView("Dashboard/Index");

        return setModelAndView(modelAndView);
    }

    @Desc("统计班级数量")
    @ResponseBody
    @RequestMapping(value = "/class/total", method = RequestMethod.GET)
    public ResponseBean totalDashboardClass(String type) {
        try {
            Map<String, Object> map = new HashMap<>();

            String startTime = null, endTime = null;
            switch (type) {
                case "year":
                    startTime = DateUtil.getCurrentYearEndTime() + "-01-01 00:00:00";
                    endTime = DateUtil.getCurrentYearEndTime() + "-12-31 23:59:59";
                    break;
                case "total":
                    break;
                case "month":
                default:
                    startTime = DateUtil.getTimesMonthmorning();
                    endTime = DateUtil.getTimesMonthnight();
            }

            map.put("start", orgClassService.totalOrgClassCount(startTime, endTime, ClassStatusEnum.STATUS_START.getCode()));
            map.put("working", orgClassService.totalOrgClassCount(startTime, endTime, ClassStatusEnum.STATUS_WORKING.getCode()));
            map.put("end", orgClassService.totalOrgClassCount(startTime, endTime, ClassStatusEnum.STATUS_END.getCode()));

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

}
