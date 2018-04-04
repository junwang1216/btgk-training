<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.in.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <style type="text/css">
        .user-list th {
            padding: 0.75rem;
        }
        .user-list td {
            padding: 0.3rem 0.75rem;
        }
        #user_recharge .modal-body {
            padding-top: 2rem;
        }
        .icon-sports {
            display: block;
        }
        .icon-gaoerfu {
            background: url("/Content/images/sports/icon-gaoerfu.png") no-repeat center;
            background-size: 1.5rem;
        }
        .icon-huaxue {
            background: url("/Content/images/sports/icon-huaxue.png") no-repeat center;
            background-size: 1.5rem;
        }
        .icon-lanqiu {
            background: url("/Content/images/sports/icon-lanqiu.png") no-repeat center;
            background-size: 1.5rem;
        }
        .icon-paiqiu {
            background: url("/Content/images/sports/icon-paiqiu.png") no-repeat center;
            background-size: 1.5rem;
        }
        .icon-pingpangqiu {
            background: url("/Content/images/sports/icon-pingpangqiu.png") no-repeat center;
            background-size: 1.5rem;
        }
        .icon-wangqiu {
            background: url("/Content/images/sports/icon-wangqiu.png") no-repeat center;
            background-size: 1.5rem;
        }
        .icon-youyong {
            background: url("/Content/images/sports/icon-youyong.png") no-repeat center;
            background-size: 1.5rem;
        }
        .icon-yumaoqiu {
            background: url("/Content/images/sports/icon-yumaoqiu.png") no-repeat center;
            background-size: 1.5rem;
        }
        .icon-zuqiu {
            background: url("/Content/images/sports/icon-zuqiu.png") no-repeat center;
            background-size: 1.5rem;
        }
        .icon-bangqiu {
            background: url("/Content/images/sports/icon-bangqiu.png") no-repeat center;
            background-size: 1.5rem;
        }

        .class-item {
            cursor: pointer;
            height: 7.5rem;
            position: relative;
        }
        .class-item .class-price {
            position: absolute;
            bottom: 0;
            left: 0;
        }
        .class-item.class-item-selected {
            border: 2px solid #4dbd74;
        }
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/students/list.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="modal fade" id="class_students" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-primary" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <form id="class_form" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                        <input type="hidden" id="class_student_id" name="studentId">
                        <input type="hidden" id="class_id" name="classId">
                        <input type="hidden" id="class_ids" name="classIds">
                        <div class="class-list row">
                            <c:forEach var="item" items="${orgClassList}" varStatus="loop">
                                <c:if test="${item.orgClass.status != 3}">
                                    <div class="col-md-4">
                                        <div class="card">
                                            <div class="card-body p-2 clearfix class-item"
                                                 data-id="${item.orgClass.id}"
                                                 data-name="${item.orgClass.className}"
                                                 data-price="${item.orgClass.classPrice}">
                                                <i class="icon-sports ${item.orgSports.sportIcon} bg-primary p-4"></i>
                                                <div class="mb-1 pt-1 font-weight-bold font-xs"> ${item.orgClass.className}</div>
                                                <div class="text-muted font-weight-bold font-xs class-price p-2">
                                                    <c:if test="${item.orgClass.status == 1}"><i class="fa fa-battery-empty" title="已开班"></i></c:if>
                                                    <c:if test="${item.orgClass.status == 2}"><i class="fa fa-battery-half" title="上课中"></i></c:if>
                                                    &nbsp;${item.orgClass.classPrice}元
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <span class="text-danger pay-note"></span>
                    <button type="button" class="btn btn-sm btn-primary save-class">
                        <i class="fa fa-check"></i> 确认去支付
                    </button>
                    <button type="button" class="btn btn-sm btn-danger save-class-recharge" style="display: none">
                        <i class="fa fa-check"></i> 确认去退费
                    </button>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="../Shared/Payment.jsp" %>

    <div class="container-fluid">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <strong>学员列表</strong>
                            <small>Students</small>
                        </div>
                        <div class="card-block">
                            <form id="students_form" action="" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                                <div class="form-group row">
                                    <div class="col-md-2">
                                        <input type="text" id="user_name" name="realName" class="form-control" placeholder="学生姓名" value="${conditions.realName}">
                                    </div>
                                    <div class="col-md-2">
                                        <input type="text" id="user_mobile" name="mobile" class="form-control" placeholder="手机号码" value="${conditions.mobile}">
                                    </div>
                                    <div class="col-md-2">
                                        <select class="form-control" id="user_class" name="classId">
                                            <option value="0">所在班级</option>
                                            <c:forEach var="item" items="${orgClassList}">
                                                <option value="${item.orgClass.id}" <c:if test="${conditions.classId == item.orgClass.id}">selected</c:if> >${item.orgClass.className}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-6">
                                        <button type="button" class="btn btn-primary search-students">
                                            <i class="fa fa-search"></i> 检 索
                                        </button>
                                        <a href="/admin/students/add" class="btn btn-primary pull-right" title="学员添加">
                                            <i class="fa fa-plus"></i> 学员添加
                                        </a>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer text-right"></div>
                        <div class="card-block">
                            <table class="table table-striped table-sm user-list">
                                <thead>
                                <tr>
                                    <th>##</th>
                                    <th>学员姓名</th>
                                    <th>所在班级</th>
                                    <th>手机号码</th>
                                    <th>出生日期</th>
                                    <th>性别</th>
                                    <th>身高(cm)</th>
                                    <th>体重(kg)</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="item" items="${orgStudentsList}" varStatus="loop">
                                    <tr data-id="${item.orgStudents.id}" <c:if test="${item.orgClass != null}">data-class="${item.orgClass.id}"</c:if> >
                                        <td>${loop.index + 1}</td>
                                        <td>${item.orgStudents.realName}</td>
                                        <td class="user-list-class">
                                            <c:forEach var="cls" items="${item.orgClassList}">
                                                <c:if test="${cls.status != 3}">
                                                    <a href="/admin/class/list?className=${cls.className}" data-class="${cls.id}">${cls.className}</a>&nbsp;
                                                </c:if>
                                                <%--<c:if test="${cls.status == 3}">--%>
                                                    <%--<span data-class="${cls.id}">${cls.className}</span>&nbsp;--%>
                                                <%--</c:if>--%>
                                            </c:forEach>
                                        </td>
                                        <td>${item.orgStudents.mobileSensitive}</td>
                                        <td>${item.orgStudents.birthday}</td>
                                        <td>
                                            <c:if test="${item.orgStudents.sex == 1}">
                                                <i class="fa fa-mars"></i> 男
                                            </c:if>
                                            <c:if test="${item.orgStudents.sex == 2}">
                                                <i class="fa fa-venus"></i> 女
                                            </c:if>
                                        </td>
                                        <td>${item.orgStudents.height}</td>
                                        <td>${item.orgStudents.weight}</td>
                                        <td>
                                            <a href="/admin/students/edit?studentId=${item.orgStudents.id}" class="btn btn-sm btn-primary user-refresh" title="重新编辑">
                                                <i class="fa fa-pencil"></i> 修改
                                            </a>
                                            <a href="javascript:" class="btn btn-sm btn-danger user-class" title="分班"
                                               data-target="#class_students" data-toggle="modal">
                                                <i class="fa fa-sitemap"></i> 分班
                                            </a>
                                                <%--<a href="#" class="btn btn-sm btn-danger user-recharge" title="缴费金额"--%>
                                                <%--data-target="#user_recharge" data-toggle="modal">--%>
                                                <%--<i class="fa fa-jpy"></i> 缴费--%>
                                                <%--</a>--%>
                                            <%--<a href="javascript:" class="btn btn-sm btn-warning user-recharge" title="退班"--%>
                                               <%--data-target="#class_students" data-toggle="modal">--%>
                                                <%--<i class="fa fa-money"></i> 退班--%>
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
    <c:param name="menu" value="students"/>
    <c:param name="subMenu" value="list"/>
</c:import>
