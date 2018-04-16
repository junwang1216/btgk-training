<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.h5.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <link href="/Content/dest/css/center/profile.min.css?ver=${static_resource_version}" rel="stylesheet">
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="/Content/js/student/center/profile.js?ver=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="page__bd">
        <div class="weui-cells__title">我的信息</div>
        <div class="weui-cells weui-cells_form course-user">
            <input type="hidden" value="${User.id}" name="id">
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label">真实姓名</label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="text" name="realName" placeholder="请输入真实姓名" value="${User.realName}">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label">手机号码</label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="tel" name="mobile" placeholder="请输入手机号码" value="${User.mobile}">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label">性别</label>
                </div>
                <div class="weui-cell__bd">
                    <select name="sex" class="weui-input">
                        <option value="1" <c:if test="${User.sex == 1}"> selected </c:if> >男</option>
                        <option value="2" <c:if test="${User.sex == 2}"> selected </c:if> >女</option>
                    </select>
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label">出生日期</label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="text" name="birthday" value="${User.birthday}" placeholder="请选择出生日期">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label">体重(kg)</label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="tel" name="weight" placeholder="请输入体重" value="${User.weight}">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label">身高(cm)</label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="tel" name="height" placeholder="请输入身高" value="${User.height}">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__bd">
                    <textarea class="weui-textarea" placeholder="请输入自我介绍" rows="3"></textarea>
                    <div class="weui-textarea-counter"><span>0</span>/200</div>
                </div>
            </div>
        </div>

        <p class="weui-btn-area">
            <a href="javascript:;" class="weui-btn weui-btn_primary save-user">保 存</a>
        </p>
    </div>
</layout:override>

<c:import url="../../Shared/GeneralLayout.jsp">
    <c:param name="title" value="修改信息"/>
</c:import>
