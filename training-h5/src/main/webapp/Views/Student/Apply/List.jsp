<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.h5.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <link href="/Content/dest/css/apply/list.min.css?ver=${static_resource_version}" rel="stylesheet">
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="page__bd">
        <div class="course-list">
            <a class="weui-flex course-item" href="/student/apply/detail?applyId=123456">
                <div class="weui-flex__item course-info">
                    <div class="course-title">青少年篮球训练营</div>
                    <div class="course-teacher">张益帆</div>
                    <div class="course-date">报名时间：2018-01-23 至 2018-01-30</div>
                </div>
            </a>
            <a class="weui-flex course-item" href="/student/apply/detail?applyId=123456">
                <div class="weui-flex__item course-info">
                    <div class="course-title">青少年篮球训练营</div>
                    <div class="course-teacher">王清妍</div>
                    <div class="course-date">报名时间：2018-01-23 至 2018-01-30</div>
                </div>
            </a>
            <a class="weui-flex course-item" href="/student/apply/detail?applyId=123456">
                <div class="weui-flex__item course-info">
                    <div class="course-title">青少年篮球训练营</div>
                    <div class="course-teacher">李尚</div>
                    <div class="course-date">报名时间：2018-01-23 至 2018-01-30</div>
                </div>
            </a>
            <a class="weui-flex course-item" href="/student/apply/detail?applyId=123456">
                <div class="weui-flex__item course-info">
                    <div class="course-title">青少年篮球训练营</div>
                    <div class="course-teacher">赵一鸣</div>
                    <div class="course-date">报名时间：2018-01-23 至 2018-01-30</div>
                </div>
            </a>
        </div>
    </div>
</layout:override>

<c:import url="../../Shared/GeneralLayout.jsp">
    <c:param name="title" value="课程列表"/>
</c:import>
