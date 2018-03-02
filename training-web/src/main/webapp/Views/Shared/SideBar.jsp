<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>

<!-- start of sidebar -->
<aside class="col-sm-4 page-sidebar">

    <div class="panel panel-info widget">
        <div class="panel-heading">
            <span>资料分类</span>
        </div>
        <div class="panel-body">
            <ul>
                <li><a href="/web/document/list?class=1">体育人文社会学<span class="badge">4万</span></a></li>
                <li><a href="/web/document/list?class=2">运动人体科学<span class="badge">4000</span></a></li>
                <li><a href="/web/document/list?class=3">学校体育<span class="badge">400</span></a></li>
                <li><a href="/web/document/list?class=4">运动训练<span class="badge">40</span></a></li>
                <li><a href="/web/document/list?class=5">体育组织<span class="badge">42万</span></a></li>
                <li><a href="/web/document/list?class=6">体育竞赛<span class="badge">1.3万</span></a></li>
                <li><a href="/web/document/list?class=7">体育设施与器材<span class="badge">23万</span></a></li>
                <li><a href="/web/document/list?class=8">大众体育<span class="badge">10万</span></a></li>
                <li><a href="/web/document/list?class=9">体育科学研究<span class="badge">2万</span></a></li>
                <li><a href="/web/document/list?class=10">体育政策制度<span class="badge">5000</span></a></li>
            </ul>
        </div>
    </div>

    <div class="panel panel-info widget">
        <div class="panel-heading">
            <span>资料标签</span>
        </div>
        <div class="panel-body tagcloud">
            <a href="/web/document/search?keywords=科技" class="btn btn-default btn-sm">科技</a>
            <a href="/web/document/search?keywords=创新" class="btn btn-default btn-sm">创新</a>
            <a href="/web/document/search?keywords=创新" class="btn btn-default btn-sm">体育</a>
            <a href="/web/document/search?keywords=创新" class="btn btn-default btn-sm">篮球</a>
            <a href="/web/document/search?keywords=创新" class="btn btn-default btn-sm">足球</a>
            <a href="/web/document/search?keywords=创新" class="btn btn-default btn-sm">羽毛球</a>
            <a href="/web/document/search?keywords=创新" class="btn btn-default btn-sm">经济</a>
            <a href="/web/document/search?keywords=创新" class="btn btn-default btn-sm">艺术</a>
            <a href="/web/document/search?keywords=创新" class="btn btn-default btn-sm">竞技</a>
            <a href="/web/document/search?keywords=创新" class="btn btn-default btn-sm">专利</a>
            <a href="/web/document/search?keywords=创新" class="btn btn-default btn-sm">新运动</a>
            <a href="/web/document/search?keywords=创新" class="btn btn-default btn-sm">公司</a>
            <a href="/web/document/search?keywords=创新" class="btn btn-default btn-sm">滑雪</a>
            <a href="/web/document/search?keywords=创新" class="btn btn-default btn-sm">场馆</a>
            <a href="/web/document/search?keywords=创新" class="btn btn-default btn-sm">公司管理</a>
            <a href="/web/document/search?keywords=创新" class="btn btn-default btn-sm">MEM</a>
            <a href="/web/document/search?keywords=创新s" class="btn btn-default btn-sm">商务经济</a>
        </div>
    </div>
</aside>
<!-- end of sidebar -->
