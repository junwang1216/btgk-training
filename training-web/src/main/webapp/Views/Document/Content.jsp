<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.web.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script async type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/web_document/content.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="page-container">
        <div class="container">
            <div class="row">
                <!-- start of page content -->
                <div class="col-sm-12 page-content">
                    <div class="panel panel-info">
                        <div class="table-responsive">
                            <table class="table table-bordered">
                                <tr>
                                    <td class="active">
                                        <span class="glyphicon glyphicon-bookmark"></span> 标题
                                    </td>
                                    <td colspan="5">计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献，计算机科技类资料文献</td>
                                </tr>
                                <tr>
                                    <td class="active">
                                        <span class="glyphicon glyphicon-user"></span> 分类
                                    </td>
                                    <td>计算机科技类</td>
                                    <td class="active">
                                        <span class="glyphicon glyphicon-user"></span> 作者
                                    </td>
                                    <td>张召忠</td>
                                    <td class="active">
                                        <span class="glyphicon glyphicon-calendar"></span> 发表日期
                                    </td>
                                    <td>2015-02-25</td>
                                </tr>
                                <tr>
                                    <td class="active">
                                        <span class="glyphicon glyphicon-tags"></span> 标签
                                    </td>
                                    <td colspan="5">
                                        <a href="/web/document/search?keywords=科技" class="btn btn-default btn-sm">科技</a>
                                        <a href="/web/document/search?keywords=创新" class="btn btn-default btn-sm">创新</a>
                                        <a href="/web/document/search?keywords=创新" class="btn btn-default btn-sm">体育</a>
                                        <a href="/web/document/search?keywords=创新" class="btn btn-default btn-sm">篮球</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="active">
                                        <span class="glyphicon glyphicon-align-justify"></span> 摘要
                                    </td>
                                    <td colspan="5">
                                        <p>计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献，计算机科技类资料文献，
                                            计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献，计算机科技类资料文献，
                                            计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献，计算机科技类资料文献，
                                            计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献，计算机科技类资料文献，
                                            计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献，计算机科技类资料文献，
                                            计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献，计算机科技类资料文献。
                                        </p>
                                        <p>计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献，计算机科技类资料文献，
                                            计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献，计算机科技类资料文献，
                                            计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献，计算机科技类资料文献，
                                            计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献，计算机科技类资料文献，
                                            计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献，计算机科技类资料文献，
                                            计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献，计算机科技类资料文献。
                                        </p>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="active"></td>
                                    <td colspan="3">
                                        <a class="btn btn-sm btn-danger">
                                            <span class="glyphicon glyphicon-star"></span> 已收藏
                                        </a>
                                        <a class="btn btn-sm btn-danger">
                                            <span class="glyphicon glyphicon-star-empty"></span> 收藏
                                        </a>
                                        <a class="btn btn-sm btn-primary">
                                            <span class="glyphicon glyphicon-download-alt"></span> 下载
                                        </a>
                                    </td>
                                    <td class="active">热点关注度</td>
                                    <td>
                                        <div class="progress">
                                            <div class="progress-bar progress-bar-warning" role="progressbar" style="width: 40%">
                                                <span>1024 次</span>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </table>
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
