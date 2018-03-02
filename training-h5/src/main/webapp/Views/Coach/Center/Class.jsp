<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.h5.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <link href="/Content/dest/css/center/class.min.css?ver=${static_resource_version}" rel="stylesheet">
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="page__bd">
        <div class="course-list">
            <div class="weui-tab">
                <div class="weui-navbar">
                    <a class="weui-navbar__item" href="#course_start">未开始</a>
                    <a class="weui-navbar__item weui-bar__item--on" href="#course_going">进行中</a>
                    <a class="weui-navbar__item" href="#course_end">已结业</a>
                </div>
                <div class="weui-tab__bd">
                    <div id="course_start" class="weui-tab__bd-item">
                        <a class="weui-flex course-item" href="/mobile/center/class/detail?type=1&classId=123456">
                            <div class="weui-flex__item course-info">
                                <div class="course-title">青少年篮球训练营</div>
                                <div class="course-teacher">171201班</div>
                                <div class="course-date">报名时间：2018-01-23 至 2018-01-30</div>
                            </div>
                        </a>
                    </div>
                    <div id="course_going" class="weui-tab__bd-item weui-tab__bd-item--active">
                        <a class="weui-flex course-item" href="/mobile/center/class/detail?type=2&classId=123456">
                            <div class="weui-flex__item course-info">
                                <div class="course-title">青少年篮球训练营</div>
                                <div class="course-teacher">171201班</div>
                                <div class="course-date">上课进度：10/20课时</div>
                            </div>
                        </a>
                        <a class="weui-flex course-item" href="/mobile/center/class/detail?type=2&classId=123456">
                            <div class="weui-flex__item course-info">
                                <div class="course-title">青少年篮球训练营</div>
                                <div class="course-teacher">171202班</div>
                                <div class="course-date">上课进度：10/20课时</div>
                            </div>
                        </a>
                    </div>
                    <div id="course_end" class="weui-tab__bd-item">
                        <a class="weui-flex course-item" href="/mobile/center/class/detail?type=3&classId=123456">
                            <div class="weui-flex__item course-info">
                                <div class="course-title">青少年篮球训练营</div>
                                <div class="course-teacher">171201班</div>
                                <div class="course-date">结业时间：2018-01-30</div>
                            </div>
                        </a>
                        <a class="weui-flex course-item" href="/mobile/center/class/detail?type=3&classId=123456">
                            <div class="weui-flex__item course-info">
                                <div class="course-title">青少年篮球训练营</div>
                                <div class="course-teacher">171202班</div>
                                <div class="course-date">结业时间：2018-01-30</div>
                            </div>
                        </a>
                        <a class="weui-flex course-item" href="/mobile/center/class/detail?type=3&classId=123456">
                            <div class="weui-flex__item course-info">
                                <div class="course-title">青少年篮球训练营</div>
                                <div class="course-teacher">171202班</div>
                                <div class="course-date">结业时间：2018-01-30</div>
                            </div>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</layout:override>

<c:import url="../../Shared/GeneralLayout.jsp">
    <c:param name="title" value="我的班级"/>
</c:import>
