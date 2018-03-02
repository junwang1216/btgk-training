package com.training.web.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.annotation.NotProtected;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by wangjun on 2017/5/1.
 */
@Controller
@RequestMapping("/center")
public class CenterController extends BaseController {

    @Desc("个人信息")
    @NotProtected
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String renderCenterProfile(Model model) {
        model.addAttribute("isLogin", true);

        return "Center/Profile";
    }

    @Desc("修改密码")
    @NotProtected
    @RequestMapping(value = "/password", method = RequestMethod.GET)
    public String renderCenterPassword(Model model) {
        model.addAttribute("isLogin", true);

        return "Center/Password";
    }

    @Desc("我要充值")
    @NotProtected
    @RequestMapping(value = "/recharge", method = RequestMethod.GET)
    public String renderCenterRecharge(Model model) {
        model.addAttribute("isLogin", true);

        return "Center/Recharge";
    }

}
