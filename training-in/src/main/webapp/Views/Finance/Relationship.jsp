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
            data-main="Content/js/app/finance/relationship.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="modal fade" id="relationship_user" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-primary" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <form id="relationship_user_form" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                        <input type="hidden" id="relationship_user_id" name="id" value="">
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label" for="relationship_user_name">
                                <span class="text-danger">*</span> 姓名
                            </label>
                            <div class="col-md-9">
                                <input type="text" class="form-control" id="relationship_user_name" name="realName" placeholder="请输入姓名"
                                       data-val="true" data-val-required="姓名不能为空"
                                       data-val-length-max="20" data-val-length-min="2" data-val-length="姓名必须包含 2~20 个字符">
                                <div data-valmsg-for="userName" data-valmsg-replace="true"></div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label" for="relationship_user_mobile">
                                <span class="text-danger">*</span> 手机号码
                            </label>
                            <div class="col-md-9">
                                <input type="text" class="form-control" id="relationship_user_mobile" name="mobile" placeholder="请输入手机号码"
                                       data-val="true" data-val-required="手机号码不能为空"
                                       data-val-regex-pattern="^(13[0-9]|15[012356789]|166|17[0-9]|18[02356789]|14[57]|19[89])[0-9]{8}$" data-val-regex="手机号码格式不正确">
                                <div data-valmsg-for="userMobile" data-valmsg-replace="true"></div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label" for="info_venue_id">
                                <span class="text-danger">*</span> 所属基地
                            </label>
                            <div class="col-md-9">
                                <select class="form-control" id="info_venue_id" name="venueId" data-val="true" data-val-required="请选择所属基地">
                                    <option value="">选择所属基地</option>
                                    <c:forEach var="venue" items="${orgFinanceVenuesList}" varStatus="loop">
                                        <option value="${venue.id}">${venue.venueName}</option>
                                    </c:forEach>
                                </select>
                                <div data-valmsg-for="venueId" data-valmsg-replace="true"></div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-sm btn-secondary" data-dismiss="modal">
                        <i class="fa fa-close"></i> 取 消
                    </button>
                    <button type="button" class="btn btn-sm btn-primary save-relationship-user">
                        <i class="fa fa-check"></i> 保 存
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="relationship_venue" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-primary modal-sm" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <form id="relationship_venue_form" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                        <input type="hidden" id="relationship_venue_id" name="id" value="">
                        <div class="form-group row">
                            <div class="col-md-12">
                                <input type="text" class="form-control" id="relationship_venue_name" name="venueName" placeholder="请输入基地名称"
                                       data-val="true" data-val-required="基地名称不能为空"
                                       data-val-length-max="20" data-val-length-min="2" data-val-length="基地名称必须包含 2~20 个字符">
                                <div data-valmsg-for="venueName" data-valmsg-replace="true"></div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-sm btn-secondary" data-dismiss="modal">
                        <i class="fa fa-close"></i> 取 消
                    </button>
                    <button type="button" class="btn btn-sm btn-primary save-relationship-venue">
                        <i class="fa fa-check"></i> 保 存
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
                            <strong>财务组织关系</strong>
                            <small>Organization Relationship</small>
                        </div>
                        <div class="card-block">
                            <form id="relationship_form" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                                <div class="form-group row">
                                    <div class="col-md-2">
                                        <select class="form-control" name="venueId">
                                            <option value="">全部基地</option>
                                            <c:forEach var="venue" items="${orgFinanceVenuesList}" varStatus="loop">
                                                <option value="${venue.id}">${venue.venueName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-10">
                                        <button type="button" class="btn btn-primary">
                                            <i class="fa fa-search"></i> 检 索
                                        </button>
                                        <a href="javascript:;" class="btn btn-primary pull-right user-relationship mr-1" title="添加用户"
                                           data-target="#relationship_user" data-toggle="modal">
                                            <i class="fa fa-plus"></i> 添加用户
                                        </a>
                                        <a href="javascript:;" class="btn btn-primary pull-right venue-relationship mr-1" title="添加基地"
                                           data-target="#relationship_venue" data-toggle="modal">
                                            <i class="fa fa-plus"></i> 添加基地
                                        </a>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer"></div>
                        <div class="card-block row">
                            <div class="col-md-5">
                                <table class="table table-striped table-sm">
                                    <thead>
                                    <tr>
                                        <th>##</th>
                                        <th>姓名</th>
                                        <th>手机号</th>
                                        <th>所属基地</th>
                                        <th>状态</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="user" items="${orgFinanceUsersList}" varStatus="loop">
                                        <tr>
                                            <td>${loop.index + 1}</td>
                                            <td>
                                                <a href="javascript:;" class="btn btn-sm btn-link user-relationship" title="详情"
                                                   data-userId="${user.id}" data-userName="${user.realName}" data-venueId="${user.venueId}" data-mobile="${user.mobile}"
                                                   data-target="#relationship_user" data-toggle="modal">
                                                    <i class="fa fa-user"></i> ${user.realName}
                                                </a>
                                            </td>
                                            <td>${user.mobile}</td>
                                            <td>
                                                <c:forEach var="venue" items="${orgFinanceVenuesList}">
                                                    <c:if test="${venue.id == user.venueId}">${venue.venueName}</c:if>
                                                </c:forEach>
                                            </td>
                                            <td>${user.status > 0 ? "在职" : "离职"}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                                <div>
                                    <%@ include file="../Shared/Pagination.jsp" %>
                                </div>
                            </div>
                            <div class="col-md-7 row">
                                <c:forEach var="item" items="${orgFinanceVenuesAllList}">
                                    <div class="col-md-6">
                                        <div class="card">
                                            <div class="card-header">
                                                <i class="fa fa-home"></i>
                                                <strong>${item.orgFinanceVenues.venueName}</strong>

                                                <div class="card-actions">
                                                    <a href="javascript:;" class="btn-settings venue-relationship" title="设置基地"
                                                       data-venueId="${item.orgFinanceVenues.id}" data-venueName="${item.orgFinanceVenues.venueName}"
                                                       data-target="#relationship_venue" data-toggle="modal">
                                                        <i class="icon-settings"></i>
                                                    </a>
                                                </div>
                                            </div>
                                            <div class="card-block">
                                                <c:forEach var="user" items="${item.orgFinanceUsersList}">
                                                    <button class="btn btn-outline-primary user-relationship ml-1 mb-1"
                                                            data-userId="${user.id}" data-userName="${user.realName}" data-venueId="${user.venueId}" data-mobile="${user.mobile}"
                                                            data-target="#relationship_user" data-toggle="modal">
                                                        <i class="fa fa-user"></i> ${user.realName}
                                                    </button>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
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
    <c:param name="subMenu" value="relationship"/>
</c:import>
