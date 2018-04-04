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
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/class/edit.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="container-fluid">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <strong>班级设置</strong>
                            <small>Edit</small>
                        </div>
                        <div class="card-block">
                            <form id="class_form" method="post" class="form-horizontal row" novalidate onsubmit="return false;">
                                <input type="hidden" id="info_class_id" name="id" value="${orgClass.id}">
                                <div class="col-md-6">
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label" for="info_class_name">
                                            <span class="text-danger">*</span> 班级名称
                                        </label>
                                        <div class="col-md-9">
                                            <input type="text" class="form-control" id="info_class_name" name="className" placeholder="请输入班级名称"
                                                   value="${orgClass.className}"
                                                   data-val="true" data-val-required="班级名称不能为空"
                                                   data-val-length-max="20" data-val-length-min="2" data-val-length="班级名称必须包含 2~20 个字符">
                                            <div data-valmsg-for="className" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label" for="info_venue_id">
                                            <span class="text-danger">*</span> 所属场馆
                                        </label>
                                        <div class="col-md-9">
                                            <select class="form-control" id="info_venue_id" name="venueId" data-val="true" data-val-required="请选择所属场馆">
                                                <option value="">选择场馆</option>
                                                <c:forEach var="venue" items="${orgVenuesList}">
                                                    <option value="${venue.id}"
                                                            <c:if test="${orgClass.venueId == venue.id}">selected</c:if> >${venue.venueName}</option>
                                                </c:forEach>
                                            </select>
                                            <div data-valmsg-for="venueId" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label" for="info_course_id">
                                            <span class="text-danger">*</span> 授课内容
                                        </label>
                                        <div class="col-md-9">
                                            <select class="form-control" id="info_course_id" name="courseId"
                                                    data-val="true" data-val-required="请选择授课内容">
                                                <option value="">选择授课内容</option>
                                                <c:forEach var="course" items="${orgCoursesList}">
                                                    <option value="${course.id}"
                                                            <c:if test="${orgClass.courseId == course.id}">selected</c:if> >${course.courseName}</option>
                                                </c:forEach>
                                            </select>
                                            <div data-valmsg-for="courseId" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label" for="info_coach_id">
                                            <span class="text-danger">*</span> 负责教练
                                        </label>
                                        <div class="col-md-9">
                                            <select class="form-control" id="info_coach_id" name="coachId" data-val="true" data-val-required="请选择负责教练">
                                                <option value="">选择教练</option>
                                                <c:forEach var="coach" items="${orgCoachesList}">
                                                    <option value="${coach.id}"
                                                            <c:if test="${orgClass.coachId == coach.id}">selected</c:if> >${coach.realName}</option>
                                                </c:forEach>
                                            </select>
                                            <div data-valmsg-for="coachId" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label" for="info_class_price">
                                            <span class="text-danger">*</span> 班级价格
                                        </label>
                                        <div class="col-md-9">
                                            <input type="text" class="form-control" id="info_class_price" name="classPrice" placeholder="班级价格"
                                                   value="${orgClass.classPrice}"
                                                   data-val="true" data-val-required="班级价格不能为空"
                                                   data-val-regex-pattern="^(0)|([1-9][0-9]*)$" data-val-regex="班级价格格式不正确">
                                            <div data-valmsg-for="classPrice" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label" for="info_class_hours">
                                            <span class="text-danger">*</span> 签到扣课时
                                        </label>
                                        <div class="col-md-9">
                                            <input type="text" class="form-control" id="info_class_hours" name="classHours" placeholder="每次的签到扣除的课时数"
                                                   value="${orgClass.classHours}"
                                                   data-val="true" data-val-required="课时数不能为空"
                                                   data-val-regex-pattern="^(0)|([1-9][0-9]*)$" data-val-regex="课时数格式不正确">
                                            <div data-valmsg-for="classHours" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer">
                            <button type="button" class="btn btn-primary save-class">
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
    <c:param name="menu" value="class"/>
    <c:param name="subMenu" value="edit"/>
</c:import>
