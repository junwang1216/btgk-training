<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>

<div class="modal login" id="login_modal" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <div class="login-m">
                    <div class="login-logo">
                        <img src="Content/images/logo.png?v=${static_resource_version}" alt="资料库">
                    </div>
                    <hr>
                    <div class="login-body">
                        <form class="form-login" method="get" autocomplete="off" novalidate="novalidate" onsubmit="return false;">
                            <div class="form-group">
                                <div class="input-group">
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-user"></span>
                                    </span>
                                    <input type="text" class="form-control" placeholder="用户名">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="input-group">
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-lock"></span>
                                    </span>
                                    <input type="password" class="form-control" placeholder="密码">
                                </div>
                            </div>
                            <hr>
                            <div class="form-group" style="overflow: hidden">
                                <div class="pull-left">
                                    <a href="#" class="btn btn-link">忘记密码</a>
                                </div>
                                <div class="pull-right">
                                    <button type="button" class="btn btn-primary btn-login" style="width: 120px">登 录</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
