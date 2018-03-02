<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.h5.layout.Blocks" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="layout" uri="http://www.sports.com/tags/tag"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <base href="<%= basePath %>">

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">

    <title>${param.title == null || param.title eq "" ? "北体高科 gaokesport.com - 智慧场馆解决方案的引导者 - TO BE THE GIANT OF WISDOM VENUES SOLUTIONS" : param.title}</title>

    <meta name="keywords" content="${param.keyword == null ? "乐享体育" : "乐享体育"}">
    <meta name="description" content="${param.desc == null ? "乐享体育" : "乐享体育"}">
    <meta name="author" content="北体高科（北京）科技有限公司">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no, address=no">
    <meta name="handleldFriendly" content="true">
    <meta name="mobileOptimized" content="width">
    <meta name="flexible" content="maximum-dpr=2">

    <!-- favicon -->
    <link href="Content/images/favicon.ico?v=${static_resource_version}" rel="shortcut icon" type="image/x-icon">

    <!-- head -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/weui/1.1.2/style/weui.min.css">
    <link rel="stylesheet" href="https://cdn.bootcss.com/jquery-weui/1.2.0/css/jquery-weui.min.css">

    <layout:block name="<%=Blocks.BLOCK_HEADER_CSS%>"/>
</head>

<body class="app flex-row align-items-center">

<!-- content -->
<layout:block name="<%=Blocks.BLOCK_BODY%>"/>

<!-- body 最后 -->
<script src="/Content/third/flexible/flexible.min.js?ver=${static_resource_version}"></script>
<script src="https://cdn.bootcss.com/jquery/1.11.0/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/jquery-weui/1.2.0/js/jquery-weui.min.js"></script>

</body>
</html>
