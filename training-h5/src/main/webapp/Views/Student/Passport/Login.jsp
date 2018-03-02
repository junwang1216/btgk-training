<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.h5.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <link href="/Content/dest/css/passport/login.min.css?ver=${static_resource_version}" rel="stylesheet">
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/student/passport/login.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="page__bd">
        <div class="logo">
            <img class="logo" src="/Content/images/logo.png?v=${static_resource_version}" alt="">
        </div>

        <div class="loginPage">
            <div class="input-container">
                <label><img src="/Content/images/passport/icon1.png?v=${static_resource_version}" alt=""></label>
                <div class="flex"><input type="tel" maxlength="11" name="mobile" placeholder="请输入手机号" value="18610018682"></div>
                <div class="delete"><img src="/Content/images/passport/icon3.png?v=${static_resource_version}" alt=""></div>
            </div>
            <div class="input-container">
                <label><img src="/Content/images/passport/icon2.png?v=${static_resource_version}" alt=""></label>
                <div class="flex"><input type="password" name="password" placeholder="请输入密码" value="123456"></div>
                <div class="delete"><img src="/Content/images/passport/icon3.png?v=${static_resource_version}" alt=""></div>
            </div>
        </div>

        <div class="weui-flex">
            <div class="weui-flex__item">
                <label for="weuiAgree" class="weui-agree">
                    <input id="weuiAgree" type="checkbox" class="weui-agree__checkbox">
                    <span class="weui-agree__text">自动登陆</span>
                </label>
            </div>
            <div class="weui-flex__item wj-mm"><a href="#">忘记密码?</a></div>
        </div>

        <div style="padding:10px;">
            <button class="weui-btn weui-btn_yellow to-login">登 录</button>
        </div>

        <div class="error-txt"><span>该手机号码未注册成为联营店！</span></div>
    </div>
</layout:override>

<c:import url="../../Shared/GeneralLayout.jsp">
    <c:param name="title" value="学员登录"/>
</c:import>
