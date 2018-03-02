<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.in.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <style type="text/css">
        .form-control-label {
            text-align: right;
            font-weight: bold;
        }
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script async type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/students/list.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="container-fluid">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <strong>批量导入学员</strong>
                            <small>batch</small>
                        </div>
                        <div class="card-block">
                            <form action="" method="post" class="form-horizontal row">
                                <div class="form-group row">
                                    <label class="col-md-3 form-control-label">
                                        <span class="text-danger">*</span> 上传文件
                                    </label>
                                    <div class="col-md-4">
                                        <input type="file" id="file-input" name="file-input">
                                    </div>
                                    <div class="col-md-5">
                                        <a href="http://www.xbooking.com/train_institution/member_import.xls" class="btn btn-link">
                                            <i class="fa fa-cloud-download"></i> 批量导入学员标准模板下载
                                        </a>
                                    </div>
                                </div>
                                <div class="help-block row">
                                    <div class="offset-1 col-md-10">
                                        <p>
                                            机构项目代码：下方列出本机构培训项目代码，在编辑学员批量录入文档时，需要根据学员的运动项目，
                                            准确填写相应的项目代码，并认真核对，以防导入过程出现错误。注：该代码只适用于本俱乐部。
                                        </p>
                                        <p>篮球：11， 游泳：21， 赛艇：870， 花样游泳：1035，</p>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer">
                            <button type="button" class="btn btn-primary">
                                <i class="fa fa-download"></i> 开始导入
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <!--/.row-->
        </div>

    </div>
</layout:override>

<c:import url="../Shared/GeneralLayout.jsp">
    <c:param name="menu" value="students"/>
    <c:param name="subMenu" value="batch"/>
</c:import>
