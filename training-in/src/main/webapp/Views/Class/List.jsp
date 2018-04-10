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
    <div class="modal fade" id="class_status" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-primary modal-sm" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <p class="help-block">已开班：可以进行任意的重新排期</p>
                    <p class="help-block">上课中：仅可以删除排期，添加排期；不能更改排期方式。</p>
                    <p class="help-block">已开班：不能增删任何排期。</p>
                    <form id="class_status_form" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                        <input type="hidden" id="info_class_id" name="id" value="">
                        <div class="form-group row">
                            <div class="col-md-12 hidden-sm-down">
                                <div class="btn-toolbar" role="toolbar">
                                    <div class="btn-group" data-toggle="buttons">
                                        <c:forEach var="st" items="${ClassStatusEnum}" varStatus="loop">
                                            <label class="btn btn-outline-primary">
                                                <input type="radio" name="status" id="info_class_status${loop.index}" value="${st.code}"> ${st.desc}
                                            </label>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-sm btn-secondary" data-dismiss="modal">
                        <i class="fa fa-close"></i> 取 消
                    </button>
                    <button type="button" class="btn btn-sm btn-primary save-class-status">
                        <i class="fa fa-check"></i> 保 存
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
                            <strong>班级管理</strong>
                            <small>Class</small>
                        </div>
                        <div class="card-block">
                            <form id="class_form" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                                <div class="form-group row">
                                    <div class="col-md-3">
                                        <input type="text" class="form-control" placeholder="班级名称" name="className" value="${className}">
                                    </div>
                                    <div class="col-md-1">
                                        <select class="form-control" name="status">
                                            <option value="0">全部状态</option>
                                            <c:forEach var="st" items="${ClassStatusEnum}">
                                                <option value="${st.code}" <c:if test="${st.code == status}">selected</c:if> >${st.desc}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-8">
                                        <button type="button" class="btn btn-primary search-class">
                                            <i class="fa fa-search"></i> 检 索
                                        </button>
                                        <a href="/admin/class/add" class="btn btn-primary pull-right" title="班级添加">
                                            <i class="fa fa-plus"></i> 班级添加
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
                                    <th>所属场馆</th>
                                    <th>授课内容</th>
                                    <th>负责教练</th>
                                    <th>学员人数</th>
                                    <th>定价(元)</th>
                                    <th>状态</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="cls" items="${orgClassList}" varStatus="loop">
                                    <tr data-id="${cls.orgClass.id}" data-status="${cls.orgClass.status}">
                                        <td>${loop.index + 1}</td>
                                        <td>${cls.orgClass.className}</td>
                                        <td>${cls.orgVenues.venueName}</td>
                                        <td>${cls.orgCourses.courseName}</td>
                                        <td>${cls.orgCoaches.realName}</td>
                                        <td>
                                            <a href="/admin/students/list?classId=${cls.orgClass.id}">${cls.orgClassStudentsLength} 人</a>
                                        </td>
                                        <td>${cls.orgClass.classPrice}</td>
                                        <td>
                                            <c:forEach var="st" items="${ClassStatusEnum}">
                                                <c:if test="${st.code == cls.orgClass.status}">
                                                <a href="/admin/class/progress?classId=${cls.orgClass.id}" title="查看进度">
                                                    <c:if test="${st.code == 1}"><i class="fa fa-battery-empty"></i></c:if>
                                                    <c:if test="${st.code == 2}"><i class="fa fa-battery-half"></i></c:if>
                                                    <c:if test="${st.code == 3}"><i class="fa fa-battery-full"></i></c:if>
                                                    &nbsp;${st.desc}
                                                </a>
                                                </c:if>
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <a href="/admin/class/schedule?classId=${cls.orgClass.id}" class="btn btn-sm btn-warning class-schedule" title="班级排班">
                                                <i class="fa fa-calendar"></i> 排班
                                            </a>
                                            <a href="/admin/class/edit?classId=${cls.orgClass.id}" class="btn btn-sm btn-primary" title="重新编辑">
                                                <i class="fa fa-pencil"></i> 修改
                                            </a>
                                            <a href="javascript:;" class="btn btn-sm btn-danger class-refresh" title="切换状态"
                                                data-target="#class_status" data-toggle="modal">
                                                <i class="fa fa-refresh"></i> 切换
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
    <c:param name="subMenu" value="list"/>
</c:import>
