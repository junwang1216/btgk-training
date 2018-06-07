<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.in.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <style type="text/css">
        .form-control-label {
            text-align: right;
            font-weight: bold;
        }
        .coaches-list th {
            padding: 0.75rem;
        }
        .coaches-list td {
            padding: 0.3rem 0.75rem;
        }
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/venue/coach.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="modal fade" id="coach_edit" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-primary" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <form id="coach_edit_form" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                        <input type="hidden" id="coach_edit_id" name="id" value="">
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label" for="coach_edit_realName">
                                <span class="text-danger">*</span> 真实姓名
                            </label>
                            <div class="col-md-9">
                                <input type="text" class="form-control" id="coach_edit_realName" name="realName" placeholder="请输入真实姓名"
                                       data-val="true" data-val-required="真实姓名不能为空"
                                       data-val-length-max="10" data-val-length-min="2" data-val-length="真实姓名必须包含 2~10 个字符">
                                <div data-valmsg-for="realName" data-valmsg-replace="true"></div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label" for="coach_edit_mobile">
                                <span class="text-danger">*</span> 手机号码
                            </label>
                            <div class="col-md-9">
                                <input type="text" class="form-control" id="coach_edit_mobile" name="mobile" placeholder="请输入手机号码"
                                       data-val="true" data-val-required="手机号码不能为空"
                                       data-val-regex-pattern="^(13[0-9]|15[012356789]|166|17[0-9]|18[012356789]|14[57]|19[89])[0-9]{8}$" data-val-regex="手机号码格式不正确">
                                <div data-valmsg-for="mobile" data-valmsg-replace="true"></div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label" for="coach_edit_idCard">
                                <span class="text-danger">*</span> 身份证号
                            </label>
                            <div class="col-md-9">
                                <input type="text" class="form-control" id="coach_edit_idCard" name="idCard" placeholder="请输入身份证号"
                                       data-val="true" data-val-required="身份证号不能为空"
                                       data-val-regex-pattern="^\d{17}([0-9]|X|x)$" data-val-regex="身份证号格式不正确">
                                <div data-valmsg-for="idCard" data-valmsg-replace="true"></div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label" for="coach_edit_email">联系邮箱</label>
                            <div class="col-md-9">
                                <input type="text" class="form-control" id="coach_edit_email" name="email" placeholder="请输入联系邮箱"
                                       data-val-regex-pattern="^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$" data-val-regex="联系邮箱格式不正确">
                                <div data-valmsg-for="email" data-valmsg-replace="true"></div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label">
                                <span class="text-danger">*</span> 所属角色
                            </label>
                            <div class="col-md-9">
                                <c:forEach var="role" items="${orgRolesList}" varStatus="loop">
                                    <label class="checkbox-inline" for="coach_edit_role${loop.index + 1}">
                                        <input type="checkbox" id="coach_edit_role${loop.index + 1}" name="roleId"
                                        <c:if test="${loop.first}"> required</c:if>
                                               value="${role.code}"> ${role.desc}
                                    </label>
                                </c:forEach>
                                <div data-valmsg-for="roleId" data-valmsg-replace="true"></div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label">
                                <span class="text-danger">*</span> 授课项目
                            </label>
                            <div class="col-md-9">
                                <c:forEach var="sport" items="${orgSportsList}" varStatus="loop">
                                    <label class="checkbox-inline" for="coach_edit_sports${loop.index + 1}">
                                        <input type="checkbox" id="coach_edit_sports${loop.index + 1}" name="sportId"
                                        <c:if test="${loop.first}"> required</c:if>
                                               value="${sport.id}"> ${sport.sportName}
                                    </label>
                                </c:forEach>
                                <div data-valmsg-for="sportId" data-valmsg-replace="true"></div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label">
                                <span class="text-danger">*</span> 所属场馆
                            </label>
                            <div class="col-md-9">
                                <c:forEach var="venue" items="${orgVenuesList}" varStatus="loop">
                                    <label class="checkbox-inline" for="coach_edit_venue${loop.index + 1}">
                                        <input type="checkbox" id="coach_edit_venue${loop.index + 1}" name="venueId"
                                        <c:if test="${loop.first}"> required</c:if>
                                               value="${venue.id}"> ${venue.venueName}
                                    </label>
                                </c:forEach>
                                <div data-valmsg-for="venueId" data-valmsg-replace="true"></div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-sm btn-secondary" data-dismiss="modal">
                        <i class="fa fa-close"></i> 取 消
                    </button>
                    <button type="button" class="btn btn-sm btn-primary save-coach">
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
                            <strong>教练管理</strong>
                            <small>Coaches</small>
                        </div>
                        <div class="card-block">
                            <form id="coaches_form" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                                <div class="form-group row">
                                    <div class="col-md-3">
                                        <input type="text" name="realName" class="form-control" placeholder="真实姓名" value="${realName}">
                                    </div>
                                    <div class="col-md-3">
                                        <input type="text" name="mobile" class="form-control" placeholder="手机号码" value="${mobile}">
                                    </div>
                                    <div class="col-md-6">
                                        <button type="button" class="btn btn-primary search-coaches">
                                            <i class="fa fa-search"></i> 检 索
                                        </button>
                                        <a href="#" class="btn btn-primary pull-right add-coach" title="用户添加"
                                           data-target="#coach_edit" data-toggle="modal">
                                            <i class="fa fa-plus"></i> 教练添加
                                        </a>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer text-right"></div>
                        <div class="card-block">
                            <table class="table table-striped table-sm coach-list">
                                <thead>
                                <tr>
                                    <td>##</td>
                                    <th>真实姓名</th>
                                    <th>手机号码</th>
                                    <th>身份证号</th>
                                    <th>所属角色</th>
                                    <th>授课项目</th>
                                    <th>所属场馆</th>
                                    <th>状态</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="coach" items="${orgCoachesList}" varStatus="loop">
                                    <tr data-id="${coach.orgCoaches.id}">
                                        <td>${loop.index + 1}</td>
                                        <td>${coach.orgCoaches.realName}</td>
                                        <td>${coach.orgCoaches.mobileSensitive}</td>
                                        <td>${coach.orgCoaches.idCardSensitive}</td>
                                        <td>${coach.roleNames}</td>
                                        <td>${coach.sportNames}</td>
                                        <td>${coach.venueNames}</td>
                                        <td>
                                            <c:if test="${coach.orgCoaches.status == 1}">
                                                <span class="badge badge-success">正常</span>
                                            </c:if>
                                            <c:if test="${coach.orgCoaches.status == 2}">
                                                <span class="badge badge-danger">锁定</span>
                                            </c:if>
                                        </td>
                                        <td>
                                            <a href="javascript:;" class="btn btn-sm btn-primary edit-coach" title="重新编辑"
                                               data-target="#coach_edit" data-toggle="modal">
                                                <i class="fa fa-pencil"></i> 修改
                                            </a>
                                            <c:if test="${coach.orgCoaches.status == 1}">
                                                <button type="button" class="btn btn-sm btn-danger coach-close" title="教练锁定">
                                                    <i class="fa fa-lock"></i> 锁定
                                                </button>
                                            </c:if>
                                            <c:if test="${coach.orgCoaches.status == 2}">
                                                <button type="button" class="btn btn-sm btn-warning coach-open" title="教练解锁">
                                                    <i class="fa fa-unlock"></i> 解锁
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
                <!--/.col-->
            </div>
            <!--/.row-->
        </div>

    </div>
</layout:override>

<c:import url="../Shared/GeneralLayout.jsp">
    <c:param name="menu" value="venue"/>
    <c:param name="subMenu" value="coaches"/>
</c:import>
