package com.training.h5.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.annotation.NotProtected;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by wangjun on 2017/12/26.
 */
public class CoachCenterController {
    @Desc("个人中心")
    @NotProtected
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String renderCenterList() {
        return "Coach/Center/Index";
    }
}
