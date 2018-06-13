<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.uk.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <link href="Content/bower_components/bootstrap-datepicker/dist/css/bootstrap-datepicker3.css?v=${static_resource_version}" rel="stylesheet">
    <style type="text/css">
        .form-control-label {
            text-align: right;
            font-weight: bold;
        }
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/finance/add.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="container-fluid">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-header">
                            <strong>运营数据编辑</strong>
                            <small>Operation Finance Edit</small>
                        </div>
                        <div class="card-block">
                            <form id="business_form" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                                <div class="">
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label">
                                            <span class="text-danger">*</span> 业务日期
                                        </label>
                                        <div class="col-md-9">
                                            <input type="text" class="form-control datepicker" name="businessDate" placeholder="请输入业务日期" autocomplete="off"
                                                   data-val="true" data-val-required="业务日期不能为空">
                                            <div data-valmsg-for="businessDate" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label">
                                            <span class="text-danger">*</span> 所属基地
                                        </label>
                                        <div class="col-md-9">
                                            <select class="form-control" name="venueId" data-val="true" data-val-required="请选择所属基地">
                                                <c:forEach var="business" items="${orgFinanceVenuesList}">
                                                    <option value="${business.id}" <c:if test="${orgFinanceDataFlow.venueId == business.id}">selected</c:if> >${business.venueName}</option>
                                                </c:forEach>
                                            </select>
                                            <div data-valmsg-for="venueId" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label">
                                            <span class="text-danger">*</span> 业务员
                                        </label>
                                        <div class="col-md-9">
                                            <select class="form-control" name="userId" data-val="true" data-val-required="请选择业务员" data-default="${orgFinanceDataFlow.userId}">
                                                <option value="0">请选择</option>
                                            </select>
                                            <div data-valmsg-for="userId" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label">
                                            <span class="text-danger">*</span> 业务类型
                                        </label>
                                        <div class="col-md-9">
                                            <select class="form-control" name="businessType" data-val="true" data-val-required="请选择业务类型">
                                                <c:forEach var="business" items="${BusinessTypeEnumList}">
                                                    <option value="${business.code}" <c:if test="${orgFinanceData.businessType == business.code}">selected</c:if> >${business.desc}</option>
                                                </c:forEach>
                                            </select>
                                            <div data-valmsg-for="businessType" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                </div>
                                <hr>
                                <div class="block-training-venue">
                                    <div class="form-group row block-training">
                                        <label class="col-md-3 form-control-label">
                                            <span class="text-danger">*</span> 渠道来源
                                        </label>
                                        <div class="col-md-9">
                                            <select class="form-control" name="channelName">
                                                <c:forEach var="business" items="${BusinessChannelTypeEnumList}">
                                                    <option value="${business.enumNote}">${business.enumNote}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label">
                                            <span class="text-danger">*</span> 流水值
                                        </label>
                                        <div class="col-md-9">
                                            <input type="text" class="form-control" placeholder="流水值" name="pipelineValue" autocomplete="off"
                                                   data-val-regex-pattern="^[1-9][0-9]*$" data-val-regex="流水值必须是数字">
                                            <div data-valmsg-for="pipelineValue" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <div class="form-group row block-training">
                                        <label class="col-md-3 form-control-label">
                                            <span class="text-danger">*</span> 体验数
                                        </label>
                                        <div class="col-md-4">
                                            <input type="text" class="form-control" placeholder="体验数" name="accessCount" autocomplete="off"
                                                   data-val-regex-pattern="^[1-9][0-9]*$" data-val-regex="体验数必须是数字">
                                            <div data-valmsg-for="accessCount" data-valmsg-replace="true"></div>
                                        </div>
                                        <div class="col-md-5">
                                            <input type="text" class="form-control" placeholder="成交数" name="businessCount" autocomplete="off"
                                                   data-val-regex-pattern="^[1-9][0-9]*$" data-val-regex="成交数必须是数字"
                                                   data-val-reqgroup-element="accessCount" data-val-reqgroup="体验数和成交数必须关联输入">
                                            <div data-valmsg-for="businessCount" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <hr>
                                    <div class="form-group row block-training">
                                        <label class="col-md-3 form-control-label">
                                            <span class="text-danger">*</span> 课单价
                                        </label>
                                        <div class="col-md-4">
                                            <select class="form-control" name="incomeType">
                                                <c:forEach var="business" items="${BusinessIncomeTypeEnumList}">
                                                    <option value="${business.enumNote}">${business.enumNote}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col-md-5">
                                            <input type="text" class="form-control" placeholder="课单价" name="incomePerValue" value="${orgFinanceData.incomeValue}"
                                                   data-val-regex-pattern="^[1-9][0-9]*$" data-val-regex="课单价必须是数字">
                                            <div data-valmsg-for="incomeValue" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <div class="form-group row block-training">
                                        <label class="col-md-3 form-control-label">
                                            <span class="text-danger">*</span> 到课数
                                        </label>
                                        <div class="col-md-4">
                                            <input type="text" class="form-control" placeholder="在册数" name="registerCount" autocomplete="off"
                                                   data-val-regex-pattern="^[1-9][0-9]*$" data-val-regex="在册数必须是数字">
                                            <div data-valmsg-for="accessCount" data-valmsg-replace="true"></div>
                                        </div>
                                        <div class="col-md-5">
                                            <input type="text" class="form-control" placeholder="到课数" name="classCount" autocomplete="off"
                                                   data-val-regex-pattern="^[1-9][0-9]*$" data-val-regex="到课数必须是数字"
                                                   data-val-reqgroup-element="accessCount" data-val-reqgroup="在册和到课数必须关联输入">
                                            <div data-valmsg-for="businessCount" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                </div>
                                <div>
                                    <div class="form-group row block-venue">
                                        <label class="col-md-3 form-control-label">
                                            <span class="text-danger">*</span> 确认收入
                                        </label>
                                        <div class="col-md-9">
                                            <input type="text" class="form-control" placeholder="确认收入" name="incomeValue" value="${orgFinanceData.incomeValue}"
                                                   data-val-regex-pattern="^[1-9][0-9]*$" data-val-regex="确认收入必须是数字">
                                            <div data-valmsg-for="incomeValue" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <hr>
                                    <div class="form-group row block-venue">
                                        <label class="col-md-3 form-control-label">
                                            <span class="text-danger">*</span> 闲时段数
                                        </label>
                                        <div class="col-md-4">
                                            <input type="text" class="form-control" placeholder="闲时总数" name="nullTotalCount" autocomplete="off"
                                                   data-val-regex-pattern="^[1-9][0-9]*$" data-val-regex="闲时总数必须是数字">
                                            <div data-valmsg-for="nullTotalCount" data-valmsg-replace="true"></div>
                                        </div>
                                        <div class="col-md-5">
                                            <input type="text" class="form-control" placeholder="闲时占用数" name="nullCount" autocomplete="off"
                                                   data-val-regex-pattern="^[1-9][0-9]*$" data-val-regex="闲时占用数必须是数字"
                                                   data-val-reqgroup-element="nullTotalCount" data-val-reqgroup="总数和占用数必须关联输入">
                                            <div data-valmsg-for="nullCount" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <div class="form-group row block-venue">
                                        <label class="col-md-3 form-control-label">
                                            <span class="text-danger">*</span> 忙时段数
                                        </label>
                                        <div class="col-md-4">
                                            <input type="text" class="form-control" placeholder="忙时总数" name="hotTotalCount" autocomplete="off"
                                                   data-val-regex-pattern="^[1-9][0-9]*$" data-val-regex="忙时总数必须是数字">
                                            <div data-valmsg-for="hotTotalCount" data-valmsg-replace="true"></div>
                                        </div>
                                        <div class="col-md-5">
                                            <input type="text" class="form-control" placeholder="忙时占用数" name="hotCount" autocomplete="off"
                                                   data-val-regex-pattern="^[1-9][0-9]*$" data-val-regex="忙时占用数必须是数字"
                                                   data-val-reqgroup-element="hotTotalCount" data-val-reqgroup="总数和占用数必须关联输入">
                                            <div data-valmsg-for="hotCount" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer">
                            <button type="button" class="btn btn-primary save-business">
                                <i class="fa fa-check"></i> 保 存
                            </button>
                        </div>
                    </div>
                </div>
                <div class="col-md-8">
                    <div class="card">
                        <div class="card-header">
                            <strong>数据流水记录</strong>
                            <small>Operation Finance Flow Log</small>
                        </div>
                        <div class="card-block">
                            <table class="table table-striped table-responsive-sm table-sm flow-list">
                                <thead>
                                <tr>
                                    <th>业务编号</th>
                                    <th>业务日期</th>
                                    <th>业务类型</th>
                                    <th>所属基地</th>
                                    <th>真实姓名</th>
                                    <th>渠道来源</th>
                                    <th>流水值</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody></tbody>
                            </table>
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-header">
                            <strong>客户体验记录</strong>
                            <small>Operation Finance Access Log</small>
                        </div>
                        <div class="card-block">
                            <table class="table table-striped table-responsive-sm table-sm business-list">
                                <thead>
                                <tr>
                                    <th>业务编号</th>
                                    <th>业务日期</th>
                                    <th>业务类型</th>
                                    <th>所属基地</th>
                                    <th>真实姓名</th>
                                    <th>渠道来源</th>
                                    <th>体验数</th>
                                    <th>成交数</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody></tbody>
                            </table>
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-header">
                            <strong>确认收入记录</strong>
                            <small>Operation Finance Income Log</small>
                        </div>
                        <div class="card-block">
                            <table class="table table-striped table-responsive-sm table-sm income-list">
                                <thead>
                                <tr>
                                    <th>业务编号</th>
                                    <th>业务日期</th>
                                    <th>业务类型</th>
                                    <th>所属基地</th>
                                    <th>真实姓名</th>
                                    <th>收入类型</th>
                                    <th>课单价</th>
                                    <th>确认收入</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody></tbody>
                            </table>
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-header">
                            <strong>上课签到记录</strong>
                            <small>Operation Finance Attendance Log</small>
                        </div>
                        <div class="card-block">
                            <table class="table table-striped table-responsive-sm table-sm attendance-list">
                                <thead>
                                <tr>
                                    <th>业务编号</th>
                                    <th>业务日期</th>
                                    <th>业务类型</th>
                                    <th>所属基地</th>
                                    <th>真实姓名</th>
                                    <th>收入类型</th>
                                    <th>在册数</th>
                                    <th>到课数</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody></tbody>
                            </table>
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-header">
                            <strong>闲忙时段记录</strong>
                            <small>Operation Finance Times Log</small>
                        </div>
                        <div class="card-block">
                            <table class="table table-striped table-responsive-sm table-sm times-list">
                                <thead>
                                <tr>
                                    <th>业务编号</th>
                                    <th>业务日期</th>
                                    <th>业务类型</th>
                                    <th>所属基地</th>
                                    <th>真实姓名</th>
                                    <th>闲时总数</th>
                                    <th>闲时占用</th>
                                    <th>忙时总数</th>
                                    <th>忙时占用</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody></tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</layout:override>

<c:import url="../Shared/GeneralLayout.jsp">
    <c:param name="menu" value="finance"/>
    <c:param name="subMenu" value="add"/>
</c:import>
