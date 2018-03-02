<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.web.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script async type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/web_center/profile.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="page-container">
        <div class="container">
            <div class="row">
                <!-- start of page content -->
                <div class="col-sm-offset-2 col-sm-8 page-content">
                    <div class="panel panel-info">
                        <div class="panel-heading"><span>修改信息</span></div>
                        <div class="panel-body">
                            <form class="form-horizontal">
                                <div class="form-group">
                                    <label for="user_account" class="col-sm-2 control-label">用户账号</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="user_account" value="user14327851" readonly>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="user_name" class="col-sm-2 control-label">用户姓名</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="user_name" placeholder="请输入真实姓名...">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="user_idcard" class="col-sm-2 control-label">身份证号</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="user_idcard" placeholder="请输入身份证号...">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="user_email" class="col-sm-2 control-label">电子邮箱</label>
                                    <div class="col-sm-10">
                                        <input type="email" class="form-control" id="user_email" placeholder="请输入电子邮箱...">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="user_note" class="col-sm-2 control-label">个人介绍</label>
                                    <div class="col-sm-10">
                                        <textarea class="form-control" id="user_note" placeholder="请输入个人介绍..."></textarea>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button type="button" class="btn btn-primary">保 存</button>
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
