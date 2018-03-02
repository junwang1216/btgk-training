<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.web.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script async type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/web_document/list.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="page-container">
        <div class="container">
            <div class="row">
                <!-- start of page content -->
                <div class="col-sm-8 main-listing">
                    <div class="panel panel-info">
                        <div class="panel-heading"><span>计算机科技类</span></div>
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover">
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=1">计算机科技类资料文献标题</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=2">计算机科技类资料文献标题，计算机科技类资料文献，计算机科技类资料文献</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=3">计算机科技类资料文献标题，计算机科技类资料文献</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=4">计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献，计...</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=5">计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献标题</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=1">计算机科技类资料文献标题</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=2">计算机科技类资料文献标题，计算机科技类资料文献，计算机科技类资料文献</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=3">计算机科技类资料文献标题，计算机科技类资料文献</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=4">计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献，计...</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=5">计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献标题</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=1">计算机科技类资料文献标题</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=2">计算机科技类资料文献标题，计算机科技类资料文献，计算机科技类资料文献</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=3">计算机科技类资料文献标题，计算机科技类资料文献</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=4">计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献，计...</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=5">计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献标题</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=1">计算机科技类资料文献标题</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=2">计算机科技类资料文献标题，计算机科技类资料文献，计算机科技类资料文献</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=3">计算机科技类资料文献标题，计算机科技类资料文献</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=4">计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献，计...</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=5">计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献标题</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=1">计算机科技类资料文献标题</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=2">计算机科技类资料文献标题，计算机科技类资料文献，计算机科技类资料文献</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=3">计算机科技类资料文献标题，计算机科技类资料文献</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=4">计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献，计...</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=5">计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献标题</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=1">计算机科技类资料文献标题</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=2">计算机科技类资料文献标题，计算机科技类资料文献，计算机科技类资料文献</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=3">计算机科技类资料文献标题，计算机科技类资料文献</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=4">计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献，计...</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=5">计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献标题</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=1">计算机科技类资料文献标题</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=2">计算机科技类资料文献标题，计算机科技类资料文献，计算机科技类资料文献</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=3">计算机科技类资料文献标题，计算机科技类资料文献</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=4">计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献，计...</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=5">计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献标题</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=1">计算机科技类资料文献标题</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=2">计算机科技类资料文献标题，计算机科技类资料文献，计算机科技类资料文献</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=3">计算机科技类资料文献标题，计算机科技类资料文献</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=4">计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献，计...</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=5">计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献标题</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=1">计算机科技类资料文献标题</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=2">计算机科技类资料文献标题，计算机科技类资料文献，计算机科技类资料文献</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=3">计算机科技类资料文献标题，计算机科技类资料文献</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=4">计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献，计...</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=5">计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献标题</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=1">计算机科技类资料文献标题</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=2">计算机科技类资料文献标题，计算机科技类资料文献，计算机科技类资料文献</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=3">计算机科技类资料文献标题，计算机科技类资料文献</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=4">计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献，计...</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="/web/document/content?article=5">计算机科技类资料文献标题，计算机科技类资料文献标题，计算机科技类资料文献标题</a>
                                    </td>
                                    <td>2013-02-25</td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <nav>
                        <ul class="pagination">
                            <li class="disabled">
                                <a href="javascritp:;">每页 50 条/总 1000 条</a>
                            </li>
                            <li class="disabled">
                                <a href="#">
                                    <span class="glyphicon glyphicon-fast-backward"></span>
                                </a>
                            </li>
                            <li class="disabled">
                                <a href="#">
                                    <span class="glyphicon glyphicon-backward"></span>
                                </a>
                            </li>
                            <li class="active"><a href="#">1</a></li>
                            <li><a href="#">2</a></li>
                            <li><a href="#">3</a></li>
                            <li><a href="#">4</a></li>
                            <li><a href="#">5</a></li>
                            <li><a href="#">7</a></li>
                            <li><a href="#">8</a></li>
                            <li><a href="#">9</a></li>
                            <li>
                                <a href="#">
                                    <span class="glyphicon glyphicon-forward"></span>
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <span class="glyphicon glyphicon-fast-forward"></span>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>
                <!-- end of page content -->
                
                <%@include file="../Shared/SideBar.jsp" %>
            </div>
        </div>
    </div>
</layout:override>

<c:import url="../Shared/GeneralLayout.jsp">
    <c:param name="nav" value="setting"/>
</c:import>
