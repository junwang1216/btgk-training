<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.in.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script async type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/students/list.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="container-fluid">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <strong>收支统计</strong>
                            <small>Summary</small>
                        </div>
                        <div class="card-block">
                            <form action="" method="post" class="form-horizontal">
                                <div class="form-group row">
                                    <div class="col-md-2">
                                        <a href="#" class="btn btn-outline-secondary">
                                            <i class="fa fa-calendar-minus-o"></i> 昨日
                                        </a>
                                        <a href="#" class="btn btn-outline-secondary">
                                            <i class="fa fa-calendar-check-o"></i> 今日
                                        </a>
                                    </div>
                                    <div class="col-md-2">
                                        <select id="income_date" name="summaryDate" class="form-control">
                                            <option>自定义期限</option>
                                            <option>最近一个月</option>
                                            <option>最近三个月</option>
                                            <option>最近六个月</option>
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        <input type="text" id="summary_start" name="summaryStart" class="form-control" placeholder="开始日期">
                                    </div>
                                    <div class="col-md-2">
                                        <input type="text" id="summary_end" name="summaryEnd" class="form-control" placeholder="结束日期">
                                    </div>
                                    <div class="col-md-4">
                                        <button type="button" class="btn btn-primary">
                                            <i class="fa fa-search"></i> 检 索
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer"></div>
                        <div class="card-block">
                            <p>场馆收支统计</p>
                            <img src="/Content/images/demo/chart2.png" style="width: 100%">
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
    <c:param name="subMenu" value="summary"/>
</c:import>
