package com.training.in.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.annotation.NotProtected;
import com.training.core.common.bean.ResponseBean;
import com.training.core.common.enums.LogTypeEnum;
import com.training.core.common.exception.MessageException;
import com.training.core.common.util.DataCryptUtil;
import com.training.core.common.util.DateUtil;
import com.training.core.common.util.StrUtil;
import com.training.core.repo.po.OrgOperator;
import com.training.core.service.OrgOperatorService;
import com.training.core.repo.query.OrgOperatorRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 账号的登录、注册、忘记密码等操作
 * Created by wangjun on 2017/4/25.
 */
@Controller
@RequestMapping("/admin/passport")
public class PassportController extends BaseController {

    private static Logger logger = Logger.getLogger(PassportController.class);

    @Resource
    private OrgOperatorService orgOperatorService;

    @Desc("用户登录")
    @NotProtected
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView renderPassportLogin(String returnUrl) {
        ModelAndView modelAndView = new ModelAndView("Passport/Login");

        modelAndView.addObject("returnUrl", StrUtil.isBlank(returnUrl) ? "/admin/dashboard/index" : returnUrl);

        return modelAndView;
    }

    @NotProtected
    @ResponseBody
    @RequestMapping(value = "/submitLogin", method = RequestMethod.POST)
    public ResponseBean submitPassportLogin(OrgOperatorRequest orgOperatorRequest) {
        try {
            if (StrUtil.isBlank(orgOperatorRequest.getUserName())) {
                throw new MessageException("请输入用户名！");
            }
            if (StrUtil.isBlank(orgOperatorRequest.getPassword())) {
                throw new MessageException("请输入密码！");
            }

            orgOperatorRequest.setPassword(DataCryptUtil.encrypt(orgOperatorRequest.getPassword()));
            OrgOperator orgOperator = orgOperatorService.innerLogin(orgOperatorRequest);
            if (orgOperator == null) {
                throw new MessageException("用户名或者密码错误！");
            }

            // 更新最新登录时间
            orgOperatorService.setLastLoginTime(DateUtil.getNowDate(), orgOperator.getId());

            setLoginUser(orgOperator);

            log(LogTypeEnum.LOG_TYPE_LOGIN, orgOperator.getOrgId(), null);

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
        return "redirect:/admin/passport/login";
    }

}
