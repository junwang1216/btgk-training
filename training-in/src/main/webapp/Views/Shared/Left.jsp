<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<div class="sidebar">
    <nav class="sidebar-nav">
        <ul class="nav">
            <c:if test="${Admin.roleId <= 999}">
                <li class="nav-item">
                    <a class="nav-link" href="/admin/dashboard/index">
                        <i class="icon-graph"></i> 营业面板
                    </a>
                </li>
                <li class="nav-item nav-dropdown <c:if test="${param.menu == 'venue'}">open</c:if>">
                    <a class="nav-link nav-dropdown-toggle" href="javascript:;">
                        <i class="icon-home"></i> 机构设置
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

                <li class="nav-item nav-dropdown <c:if test="${param.menu == 'class'}">open</c:if>" style="display: none">
                    <a class="nav-link nav-dropdown-toggle" href="javascript:;">
                        <i class="icon-calendar"></i> 约课管理
                    </a>
                    <ul class="nav-dropdown-items">
                        <li class="nav-item <c:if test="${param.subMenu == 'add'}">open</c:if>">
                            <a class="nav-link <c:if test="${param.subMenu == 'add'}">active</c:if>" href="/admin/class/add">
                                <i class="icon-arrow-right"></i> 约课发布
                            </a>
                        </li>
                        <li class="nav-item <c:if test="${param.subMenu == 'list' || param.subMenu == 'edit' || param.subMenu == 'schedule'}">open</c:if>">
                            <a class="nav-link <c:if test="${param.subMenu == 'list' || param.subMenu == 'edit' || param.subMenu == 'schedule'}">active</c:if>" href="/admin/class/list">
                                <i class="icon-arrow-right"></i> 约课列表
                            </a>
                        </li>
                        <li class="nav-item <c:if test="${param.subMenu == 'progress'}">open</c:if>">
                            <a class="nav-link <c:if test="${param.subMenu == 'progress'}">active</c:if>" href="/admin/class/progress">
                                <i class="icon-arrow-right"></i> 约课课表
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

                <li class="nav-item nav-dropdown <c:if test="${param.menu == 'marketing'}">open</c:if>">
                    <a class="nav-link nav-dropdown-toggle" href="javascript:;">
                        <i class="icon-present"></i> 市场营销
                    </a>
                    <ul class="nav-dropdown-items">
                        <li class="nav-item <c:if test="${param.subMenu == 'potentialStu'}">open</c:if>">
                            <a class="nav-link <c:if test="${param.subMenu == 'potentialStu'}">active</c:if>" href="/admin/marketing/potential/students">
                                <i class="icon-arrow-right"></i> 潜在学员
                            </a>
                        </li>
                        <li class="nav-item <c:if test="${param.subMenu == 'add'}">open</c:if>">
                            <a class="nav-link <c:if test="${param.subMenu == 'add'}">active</c:if>" href="/admin/marketing/potential/students/change">
                                <i class="icon-arrow-right"></i> 转化分析
                            </a>
                        </li>
                    </ul>
                </li>

                <li class="nav-item nav-dropdown <c:if test="${param.menu == 'data'}">open</c:if>">
                    <a class="nav-link nav-dropdown-toggle" href="javascript:;">
                        <i class="icon-chart"></i> 财务数据
                    </a>
                    <ul class="nav-dropdown-items">
                        <li class="nav-item <c:if test="${param.subMenu == 'finance' || param.subMenu == 'finance_log' || param.subMenu == 'finance_edit' || param.subMenu == 'finance_chart' || param.subMenu == 'finance_settings'}">open</c:if>">
                            <a class="nav-link <c:if test="${param.subMenu == 'finance' || param.subMenu == 'finance_log' || param.subMenu == 'finance_edit' || param.subMenu == 'finance_chart' || param.subMenu == 'finance_settings'}">active</c:if>" href="/admin/data/operation/finance">
                                <i class="icon-arrow-right"></i> 运营财务
                            </a>
                        </li>
                        <li class="nav-item <c:if test="${param.subMenu == 'orders' || param.subMenu == 'detail'}">open</c:if>">
                            <a class="nav-link <c:if test="${param.subMenu == 'orders' || param.subMenu == 'detail'}">active</c:if>" href="/admin/order/list">
                                <i class="icon-arrow-right"></i> 我的订单
                            </a>
                        </li>
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
                        <li class="nav-item <c:if test="${param.subMenu == 'setting'}">open</c:if>" style="display: none">
                            <a class="nav-link <c:if test="${param.subMenu == 'setting'}">active</c:if>" href="/admin/data/settings">
                                <i class="icon-arrow-right"></i> 收支类型设置
                            </a>
                        </li>
                    </ul>
                </li>
            </c:if>

            <c:if test="${Admin.roleId >= 9990}">
                <li class="nav-item nav-dropdown <c:if test="${param.menu == 'finance'}">open</c:if>">
                    <a class="nav-link nav-dropdown-toggle" href="javascript:;">
                        <i class="icon-chart"></i> 财务报表
                    </a>
                    <ul class="nav-dropdown-items">
                        <li class="nav-item <c:if test="${param.subMenu == 'relationship'}">open</c:if>">
                            <a class="nav-link <c:if test="${param.subMenu == 'relationship'}">active</c:if>" href="/admin/finance/relationship">
                                <i class="icon-arrow-right"></i> 组织管理
                            </a>
                        </li>
                        <li class="nav-item <c:if test="${param.subMenu == 'settings'}">open</c:if>">
                            <a class="nav-link <c:if test="${param.subMenu == 'settings'}">active</c:if>" href="/admin/finance/settings">
                                <i class="icon-arrow-right"></i> 目标设置
                            </a>
                        </li>
                        <li class="nav-item <c:if test="${param.subMenu == 'record'}">open</c:if>">
                            <a class="nav-link <c:if test="${param.subMenu == 'record'}">active</c:if>" href="/admin/finance/log">
                                <i class="icon-arrow-right"></i> 完成日志
                            </a>
                        </li>
                        <li class="nav-item <c:if test="${param.subMenu == 'performance'}">open</c:if>">
                            <a class="nav-link <c:if test="${param.subMenu == 'performance'}">active</c:if>" href="/admin/finance/performance">
                                <i class="icon-arrow-right"></i> 业绩排名
                            </a>
                        </li>
                        <li class="nav-item <c:if test="${param.subMenu == 'summary'}">open</c:if>">
                            <a class="nav-link <c:if test="${param.subMenu == 'summary'}">active</c:if>" href="/admin/finance/summary">
                                <i class="icon-arrow-right"></i> 数据汇总
                            </a>
                        </li>
                    </ul>
                </li>
            </c:if>

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
                    <c:if test="${Admin.roleId <= 999}">
                        <li class="nav-item <c:if test="${param.subMenu == 'database'}">open</c:if>">
                            <a class="nav-link <c:if test="${param.subMenu == 'database'}">active</c:if>" href="/admin/settings/database">
                                <i class="icon-arrow-right"></i> 数据库备份
                            </a>
                        </li>
                    </c:if>
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
