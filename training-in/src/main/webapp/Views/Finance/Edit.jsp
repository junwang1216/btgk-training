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
            data-main="Content/js/app/finance/edit.js?v=${static_resource_version}"></script>
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
                                <div class="offset-2 col-md-4">
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
                                            <span class="text-danger">*</span> 业务类型
                                        </label>
                                        <div class="col-md-9">
                                            <select class="form-control" name="businessType" data-val="true" data-val-required="请选择业务类型">
                                                <c:forEach var="business" items="${BusinessTypeEnumList}">
                                                    <option value="${business.code}" <c:if test="${orgFinance.businessType == business.code}">selected</c:if> >${business.desc}</option>
                                                </c:forEach>
                                            </select>
                                            <div data-valmsg-for="businessType" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label">
                                            <span class="text-danger">*</span> 所属基地
                                        </label>
                                        <div class="col-md-9">
                                            <select class="form-control" name="venueId" data-val="true" data-val-required="请选择所属基地">
                                                <c:forEach var="business" items="${orgFinanceVenuesList}">
                                                    <option value="${business.id}" <c:if test="${orgFinance.baseType == business.id}">selected</c:if> >${business.venueName}</option>
                                                </c:forEach>
                                            </select>
                                            <div data-valmsg-for="baseType" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label">
                                            <span class="text-danger">*</span> 业务员
                                        </label>
                                        <div class="col-md-9">
                                            <select class="form-control" name="userId" data-val="true" data-val-required="请选择业务员">
                                                <option value="0">请选择</option>
                                            </select>
                                            <div data-valmsg-for="realName" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label">
                                            <span class="text-danger">*</span> 渠道来源
                                        </label>
                                        <div class="col-md-9">
                                            <select class="form-control" name="channelType" data-val="true" data-val-required="请选择来源渠道">
                                                <c:forEach var="business" items="${BusinessChannelTypeEnumList}">
                                                    <option value="${business.code}" <c:if test="${orgFinance.channelType == business.code}">selected</c:if> >${business.desc}</option>
                                                </c:forEach>
                                            </select>
                                            <div data-valmsg-for="channelType" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label">
                                            <span class="text-danger">*</span> 流水情况
                                        </label>
                                        <div class="col-md-9">
                                            <input type="text" class="form-control" placeholder="完成值" name="pipelineValue"
                                                   value="${orgFinance.pipelineValue}"
                                                   data-val="true" data-val-required="完成值不能为空"
                                                   data-val-regex-pattern="^(0)|([1-9][0-9]*)$" data-val-regex="完成值格式不正确">
                                            <div data-valmsg-for="pipelineValue" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label">
                                            <span class="text-danger">*</span> 确认收入
                                        </label>
                                        <div class="col-md-9">
                                            <input type="text" class="form-control" placeholder="完成值" name="incomeValue"
                                                   value="${orgFinance.incomeValue}"
                                                   data-val="true" data-val-required="完成值不能为空"
                                                   data-val-regex-pattern="^(0)|([1-9][0-9]*)$" data-val-regex="完成值格式不正确">
                                            <div data-valmsg-for="incomeValue" data-valmsg-replace="true"></div>
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
                        <c:if test="${orgFinance.businessNo == null}">
                            <div class="card-block">
                                <table class="table table-striped table-sm business-list">
                                    <thead>
                                    <tr>
                                        <th>业务编号</th>
                                        <th>业务日期</th>
                                        <th>业务类型</th>
                                        <th>所属基地</th>
                                        <th>姓名</th>
                                        <th>来源渠道</th>
                                        <th>流水情况</th>
                                        <th>确认收入</th>
                                        <th>在册人数</th>
                                        <th>到课人数</th>
                                        <th>体验数</th>
                                        <th>成交数</th>
                                    </tr>
                                    </thead>
                                    <tbody></tbody>
                                </table>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
            <!--/.row-->
        </div>

    </div>
</layout:override>

<c:import url="../Shared/GeneralLayout.jsp">
    <c:param name="menu" value="finance"/>
    <c:param name="subMenu" value="edit"/>
</c:import>
