<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.in.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <style type="text/css">

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
                            <strong>订单详情</strong>
                            <small>Orders</small>
                        </div>
                        <div class="card-block">
                            <table class="table table-bordered orders-list">
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
                                <tr data-no="${orgOrders.orderNo}" data-amount="${orgOrders.orderAmount}" data-pay-amount="${orgOrders.payAmount}">
                                    <td class="orders-no">${orgOrders.orderNo}</td>
                                    <td class="orders-type">
                                        <c:forEach var="type" items="${OrderTypeEnum}">
                                            <c:if test="${type.code == orgOrders.orderType}">
                                                ${type.desc}
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td class="orders-amount">¥${orgOrders.orderAmount}元</td>
                                    <td class="orders-pay-type">
                                        <c:forEach var="type" items="${PayTypeEnum}">
                                            <c:if test="${type.code == orgOrders.payType}">
                                                ${type.desc}
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td class="orders-pay-amount">￥${orgOrders.payAmount}元</td>
                                    <td class="orders-date">
                                        <c:forEach var="status" items="${OrderStatusEnum}">
                                            <c:if test="${status.code == orgOrders.orderStatus}">
                                                ${status.desc}
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td class="orders-date">${orgOrders.createTime}</td>
                                    <td>
                                        <c:if test="${orgOrders.orderStatus == 2}">
                                            <button class="btn btn-sm btn-danger goto-payment" title="支付">
                                                <i class="fa fa-money"></i> 支付
                                            </button>
                                            <button class="btn btn-sm btn-warning cancel-payment" title="取消">
                                                <i class="fa fa-remove"></i> 取消
                                            </button>
                                        </c:if>
                                        <c:if test="${orgOrders.orderStatus == 1}">
                                            <button class="btn btn-sm btn-warning back-payment" title="退款">
                                                <i class="fa fa-history"></i> 退款
                                            </button>
                                        </c:if>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-header">
                            <strong>班级信息</strong>
                            <small>Class</small>
                        </div>
                        <div class="card-block">
                            <c:forEach var="cls" items="${orgClassList}">
                                <table class="table table-bordered orders-list">
                                    <tr>
                                        <th class="active">班级名称</th><td>${cls.orgClass.className}</td>
                                        <th class="active">所属场馆</th><td>${cls.orgVenues.venueName}</td>
                                    </tr>
                                    <tr>
                                        <th class="active">授课内容</th><td>${cls.orgCourses.courseName}</td>
                                        <th class="active">执教教练</th><td>${cls.orgCoaches.realName}</td>
                                    </tr>
                                    <tr>
                                        <th class="active">班级价格</th><td>￥${cls.orgClass.classPrice}元</td>
                                        <th class="active"></th><td></td>
                                    </tr>
                                </table>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-header">
                            <strong>学员信息</strong>
                            <small>Users</small>
                        </div>
                        <div class="card-block">
                            <table class="table table-bordered orders-list">
                                <tr><th class="active">真实姓名</th><td>${orgStudents.realName}</td></tr>
                                <tr><th class="active">手机号码</th><td>${orgStudents.mobileSensitive}</td></tr>
                                <tr><th class="active">出生日期</th><td>${orgStudents.birthday}</td></tr>
                                <tr><th class="active">性别</th><td>${orgStudents.sex == 1 ? "男" : "女"}</td></tr>
                                <tr><th class="active">身高</th><td>${orgStudents.height}cm</td></tr>
                                <tr><th class="active">体重</th><td>${orgStudents.weight}kg</td></tr>
                            </table>
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
    <c:param name="subMenu" value="detail"/>
</c:import>
