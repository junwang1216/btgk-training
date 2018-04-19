<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.h5.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <link href="/Content/dest/css/center/index.min.css?ver=${static_resource_version}" rel="stylesheet">
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="page__bd">
        <div class="center-profile">
            <div class="weui-form-preview">
                <a href="/student/center/profile" class="weui-form-preview__bd center-header">
                    <div class="weui-form-preview__item">
                        <img src="/Content/images/icon-user1.png">
                        <!--<p>${User.realName}(100积分)</p>-->
                        <p>${User.realName}(${User.mobile})</p>
                    </div>
                </a>
                <div class="weui-form-preview__ft">
                    <a class="weui-form-preview__btn weui-form-preview__btn_primary"
                       href="/student/center/attendance">每日签到</a>
                    <a class="weui-form-preview__btn weui-form-preview__btn_primary" href="/student/apply/list">课程报名</a>
                </div>
            </div>
        </div>

        <div class="weui-cells center-list">
            <a class="weui-cell weui-cell_access" href="/student/center/class">
                <div class="weui-cell__hd">
                    <img src="/Content/images/icon-order.png?v=${static_resource_version}">
                </div>
                <div class="weui-cell__bd">
                    <p>我的班级</p>
                </div>
                <div class="weui-cell__ft"></div>
            </a>
            <a class="weui-cell weui-cell_access" href="/student/center/class/log">
                <div class="weui-cell__hd">
                    <img src="/Content/images/icon-unpaid7.png?v=${static_resource_version}">
                </div>
                <div class="weui-cell__bd">
                    <p>上课进度</p>
                </div>
                <div class="weui-cell__ft"></div>
            </a>
            <a class="weui-cell weui-cell_access" href="/student/passport/reset">
                <div class="weui-cell__hd">
                    <img src="/Content/images/icon-password.png?v=${static_resource_version}">
                </div>
                <div class="weui-cell__bd">
                    <p>修改密码</p>
                </div>
                <div class="weui-cell__ft"></div>
            </a>
        </div>

        <div class="weui-cells center-list">
            <a class="weui-cell weui-cell_access" href="javascript:;">
                <div class="weui-cell__hd">
                    <img src="/Content/images/icon-about.png?v=${static_resource_version}">
                </div>
                <div class="weui-cell__bd">
                    <p>关于我们</p>
                </div>
                <div class="weui-cell__ft"></div>
            </a>
            <a class="weui-cell weui-cell_access" href="javascript:;">
                <div class="weui-cell__hd">
                    <img src="/Content/images/icon-phone.png?v=${static_resource_version}">
                </div>
                <div class="weui-cell__bd">
                    <p>联系客服</p>
                </div>
                <div class="weui-cell__ft"></div>
            </a>
        </div>

        <p class="weui-btn-area">
            <a href="/student/passport/logout" class="weui-btn weui-btn_warn">退出登录</a>
        </p>

        <div class="weui-footer">
            <p class="weui-footer__text">Copyright © 2016 北体高科（北京）科技有限公司</p>
        </div>
    </div>
</layout:override>

<c:import url="../../Shared/GeneralLayout.jsp">
    <c:param name="title" value="个人中心"/>
</c:import>
