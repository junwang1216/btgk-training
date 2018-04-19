<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.h5.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <link href="/Content/dest/css/center/detail.min.css?ver=${static_resource_version}" rel="stylesheet">
    <style type="text/css">
        .weui-media-box__desc {
            -webkit-line-clamp: 10;
        }
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="page__bd">
        <div class="weui-flex course-item">
            <div class="weui-flex__item course-info">
                <div class="course-title">${orgClass.className}</div>
                <div class="course-teacher">${orgCoaches.realName}</div>
                <div class="course-date">报名中...</div>
            </div>
        </div>
        <div class="weui-panel course-detail">
            <div class="weui-panel__hd">班级介绍</div>
            <div class="weui-panel__bd">
                <div class="weui-media-box weui-media-box_text">
                    <p class="weui-media-box__desc">地址：${orgVenues.venueName}</p>
                    <p class="weui-media-box__desc">${orgCourses.courseNote}</p>
                </div>
            </div>
        </div>
        <div class="weui-panel course-table">
            <div class="weui-panel__hd">班级排期</div>
            <div class="weui-panel__bd">
                <c:forEach var="schedule" items="${orgClassScheduleAllList}" varStatus="loop">
                    <div class="weui-media-box weui-media-box_text">
                        <p class="weui-media-box__desc">
                            第${loop.index + 1}课<span style="float: right">${schedule.classDate}[周${schedule.classWeek}]</span>
                        </p>
                    </div>
                </c:forEach>
                <c:if test="${orgClassScheduleAllList.size() == 0}">
                    <p class="weui-cells__tip">班级还没有排期，请期待！</p>
                </c:if>
            </div>
        </div>
    </div>
</layout:override>

<c:import url="../../Shared/GeneralLayout.jsp">
    <c:param name="title" value="课程介绍"/>
</c:import>
