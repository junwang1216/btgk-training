package com.training.h5.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.annotation.NotProtected;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by wangjun on 2017/5/2.
 */
@Controller
@RequestMapping("/student/enter")
public class StudentEnterController extends BaseController {

    @Desc("学生入口")
    @NotProtected
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView renderStudentEnter() {

        ModelAndView modelAndView = new ModelAndView("Student/Index");

        return modelAndView;
    }

}
