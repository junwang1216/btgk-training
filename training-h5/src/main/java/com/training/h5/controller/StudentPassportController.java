package com.training.h5.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.annotation.NotProtected;
import com.training.core.common.bean.ResponseBean;
import com.training.core.common.config.WebConfig;
import com.training.core.common.constant.IPlatformConstant;
import com.training.core.common.exception.MessageException;
import com.training.core.common.util.*;
import com.training.core.mail.MailSenderInfo;
import com.training.core.mail.SimpleMailSender;
import com.training.core.repo.po.OrgStudents;
import com.training.core.repo.query.OrgStudentRequest;
import com.training.core.service.OrgStudentsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

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

    @Desc("用户忘记密码")
    @NotProtected
    @RequestMapping(value = "/forget", method = RequestMethod.GET)
    public ModelAndView renderPassportForget(String token) {
        ModelAndView modelAndView = new ModelAndView("/Student/Passport/ForgetPassword");

        boolean isAllow = false;
        String mobile = "";

        if (token != null) {
            try {
                String params = DataCryptUtil.decrypt(token);
                String[] paramArray = params.split("[&]");

                for (String p : paramArray) {
                    String[] pArr = p.split("=");

                    if (pArr[0].equals("mobile")) {
                        mobile = pArr[1];
                    }

                    if (pArr[0].equals("timestamp")) {
                        if (DateUtil.getTimeHourNums(DateUtil.getNowDate(), pArr[0]) < 24) {
                            isAllow = true;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        modelAndView.addObject("isAllow", isAllow);
        modelAndView.addObject("mobile", mobile);

        return modelAndView;
    }

    @NotProtected
    @ResponseBody
    @RequestMapping(value = "/sendForgetEmail", method = RequestMethod.POST)
    public ResponseBean sendForgetEmail(String mobile, String mail) {
        try {

            String timestamp = DateUtil.getNowDate();
            String params = DataCryptUtil.encrypt("mobile=" + mobile + "&timestamp" + timestamp);
            String content = "http://localhost:8081/student/passport/forget?token=" + params;

            MailSenderInfo mailInfo = new MailSenderInfo();
            mailInfo.setMailServerHost(WebConfig.getMailHost());
            mailInfo.setMailServerPort(WebConfig.getMailPort().toString());
            mailInfo.setValidate(true);
            mailInfo.setUserName(WebConfig.getMailUserName());
            mailInfo.setPassword(WebConfig.getMailPassword());//您的邮箱密码
            mailInfo.setFromAddress(WebConfig.getMailUserName());
            mailInfo.setToAddress(mail);
            mailInfo.setSubject("北体高科培训-重置密码");
            mailInfo.setContent(content);

            SimpleMailSender sms = new SimpleMailSender();
            sms.sendTextMail(mailInfo);//发送文体格式
            //sms.sendHtmlMail(mailInfo);//发送html格式

            return new ResponseBean(true);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/saveForget", method = RequestMethod.POST)
    public ResponseBean submitPassportForget(OrgStudentRequest orgStudentRequest) {
        try {
            if (StrUtil.isBlank(orgStudentRequest.getMobile())) {
                throw new MessageException("请输入手机号！");
            }
            if (StrUtil.isBlank(orgStudentRequest.getPassword())) {
                throw new MessageException("请输入密码！");
            }

            List<OrgStudents> orgStudentsList = orgStudentsService.queryOrgStudentsList(null, orgStudentRequest.getMobile(), null, 0, 1000);

            if (orgStudentsList.size() == 0) {
                throw new MessageException("没有找到此学员！");
            }

            orgStudentsList.get(0).setPassword(DataCryptUtil.encrypt(orgStudentRequest.getPassword()));
            int result = orgStudentsService.saveOrgStudents(orgStudentsList.get(0));

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("用户重置密码")
    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public ModelAndView renderPassportReset() {
        ModelAndView modelAndView = new ModelAndView("/Student/Passport/ResetPassword");

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/saveReset", method = RequestMethod.POST)
    public ResponseBean submitPassportReset(OrgStudentRequest orgStudentRequest) {
        try {
            if (StrUtil.isBlank(orgStudentRequest.getMobile())) {
                throw new MessageException("请输入手机号！");
            }
            if (StrUtil.isBlank(orgStudentRequest.getPassword())) {
                throw new MessageException("请输入密码！");
            }

            OrgStudents orgStudents = (OrgStudents)super.getRequest().getSession().getAttribute(IPlatformConstant.LOGIN_USER);
            OrgStudents orgStudents1 = orgStudentsService.getOrgStudents(orgStudents.getId());

            if (!(orgStudents1.getPassword().equals(DataCryptUtil.encrypt(orgStudentRequest.getPassword())))) {
                throw new MessageException("原始密码输入错误！");
            }

            orgStudents1.setPassword(DataCryptUtil.encrypt(orgStudentRequest.getPassword()));
            orgStudentsService.saveOrgStudents(orgStudents1);

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
