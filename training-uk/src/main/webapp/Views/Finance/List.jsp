<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.uk.layout.Blocks" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <link href="Content/bower_components/bootstrap-datepicker/dist/css/bootstrap-datepicker3.css?v=${static_resource_version}" rel="stylesheet">
    <style type="text/css">
        .flow-list th, .business-list th, .income-list th, .attendance-list th, .times-list th {
            padding: 0.75rem;
        }
        .flow-list td, .business-list td, .income-list td, .attendance-list td, .times-list td {
            padding: 0.3rem 0.75rem;
        }
        .finance-tab-type {
            background: none;
            margin: 0;
            padding: 0;
        }
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/finance/list.js?v=${static_resource_version}"></script>
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
                                <input type="hidden" name="tabType" value="${conditions.tabType}">
                                <div class="form-group row">
                                    <div class="col-md-2">
                                        <select class="form-control" name="busType">
                                            <option value="-1">全部业务类型</option>
                                            <c:forEach var="business" items="${BusinessTypeEnumList}">
                                                <option value="${business.code}" <c:if test="${business.code == conditions.busType}">selected</c:if> >${business.desc}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        <select class="form-control" name="venueId">
                                            <option value="0">全部基地</option>
                                            <c:forEach var="venue" items="${orgFinanceVenuesList}">
                                                <option value="${venue.id}" <c:if test="${venue.id == conditions.venueId}">selected</c:if> >${venue.venueName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-8">
                                        <button type="button" class="btn btn-primary search-business">
                                            <i class="fa fa-search"></i> 检 索
                                        </button>
                                        <a href="/admin/finance/add" class="btn btn-primary pull-right" title="添加">
                                            <i class="fa fa-plus"></i> 添 加
                                        </a>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer text-right">
                            <nav class="breadcrumb finance-tab-type">
                                <a class="breadcrumb-item <c:if test="${conditions.tabType == 1}">active</c:if>" href="/admin/finance/list?tabType=1">流水</a>
                                <a class="breadcrumb-item <c:if test="${conditions.tabType == 2}">active</c:if>" href="/admin/finance/list?tabType=2">体验成交</a>
                                <a class="breadcrumb-item <c:if test="${conditions.tabType == 3}">active</c:if>" href="/admin/finance/list?tabType=3">确认收入</a>
                                <a class="breadcrumb-item <c:if test="${conditions.tabType == 4}">active</c:if>" href="/admin/finance/list?tabType=4">在册到课</a>
                                <a class="breadcrumb-item <c:if test="${conditions.tabType == 5}">active</c:if>" href="/admin/finance/list?tabType=5">闲忙时段</a>
                            </nav>
                        </div>
                        <div class="card-block">
                            <c:if test="${conditions.tabType == 1}">
                                <table class="table table-striped table-responsive-sm table-sm flow-list">
                                    <thead>
                                    <tr>
                                        <th>业务编号</th>
                                        <th>业务日期</th>
                                        <th>业务类型</th>
                                        <th>所属基地</th>
                                        <th>真实姓名</th>
                                        <th>渠道来源</th>
                                        <th>流水值</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="business" items="${orgFinanceDataList}" varStatus="loop">
                                        <tr data-id="${business.businessNo}">
                                            <td>${business.businessNo}</td>
                                            <td>${business.businessDate}</td>
                                            <td>${business.venueName}</td>
                                            <td>${business.businessTitle}</td>
                                            <td>${business.realName}</td>
                                            <td>${business.channelName}</td>
                                            <td><fmt:formatNumber value="${business.pipelineValue}" type="currency" maxFractionDigits="0" /></td>
                                            <td>
                                                <a href="javascript:;" class="btn btn-danger btn-sm btn-delete" title="删除" data-business="${business.businessNo}">
                                                    <i class="fa fa-trash"></i> 删 除
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>
                            <c:if test="${conditions.tabType == 2}">
                                <table class="table table-striped table-responsive-sm table-sm business-list">
                                    <thead>
                                    <tr>
                                        <th>业务编号</th>
                                        <th>业务日期</th>
                                        <th>业务类型</th>
                                        <th>所属基地</th>
                                        <th>真实姓名</th>
                                        <th>渠道来源</th>
                                        <th>体验数</th>
                                        <th>成交数</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="business" items="${orgFinanceDataList}" varStatus="loop">
                                        <tr data-id="${business.businessNo}">
                                            <td>${business.businessNo}</td>
                                            <td>${business.businessDate}</td>
                                            <td>${business.venueName}</td>
                                            <td>${business.businessTitle}</td>
                                            <td>${business.realName}</td>
                                            <td>${business.channelName}</td>
                                            <td>${business.accessCount}人</td>
                                            <td>${business.businessCount}人</td>
                                            <td>
                                                <a href="javascript:;" class="btn btn-danger btn-sm btn-delete" title="删除" data-business="${business.businessNo}">
                                                    <i class="fa fa-trash"></i> 删 除
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>
                            <c:if test="${conditions.tabType == 3}">
                                <table class="table table-striped table-responsive-sm table-sm income-list">
                                    <thead>
                                    <tr>
                                        <th>业务编号</th>
                                        <th>业务日期</th>
                                        <th>业务类型</th>
                                        <th>所属基地</th>
                                        <th>真实姓名</th>
                                        <th>收入类型</th>
                                        <th>课单价</th>
                                        <th>确认收入</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="business" items="${orgFinanceDataList}" varStatus="loop">
                                        <tr data-id="${business.businessNo}">
                                            <td>${business.businessNo}</td>
                                            <td>${business.businessDate}</td>
                                            <td>${business.venueName}</td>
                                            <td>${business.businessTitle}</td>
                                            <td>${business.realName}</td>
                                            <td>${business.incomeType}</td>
                                            <td>${business.incomePerValue}元</td>
                                            <td>${business.incomeValue}元</td>
                                            <td>
                                                <a href="javascript:;" class="btn btn-danger btn-delete" title="删除" data-business="${business.businessNo}">
                                                    <i class="fa fa-trash"></i> 删 除
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>
                            <c:if test="${conditions.tabType == 4}">
                                <table class="table table-striped table-responsive-sm table-sm attendance-list">
                                    <thead>
                                    <tr>
                                        <th>业务编号</th>
                                        <th>业务日期</th>
                                        <th>业务类型</th>
                                        <th>所属基地</th>
                                        <th>真实姓名</th>
                                        <th>收入类型</th>
                                        <th>在册数</th>
                                        <th>到课数</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="business" items="${orgFinanceDataList}" varStatus="loop">
                                        <tr data-id="${business.businessNo}">
                                            <td>${business.businessNo}</td>
                                            <td>${business.businessDate}</td>
                                            <td>${business.venueName}</td>
                                            <td>${business.businessTitle}</td>
                                            <td>${business.realName}</td>
                                            <td>${business.incomeType}</td>
                                            <td>${business.registerCount}人</td>
                                            <td>${business.classCount}人</td>
                                            <td>
                                                <a href="javascript:;" class="btn btn-danger btn-delete" title="删除" data-business="${business.businessNo}">
                                                    <i class="fa fa-trash"></i> 删 除
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>
                            <c:if test="${conditions.tabType == 5}">
                                <table class="table table-striped table-responsive-sm table-sm times-list">
                                    <thead>
                                    <tr>
                                        <th>业务编号</th>
                                        <th>业务日期</th>
                                        <th>业务类型</th>
                                        <th>所属基地</th>
                                        <th>真实姓名</th>
                                        <th>闲时总数</th>
                                        <th>闲时占用</th>
                                        <th>忙时总数</th>
                                        <th>忙时占用</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="business" items="${orgFinanceDataList}" varStatus="loop">
                                        <tr data-id="${business.businessNo}">
                                            <td>${business.businessNo}</td>
                                            <td>${business.businessDate}</td>
                                            <td>${business.venueName}</td>
                                            <td>${business.businessTitle}</td>
                                            <td>${business.realName}</td>
                                            <td>${business.nullTotalCount}时</td>
                                            <td>${business.nullCount}时</td>
                                            <td>${business.hotTotalCount}时</td>
                                            <td>${business.hotCount}时</td>
                                            <td>
                                                <a href="javascript:;" class="btn btn-danger btn-delete" title="删除" data-business="${business.businessNo}">
                                                    <i class="fa fa-trash"></i> 删 除
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>
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
    <c:param name="menu" value="finance"/>
    <c:param name="subMenu" value="record"/>
</c:import>
