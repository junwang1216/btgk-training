<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.uk.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <style type="text/css">
        .container-fluid {
            background: #fff url("/Content/images/welcome.jpg?v=${static_resource_version}") no-repeat bottom left;
            background-size: auto 100%;
            height: 90%;
        }
        .jumbotron {
            background: none;
            margin-top: 10rem;
        }
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/dashboard/index.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="container-fluid">
        <div class="animated fadeIn">
            <div class="row">
                <div class="offset-4 col-md-8">
                    <div class="jumbotron jumbotron-fluid">
                        <div class="container">
                            <h1 class="display-5 text-center">员工业绩系统</h1>
                            <h2 class="display-6 text-center mt-4">欢迎您</h2>
                            <p class="lead"></p>
                        </div>
                    </div>
                </div>
            </div>
            <!--/.card-->
        </div>
    </div>
</layout:override>

<c:import url="../Shared/GeneralLayout.jsp">
    <c:param name="menu" value="dashboard"/>
</c:import>
