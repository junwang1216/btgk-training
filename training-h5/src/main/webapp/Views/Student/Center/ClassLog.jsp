<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.h5.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <link href="/Content/dest/css/center/log.min.css?ver=${static_resource_version}" rel="stylesheet">
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/student/center/classLog.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="page__bd">

        <div class="weui-progress" id="select_class">
            <div class="weui-progress__bar class-bar">
                <div class="weui-progress__inner-bar" style="width: ${orgAttendanceList.size() * 100 / orgClassScheduleList.size()}%;"></div>
            </div>
            <div class="weui-cells class-title">
                <div class="weui-cell">
                    <div class="weui-cell__bd">
                        <p>${orgClass.className}</p>
                    </div>
                    <div class="weui-cell__ft">${orgAttendanceList.size()}/${orgClassScheduleList.size()}课次</div>
                </div>
            </div>
        </div>
        <div class="weui-cells class-list">
            <c:forEach var="cls" items="${orgClassScheduleList}" varStatus="loop">
                <div class="weui-cell weui-cell_access class-item" data-date="${cls.classDate}"
                     data-schedule="${cls.id}" data-sign="${cls.hasSigned}">
                    <div class="weui-cell__hd">
                        <c:if test="${cls.hasSigned == 1}">
                            <img src="/Content/images/icon-selected.png" alt="">
                        </c:if>
                        <c:if test="${cls.hasSigned == 0}">
                            <img src="/Content/images/icon-unselected.png" alt="">
                        </c:if>
                    </div>
                    <div class="weui-cell__bd">
                        <p>${cls.classDate}（周${cls.classWeek}）</p>
                    </div>
                    <c:if test="${cls.hasSigned == 1}">
                        <div class="weui-cell__ft">已完成</div>
                    </c:if>
                    <c:if test="${cls.hasSigned == 0}">
                        <div class="weui-cell__ft">未签到</div>
                    </c:if>
                </div>
            </c:forEach>
        </div>
    </div>

    <div>
        <div class="weui-mask" id="iosMask"></div>
        <div class="weui-actionsheet" id="iosActionsheet">
            <div class="weui-actionsheet__title">
                <p class="weui-actionsheet__title-text">班级列表</p>
            </div>
            <div class="weui-actionsheet__menu">
                <c:forEach var="cls" items="${orgClassList}">
                    <div class="weui-actionsheet__cell">
                        <a href="/student/center/class/log?classId=${cls.id}">${cls.className}</a>
                    </div>
                </c:forEach>
            </div>
            <div class="weui-actionsheet__action">
                <div class="weui-actionsheet__cell" id="iosActionsheetCancel">取 消</div>
            </div>
        </div>
    </div>
</layout:override>

<c:import url="../../Shared/GeneralLayout.jsp">
    <c:param name="title" value="上课进度"/>
</c:import>
