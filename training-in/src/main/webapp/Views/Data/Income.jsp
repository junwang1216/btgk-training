<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.in.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <style type="text/css">
        .income-list th {
            padding: 0.75rem;
        }
        .income-list td {
            padding: 0.3rem 0.75rem;
        }
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script async type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/students/list.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="modal fade" id="income_chart" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-primary modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-body row">
                    <div class="col-md-6 text-center">
                        <p>收入类型对比</p>
                        <img src="/Content/images/demo/chart1.png" style="width: 100%">
                    </div>
                    <div class="col-md-6 text-center">
                        <p>场馆收入对比</p>
                        <img src="/Content/images/demo/chart1.png" style="width: 100%">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-sm btn-secondary" data-dismiss="modal">
                        <i class="fa fa-close"></i> 关 闭
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="container-fluid">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <strong>收入流水</strong>
                            <small>Income</small>
                        </div>
                        <div class="card-block">
                            <form action="" method="post" class="form-horizontal">
                                <div class="form-group row">
                                    <div class="col-md-2">
                                        <a href="#" class="btn btn-outline-secondary">
                                            <i class="fa fa-calendar-minus-o"></i> 昨日
                                        </a>
                                        <a href="#" class="btn btn-outline-secondary">
                                            <i class="fa fa-calendar-check-o"></i> 今日
                                        </a>
                                    </div>
                                    <div class="col-md-2">
                                        <select id="income_date" name="incomeDate" class="form-control">
                                            <option>自定义期限</option>
                                            <option>最近一个月</option>
                                            <option>最近三个月</option>
                                            <option>最近六个月</option>
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        <input type="text" id="income_start" name="incomeStart" class="form-control" placeholder="开始日期">
                                    </div>
                                    <div class="col-md-2">
                                        <input type="text" id="income_end" name="incomeEnd" class="form-control" placeholder="结束日期">
                                    </div>
                                    <div class="col-md-4">
                                        <button type="button" class="btn btn-primary">
                                            <i class="fa fa-search"></i> 检 索
                                        </button>
                                        <a href="#" class="btn btn-primary pull-right"
                                           data-target="#income_chart" data-toggle="modal">
                                            <i class="fa fa-bar-chart"></i> 图表展示
                                        </a>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer"></div>
                        <div class="card-block">
                            <ul class="nav nav-tabs" role="tablist">
                                <li class="nav-item active"><a class="nav-link">全部流水</a></li>
                                <li class="nav-item"><a class="nav-link">未审核</a></li>
                                <li class="nav-item"><a class="nav-link">已审核</a></li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane active">
                                    <table class="table table-striped table-sm income-list">
                                        <thead>
                                        <tr>
                                            <th class="income-no">编号</th>
                                            <th class="income-type">缴费类型</th>
                                            <th class="income-student">交费学员</th>
                                            <th class="income-money">缴费金额</th>
                                            <th class="income-venue">所属场馆</th>
                                            <th class="income-date">缴费时间</th>
                                            <th class="income-person">经办人</th>
                                            <th class="income-person">审核人</th>
                                            <th class="income-date">审核时间</th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr data-id="">
                                            <td class="income-no">1</td>
                                            <td class="income-type">新生缴费</td>
                                            <td class="income-student">王大锤</td>
                                            <td class="income-money">¥3000.00</td>
                                            <td class="income-venue">奥体中心</td>
                                            <td class="income-date">2017-12-18 12:11:22</td>
                                            <td class="income-person">李逵</td>
                                            <td class="income-person">林冲</td>
                                            <td class="income-date">2017-12-18 18:15:00</td>
                                            <td>
                                                <a href="#" class="btn btn-sm btn-primary" title="查看详情">
                                                    <i class="fa fa-search"></i> 查看
                                                </a>
                                                <button class="btn btn-sm btn-success" title="审核" disabled>
                                                    <i class="fa fa-check"></i> 已审
                                                </button>
                                            </td>
                                        </tr>
                                        <tr data-id="">
                                            <td class="income-no">2</td>
                                            <td class="income-type">会员续费</td>
                                            <td class="income-student">王大锤</td>
                                            <td class="income-money">¥3000.00</td>
                                            <td class="income-venue">奥体中心</td>
                                            <td class="income-date">2017-12-18 12:11:22</td>
                                            <td class="income-person">李逵</td>
                                            <td class="income-person">--</td>
                                            <td class="income-date">--</td>
                                            <td>
                                                <a href="#" class="btn btn-sm btn-primary" title="查看详情">
                                                    <i class="fa fa-search"></i> 查看
                                                </a>
                                                <button class="btn btn-sm btn-danger" title="审核">
                                                    <i class="fa fa-clock-o"></i> 审核
                                                </button>
                                            </td>
                                        </tr>
                                        <tr data-id="">
                                            <td class="income-no">3</td>
                                            <td class="income-type">会员续费</td>
                                            <td class="income-student">王大锤</td>
                                            <td class="income-money">¥3000.00</td>
                                            <td class="income-venue">奥体中心</td>
                                            <td class="income-date">2017-12-18 12:11:22</td>
                                            <td class="income-person">李逵</td>
                                            <td class="income-person">林冲</td>
                                            <td class="income-date">2017-12-18 18:15:00</td>
                                            <td>
                                                <a href="#" class="btn btn-sm btn-primary" title="查看详情">
                                                    <i class="fa fa-search"></i> 查看
                                                </a>
                                                <button class="btn btn-sm btn-success" title="审核" disabled>
                                                    <i class="fa fa-check"></i> 已审
                                                </button>
                                            </td>
                                        </tr>
                                        <tr data-id="">
                                            <td class="income-no">4</td>
                                            <td class="income-type">新生缴费</td>
                                            <td class="income-student">王大锤</td>
                                            <td class="income-money">¥3000.00</td>
                                            <td class="income-venue">奥体中心</td>
                                            <td class="income-date">2017-12-18 12:11:22</td>
                                            <td class="income-person">李逵</td>
                                            <td class="income-person">林冲</td>
                                            <td class="income-date">2017-12-18 18:15:00</td>
                                            <td>
                                                <a href="#" class="btn btn-sm btn-primary" title="查看详情">
                                                    <i class="fa fa-search"></i> 查看
                                                </a>
                                                <button class="btn btn-sm btn-success" title="审核" disabled>
                                                    <i class="fa fa-check"></i> 已审
                                                </button>
                                            </td>
                                        </tr>
                                        <tr data-id="">
                                            <td class="income-no">5</td>
                                            <td class="income-type">会员续费</td>
                                            <td class="income-student">王大锤</td>
                                            <td class="income-money">¥3000.00</td>
                                            <td class="income-venue">奥体中心</td>
                                            <td class="income-date">2017-12-18 12:11:22</td>
                                            <td class="income-person">李逵</td>
                                            <td class="income-person">--</td>
                                            <td class="income-date">--</td>
                                            <td>
                                                <a href="#" class="btn btn-sm btn-primary" title="查看详情">
                                                    <i class="fa fa-search"></i> 查看
                                                </a>
                                                <button class="btn btn-sm btn-danger" title="审核">
                                                    <i class="fa fa-clock-o"></i> 审核
                                                </button>
                                            </td>
                                        </tr>
                                        <tr data-id="">
                                            <td class="income-no">6</td>
                                            <td class="income-type">会员续费</td>
                                            <td class="income-student">王大锤</td>
                                            <td class="income-money">¥3000.00</td>
                                            <td class="income-venue">奥体中心</td>
                                            <td class="income-date">2017-12-18 12:11:22</td>
                                            <td class="income-person">李逵</td>
                                            <td class="income-person">林冲</td>
                                            <td class="income-date">2017-12-18 18:15:00</td>
                                            <td>
                                                <a href="#" class="btn btn-sm btn-primary" title="查看详情">
                                                    <i class="fa fa-search"></i> 查看
                                                </a>
                                                <button class="btn btn-sm btn-success" title="审核" disabled>
                                                    <i class="fa fa-check"></i> 已审
                                                </button>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <div>
                                        <%@ include file="../Shared/Pagination.jsp" %>
                                    </div>
                                </div>
                            </div>
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
    <c:param name="subMenu" value="income"/>
</c:import>
