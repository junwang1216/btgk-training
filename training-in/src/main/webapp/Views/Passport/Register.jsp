<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.in.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/admin_passport/register.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card mx-4">
                    <div class="card-block p-4">
                        <h1>Register</h1>
                        <p class="text-muted">Create your account</p>
                        <div class="input-group mb-3">
                            <span class="input-group-addon"><i class="icon-user"></i>
                            </span>
                            <input type="text" class="form-control" placeholder="Username">
                        </div>

                        <div class="input-group mb-3">
                            <span class="input-group-addon">@</span>
                            <input type="text" class="form-control" placeholder="Email">
                        </div>

                        <div class="input-group mb-3">
                            <span class="input-group-addon"><i class="icon-lock"></i>
                            </span>
                            <input type="password" class="form-control" placeholder="Password">
                        </div>

                        <div class="input-group mb-4">
                            <span class="input-group-addon"><i class="icon-lock"></i>
                            </span>
                            <input type="password" class="form-control" placeholder="Repeat password">
                        </div>

                        <button type="button" class="btn btn-block btn-success">Create Account</button>
                    </div>
                    <div class="card-footer p-4">
                        <div class="row">
                            <div class="col-6">
                                <button class="btn btn-block btn-facebook" type="button">
                                    <span>facebook</span>
                                </button>
                            </div>
                            <div class="col-6">
                                <button class="btn btn-block btn-twitter" type="button">
                                    <span>twitter</span>
                                </button>
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
