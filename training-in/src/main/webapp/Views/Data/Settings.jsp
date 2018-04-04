<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.in.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <style type="text/css">
        .branch-list th {
            padding: 0.75rem;
        }
        .branch-list td {
            padding: 0.3rem 0.75rem;
        }
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/data/settings.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="container-fluid">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-header">
                            <strong>收入类型</strong>
                            <small>Income</small>
                        </div>
                        <div class="card-block">
                            <form id="income_form" class="form-horizontal" novalidate onsubmit="return false;">
                                <c:forEach var="setting" items="${incomeSettingList}">
                                    <div class="form-group row">
                                        <div class="col-md-1">
                                            <button type="button" class="btn btn-outline-secondary up-settings">
                                                <i class="fa fa-long-arrow-up"></i>
                                            </button>
                                        </div>
                                        <div class="col-md-6">
                                            <input type="text" class="form-control" placeholder="类型名称" name="balanceName"
                                                   value="${setting.balanceName}">
                                        </div>
                                        <div class="col-md-5">
                                            <button type="button" class="btn btn-danger del-settings">
                                                <i class="fa fa-trash"></i> 删除
                                            </button>
                                            <button type="button" class="btn btn-primary add-settings" style="display: none;">
                                                <i class="fa fa-trash"></i> 添加
                                            </button>
                                        </div>
                                    </div>
                                </c:forEach>
                                <div class="form-group row">
                                    <div class="col-md-1">
                                        <button type="button" class="btn btn-outline-secondary up-settings">
                                            <i class="fa fa-long-arrow-up"></i>
                                        </button>
                                    </div>
                                    <div class="col-md-6">
                                        <input type="text" class="form-control" placeholder="类型名称" name="balanceName">
                                    </div>
                                    <div class="col-md-5">
                                        <button type="button" class="btn btn-danger del-settings">
                                            <i class="fa fa-trash"></i> 删除
                                        </button>
                                        <button type="button" class="btn btn-primary add-settings">
                                            <i class="fa fa-trash"></i> 添加
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer">
                            <button type="button" class="btn btn-primary save-income-settings">
                                <i class="fa fa-check"></i> 保 存
                            </button>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-header">
                            <strong>支出类型</strong>
                            <small>Expend</small>
                        </div>
                        <div class="card-block">
                            <form id="expend_form" class="form-horizontal" novalidate onsubmit="return false;">
                                <c:forEach var="setting" items="${expendSettingList}">
                                    <div class="form-group row">
                                        <div class="col-md-1">
                                            <button type="button" class="btn btn-outline-secondary up-settings">
                                                <i class="fa fa-long-arrow-up"></i>
                                            </button>
                                        </div>
                                        <div class="col-md-6">
                                            <input type="text" class="form-control" placeholder="类型名称" name="balanceName"
                                                   value="${setting.balanceName}">
                                        </div>
                                        <div class="col-md-5">
                                            <button type="button" class="btn btn-danger del-settings">
                                                <i class="fa fa-trash"></i> 删除
                                            </button>
                                            <button type="button" class="btn btn-primary add-settings" style="display: none;">
                                                <i class="fa fa-trash"></i> 添加
                                            </button>
                                        </div>
                                    </div>
                                </c:forEach>
                                <div class="form-group row">
                                    <div class="col-md-1">
                                        <button type="button" class="btn btn-outline-secondary up-settings">
                                            <i class="fa fa-long-arrow-up"></i>
                                        </button>
                                    </div>
                                    <div class="col-md-6">
                                        <input type="text" class="form-control" placeholder="类型名称" name="balanceName">
                                    </div>
                                    <div class="col-md-5">
                                        <button type="button" class="btn btn-danger del-settings">
                                            <i class="fa fa-trash"></i> 删除
                                        </button>
                                        <button type="button" class="btn btn-primary add-settings">
                                            <i class="fa fa-trash"></i> 添加
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer">
                            <button type="button" class="btn btn-primary save-expend-settings">
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
    <c:param name="menu" value="data"/>
    <c:param name="subMenu" value="settings"/>
</c:import>
