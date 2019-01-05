<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.in.layout.Blocks" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <style type="text/css">
        .class-list th {
            padding: 0.75rem;
        }
        .class-list td {
            padding: 0.3rem 0.75rem;
        }
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/class/list.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="container-fluid">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <strong>转化分析</strong>
                            <small>Analysis of Transformation</small>
                        </div>
                        <div class="card-block">
                            某个时间段，某个人
                            </a>
                        </div>
                        <div class="card-footer text-right"></div>
                        <div class="card-block">
                            <div class="row">
                                <div class="col-sm-6">
                                    <table class="table table-striped table-sm class-list">
                                        <thead>
                                        <tr>
                                            <th>##</th>
                                            <th>学员姓名</th>
                                            <th>所属场馆</th>
                                            <th>手机号码</th>
                                            <th>营销次数</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr data-id="学员ID">
                                            <td>1</td>
                                            <td>张三</td>
                                            <td>三三场馆</td>
                                            <td>15801303144</td>
                                            <td>2</td>
                                        </tr>
                                        <tr data-id="学员ID">
                                            <td>1</td>
                                            <td>张三</td>
                                            <td>三三场馆</td>
                                            <td>15801303144</td>
                                            <td>2</td>
                                        </tr>
                                        <tr data-id="学员ID">
                                            <td>1</td>
                                            <td>张三</td>
                                            <td>三三场馆</td>
                                            <td>15801303144</td>
                                            <td>2</td>
                                        </tr>
                                        <tr data-id="学员ID">
                                            <td>1</td>
                                            <td>张三</td>
                                            <td>三三场馆</td>
                                            <td>15801303144</td>
                                            <td>2</td>
                                        </tr>
                                        <tr data-id="学员ID">
                                            <td>1</td>
                                            <td>张三</td>
                                            <td>三三场馆</td>
                                            <td>15801303144</td>
                                            <td>2</td>
                                        </tr>
                                        <tr data-id="学员ID">
                                            <td>1</td>
                                            <td>张三</td>
                                            <td>三三场馆</td>
                                            <td>15801303144</td>
                                            <td>2</td>
                                        </tr>
                                        <tr data-id="学员ID">
                                            <td>1</td>
                                            <td>张三</td>
                                            <td>三三场馆</td>
                                            <td>15801303144</td>
                                            <td>2</td>
                                        </tr>
                                        <tr data-id="学员ID">
                                            <td>1</td>
                                            <td>张三</td>
                                            <td>三三场馆</td>
                                            <td>15801303144</td>
                                            <td>2</td>
                                        </tr>
                                        <tr data-id="学员ID">
                                            <td>1</td>
                                            <td>张三</td>
                                            <td>三三场馆</td>
                                            <td>15801303144</td>
                                            <td>2</td>
                                        </tr>
                                        <tr data-id="学员ID">
                                            <td>1</td>
                                            <td>张三</td>
                                            <td>三三场馆</td>
                                            <td>15801303144</td>
                                            <td>2</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="col-sm-6">
                                    整体转化
                                    时间轴转化
                                    人员轴转化
                                </div>
                            </div>
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
    <c:param name="menu" value="marketing"/>
    <c:param name="subMenu" value="potentialStu"/>
</c:import>
