<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.in.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript">
        window.summary = {
            labelList : '${labelList}'.replace(/\]|\[|\s/g,"").split(","),
            titleShow : '${titleShow}',
            valuePaymentTotal : ${valuePaymentTotal},
            valuePaymentList  : ${valuePaymentList},
            valueRefundTotal  : ${valueRefundTotal},
            valueRefundList   : ${valueRefundList},
            startTime : '${startTime}',
            endTime   : '${endTime}'
        };
    </script>
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/data/summary.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="container-fluid">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <strong>收支统计</strong>
                            <small>Summary</small>
                        </div>
                        <div class="card-block">
                            <form action="" method="post" class="form-horizontal">
                                <div class="form-group row">
                                    <div class="col-md-12">
                                        <a href="/admin/data/summary?typeTime=prev_month" class="btn btn-outline-secondary">
                                            <i class="fa fa-calendar-minus-o"></i> 上 月
                                        </a>
                                        <a href="/admin/data/summary?typeTime=month" class="btn btn-outline-secondary">
                                            <i class="fa fa-calendar-check-o"></i> 本 月
                                        </a>
                                        <a href="/admin/data/summary?typeTime=prev_year" class="btn btn-outline-secondary">
                                            <i class="fa fa-calendar-minus-o"></i> 去 年
                                        </a>
                                        <a href="/admin/data/summary?typeTime=year" class="btn btn-outline-secondary">
                                            <i class="fa fa-calendar-check-o"></i> 本 年
                                        </a>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer"></div>
                        <div class="card-block">
                            <p>培训收支统计</p>
                            <div class="chart-wrapper" style="height:400px;">
                                <canvas id="data_summary_chart" class="chart" height="400"></canvas>
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
    <c:param name="menu" value="data"/>
    <c:param name="subMenu" value="summary"/>
</c:import>
