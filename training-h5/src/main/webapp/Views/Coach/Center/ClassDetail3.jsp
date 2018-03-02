<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.training.h5.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.sports.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <link href="/Content/dest/css/center/detail.min.css?ver=${static_resource_version}" rel="stylesheet">
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script src="https://cdn.bootcss.com/echarts/3.8.4/echarts.min.js"></script>
    <script>
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据
        var option = {
            radar: [
                {
                    indicator: [
                        {text: '跑动', max: 100},
                        {text: '弹跳', max: 100},
                        {text: '身体对抗', max: 100},
                        {text: '传球', max: 100},
                        {text: '运球', max: 100},
                        {text: '篮板', max: 100},
                        {text: '盖帽', max: 100},
                        {text: '抢断', max: 100},
                        {text: '中投', max: 100},
                        {text: '三分球', max: 100},
                        {text: '灌篮', max: 100},
                        {text: '近投', max: 100}
                    ],
                    radius: '75%',
                    center: ['50%','50%'],
                    silent: true
                }
            ],
            series: [
                {
                    type: 'radar',
                    itemStyle: {normal: {areaStyle: {type: 'default'}}},
                    data: [
                        {
                            value: [37, 90, 75, 44, 35, 91, 49, 37, 82, 34, 74, 69],
                            name: '个人值'
                        }
                    ]
                }
            ]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    </script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="page__bd">
        <div id="main" class="user-radar"></div>

        <div class="weui-cells__title">能力测试列表（值/最大值）</div>
        <div class="weui-cells">
            <div class="weui-cell">
                <div class="weui-cell__bd"><p>跑动</p></div>
                <div class="weui-cell__ft">37/100</div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__bd"><p>弹跳</p></div>
                <div class="weui-cell__ft">90/100</div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__bd"><p>身体对抗</p></div>
                <div class="weui-cell__ft">75/100</div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__bd"><p>传球</p></div>
                <div class="weui-cell__ft">44/100</div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__bd"><p>运球</p></div>
                <div class="weui-cell__ft">35/100</div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__bd"><p>篮板</p></div>
                <div class="weui-cell__ft">91/100</div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__bd"><p>盖帽</p></div>
                <div class="weui-cell__ft">49/100</div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__bd"><p>抢断</p></div>
                <div class="weui-cell__ft">37/100</div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__bd"><p>中投</p></div>
                <div class="weui-cell__ft">82/100</div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__bd"><p>三分球</p></div>
                <div class="weui-cell__ft">34/100</div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__bd"><p>灌篮</p></div>
                <div class="weui-cell__ft">74/100</div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__bd"><p>近投</p></div>
                <div class="weui-cell__ft">69/100</div>
            </div>
        </div>

        <div class="weui-cells__title">能力测试评价</div>
        <div class="weui-cells weui-cells_form">
            <div class="weui-cell">
                <div class="weui-cell__bd">
                    <p>我的结业评价我的结业评价我的结业评价，我的结业评价我的结业评价。</p>
                </div>
            </div>
        </div>
    </div>
</layout:override>

<c:import url="../../Shared/GeneralLayout.jsp">
    <c:param name="title" value="结业评价"/>
</c:import>
