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
            data-main="Content/js/app/finance/edit.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="container-fluid">
        <div class="animated fadeIn">
            <form id="business_form" method="post" class="form-horizontal row" novalidate onsubmit="return false;">
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-header">
                            <strong>基本信息</strong>
                            <small>Operation Finance Edit</small>
                        </div>
                        <div class="card-block">
                            <input type="hidden" name="businessNo" value="${orgFinanceData.businessNo}">
                            <div class="form-group row">
                                <label class="col-md-3 form-control-label">
                                    <span class="text-danger">*</span> 业务日期
                                </label>
                                <div class="col-md-9">
                                    <input type="text" class="form-control datepicker" name="businessDate" placeholder="请输入业务日期"
                                           value="${orgFinanceData.businessDate}"
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
                                            <option value="${business.id}" <c:if test="${orgFinanceData.venueId == business.id}">selected</c:if> >${business.venueName}</option>
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
                                    <select class="form-control" name="userId" data-val="true" data-val-required="请选择业务员" data-default="${orgFinanceData.userId}">
                                        <option value="0">请选择</option>
                                    </select>
                                    <div data-valmsg-for="userId" data-valmsg-replace="true"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-header">
                            <strong>青训数据</strong>
                            <small>Operation Finance Edit</small>
                        </div>
                        <div class="card-block">
                            <div class="form-group row">
                                <label class="col-md-3 form-control-label">
                                    <span class="text-danger">*</span> 渠道来源
                                </label>
                                <div class="col-md-9">
                                    <select class="form-control" name="channelType" data-val="true" data-val-required="请选择来源渠道">
                                        <c:forEach var="business" items="${BusinessChannelTypeEnumList}">
                                            <option value="${business.code}" <c:if test="${orgFinanceData.channelType == business.code}">selected</c:if> >${business.desc}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-md-3 form-control-label">
                                    <span class="text-danger">*</span> 流水情况
                                </label>
                                <div class="col-md-9">
                                    <input type="text" class="form-control" placeholder="流水情况" name="pipelineValue" value="${orgFinanceData.pipelineValue}"
                                           data-val-regex-pattern="^[1-9][0-9]*$" data-val-regex="流水情况格式不正确">
                                    <div data-valmsg-for="pipelineValue" data-valmsg-replace="true"></div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-md-3 form-control-label">
                                    <span class="text-danger">*</span> 体验数
                                </label>
                                <div class="col-md-4">
                                    <input type="text" class="form-control" placeholder="体验数" name="accessCount"
                                           value="${orgFinanceData.accessCount}"
                                           data-val-regex-pattern="^(\s*)|([1-9][0-9]*)$" data-val-regex="体验数格式不正确">
                                    <div data-valmsg-for="accessCount" data-valmsg-replace="true"></div>
                                </div>
                                <div class="col-md-5">
                                    <input type="text" class="form-control" placeholder="成交数" name="businessCount"
                                           value="${orgFinanceData.businessCount}"
                                           data-val-regex-pattern="^(\s*)|([1-9][0-9]*)$" data-val-regex="成交数格式不正确"
                                           data-val-reqgroup-element="accessCount" data-val-reqgroup="体验和成交数必须同时有值">
                                    <div data-valmsg-for="businessCount" data-valmsg-replace="true"></div>
                                </div>
                            </div>
                            <hr>
                            <div class="form-group row">
                                <label class="col-md-3 form-control-label">
                                    <span class="text-danger">*</span> 收入类型
                                </label>
                                <div class="col-md-4">
                                    <select class="form-control" name="incomeType" data-val="true" data-val-required="请选择收入类型">
                                        <option>中教班</option>
                                        <option>外教班</option>
                                    </select>
                                </div>
                                <div class="col-md-5">
                                    <input type="text" class="form-control" placeholder="课单价" name="incomeValue" value="${orgFinanceData.incomeValue}"
                                           data-val-regex-pattern="^[1-9][0-9]*$" data-val-regex="确认收入格式不正确">
                                    <div data-valmsg-for="incomeValue" data-valmsg-replace="true"></div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-md-3 form-control-label">
                                    <span class="text-danger">*</span> 到课数
                                </label>
                                <div class="col-md-4">
                                    <input type="text" class="form-control" placeholder="在册数" name="accessCount"
                                           value="${orgFinanceData.accessCount}"
                                           data-val-regex-pattern="^(\s*)|([1-9][0-9]*)$" data-val-regex="体验数格式不正确">
                                    <div data-valmsg-for="accessCount" data-valmsg-replace="true"></div>
                                </div>
                                <div class="col-md-5">
                                    <input type="text" class="form-control" placeholder="到课数" name="businessCount"
                                           value="${orgFinanceData.businessCount}"
                                           data-val-regex-pattern="^(\s*)|([1-9][0-9]*)$" data-val-regex="成交数格式不正确"
                                           data-val-reqgroup-element="accessCount" data-val-reqgroup="体验和成交数必须同时有值">
                                    <div data-valmsg-for="businessCount" data-valmsg-replace="true"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-header">
                            <strong>场租数据</strong>
                            <small>Operation Finance Edit</small>
                        </div>
                        <div class="card-block">
                                <div class="form-group row">
                                    <label class="col-md-3 form-control-label">
                                        <span class="text-danger">*</span> 流水情况
                                    </label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" placeholder="流水情况" name="pipelineValue" value="${orgFinanceData.pipelineValue}"
                                               data-val-regex-pattern="^[1-9][0-9]*$" data-val-regex="流水情况格式不正确">
                                        <div data-valmsg-for="pipelineValue" data-valmsg-replace="true"></div>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-md-3 form-control-label">
                                        <span class="text-danger">*</span> 确认收入
                                    </label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" placeholder="确认收入" name="incomeValue" value="${orgFinanceData.incomeValue}"
                                               data-val-regex-pattern="^[1-9][0-9]*$" data-val-regex="确认收入格式不正确">
                                        <div data-valmsg-for="incomeValue" data-valmsg-replace="true"></div>
                                    </div>
                                </div>
                            <div class="form-group row">
                                <label class="col-md-3 form-control-label">
                                    <span class="text-danger">*</span> 闲时段数
                                </label>
                                <div class="col-md-4">
                                    <input type="text" class="form-control" placeholder="总数" name="nullTotalCount"
                                           value="${orgFinanceData.nullTotalCount}"
                                           data-val="true" data-val-required="总数不能为空"
                                           data-val-regex-pattern="^((\s*)|([1-9][0-9]*))$" data-val-regex="总数格式不正确">
                                    <div data-valmsg-for="nullTotalCount" data-valmsg-replace="true"></div>
                                </div>
                                <div class="col-md-5">
                                    <input type="text" class="form-control" placeholder="占用数" name="nullCount"
                                           value="${orgFinanceData.nullCount}"
                                           data-val="true" data-val-required="占用数不能为空"
                                           data-val-regex-pattern="^((\s*)|([1-9][0-9]*))$" data-val-regex="占用数格式不正确"
                                           data-val-reqgroup-element="nullTotalCount" data-val-reqgroup="总数和占用数必须同时有值">
                                    <div data-valmsg-for="nullCount" data-valmsg-replace="true"></div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-md-3 form-control-label">
                                    <span class="text-danger">*</span> 忙时段数
                                </label>
                                <div class="col-md-4">
                                    <input type="text" class="form-control" placeholder="总数" name="hotTotalCount"
                                           value="${orgFinanceData.hotTotalCount}"
                                           data-val="true" data-val-required="总数不能为空"
                                           data-val-regex-pattern="^((\s*)|([1-9][0-9]*))$" data-val-regex="总数格式不正确">
                                    <div data-valmsg-for="hotTotalCount" data-valmsg-replace="true"></div>
                                </div>
                                <div class="col-md-5">
                                    <input type="text" class="form-control" placeholder="占用数" name="hotCount"
                                           value="${orgFinanceData.hotCount}"
                                           data-val="true" data-val-required="占用数不能为空"
                                           data-val-regex-pattern="^((\s*)|([1-9][0-9]*))$" data-val-regex="占用数格式不正确"
                                           data-val-reqgroup-element="hotTotalCount" data-val-reqgroup="总数和占用数必须同时有值">
                                    <div data-valmsg-for="hotCount" data-valmsg-replace="true"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-block">
                            <table class="table table-striped table-sm business-list" style="display: block;">
                                <thead>
                                <tr>
                                    <th>业务编号</th>
                                    <th>业务日期</th>
                                    <th>业务类型</th>
                                    <th>所属基地</th>
                                    <th>姓名</th>
                                    <th>渠道/类型</th>
                                    <th>流水/收入</th>
                                    <th>体验/在册</th>
                                    <th>成交/到课</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>dwqdw121242werwr</td>
                                    <td>2018-06-09</td>
                                    <td>青训</td>
                                    <td>方庄基地</td>
                                    <td>张三</td>
                                    <td>公司</td>
                                    <td>1000元</td>
                                    <td>100</td>
                                    <td>90</td>
                                    <td>删除</td>
                                </tr>
                                <tr>
                                    <td>dwqdw121242werwr</td>
                                    <td>2018-06-09</td>
                                    <td>青训</td>
                                    <td>方庄基地</td>
                                    <td>张三</td>
                                    <td>中教班</td>
                                    <td>2200元</td>
                                    <td>23</td>
                                    <td>22</td>
                                    <td>删除</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="card-block">
                            <table class="table table-striped table-sm venue-list" style="display: block;">
                                <thead>
                                <tr>
                                    <th>业务编号</th>
                                    <th>业务日期</th>
                                    <th>业务类型</th>
                                    <th>所属基地</th>
                                    <th>姓名</th>
                                    <th>流水情况</th>
                                    <th>确认收入</th>
                                    <th>闲时占用</th>
                                    <th>闲时总数</th>
                                    <th>忙时占用</th>
                                    <th>忙时总数</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>dwqdw121242werwr</td>
                                    <td>2018-06-09</td>
                                    <td>场租</td>
                                    <td>方庄基地</td>
                                    <td>张三</td>
                                    <td>2200元</td>
                                    <td>2200元</td>
                                    <td>23</td>
                                    <td>23</td>
                                    <td>22</td>
                                    <td>22</td>
                                    <td>删除</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <!--/.row-->
                </div>
            </div>
        </div>
    </div>
</layout:override>

<c:import url="../Shared/GeneralLayout.jsp">
    <c:param name="menu" value="finance"/>
    <c:param name="subMenu" value="add"/>
</c:import>
