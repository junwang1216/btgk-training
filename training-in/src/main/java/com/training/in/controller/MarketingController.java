package com.training.in.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.constant.IPlatformConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 营销管理
 * Created by wangjun on 2018/12/29.
 */
@Controller
@RequestMapping("/admin/marketing")
public class MarketingController extends BaseController {

    private ModelAndView setModelAndView(ModelAndView modelAndView) {
        return modelAndView.addObject("Admin", super.getRequest().getSession().getAttribute(IPlatformConstant.LOGIN_USER));
    }

    @Desc("潜在学员")
    @RequestMapping(value = "/potential/students", method = RequestMethod.GET)
    public ModelAndView renderPotentialStudentsList() {

        ModelAndView modelAndView = new ModelAndView("Marketing/PotentialStudents");

        modelAndView.addObject("conditions", null);

        return setModelAndView(modelAndView);
    }

    @Desc("潜在学员详情")
    @RequestMapping(value = "/potential/students/info", method = RequestMethod.GET)
    public ModelAndView renderPotentialStudentsInfo() {

        ModelAndView modelAndView = new ModelAndView("Marketing/PotentialStudentsInfo");

        modelAndView.addObject("conditions", null);

        return setModelAndView(modelAndView);
    }

    @Desc("潜在学员添加")
    @RequestMapping(value = "/potential/students/add", method = RequestMethod.GET)
    public ModelAndView renderPotentialStudentsAdd() {

        ModelAndView modelAndView = new ModelAndView("Marketing/PotentialStudentsAdd");

        modelAndView.addObject("conditions", null);

        return setModelAndView(modelAndView);
    }

    @Desc("潜在学员试听记录")
    @RequestMapping(value = "/preview/students/log", method = RequestMethod.GET)
    public ModelAndView renderPreviewStudentsLog() {
        // 试听课，试听教练，有无签到
        ModelAndView modelAndView = new ModelAndView("Marketing/PreviewStudentsLog");

        modelAndView.addObject("conditions", null);

        return setModelAndView(modelAndView);
    }

    @Desc("潜在学员转化率")
    @RequestMapping(value = "/potential/students/change", method = RequestMethod.GET)
    public ModelAndView renderPotentialStudentsChange() {
        // 时间段的转化率，推销人的转化率，试听课的转化率，试听教练的转化率
        ModelAndView modelAndView = new ModelAndView("Marketing/PotentialStudentsChange");

        modelAndView.addObject("conditions", null);

        return setModelAndView(modelAndView);
    }
}
