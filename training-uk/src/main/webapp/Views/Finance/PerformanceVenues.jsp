<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.uk.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <style type="text/css">
        .dropdown-item.active, .dropdown-item:active {
            color: #fff !important;
            text-decoration: none !important;
            background-color: #20a8d8 !important;
        }
        .dropdown-item {
            position: relative;
            padding: 10px 20px !important;
            border-left: none !important;
            border-bottom: 1px solid #cfd8dc !important;
        }
        .dropdown-item {
            display: block;
            width: 100% !important;
            padding: 0.25rem 1.5rem !important;
            clear: both;
            font-weight: normal;
            color: #263238;
            text-align: inherit;
            white-space: nowrap;
            background: none;
            border: 0;
        }
        .dropdown-toggle::after {
            display: none;
        }
        .chart {
            background-color: rgba(221, 221, 221, .2);
        }
        .charts-content {
            margin-bottom: 0;
            margin-top: .4rem;
            list-style: none;
        }
        .charts-content li label {
            margin-bottom: 0;
            width: 4.6rem;
        }
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/finance/performanceVenues.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="container-fluid">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <strong>业绩排名</strong>
                            <small>Performance</small>
                        </div>
                        <div class="card-block">
                            <div class="btn-toolbar justify-content-between" role="toolbar">
                                <div class="btn-group" data-toggle="buttons">
                                    <label class="btn btn-outline-secondary <c:if test="${conditions.typeTime == 'prev_day'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type1" value="prev_day"
                                               <c:if test="${conditions.typeTime == 'prev_day'}">checked</c:if> >昨 日
                                    </label>
                                    <label class="btn btn-outline-secondary mr-3 <c:if test="${conditions.typeTime == 'day'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type2" value="day"
                                               <c:if test="${conditions.typeTime == 'day'}">checked</c:if> >今 日
                                    </label>

                                    <label class="btn btn-outline-secondary <c:if test="${conditions.typeTime == 'prev_week'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type3" value="prev_week"
                                               <c:if test="${conditions.typeTime == 'prev_week'}">checked</c:if> >上 周
                                    </label>
                                    <label class="btn btn-outline-secondary mr-3 <c:if test="${conditions.typeTime == 'week'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type4" value="week"
                                               <c:if test="${conditions.typeTime == 'week'}">checked</c:if> >本 周
                                    </label>

                                    <label class="btn btn-outline-secondary <c:if test="${conditions.typeTime == 'prev_month'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type5" value="prev_month"
                                               <c:if test="${conditions.typeTime == 'prev_month'}">checked</c:if> >上 月
                                    </label>
                                    <label class="btn btn-outline-secondary mr-3 <c:if test="${conditions.typeTime == 'month'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type6" value="month"
                                               <c:if test="${conditions.typeTime == 'month'}">checked</c:if> >本 月
                                    </label>

                                    <label class="btn btn-outline-secondary <c:if test="${conditions.typeTime == 'prev_period'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type9" value="prev_period"
                                               <c:if test="${conditions.typeTime == 'prev_period'}">checked</c:if> >上 季
                                    </label>
                                    <label class="btn btn-outline-secondary mr-3 <c:if test="${conditions.typeTime == 'period'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type10" value="period"
                                               <c:if test="${conditions.typeTime == 'period'}">checked</c:if> >本 季
                                    </label>

                                    <label class="btn btn-outline-secondary <c:if test="${conditions.typeTime == 'prev_year'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type7" value="prev_year"
                                               <c:if test="${conditions.typeTime == 'prev_year'}">checked</c:if> >去 年
                                    </label>
                                    <label class="btn btn-outline-secondary <c:if test="${conditions.typeTime == 'year'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type8" value="year"
                                               <c:if test="${conditions.typeTime == 'year'}">checked</c:if> >今 年
                                    </label>
                                </div>
                                <div class="btn-group" data-toggle="buttons">
                                    <label class="btn btn-outline-secondary <c:if test="${conditions.busType == 1}">active</c:if> ">
                                        <input type="radio" name="total_bus_type" id="total_bus_type1" value="1"
                                               <c:if test="${conditions.busType == 1}">checked</c:if> >青少年培训
                                    </label>
                                    <label class="btn btn-outline-secondary <c:if test="${conditions.busType == 2}">active</c:if> ">
                                        <input type="radio" name="total_bus_type" id="total_bus_type2" value="2"
                                               <c:if test="${conditions.busType == 2}">checked</c:if> >场地租赁
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="card-footer"></div>
                        <div class="card-block">
                            <div class="card">
                                <div class="card-header">
                                    <strong>所有基地</strong>

                                    <div class="card-actions">
                                        <a href="/admin/finance/performance?venueId=0" class="btn-direction" title="个人业绩排名">
                                            <i class="icon-direction"></i>
                                        </a>
                                        <div class="dropdown-menu dropdown-menu-right">
                                            <a class="dropdown-item" href="javascript:;">所有基地</a>
                                            <c:forEach var="venue" items="${orgFinanceVenuesList}">
                                                <c:if test="${venue.id == conditions.venueId}">
                                                    <a class="dropdown-item active" href="javascript:;">${venue.venueName}</a>
                                                </c:if>
                                                <c:if test="${venue.id != conditions.venueId}">
                                                    <a class="dropdown-item"
                                                       href="/admin/finance/performance?busType=&venueId=${venue.id}">${venue.venueName}</a>
                                                </c:if>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-block">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div id="finance_performance_chart1" class="chart"  style="width: 100%; height:360px;"></div>
                                        </div>
                                        <div class="col-md-6">
                                            <div id="finance_performance_chart4" class="chart"  style="width: 100%; height:360px;"></div>
                                        </div>

                                        <div class="col-md-6 mt-2">
                                            <div id="finance_performance_chart2" class="chart"  style="width: 100%; height:360px;"></div>
                                        </div>
                                        <div class="col-md-6 mt-2">
                                            <div id="finance_performance_chart3" class="chart"  style="width: 100%; height:360px;"></div>
                                        </div>

                                        <div class="col-md-6 mt-2">
                                            <div id="finance_performance_chart5" class="chart"  style="width: 100%; height:360px;"></div>
                                        </div>
                                        <div class="col-md-6 mt-2">
                                            <div id="finance_performance_chart6" class="chart"  style="width: 100%; height:360px;"></div>
                                        </div>
                                    </div>
                                </div>
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
    <c:param name="menu" value="finance"/>
    <c:param name="subMenu" value="performance"/>
</c:import>
