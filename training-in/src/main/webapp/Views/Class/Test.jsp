<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.in.layout.Blocks" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <style type="text/css">
        .class-list th {
            padding: 0.75rem;
        }
        .class-list td {
            padding: 0.3rem 0.75rem;
        }
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/class/test.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="modal fade" id="test_edit" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-primary" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <form id="test_edit_form" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                        <input type="hidden" id="test_edit_id" name="id" value="">
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label">
                                <span class="text-danger">*</span> 评测标题
                            </label>
                            <div class="col-md-9">
                                <input type="text" class="form-control" id="test_edit_testName" name="testName" placeholder="请输入评测标题"
                                       data-val="true" data-val-required="评测标题不能为空"
                                       data-val-length-max="10" data-val-length-min="2" data-val-length="评测标题必须包含 2~10 个字符">
                                <div data-valmsg-for="testName" data-valmsg-replace="true"></div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label">
                                <span class="text-danger">*</span> 评测班级
                            </label>
                            <div class="col-md-9">
                                <select class="form-control" id="test_edit_classId" name="classId"
                                        data-val="true" data-val-required="请选择评测班级">
                                    <option value="">请选择评测班级</option>
                                    <c:forEach var="cls" items="${orgClassList}" varStatus="loop">
                                        <option value="${cls.id}">${cls.className}</option>
                                    </c:forEach>
                                </select>
                                <div data-valmsg-for="classId" data-valmsg-replace="true"></div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label">
                                <span class="text-danger">*</span> 评测班次
                            </label>
                            <div class="col-md-9">
                                <select class="form-control" id="test_edit_classDate" name="classDate"
                                        data-val="true" data-val-required="请选择评测班次">
                                    <option value="">请选择评测班次</option>
                                </select>
                                <div data-valmsg-for="classDate" data-valmsg-replace="true"></div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label">评测描述</label>
                            <div class="col-md-9">
                                <textarea rows="9" class="form-control" id="test_edit_testNote" name="testNote" placeholder="请输入评测描述"></textarea>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-sm btn-secondary" data-dismiss="modal">
                        <i class="fa fa-close"></i> 取 消
                    </button>
                    <button type="button" class="btn btn-sm btn-primary save-test">
                        <i class="fa fa-check"></i> 保 存
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="test_start" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-primary" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <form id="test_start_form" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                        <input type="hidden" id="test_start_id" name="testId" value="">
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label">
                                <span class="text-danger">*</span> 评测学员
                            </label>
                            <div class="col-md-9">
                                <select class="form-control" id="test_start_studentId" name="studentId">
                                    <option value="">请选择学员</option>
                                </select>
                            </div>
                        </div>
                        <div class="test-start-skills"></div>
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label">评价描述</label>
                            <div class="col-md-9">
                                <textarea rows="9" class="form-control" id="test_start_testNote" name="testRemark" placeholder="请输入评测描述"></textarea>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-sm btn-secondary" data-dismiss="modal">
                        <i class="fa fa-close"></i> 关 闭
                    </button>
                    <button type="button" class="btn btn-sm btn-primary save-test-start">
                        <i class="fa fa-check"></i> 保 存
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="test_detail" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-primary modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-body class-score-list">
                    <table class="table table-striped table-sm class-list">
                        <thead>
                        <tr>
                            <th>##</th>
                            <th>姓名</th>
                            <th>运球</th>
                            <th>传球</th>
                            <th>弹跳</th>
                            <th>投篮</th>
                            <th>总分</th>
                            <th>评语</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>1</td>
                            <td>张小斐</td>
                            <td class="text-warning">5/10</td>
                            <td class="text-danger">3/10</td>
                            <td class="text-success">8/10</td>
                            <td class="text-muted">6/10</td>
                            <td class="text-danger">10/40(差)</td>
                            <td>
                                <a href="javascript:;" class="btn btn-sm btn-primary test-comment" data-toggle="popover" data-trigger="hover"
                                   title="" data-content="评语评语评语评语，评语评语评语，评语评语评语评语评语。" data-original-title="评语">
                                    <i class="fa fa-commenting-o"></i>
                                </a>
                            </td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>张小斐</td>
                            <td class="text-warning">5/10</td>
                            <td class="text-danger">3/10</td>
                            <td class="text-success">8/10</td>
                            <td class="text-muted">6/10</td>
                            <td class="text-warning">22/40(中)</td>
                            <td>
                                <a href="javascript:;" class="btn btn-sm btn-primary test-comment" data-toggle="popover" data-trigger="hover"
                                   title="" data-content="评语评语评语评语，评语评语评语，评语评语评语评语评语。" data-original-title="评语">
                                    <i class="fa fa-commenting-o"></i>
                                </a>
                            </td>
                        </tr>
                        <tr>
                            <td>3</td>
                            <td>张小斐</td>
                            <td class="text-warning">5/10</td>
                            <td class="text-danger">3/10</td>
                            <td class="text-success">8/10</td>
                            <td class="text-muted">6/10</td>
                            <td class="text-muted">22/40(良)</td>
                            <td>
                                <a href="javascript:;" class="btn btn-sm btn-primary test-comment" data-toggle="popover" data-trigger="hover"
                                   title="" data-content="评语评语评语评语，评语评语评语，评语评语评语评语评语。" data-original-title="评语">
                                    <i class="fa fa-commenting-o"></i>
                                </a>
                            </td>
                        </tr>
                        <tr>
                            <td>4</td>
                            <td>张小斐</td>
                            <td class="text-warning">5/10</td>
                            <td class="text-danger">3/10</td>
                            <td class="text-success">8/10</td>
                            <td class="text-muted">6/10</td>
                            <td class="text-success">22/40(优)</td>
                            <td>
                                <a href="javascript:;" class="btn btn-sm btn-primary test-comment" data-toggle="popover" data-trigger="hover"
                                   title="" data-content="评语评语评语评语，评语评语评语，评语评语评语评语评语。" data-original-title="评语">
                                    <i class="fa fa-commenting-o"></i>
                                </a>
                            </td>
                        </tr>
                        </tbody>
                    </table>
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
                            <strong>班级评测</strong>
                            <small>Examination</small>
                        </div>
                        <div class="card-block">
                            <form id="class_test_form" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                                <div class="form-group row">
                                    <div class="col-md-3">
                                        <select class="form-control" id="class_test_classId" name="classId">
                                            <option value="">请选择评测班级</option>
                                            <c:forEach var="cls" items="${orgClassList}" varStatus="loop">
                                                <option value="${cls.id}">${cls.className}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-9">
                                        <button type="button" class="btn btn-primary search-test">
                                            <i class="fa fa-search"></i> 检 索
                                        </button>
                                        <a href="javascript:;" class="btn btn-primary pull-right test-add" title="评测添加"
                                           data-target="#test_edit" data-toggle="modal">
                                            <i class="fa fa-plus"></i> 评测添加
                                        </a>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer text-right"></div>
                        <div class="card-block">
                            <table class="table table-striped table-sm class-test-list">
                                <thead>
                                <tr>
                                    <th>##</th>
                                    <th>评测标题</th>
                                    <th>评测班级</th>
                                    <th>评测教练</th>
                                    <th>评测人数</th>
                                    <th>评测班次</th>
                                    <th>创建时间</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="test" items="${orgClassTestList}" varStatus="loop">
                                    <tr data-id="${test.orgClassTest.id}" data-classId="${test.orgClass.id}">
                                        <td>${loop.index + 1}</td>
                                        <td>${test.orgClassTest.testName}</td>
                                        <td>${test.orgClass.className}</td>
                                        <td>${test.orgCoaches.realName}</td>
                                        <td>10人</td>
                                        <td>${test.orgClassSchedule.classDate}</td>
                                        <td>${test.orgClassTest.createTime}</td>
                                        <td>
                                            <a href="javascript:;" class="btn btn-sm btn-info test-start" title="评测"
                                               data-target="#test_start" data-toggle="modal">
                                                <i class="fa fa-pencil"></i> 评测
                                            </a>
                                            <a href="javascript:;" class="btn btn-sm btn-primary test-query" title="查看详情"
                                               data-target="#test_detail" data-toggle="modal">
                                                <i class="fa fa-trophy"></i> 结果
                                            </a>
                                            <a href="javascript:;" class="btn btn-sm btn-danger test-query" title="删除">
                                                <i class="fa fa-remove"></i> 删除
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <div>
                                <%@ include file="../Shared/Pagination.jsp" %>
                            </div>
                        </div>
                    </div>
                </div>
                <!--/.col-->
            </div>
            <!--/.row-->
        </div>

    </div>
</layout:override>

<c:import url="../Shared/GeneralLayout.jsp">
    <c:param name="menu" value="class"/>
    <c:param name="subMenu" value="test"/>
</c:import>
