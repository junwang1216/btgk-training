package com.training.in.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.annotation.NotProtected;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 帮助文档
 * Created by wangjun on 2018/3/26.
 */
@Controller
@RequestMapping("/admin/helper")
public class HelperController extends BaseController {
    @Desc("系统初始化测试支付")
    @NotProtected
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView renderHelpIndex() {
        ModelAndView modelAndView = new ModelAndView("Helper/Index");

        return modelAndView;
    }
}
