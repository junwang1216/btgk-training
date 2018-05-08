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
            padding: 0.75rem;
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
    <div class="modal fade" id="month_detail" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-primary" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <table class="table table-responsive table-sm settings-list">
                        <thead>
                        <tr>
                            <th colspan="2">目标日期</th>
                            <th>最低目标</th>
                            <th>挑战目标</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>合计目标</td>
                            <td>01月01日 -- 01月31日</td>
                            <td>20,000元</td>
                            <td>20,000元</td>
                        </tr>
                        <tr>
                            <td>月均目标</td>
                            <td>01月01日 -- 01月31日</td>
                            <td>20,000元</td>
                            <td>20,000元</td>
                        </tr>
                        <tr>
                            <td>01周(3天)</td>
                            <td>01月01日 -- 01月06日</td>
                            <td>20,000元</td>
                            <td>20,000元</td>
                        </tr>
                        <tr>
                            <td>02周(7天)</td>
                            <td>01月01日 -- 01月06日</td>
                            <td>20,000元</td>
                            <td>20,000元</td>
                        </tr>
                        <tr>
                            <td>03周(7天)</td>
                            <td>01月01日 -- 01月06日</td>
                            <td>20,000元</td>
                            <td>20,000元</td>
                        </tr>
                        <tr>
                            <td>04周(7天)</td>
                            <td>01月01日 -- 01月06日</td>
                            <td>20,000元</td>
                            <td>20,000元</td>
                        </tr>
                        <tr>
                            <td>05周(7天)</td>
                            <td>01月01日 -- 01月06日</td>
                            <td>20,000元</td>
                            <td>20,000元</td>
                        </tr>
                        </tbody>
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
                            <form id="finance_flow_form" class="form-horizontal" novalidate onsubmit="return false;">
                                <div class="form-group row">
                                    <div class="col-md-3">
                                        <select class="form-control" name="venueId" id="flow_venue_id">
                                            <c:forEach var="venue" items="${orgFinanceVenuesList}">
                                                <option value="${venue.id}" <c:if test="${venue.id == conditions.venueId}">selected</c:if> >${venue.venueName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-3">
                                        <select class="form-control" name="userId" id="flow_user_id">
                                            <option value="0">所有人</option>
                                        </select>
                                    </div>
                                    <div class="col-md-3">
                                        <select class="form-control">
                                            <c:forEach var="year" items="${yearList}">
                                                <option value="year" <c:if test="${year == conditions.year}">selected</c:if> >${year}年</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-3">
                                        <select class="form-control">
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
                                    <div class="col-md-5">
                                        <input type="text" class="form-control" placeholder="最低目标">
                                    </div>
                                    <div class="col-md-5">
                                        <input type="text" class="form-control" placeholder="挑战目标">
                                    </div>
                                    <div class="col-md-2">
                                        <button type="button" class="btn btn-primary save-finance-flow">
                                            <i class="fa fa-check"></i> 保 存
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer"></div>
                        <div class="card-block">
                            <div class="card">
                                <div class="card-header">
                                    <c:forEach var="venue" items="${orgFinanceVenuesList}">
                                        <c:if test="${venue.id == conditions.venueId}"><strong>${venue.venueName}</strong></c:if>
                                    </c:forEach>

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
                                    <div class="row">
                                        <div class="col-md-6">
                                            <select class="form-control">
                                                <c:forEach var="year" items="${yearList}">
                                                    <option value="year" <c:if test="${year == conditions.year}">selected</c:if> >${year}年</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col-md-6">
                                            <select class="form-control">
                                                <option>整个基地</option>
                                                <c:forEach var="user" items="${orgFinanceUsersList}">
                                                    <option value="${user.id}" <c:if test="${user.id == conditions.userId}">selected</c:if> >"${user.realName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <table class="table table-responsive table-sm settings-list">
                                        <thead>
                                        <tr>
                                            <th>目标对象</th>
                                            <th>目标月份</th>
                                            <th>最低目标</th>
                                            <th>挑战目标</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="flow" items="${flowGoalsList}" varStatus="loop">
                                            <tr>
                                                <td>
                                                    <a href="javascript:;" data-target="#month_detail" data-toggle="modal">
                                                        <i class="fa fa-user"></i> 叶志强
                                                    </a>
                                                </td>
                                                <td>01月份</td>
                                                <td>100,000元</td>
                                                <td>200,000元</td>
                                            </tr>
                                            <c:if test="${loop.index % 3 == 2}">
                                                <tr class="bg-faded">
                                                    <td>
                                                        <i class="fa fa-database"></i> 季度目标
                                                    </td>
                                                    <td>01季度</td>
                                                    <td>300,000元</td>
                                                    <td>600,000元</td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${loop.last}">
                                                <tr class="bg-faded">
                                                    <td><i class="fa fa-database"></i> 整年目标</td>
                                                    <td>2018年</td>
                                                    <td>1,200,000元</td>
                                                    <td>2,400,000元</td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                        <%--<tr>--%>
                                            <%--<td>--%>
                                                <%--<a href="javascript:;" data-target="#month_detail" data-toggle="modal">--%>
                                                    <%--<i class="fa fa-user"></i> 叶志强--%>
                                                <%--</a>--%>
                                            <%--</td>--%>
                                            <%--<td>01月份</td>--%>
                                            <%--<td>100,000元</td>--%>
                                            <%--<td>200,000元</td>--%>
                                        <%--</tr>--%>
                                        <%--<tr>--%>
                                            <%--<td>--%>
                                                <%--<a href="#">--%>
                                                    <%--<i class="fa fa-user"></i> 叶志强--%>
                                                <%--</a>--%>
                                            <%--</td>--%>
                                            <%--<td>02月份</td>--%>
                                            <%--<td>100,000元</td>--%>
                                            <%--<td>200,000元</td>--%>
                                        <%--</tr>--%>
                                        <%--<tr>--%>
                                            <%--<td>--%>
                                                <%--<a href="#">--%>
                                                    <%--<i class="fa fa-user"></i> 叶志强--%>
                                                <%--</a>--%>
                                            <%--</td>--%>
                                            <%--<td>03月份</td>--%>
                                            <%--<td>100,000元</td>--%>
                                            <%--<td>200,000元</td>--%>
                                        <%--</tr>--%>
                                        <%--<tr class="bg-faded">--%>
                                            <%--<td>--%>
                                                <%--<i class="fa fa-database"></i> 季度目标--%>
                                            <%--</td>--%>
                                            <%--<td>01季度</td>--%>
                                            <%--<td>300,000元</td>--%>
                                            <%--<td>600,000元</td>--%>
                                        <%--</tr>--%>
                                        <%--<tr>--%>
                                            <%--<td>--%>
                                                <%--<a href="#">--%>
                                                    <%--<i class="fa fa-user"></i> 叶志强--%>
                                                <%--</a>--%>
                                            <%--</td>--%>
                                            <%--<td>04月份</td>--%>
                                            <%--<td>100,000元</td>--%>
                                            <%--<td>200,000元</td>--%>
                                        <%--</tr>--%>
                                        <%--<tr>--%>
                                            <%--<td>--%>
                                                <%--<a href="#">--%>
                                                    <%--<i class="fa fa-user"></i> 叶志强--%>
                                                <%--</a>--%>
                                            <%--</td>--%>
                                            <%--<td>05月份</td>--%>
                                            <%--<td>100,000元</td>--%>
                                            <%--<td>200,000元</td>--%>
                                        <%--</tr>--%>
                                        <%--<tr>--%>
                                            <%--<td>--%>
                                                <%--<a href="#">--%>
                                                    <%--<i class="fa fa-user"></i> 叶志强--%>
                                                <%--</a>--%>
                                            <%--</td>--%>
                                            <%--<td>06月份</td>--%>
                                            <%--<td>100,000元</td>--%>
                                            <%--<td>200,000元</td>--%>
                                        <%--</tr>--%>
                                        <%--<tr class="bg-faded">--%>
                                            <%--<td><i class="fa fa-database"></i> 季度目标</td>--%>
                                            <%--<td>02季度</td>--%>
                                            <%--<td>300,000元</td>--%>
                                            <%--<td>600,000元</td>--%>
                                        <%--</tr>--%>
                                        <%--<tr>--%>
                                            <%--<td>--%>
                                                <%--<a href="#">--%>
                                                    <%--<i class="fa fa-user"></i> 叶志强--%>
                                                <%--</a>--%>
                                            <%--</td>--%>
                                            <%--<td>07月份</td>--%>
                                            <%--<td>100,000元</td>--%>
                                            <%--<td>200,000元</td>--%>
                                        <%--</tr>--%>
                                        <%--<tr>--%>
                                            <%--<td>--%>
                                                <%--<a href="#">--%>
                                                    <%--<i class="fa fa-user"></i> 叶志强--%>
                                                <%--</a>--%>
                                            <%--</td>--%>
                                            <%--<td>08月份</td>--%>
                                            <%--<td>100,000元</td>--%>
                                            <%--<td>200,000元</td>--%>
                                        <%--</tr>--%>
                                        <%--<tr>--%>
                                            <%--<td>--%>
                                                <%--<a href="#">--%>
                                                    <%--<i class="fa fa-user"></i> 叶志强--%>
                                                <%--</a>--%>
                                            <%--</td>--%>
                                            <%--<td>09月份</td>--%>
                                            <%--<td>100,000元</td>--%>
                                            <%--<td>200,000元</td>--%>
                                        <%--</tr>--%>
                                        <%--<tr class="bg-faded">--%>
                                            <%--<td><i class="fa fa-database"></i> 季度目标</td>--%>
                                            <%--<td>03季度</td>--%>
                                            <%--<td>300,000元</td>--%>
                                            <%--<td>600,000元</td>--%>
                                        <%--</tr>--%>
                                        <%--<tr>--%>
                                            <%--<td>--%>
                                                <%--<a href="#">--%>
                                                    <%--<i class="fa fa-user"></i> 叶志强--%>
                                                <%--</a>--%>
                                            <%--</td>--%>
                                            <%--<td>10月份</td>--%>
                                            <%--<td>100,000元</td>--%>
                                            <%--<td>200,000元</td>--%>
                                        <%--</tr>--%>
                                        <%--<tr>--%>
                                            <%--<td>--%>
                                                <%--<a href="#">--%>
                                                    <%--<i class="fa fa-user"></i> 叶志强--%>
                                                <%--</a>--%>
                                            <%--</td>--%>
                                            <%--<td>11月份</td>--%>
                                            <%--<td>100,000元</td>--%>
                                            <%--<td>200,000元</td>--%>
                                        <%--</tr>--%>
                                        <%--<tr>--%>
                                            <%--<td>--%>
                                                <%--<a href="#">--%>
                                                    <%--<i class="fa fa-user"></i> 叶志强--%>
                                                <%--</a>--%>
                                            <%--</td>--%>
                                            <%--<td>12月份</td>--%>
                                            <%--<td>100,000元</td>--%>
                                            <%--<td>200,000元</td>--%>
                                        <%--</tr>--%>
                                        <%--<tr class="bg-faded">--%>
                                            <%--<td><i class="fa fa-database"></i> 季度目标</td>--%>
                                            <%--<td>04季度</td>--%>
                                            <%--<td>300,000元</td>--%>
                                            <%--<td>600,000元</td>--%>
                                        <%--</tr>--%>
                                        <%--<tr class="bg-faded">--%>
                                            <%--<td><i class="fa fa-database"></i> 整年目标</td>--%>
                                            <%--<td>2018年</td>--%>
                                            <%--<td>1,200,000元</td>--%>
                                            <%--<td>2,400,000元</td>--%>
                                        <%--</tr>--%>
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
                            <form id="finance_income_form" class="form-horizontal" novalidate onsubmit="return false;">
                                <div class="form-group row">
                                    <div class="col-md-3">
                                        <select class="form-control" name="venueId" id="income_venue_id">
                                            <c:forEach var="venue" items="${orgFinanceVenuesList}">
                                                <option value="${venue.id}" <c:if test="${venue.id == conditions.venueId}">selected</c:if> >${venue.venueName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-3">
                                        <select class="form-control" name="userId" id="income_user_id">
                                            <option value="0">所有人</option>
                                        </select>
                                    </div>
                                    <div class="col-md-3">
                                        <select class="form-control">
                                            <c:forEach var="year" items="${yearList}">
                                                <option value="year" <c:if test="${year == conditions.year}">selected</c:if> >${year}年</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-3">
                                        <select class="form-control">
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
                                    <div class="col-md-5">
                                        <input type="text" class="form-control" placeholder="最低目标">
                                    </div>
                                    <div class="col-md-5">
                                        <input type="text" class="form-control" placeholder="挑战目标">
                                    </div>
                                    <div class="col-md-2">
                                        <button type="button" class="btn btn-primary save-finance-income">
                                            <i class="fa fa-check"></i> 保 存
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer"></div>
                        <div class="card-block">
                            <div class="card">
                                <div class="card-header">
                                    <c:forEach var="venue" items="${orgFinanceVenuesList}">
                                        <c:if test="${venue.id == conditions.venueId}"><strong>${venue.venueName}</strong></c:if>
                                    </c:forEach>

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
                                    <div class="row">
                                        <div class="col-md-6">
                                            <select class="form-control">
                                                <c:forEach var="year" items="${yearList}">
                                                    <option value="year" <c:if test="${year == conditions.year}">selected</c:if> >${year}年</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col-md-6">
                                            <select class="form-control">
                                                <option>整个基地</option>
                                                <c:forEach var="user" items="${orgFinanceUsersList}">
                                                    <option value="${user.id}" <c:if test="${user.id == conditions.userId}">selected</c:if> >"${user.realName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <table class="table table-responsive table-sm settings-list">
                                        <thead>
                                        <tr>
                                            <th>目标对象</th>
                                            <th>目标月份</th>
                                            <th>最低目标</th>
                                            <th>挑战目标</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="flow" items="${incomeGoalsList}" varStatus="loop">
                                            <tr>
                                                <td>
                                                    <a href="javascript:;" data-target="#month_detail" data-toggle="modal">
                                                        <i class="fa fa-user"></i> 叶志强
                                                    </a>
                                                </td>
                                                <td>01月份</td>
                                                <td>100,000元</td>
                                                <td>200,000元</td>
                                            </tr>
                                            <c:if test="${loop.index % 3 == 2}">
                                                <tr class="bg-faded">
                                                    <td>
                                                        <i class="fa fa-database"></i> 季度目标
                                                    </td>
                                                    <td>01季度</td>
                                                    <td>300,000元</td>
                                                    <td>600,000元</td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${loop.last}">
                                                <tr class="bg-faded">
                                                    <td><i class="fa fa-database"></i> 整年目标</td>
                                                    <td>2018年</td>
                                                    <td>1,200,000元</td>
                                                    <td>2,400,000元</td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
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
