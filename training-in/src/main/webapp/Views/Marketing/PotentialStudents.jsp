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
            data-main="Content/js/app/class/list.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="modal fade" id="student_preview_go" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-primary" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <form id="preview_form" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label" for="">
                                <span class="text-danger">*</span> 试听班级
                            </label>
                            <div class="col-md-9">
                                <select class="form-control" name="status">
                                    <option value="1">班级A</option>
                                    <option value="2">班级B</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label" for="">
                                <span class="text-danger">*</span> 试听日期
                            </label>
                            <div class="col-md-9">
                                <select class="form-control" name="status">
                                    <option value="1">2018-12-12 09:00-10:00</option>
                                    <option value="1">2018-12-12 09:00-10:00</option>
                                </select>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-sm btn-secondary" data-dismiss="modal">
                        <i class="fa fa-close"></i> 取 消
                    </button>
                    <button type="button" class="btn btn-sm btn-primary save-class-status">
                        <i class="fa fa-check"></i> 确 认
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="student_change_go" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-primary" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <form id="change_form" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label" for="">
                                <span class="text-danger">*</span> 分配班级
                            </label>
                            <div class="col-md-9">
                                <select class="form-control" name="status">
                                    <option value="1">班级A</option>
                                    <option value="2">班级B</option>
                                </select>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-sm btn-secondary" data-dismiss="modal">
                        <i class="fa fa-close"></i> 取 消
                    </button>
                    <button type="button" class="btn btn-sm btn-primary save-class-status">
                        <i class="fa fa-check"></i> 确 认
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
                            <strong>潜在学员</strong>
                            <small>Potential Students</small>
                        </div>
                        <div class="card-block">
                            <form id="students_form" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                                <div class="form-group row">
                                    <div class="col-md-3">
                                        <input type="text" class="form-control" placeholder="学员姓名" name="studentName">
                                    </div>
                                    <div class="col-md-2">
                                        <select class="form-control" name="status">
                                            <option value="0">营销阶段</option>
                                            <option value="1">初始到访</option>
                                            <option value="2">电话回访</option>
                                            <option value="2">预约试听</option>
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        <select class="form-control" name="status">
                                            <option value="0">全部销售</option>
                                        </select>
                                    </div>
                                    <div class="col-md-5">
                                        <button type="button" class="btn btn-primary search-class">
                                            <i class="fa fa-search"></i> 检 索
                                        </button>
                                        <a href="/admin/marketing/potential/students/add" class="btn btn-primary pull-right" title="学员添加">
                                            <i class="fa fa-plus"></i> 学员添加
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
                                    <th>学员姓名</th>
                                    <th>所属场馆</th>
                                    <th>手机号码</th>
                                    <th>出生日期</th>
                                    <th>性别</th>
                                    <th>营销阶段</th>
                                    <th>推销人</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                    <tr data-id="学员ID">
                                        <td>1</td>
                                        <td>
                                            <a href="/admin/marketing/potential/students/info">张三</a>
                                        </td>
                                        <td>三三场馆</td>
                                        <td>15801303144</td>
                                        <td>2017-12-12</td>
                                        <td>男</td>
                                        <td>初始到访</td>
                                        <td>詹思思</td>
                                        <td>
                                            <a href="javascript:;" class="btn btn-sm btn-primary" title="预约试听">
                                                <i class="fa fa-location-arrow"></i> 预约试听
                                            </a>
                                            <a href="javascript:;" class="btn btn-sm btn-primary" title="转成正式学员">
                                                <i class="fa fa-graduation-cap"></i> 转成学员
                                            </a>
                                        </td>
                                    </tr>
                                    <tr data-id="学员ID">
                                        <td>2</td>
                                        <td>张三</td>
                                        <td>三三场馆</td>
                                        <td>15801303144</td>
                                        <td>2017-12-12</td>
                                        <td>男</td>
                                        <td>已试听，已回访</td>
                                        <td>詹思思</td>
                                        <td>
                                            <a href="javascript:;" class="btn btn-sm btn-primary" title="预约试听">
                                                <i class="fa fa-location-arrow"></i> 预约试听
                                            </a>
                                            <a href="javascript:;" class="btn btn-sm btn-primary" title="转成正式学员">
                                                <i class="fa fa-graduation-cap"></i> 转成学员
                                            </a>
                                        </td>
                                    </tr>
                                    <tr data-id="学员ID">
                                        <td>3</td>
                                        <td>张三</td>
                                        <td>三三场馆</td>
                                        <td>15801303144</td>
                                        <td>2017-12-12</td>
                                        <td>男</td>
                                        <td>已试听，已回访</td>
                                        <td>詹思思</td>
                                        <td>
                                            <a href="javascript:;" class="btn btn-sm btn-primary" title="预约试听">
                                                <i class="fa fa-location-arrow"></i> 预约试听
                                            </a>
                                            <a href="javascript:;" class="btn btn-sm btn-primary" title="转成正式学员">
                                                <i class="fa fa-graduation-cap"></i> 转成学员
                                            </a>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            <div></div>
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
    <c:param name="menu" value="marketing"/>
    <c:param name="subMenu" value="potentialStu"/>
</c:import>
