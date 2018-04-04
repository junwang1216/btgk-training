<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.in.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <style type="text/css">
        .class-list th {
            padding: 0.75rem;
        }
        .class-list td {
            padding: 0.75rem;
        }
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/class/coachSign.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="container-fluid">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <strong>教练签到</strong>
                            <small>Attendance</small>
                        </div>
                        <div class="card-block">
                            <form action="" method="post" class="form-horizontal">
                                <div class="form-group row">
                                    <div class="col-md-2">
                                        <select class="form-control" id="sign_venueId" name="venueId">
                                            <option value="0">所属场馆</option>
                                            <c:forEach var="venue" items="${orgVenuesList}">
                                                <option value="${venue.id}">${venue.venueName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        <select class="form-control" id="sign_classId" name="classId">
                                            <option value="0">负责班级</option>
                                            <c:forEach var="cls" items="${classList}">
                                                <option value="${cls.id}">${cls.className}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-8">
                                        <button type="button" class="btn btn-primary search-coach">
                                            <i class="fa fa-search"></i> 检 索
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer text-right"></div>
                        <div class="card-block">
                            <table class="table table-striped table-sm class-list">
                                <thead>
                                <tr>
                                    <th>##</th>
                                    <th>签到日期</th>
                                    <th>签到教练</th>
                                    <th>所在班级</th>
                                    <th>所属场馆</th>
                                    <th>签到时间</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="sign" items="${orgAttendanceResponseList}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index + 1}</td>
                                        <td>${sign.orgAttendance.inDate}</td>
                                        <td>${sign.orgCoaches.realName}</td>
                                        <td>${sign.orgClass.className}</td>
                                        <td>${sign.orgVenues.venueName}</td>
                                        <td>${sign.orgAttendance.createTime}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <div>
                                <%@ include file="../Shared/Pagination.jsp" %>
                            </div>
                        </div>
                    </div>
                </div>
                <!--/.col-->
            </div>
            <!--/.row-->
        </div>

    </div>
</layout:override>

<c:import url="../Shared/GeneralLayout.jsp">
    <c:param name="menu" value="class"/>
    <c:param name="subMenu" value="coach"/>
</c:import>
