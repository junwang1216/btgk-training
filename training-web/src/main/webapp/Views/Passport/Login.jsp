<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.web.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/passport/login.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card-group mb-0">
                    <div class="card p-4">
                        <div class="card-block">
                            <h1>账号登录</h1>
                            <p class="text-muted">LOGIN TO YOUR ACCOUNT</p>
                            <div class="input-group mb-3">
                                <span class="input-group-addon"><i class="icon-user"></i></span>
                                <input type="text" id="user_account" name="userAccount" class="form-control"
                                       placeholder="请输入您的账号...">
                            </div>
                            <div class="input-group mb-4">
                                <span class="input-group-addon"><i class="icon-lock"></i></span>
                                <input type="password" id="user_password" name="userPassword" class="form-control"
                                       placeholder="请输入您的密码...">
                            </div>
                            <div class="row">
                                <div class="col-6">
                                    <button type="button" id="user_login" class="btn btn-primary px-4">登 录</button>
                                </div>
                                <div class="col-6 text-right">
                                    <a href="javascript:;" class="btn btn-link px-0">忘记密码？</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card card-inverse card-primary py-5 d-md-down-none" style="width: 44%">
                        <div class="card-block text-center">
                            <div>
                                <h2>北京体育大学</h2>
                                <h3>资料库</h3>
                                <p>您还没有账户？</p>
                                <a href="/passport/register" class="btn btn-primary active mt-3">立即注册</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</layout:override>

<c:import url="../Shared/PassportLayout.jsp">
    <c:param name="nav" value="setting"/>
</c:import>
