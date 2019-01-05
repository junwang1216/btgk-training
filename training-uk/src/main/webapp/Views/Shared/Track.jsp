<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<!-- Breadcrumb -->
<ol class="breadcrumb">
    <li class="breadcrumb-item">当前系统</li>

    <c:choose>
        <c:when test="${param.menu == 'dashboard'}">
            <li class="breadcrumb-item active">工作台</li>
        </c:when>

        <c:when test="${param.menu == 'finance'}">
            <li class="breadcrumb-item">财务报表</li>
            <c:choose>
                <c:when test="${param.subMenu == 'relationship'}">
                    <li class="breadcrumb-item active">组织管理</li>
                </c:when>
                <c:when test="${param.subMenu == 'settings'}">
                    <li class="breadcrumb-item active">指标设置</li>
                </c:when>
                <c:when test="${param.subMenu == 'params'}">
                    <li class="breadcrumb-item active">参数设置</li>
                </c:when>
                <c:when test="${param.subMenu == 'log'}">
                    <li class="breadcrumb-item active">完成日志</li>
                </c:when>
                <c:when test="${param.subMenu == 'add'}">
                    <li class="breadcrumb-item active">编辑数据</li>
                </c:when>
                <c:when test="${param.subMenu == 'performance'}">
                    <li class="breadcrumb-item active">业绩排名</li>
                </c:when>
                <c:when test="${param.subMenu == 'summary'}">
                    <li class="breadcrumb-item active">数据汇总</li>
                </c:when>
            </c:choose>
        </c:when>

        <c:when test="${param.menu == 'settings'}">
            <li class="breadcrumb-item">系统设置</li>
            <c:choose>
                <c:when test="${param.subMenu == 'admin' || param.subMenu == 'profile'}">
                    <li class="breadcrumb-item active">管理员设置</li>
                </c:when>
                <c:when test="${param.subMenu == 'database'}">
                    <li class="breadcrumb-item active">数据库备份</li>
                </c:when>
                <c:when test="${param.subMenu == 'log'}">
                    <li class="breadcrumb-item active">系统日志</li>
                </c:when>
            </c:choose>
        </c:when>
    </c:choose>
</ol>

<div class="modal fade" id="contact_online" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-success modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <table class="table table-responsive-sm table-bordered">
                    <tr>
                        <th class="text-center"><i class="fa fa-2x fa-mobile"></i></th><td>12345678901</td>
                    </tr>
                    <tr>
                        <th class="text-center"><i class="fa fa-2x fa-phone"></i></th><td>(010)-1234567</td>
                    </tr>
                    <tr>
                        <th class="text-center"><i class="fa fa-2x fa-at"></i></th><td>test1234@test.com</td>
                    </tr>
                    <tr>
                        <th class="text-center"><i class="fa fa-2x fa-location-arrow"></i></th><td>北京市海淀区某某路100号某某大厦1232室</td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
