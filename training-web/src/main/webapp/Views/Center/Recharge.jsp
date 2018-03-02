<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.web.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script async type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/web_center/recharge.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="page-container">
        <div class="container">
            <div class="row">
                <!-- start of page content -->
                <div class="col-sm-8 page-content">
                    <div class="panel panel-info">
                        <div class="panel-heading"><span>余额充值</span></div>
                        <div class="panel-body">
                            <form class="form-horizontal">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">当前余额</label>
                                    <div class="col-sm-10">
                                        <p class="bg-info" style="padding: 8px 15px;">100.00元</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="recharge_money" class="col-sm-2 control-label">充值金额</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="recharge_money" placeholder="请输入充值金额...">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">充值渠道</label>
                                    <div class="col-sm-10">
                                        <img src="Content/images/web/zhifubao.png" alt="支付宝" class="img-thumbnail" style="width: 240px;">
                                        <img src="Content/images/web/weixin.png" alt="微信" class="img-thumbnail" style="width: 240px;">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button type="submit" class="btn btn-primary">充 值</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="panel panel-info">
                        <div class="panel-heading"><span>充值记录</span></div>
                        <div class="table-responsive">
                            <table class="table table-bordered">
                                <tr>
                                    <td>100.00元</td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>100.00元</td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>100.00元</td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>100.00元</td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>100.00元</td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>100.00元</td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>100.00元</td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>100.00元</td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>100.00元</td>
                                    <td>2013-02-25</td>
                                </tr>
                                <tr>
                                    <td>100.00元</td>
                                    <td>2013-02-25</td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
                </div>
            </div>
        </div>
    </div>
</layout:override>

<c:import url="../Shared/GeneralLayout.jsp">
    <c:param name="nav" value="setting"/>
</c:import>
