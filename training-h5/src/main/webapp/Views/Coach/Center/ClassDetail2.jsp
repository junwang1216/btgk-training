<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.h5.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <link href="/Content/dest/css/center/detail.min.css?ver=${static_resource_version}" rel="stylesheet">
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="page__bd">
        <div class="weui-flex course-item">
            <div class="weui-flex__item course-info">
                <div class="course-title">青少年篮球训练营</div>
                <div class="course-teacher">张益帆</div>
                <div class="course-date">上课进度：10/20课时</div>
            </div>
        </div>

        <div class="weui-cells">
            <div class="weui-cell weui-cell_access">
                <div class="weui-cell__hd">
                    <img src="/Content/images/icon-selected.png" alt="">
                </div>
                <div class="weui-cell__bd">
                    <p>基础训练</p>
                </div>
                <div class="weui-cell__ft">2017-12-25</div>
            </div>
            <div class="weui-cell weui-cell_access">
                <div class="weui-cell__hd">
                    <img src="/Content/images/icon-selected.png" alt="">
                </div>
                <div class="weui-cell__bd">
                    <p>基础训练</p>
                </div>
                <div class="weui-cell__ft">2017-12-25</div>
            </div>
            <div class="weui-cell weui-cell_access">
                <div class="weui-cell__hd">
                    <img src="/Content/images/icon-unselected.png" alt="">
                </div>
                <div class="weui-cell__bd">
                    <p>基础训练</p>
                </div>
                <div class="weui-cell__ft">2017-12-25</div>
            </div>
            <div class="weui-cell weui-cell_access">
                <div class="weui-cell__hd">
                    <img src="/Content/images/icon-unselected.png" alt="">
                </div>
                <div class="weui-cell__bd">
                    <p>基础训练</p>
                </div>
                <div class="weui-cell__ft">2017-12-25</div>
            </div>
            <div class="weui-cell weui-cell_access">
                <div class="weui-cell__hd">
                    <img src="/Content/images/icon-unselected.png" alt="">
                </div>
                <div class="weui-cell__bd">
                    <p>基础训练</p>
                </div>
                <div class="weui-cell__ft">2017-12-25</div>
            </div>
            <div class="weui-cell weui-cell_access">
                <div class="weui-cell__hd">
                    <img src="/Content/images/icon-unselected.png" alt="">
                </div>
                <div class="weui-cell__bd">
                    <p>基础训练</p>
                </div>
                <div class="weui-cell__ft">2017-12-25</div>
            </div>
            <div class="weui-cell weui-cell_access">
                <div class="weui-cell__hd">
                    <img src="/Content/images/icon-unselected.png" alt="">
                </div>
                <div class="weui-cell__bd">
                    <p>基础训练</p>
                </div>
                <div class="weui-cell__ft">2017-12-25</div>
            </div>
        </div>
    </div>
</layout:override>

<c:import url="../../Shared/GeneralLayout.jsp">
    <c:param name="title" value="课程介绍"/>
</c:import>
