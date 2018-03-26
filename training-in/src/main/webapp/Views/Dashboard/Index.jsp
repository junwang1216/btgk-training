<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.in.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/dashboard/index.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="container-fluid">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-sm-6 col-lg-3">
                    <div class="card card-inverse card-primary">
                        <div class="card-block pb-0">
                            <div class="btn-group float-right">
                                <button type="button" class="btn btn-transparent active dropdown-toggle p-0" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <i class="icon-settings"></i>
                                </button>
                                <div class="dropdown-menu dropdown-menu-right">
                                    <a class="dropdown-item" href="#">当  日</a>
                                    <a class="dropdown-item" href="#">近一周</a>
                                    <a class="dropdown-item" href="#">近一月</a>
                                    <a class="dropdown-item" href="#">总  量</a>
                                </div>
                            </div>
                            <h4 class="mb-0">2/10</h4>
                            <p>上课统计（班次/课时）</p>
                        </div>
                        <div class="chart-wrapper px-3" style="height:70px;">
                            <canvas id="card-chart1" class="chart" height="70"></canvas>
                        </div>
                    </div>
                </div>
                <!--/.col-->

                <div class="col-sm-6 col-lg-3 total-class">
                    <div class="card card-inverse card-info">
                        <div class="card-block pb-0">
                            <div class="btn-group float-right">
                                <button type="button" class="btn btn-transparent dropdown-toggle p-0" data-toggle="dropdown">
                                    <i class="icon-book-open"></i>
                                </button>
                                <div class="dropdown-menu dropdown-menu-right">
                                    <a class="dropdown-item" href="javascript:;" data-type="month">本  月</a>
                                    <a class="dropdown-item" href="javascript:;" data-type="year">本  年</a>
                                    <a class="dropdown-item" href="javascript:;" data-type="total">总  量</a>
                                </div>
                            </div>
                            <h4 class="mb-0 total-class-count">0</h4>
                            <p class="total-class-name">年班级数量（个）</p>
                        </div>
                        <div class="chart-wrapper px-3" style="height: 70px;">
                            <canvas id="total_class_chart" class="chart" height="70"></canvas>
                        </div>
                    </div>
                </div>
                <!--/.col-->

                <div class="col-sm-6 col-lg-3">
                    <div class="card card-inverse card-warning">
                        <div class="card-block pb-0">
                            <div class="btn-group float-right">
                                <button type="button" class="btn btn-transparent active dropdown-toggle p-0" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <i class="icon-settings"></i>
                                </button>
                                <div class="dropdown-menu dropdown-menu-right">
                                    <a class="dropdown-item" href="#">当  日</a>
                                    <a class="dropdown-item" href="#">近一周</a>
                                    <a class="dropdown-item" href="#">近一月</a>
                                    <a class="dropdown-item" href="#">总  量</a>
                                </div>
                            </div>
                            <h4 class="mb-0">19,823</h4>
                            <p>入账金额（元）</p>
                        </div>
                        <div class="chart-wrapper" style="height:70px;">
                            <canvas id="card-chart3" class="chart" height="70"></canvas>
                        </div>
                    </div>
                </div>
                <!--/.col-->

                <div class="col-sm-6 col-lg-3">
                    <div class="card card-inverse card-danger">
                        <div class="card-block pb-0">
                            <h4 class="mb-0">9,823元</h4>
                            <p>出账金额（元）</p>
                        </div>
                        <div class="chart-wrapper px-3" style="height:70px;">
                            <canvas id="card-chart4" class="chart" height="70"></canvas>
                        </div>
                    </div>
                </div>
                <!--/.col-->
            </div>
            <!--/.row-->

            <div class="card">
                <div class="card-block">
                    <div class="row">
                        <div class="col-sm-5">
                            <h4 class="card-title mb-0">学员数量（人）</h4>
                            <div class="small text-muted">2017年05月</div>
                        </div>
                        <!--/.col-->
                        <div class="col-sm-7 hidden-sm-down">
                            <button type="button" class="btn btn-primary float-right"><i class="icon-cloud-download"></i>
                            </button>
                            <div class="btn-toolbar float-right" role="toolbar" aria-label="Toolbar with button groups">
                                <div class="btn-group mr-3" data-toggle="buttons" aria-label="First group">
                                    <label class="btn btn-outline-secondary">
                                        <input type="radio" name="options" id="option1">日
                                    </label>
                                    <label class="btn btn-outline-secondary active">
                                        <input type="radio" name="options" id="option2" checked="">月
                                    </label>
                                    <label class="btn btn-outline-secondary">
                                        <input type="radio" name="options" id="option3">年
                                    </label>
                                </div>
                            </div>
                        </div>
                        <!--/.col-->
                    </div>
                    <!--/.row-->
                    <div class="chart-wrapper" style="height:300px;margin-top:40px;">
                        <canvas id="main-chart" class="chart" height="300"></canvas>
                    </div>
                </div>
                <div class="card-footer">
                    <ul>
                        <li>
                            <div class="text-muted">用户访问量</div>
                            <strong>29.703 Users (40%)</strong>
                            <div class="progress progress-xs mt-2">
                                <div class="progress-bar bg-success" role="progressbar" style="width: 40%" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100"></div>
                            </div>
                        </li>
                        <li class="hidden-sm-down">
                            <div class="text-muted">注册用户量</div>
                            <strong>24.093 Users (20%)</strong>
                            <div class="progress progress-xs mt-2">
                                <div class="progress-bar bg-info" role="progressbar" style="width: 20%" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"></div>
                            </div>
                        </li>
                        <li>
                            <div class="text-muted">新学员量</div>
                            <strong>78.706 Views (60%)</strong>
                            <div class="progress progress-xs mt-2">
                                <div class="progress-bar bg-warning" role="progressbar" style="width: 60%" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"></div>
                            </div>
                        </li>
                        <li class="hidden-sm-down">
                            <div class="text-muted">老学员量</div>
                            <strong>22.123 Users (80%)</strong>
                            <div class="progress progress-xs mt-2">
                                <div class="progress-bar bg-danger" role="progressbar" style="width: 80%" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100"></div>
                            </div>
                        </li>
                        <li class="hidden-sm-down">
                            <div class="text-muted">转化率</div>
                            <strong>40.15%</strong>
                            <div class="progress progress-xs mt-2">
                                <div class="progress-bar" role="progressbar" style="width: 40%" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100"></div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <!--/.card-->
        </div>
    </div>
</layout:override>

<c:import url="../Shared/GeneralLayout.jsp">
    <c:param name="menu" value="dashboard"/>
</c:import>
