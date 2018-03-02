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
    <script async type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/class/test.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="modal fade" id="test_detail" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-primary modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <table class="table table-striped table-sm class-list">
                        <thead>
                        <tr>
                            <th>##</th>
                            <th>学员姓名</th>
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
                                        <input type="text" class="form-control" placeholder="班级名称" name="className">
                                    </div>
                                    <div class="col-md-9">
                                        <button type="button" class="btn btn-primary search-test">
                                            <i class="fa fa-search"></i> 检 索
                                        </button>
                                        <a href="#" class="btn btn-primary pull-right" title="评测添加">
                                            <i class="fa fa-plus"></i> 评测添加
                                        </a>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer text-right"></div>
                        <div class="card-block">
                            <table class="table table-striped table-sm class-list">
                                <thead>
                                <tr>
                                    <th>##</th>
                                    <th>班级名称</th>
                                    <th>负责教练</th>
                                    <th>评测标题</th>
                                    <th>评测人数</th>
                                    <th>评测时间</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>1</td>
                                    <td>青少年篮球兴趣班</td>
                                    <td>刘少华</td>
                                    <td>阶段评测一</td>
                                    <td>10人</td>
                                    <td>2018-01-11 12:11:11</td>
                                    <td>
                                        <a href="javascript:;" class="btn btn-sm btn-primary test-query" title="查看详情"
                                           data-target="#test_detail" data-toggle="modal">
                                            <i class="fa fa-list"></i> 评测详情
                                        </a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>2</td>
                                    <td>青少年篮球兴趣班</td>
                                    <td>刘少华</td>
                                    <td>阶段评测二</td>
                                    <td>12人</td>
                                    <td>2018-02-11 12:11:11</td>
                                    <td>
                                        <a href="javascript:;" class="btn btn-sm btn-primary test-query" title="查看详情"
                                           data-target="#test_detail" data-toggle="modal">
                                            <i class="fa fa-list"></i> 评测详情
                                        </a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>3</td>
                                    <td>青少年篮球兴趣班</td>
                                    <td>刘少华</td>
                                    <td>阶段评测二</td>
                                    <td>19人</td>
                                    <td>2018-03-11 12:11:11</td>
                                    <td>
                                        <a href="javascript:;" class="btn btn-sm btn-primary test-query" title="查看详情"
                                           data-target="#test_detail" data-toggle="modal">
                                            <i class="fa fa-list"></i> 评测详情
                                        </a>
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
