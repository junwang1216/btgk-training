<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<header class="app-header navbar">
    <button class="navbar-toggler mobile-sidebar-toggler d-lg-none" type="button">☰</button>
    <a class="navbar-brand" href="javascript:;"></a>
    <ul class="nav navbar-nav d-md-down-none">
        <li class="nav-item">
            <a class="nav-link navbar-toggler sidebar-toggler" href="javascript:;">☰</a>
        </li>
    </ul>
    <ul class="nav navbar-nav ml-auto">
        <li class="nav-item d-md-down-none pr-4">
            <a class="nav-link" href="javascript:;" data-target="#contact_online" data-toggle="modal">
                <i class="icon-speech"></i> 联系我们
            </a>
        </li>
        <li class="nav-item d-md-down-none pr-4">
            <a class="nav-link" href="/admin/settings/log">
                <i class="icon-info"></i> 系统日志
            </a>
        </li>
        <li class="nav-item dropdown pr-4">
            <a class="nav-link dropdown-toggle nav-link" data-toggle="dropdown" href="#" role="button"
                aria-haspopup="true" aria-expanded="false">
                <img src="Content/images/avatars/6.jpg?v=${static_resource_version}" class="img-avatar" alt="${Admin.realName}">
                <span class="d-md-down-none">${Admin.realName}</span>
            </a>
            <div class="dropdown-menu dropdown-menu-right">
                <%--<a class="dropdown-item" href="/admin/settings/profile">--%>
                    <%--<i class="fa fa-pencil"></i> 完善信息--%>
                <%--</a>--%>
                <%--<a class="dropdown-item" href="/admin/settings/profile">--%>
                    <%--<i class="fa fa-key"></i> 修改密码--%>
                <%--</a>--%>
                <a class="dropdown-item" href="/admin/passport/logout">
                    <i class="fa fa-lock"></i> 退出登录
                </a>
            </div>
        </li>
    </ul>
</header>
