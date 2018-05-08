<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.in.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <style type="text/css">
    </style>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script type="text/javascript" src="Content/js/require.js?v=${static_resource_version}"
            data-main="Content/js/app/data/operationFinanceSettings.js?v=${static_resource_version}"></script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="container-fluid">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-header">
                            <strong>流水指标设置</strong>
                            <small>Operation Finance Settings</small>
                        </div>
                        <div class="card-block">
                            <form class="form-horizontal" novalidate onsubmit="return false;">
                                <div class="form-group row">
                                    <div class="col-md-2">
                                        <select class="form-control">
                                            <option>2016年</option>
                                            <option>2017年</option>
                                            <option>2018年</option>
                                            <option>2019年</option>
                                            <option>2020年</option>
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        <select class="form-control">
                                            <option>全年</option>
                                            <option>01月</option>
                                            <option>02月</option>
                                            <option>03月</option>
                                            <option>04月</option>
                                            <option>05月</option>
                                            <option>06月</option>
                                            <option>07月</option>
                                            <option>08月</option>
                                            <option>09月</option>
                                            <option>10月</option>
                                            <option>11月</option>
                                            <option>12月</option>
                                        </select>
                                    </div>
                                    <div class="col-md-3">
                                        <input type="text" class="form-control" placeholder="最低目标">
                                    </div>
                                    <div class="col-md-3">
                                        <input type="text" class="form-control" placeholder="挑战目标">
                                    </div>
                                    <div class="col-md-2">
                                        <button type="button" class="btn btn-primary">
                                            <i class="fa fa-check"></i> 保 存
                                        </button>
                                    </div>
                                </div>
                            </form>
                            <table class="table table-striped table-sm">
                                <thead>
                                <tr>
                                    <th>编号</th>
                                    <th>基准日期</th>
                                    <th>业务类型</th>
                                    <th>最低目标</th>
                                    <th>挑战目标</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>1</td>
                                        <td>2018年</td>
                                        <td>青少年培训</td>
                                        <td>1,000,000元</td>
                                        <td>2,000,000元</td>
                                        <td>
                                            <a href="#" class="btn btn-primary btn-sm" title="修改">
                                                <i class="fa fa-pencil"></i> 修改
                                            </a>
                                            <a href="#" class="btn btn-danger btn-sm" title="删除">
                                                <i class="fa fa-trash"></i> 删除
                                            </a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>2</td>
                                        <td>2018年01月</td>
                                        <td>青少年培训</td>
                                        <td>10,000元</td>
                                        <td>20,000元</td>
                                        <td>
                                            <a href="#" class="btn btn-primary btn-sm" title="修改">
                                                <i class="fa fa-pencil"></i> 修改
                                            </a>
                                            <a href="#" class="btn btn-danger btn-sm" title="删除">
                                                <i class="fa fa-trash"></i> 删除
                                            </a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>3</td>
                                        <td>2018年02月</td>
                                        <td>青少年培训</td>
                                        <td>10,000元</td>
                                        <td>20,000元</td>
                                        <td>
                                            <a href="#" class="btn btn-primary btn-sm" title="修改">
                                                <i class="fa fa-pencil"></i> 修改
                                            </a>
                                            <a href="#" class="btn btn-danger btn-sm" title="删除">
                                                <i class="fa fa-trash"></i> 删除
                                            </a>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="card-footer">
                            <button type="button" class="btn btn-primary save-income-settings">
                                <i class="fa fa-check"></i> 保 存
                            </button>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-header">
                            <strong>确认收入指标设置</strong>
                            <small>Operation Finance Settings</small>
                        </div>
                        <div class="card-block">
                            <form class="form-horizontal" novalidate onsubmit="return false;">
                                <div class="form-group row">
                                    <div class="col-md-2">
                                        <select class="form-control">
                                            <option>2016年</option>
                                            <option>2017年</option>
                                            <option>2018年</option>
                                            <option>2019年</option>
                                            <option>2020年</option>
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        <select class="form-control">
                                            <option>全年</option>
                                            <option>01月</option>
                                            <option>02月</option>
                                            <option>03月</option>
                                            <option>04月</option>
                                            <option>05月</option>
                                            <option>06月</option>
                                            <option>07月</option>
                                            <option>08月</option>
                                            <option>09月</option>
                                            <option>10月</option>
                                            <option>11月</option>
                                            <option>12月</option>
                                        </select>
                                    </div>
                                    <div class="col-md-3">
                                        <input type="text" class="form-control" placeholder="最低目标">
                                    </div>
                                    <div class="col-md-3">
                                        <input type="text" class="form-control" placeholder="挑战目标">
                                    </div>
                                    <div class="col-md-2">
                                        <button type="button" class="btn btn-primary">
                                            <i class="fa fa-check"></i> 保 存
                                        </button>
                                    </div>
                                </div>
                            </form>
                            <table class="table table-striped table-sm">
                                <thead>
                                <tr>
                                    <th>
                                        <div class="btn-toolbar" role="toolbar">
                                            <div class="btn-group">
                                                <button class="btn btn-secondary btn-sm dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                    指标年份
                                                </button>
                                                <div class="dropdown-menu">
                                                    <a class="dropdown-item" href="#">2016</a>
                                                    <a class="dropdown-item" href="#">2017</a>
                                                    <a class="dropdown-item" href="#">2018</a>
                                                    <a class="dropdown-item" href="#">2019</a>
                                                    <a class="dropdown-item" href="#">2020</a>
                                                </div>
                                            </div>
                                        </div>
                                    </th>
                                    <th>业务类型</th>
                                    <th>最低目标</th>
                                    <th>挑战目标</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>全年</td>
                                    <td>青少年培训</td>
                                    <td>1,000,000元</td>
                                    <td>2,000,000元</td>
                                    <td>
                                        <a href="#" class="btn btn-primary btn-sm" title="修改">
                                            <i class="fa fa-pencil"></i> 修改
                                        </a>
                                        <a href="#" class="btn btn-danger btn-sm" title="删除">
                                            <i class="fa fa-trash"></i> 删除
                                        </a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>01月</td>
                                    <td>青少年培训</td>
                                    <td>10,000元</td>
                                    <td>20,000元</td>
                                    <td>
                                        <a href="#" class="btn btn-primary btn-sm" title="修改">
                                            <i class="fa fa-pencil"></i> 修改
                                        </a>
                                        <a href="#" class="btn btn-danger btn-sm" title="删除">
                                            <i class="fa fa-trash"></i> 删除
                                        </a>
                                    </td>
                                </tr>
                                    <td>02月</td>
                                    <td>青少年培训</td>
                                    <td>10,000元</td>
                                    <td>20,000元</td>
                                    <td>
                                        <a href="#" class="btn btn-primary btn-sm" title="修改">
                                            <i class="fa fa-pencil"></i> 修改
                                        </a>
                                        <a href="#" class="btn btn-danger btn-sm" title="删除">
                                            <i class="fa fa-trash"></i> 删除
                                        </a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>03月</td>
                                    <td>青少年培训</td>
                                    <td>10,000元</td>
                                    <td>20,000元</td>
                                    <td>
                                        <a href="#" class="btn btn-primary btn-sm" title="修改">
                                            <i class="fa fa-pencil"></i> 修改
                                        </a>
                                        <a href="#" class="btn btn-danger btn-sm" title="删除">
                                            <i class="fa fa-trash"></i> 删除
                                        </a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>01季度</td>
                                    <td>青少年培训</td>
                                    <td>30,000元</td>
                                    <td>60,000元</td>
                                    <td>
                                        <a href="#" class="btn btn-primary btn-sm" title="修改">
                                            <i class="fa fa-pencil"></i> 修改
                                        </a>
                                        <a href="#" class="btn btn-danger btn-sm" title="删除">
                                            <i class="fa fa-trash"></i> 删除
                                        </a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>04月</td>
                                    <td>青少年培训</td>
                                    <td>10,000元</td>
                                    <td>20,000元</td>
                                    <td>
                                        <a href="#" class="btn btn-primary btn-sm" title="修改">
                                            <i class="fa fa-pencil"></i> 修改
                                        </a>
                                        <a href="#" class="btn btn-danger btn-sm" title="删除">
                                            <i class="fa fa-trash"></i> 删除
                                        </a>
                                    </td>
                                </tr>
                                <td>05月</td>
                                <td>青少年培训</td>
                                <td>10,000元</td>
                                <td>20,000元</td>
                                <td>
                                    <a href="#" class="btn btn-primary btn-sm" title="修改">
                                        <i class="fa fa-pencil"></i> 修改
                                    </a>
                                    <a href="#" class="btn btn-danger btn-sm" title="删除">
                                        <i class="fa fa-trash"></i> 删除
                                    </a>
                                </td>
                                </tr>
                                <tr>
                                    <td>06月</td>
                                    <td>青少年培训</td>
                                    <td>10,000元</td>
                                    <td>20,000元</td>
                                    <td>
                                        <a href="#" class="btn btn-primary btn-sm" title="修改">
                                            <i class="fa fa-pencil"></i> 修改
                                        </a>
                                        <a href="#" class="btn btn-danger btn-sm" title="删除">
                                            <i class="fa fa-trash"></i> 删除
                                        </a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>02季度</td>
                                    <td>青少年培训</td>
                                    <td>30,000元</td>
                                    <td>60,000元</td>
                                    <td>
                                        <a href="#" class="btn btn-primary btn-sm" title="修改">
                                            <i class="fa fa-pencil"></i> 修改
                                        </a>
                                        <a href="#" class="btn btn-danger btn-sm" title="删除">
                                            <i class="fa fa-trash"></i> 删除
                                        </a>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="card-footer">
                            <button type="button" class="btn btn-primary save-expend-settings">
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
    <c:param name="menu" value="finance"/>
    <c:param name="subMenu" value="settings"/>
</c:import>
