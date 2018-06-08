<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.uk.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <style type="text/css">
        .form-control-label {
            text-align: right;
            font-weight: bold;
        }
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/settings/profile.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="container-fluid">
        <div class="animated fadeIn">
            <div class="row">
                <div class="offset-3 col-md-6">
                    <div class="card">
                        <div class="card-header">
                            <strong>修改密码</strong>
                            <small>Password</small>
                        </div>
                        <div class="card-block">
                            <form id="admin_password_form" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                                <input type="hidden" id="p_admin_id" name="id" value="${User.id}">
                                <div class="form-group row">
                                    <label class="col-md-3 form-control-label" for="admin_old_password">
                                        <span class="text-danger">*</span> 原密码
                                    </label>
                                    <div class="col-md-9">
                                        <input type="password" id="admin_old_password" name="oldPassword" class="form-control" placeholder="请输入原密码..."
                                               autocomplete="off" data-val="true" data-val-required="原密码不能为空">
                                        <div data-valmsg-for="oldPassword" data-valmsg-replace="true"></div>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-md-3 form-control-label" for="admin_new_password">
                                        <span class="text-danger">*</span> 新密码
                                    </label>
                                    <div class="col-md-9">
                                        <input type="password" id="admin_new_password" name="newPassword" class="form-control" placeholder="请输入新密码..."
                                               autocomplete="off" data-val="true" data-val-required="新密码不能为空">
                                        <div data-valmsg-for="newPassword" data-valmsg-replace="true"></div>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-md-3 form-control-label" for="admin_again_password">
                                        <span class="text-danger">*</span> 确认密码
                                    </label>
                                    <div class="col-md-9">
                                        <input type="password" id="admin_again_password" name="againPassword" class="form-control" placeholder="请输入确认密码..."
                                               autocomplete="off" data-val="true" data-val-required="确认密码不能为空">
                                        <div data-valmsg-for="againPassword" data-valmsg-replace="true"></div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer">
                            <button type="button" class="btn btn-primary save-password">
                                <i class="fa fa-check"></i> 保 存
                            </button>
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
    <c:param name="subMenu" value="password"/>
</c:import>
