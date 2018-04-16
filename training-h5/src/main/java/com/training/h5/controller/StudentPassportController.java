package com.training.h5.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.annotation.NotProtected;
import com.training.core.common.bean.ResponseBean;
import com.training.core.common.constant.IPlatformConstant;
import com.training.core.common.exception.MessageException;
import com.training.core.common.util.DataCryptUtil;
import com.training.core.common.util.DateUtil;
import com.training.core.common.util.StrUtil;
import com.training.core.repo.po.OrgStudents;
import com.training.core.repo.query.OrgStudentRequest;
import com.training.core.service.OrgStudentsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by wangjun on 2017/4/25.
 */
@Controller
@RequestMapping("/student/passport")
public class StudentPassportController extends BaseController {

    @Resource
    private OrgStudentsService orgStudentsService;

    @Desc("用户登录")
    @NotProtected
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView renderPassportLogin(String returnUrl) {
        ModelAndView modelAndView = new ModelAndView("/Student/Passport/Login");

        modelAndView.addObject("returnUrl", StrUtil.isBlank(returnUrl) ? "/student/center/index" : returnUrl);

        return modelAndView;
    }

    @NotProtected
    @ResponseBody
    @RequestMapping(value = "/submitLogin", method = RequestMethod.POST)
    public ResponseBean submitPassportLogin(OrgStudentRequest orgStudentRequest) {
        try {
            if (StrUtil.isBlank(orgStudentRequest.getMobile())) {
                throw new MessageException("请输入手机号！");
            }
            if (StrUtil.isBlank(orgStudentRequest.getPassword())) {
                throw new MessageException("请输入密码！");
            }

            orgStudentRequest.setPassword(DataCryptUtil.encrypt(orgStudentRequest.getPassword()));
            OrgStudents orgStudents = orgStudentsService.innerLogin(orgStudentRequest);
            if (orgStudents == null) {
                throw new MessageException("用户名或者密码错误！");
            }

            // 更新最新登录时间
            orgStudentsService.setLastLoginTime(DateUtil.getNowDate(), orgStudents.getId());

            super.getRequest().getSession().setAttribute(IPlatformConstant.LOGIN_USER, orgStudents);

            return new ResponseBean(true);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("用户注册")
    @NotProtected
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String renderPassportRegister() {
        return "Passport/Register";
    }

    @Desc("退出登录")
    @NotProtected
    @RequestMapping("/logout")
    public String redirectLogout() {
        super.getRequest().getSession().invalidate();
        return "redirect:/student/passport/login";
    }

}
