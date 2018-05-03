<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.in.layout.Blocks" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <link href="Content/bower_components/bootstrap-datepicker/dist/css/bootstrap-datepicker3.css?v=${static_resource_version}" rel="stylesheet">
    <style type="text/css">
        .user-list th {
            padding: 0.75rem;
        }
        .user-list td {
            padding: 0.3rem 0.75rem;
        }
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/data/operationFinanceLog.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="container-fluid">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <strong>运营财务日志</strong>
                            <small>Operation Finance Log</small>
                        </div>
                        <div class="card-block">
                            <form id="business_form" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                                <div class="form-group row">
                                    <div class="col-md-2">
                                        <select class="form-control" name="businessType">
                                            <option value="0">全部业务类型</option>
                                            <c:forEach var="business" items="${orgFinanceBusinessList}">
                                                <option value="${business.enumValue}">${business.enumNote}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        <select class="form-control" name="baseType">
                                            <option value="0">全部基地</option>
                                            <c:forEach var="business" items="${orgFinanceBaseList}">
                                                <option value="${business.enumValue}">${business.enumNote}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        <select class="form-control" name="channelType">
                                            <option value="0">全部渠道</option>
                                            <c:forEach var="business" items="${orgFinanceChannelList}">
                                                <option value="${business.enumValue}">${business.enumNote}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        <input type="text" class="form-control" placeholder="业务员姓名" name="realName">
                                    </div>
                                    <div class="col-md-2">
                                        <input type="text" class="form-control datepicker" placeholder="业务日期" name="businessDate">
                                    </div>
                                    <div class="col-md-2">
                                        <button type="button" class="btn btn-primary search-business">
                                            <i class="fa fa-search"></i> 检 索
                                        </button>
                                        <a href="/admin/data/operation/finance/edit" class="btn btn-primary pull-right" title="添加">
                                            <i class="fa fa-plus"></i> 添加
                                        </a>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer text-right"></div>
                        <div class="card-block">
                            <table class="table table-striped table-sm user-list">
                                <thead>
                                <tr>
                                    <th>编号</th>
                                    <th>业务日期</th>
                                    <th>所属基地</th>
                                    <th>业务类型</th>
                                    <th>姓名</th>
                                    <th>来源渠道</th>
                                    <th>流水情况</th>
                                    <th>确认收入</th>
                                    <th>在册人数</th>
                                    <th>到课人数</th>
                                    <th>体验数</th>
                                    <th>成交数</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="business" items="${orgFinanceList}" varStatus="loop">
                                    <tr data-id="${business.businessNo}">
                                        <td>${loop.index + 1}</td>
                                        <td>${business.businessDate}</td>
                                        <td>
                                            <c:forEach var="base" items="${orgFinanceBaseList}">
                                                <c:if test="${base.enumValue == business.baseType}">${base.enumNote}</c:if>
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <c:forEach var="busi" items="${orgFinanceBusinessList}">
                                                <c:if test="${busi.enumValue == business.businessType}">${busi.enumNote}</c:if>
                                            </c:forEach>
                                        </td>
                                        <td>${business.realName}</td>
                                        <td>
                                            <c:forEach var="channel" items="${orgFinanceChannelList}">
                                                <c:if test="${channel.enumValue == business.channelType}">${channel.enumNote}</c:if>
                                            </c:forEach>
                                        </td>
                                        <td><fmt:formatNumber value="${business.pipelineValue}" type="currency" maxFractionDigits="0" /></td>
                                        <td><fmt:formatNumber value="${business.incomeValue}" type="currency" maxFractionDigits="0" /></td>
                                        <td>${business.registerCount}人</td>
                                        <td>${business.classCount}人</td>
                                        <td>${business.accessCount}人</td>
                                        <td>${business.businessCount}人</td>
                                        <td>
                                            <a href="/admin/data/operation/finance/edit?businessNo=${business.businessNo}" class="btn btn-primary btn-sm" title="编辑">
                                                <i class="fa fa-pencil"></i> 修改
                                            </a>
                                            <a href="/admin/data/operation/finance/edit" class="btn btn-danger btn-sm" title="编辑">
                                                <i class="fa fa-trash"></i> 删除
                                            </a>
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
                <!--/.col-->
            </div>
            <!--/.row-->
        </div>

    </div>
</layout:override>

<c:import url="../Shared/GeneralLayout.jsp">
    <c:param name="menu" value="data"/>
    <c:param name="subMenu" value="finance_log"/>
</c:import>
