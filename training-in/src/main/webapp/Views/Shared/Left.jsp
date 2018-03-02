<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<div class="sidebar">
    <nav class="sidebar-nav">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href="/admin/dashboard/index">
                    <i class="icon-graph"></i> 工作面板
                </a>
            </li>

            <li class="nav-item nav-dropdown <c:if test="${param.menu == 'venue'}">open</c:if>">
                <a class="nav-link nav-dropdown-toggle" href="javascript:;">
                    <i class="icon-settings"></i> 机构设置
                </a>
                <ul class="nav-dropdown-items">
                    <li class="nav-item <c:if test="${param.subMenu == 'settings'}">open</c:if>">
                        <a class="nav-link <c:if test="${param.subMenu == 'settings'}">active</c:if>" href="/admin/venue/settings">
                            <i class="icon-arrow-right"></i> 基本资料
                        </a>
                    </li>
                    <li class="nav-item <c:if test="${param.subMenu == 'branch'}">open</c:if>">
                        <a class="nav-link <c:if test="${param.subMenu == 'branch'}">active</c:if>" href="/admin/venue/branch">
                            <i class="icon-arrow-right"></i> 场馆设置
                        </a>
                    </li>
                    <li class="nav-item <c:if test="${param.subMenu == 'sports'}">open</c:if>">
                        <a class="nav-link <c:if test="${param.subMenu == 'sports'}">active</c:if>" href="/admin/venue/sports">
                            <i class="icon-arrow-right"></i> 项目设置
                        </a>
                    </li>
                    <li class="nav-item <c:if test="${param.subMenu == 'course'}">open</c:if>">
                        <a class="nav-link <c:if test="${param.subMenu == 'course'}">active</c:if>" href="/admin/venue/course">
                            <i class="icon-arrow-right"></i> 课程管理
                        </a>
                    </li>
                    <li class="nav-item <c:if test="${param.subMenu == 'coaches'}">open</c:if>">
                        <a class="nav-link <c:if test="${param.subMenu == 'coaches'}">active</c:if>" href="/admin/venue/coaches">
                            <i class="icon-arrow-right"></i> 教练/班主任设置
                        </a>
                    </li>
                </ul>
            </li>

            <li class="nav-item nav-dropdown <c:if test="${param.menu == 'class'}">open</c:if>">
                <a class="nav-link nav-dropdown-toggle" href="javascript:;">
                    <i class="icon-list"></i> 班级管理
                </a>
                <ul class="nav-dropdown-items">
                    <li class="nav-item <c:if test="${param.subMenu == 'add'}">open</c:if>">
                        <a class="nav-link <c:if test="${param.subMenu == 'add'}">active</c:if>" href="/admin/class/add">
                            <i class="icon-arrow-right"></i> 班级设置
                        </a>
                    </li>
                    <li class="nav-item <c:if test="${param.subMenu == 'list' || param.subMenu == 'edit' || param.subMenu == 'schedule'}">open</c:if>">
                        <a class="nav-link <c:if test="${param.subMenu == 'list' || param.subMenu == 'edit' || param.subMenu == 'schedule'}">active</c:if>" href="/admin/class/list">
                            <i class="icon-arrow-right"></i> 班级列表
                        </a>
                    </li>
                    <li class="nav-item <c:if test="${param.subMenu == 'progress'}">open</c:if>">
                        <a class="nav-link <c:if test="${param.subMenu == 'progress'}">active</c:if>" href="/admin/class/progress">
                            <i class="icon-arrow-right"></i> 上课进度
                        </a>
                    </li>
                    <li class="nav-item <c:if test="${param.subMenu == 'test'}">open</c:if>">
                        <a class="nav-link <c:if test="${param.subMenu == 'test'}">active</c:if>" href="/admin/class/test">
                            <i class="icon-arrow-right"></i> 班级评测
                        </a>
                    </li>
                    <li class="nav-item <c:if test="${param.subMenu == 'coach'}">open</c:if>">
                        <a class="nav-link <c:if test="${param.subMenu == 'coach'}">active</c:if>" href="/admin/class/coach/sign">
                            <i class="icon-arrow-right"></i> 教练签到
                        </a>
                    </li>
                    <li class="nav-item <c:if test="${param.subMenu == 'student'}">open</c:if>">
                        <a class="nav-link <c:if test="${param.subMenu == 'student'}">active</c:if>" href="/admin/class/student/sign">
                            <i class="icon-arrow-right"></i> 学员签到
                        </a>
                    </li>
                </ul>
            </li>

            <li class="nav-item nav-dropdown <c:if test="${param.menu == 'students'}">open</c:if>">
                <a class="nav-link nav-dropdown-toggle" href="javascript:;">
                    <i class="icon-people"></i> 学员管理
                </a>
                <ul class="nav-dropdown-items">
                    <li class="nav-item <c:if test="${param.subMenu == 'add'}">open</c:if>">
                        <a class="nav-link <c:if test="${param.subMenu == 'add'}">active</c:if>" href="/admin/students/add">
                            <i class="icon-arrow-right"></i> 学员添加
                        </a>
                    </li>
                    <li class="nav-item <c:if test="${param.subMenu == 'list'|| param.subMenu == 'edit'}">open</c:if>">
                        <a class="nav-link <c:if test="${param.subMenu == 'list'|| param.subMenu == 'edit'}">active</c:if>" href="/admin/students/list">
                            <i class="icon-arrow-right"></i> 学员列表
                        </a>
                    </li>
                    <li class="nav-item <c:if test="${param.subMenu == 'batch'}">open</c:if>">
                        <a class="nav-link <c:if test="${param.subMenu == 'batch'}">active</c:if>" href="/admin/students/batch">
                            <i class="icon-arrow-right"></i> 批量导入学员
                        </a>
                    </li>
                </ul>
            </li>

            <li class="nav-item nav-dropdown <c:if test="${param.menu == 'data'}">open</c:if>">
                <a class="nav-link nav-dropdown-toggle" href="javascript:;">
                    <i class="icon-chart"></i> 数据统计
                </a>
                <ul class="nav-dropdown-items">
                    <li class="nav-item <c:if test="${param.subMenu == 'income'}">open</c:if>">
                        <a class="nav-link <c:if test="${param.subMenu == 'income'}">active</c:if>" href="/admin/data/income">
                            <i class="icon-arrow-right"></i> 收入流水
                        </a>
                    </li>
                    <li class="nav-item <c:if test="${param.subMenu == 'expend'}">open</c:if>">
                        <a class="nav-link <c:if test="${param.subMenu == 'expend'}">active</c:if>" href="/admin/data/expend">
                            <i class="icon-arrow-right"></i> 支出流水
                        </a>
                    </li>
                    <li class="nav-item <c:if test="${param.subMenu == 'summary'}">open</c:if>">
                        <a class="nav-link <c:if test="${param.subMenu == 'summary'}">active</c:if>" href="/admin/data/summary">
                            <i class="icon-arrow-right"></i> 收支统计
                        </a>
                    </li>
                    <li class="nav-item <c:if test="${param.subMenu == 'setting'}">open</c:if>">
                        <a class="nav-link <c:if test="${param.subMenu == 'setting'}">active</c:if>" href="/admin/data/settings">
                            <i class="icon-arrow-right"></i> 收支类型设置
                        </a>
                    </li>
                </ul>
            </li>

            <li class="nav-item nav-dropdown <c:if test="${param.menu == 'settings'}">open</c:if>">
                <a class="nav-link nav-dropdown-toggle" href="javascript:;">
                    <i class="icon-settings"></i> 系统设置
                </a>
                <ul class="nav-dropdown-items">
                    <li class="nav-item <c:if test="${param.subMenu == 'admin' || param.subMenu == 'profile'}">open</c:if>">
                        <a class="nav-link <c:if test="${param.subMenu == 'admin' || param.subMenu == 'profile'}">active</c:if>" href="/admin/settings/admin">
                            <i class="icon-arrow-right"></i> 管理员设置
                        </a>
                    </li>
                    <li class="nav-item <c:if test="${param.subMenu == 'database'}">open</c:if>">
                        <a class="nav-link <c:if test="${param.subMenu == 'database'}">active</c:if>" href="/admin/settings/database">
                            <i class="icon-arrow-right"></i> 数据库备份
                        </a>
                    </li>
                    <li class="nav-item <c:if test="${param.subMenu == 'log'}">open</c:if>">
                        <a class="nav-link <c:if test="${param.subMenu == 'log'}">active</c:if>" href="/admin/settings/log">
                            <i class="icon-arrow-right"></i> 系统日志
                        </a>
                    </li>
                </ul>
            </li>
        </ul>
    </nav>
</div>
