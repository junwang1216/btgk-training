<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.in.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <link href="Content/bower_components/jquery-timepicker-wvega/jquery.timepicker.css?v=${static_resource_version}" rel="stylesheet">
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
            data-main="Content/js/app/class/schedule.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="container-fluid">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <strong>排班设置</strong>
                            <small>Schedule</small>
                        </div>
                        <div class="card-block">
                            <form id="class_schedule_form" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                                <input type="hidden" id="class_schedule_id" name="classId" value="${classId}">
                                <div class="form-group row">
                                    <label class="col-md-2 form-control-label">
                                        <span class="text-danger">*</span> 排课方式
                                    </label>
                                    <div class="col-md-10">
                                        <label class="radio-inline" for="class_schedule1">
                                            <input type="radio" id="class_schedule1" name="classSchedule" value="week"
                                                    <c:if test="${orgClassSchedule.classSchedule == 'week'}"> checked</c:if>
                                                    <c:if test="${orgClassWorking || orgClassEnd}"> disabled</c:if> > 每周课次
                                        </label>
                                        <label class="radio-inline" for="class_schedule2">
                                            <input type="radio" id="class_schedule2" name="classSchedule" value="date"
                                                   <c:if test="${orgClassSchedule.classSchedule == 'date'}"> checked</c:if>
                                                   <c:if test="${orgClassWorking || orgClassEnd}"> disabled</c:if> > 自定义课次
                                        </label>
                                    </div>
                                </div>
                                <div class="class-week-list" style="${orgClassSchedule.classWeekStyle}">
                                    <div class="form-group row">
                                        <label class="col-md-2 form-control-label">
                                            <span class="text-danger">*</span> 上课周期
                                        </label>
                                        <div class="col-md-3">
                                            <input type="text" class="form-control datepicker" placeholder="开始日期" id="class_schedule_startDate"
                                                   name="startDate" value="${orgClassSchedule.startDate}" <c:if test="${orgClassWorking || orgClassEnd}">disabled</c:if> >
                                        </div>
                                        <div class="col-md-3">
                                            <input type="text" class="form-control datepicker" placeholder="结束日期" id="class_schedule_endDate"
                                                   name="endDate" value="${orgClassSchedule.endDate}" <c:if test="${orgClassWorking || orgClassEnd}">disabled</c:if> >
                                        </div>
                                    </div>
                                    <c:if test="${orgClassSchedule.classSchedule == 'week'}">
                                        <c:forEach var="schedule" items="${orgClassScheduleList}" varStatus="loop">
                                            <div class="form-group row schedule-item">
                                                <input type="hidden" name="id" value="${schedule.id}" disabled>
                                                <div class="offset-2 col-md-2">
                                                    <select class="form-control" name="classWeek" <c:if test="${orgClassWorking || orgClassEnd}">disabled</c:if> >
                                                        <option value="1" <c:if test="${schedule.classWeek == '1'}">selected</c:if> >周一</option>
                                                        <option value="2" <c:if test="${schedule.classWeek == '2'}">selected</c:if> >周二</option>
                                                        <option value="3" <c:if test="${schedule.classWeek == '3'}">selected</c:if> >周三</option>
                                                        <option value="4" <c:if test="${schedule.classWeek == '4'}">selected</c:if> >周四</option>
                                                        <option value="5" <c:if test="${schedule.classWeek == '5'}">selected</c:if> >周五</option>
                                                        <option value="6" <c:if test="${schedule.classWeek == '6'}">selected</c:if> >周六</option>
                                                        <option value="7" <c:if test="${schedule.classWeek == '7'}">selected</c:if> >周日</option>
                                                    </select>
                                                </div>
                                                <div class="col-md-2">
                                                    <input type="text" class="form-control timepicker" placeholder="开始时间" name="startTime"
                                                           value="${schedule.startTime}" <c:if test="${orgClassWorking || orgClassEnd}">disabled</c:if> >
                                                </div>
                                                <div class="col-md-2">
                                                    <input type="text" class="form-control timepicker" placeholder="结束时间" name="endTime"
                                                           value="${schedule.endTime}" <c:if test="${orgClassWorking || orgClassEnd}">disabled</c:if> >
                                                </div>
                                                <div class="col-md-2">
                                                    <select class="form-control" name="coachId" <c:if test="${orgClassWorking || orgClassEnd}">disabled</c:if> >
                                                        <c:forEach var="coach" items="${orgCoachesList}">
                                                            <option value="${coach.id}"
                                                                    <c:if test="${schedule.coachId == coach.id}">selected</c:if> >${coach.realName}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="col-md-2">
                                                    <c:if test="${!orgClassEnd}">
                                                        <button type="button" class="btn btn-sm btn-danger class-week-del">
                                                            <i class="fa fa-trash"></i> 删除
                                                        </button>
                                                        <button type="button" class="btn btn-sm btn-primary class-week-add" <c:if test="${!loop.last}"> style="display: none;"</c:if> >
                                                            <i class="fa fa-plus"></i> 添加
                                                        </button>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${orgClassSchedule.classSchedule == 'date' || (orgClassSchedule.classSchedule == 'week' && orgClassScheduleList.size() == 0)}">
                                        <div class="form-group row">
                                            <div class="offset-2 col-md-2">
                                                <select class="form-control" name="classWeek">
                                                    <option value="1">周一</option>
                                                    <option value="2">周二</option>
                                                    <option value="3">周三</option>
                                                    <option value="4">周四</option>
                                                    <option value="5">周五</option>
                                                    <option value="6">周六</option>
                                                    <option value="7">周日</option>
                                                </select>
                                            </div>
                                            <div class="col-md-2">
                                                <input type="text" class="form-control timepicker" placeholder="开始时间" name="startTime">
                                            </div>
                                            <div class="col-md-2">
                                                <input type="text" class="form-control timepicker" placeholder="结束时间" name="endTime">
                                            </div>
                                            <div class="col-md-2">
                                                <select class="form-control" name="coachId">
                                                    <c:forEach var="coach" items="${orgCoachesList}">
                                                        <option value="${coach.id}"
                                                                <c:if test="${orgClass.coachId == coach.id}">selected</c:if> >${coach.realName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="col-md-2">
                                                <button type="button" class="btn btn-sm btn-danger class-week-del">
                                                    <i class="fa fa-trash"></i> 删除
                                                </button>
                                                <button type="button" class="btn btn-sm btn-primary class-week-add">
                                                    <i class="fa fa-plus"></i> 添加
                                                </button>
                                            </div>
                                        </div>
                                    </c:if>
                                </div>
                                <div class="class-date-list" style="${orgClassSchedule.classDateStyle}">
                                    <c:if test="${orgClassSchedule.classSchedule == 'date'}">
                                        <c:forEach var="schedule" items="${orgClassScheduleList}" varStatus="loop">
                                            <div class="form-group row schedule-item">
                                                <input type="hidden" name="id" value="${schedule.id}" disabled>
                                                <div class="offset-2 col-md-2">
                                                    <input type="text" class="form-control datepicker" placeholder="上课日期" name="classDate" value="${schedule.classDate}" <c:if test="${orgClassWorking || orgClassEnd}">disabled</c:if> >
                                                </div>
                                                <div class="col-md-2">
                                                    <input type="text" class="form-control timepicker" placeholder="开始时间" name="startTime" value="${schedule.startTime}" <c:if test="${orgClassWorking || orgClassEnd}">disabled</c:if> >
                                                </div>
                                                <div class="col-md-2">
                                                    <input type="text" class="form-control timepicker" placeholder="结束时间" name="endTime" value="${schedule.endTime}" <c:if test="${orgClassWorking || orgClassEnd}">disabled</c:if> >
                                                </div>
                                                <div class="col-md-2">
                                                    <select class="form-control" name="classDateCoachId" <c:if test="${orgClassWorking || orgClassEnd}">disabled</c:if> >
                                                        <option value="0">上课教练</option>
                                                        <c:forEach var="coach" items="${orgCoachesList}">
                                                            <option value="${coach.id}"
                                                                    <c:if test="${schedule.coachId == coach.id}">selected</c:if> >${coach.realName}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="col-md-2">
                                                    <c:if test="${!orgClassEnd}">
                                                        <button type="button" class="btn btn-sm btn-danger class-date-del">
                                                            <i class="fa fa-trash"></i> 删除
                                                        </button>
                                                        <button type="button" class="btn btn-sm btn-primary class-date-add" <c:if test="${!loop.last}"> style="display: none;"</c:if> >
                                                            <i class="fa fa-plus"></i> 添加
                                                        </button>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${orgClassSchedule.classSchedule == 'week' || (orgClassSchedule.classSchedule == 'date' && orgClassScheduleList.size() == 0)}">
                                        <div class="form-group row">
                                            <div class="offset-2 col-md-2">
                                                <input type="text" class="form-control datepicker" placeholder="上课日期" name="classDate">
                                            </div>
                                            <div class="col-md-2">
                                                <input type="text" class="form-control timepicker" placeholder="开始时间" name="startTime">
                                            </div>
                                            <div class="col-md-2">
                                                <input type="text" class="form-control timepicker" placeholder="结束时间" name="endTime">
                                            </div>
                                            <div class="col-md-2">
                                                <select class="form-control" name="classDateCoachId">
                                                    <option value="0">上课教练</option>
                                                    <c:forEach var="coach" items="${orgCoachesList}">
                                                        <option value="${coach.id}"
                                                                <c:if test="${orgClass.coachId == coach.id}">selected</c:if> >${coach.realName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="col-md-2">
                                                <button type="button" class="btn btn-sm btn-danger class-date-del">
                                                    <i class="fa fa-trash"></i> 删除
                                                </button>
                                                <button type="button" class="btn btn-sm btn-primary class-date-add">
                                                    <i class="fa fa-plus"></i> 添加
                                                </button>
                                            </div>
                                        </div>
                                    </c:if>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer">
                            <c:if test="${!orgClassEnd}">
                                <button type="button" class="btn btn-primary save-class-schedule">
                                    <i class="fa fa-check"></i> 保 存
                                </button>
                            </c:if>
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
    <c:param name="subMenu" value="schedule"/>
</c:import>
