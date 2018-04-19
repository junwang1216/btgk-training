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
    <script type="text/javascript" src="Content/js/student/passport/reset.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="page__bd">
        <div class="logo">
            <img class="logo" src="/Content/images/logo.png?v=${static_resource_version}" alt="">
        </div>

        <div class="loginPage">
            <div class="input-container">
                <label><img src="/Content/images/passport/icon1.png?v=${static_resource_version}" alt=""></label>
                <div class="flex"><input type="tel" maxlength="11" name="mobile" placeholder="请输入手机号"></div>
                <div class="delete"><img src="/Content/images/passport/icon3.png?v=${static_resource_version}" alt=""></div>
            </div>
            <div class="input-container">
                <label><img src="/Content/images/passport/icon2.png?v=${static_resource_version}" alt=""></label>
                <div class="flex"><input type="password" name="password" placeholder="请输入旧密码"></div>
                <div class="delete"><img src="/Content/images/passport/icon3.png?v=${static_resource_version}" alt=""></div>
            </div>
            <div class="input-container">
                <label><img src="/Content/images/passport/icon2.png?v=${static_resource_version}" alt=""></label>
                <div class="flex"><input type="password" name="password" placeholder="请输入新密码"></div>
                <div class="delete"><img src="/Content/images/passport/icon3.png?v=${static_resource_version}" alt=""></div>
            </div>
            <div class="input-container">
                <label><img src="/Content/images/passport/icon2.png?v=${static_resource_version}" alt=""></label>
                <div class="flex"><input type="password" name="password" placeholder="请输入确认密码"></div>
                <div class="delete"><img src="/Content/images/passport/icon3.png?v=${static_resource_version}" alt=""></div>
            </div>
        </div>

        <div style="padding:10px;">
            <button class="weui-btn weui-btn_yellow to-reset">重置密码</button>
        </div>
    </div>
</layout:override>

<c:import url="../../Shared/GeneralLayout.jsp">
    <c:param name="title" value="修改密码"/>
</c:import>
