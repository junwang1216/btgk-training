<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.h5.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <link href="/Content/dest/css/apply/confirm.min.css?ver=${static_resource_version}" rel="stylesheet">
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="page__bd">
        <div class="weui-flex course-item">
            <div class="weui-flex__item course-info">
                <div class="course-title">青少年足球训练营</div>
                <div class="course-money">
                    支付金额：<span>2400</span>元
                </div>
            </div>
        </div>
        <div class="weui-cells__title">报名信息</div>
        <div class="weui-cells weui-cells_form course-user">
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label">真实姓名</label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="text" placeholder="请输入真实姓名" value="王梓睿">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label">手机号码</label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="tel" placeholder="请输入手机号码" value="15801223456">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label">出生日期</label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="date" placeholder="请选择出生日期" value="1995-12-12">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label">体重(kg)</label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="tel" placeholder="请输入体重" value="50">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label">身高(cm)</label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="tel" placeholder="请输入身高" value="140">
                </div>
            </div>
        </div>

        <label for="weuiAgree" class="weui-agree">
            <input id="weuiAgree" type="checkbox" class="weui-agree__checkbox" checked>
            <span class="weui-agree__text">阅读并同意<a href="javascript:void(0);">《乐享培训报名协议》</a></span>
        </label>

        <p class="weui-btn-area">
            <a href="/student/apply/state" class="weui-btn weui-btn_primary">确认报名</a>
        </p>
    </div>
</layout:override>

<c:import url="../../Shared/GeneralLayout.jsp">
    <c:param name="title" value="确认报名"/>
</c:import>
