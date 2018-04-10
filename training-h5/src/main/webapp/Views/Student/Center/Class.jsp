<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.h5.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <link href="/Content/dest/css/center/class.min.css?ver=${static_resource_version}" rel="stylesheet">
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="page__bd">
        <div class="course-list">
            <div class="weui-tab">
                <div class="weui-navbar">
                    <a class="weui-navbar__item" href="#course_start">未开始</a>
                    <a class="weui-navbar__item weui-bar__item--on" href="#course_going">进行中</a>
                    <a class="weui-navbar__item" href="#course_end">已结业</a>
                </div>
                <div class="weui-tab__bd">
                    <div id="course_start" class="weui-tab__bd-item">
                        <c:if test="${orgClassStartList.size() == 0}">
                            <p class="no-log"><img src="/Content/images/icon-order.png?ver=${static_resource_version}"> 没有任何分班记录！</p>
                        </c:if>
                        <c:forEach var="cls" items="${orgClassStartList}" varStatus="loop">
                            <a class="weui-flex course-item" href="/student/center/class/detail?type=1&classId=${cls.orgClass.id}">
                                <div class="weui-flex__item course-info">
                                    <div class="course-title">${cls.orgClass.className}</div>
                                    <div class="course-teacher">${cls.orgCoaches.realName}</div>
                                    <div class="course-date">报名时间：2018-01-23 至 2018-01-30</div>
                                </div>
                            </a>
                        </c:forEach>
                    </div>
                    <div id="course_going" class="weui-tab__bd-item weui-tab__bd-item--active">
                        <c:if test="${orgClassGoingList.size() == 0}">
                            <p class="no-log"><img src="/Content/images/icon-order.png?ver=${static_resource_version}"> 没有任何上课记录！</p>
                        </c:if>

                        <c:forEach var="cls" items="${orgClassGoingList}" varStatus="loop">
                            <a class="weui-flex course-item" href="/student/center/class/detail?type=2&classId=${cls.orgClass.id}">
                                <div class="weui-flex__item course-info">
                                    <div class="course-title">${cls.orgClass.className}</div>
                                    <div class="course-teacher">${cls.orgCoaches.realName}</div>
                                    <div class="course-date">上课进度：10/20课时</div>
                                </div>
                            </a>
                        </c:forEach>
                    </div>
                    <div id="course_end" class="weui-tab__bd-item">
                        <c:if test="${orgClassEndList.size() == 0}">
                            <p class="no-log"><img src="/Content/images/icon-order.png?ver=${static_resource_version}"> 没有任何结课记录！</p>
                        </c:if>

                        <c:forEach var="cls" items="${orgClassEndList}" varStatus="loop">
                            <a class="weui-flex course-item" href="/student/center/class/detail?type=3&classId=${cls.orgClass.id}">
                                <div class="weui-flex__item course-info">
                                    <div class="course-title">${cls.orgClass.className}</div>
                                    <div class="course-teacher">${cls.orgCoaches.realName}</div>
                                    <div class="course-date">结业时间：2018-01-30</div>
                                </div>
                            </a>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</layout:override>

<c:import url="../../Shared/GeneralLayout.jsp">
    <c:param name="title" value="我的班级"/>
</c:import>
