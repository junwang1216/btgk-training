package com.training.web.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.annotation.NotProtected;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by wangjun on 2017/5/2.
 */
@Controller
@RequestMapping("/passport")
public class PassportController extends BaseController {

    @Desc("登录页面")
    @NotProtected
    @RequestMapping("/login")
    public ModelAndView renderLoginPage() {
        return new ModelAndView("/Passport/Login");
    }

    @Desc("注册页面")
    @NotProtected
    @RequestMapping("/register")
    public ModelAndView renderRegisterPage() {
        return new ModelAndView("/Passport/Register");
    }

    @Desc("退出登录")
    @RequestMapping("/logout")
    public String logout() {
        super.getRequest().getSession().invalidate();
        return redirect("/document/search");
    }

}
