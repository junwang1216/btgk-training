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
            <c:forEach var="cls" items="${orgClassList}" varStatus="loop">
                <a class="weui-flex course-item" href="/student/apply/detail?classId=${cls.orgClass.id}">
                    <div class="weui-flex__item course-info">
                        <div class="course-title">${cls.orgClass.className}</div>
                        <div class="course-teacher">${cls.orgCoaches.realName}</div>
                        <div class="course-date">已报名：${cls.orgClassStudentsLength}人</div>
                    </div>
                </a>
            </c:forEach>
        </div>
        <c:if test="${orgClassListLength == 0}">
            <div class="weui-loadmore weui-loadmore_line">
                <span class="weui-loadmore__tips">暂无数据</span>
            </div>
        </c:if>
    </div>
</layout:override>

<c:import url="../../Shared/GeneralLayout.jsp">
    <c:param name="title" value="课程列表"/>
</c:import>
