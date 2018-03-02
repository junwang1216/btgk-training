package com.training.h5.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.annotation.NotProtected;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by wangjun on 2017/5/2.
 */
@Controller
@RequestMapping("/student/apply")
public class StudentApplyController extends BaseController {

    @Desc("课程列表")
    @NotProtected
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String renderApplyList() {
        return "Student/Apply/List";
    }

    @Desc("课程详情")
    @NotProtected
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String renderApplyDetail() {
        return "Student/Apply/Detail";
    }

    @Desc("课程确认")
    @NotProtected
    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String renderApplyConfirm() {
        return "Student/Apply/Confirm";
    }

    @Desc("课程状态")
    @NotProtected
    @RequestMapping(value = "/state", method = RequestMethod.GET)
    public String renderApplyState() {
        return "Student/Apply/State";
    }

}
