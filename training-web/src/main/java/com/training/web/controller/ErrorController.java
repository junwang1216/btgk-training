package com.training.web.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.annotation.NotProtected;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by wangjun on 2017/4/25.
 */
@Controller
@RequestMapping("/error")
public class ErrorController {

    @Desc("没找到页面")
    @NotProtected
    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String renderError404() {
        return "Error/404";
    }

    @Desc("系统异常")
    @NotProtected
    @RequestMapping(value = "/500", method = RequestMethod.GET)
    public String renderError500() {
        return "Error/500";
    }

}
