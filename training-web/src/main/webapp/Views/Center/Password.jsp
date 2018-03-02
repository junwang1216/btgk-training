<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.web.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script async type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/web_center/password.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="page-container">
        <div class="container">
            <div class="row">
                <!-- start of page content -->
                <div class="col-sm-offset-2 col-sm-8 page-content">
                    <div class="panel panel-info">
                        <div class="panel-heading"><span>修改密码</span></div>
                        <div class="panel-body">
                            <form class="form-horizontal">
                                <div class="form-group">
                                    <label for="old_password" class="col-sm-2 control-label">旧密码</label>
                                    <div class="col-sm-10">
                                        <input type="password" class="form-control" id="old_password" placeholder="请输入旧密码...">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="new_password" class="col-sm-2 control-label">新密码</label>
                                    <div class="col-sm-10">
                                        <input type="password" class="form-control" id="new_password" placeholder="请输入新密码...">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="confirm_password" class="col-sm-2 control-label">确认密码</label>
                                    <div class="col-sm-10">
                                        <input type="password" class="form-control" id="confirm_password" placeholder="请输入确认密码...">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button type="submit" class="btn btn-primary">保 存</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</layout:override>

<c:import url="../Shared/GeneralLayout.jsp">
    <c:param name="nav" value="setting"/>
</c:import>
