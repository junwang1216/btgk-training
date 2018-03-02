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
                <div class="course-date">报名时间：2018-01-23 至 2018-01-30</div>
            </div>
        </div>
        <div class="weui-panel course-detail">
            <div class="weui-panel__hd">课程介绍</div>
            <div class="weui-panel__bd">
                <div class="weui-media-box weui-media-box_text">
                    <p class="weui-media-box__desc">1、顶级中、外教现场授课，英语口语与篮球运动相结合的创新教育模式</p>
                    <p class="weui-media-box__desc">2、百个场馆时段与您就近匹配</p>
                    <p class="weui-media-box__desc">3、丰富多彩的篮球赛事和兴趣活动</p>
                    <p class="weui-media-box__desc">4、优肯定制化的学员成长数据管理系统</p>
                </div>
            </div>
        </div>
        <div class="weui-panel course-table">
            <div class="weui-panel__hd">课程流程</div>
            <div class="weui-panel__bd">
                <div class="weui-media-box weui-media-box_text">
                    <p class="weui-media-box__desc">
                        活动热身（身体拉伸，活动预热等）<span style="float: right">0~40分钟</span>
                    </p>
                </div>
                <div class="weui-media-box weui-media-box_text">
                    <p class="weui-media-box__desc">
                        基础技能训练（传球，运球，投篮等）<span style="float: right">40~80分钟</span>
                    </p>
                </div>
                <div class="weui-media-box weui-media-box_text">
                    <p class="weui-media-box__desc">
                        对抗游戏，分组对抗，3V3等<span style="float: right">80~120分钟</span>
                    </p>
                </div>
            </div>
        </div>

        <p class="weui-btn-area">
            <a href="javascript:;" class="weui-btn weui-btn_disabled weui-btn_primary">我已报名</a>
        </p>
    </div>
</layout:override>

<c:import url="../../Shared/GeneralLayout.jsp">
    <c:param name="title" value="课程介绍"/>
</c:import>
