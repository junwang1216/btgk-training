package com.training.in.controller;

import com.training.core.common.annotation.Desc;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by wangjun on 2017/4/25.
 */
@Controller
@RequestMapping("/admin/dashboard")
public class DashboardController extends BaseController {

    private ModelAndView setModelAndView(ModelAndView modelAndView) {
        return modelAndView.addObject("Admin", getLoginUser());
    }

    @Desc("工作面板")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView renderDashboardIndex() {

        ModelAndView modelAndView = new ModelAndView("Dashboard/Index");

        return setModelAndView(modelAndView);
    }

}
