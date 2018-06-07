<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.in.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <link href="Content/utils/citySelect/cityLayout.css?v=${static_resource_version}"
          rel="stylesheet">
    <style type="text/css">
        .form-control-label {
            text-align: right;
            font-weight: bold;
        }
        #id_org_address_select {
            cursor: pointer;
        }
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/venue/settings.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="container-fluid">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <strong>基础资料</strong>
                            <small>Settings</small>
                            <label class="switch switch-sm switch-text switch-info float-right mb-0">
                                <input type="checkbox" class="switch-input" name="isEdit" id="info_is_edit">
                                <span class="switch-label" data-on="是" data-off="否"></span>
                                <span class="switch-handle"></span>
                            </label>
                        </div>
                        <div class="card-block">
                            <form id="settings_form" action="" method="post" class="form-horizontal row" novalidate onclick="return false;">
                                <input type="hidden" id="info_id" name="id" value="${information.id}">
                                <div class="col-md-6">
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label" for="info_tag_name">
                                            <span class="text-danger">*</span> 机构名称
                                        </label>
                                        <div class="col-md-9">
                                            <input type="text" id="info_tag_name" name="orgName" class="form-control" placeholder="请输入机构名称"
                                                   value="${information.orgName}"
                                                   data-val="true" data-val-required="机构名称不能为空"
                                                   data-val-length-max="30" data-val-length-min="2" data-val-length="机构名称必须包含 2~30 个字符">
                                            <div data-valmsg-for="orgName" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label" for="id_org_address_select">
                                            <span class="text-danger">*</span> 所在地址
                                        </label>
                                        <div class="col-md-9">
                                            <input type="text" class="form-control" id="id_org_address_select"
                                                   name="addressSelect" placeholder="请选择地址" readonly
                                                   value="${information.province}-${information.city}-${information.district}"
                                                   data-val="true" data-val-required="所在地址不能为空">
                                            <input type="hidden" class="form-control" id="id_org_province" name="province"
                                                   value="${information.province}">
                                            <input type="hidden" class="form-control" id="id_org_city" name="city"
                                                   value="${information.city}">
                                            <input type="hidden" class="form-control" id="id_org_district" name="district"
                                                   value="${information.district}">
                                            <div data-valmsg-for="addressSelect" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label" for="info_address">
                                            <span class="text-danger">*</span> 详细地址
                                        </label>
                                        <div class="col-md-9">
                                            <input type="text" class="form-control" id="info_address" name="address" placeholder="请输入详细地址"
                                                   value="${information.address}"
                                                   data-val="true" data-val-required="详细地址不能为空"
                                                   data-val-length-max="50" data-val-length-min="2" data-val-length="详细地址必须包含 2~50 个字符">
                                            <div data-valmsg-for="address" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label" for="info_org_note">
                                            <span class="text-danger">*</span> 机构简介
                                        </label>
                                        <div class="col-md-9">
                                            <textarea rows="9" class="form-control" id="info_org_note" name="orgNote" placeholder="一句话介绍机构简介"
                                                      data-val="true" data-val-required="机构简介不能为空"
                                                      data-val-length-max="200" data-val-length-min="10" data-val-length="机构简介必须包含 10~200 个字符">${information.orgNote}</textarea>
                                            <div data-valmsg-for="orgNote" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label" for="info_contact_name">
                                            <span class="text-danger">*</span> 联系人
                                        </label>
                                        <div class="col-md-9">
                                            <input type="text" class="form-control" id="info_contact_name" name="contactName" placeholder="请输入联系人"
                                                   value="${information.contactName}"
                                                   data-val="true" data-val-required="联系人不能为空"
                                                   data-val-length-max="10" data-val-length-min="2" data-val-length="联系人必须包含 2~10 个字符">
                                            <div data-valmsg-for="contactName" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label" for="info_contact_phone">座机号码</label>
                                        <div class="col-md-9">
                                            <input type="text" class="form-control" id="info_contact_phone" name="contactPhone" placeholder="请输入座机号码"
                                                   value="${information.contactPhone}"
                                                   data-val-regex-pattern="^(0[0-9]{2,3}/-)?([2-9][0-9]{6,7})+(/-[0-9]{1,4})?$" data-val-regex="座机号码格式不正确">
                                        </div>
                                        <div data-valmsg-for="contactPhone" data-valmsg-replace="true"></div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label" for="info_contact_mobile">
                                            <span class="text-danger">*</span> 手机号码
                                        </label>
                                        <div class="col-md-9">
                                            <input type="text" class="form-control" id="info_contact_mobile" name="contactMobile" placeholder="请输入手机号码"
                                                   value="${information.contactMobile}"
                                                   data-val="true" data-val-required="手机号码不能为空"
                                                   data-val-regex-pattern="^(13[0-9]|15[012356789]|166|17[0-9]|18[012356789]|14[57]|19[89])[0-9]{8}$" data-val-regex="手机号码格式不正确">
                                            <div data-valmsg-for="contactMobile" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label" for="info_contact_email">联系邮箱</label>
                                        <div class="col-md-9">
                                            <input type="text" class="form-control" id="info_contact_email" name="contactEmail" placeholder="请输入联系邮箱"
                                                   value="${information.contactEmail}"
                                                   data-val-regex-pattern="^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$" data-val-regex="联系邮箱格式不正确">
                                            <div data-valmsg-for="contactEmail" data-valmsg-replace="true"></div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer">
                            <button type="button" class="btn btn-primary save-settings">
                                <i class="fa fa-check"></i> 保 存
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
    <c:param name="menu" value="venue"/>
    <c:param name="subMenu" value="settings"/>
</c:import>
