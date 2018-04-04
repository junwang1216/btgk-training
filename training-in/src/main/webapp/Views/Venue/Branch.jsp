<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.in.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <link href="Content/utils/citySelect/cityLayout.css?v=${static_resource_version}" rel="stylesheet">
    <style type="text/css">
        .form-control-label {
            text-align: right;
            font-weight: bold;
        }
        #id_org_address_select {
            cursor: pointer;
        }
        .branch-list th {
            padding: 0.75rem;
        }
        .branch-list td {
            padding: 0.3rem 0.75rem;
        }
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/venue/branch.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="container-fluid">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-md-5">
                    <div class="card">
                        <div class="card-header">
                            <strong>场馆设置</strong>
                            <small>Settings</small>
                        </div>
                        <div class="card-block">
                            <form id="branch_form" method="post" class="form-horizontal" novalidate onsubmit="return false;">
                                <input type="hidden" id="bch_id" name="id" value="${orgVenues.id}">
                                <div class="form-group row">
                                    <label class="col-md-3 form-control-label" for="bch_venue_name">
                                        <span class="text-danger">*</span> 场馆名称
                                    </label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" id="bch_venue_name" name="venueName" placeholder="请输入场馆名称"
                                               value="${orgVenues.venueName}"
                                               data-val="true" data-val-required="场馆名称不能为空"
                                               data-val-length-max="20" data-val-length-min="2" data-val-length="场馆名称必须包含 2~20 个字符">
                                        <div data-valmsg-for="venueName" data-valmsg-replace="true"></div>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-md-3 form-control-label" for="id_org_address_select">
                                        <span class="text-danger">*</span> 所在地址
                                    </label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" id="id_org_address_select"
                                               name="addressSelect" placeholder="请选择地址" readonly
                                               value="${orgVenues.province}-${orgVenues.city}-${orgVenues.district}"
                                               data-val="true" data-val-required="所在地址不能为空">
                                        <input type="hidden" class="form-control" id="id_org_province" name="province"
                                               value="${orgVenues.province}">
                                        <input type="hidden" class="form-control" id="id_org_city" name="city"
                                               value="${orgVenues.city}">
                                        <input type="hidden" class="form-control" id="id_org_district" name="district"
                                               value="${orgVenues.district}">
                                        <div data-valmsg-for="addressSelect" data-valmsg-replace="true"></div>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-md-3 form-control-label" for="bch_address">
                                        <span class="text-danger">*</span> 详细地址
                                    </label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" id="bch_address" name="address" placeholder="请输入详细地址"
                                               value="${orgVenues.address}"
                                               data-val="true" data-val-required="详细地址不能为空"
                                               data-val-length-max="50" data-val-length-min="2" data-val-length="详细地址必须包含 2~50 个字符">
                                        <div data-valmsg-for="address" data-valmsg-replace="true"></div>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-md-3 form-control-label" for="bch_contact_name">
                                        <span class="text-danger">*</span> 联系人
                                    </label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" id="bch_contact_name" name="contactName" placeholder="请输入联系人"
                                               value="${orgVenues.contactName}"
                                               data-val="true" data-val-required="联系人不能为空"
                                               data-val-length-max="10" data-val-length-min="2" data-val-length="联系人必须包含 2~10 个字符">
                                        <div data-valmsg-for="contactName" data-valmsg-replace="true"></div>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-md-3 form-control-label" for="bch_contact_phone">座机号码</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" id="bch_contact_phone" name="contactPhone" placeholder="请输入座机号码"
                                               value="${orgVenues.contactPhone}"
                                               data-val-regex-pattern="^(0[0-9]{2,3}/-)?([2-9][0-9]{6,7})+(/-[0-9]{1,4})?$" data-val-regex="座机号码格式不正确">
                                    </div>
                                    <div data-valmsg-for="contactPhone" data-valmsg-replace="true"></div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-md-3 form-control-label" for="bch_contact_mobile">
                                        <span class="text-danger">*</span> 手机号码
                                    </label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" id="bch_contact_mobile" name="contactMobile" placeholder="请输入手机号码"
                                               value="${orgVenues.contactMobile}"
                                               data-val="true" data-val-required="手机号码不能为空"
                                               data-val-regex-pattern="^(13[0-9]|15[012356789]|166|17[0-9]|18[02356789]|14[57]|19[89])[0-9]{8}$" data-val-regex="手机号码格式不正确">
                                        <div data-valmsg-for="contactMobile" data-valmsg-replace="true"></div>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-md-3 form-control-label" for="bch_contact_email">联系邮箱</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" id="bch_contact_email" name="contactEmail" placeholder="请输入联系邮箱"
                                               value="${orgVenues.contactEmail}"
                                               data-val-regex-pattern="^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$" data-val-regex="联系邮箱格式不正确">
                                        <div data-valmsg-for="contactEmail" data-valmsg-replace="true"></div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer">
                            <button type="button" class="btn btn-primary save-branch">
                                <i class="fa fa-check"></i> 保 存
                            </button>
                        </div>
                    </div>
                </div>
                <div class="col-md-7">
                    <div class="card">
                        <div class="card-header">
                            <strong>场馆列表</strong>
                            <small>List</small>
                        </div>
                        <div class="card-block">
                            <table class="table table-striped table-sm branch-list">
                                <thead>
                                <tr>
                                    <th>##</th>
                                    <th>场馆名称</th>
                                    <th>联系人</th>
                                    <th>手机号码</th>
                                    <th>状态</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="venue" items="${orgVenuesList}" varStatus="loop">
                                    <tr data-id="${venue.id}">
                                        <td>${loop.index + 1}</td>
                                        <td>${venue.venueName}</td>
                                        <td>${venue.contactName}</td>
                                        <td>${venue.contactMobileSensitive}</td>
                                        <td>
                                            <c:if test="${venue.status == 1}">
                                                <span class="badge badge-success">启用</span>
                                            </c:if>
                                            <c:if test="${venue.status == 2}">
                                                <span class="badge badge-danger">关闭</span>
                                            </c:if>
                                        </td>
                                        <td>
                                            <a href="/admin/venue/branch?venueId=${venue.id}" class="btn btn-sm btn-primary" title="场馆编辑">
                                                <i class="fa fa-pencil"></i> 修改
                                            </a>
                                            <c:if test="${venue.status == 1}">
                                                <button type="button" class="btn btn-sm btn-danger branch-close" title="场馆关闭">
                                                    <i class="fa fa-lock"></i> 关闭
                                                </button>
                                            </c:if>
                                            <c:if test="${venue.status == 2}">
                                                <button type="button" class="btn btn-sm btn-warning branch-open" title="场馆启用">
                                                    <i class="fa fa-unlock"></i> 启用
                                                </button>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div class="card-footer">
                            <a href="/admin/venue/branch" class="btn btn-primary">
                                <i class="fa fa-plus"></i> 添 加
                            </a>
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
    <c:param name="subMenu" value="branch"/>
</c:import>
