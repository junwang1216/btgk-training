<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.in.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <style type="text/css">
        .orders-list th {
            padding: 0.75rem;
        }
        .orders-list td {
            padding: 0.3rem 0.75rem;
        }
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/data/orders.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">

    <%@ include file="../Shared/Payment.jsp" %>

    <%@ include file="../Shared/RefundPayment.jsp" %>

    <div class="container-fluid">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <strong>我的订单</strong>
                            <small>Orders</small>
                        </div>
                        <div class="card-block">
                            <form action="" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                                <div class="form-group row">
                                    <div class="col-md-2">
                                        <input type="text" id="income_en1" name="incomeEnd" class="form-control" placeholder="订单编号">
                                    </div>
                                    <div class="col-md-2">
                                        <input type="text" id="income_start" name="incomeStart" class="form-control" placeholder="开始日期">
                                    </div>
                                    <div class="col-md-2">
                                        <input type="text" id="income_end" name="incomeEnd" class="form-control" placeholder="结束日期">
                                    </div>
                                    <div class="col-md-2">
                                        <select id="income_date" name="incomeDate" class="form-control">
                                            <option>自定义期限</option>
                                            <option>最近一个月</option>
                                            <option>最近三个月</option>
                                            <option>最近六个月</option>
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        <button type="button" class="btn btn-primary">
                                            <i class="fa fa-search"></i> 检 索
                                        </button>
                                    </div>
                                    <div class="col-md-2 text-right">
                                        <a href="#" class="btn btn-outline-secondary">
                                            <i class="fa fa-calendar-minus-o"></i> 昨日
                                        </a>
                                        <a href="#" class="btn btn-outline-secondary">
                                            <i class="fa fa-calendar-check-o"></i> 今日
                                        </a>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer"></div>
                        <div class="card-block">
                            <ul class="nav nav-tabs" role="tablist">
                                <li class="nav-item active"><a class="nav-link">全部订单 <span class="badge badge-pill badge-danger">11</span></a></li>
                                <li class="nav-item"><a class="nav-link">已支付 <span class="badge badge-pill badge-danger">11</span></a></li>
                                <li class="nav-item"><a class="nav-link">未支付 <span class="badge badge-pill badge-danger">11</span></a></li>
                                <li class="nav-item"><a class="nav-link">已取消 <span class="badge badge-pill badge-danger">11</span></a></li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane active">
                                    <table class="table table-striped table-sm orders-list">
                                        <thead>
                                        <tr>
                                            <th class="orders-no">订单编号</th>
                                            <th class="orders-type">订单类型</th>
                                            <th class="orders-amount">订单金额</th>
                                            <th class="orders-pay-type">支付方式</th>
                                            <th class="orders-pay-amount">支付金额</th>
                                            <th class="orders-status">订单状态</th>
                                            <th class="orders-date">订单日期</th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="order" items="${orgOrdersList}">
                                            <tr data-id="${order.orgOrders.id}" data-no="${order.orgOrders.orderNo}" data-amount="${order.orgOrders.orderAmount}" data-pay-amount="${order.orgOrders.payAmount}">
                                                <td class="orders-no">${order.orgOrders.orderNo}</td>
                                                <td class="orders-type">
                                                    <c:forEach var="type" items="${OrderTypeEnum}">
                                                        <c:if test="${type.code == order.orgOrders.orderType}">
                                                            ${type.desc}
                                                        </c:if>
                                                    </c:forEach>
                                                </td>
                                                <td class="orders-amount">¥${order.orgOrders.orderAmount}元</td>
                                                <td class="orders-pay-type">
                                                    <c:forEach var="type" items="${PayTypeEnum}">
                                                        <c:if test="${type.code == order.orgOrders.payType}">
                                                            ${type.desc}
                                                        </c:if>
                                                    </c:forEach>
                                                </td>
                                                <td class="orders-pay-amount">￥${order.orgOrders.payAmount}元</td>
                                                <td class="orders-date">
                                                    <c:forEach var="status" items="${OrderStatusEnum}">
                                                        <c:if test="${status.code == order.orgOrders.orderStatus}">
                                                            ${status.desc}
                                                        </c:if>
                                                    </c:forEach>
                                                </td>
                                                <td class="orders-date">${order.orgOrders.createTime}</td>
                                                <td>
                                                    <a href="/admin/order/detail?orderId=${order.orgOrders.id}" class="btn btn-sm btn-primary" title="查看详情">
                                                        <i class="fa fa-search"></i> 详情
                                                    </a>
                                                    <c:if test="${order.orgOrders.orderStatus == 2}">
                                                        <button class="btn btn-sm btn-danger goto-payment" title="支付">
                                                            <i class="fa fa-money"></i> 支付
                                                        </button>
                                                        <button class="btn btn-sm btn-warning cancel-payment" title="取消">
                                                            <i class="fa fa-remove"></i> 取消
                                                        </button>
                                                    </c:if>
                                                    <c:if test="${order.orgOrders.orderStatus == 1}">
                                                        <button class="btn btn-sm btn-warning back-payment" title="退款">
                                                            <i class="fa fa-history"></i> 退款
                                                        </button>
                                                    </c:if>
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
    <c:param name="subMenu" value="orders"/>
</c:import>
