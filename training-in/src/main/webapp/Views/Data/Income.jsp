<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.in.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <style type="text/css">
        .income-list th {
            padding: 0.75rem;
        }
        .income-list td {
            padding: 0.3rem 0.75rem;
        }
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/data/income.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="modal fade" id="income_chart" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-primary modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-body row">
                    <div class="col-md-6 text-center">
                        <p>收入类型对比</p>
                        <div id="income_pay_type_chart"></div>
                        <img src="/Content/images/demo/chart1.png" style="width: 100%">
                    </div>
                    <div class="col-md-6 text-center">
                        <p>场馆收入对比</p>
                        <img src="/Content/images/demo/chart1.png" style="width: 100%">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-sm btn-secondary" data-dismiss="modal">
                        <i class="fa fa-close"></i> 关 闭
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="container-fluid">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <strong>收入流水</strong>
                            <small>Income</small>
                        </div>
                        <div class="card-block">
                            <form id="income_form" action="" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                                <div class="form-group row">
                                    <div class="col-md-3">
                                        <c:if test="${condition.typeTime == 'year'}">
                                            <a href="/admin/data/income?typeTime=year" class="btn btn-outline-primary">
                                                <i class="fa fa-calendar-check-o"></i> 本 年
                                            </a>
                                            <a href="/admin/data/income?typeTime=month" class="btn btn-outline-secondary">
                                                <i class="fa fa-calendar-minus-o"></i> 本 月
                                            </a>
                                            <a href="/admin/data/income?typeTime=day" class="btn btn-outline-secondary">
                                                <i class="fa fa-calendar-minus-o"></i> 今 日
                                            </a>
                                        </c:if>
                                        <c:if test="${condition.typeTime == 'month'}">
                                            <a href="/admin/data/income?typeTime=year" class="btn btn-outline-secondary">
                                                <i class="fa fa-calendar-minus-o"></i> 本 年
                                            </a>
                                            <a href="/admin/data/income?typeTime=month" class="btn btn-outline-primary">
                                                <i class="fa fa-calendar-check-o"></i> 本 月
                                            </a>
                                            <a href="/admin/data/income?typeTime=day" class="btn btn-outline-secondary">
                                                <i class="fa fa-calendar-minus-o"></i> 今 日
                                            </a>
                                        </c:if>
                                        <c:if test="${condition.typeTime == 'day'}">
                                            <a href="/admin/data/income?typeTime=year" class="btn btn-outline-secondary">
                                                <i class="fa fa-calendar-minus-o"></i> 本 年
                                            </a>
                                            <a href="/admin/data/income?typeTime=month" class="btn btn-outline-secondary">
                                                <i class="fa fa-calendar-minus-o"></i> 本 月
                                            </a>
                                            <a href="/admin/data/income?typeTime=day" class="btn btn-outline-primary">
                                                <i class="fa fa-calendar-check-o"></i> 今 日
                                            </a>
                                        </c:if>
                                    </div>
                                    <div class="col-md-2">
                                        <input type="text" id="income_start" name="startTime" class="form-control" placeholder="开始日期">
                                    </div>
                                    <div class="col-md-2">
                                        <input type="text" id="income_end" name="endTime" class="form-control" placeholder="结束日期">
                                    </div>
                                    <div class="col-md-5">
                                        <button type="button" class="btn btn-primary search-income">
                                            <i class="fa fa-search"></i> 检 索
                                        </button>
                                        <a href="#" class="btn btn-primary pull-right"
                                           data-target="#income_chart" data-toggle="modal">
                                            <i class="fa fa-bar-chart"></i> 图表展示
                                        </a>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer"></div>
                        <div class="card-block">
                            <ul class="nav nav-tabs" role="tablist">
                                <li class="nav-item active"><a class="nav-link">全部流水</a></li>
                                <li class="nav-item"><a class="nav-link">未审核</a></li>
                                <li class="nav-item"><a class="nav-link">已审核</a></li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane active">
                                    <table class="table table-striped table-sm income-list">
                                        <thead>
                                        <tr>
                                            <th class="income-no">缴费编号</th>
                                            <th class="income-type">缴费类型</th>
                                            <th class="income-type">支付类型</th>
                                            <th class="income-money">缴费金额</th>
                                            <th class="income-date">缴费时间</th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="balance" items="${orgBalanceDataResponseList}" varStatus="loop">
                                            <tr data-id="${balance.balanceNo}">
                                                <td class="income-no">${balance.balanceNo}</td>
                                                <td class="income-type">${balance.balanceType}</td>
                                                <td class="income-type">${balance.balancePayType}</td>
                                                <td class="income-money">¥${balance.balanceAmount}</td>
                                                <td class="income-date">${balance.balanceTime}</td>
                                                <td>
                                                    <button class="btn btn-sm btn-success" title="审核" disabled>
                                                        <i class="fa fa-check"></i> 已审
                                                    </button>
                                                </td>
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
                    </div>
                </div>
            </div>
            <!--/.row-->
        </div>

    </div>
</layout:override>

<c:import url="../Shared/GeneralLayout.jsp">
    <c:param name="menu" value="data"/>
    <c:param name="subMenu" value="income"/>
</c:import>
