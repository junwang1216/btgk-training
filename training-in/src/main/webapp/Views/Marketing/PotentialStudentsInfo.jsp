<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.in.layout.Blocks" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <style type="text/css">
        .class-list th {
            padding: 0.75rem;
        }
        .class-list td {
            padding: 0.3rem 0.75rem;
        }
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/class/list.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="modal fade" id="student_change_go" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-primary" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <form id="change_form" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label" for="">
                                <span class="text-danger">*</span> 分配班级
                            </label>
                            <div class="col-md-9">
                                <select class="form-control" name="status">
                                    <option value="1">班级A</option>
                                    <option value="2">班级B</option>
                                </select>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-sm btn-secondary" data-dismiss="modal">
                        <i class="fa fa-close"></i> 取 消
                    </button>
                    <button type="button" class="btn btn-sm btn-primary save-class-status">
                        <i class="fa fa-check"></i> 确 认
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="student_push_go" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-primary" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <form id="push_form" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label" for="">
                                <span class="text-danger">*</span> 营销策略
                            </label>
                            <div class="col-md-9">
                                <select class="form-control" name="status">
                                    <option value="1">班级A</option>
                                    <option value="2">班级B</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label" for="">
                                <span class="text-danger">*</span> 试听班级
                            </label>
                            <div class="col-md-9">
                                <select class="form-control" name="status">
                                    <option value="1">班级A</option>
                                    <option value="2">班级B</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label" for="">
                                <span class="text-danger">*</span> 试听课程
                            </label>
                            <div class="col-md-9">
                                <select class="form-control" name="status">
                                    <option value="1">2018-12-12 09:00-10:00</option>
                                    <option value="1">2018-12-12 09:00-10:00</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label" for="">
                                <span class="text-danger">*</span> 整体评价
                            </label>
                            <div class="col-md-9">
                                <select class="form-control" name="status">
                                    <option value="1">班级A</option>
                                    <option value="2">班级B</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-3 form-control-label" for="">
                                <span class="text-danger">*</span> 备注
                            </label>
                            <div class="col-md-9">
                                <textarea class="form-control"></textarea>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-sm btn-secondary" data-dismiss="modal">
                        <i class="fa fa-close"></i> 取 消
                    </button>
                    <button type="button" class="btn btn-sm btn-primary save-class-status">
                        <i class="fa fa-check"></i> 确 认
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="container-fluid">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <strong>潜在学员详情</strong>
                            <small>Potential Students Information</small>
                            <label class="switch switch-sm switch-text switch-info float-right mb-0">
                                <input type="checkbox" class="switch-input" name="isEdit" id="info_is_edit">
                                <span class="switch-label" data-on="是" data-off="否"></span>
                                <span class="switch-handle"></span>
                            </label>
                        </div>
                        <div class="card-block">
                            <form id="students_form" method="post" class="form-horizontal row" novalidate onsubmit="return false;">
                                <input type="hidden" id="info_id" name="id">
                                <div class="col-md-6">
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label" for="info_tag_name">
                                            <span class="text-danger">*</span> 学员姓名
                                        </label>
                                        <div class="col-md-9">
                                            <input type="text" id="info_tag_name" name="orgName" class="form-control" placeholder="请输入学员姓名"
                                                   data-val="true" data-val-required="机构名称不能为空"
                                                   data-val-length-max="30" data-val-length-min="2" data-val-length="机构名称必须包含 2~30 个字符">
                                            <div data-valmsg-for="orgName" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label" for="info_tag_name">
                                            <span class="text-danger">*</span> 出生日期
                                        </label>
                                        <div class="col-md-9">
                                            <input type="text" id="info_tag_name" name="orgName" class="form-control" placeholder="请输入学员姓名"
                                                   data-val="true" data-val-required="机构名称不能为空"
                                                   data-val-length-max="30" data-val-length-min="2" data-val-length="机构名称必须包含 2~30 个字符">
                                            <div data-valmsg-for="orgName" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label" for="info_tag_name">
                                            <span class="text-danger">*</span> 身份证号
                                        </label>
                                        <div class="col-md-9">
                                            <input type="text" id="info_tag_name" name="orgName" class="form-control" placeholder="请输入学员姓名"
                                                   data-val="true" data-val-required="机构名称不能为空"
                                                   data-val-length-max="30" data-val-length-min="2" data-val-length="机构名称必须包含 2~30 个字符">
                                            <div data-valmsg-for="orgName" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label" for="info_contact_mobile">
                                            <span class="text-danger">*</span> 手机号码
                                        </label>
                                        <div class="col-md-9">
                                            <input type="text" class="form-control" id="info_contact_mobile" name="contactMobile" placeholder="请输入手机号码"
                                                   data-val="true" data-val-required="手机号码不能为空"
                                                   data-val-regex-pattern="^(13[0-9]|15[012356789]|166|17[0-9]|18[012356789]|14[57]|19[89])[0-9]{8}$" data-val-regex="手机号码格式不正确">
                                            <div data-valmsg-for="contactMobile" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label" for="info_contact_name">
                                            <span class="text-danger">*</span> 联系人姓名
                                        </label>
                                        <div class="col-md-9">
                                            <input type="text" class="form-control" id="info_contact_name" name="contactName" placeholder="请输入联系人"
                                                   data-val="true" data-val-required="联系人不能为空"
                                                   data-val-length-max="10" data-val-length-min="2" data-val-length="联系人必须包含 2~10 个字符">
                                            <div data-valmsg-for="contactName" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label" for="info_contact_name">
                                            <span class="text-danger">*</span> 学员性别
                                        </label>
                                        <div class="col-md-9">
                                            <input type="text" class="form-control" id="info_contact_name" name="contactName" placeholder="请输入联系人"
                                                   data-val="true" data-val-required="联系人不能为空"
                                                   data-val-length-max="10" data-val-length-min="2" data-val-length="联系人必须包含 2~10 个字符">
                                            <div data-valmsg-for="contactName" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer text-right">
                            <button type="button" class="btn btn-primary save-settings" data-target="#student_push_go" data-toggle="modal">
                                <i class="fa fa-comment"></i> 我要营销
                            </button>
                            <button type="button" class="btn btn-primary save-settings" data-target="#student_push_go" data-toggle="modal">
                                <i class="fa fa-check"></i> 学员转正
                            </button>
                            <button type="button" class="btn btn-primary save-settings">
                                <i class="fa fa-check"></i> 保 存
                            </button>
                        </div>
                        <div class="card-block">
                            <table class="table table-striped table-sm class-list">
                                <thead>
                                <tr>
                                    <th>##</th>
                                    <th>营销时间</th>
                                    <th>营销策略</th>
                                    <th>营销人</th>
                                    <th>营销备注</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <tr data-id="">
                                        <td>1</td>
                                        <td>2018-12-12 11:11:11</td>
                                        <td>电话回访</td>
                                        <td>张思茂</td>
                                        <td>已经约好下次见面</td>
                                    </tr>
                                    <tr data-id="">
                                        <td>1</td>
                                        <td>2018-12-12 11:11:11</td>
                                        <td>电话回访</td>
                                        <td>张思茂</td>
                                        <td>已经约好下次见面</td>
                                    </tr>
                                </tbody>
                            </table>
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
    <c:param name="menu" value="marketing"/>
    <c:param name="subMenu" value="potentialStu"/>
</c:import>
