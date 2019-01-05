<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.uk.layout.Blocks" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <style type="text/css">
        tr.collapse.in {
            display: table-row;
        }
        .table-bordered th,
        .table-bordered td {
            text-align: center;
        }
        .width-10rem {
            width: 10rem;
        }
        .width-8rem {
            width: 8rem;
        }
        .width-6rem {
            width: 6rem;
        }
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/finance/performanceSummary.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="container-fluid">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <strong>运营财务报表</strong>
                            <small>Operation Finance Summary</small>

                            <div class="card-actions">
                                <a href="/admin/finance/performanceSummary/export?typeTime=${typeTime}" class="btn-cloud-download" title="导出数据" target="_blank">
                                    <i class="icon-cloud-download"></i>
                                </a>
                            </div>
                        </div>
                        <div class="card-block">
                            <div class="btn-toolbar justify-content-between" role="toolbar">
                                <div class="btn-group" data-toggle="buttons">
                                    <label class="btn btn-outline-secondary <c:if test="${typeTime == 'prev_day'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type1" value="prev_day"
                                               <c:if test="${typeTime == 'prev_day'}">checked</c:if> >昨 日
                                    </label>
                                    <label class="btn btn-outline-secondary mr-3 <c:if test="${typeTime == 'day'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type2" value="day"
                                               <c:if test="${typeTime == 'day'}">checked</c:if> >今 日
                                    </label>

                                    <label class="btn btn-outline-secondary <c:if test="${typeTime == 'prev_week'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type3" value="prev_week"
                                               <c:if test="${typeTime == 'prev_week'}">checked</c:if> >上 周
                                    </label>
                                    <label class="btn btn-outline-secondary mr-3 <c:if test="${typeTime == 'week'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type4" value="week"
                                               <c:if test="${typeTime == 'week'}">checked</c:if> >本 周
                                    </label>

                                    <label class="btn btn-outline-secondary <c:if test="${typeTime == 'prev_month'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type5" value="prev_month"
                                               <c:if test="${typeTime == 'prev_month'}">checked</c:if> >上 月
                                    </label>
                                    <label class="btn btn-outline-secondary mr-3 <c:if test="${typeTime == 'month'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type6" value="month"
                                               <c:if test="${typeTime == 'month'}">checked</c:if> >本 月
                                    </label>

                                    <label class="btn btn-outline-secondary <c:if test="${typeTime == 'prev_period'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type9" value="prev_period"
                                               <c:if test="${typeTime == 'prev_period'}">checked</c:if> >上 季
                                    </label>
                                    <label class="btn btn-outline-secondary mr-3 <c:if test="${typeTime == 'period'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type10" value="period"
                                               <c:if test="${typeTime == 'period'}">checked</c:if> >本 季
                                    </label>

                                    <label class="btn btn-outline-secondary <c:if test="${typeTime == 'prev_year'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type7" value="prev_year"
                                               <c:if test="${typeTime == 'prev_year'}">checked</c:if> >去 年
                                    </label>
                                    <label class="btn btn-outline-secondary <c:if test="${typeTime == 'year'}">active</c:if> ">
                                        <input type="radio" name="total_students_type" id="total_students_type8" value="year"
                                               <c:if test="${typeTime == 'year'}">checked</c:if> >今 年
                                    </label>
                                </div>
                                <div class="btn-group" data-toggle="buttons">
                                    <label class="btn btn-outline-secondary <c:if test="${busType == 0}">active</c:if> ">
                                        <input type="radio" name="total_bus_type" id="total_bus_type0" value="0"
                                               <c:if test="${busType == 0}">checked</c:if> >全部业务
                                    </label>
                                    <label class="btn btn-outline-secondary <c:if test="${busType == 1}">active</c:if> ">
                                        <input type="radio" name="total_bus_type" id="total_bus_type1" value="1"
                                               <c:if test="${busType == 1}">checked</c:if> >青少年培训
                                    </label>
                                    <label class="btn btn-outline-secondary <c:if test="${busType == 2}">active</c:if> ">
                                        <input type="radio" name="total_bus_type" id="total_bus_type2" value="2"
                                               <c:if test="${busType == 2}">checked</c:if> >场地租赁
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="card-footer"></div>
                        <div class="card-block">
                            <div class="table-overflow" style="overflow: auto;">
                                <table class="table table-responsive-sm table-bordered table-parent-content table-hover" style="width: 186rem;">
                                    <thead>
                                    <tr>
                                        <th rowspan="2" class="width-10rem">所在基地</th>
                                        <th rowspan="2" class="width-10rem">真实姓名</th>
                                        <th colspan="3" class="text-center">流水情况（元）</th>
                                        <th colspan="3" class="text-center">确认收入情况（元）</th>
                                        <th rowspan="2" class="width-10rem">真实姓名</th>
                                        <th colspan="3" class="text-center">签到情况（人）</th>
                                        <th colspan="3" class="text-center">体验成交（人）</th>
                                        <th rowspan="2" class="width-10rem">真实姓名</th>
                                        <th colspan="6">场地利用情况（时）</th>
                                    </tr>
                                    <tr>
                                        <th class="width-10rem">完成情况</th>
                                        <th class="width-10rem">最低目标</th>
                                        <th class="width-10rem">挑战目标</th>

                                        <th class="width-10rem">完成情况</th>
                                        <th class="width-10rem">最低目标</th>
                                        <th class="width-10rem">挑战目标</th>

                                        <th class="width-8rem">在册数</th>
                                        <th class="width-8rem">到课数</th>
                                        <th class="width-8rem">到课率</th>

                                        <th class="width-8rem">体验数</th>
                                        <th class="width-8rem">成交数</th>
                                        <th class="width-8rem">转化率</th>

                                        <th class="width-8rem">闲时占用</th>
                                        <th class="width-8rem">闲时总数</th>
                                        <th class="width-8rem">占用率</th>

                                        <th class="width-8rem">忙时占用</th>
                                        <th class="width-8rem">忙时总数</th>
                                        <th class="width-8rem">占用率</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="business" items="${orgFinanceDataResponseList}" varStatus="loop">
                                        <tr data-id="${business.businessNo}">
                                            <td class="width-10rem">${business.venueName}</td>
                                            <td class="width-10rem">
                                                <a data-toggle="collapse" href="#collapse${loop.index}" aria-expanded="false" aria-controls="collapse${loop.index}">${business.realName}</a>
                                            </td>

                                            <td class="width-10rem">
                                                <fmt:formatNumber value="${business.pipelineValue}" type="currency" maxFractionDigits="0" />
                                            </td>
                                            <td class="width-10rem">
                                                <fmt:formatNumber value="${business.pipelineTarget}" type="currency" maxFractionDigits="0" />
                                                (<fmt:formatNumber value="${business.pipelineTargetPercent}" type="percent" maxFractionDigits="2" minFractionDigits="2" />)
                                            </td>
                                            <td class="width-10rem">
                                                <fmt:formatNumber value="${business.pipelineChallenge}" type="currency" maxFractionDigits="0" />
                                                (<fmt:formatNumber value="${business.pipelineChallengePercent}" type="percent" maxFractionDigits="2" minFractionDigits="2" />)
                                            </td>

                                            <td class="width-10rem">
                                                <fmt:formatNumber value="${business.incomeValue}" type="currency" maxFractionDigits="0" />
                                            </td>
                                            <td class="width-10rem">
                                                <fmt:formatNumber value="${business.incomeTarget}" type="currency" maxFractionDigits="0" />
                                                (<fmt:formatNumber value="${business.incomeTargetPercent}" type="percent" maxFractionDigits="2" minFractionDigits="2" />)
                                            </td>
                                            <td class="width-10rem">
                                                <fmt:formatNumber value="${business.incomeChallenge}" type="currency" maxFractionDigits="0" />
                                                (<fmt:formatNumber value="${business.incomeChallengePercent}" type="percent" maxFractionDigits="2" minFractionDigits="2" />)
                                            </td>

                                            <td class="width-10rem">
                                                <a data-toggle="collapse" href="#collapse${loop.index}" aria-expanded="false" aria-controls="collapse${loop.index}">${business.realName}</a>
                                            </td>

                                            <td class="width-8rem">${business.registerCount}人</td>
                                            <td class="width-8rem">${business.classCount}人</td>
                                            <td class="width-8rem"><fmt:formatNumber value="${business.classCountPercent}" type="percent" maxFractionDigits="2" minFractionDigits="2" /></td>

                                            <td class="width-8rem">${business.accessCount}人</td>
                                            <td class="width-8rem">${business.businessCount}人</td>
                                            <td class="width-8rem"><fmt:formatNumber value="${business.businessCountPercent}" type="percent" maxFractionDigits="2" minFractionDigits="2" /></td>

                                            <td class="width-10rem">
                                                <a data-toggle="collapse" href="#collapse${loop.index}" aria-expanded="false" aria-controls="collapse${loop.index}">${business.realName}</a>
                                            </td>

                                            <td class="width-8rem">${business.nullCount}时</td>
                                            <td class="width-8rem">${business.nullTotalCount}时</td>
                                            <td class="width-8rem"><fmt:formatNumber value="${business.nullCountPercent}" type="percent" maxFractionDigits="2" minFractionDigits="2" /></td>

                                            <td class="width-8rem">${business.hotCount}时</td>
                                            <td class="width-8rem">${business.hotTotalCount}时</td>
                                            <td class="width-8rem"><fmt:formatNumber value="${business.hotCountPercent}" type="percent" maxFractionDigits="2" minFractionDigits="2" /></td>
                                        </tr>
                                        <tr class="collapse" id="collapse${loop.index}">
                                            <td colspan="21">
                                                <table class="table table-responsive-sm table-bordered table-children">
                                                    <thead>
                                                    <tr>
                                                        <th>渠道来源</th>
                                                        <th>流水情况</th>
                                                        <th>体验数</th>
                                                        <th>成交数</th>
                                                        <th>转化率</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:forEach var="businessChannel" items="${business.orgFinanceDataResponseChannelList}">
                                                        <c:if test="${businessChannel.channelName != null}">
                                                            <tr>
                                                                <td>${businessChannel.channelName}</td>
                                                                <td><fmt:formatNumber value="${businessChannel.pipelineValue}" type="currency" maxFractionDigits="0" /></td>
                                                                <td>${businessChannel.accessCount}人</td>
                                                                <td>${businessChannel.businessCount}人</td>
                                                                <td><fmt:formatNumber value="${businessChannel.businessCountPercent}" type="percent" maxFractionDigits="2" minFractionDigits="2" /></td>
                                                            </tr>
                                                        </c:if>
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
                                                <table class="table table-responsive-sm table-bordered table-children">
                                                    <thead>
                                                    <tr>
                                                        <th>收入类型</th>
                                                        <th>确认收入</th>
                                                        <th>在册数</th>
                                                        <th>到课数</th>
                                                        <th>到课率</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:forEach var="businessChannel" items="${business.orgFinanceDataResponseChannelList}">
                                                        <c:if test="${businessChannel.incomeType != null}">
                                                            <tr>
                                                                <td>${businessChannel.incomeType}</td>
                                                                <td><fmt:formatNumber value="${businessChannel.incomeValue}" type="currency" maxFractionDigits="0" /></td>
                                                                <td>${businessChannel.registerCount}人</td>
                                                                <td>${businessChannel.classCount}人</td>
                                                                <td><fmt:formatNumber value="${businessChannel.classCountPercent}" type="percent" maxFractionDigits="2" minFractionDigits="2" /></td>
                                                            </tr>
                                                        </c:if>
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--/.row-->
        </div>

    </div>
</layout:override>

<c:import url="../Shared/Layout.jsp">
    <c:param name="menu" value="finance"/>
    <c:param name="subMenu" value="summary"/>
</c:import>
