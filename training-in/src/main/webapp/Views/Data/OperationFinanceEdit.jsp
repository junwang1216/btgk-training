<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.in.layout.Blocks" %>
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
            data-main="Content/js/app/data/operationFinanceEdit.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="container-fluid">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <strong>运营财务录入</strong>
                            <small>Operation Finance Edit</small>
                        </div>
                        <div class="card-block">
                            <form id="business_form" method="post" class="form-horizontal row" novalidate onsubmit="return false;">
                                <input type="hidden" name="businessNo" value="${orgFinance.businessNo}">
                                <div class="col-md-6">
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label">
                                            <span class="text-danger">*</span> 业务日期
                                        </label>
                                        <div class="col-md-9">
                                            <input type="text" class="form-control datepicker" name="businessDate" placeholder="请输入业务日期"
                                                   value="${orgFinance.businessDate}"
                                                   data-val="true" data-val-required="业务日期不能为空">
                                            <div data-valmsg-for="businessDate" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label">
                                            <span class="text-danger">*</span> 所属基地
                                        </label>
                                        <div class="col-md-9">
                                            <select class="form-control" name="baseType" data-val="true" data-val-required="请选择所属基地">
                                                <c:forEach var="business" items="${orgFinanceBaseList}">
                                                    <option value="${business.enumValue}" <c:if test="${orgFinance.baseType == business.enumValue}">selected</c:if> >${business.enumNote}</option>
                                                </c:forEach>
                                            </select>
                                            <div data-valmsg-for="baseType" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label">
                                            <span class="text-danger">*</span> 业务类型
                                        </label>
                                        <div class="col-md-9">
                                            <select class="form-control" name="businessType" data-val="true" data-val-required="请选择业务类型">
                                                <c:forEach var="business" items="${orgFinanceBusinessList}">
                                                    <option value="${business.enumValue}" <c:if test="${orgFinance.businessType == business.enumValue}">selected</c:if> >${business.enumNote}</option>
                                                </c:forEach>
                                            </select>
                                            <div data-valmsg-for="businessType" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label">
                                            <span class="text-danger">*</span> 业务员姓名
                                        </label>
                                        <div class="col-md-9">
                                            <input type="text" class="form-control" placeholder="请输入业务员姓名" name="realName"
                                                   value="${orgFinance.realName}"
                                                   data-val="true" data-val-required="业务员姓名不能为空"
                                                   data-val-length-max="20" data-val-length-min="2" data-val-length="业务员姓名必须包含 2~20 个字符">
                                            <div data-valmsg-for="realName" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label">
                                            <span class="text-danger">*</span> 来源渠道
                                        </label>
                                        <div class="col-md-9">
                                            <select class="form-control" name="channelType" data-val="true" data-val-required="请选择来源渠道">
                                                <c:forEach var="business" items="${orgFinanceChannelList}">
                                                    <option value="${business.enumValue}" <c:if test="${orgFinance.channelType == business.enumValue}">selected</c:if> >${business.enumNote}</option>
                                                </c:forEach>
                                            </select>
                                            <div data-valmsg-for="channelType" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label">
                                            <span class="text-danger">*</span> 流水情况
                                        </label>
                                        <div class="col-md-3">
                                            <input type="text" class="form-control" placeholder="完成值" name="pipelineValue"
                                                   value="${orgFinance.pipelineValue}"
                                                   data-val="true" data-val-required="完成值不能为空"
                                                   data-val-regex-pattern="^(0)|([1-9][0-9]*)$" data-val-regex="完成值格式不正确">
                                            <div data-valmsg-for="pipelineValue" data-valmsg-replace="true"></div>
                                        </div>
                                        <div class="col-md-3">
                                            <input type="text" class="form-control" placeholder="目标值" name="pipelineTarget"
                                                   value="${orgFinance.pipelineTarget}"
                                                   data-val="true" data-val-required="目标值不能为空"
                                                   data-val-regex-pattern="^(0)|([1-9][0-9]*)$" data-val-regex="目标值格式不正确">
                                            <div data-valmsg-for="pipelineTarget" data-valmsg-replace="true"></div>
                                        </div>
                                        <div class="col-md-3">
                                            <input type="text" class="form-control" placeholder="挑战值" name="pipelineChallenge"
                                                   value="${orgFinance.pipelineChallenge}"
                                                   data-val="true" data-val-required="挑战值不能为空"
                                                   data-val-regex-pattern="^(0)|([1-9][0-9]*)$" data-val-regex="挑战值格式不正确">
                                            <div data-valmsg-for="pipelineChallenge" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label">
                                            <span class="text-danger">*</span> 确认收入
                                        </label>
                                        <div class="col-md-3">
                                            <input type="text" class="form-control" placeholder="完成值" name="incomeValue"
                                                   value="${orgFinance.incomeValue}"
                                                   data-val="true" data-val-required="完成值不能为空"
                                                   data-val-regex-pattern="^(0)|([1-9][0-9]*)$" data-val-regex="完成值格式不正确">
                                            <div data-valmsg-for="incomeValue" data-valmsg-replace="true"></div>
                                        </div>
                                        <div class="col-md-3">
                                            <input type="text" class="form-control" placeholder="目标值" name="incomeTarget"
                                                   value="${orgFinance.incomeTarget}"
                                                   data-val="true" data-val-required="目标值不能为空"
                                                   data-val-regex-pattern="^(0)|([1-9][0-9]*)$" data-val-regex="目标值格式不正确">
                                            <div data-valmsg-for="incomeTarget" data-valmsg-replace="true"></div>
                                        </div>
                                        <div class="col-md-3">
                                            <input type="text" class="form-control" placeholder="挑战值" name="incomeChallenge"
                                                   value="${orgFinance.incomeChallenge}"
                                                   data-val="true" data-val-required="挑战值不能为空"
                                                   data-val-regex-pattern="^(0)|([1-9][0-9]*)$" data-val-regex="挑战值格式不正确">
                                            <div data-valmsg-for="incomeChallenge" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <div class="block-training">
                                        <div class="form-group row">
                                            <label class="col-md-3 form-control-label">
                                                <span class="text-danger">*</span> 培训人数
                                            </label>
                                            <div class="col-md-4">
                                                <input type="text" class="form-control" placeholder="在册人数" name="registerCount"
                                                       value="${orgFinance.registerCount}"
                                                       data-val="true" data-val-required="在册人数不能为空"
                                                       data-val-regex-pattern="^(0)|([1-9][0-9]*)$" data-val-regex="在册人数格式不正确">
                                                <div data-valmsg-for="trainingCount" data-valmsg-replace="true"></div>
                                            </div>
                                            <div class="col-md-5">
                                                <input type="text" class="form-control" placeholder="到课人数" name="classCount"
                                                       value="${orgFinance.classCount}"
                                                       data-val="true" data-val-required="到课人数不能为空"
                                                       data-val-regex-pattern="^(0)|([1-9][0-9]*)$" data-val-regex="到课人数格式不正确">
                                                <div data-valmsg-for="classCount" data-valmsg-replace="true"></div>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-md-3 form-control-label">
                                                <span class="text-danger">*</span> 渠道人数
                                            </label>
                                            <div class="col-md-4">
                                                <input type="text" class="form-control" placeholder="体验数" name="accessCount"
                                                       value="${orgFinance.accessCount}"
                                                       data-val="true" data-val-required="体验数不能为空"
                                                       data-val-regex-pattern="^(0)|([1-9][0-9]*)$" data-val-regex="体验数格式不正确">
                                                <div data-valmsg-for="accessCount" data-valmsg-replace="true"></div>
                                            </div>
                                            <div class="col-md-5">
                                                <input type="text" class="form-control" placeholder="成交数" name="businessCount"
                                                       value="${orgFinance.businessCount}"
                                                       data-val="true" data-val-required="成交数不能为空"
                                                       data-val-regex-pattern="^(0)|([1-9][0-9]*)$" data-val-regex="成交数格式不正确">
                                                <div data-valmsg-for="businessCount" data-valmsg-replace="true"></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="block-venue">
                                        <div class="form-group row">
                                            <label class="col-md-3 form-control-label">
                                                <span class="text-danger">*</span> 闲时段数
                                            </label>
                                            <div class="col-md-4">
                                                <input type="text" class="form-control" placeholder="总数" name="nullTotalCount"
                                                       value="${orgFinance.nullTotalCount}"
                                                       data-val="true" data-val-required="总数不能为空"
                                                       data-val-regex-pattern="^(0)|([1-9][0-9]*)$" data-val-regex="总数格式不正确">
                                                <div data-valmsg-for="nullTotalCount" data-valmsg-replace="true"></div>
                                            </div>
                                            <div class="col-md-5">
                                                <input type="text" class="form-control" placeholder="占用数" name="nullCount"
                                                       value="${orgFinance.nullCount}"
                                                       data-val="true" data-val-required="占用数不能为空"
                                                       data-val-regex-pattern="^(0)|([1-9][0-9]*)$" data-val-regex="占用数格式不正确">
                                                <div data-valmsg-for="nullCount" data-valmsg-replace="true"></div>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-md-3 form-control-label">
                                                <span class="text-danger">*</span> 忙时段数
                                            </label>
                                            <div class="col-md-4">
                                                <input type="text" class="form-control" placeholder="总数" name="hotTotalCount"
                                                       value="${orgFinance.hotTotalCount}"
                                                       data-val="true" data-val-required="总数不能为空"
                                                       data-val-regex-pattern="^(0)|([1-9][0-9]*)$" data-val-regex="总数格式不正确">
                                                <div data-valmsg-for="hotTotalCount" data-valmsg-replace="true"></div>
                                            </div>
                                            <div class="col-md-5">
                                                <input type="text" class="form-control" placeholder="占用数" name="hotCount"
                                                       value="${orgFinance.hotCount}"
                                                       data-val="true" data-val-required="占用数不能为空"
                                                       data-val-regex-pattern="^(0)|([1-9][0-9]*)$" data-val-regex="占用数格式不正确">
                                                <div data-valmsg-for="hotCount" data-valmsg-replace="true"></div>
                                            </div>
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
            </div>
            <!--/.row-->
        </div>

    </div>
</layout:override>

<c:import url="../Shared/GeneralLayout.jsp">
    <c:param name="menu" value="data"/>
    <c:param name="subMenu" value="finance_edit"/>
</c:import>
