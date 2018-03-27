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
        .card-body.p-2 {
            box-shadow: 0 2px 2px 1px #cfd8dc;
            cursor: pointer;
        }
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script async type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/class/progress.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="modal fade" id="attendance_list" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-primary" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <input type="hidden" id="attendance_classId" name="inClassID">
                    <input type="hidden" id="attendance_date" name="inDate">
                    <input type="hidden" id="attendance_schedule" name="inScheduleId">
                    <input type="hidden" id="attendance_coach" name="inUserId">
                    <table class="table table-striped table-sm user-list">
                        <thead>
                        <tr>
                            <th>##</th>
                            <th>学员姓名</th>
                            <th>手机号码</th>
                            <th>性别</th>
                            <th>状态</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>1</td>
                            <td>郭德纲</td>
                            <td>15801303167</td>
                            <td>男</td>
                            <td class="text-muted">已上课</td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>何小飞</td>
                            <td>15801303167</td>
                            <td>男</td>
                            <td class="text-muted">已上课</td>
                        </tr>
                        <tr>
                            <td>3</td>
                            <td>张贺</td>
                            <td>15801303167</td>
                            <td>男</td>
                            <td class="text-danger">未上课</td>
                        </tr>
                        <tr>
                            <td>4</td>
                            <td>李晶晶</td>
                            <td>15801303167</td>
                            <td>女</td>
                            <td class="text-muted">已上课</td>
                        </tr>
                        <tr>
                            <td>5</td>
                            <td>王司马</td>
                            <td>15801303167</td>
                            <td>男</td>
                            <td>
                                <a href="javascript:;" class="btn btn-sm btn-primary">
                                    <i class="fa fa-paw"></i> 签到
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
                    <button type="button" class="btn btn-sm btn-primary close-class-date" style="display: none;">
                        <i class="fa fa-check"></i> 结 课
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
                            <strong>上课进度</strong>
                            <small>Progress</small>
                        </div>
                        <div class="card-block">
                            <form id="class_form" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                                <div class="form-group row">
                                    <div class="col-md-3">
                                        <input type="text" class="form-control" placeholder="班级名称" name="className" value="${orgClass.className}">
                                    </div>
                                    <div class="col-md-9">
                                        <button type="button" class="btn btn-primary search-class">
                                            <i class="fa fa-search"></i> 检 索
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer text-right"></div>
                        <div class="card-block">
                            <ul class="nav nav-tabs" role="tablist">
                                <li class="nav-item <c:if test="${status == null}">active</c:if> ">
                                    <a href="javascript:;" class="nav-link class-date-all">所有课时 <span class="badge badge-pill badge-danger">${orgClassScheduleAllList.size()}</span></a>
                                </li>
                                <li class="nav-item <c:if test="${status == 1}">active</c:if> ">
                                    <a href="javascript:;" class="nav-link class-date-will">未上课 <span class="badge badge-pill badge-danger">${orgClassScheduleStartList.size()}</span></a>
                                </li>
                                <li class="nav-item <c:if test="${status == 2}">active</c:if> ">
                                    <a href="javascript:;" class="nav-link class-date-finished">已上课 <span class="badge badge-pill badge-danger">${orgClassScheduleEndList.size()}</span></a>
                                </li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane active">
                                    <div class="row class-date-list">
                                        <c:if test="${status == null || status == 2}">
                                            <c:forEach var="schedule" items="${orgClassScheduleEndList}" varStatus="loop">
                                                <c:if test="${!loop.last}">
                                                    <div class="col-md-2">
                                                        <div class="card text-white bg-success class-date"
                                                             data-id="${schedule.classId}" data-date="${schedule.classDate}"
                                                             data-schedule="${schedule.id}" data-coach="${orgClass.coachId}" data-status="1"
                                                             data-toggle="modal" data-target="#attendance_list">
                                                            <div class="card-body text-center p-2">
                                                                <div class="m-0">${orgClass.className}</div>
                                                                <div class="h5 py-2 m-0">${schedule.classDate}</div>
                                                                <small class="m-0">已签到：11人 | 已结课</small>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:if>
                                                <c:if test="${loop.last}">
                                                    <div class="col-md-2">
                                                        <div class="card text-white bg-warning class-date"
                                                             data-id="${schedule.classId}" data-date="${schedule.classDate}"
                                                             data-schedule="${schedule.id}" data-coach="${orgClass.coachId}" data-status="1"
                                                             data-toggle="modal" data-target="#attendance_list">
                                                            <div class="card-body text-center p-2">
                                                                <div class="m-0">${orgClass.className}</div>
                                                                <div class="h5 py-2 m-0">${schedule.classDate}</div>
                                                                <small class="m-0">已签到：2人 | 已结课</small>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                        <c:if test="${status == null || status == 1}">
                                            <c:forEach var="schedule" items="${orgClassScheduleStartList}" varStatus="loop">
                                                <div class="col-md-2">
                                                    <div class="card text-white bg-danger class-date"
                                                         data-id="${schedule.classId}" data-date="${schedule.classDate}"
                                                         data-schedule="${schedule.id}" data-coach="${orgClass.coachId}" data-status="2"
                                                         data-toggle="modal" data-target="#attendance_list">
                                                        <div class="card-body text-center p-2">
                                                            <div class="m-0">${orgClass.className}</div>
                                                            <div class="h5 py-2 m-0">${schedule.classDate}</div>
                                                            <small class="m-0">预估签到：11人 | 未上课</small>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </c:if>
                                    </div>
                                </div>
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
    <c:param name="subMenu" value="progress"/>
</c:import>
