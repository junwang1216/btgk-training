<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.in.layout.Blocks" %>
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
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/data/operationFinance.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="container-fluid">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <strong>运营财务报表</strong>
                            <small>Operation Finance</small>

                            <div class="card-actions">
                                <a href="/admin/data/operation/finance/edit" class="btn-settings" title="详情编辑"><i class="icon-settings"></i></a>
                                <a href="/admin/data/operation/finance/log" class="btn-list" title="详情日志"><i class="icon-list"></i></a>
                                <a href="/admin/data/operation/finance/export" class="btn-cloud-download" title="导出数据"><i class="icon-cloud-download"></i></a>
                            </div>
                        </div>
                        <div class="card-block">
                            <div class="btn-toolbar" role="toolbar">
                                <div class="btn-group" data-toggle="buttons">
                                    <label class="btn btn-outline-secondary mr-3">
                                        <input type="radio" name="total_students_type" id="total_students_type11" value="all">全 部
                                    </label>

                                    <label class="btn btn-outline-secondary">
                                        <input type="radio" name="total_students_type" id="total_students_type1" value="prev_day">昨 日
                                    </label>
                                    <label class="btn btn-outline-secondary mr-3">
                                        <input type="radio" name="total_students_type" id="total_students_type2" value="day">今 日
                                    </label>

                                    <label class="btn btn-outline-secondary">
                                        <input type="radio" name="total_students_type" id="total_students_type3" value="prev_week">上 周
                                    </label>
                                    <label class="btn btn-outline-secondary mr-3">
                                        <input type="radio" name="total_students_type" id="total_students_type4" value="week">本 周
                                    </label>

                                    <label class="btn btn-outline-secondary">
                                        <input type="radio" name="total_students_type" id="total_students_type5" value="prev_month">上 月
                                    </label>
                                    <label class="btn btn-outline-secondary mr-3">
                                        <input type="radio" name="total_students_type" id="total_students_type6" value="month">本 月
                                    </label>

                                    <label class="btn btn-outline-secondary">
                                        <input type="radio" name="total_students_type" id="total_students_type9" value="prev_ji">上 季
                                    </label>
                                    <label class="btn btn-outline-secondary mr-3">
                                        <input type="radio" name="total_students_type" id="total_students_type10" value="ji">本 季
                                    </label>

                                    <label class="btn btn-outline-secondary">
                                        <input type="radio" name="total_students_type" id="total_students_type7" value="prev_year">去 年
                                    </label>
                                    <label class="btn btn-outline-secondary active">
                                        <input type="radio" name="total_students_type" id="total_students_type8" value="year" checked="checked">今 年
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="card-footer"></div>
                        <div class="card-block">
                            <table class="table table-responsive-sm table-bordered">
                                <thead>
                                <tr>
                                    <th rowspan="2">所在基地</th>
                                    <th rowspan="2">姓名</th>
                                    <th colspan="3" class="text-center">流水情况（元）</th>
                                    <th colspan="3" class="text-center">确认收入情况（元）</th>
                                    <th colspan="2" class="text-center">青训人数（人）</th>
                                    <th colspan="3" class="text-center">个人渠道（人）</th>
                                    <th colspan="3" class="text-center">转介绍渠道（人）</th>
                                    <th rowspan="2">非公司渠转化率</th>
                                    <th colspan="3" class="text-center">公司渠道（人）</th>
                                    <th rowspan="2">整体转化率</th>
                                </tr>
                                <tr>
                                    <th>完成情况</th>
                                    <th>最低目标</th>
                                    <th>挑战目标</th>

                                    <th>完成情况</th>
                                    <th>最低目标</th>
                                    <th>挑战目标</th>

                                    <th>在册人数</th>
                                    <th>到课人数</th>

                                    <th>体验数</th>
                                    <th>成交数</th>
                                    <th>转化率</th>

                                    <th>体验数</th>
                                    <th>成交数</th>
                                    <th>转化率</th>

                                    <th>体验数</th>
                                    <th>成交数</th>
                                    <th>转化率</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="business" items="${orgFinanceList}">
                                    <tr data-id="${business.businessNo}">
                                        <td>
                                            <c:forEach var="base" items="${orgFinanceBaseList}">
                                                <c:if test="${base.enumValue == business.baseType}">${base.enumNote}</c:if>
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <a data-toggle="collapse" href="#collapse${business.businessNo}" aria-expanded="false" aria-controls="collapse${business.businessNo}">${business.realName}</a>
                                        </td>

                                        <td>
                                            <fmt:formatNumber value="${business.pipelineValue}" type="currency" maxFractionDigits="0" />
                                        </td>
                                        <td>
                                            <fmt:formatNumber value="${business.pipelineTarget}" type="currency" maxFractionDigits="0" />
                                            (<fmt:formatNumber value="${business.pipelineTargetPercent}" type="percent" maxFractionDigits="2" minFractionDigits="2" />)
                                        </td>
                                        <td>
                                            <fmt:formatNumber value="${business.pipelineChallenge}" type="currency" maxFractionDigits="0" />
                                            (<fmt:formatNumber value="${business.pipelineChallengePercent}" type="percent" maxFractionDigits="2" minFractionDigits="2" />)
                                        </td>

                                        <td>
                                            <fmt:formatNumber value="${business.incomeValue}" type="currency" maxFractionDigits="0" />
                                        </td>
                                        <td>
                                            <fmt:formatNumber value="${business.incomeTarget}" type="currency" maxFractionDigits="0" />
                                            (<fmt:formatNumber value="${business.incomeTargetPercent}" type="percent" maxFractionDigits="2" minFractionDigits="2" />)
                                        </td>
                                        <td>
                                            <fmt:formatNumber value="${business.incomeChallenge}" type="currency" maxFractionDigits="0" />
                                            (<fmt:formatNumber value="${business.incomeChallengePercent}" type="percent" maxFractionDigits="2" minFractionDigits="2" />)
                                        </td>

                                        <td>${business.registerCount}人</td>
                                        <td>${business.classCount}人</td>

                                        <td>${business.accessCount2}人</td>
                                        <td>${business.businessCount2}人</td>
                                        <td><fmt:formatNumber value="${business.businessCount2Percent}" type="percent" maxFractionDigits="2" minFractionDigits="2" /></td>

                                        <td>${business.accessCount3}人</td>
                                        <td>${business.businessCount3}人</td>
                                        <td><fmt:formatNumber value="${business.businessCount3Percent}" type="percent" maxFractionDigits="2" minFractionDigits="2" /></td>

                                        <td><fmt:formatNumber value="${business.businessCountPerson}" type="percent" maxFractionDigits="2" minFractionDigits="2" /></td>

                                        <td>${business.accessCount1}人</td>
                                        <td>${business.businessCount1}人</td>
                                        <td><fmt:formatNumber value="${business.businessCount1Percent}" type="percent" maxFractionDigits="2" minFractionDigits="2" /></td>

                                        <td><fmt:formatNumber value="${business.businessCountPercent}" type="percent" maxFractionDigits="2" minFractionDigits="2" /></td>
                                    </tr>
                                    <tr class="collapse" id="collapse${business.businessNo}">
                                        <td colspan="21">
                                            <table class="table table-responsive-sm table-bordered">
                                                <thead>
                                                <tr>
                                                    <th>费用来源</th>
                                                    <th>流水情况</th>
                                                    <th>确认收入</th>
                                                    <th>在册人数</th>
                                                    <th>到课人数</th>
                                                    <th>体验数</th>
                                                    <th>成交数</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <tr>
                                                    <td>公司</td>
                                                    <td>
                                                        <fmt:formatNumber value="${business.pipelineValue1}" type="currency" maxFractionDigits="0" />
                                                    </td>
                                                    <td>
                                                        <fmt:formatNumber value="${business.incomeValue1}" type="currency" maxFractionDigits="0" />
                                                    </td>
                                                    <td>${business.registerCount1}人</td>
                                                    <td>${business.classCount1}人</td>
                                                    <td>${business.accessCount1}人</td>
                                                    <td>${business.businessCount1}人</td>
                                                </tr>
                                                <tr>
                                                    <td>个人</td>
                                                    <td>
                                                        <fmt:formatNumber value="${business.pipelineValue2}" type="currency" maxFractionDigits="0" />
                                                    </td>
                                                    <td>
                                                        <fmt:formatNumber value="${business.incomeValue2}" type="currency" maxFractionDigits="0" />
                                                    </td>
                                                    <td>${business.registerCount2}人</td>
                                                    <td>${business.classCount2}人</td>
                                                    <td>${business.accessCount2}人</td>
                                                    <td>${business.businessCount2}人</td>
                                                </tr>
                                                <tr>
                                                    <td>转介绍</td>
                                                    <td>
                                                        <fmt:formatNumber value="${business.pipelineValue3}" type="currency" maxFractionDigits="0" />
                                                    </td>
                                                    <td>
                                                        <fmt:formatNumber value="${business.incomeValue3}" type="currency" maxFractionDigits="0" />
                                                    </td>
                                                    <td>${business.registerCount3}人</td>
                                                    <td>${business.classCount3}人</td>
                                                    <td>${business.accessCount3}人</td>
                                                    <td>${business.businessCount3}人</td>
                                                </tr>
                                                <tr>
                                                    <td>续费</td>
                                                    <td>
                                                        <fmt:formatNumber value="${business.pipelineValue4}" type="currency" maxFractionDigits="0" />
                                                    </td>
                                                    <td>
                                                        <fmt:formatNumber value="${business.incomeValue4}" type="currency" maxFractionDigits="0" />
                                                    </td>
                                                    <td>${business.registerCount4}人</td>
                                                    <td>${business.classCount4}人</td>
                                                    <td>${business.accessCount4}人</td>
                                                    <td>${business.businessCount4}人</td>
                                                </tr>
                                                <tr>
                                                    <td>合计</td>
                                                    <td>
                                                        <fmt:formatNumber value="${business.pipelineValue}" type="currency" maxFractionDigits="0" />
                                                    </td>
                                                    <td>
                                                        <fmt:formatNumber value="${business.incomeValue}" type="currency" maxFractionDigits="0" />
                                                    </td>
                                                    <td>${business.registerCount}人</td>
                                                    <td>${business.classCount}人</td>
                                                    <td>${business.accessCount}人</td>
                                                    <td>${business.businessCount}人</td>
                                                </tr>
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
            <!--/.row-->
        </div>

    </div>
</layout:override>

<c:import url="../Shared/GeneralLayout.jsp">
    <c:param name="menu" value="data"/>
    <c:param name="subMenu" value="finance"/>
</c:import>
