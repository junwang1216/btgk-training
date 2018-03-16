<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.in.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <style type="text/css">
        .form-control-label {
            text-align: right;
            font-weight: bold;
        }
        .course-list th {
            padding: 0.75rem;
        }
        .course-list td {
            padding: 0.3rem 0.75rem;
        }
        .course-list .course-note p {
            text-overflow: ellipsis;
            white-space: nowrap;
            overflow: hidden;
            width: 30rem;
            margin-bottom: 0;
            cursor: pointer;
        }
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script async type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/venue/course.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="modal fade" id="course_edit" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-primary" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <form id="course_edit_form" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                        <input type="hidden" id="course_edit_id" name="id" value="">
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label">
                                <span class="text-danger">*</span> 运动项目
                            </label>
                            <div class="col-md-9">
                                <select class="form-control" id="course_edit_sportId" name="sportId"
                                        data-val="true" data-val-required="请选择运动项目">
                                    <option value="">请选择项目</option>
                                    <c:forEach var="sport" items="${orgSportsList}" varStatus="loop">
                                        <option value="${sport.id}">${sport.sportName}</option>
                                    </c:forEach>
                                </select>
                                <div data-valmsg-for="sportId" data-valmsg-replace="true"></div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label" for="course_edit_courseName">
                                <span class="text-danger">*</span> 课程标题
                            </label>
                            <div class="col-md-9">
                                <input type="text" class="form-control" id="course_edit_courseName" name="courseName" placeholder="请输入课程标题"
                                       data-val="true" data-val-required="课程标题不能为空"
                                       data-val-length-max="10" data-val-length-min="2" data-val-length="课程标题必须包含 2~10 个字符">
                                <div data-valmsg-for="courseName" data-valmsg-replace="true"></div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label" for="course_edit_courseNote">
                                <span class="text-danger">*</span> 课程内容
                            </label>
                            <div class="col-md-9">
                                <textarea rows="9" class="form-control" id="course_edit_courseNote" name="courseNote" placeholder="请输入课程内容"
                                          data-val="true" data-val-required="课程标题不能为空"
                                          data-val-length-max="500" data-val-length-min="10" data-val-length="课程标题必须包含 10~500 个字符"></textarea>
                                <div data-valmsg-for="courseNote" data-valmsg-replace="true"></div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-sm btn-secondary" data-dismiss="modal">
                        <i class="fa fa-close"></i> 取 消
                    </button>
                    <button type="button" class="btn btn-sm btn-primary save-course">
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
                            <strong>课程管理</strong>
                            <small>Course</small>
                        </div>
                        <div class="card-block">
                            <form id="course_form" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                                <div class="form-group row">
                                    <div class="col-md-3">
                                        <input type="text" class="form-control" name="courseName" placeholder="课程标题"
                                               value="${courseName}">
                                    </div>
                                    <div class="col-md-2">
                                        <select class="form-control" name="sportId">
                                            <option value="0">运动项目</option>
                                            <c:forEach var="sport" items="${orgSportsList}" varStatus="loop">
                                                <option value="${sport.id}" <c:if test="${sport.id == sportId}">selected</c:if> >${sport.sportName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-7">
                                        <button type="button" class="btn btn-primary search-course">
                                            <i class="fa fa-search"></i> 检 索
                                        </button>
                                        <a href="javascript:;" class="btn btn-primary pull-right add-course" title="课程添加"
                                           data-target="#course_edit" data-toggle="modal">
                                            <i class="fa fa-plus"></i> 课程添加
                                        </a>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer text-right"></div>
                        <div class="card-block">
                            <table class="table table-striped table-sm course-list">
                                <thead>
                                <tr>
                                    <th>##</th>
                                    <th>课程标题</th>
                                    <th>运动项目</th>
                                    <th>课程描述</th>
                                    <th>状态</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="course" items="${orgCoursesList}" varStatus="loop">
                                    <tr data-id="${course.id}">
                                        <td>${loop.index + 1}</td>
                                        <td>${course.courseName}</td>
                                        <td>
                                            <c:forEach var="sport" items="${orgSportsList}">
                                                <c:if test="${course.sportId == sport.id}">${sport.sportName}</c:if>
                                            </c:forEach>
                                        </td>
                                        <td class="course-note">
                                            <p data-toggle="popover" data-trigger="hover" title=""
                                               data-content="${course.courseNote}" data-original-title="课程描述">${course.courseNote}</p>
                                        </td>
                                        <td>
                                            <c:if test="${course.status == 1}">
                                                <span class="badge badge-success">允许授课</span>
                                            </c:if>
                                            <c:if test="${course.status == 2}">
                                                <span class="badge badge-danger">不可授课</span>
                                            </c:if>
                                        </td>
                                        <td>
                                            <button type="button" class="btn btn-sm btn-primary edit-course" title="课程编辑"
                                                    data-target="#course_edit" data-toggle="modal">
                                                <i class="fa fa-pencil"></i> 修改
                                            </button>
                                            <c:if test="${course.status == 1}">
                                                <button type="button" class="btn btn-sm btn-danger course-close" title="课程锁定">
                                                    <i class="fa fa-lock"></i> 锁定
                                                </button>
                                            </c:if>
                                            <c:if test="${course.status == 2}">
                                                <button type="button" class="btn btn-sm btn-warning course-open" title="课程解锁">
                                                    <i class="fa fa-unlock"></i> 解锁
                                                </button>
                                            </c:if>
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
    <c:param name="menu" value="venue"/>
    <c:param name="subMenu" value="course"/>
</c:import>
