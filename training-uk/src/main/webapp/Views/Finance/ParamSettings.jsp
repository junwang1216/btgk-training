<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.uk.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <style type="text/css">
        .settings-list th {
            padding: 0.75rem;
        }
        .settings-list td {
            padding: 0.45rem;
        }
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/finance/paramSettings.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="container-fluid">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-header">
                            <strong>收入类型设置</strong>
                            <small>Income Type Settings</small>
                        </div>
                        <div class="card-block">
                            <form id="income_form" class="form-horizontal" novalidate onsubmit="return false;">
                                <input type="hidden" name="incomeGroup" value="${orgFinanceClassGroup}">
                                <c:forEach var="setting" items="${orgFinanceClassEnumsList}" varStatus="loop">
                                    <div class="form-group row">
                                        <div class="col-md-6">
                                            <input type="text" class="form-control" placeholder="类型名称" name="incomeType" value="${setting.enumNote}">
                                        </div>
                                        <div class="col-md-6">
                                            <button type="button" class="btn btn-danger del-settings">
                                                <i class="fa fa-trash"></i> 删除
                                            </button>
                                            <c:if test="${loop.last}">
                                                <button type="button" class="btn btn-primary add-settings">
                                                    <i class="fa fa-plus"></i> 添加
                                                </button>
                                            </c:if>
                                            <c:if test="${!loop.last}">
                                                <button type="button" class="btn btn-primary add-settings" style="display: none;">
                                                    <i class="fa fa-plus"></i> 添加
                                                </button>
                                            </c:if>
                                        </div>
                                    </div>
                                </c:forEach>
                                <c:if test="${orgFinanceClassEnumsList.size() == 0}">
                                    <div class="form-group row">
                                        <div class="col-md-6">
                                            <input type="text" class="form-control" placeholder="类型名称" name="incomeType">
                                        </div>
                                        <div class="col-md-5">
                                            <button type="button" class="btn btn-danger del-settings">
                                                <i class="fa fa-trash"></i> 删除
                                            </button>
                                            <button type="button" class="btn btn-primary add-settings">
                                                <i class="fa fa-plus"></i> 添加
                                            </button>
                                        </div>
                                    </div>
                                </c:if>
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
                            <strong>流水渠道设置</strong>
                            <small>Pipeline Channel Setting</small>
                        </div>
                        <div class="card-block">
                            <form id="expend_form" class="form-horizontal" novalidate onsubmit="return false;">
                                <input type="hidden" name="channelGroup" value="${orgFinanceChannelGroup}">
                                <c:forEach var="setting" items="${orgFinanceChannelEnumsList}" varStatus="loop">
                                    <div class="form-group row">
                                        <div class="col-md-6">
                                            <input type="text" class="form-control" placeholder="渠道名称" name="flowChannel" value="${setting.enumNote}">
                                        </div>
                                        <div class="col-md-5">
                                            <button type="button" class="btn btn-danger del-settings">
                                                <i class="fa fa-trash"></i> 删除
                                            </button>
                                            <c:if test="${loop.last}">
                                                <button type="button" class="btn btn-primary add-settings">
                                                    <i class="fa fa-plus"></i> 添加
                                                </button>
                                            </c:if>
                                            <c:if test="${!loop.last}">
                                                <button type="button" class="btn btn-primary add-settings" style="display: none;">
                                                    <i class="fa fa-plus"></i> 添加
                                                </button>
                                            </c:if>
                                        </div>
                                    </div>
                                </c:forEach>
                                <c:if test="${orgFinanceChannelEnumsList.size() == 0}">
                                    <div class="form-group row">
                                        <div class="col-md-6">
                                            <input type="text" class="form-control" placeholder="渠道名称" name="channelName">
                                        </div>
                                        <div class="col-md-5">
                                            <button type="button" class="btn btn-danger del-settings">
                                                <i class="fa fa-trash"></i> 删除
                                            </button>
                                            <button type="button" class="btn btn-primary add-settings">
                                                <i class="fa fa-plus"></i> 添加
                                            </button>
                                        </div>
                                    </div>
                                </c:if>
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
    <c:param name="menu" value="finance"/>
    <c:param name="subMenu" value="params"/>
</c:import>
