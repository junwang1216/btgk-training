<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.in.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/finance/performance.js?v=${static_resource_version}"></script>
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

                            <div class="card-actions">
                                <a href="/admin/data/operation/finance/edit" class="btn-settings" title="详情编辑"><i class="icon-note"></i></a>
                                <a href="/admin/data/operation/finance/log" class="btn-list" title="详情日志"><i class="icon-list"></i></a>
                                <a href="/admin/data/operation/finance/settings" class="btn-settings" title="指标设置"><i class="icon-settings"></i></a>
                                <a href="/admin/data/operation/finance" class="btn-list" title="数据统计"><i class="icon-grid"></i></a>
                                <a href="/admin/data/operation/finance/export?typeTime=${typeTime}" class="btn-cloud-download" title="导出数据" target="_blank">
                                    <i class="icon-cloud-download"></i>
                                </a>
                            </div>
                        </div>
                        <div class="card-block">
                            <div class="btn-toolbar" role="toolbar">
                                <div class="btn-group" data-toggle="buttons">
                                    <label class="btn btn-outline-secondary <c:if test="${typeTime == 'prev_day'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type1" value="prev_day"
                                               <c:if test="${typeTime == 'prev_day'}">checked</c:if> >昨 日
                                    </label>
                                    <label class="btn btn-outline-secondary mr-3 <c:if test="${typeTime == 'day'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type2" value="day"
                                               <c:if test="${typeTime == 'day'}">checked</c:if> >今 日
                                    </label>

                                    <label class="btn btn-outline-secondary <c:if test="${typeTime == 'prev_week'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type3" value="prev_week"
                                               <c:if test="${typeTime == 'prev_week'}">checked</c:if> >上 周
                                    </label>
                                    <label class="btn btn-outline-secondary mr-3 <c:if test="${typeTime == 'week'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type4" value="week"
                                               <c:if test="${typeTime == 'week'}">checked</c:if> >本 周
                                    </label>

                                    <label class="btn btn-outline-secondary <c:if test="${typeTime == 'prev_month'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type5" value="prev_month"
                                               <c:if test="${typeTime == 'prev_month'}">checked</c:if> >上 月
                                    </label>
                                    <label class="btn btn-outline-secondary mr-3 <c:if test="${typeTime == 'month'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type6" value="month"
                                               <c:if test="${typeTime == 'month'}">checked</c:if> >本 月
                                    </label>

                                    <label class="btn btn-outline-secondary <c:if test="${typeTime == 'prev_period'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type9" value="prev_period"
                                               <c:if test="${typeTime == 'prev_period'}">checked</c:if> >上 季
                                    </label>
                                    <label class="btn btn-outline-secondary mr-3 <c:if test="${typeTime == 'period'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type10" value="period"
                                               <c:if test="${typeTime == 'period'}">checked</c:if> >本 季
                                    </label>

                                    <label class="btn btn-outline-secondary <c:if test="${typeTime == 'prev_year'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type7" value="prev_year"
                                               <c:if test="${typeTime == 'prev_year'}">checked</c:if> >去 年
                                    </label>
                                    <label class="btn btn-outline-secondary <c:if test="${typeTime == 'year'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type8" value="year"
                                               <c:if test="${typeTime == 'year'}">checked</c:if> >今 年
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="card-footer"></div>
                        <div class="card-block">
                            <div class="row">
                                <div class="col-md-6">
                                    <p>基地业绩汇总</p>
                                    <div id="finance_performance_chart4" class="chart"  style="width: 100%; height:500px;"></div>
                                </div>
                                <div class="col-md-6">
                                    <p>所有基地业绩</p>
                                    <div id="finance_performance_chart3" class="chart"  style="width: 100%; height:500px;"></div>
                                </div>
                                <div class="col-md-6">
                                    <p>方庄基地业绩</p>
                                    <div id="finance_performance_chart2" class="chart"  style="width: 100%; height:500px;"></div>
                                </div>
                                <div class="col-md-6">
                                    <p>莫尔烟业绩</p>
                                    <div id="finance_performance_chart1" class="chart"  style="width: 100%; height:500px;"></div>
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
