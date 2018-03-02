<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.h5.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <link href="/Content/dest/css/center/attendance.min.css?ver=${static_resource_version}" rel="stylesheet">
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script>
        $("#date3").calendar({
            container: "#inline-calendar",
            inputReadOnly: true,
            value: ["2017-12-02", "2017-12-03", "2017-12-09", "2017-12-10",
                "2017-12-16", "2017-12-17", "2017-12-23", "2017-12-24"]
        });
    </script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="page__bd">
        <div class="attendance-calendar">
            <input class="weui-input" id="date3" type="text" readonly style="display: none;">
            <div id="inline-calendar" class="attendance-item"></div>
        </div>
        <p class="weui-btn-area">
            <a href="javascript:;" class="weui-btn weui-btn_primary">我要签到</a>
        </p>
        <div class="weui-cells attendance-rewards">
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <img src="/Content/images/icon-calendar-checked.png">
                </div>
                <div class="weui-cell__bd">
                    <p>连续签到1次送积分</p>
                </div>
                <div class="weui-cell__ft">+10</div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <img src="/Content/images/icon-calendar.png">
                </div>
                <div class="weui-cell__bd">
                    <p>连续签到10次送积分</p>
                </div>
                <div class="weui-cell__ft">+100</div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <img src="/Content/images/icon-calendar.png">
                </div>
                <div class="weui-cell__bd">
                    <p>连续签到50次送积分</p>
                </div>
                <div class="weui-cell__ft">+500</div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <img src="/Content/images/icon-calendar.png">
                </div>
                <div class="weui-cell__bd">
                    <p>连续签到100次送积分</p>
                </div>
                <div class="weui-cell__ft">+1000</div>
            </div>
        </div>
    </div>
</layout:override>

<c:import url="../../Shared/GeneralLayout.jsp">
    <c:param name="title" value="签到"/>
</c:import>
