<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.uk.layout.Blocks" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <style type="text/css">
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/settings/database.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="container-fluid">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <strong>数据库备份</strong>
                            <small>Backup</small>
                        </div>
                        <div class="card-block">
                            <button type="button" class="btn btn-primary btn-lg backup-database">
                                <span class="fa fa-database"></span> 数据库备份
                            </button>
                            <span class="text-muted ml-2">需要安装"mysql"，并能够直接执行指令"mysqldump"。</span>
                        </div>
                        <div class="card-footer">
                            <button type="button" class="btn btn-danger restore-database">
                                <span class="fa fa-database"></span> 数据库还原
                            </button>
                            <span class="text-danger ml-2">注意：请慎重点击"数据库还原"，此功能会选择最近的备份进行数据覆盖。</span>
                        </div>
                        <div class="card-block">
                            <table class="table table-striped table-sm admin-list">
                                <thead>
                                <tr>
                                    <th>编号</th>
                                    <th>文件名称</th>
                                    <th>文件路径</th>
                                    <th>操作人</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="f" items="${fileList}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index + 1}</td>
                                        <td>${f.getName()}</td>
                                        <td>${f.getAbsolutePath()}</td>
                                        <td>
                                            <button type="button" class="btn btn-sm btn-danger" title="删除备份">
                                                <i class="fa fa-remove"></i> 删除
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
    <c:param name="subMenu" value="database"/>
</c:import>
