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

            <li class="nav-item nav-dropdown <c:if test="${param.menu == 'finance'}">open</c:if>">
                <a class="nav-link nav-dropdown-toggle" href="javascript:;">
                    <i class="icon-chart"></i> 财务报表
                </a>
                <ul class="nav-dropdown-items">
                    <li class="nav-item <c:if test="${param.subMenu == 'add'}">open</c:if>">
                        <a class="nav-link <c:if test="${param.subMenu == 'add'}">active</c:if>" href="/admin/finance/add">
                            <i class="icon-arrow-right"></i> 数据编辑
                        </a>
                    </li>
                    <li class="nav-item <c:if test="${param.subMenu == 'record'}">open</c:if>">
                        <a class="nav-link <c:if test="${param.subMenu == 'record'}">active</c:if>" href="/admin/finance/log">
                            <i class="icon-arrow-right"></i> 完成日志
                        </a>
                    </li>
                    <c:if test="${Admin.roleId == 9999}">
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
                        <li class="nav-item <c:if test="${param.subMenu == 'performance'}">open</c:if>">
                            <a class="nav-link <c:if test="${param.subMenu == 'performance'}">active</c:if>" href="/admin/finance/performance?venueId=-1">
                                <i class="icon-arrow-right"></i> 业绩排名
                            </a>
                        </li>
                        <li class="nav-item <c:if test="${param.subMenu == 'summary'}">open</c:if>">
                            <a class="nav-link <c:if test="${param.subMenu == 'summary'}">active</c:if>" href="/admin/finance/summary">
                                <i class="icon-arrow-right"></i> 数据汇总
                            </a>
                        </li>
                    </c:if>
                </ul>
            </li>

            <li class="nav-item nav-dropdown <c:if test="${param.menu == 'settings'}">open</c:if>">
                <a class="nav-link nav-dropdown-toggle" href="javascript:;">
                    <i class="icon-settings"></i> 系统设置
                </a>
                <ul class="nav-dropdown-items">
                    <c:if test="${Admin.roleId == 9999}">
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
                    </c:if>
                    <li class="nav-item <c:if test="${param.subMenu == 'password'}">open</c:if>">
                        <a class="nav-link <c:if test="${param.subMenu == 'password'}">active</c:if>" href="/admin/settings/profile">
                            <i class="icon-arrow-right"></i> 修改密码
                        </a>
                    </li>
                </ul>
            </li>
        </ul>
    </nav>
</div>
