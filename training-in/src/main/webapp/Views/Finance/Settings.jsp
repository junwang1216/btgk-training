<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.in.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <style type="text/css">
        .settings-list th {
            padding: 0.75rem;
        }
        .settings-list td {
            padding: 0.45rem;
        }
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
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/finance/settings.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <c:forEach var="venue" items="${orgFinanceVenuesList}">
        <c:if test="${venue.id == conditions.venueId}">
            <c:set var="venueName" value="${venue.venueName}"/>
        </c:if>
    </c:forEach>

    <div class="modal fade" id="month_detail" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-primary" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <table class="table table-responsive table-sm settings-list month-detail-list">
                        <thead>
                        <tr>
                            <th colspan="2">目标日期</th>
                            <th>最低目标</th>
                            <th>挑战目标</th>
                        </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-sm btn-secondary" data-dismiss="modal">
                        <i class="fa fa-close"></i> 关 闭
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="flow_edit" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-primary" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <form id="finance_flow_form" class="form-horizontal" novalidate onsubmit="return false;">
                        <input type="hidden" name="id" id="flow_id">
                        <input type="hidden" name="busType" value="${conditions.busType}">
                        <input type="hidden" name="goalType" value="1">
                        <div class="form-group row">
                            <div class="col-md-6">
                                <select class="form-control" name="venueId" id="flow_venue_id">
                                    <c:forEach var="venue" items="${orgFinanceVenuesList}">
                                        <option value="${venue.id}" <c:if test="${venue.id == conditions.venueId}">selected</c:if> >${venue.venueName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <select class="form-control" name="userId" id="flow_user_id">
                                    <option value="0">所有人</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-md-6">
                                <select class="form-control" name="year">
                                    <c:forEach var="year" items="${yearList}">
                                        <option value="${year}" <c:if test="${year == conditions.year}">selected</c:if> >${year}年</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <select class="form-control" name="month">
                                    <option value="0">月均值</option>
                                    <option value="1">01月</option>
                                    <option value="2">02月</option>
                                    <option value="3">03月</option>
                                    <option value="4">04月</option>
                                    <option value="5">05月</option>
                                    <option value="6">06月</option>
                                    <option value="7">07月</option>
                                    <option value="8">08月</option>
                                    <option value="9">09月</option>
                                    <option value="10">10月</option>
                                    <option value="11">11月</option>
                                    <option value="12">12月</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-md-6">
                                <input type="text" class="form-control" placeholder="最低目标" name="minValue">
                            </div>
                            <div class="col-md-6">
                                <input type="text" class="form-control" placeholder="挑战目标" name="maxValue">
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-sm btn-secondary" data-dismiss="modal">
                        <i class="fa fa-close"></i> 取 消
                    </button>
                    <button type="button" class="btn btn-sm btn-primary save-finance-flow">
                        <i class="fa fa-check"></i> 保 存
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="income_edit" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-primary" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <form id="finance_income_form" class="form-horizontal" novalidate onsubmit="return false;">
                        <input type="hidden" name="id" id="income_id">
                        <input type="hidden" name="busType" value="${conditions.busType}">
                        <input type="hidden" name="goalType" value="2">
                        <div class="form-group row">
                            <div class="col-md-6">
                                <select class="form-control" name="venueId" id="income_venue_id">
                                    <c:forEach var="venue" items="${orgFinanceVenuesList}">
                                        <option value="${venue.id}" <c:if test="${venue.id == conditions.venueId}">selected</c:if> >${venue.venueName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <select class="form-control" name="userId" id="income_user_id">
                                    <option value="0">所有人</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-md-6">
                                <select class="form-control" name="year">
                                    <c:forEach var="year" items="${yearList}">
                                        <option value="${year}" <c:if test="${year == conditions.year}">selected</c:if> >${year}年</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <select class="form-control" name="month">
                                    <option value="0">月均值</option>
                                    <option value="1">01月</option>
                                    <option value="2">02月</option>
                                    <option value="3">03月</option>
                                    <option value="4">04月</option>
                                    <option value="5">05月</option>
                                    <option value="6">06月</option>
                                    <option value="7">07月</option>
                                    <option value="8">08月</option>
                                    <option value="9">09月</option>
                                    <option value="10">10月</option>
                                    <option value="11">11月</option>
                                    <option value="12">12月</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-md-6">
                                <input type="text" class="form-control" placeholder="最低目标" name="minValue">
                            </div>
                            <div class="col-md-6">
                                <input type="text" class="form-control" placeholder="挑战目标" name="maxValue">
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-sm btn-secondary" data-dismiss="modal">
                        <i class="fa fa-close"></i> 取 消
                    </button>
                    <button type="button" class="btn btn-sm btn-primary save-finance-income">
                        <i class="fa fa-check"></i> 保 存
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="container-fluid">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-header">
                            <strong>流水指标设置</strong>
                            <small>Operation Finance Settings</small>

                            <div class="card-actions">
                                <a href="#" class="btn-options-vertical dropdown-toggle" title="切换类型" data-toggle="dropdown">
                                    <i class="icon-options-vertical"></i>
                                </a>
                                <div class="dropdown-menu dropdown-menu-right">
                                    <c:forEach var="type" items="${BusinessTypeEnumList}">
                                        <c:if test="${type.code == conditions.busType}">
                                            <a class="dropdown-item active" href="javascript:;">${type.desc}</a>
                                        </c:if>
                                        <c:if test="${type.code != conditions.busType}">
                                            <a class="dropdown-item"
                                               href="/admin/finance/settings?busType=${type.code}&goalType=${conditions.goalType}&venueId=${conditions.venueId}&year=${conditions.year}">${type.desc}</a>
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                        <div class="card-block">
                            <form id="finance_flow_query_form" class="form-horizontal row" novalidate onsubmit="return false;">
                                <div class="col-md-4">
                                    <select class="form-control" name="year">
                                        <c:forEach var="year" items="${yearList}">
                                            <option value="${year}" <c:if test="${year == conditions.year}">selected</c:if> >${year}年</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-4">
                                    <select class="form-control" name="userId">
                                        <option value="0">整个基地</option>
                                        <c:forEach var="user" items="${orgFinanceUsersList}">
                                            <option value="${user.id}" <c:if test="${user.id == conditions.userId}">selected</c:if> >${user.realName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-4">
                                    <button type="button" class="btn btn-primary search-finance-flow">
                                        <i class="fa fa-search"></i> 检 索
                                    </button>
                                    <button type="button" class="btn btn-primary add-finance-flow"
                                            data-target="#flow_edit" data-toggle="modal">
                                        <i class="fa fa-plus"></i> 添 加
                                    </button>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer"></div>
                        <div class="card-block">
                            <div class="card">
                                <div class="card-header">
                                    <strong>${venueName}</strong>

                                    <div class="card-actions">
                                        <a href="javascript:;" class="btn-direction dropdown-toggle" title="切换基地" data-toggle="dropdown">
                                            <i class="icon-direction"></i>
                                        </a>
                                        <div class="dropdown-menu dropdown-menu-right">
                                            <c:forEach var="venue" items="${orgFinanceVenuesList}">
                                                <c:if test="${venue.id == conditions.venueId}">
                                                    <a class="dropdown-item active" href="javascript:;">${venue.venueName}</a>
                                                </c:if>
                                                <c:if test="${venue.id != conditions.venueId}">
                                                    <a class="dropdown-item"
                                                       href="/admin/finance/settings?busType=${conditions.busType}&goalType=${conditions.goalType}&venueId=${venue.id}&year=${conditions.year}">${venue.venueName}</a>
                                                </c:if>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-block">
                                    <table class="table table-responsive table-sm settings-list">
                                        <thead>
                                        <tr>
                                            <th>目标对象</th>
                                            <th>目标月份</th>
                                            <th>最低目标</th>
                                            <th>挑战目标</th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:if test="${flowGoalsList.size() < 12}">
                                            <c:forEach var="flow" items="${flowGoalsList}" varStatus="loop">
                                                <tr>
                                                    <td>
                                                        <c:if test="${flow.id > 0}">
                                                            <a href="javascript:;" class="flow-user" data-target="#month_detail" data-toggle="modal"
                                                               data-id="${flow.id}">
                                                                <i class="fa fa-user"></i> ${flow.userName}
                                                            </a>
                                                        </c:if>
                                                        <c:if test="${flow.id <= 0}">
                                                            <a href="javascript:;" class="flow-user" data-target="#month_detail" data-toggle="modal"
                                                               data-venue="${flow.venueId}" data-busType="${flow.busType}"
                                                               data-goalType="${flow.goalType}" data-year="${flow.year}" data-month="${flow.month}">
                                                                <i class="fa fa-home"></i> ${venueName}
                                                            </a>
                                                        </c:if>
                                                    </td>
                                                    <td>${flow.month}月份</td>
                                                    <td>${flow.minValue}元</td>
                                                    <td>${flow.maxValue}元</td>
                                                    <td>
                                                        <c:if test="${flow.id > 0}">
                                                            <a href="javascript:;" class="btn btn-sm btn-link edit-finance-flow" data-id="${flow.id}"
                                                               data-target="#flow_edit" data-toggle="modal">
                                                                <i class="fa fa-edit"></i>
                                                            </a>
                                                        </c:if>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </c:if>
                                        <c:if test="${flowGoalsList.size() == 17}">
                                            <c:forEach var="flow" items="${flowGoalsList}" varStatus="loop">
                                                <c:if test="${loop.index != 3 && loop.index != 7 && loop.index != 11 && loop.index != 15 && loop.index != 16}">
                                                    <tr>
                                                        <td>
                                                            <c:if test="${flow.id > 0}">
                                                                <a href="javascript:;" class="flow-user" data-target="#month_detail" data-toggle="modal"
                                                                   data-id="${flow.id}">
                                                                    <i class="fa fa-user"></i> ${flow.userName}
                                                                </a>
                                                            </c:if>
                                                            <c:if test="${flow.id <= 0}">
                                                                <a href="javascript:;" class="flow-user" data-target="#month_detail" data-toggle="modal"
                                                                   data-venue="${flow.venueId}" data-busType="${flow.busType}"
                                                                   data-goalType="${flow.goalType}" data-year="${flow.year}" data-month="${flow.month}">
                                                                    <i class="fa fa-home"></i> ${venueName}
                                                                </a>
                                                            </c:if>
                                                        </td>
                                                        <td>${flow.month}月份</td>
                                                        <td>${flow.minValue}元</td>
                                                        <td>${flow.maxValue}元</td>
                                                        <td>
                                                            <c:if test="${flow.id > 0}">
                                                                <a href="javascript:;" class="btn btn-sm btn-link edit-finance-flow" data-id="${flow.id}"
                                                                   data-target="#flow_edit" data-toggle="modal">
                                                                    <i class="fa fa-edit"></i>
                                                                </a>
                                                            </c:if>
                                                        </td>
                                                    </tr>
                                                </c:if>
                                                <c:if test="${loop.index == 3 || loop.index == 7 || loop.index == 11 || loop.index == 15}">
                                                    <tr class="bg-faded">
                                                        <td><i class="fa fa-database"></i> 季度目标</td>
                                                        <td>${flow.userName}</td>
                                                        <td>${flow.minValue}元</td>
                                                        <td>${flow.maxValue}元</td>
                                                        <td></td>
                                                    </tr>
                                                </c:if>
                                                <c:if test="${loop.index == 16}">
                                                    <tr class="bg-faded">
                                                        <td><i class="fa fa-database"></i> 整年目标</td>
                                                        <td>${flow.userName}</td>
                                                        <td>${flow.minValue}元</td>
                                                        <td>${flow.maxValue}元</td>
                                                        <td></td>
                                                    </tr>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-header">
                            <strong>确认收入指标设置</strong>
                            <small>Operation Finance Settings</small>

                            <div class="card-actions">
                                <a href="#" class="btn-options-vertical dropdown-toggle" title="切换类型" data-toggle="dropdown">
                                    <i class="icon-options-vertical"></i>
                                </a>
                                <div class="dropdown-menu dropdown-menu-right">
                                    <c:forEach var="type" items="${BusinessTypeEnumList}">
                                        <c:if test="${type.code == conditions.busType}">
                                            <a class="dropdown-item active" href="javascript:;">${type.desc}</a>
                                        </c:if>
                                        <c:if test="${type.code != conditions.busType}">
                                            <a class="dropdown-item"
                                               href="/admin/finance/settings?busType=${type.code}&goalType=${conditions.goalType}&venueId=${conditions.venueId}&year=${conditions.year}">${type.desc}</a>
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                        <div class="card-block">
                            <form id="finance_income_query_form" class="form-horizontal row" novalidate onsubmit="return false;">
                                <div class="col-md-4">
                                    <select class="form-control" name="year">
                                        <c:forEach var="year" items="${yearList}">
                                            <option value="${year}" <c:if test="${year == conditions.year}">selected</c:if> >${year}年</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-4">
                                    <select class="form-control" name="userId">
                                        <option value="0">整个基地</option>
                                        <c:forEach var="user" items="${orgFinanceUsersList}">
                                            <option value="${user.id}" <c:if test="${user.id == conditions.userId}">selected</c:if> >${user.realName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-4">
                                    <button type="button" class="btn btn-primary search-finance-income">
                                        <i class="fa fa-search"></i> 检 索
                                    </button>
                                    <button type="button" class="btn btn-primary add-finance-income"
                                            data-target="#income_edit" data-toggle="modal">
                                        <i class="fa fa-plus"></i> 添 加
                                    </button>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer"></div>
                        <div class="card-block">
                            <div class="card">
                                <div class="card-header">
                                    <strong>${venueName}</strong>

                                    <div class="card-actions">
                                        <a href="javascript:;" class="btn-direction dropdown-toggle" title="切换基地" data-toggle="dropdown">
                                            <i class="icon-direction"></i>
                                        </a>
                                        <div class="dropdown-menu dropdown-menu-right">
                                            <c:forEach var="venue" items="${orgFinanceVenuesList}">
                                                <c:if test="${venue.id == conditions.venueId}">
                                                    <a class="dropdown-item active" href="javascript:;">${venue.venueName}</a>
                                                </c:if>
                                                <c:if test="${venue.id != conditions.venueId}">
                                                    <a class="dropdown-item"
                                                       href="/admin/finance/settings?busType=${conditions.busType}&goalType=${conditions.goalType}&venueId=${venue.id}&year=${conditions.year}">${venue.venueName}</a>
                                                </c:if>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-block">
                                    <table class="table table-responsive table-sm settings-list">
                                        <thead>
                                        <tr>
                                            <th>目标对象</th>
                                            <th>目标月份</th>
                                            <th>最低目标</th>
                                            <th>挑战目标</th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:if test="${incomeGoalsList.size() < 12}">
                                            <c:forEach var="income" items="${incomeGoalsList}" varStatus="loop">
                                                <tr>
                                                    <td>
                                                        <c:if test="${income.id > 0}">
                                                            <a href="javascript:;" class="flow-user" data-target="#month_detail" data-toggle="modal"
                                                               data-id="${income.id}">
                                                                <i class="fa fa-user"></i> ${income.userName}
                                                            </a>
                                                        </c:if>
                                                        <c:if test="${income.id <= 0}">
                                                            <a href="javascript:;" class="flow-user" data-target="#month_detail" data-toggle="modal"
                                                               data-venue="${income.venueId}" data-busType="${income.busType}"
                                                               data-goalType="${income.goalType}" data-year="${income.year}" data-month="${income.month}">
                                                                <i class="fa fa-home"></i> ${venueName}
                                                            </a>
                                                        </c:if>
                                                    </td>
                                                    <td>${income.month}月份</td>
                                                    <td>${income.minValue}元</td>
                                                    <td>${income.maxValue}元</td>
                                                    <td>
                                                        <c:if test="${income.id > 0}">
                                                            <a href="javascript:;" class="btn btn-sm btn-link edit-finance-income" data-id="${income.id}"
                                                               data-target="#income_edit" data-toggle="modal">
                                                                <i class="fa fa-edit"></i>
                                                            </a>
                                                        </c:if>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </c:if>
                                        <c:if test="${incomeGoalsList.size() == 17}">
                                            <c:forEach var="income" items="${incomeGoalsList}" varStatus="loop">
                                                <c:if test="${loop.index != 3 && loop.index != 7 && loop.index != 11 && loop.index != 15 && loop.index != 16}">
                                                    <tr>
                                                        <td>
                                                            <c:if test="${income.id > 0}">
                                                                <a href="javascript:;" class="flow-user" data-target="#month_detail" data-toggle="modal"
                                                                   data-id="${income.id}">
                                                                    <i class="fa fa-user"></i> ${income.userName}
                                                                </a>
                                                            </c:if>
                                                            <c:if test="${income.id <= 0}">
                                                                <a href="javascript:;" class="flow-user" data-target="#month_detail" data-toggle="modal"
                                                                   data-venue="${income.venueId}" data-busType="${income.busType}"
                                                                   data-goalType="${income.goalType}" data-year="${income.year}" data-month="${income.month}">
                                                                    <i class="fa fa-home"></i> ${venueName}
                                                                </a>
                                                            </c:if>
                                                        </td>
                                                        <td>${income.month}月份</td>
                                                        <td>${income.minValue}元</td>
                                                        <td>${income.maxValue}元</td>
                                                        <td>
                                                            <c:if test="${income.id > 0}">
                                                                <a href="javascript:;" class="btn btn-sm btn-link edit-finance-income" data-id="${income.id}"
                                                                   data-target="#income_edit" data-toggle="modal">
                                                                    <i class="fa fa-edit"></i>
                                                                </a>
                                                            </c:if>
                                                        </td>
                                                    </tr>
                                                </c:if>
                                                <c:if test="${loop.index == 3 || loop.index == 7 || loop.index == 11 || loop.index == 15}">
                                                    <tr class="bg-faded">
                                                        <td><i class="fa fa-database"></i> 季度目标</td>
                                                        <td>${income.userName}</td>
                                                        <td>${income.minValue}元</td>
                                                        <td>${income.maxValue}元</td>
                                                        <td></td>
                                                    </tr>
                                                </c:if>
                                                <c:if test="${loop.index == 16}">
                                                    <tr class="bg-faded">
                                                        <td><i class="fa fa-database"></i> 整年目标</td>
                                                        <td>${income.userName}</td>
                                                        <td>${income.minValue}元</td>
                                                        <td>${income.maxValue}元</td>
                                                        <td></td>
                                                    </tr>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                        </tbody>
                                    </table>
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
    <c:param name="subMenu" value="settings"/>
</c:import>
