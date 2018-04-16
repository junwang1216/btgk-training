<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.h5.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <link href="/Content/dest/css/enter/index.min.css?ver=${static_resource_version}" rel="stylesheet">
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="/Content/bower_components/jquery-qrcode/dist/jquery-qrcode.min.js?ver=${static_resource_version}"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            var url = window.location.protocol + "//" + window.location.hostname + ":" + window.location.port;
            $('#student_apply_enter').qrcode({
                background: "#FFFFFF",
                text: url + "/student/apply/list?_t=" + (new Date()).getTime()
            });

            $('#student_course_enter').qrcode({
                background: "#FFFFFF",
                text: url + "/student/center/index?_t=" + (new Date()).getTime()
            });
        });
    </script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="page__bd">
        <div class="weui-panel">
            <div class="weui-panel__hd">学员报名</div>
            <div class="weui-panel__bd">
                <div id="student_apply_enter"></div>
            </div>
        </div>
        <div class="weui-panel">
            <div class="weui-panel__hd">我的课程</div>
            <div class="weui-panel__bd">
                <div id="student_course_enter"></div>
            </div>
        </div>
    </div>
</layout:override>

<c:import url="../Shared/GeneralLayout.jsp">
    <c:param name="title" value="学员入口"/>
</c:import>
