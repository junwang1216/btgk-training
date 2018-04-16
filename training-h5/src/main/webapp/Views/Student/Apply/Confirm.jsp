<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.h5.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <link href="/Content/dest/css/apply/confirm.min.css?ver=${static_resource_version}" rel="stylesheet">
    <style type="text/css">
        .js_grid {
            width: 50%;
        }
        a.weui-grid.js_grid.active {
            background: #dcdcdc;
        }
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="/Content/js/student/apply/confirm.js?ver=${static_resource_version}"></script>
    <script type="text/javascript">
        $(document).ready(function () {
//            $.modal({
//                title: "支付确认",
//                text: "你已经报名成功，请去支付！",
//                buttons: [
//                    { text: "支付", onClick: function () { console.log(2)} },
//                    { text: "取消", className: "default", onClick: function() { console.log(3)} }
//                ]
//            });
        });
    </script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="page__bd">
        <div class="weui-flex course-item">
            <div class="weui-flex__item course-info">
                <div class="course-title">${orgClass.className}</div>
                <div class="course-money">
                    报名金额：<span>${orgClass.classPrice}</span>元
                </div>
            </div>
        </div>
        <div class="weui-cells__title">报名信息</div>
        <div class="weui-cells weui-cells_form course-user">
            <input class="weui-input" type="hidden" name="classId" value="${orgClass.id}">
            <c:if test="${orgStudents == null}">
                <div class="weui-cell">
                    <div class="weui-cell__hd">
                        <label class="weui-label">真实姓名</label>
                    </div>
                    <div class="weui-cell__bd">
                        <input class="weui-input" type="text" name="realName" placeholder="请输入真实姓名" value="王梓睿">
                    </div>
                </div>
                <div class="weui-cell">
                    <div class="weui-cell__hd">
                        <label class="weui-label">手机号码</label>
                    </div>
                    <div class="weui-cell__bd">
                        <input class="weui-input" type="tel" name="mobile" placeholder="请输入手机号码" value="15801223456">
                    </div>
                </div>
                <div class="weui-cell">
                    <div class="weui-cell__hd">
                        <label class="weui-label">出生日期</label>
                    </div>
                    <div class="weui-cell__bd">
                        <input class="weui-input" type="date" name="birthday" placeholder="请选择出生日期" value="1995-12-12">
                    </div>
                </div>
                <div class="weui-cell">
                    <div class="weui-cell__hd">
                        <label class="weui-label">体重(kg)</label>
                    </div>
                    <div class="weui-cell__bd">
                        <input class="weui-input" type="tel" name="weight" placeholder="请输入体重" value="50">
                    </div>
                </div>
                <div class="weui-cell">
                    <div class="weui-cell__hd">
                        <label class="weui-label">身高(cm)</label>
                    </div>
                    <div class="weui-cell__bd">
                        <input class="weui-input" type="tel" name="height" placeholder="请输入身高" value="140">
                    </div>
                </div>
            </c:if>
            <c:if test="${orgStudents != null}">
                <input class="weui-input" type="hidden" name="studentId" value="${orgStudents.id}">
                <div class="weui-cell">
                    <div class="weui-cell__hd">
                        <label class="weui-label">真实姓名</label>
                    </div>
                    <div class="weui-cell__bd">
                        <input class="weui-input" type="text" name="realName" readonly value="${orgStudents.realName}">
                    </div>
                </div>
                <div class="weui-cell">
                    <div class="weui-cell__hd">
                        <label class="weui-label">手机号码</label>
                    </div>
                    <div class="weui-cell__bd">
                        <input class="weui-input" type="tel" name="mobile" readonly value="${orgStudents.mobile}">
                    </div>
                </div>
                <div class="weui-cell">
                    <div class="weui-cell__hd">
                        <label class="weui-label">出生日期</label>
                    </div>
                    <div class="weui-cell__bd">
                        <input class="weui-input" type="date" name="birthday" readonly value="${orgStudents.birthday}">
                    </div>
                </div>
                <div class="weui-cell">
                    <div class="weui-cell__hd">
                        <label class="weui-label">体重(kg)</label>
                    </div>
                    <div class="weui-cell__bd">
                        <input class="weui-input" type="tel" name="weight" readonly value="${orgStudents.weight}">
                    </div>
                </div>
                <div class="weui-cell">
                    <div class="weui-cell__hd">
                        <label class="weui-label">身高(cm)</label>
                    </div>
                    <div class="weui-cell__bd">
                        <input class="weui-input" type="tel" name="height" readonly value="${orgStudents.height}">
                    </div>
                </div>
            </c:if>
        </div>

        <label for="weuiAgree" class="weui-agree">
            <input id="weuiAgree" type="checkbox" class="weui-agree__checkbox" checked style="display: none">
            <span class="weui-agree__text">阅读并同意<a href="javascript:void(0);">《乐享培训报名协议》</a></span>
        </label>

        <p class="weui-btn-area">
            <a id="confirm_apply" href="javascript:;" class="weui-btn weui-btn_primary">确认报名</a>
        </p>
    </div>

    <div id="pay_confirm" class="weui-popup__container popup-bottom">
        <div class="weui-popup__overlay"></div>
        <div class="weui-popup__modal">
            <div class="toolbar">
                <div class="toolbar-inner">
                    <a href="javascript:;" class="picker-button close-popup">关闭</a>
                    <h1 class="title">支付确认</h1>
                </div>
            </div>
            <div class="modal-content">
                <div class="weui-grids">
                    <a href="javascript:;" class="weui-grid js_grid" data-type="2">
                        <div class="weui-grid__icon">
                            <img src="/Content/images/payment/icon-weixin.png" alt="微信">
                        </div>
                        <p class="weui-grid__label">微信</p>
                    </a>
                    <a href="javascript:;" class="weui-grid js_grid" data-type="3">
                        <div class="weui-grid__icon">
                            <img src="/Content/images/payment/icon-alipay.png" alt="支付宝">
                        </div>
                        <p class="weui-grid__label">支付宝</p>
                    </a>
                </div>
                <div class="weui-msg">
                    <div class="weui-msg__text-area">
                        <input type="hidden" name="orderNo" class="form-control">
                        <input type="hidden" name="payAmount" class="form-control">
                        <h2 class="weui-msg__title pay-amount">2400元</h2>
                        <p class="weui-msg__desc text-center">支付金额</p>
                    </div>
                    <div class="weui-msg__opr-area">
                        <p class="weui-btn-area">
                            <a id="confirm_pay" href="javascript:;" class="weui-btn weui-btn_primary">确认支付</a>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</layout:override>

<c:import url="../../Shared/GeneralLayout.jsp">
    <c:param name="title" value="确认报名"/>
</c:import>
