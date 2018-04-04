<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.in.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <style type="text/css">
        .icon-sports {}
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
        .sport-item .sport-setting {
            margin-top: -2rem;
        }
        .sport-item a {
            text-decoration: none;
        }
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/venue/sports.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="container-fluid">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-header">
                            <strong>项目设置</strong>
                            <small>Settings</small>
                        </div>
                        <div class="card-block row">
                            <c:forEach var="sport" items="${orgSportsList}" varStatus="loop">
                                <c:if test="${sport.id == sportId}">
                                    <div class="col-md-6">
                                        <div class="card card-accent-success sport-item">
                                            <a class="card-body p-0" href="javascript:;">
                                                <div class="p-2 clearfix">
                                                    <i class="icon-sports ${sport.sportIcon} bg-primary p-4 mr-3 float-left"></i>
                                                    <div class="mb-0 pt-1">${sport.sportName}</div>
                                                    <div class="text-muted text-uppercase font-weight-bold font-xs">${sport.sportNameEn}</div>
                                                    <i class="fa fa-chevron-right p-2 float-right sport-setting"></i>
                                                </div>
                                            </a>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${sport.id != sportId}">
                                    <div class="col-md-6">
                                        <div class="card card-accent-secondary sport-item">
                                            <a class="card-body p-0" href="/admin/venue/sports?sportId=${sport.id}">
                                                <div class="p-2 clearfix">
                                                    <i class="icon-sports ${sport.sportIcon} bg-primary p-4 mr-3 float-left"></i>
                                                    <div class="mb-0 pt-1">${sport.sportName}</div>
                                                    <div class="text-muted text-uppercase font-weight-bold font-xs">${sport.sportNameEn}</div>
                                                    <i class="fa fa-cog p-2 float-right sport-setting"></i>
                                                </div>
                                            </a>
                                        </div>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-header">
                            <strong>评测技能</strong>
                            <small>Skills</small>
                        </div>
                        <div class="card-block">
                            <form id="skill_form" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                                <input type="hidden" name="sportId" id="id_skill_sportId" value="${sportId}">
                                <c:if test="${orgSportsSkillsList != null}">
                                    <c:forEach var="skill" items="${orgSportsSkillsList}" varStatus="loop">
                                        <div class="form-group row skill-item">
                                            <input type="hidden" name="id" value="${skill.id}" disabled>
                                            <div class="col-md-5">
                                                <input type="text" class="form-control" name="skillName"
                                                       placeholder="评测项名称" value="${skill.skillName}" disabled>
                                            </div>
                                            <div class="col-md-3">
                                                <select class="form-control" name="maxValue" disabled>
                                                    <option value="10" <c:if test="${skill.maxValue == 10}"></c:if> >最高10分</option>
                                                    <option value="100" <c:if test="${skill.maxValue == 100}"></c:if>>最高100分</option>
                                                </select>
                                            </div>
                                            <div class="col-md-4">
                                                <button type="button" class="btn btn-sm btn-danger delete-skill">
                                                    <i class="fa fa-trash"></i> 删除
                                                </button>
                                                <c:if test="${loop.last}">
                                                    <button type="button" class="btn btn-sm btn-primary add-skill">
                                                        <i class="fa fa-plus"></i> 添加
                                                    </button>
                                                </c:if>
                                                <c:if test="${!loop.last}">
                                                    <button type="button" class="btn btn-sm btn-primary add-skill" style="display: none;">
                                                        <i class="fa fa-plus"></i> 添加
                                                    </button>
                                                </c:if>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${orgSportsSkillsList == null || orgSportsSkillsList.size() == 0}">
                                    <div class="form-group row">
                                        <div class="col-md-5">
                                            <input type="text" class="form-control" name="skillName" placeholder="评测项名称">
                                        </div>
                                        <div class="col-md-3">
                                            <select class="form-control" name="maxValue">
                                                <option value="10">最高10分</option>
                                                <option value="100">最高100分</option>
                                            </select>
                                        </div>
                                        <div class="col-md-4">
                                            <button type="button" class="btn btn-sm btn-danger delete-skill">
                                                <i class="fa fa-trash"></i> 删除
                                            </button>
                                            <button type="button" class="btn btn-sm btn-primary add-skill">
                                                <i class="fa fa-plus"></i> 添加
                                            </button>
                                        </div>
                                    </div>
                                </c:if>
                            </form>
                        </div>
                        <div class="card-footer">
                            <button type="button" class="btn btn-primary save-skill">
                                <i class="fa fa-check"></i> 保 存
                            </button>
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
    <c:param name="menu" value="venue"/>
    <c:param name="subMenu" value="sports"/>
</c:import>
