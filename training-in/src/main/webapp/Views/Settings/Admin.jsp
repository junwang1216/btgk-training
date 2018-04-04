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
        .admin-list th {
            padding: 0.75rem;
        }
        .admin-list td {
            padding: 0.3rem 0.75rem;
        }
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/settings/admin.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="modal fade" id="operator_edit" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-primary" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <form id="admin_form" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                        <input type="hidden" id="admin_id" name="id" value="">
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label" for="admin_userNo">
                                <span class="text-danger">*</span> 运营编号
                            </label>
                            <div class="col-md-6">
                                <input type="text" class="form-control" id="admin_userNo" name="userNo" placeholder="请输入运营编号"
                                       data-val="true" data-val-required="运营编号不能为空">
                                <div data-valmsg-for="userNo" data-valmsg-replace="true"></div>
                            </div>
                            <div class="col-md-3">
                                <button type="button" class="btn btn-sm btn-primary random-operator" title="随机">
                                    <i class="fa fa-random"></i> 随机
                                </button>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label" for="admin_userName">
                                <span class="text-danger">*</span> 运营账号
                            </label>
                            <div class="col-md-9">
                                <input type="text" class="form-control" id="admin_userName" name="userName" placeholder="请输入运营账号"
                                       data-val="true" data-val-required="运营账号不能为空"
                                       data-val-regex-pattern="^[a-zA-Z][a-zA-Z0-9_]{4,15}$" data-val-regex="运营账号格式不正确">
                                <div data-valmsg-for="userName" data-valmsg-replace="true"></div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label" for="admin_realName">
                                <span class="text-danger">*</span> 真实姓名
                            </label>
                            <div class="col-md-9">
                                <input type="text" class="form-control" id="admin_realName" name="realName" placeholder="请输入真实姓名"
                                       data-val="true" data-val-required="真实姓名不能为空"
                                       data-val-length-max="10" data-val-length-min="2" data-val-length="真实姓名必须包含 2~10 个字符">
                                <div data-valmsg-for="realName" data-valmsg-replace="true"></div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label" for="admin_mobile">
                                <span class="text-danger">*</span> 手机号码
                            </label>
                            <div class="col-md-9">
                                <input type="text" class="form-control" id="admin_mobile" name="mobile" placeholder="请输入手机号码"
                                       data-val="true" data-val-required="手机号码不能为空"
                                       data-val-regex-pattern="^(13[0-9]|15[012356789]|166|17[0-9]|18[02356789]|14[57]|19[89])[0-9]{8}$" data-val-regex="手机号码格式不正确">
                                <div data-valmsg-for="mobile" data-valmsg-replace="true"></div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label" for="admin_idCard">
                                <span class="text-danger">*</span> 身份证号
                            </label>
                            <div class="col-md-9">
                                <input type="text" class="form-control" id="admin_idCard" name="idCard" placeholder="请输入身份证号"
                                       data-val="true" data-val-required="身份证号不能为空"
                                       data-val-regex-pattern="^\d{17}([0-9]|X|x)$" data-val-regex="身份证号格式不正确">
                                <div data-valmsg-for="idCard" data-valmsg-replace="true"></div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label" for="admin_email">联系邮箱</label>
                            <div class="col-md-9">
                                <input type="text" class="form-control" id="admin_email" name="email" placeholder="请输入联系邮箱"
                                       data-val-regex-pattern="^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$" data-val-regex="联系邮箱格式不正确">
                                <div data-valmsg-for="email" data-valmsg-replace="true"></div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label" for="admin_roleId">
                                <span class="text-danger">*</span> 所属角色
                            </label>
                            <div class="col-md-9">
                                <select class="form-control" id="admin_roleId" name="roleId"
                                        data-val="true" data-val-required="请选择所属角色">
                                    <c:forEach var="role" items="${RoleTypeEnum}">
                                        <c:if test="${role.code >= RoleTypeStart.code && role.code < Admin.roleId}">
                                            <option value="${role.code}">${role.desc}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                                <div data-valmsg-for="roleId" data-valmsg-replace="true"></div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-sm btn-secondary" data-dismiss="modal">
                        <i class="fa fa-close"></i> 取 消
                    </button>
                    <button type="button" class="btn btn-sm btn-primary save-operator">
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
                            <strong>管理员设置</strong>
                            <small>Admin</small>
                        </div>
                        <div class="card-block">
                            <form id="admin_search_form" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                                <div class="form-group row">
                                    <div class="col-md-3">
                                        <input type="text" name="realName" class="form-control" placeholder="真实姓名">
                                    </div>
                                    <div class="col-md-3">
                                        <input type="text" name="mobile" class="form-control" placeholder="手机号码">
                                    </div>
                                    <div class="col-md-6">
                                        <button type="button" class="btn btn-primary">
                                            <i class="fa fa-search"></i> 检 索
                                        </button>
                                        <a href="#" class="btn btn-primary pull-right add-operator" title="管理员添加"
                                           data-target="#operator_edit" data-toggle="modal">
                                            <i class="fa fa-plus"></i> 管理员添加
                                        </a>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer text-right"></div>
                        <div class="card-block">
                            <table class="table table-striped table-sm admin-list">
                                <thead>
                                <tr>
                                    <th>编号</th>
                                    <th>运营编号</th>
                                    <th>运营账号</th>
                                    <th>运营角色</th>
                                    <th>真实姓名</th>
                                    <th>手机号码</th>
                                    <th>最后一次登录时间</th>
                                    <th>状态</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="operator" items="${orgOperatorList}" varStatus="loop">
                                    <tr data-id="${operator.id}">
                                        <td>${loop.index + 1}</td>
                                        <td>${operator.userNo}</td>
                                        <td>${operator.userName}</td>
                                        <td>
                                        <c:forEach var="role" items="${RoleTypeEnum}">
                                            <c:if test="${operator.roleId == role.code}">${role.desc}</c:if>
                                        </c:forEach>
                                        </td>
                                        <td>${operator.realName}</td>
                                        <td>${operator.mobile}</td>
                                        <td>${operator.lastLoginTime}</td>
                                        <td>${operator.status == 1 ? "正常" : "已锁定"}</td>
                                        <td>
                                            <c:if test="${operator.roleId < Admin.roleId}">
                                                <button type="button" class="btn btn-sm btn-warning user-edit" title="重置密码">
                                                    <i class="fa fa-refresh"></i> 重置密码
                                                </button>
                                                <c:if test="${operator.status == 1}">
                                                    <button type="button" class="btn btn-sm btn-danger user-delete" title="删除用户"
                                                            data-toggle="modal" data-target="#warning_tips">
                                                        <i class="fa fa-lock"></i> 锁定
                                                    </button>
                                                </c:if>
                                                <c:if test="${operator.status != 1}">
                                                    <button type="button" class="btn btn-sm btn-danger user-delete" title="删除用户"
                                                            data-toggle="modal" data-target="#warning_tips">
                                                        <i class="fa fa-unlock"></i> 解锁
                                                    </button>
                                                </c:if>
                                            </c:if>
                                            <button type="button" class="btn btn-sm btn-primary operator-edit" title="编辑用户"
                                                    data-toggle="modal" data-target="#operator_edit">
                                                <i class="fa fa-pencil"></i> 修改
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
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
    <c:param name="menu" value="settings"/>
    <c:param name="subMenu" value="admin"/>
</c:import>
