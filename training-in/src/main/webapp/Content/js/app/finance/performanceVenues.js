requirejs.config({
    baseUrl: 'Content/',
    paths: {
        "jquery"    : 'bower_components/jquery/dist/jquery',
        "tether"    : 'bower_components/tether/dist/js/tether',
        "bootstrap" : 'bower_components/bootstrap/dist/js/bootstrap',
        "pace"      : 'bower_components/pace/pace',

        "echarts"        : 'bower_components/echarts/dist/echarts.min',
        "echarts-walden" : 'js/widgets/walden',

        "alert"     : 'utils/jqueryAlert/alert/alert',

        "base"      : 'js/widgets/base',
        "override"  : 'js/widgets/override'
    },
    shim: {
        "bootstrap": {
            deps: ["jquery", "override"]
        },
        "echarts-walden": {
            deps: ["echarts"]
        }
    },  // 依赖关系
    waitSeconds: 0,
    urlArgs: '_=' + new Date().getTime()
});

require(['jquery', 'echarts', 'alert', 'bootstrap', 'pace', 'base', 'override', 'echarts-walden'], function ($, echarts, jqueryAlert) {
    'use strict';

    var chartsOption = {
        setTitle: function (text, subText) {
            return {
                text: text || "",
                subtext: subText || "",
                textStyle: {
                    fontSize: 14,
                    fontWeight: "normal"
                },
                top: "4%",
                left: "4%"
            };
        },
        setLegend: function (data, dir) {
            return {
                orient: dir || 'horizontal',
                data: data || [],
                top: "4%",
                right: "4%",
                selectedMode: false
            };
        },
        setGrid: function () {
            return {
                left: '4%',
                right: '4%',
                bottom: '4%',
                containLabel: true
            };
        },
        setXAxis: function () {
            return {
                type: 'value'
            };
        },
        setYAxis: function (data) {
            return {
                type: 'category',
                data: data || []
            };
        },
        setSeries: function (series) {
            return series || [];
        }
    };

    function renderPipelineValueCharts(venueNames, channelNames, venueChannelData, venueData) {
        var cnIndex, vnIndex, vcdIndex, vdIndex;

        var pipelineValues = [];
        for (cnIndex = 0; cnIndex < channelNames.length; cnIndex++) {
            var pipelineValueChannels = [];
            for (vnIndex = 0; vnIndex < venueNames.length; vnIndex++) {
                pipelineValueChannels.push(0);
                for (vcdIndex = 0; vcdIndex < venueChannelData.length; vcdIndex++) {
                    if (venueChannelData[vcdIndex].venueName == venueNames[vnIndex] && venueChannelData[vcdIndex].channelName == channelNames[cnIndex]) {
                        pipelineValueChannels[vnIndex] += (venueChannelData[vcdIndex].pipelineValue);
                        break;
                    }
                }
            }
            pipelineValues.push(pipelineValueChannels);
        }

        var venuePipelineValues = [];
        var venuePipelineTargets = [];
        var venuePipelineChallenges = [];
        for (vnIndex = 0; vnIndex < venueNames.length; vnIndex++) {
            venuePipelineValues.push(0);
            venuePipelineTargets.push(0);
            venuePipelineChallenges.push(0);
            for (vdIndex = 0; vdIndex < venueData.length; vdIndex++) {
                if (venueData[vdIndex].venueName == venueNames[vnIndex]) {
                    venuePipelineValues[vnIndex] += venueData[vdIndex].pipelineValue;
                    venuePipelineTargets[vnIndex] += venueData[vdIndex].pipelineTarget;
                    venuePipelineChallenges[vnIndex] += venueData[vdIndex].pipelineChallenge;
                    break;
                }
            }
        }

        var channelPipelineValues = [];
        for (cnIndex = 0; cnIndex < channelNames.length; cnIndex++) {
            channelPipelineValues.push(0);
            for (vcdIndex = 0; vcdIndex < venueChannelData.length; vcdIndex++) {
                if (venueChannelData[vcdIndex].channelName == channelNames[cnIndex]) {
                    channelPipelineValues[cnIndex] += venueChannelData[vcdIndex].pipelineValue;
                    break;
                }
            }
        }

        // console.log("===== 流水情况 =====");
        // console.log(venueNames);
        // console.log(channelNames);
        // console.log(pipelineValues);
        // console.log(venuePipelineValues);
        // console.log(venuePipelineTargets);
        // console.log(venuePipelineChallenges);
        // console.log(channelPipelineValues);

        var performanceChart1 = echarts.init(document.getElementById('finance_performance_chart1'), "walden");
        var performanceChart1Option = {
            title  : chartsOption.setTitle("流水情况排名（元）"),
            legend : chartsOption.setLegend(channelNames.concat["目标值", "挑战值"]),
            grid   : chartsOption.setGrid(),
            xAxis  : chartsOption.setXAxis(),
            yAxis  : chartsOption.setYAxis(venueNames),
            series : chartsOption.setSeries(
                [{
                    name: channelNames[0],
                    type: 'bar',
                    stack: '总量',
                    data: pipelineValues[0]
                }, {
                    name: channelNames[1],
                    type: 'bar',
                    stack: '总量',
                    data: pipelineValues[1]
                }, {
                    name: channelNames[2],
                    type: 'bar',
                    stack: '总量',
                    data: pipelineValues[2]
                }, {
                    name: channelNames[3],
                    type: 'bar',
                    stack: '总量',
                    data: pipelineValues[3]
                }, {
                    name: "目标值",
                    type: 'line',
                    data: venuePipelineTargets
                }, {
                    name: "挑战值",
                    type: 'line',
                    data: venuePipelineChallenges
                }]
            ),
            tooltip : {
                trigger     : 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                },
                formatter   : function (items) {
                    var showFormatter = "";

                    showFormatter += '<div class="charts-title">' + items[0].name + '</div>';
                    showFormatter += '<ul class="charts-content">' +
                        '<li><label>' + items[0].marker + items[0].seriesName + '</label>: ' + $.moneyFormat(items[0].value, 0, ".", ",") + '元</li>' +
                        '<li><label>' + items[1].marker + items[1].seriesName + '</label>: ' + $.moneyFormat(items[1].value, 0, ".", ",") + '元</li>' +
                        '<li><label>' + items[2].marker + items[2].seriesName + '</label>: ' + $.moneyFormat(items[2].value, 0, ".", ",") + '元</li>' +
                        '<li><label>' + items[3].marker + items[3].seriesName + '</label>: ' + $.moneyFormat(items[3].value, 0, ".", ",") + '元</li>' +
                        '<li><label>' + '流水合计</label>: ' + $.moneyFormat((items[0].value + items[1].value + items[2].value + items[3].value), 0, ".", ",") + '元</li>' +
                        '<li><label>' + '目标值</label>: ' + $.moneyFormat((items[4].value), 0, ".", ",") + '元</li>' +
                        '<li><label>' + '挑战值</label>: ' + $.moneyFormat((items[5].value), 0, ".", ",") + '元</li>' +
                        '</ul>';

                    return showFormatter;
                }
            }
        };
        performanceChart1.setOption(performanceChart1Option);

        var performanceChart4 = echarts.init(document.getElementById('finance_performance_chart4'), "walden");
        var performanceChart4Option = {
            title  : chartsOption.setTitle("流水情况占比", "基地/渠道（百分比）"),
            legend : chartsOption.setLegend(venueNames.concat(channelNames), 'vertical'),
            grid   : chartsOption.setGrid(),
            series : [{
                name :'渠道流水',
                type :'pie',
                radius : ['60%', '80%'],
                data : [
                    {value : channelPipelineValues[0], name : channelNames[0]},
                    {value : channelPipelineValues[1], name : channelNames[1]},
                    {value : channelPipelineValues[2], name : channelNames[2]},
                    {value : channelPipelineValues[3], name : channelNames[3]}
                ]
            }, {
                name :'基地流水',
                type :'pie',
                selectedMode : false,
                radius : [0, '50%'],
                label  : {
                    normal : {
                        position : 'inner'
                    }
                },
                data : [
                    {value : venuePipelineValues[0], name : venueNames[0]},
                    {value : venuePipelineValues[1], name : venueNames[1]},
                    {value : venuePipelineValues[2], name : venueNames[2]},
                    {value : venuePipelineValues[3], name : venueNames[3]}
                ]
            }],
            tooltip : {
                trigger: 'item',
                formatter: function (items) {
                    return items.seriesName + "<br />" + items.marker + items.name + ": " + items.value + " (" + items.percent + "%)";
                }
            }
        };
        performanceChart4.setOption(performanceChart4Option);
        performanceChart4.on('click', function (params) {
            var venueName = params.name;
            if (params.data.selected) {
                venueName = "";
            }

            var venueChannelPipelineValues = [];
            for (cnIndex = 0; cnIndex < channelNames.length; cnIndex++) {
                venueChannelPipelineValues.push(0);
                for (vcdIndex = 0; vcdIndex < venueChannelData.length; vcdIndex++) {
                    if (venueChannelData[vcdIndex].channelName == channelNames[cnIndex] && (!venueName || venueChannelData[vcdIndex].venueName == venueName)) {
                        venueChannelPipelineValues[cnIndex] += venueChannelData[vcdIndex].pipelineValue;
                        break;
                    }
                }
            }

            performanceChart4Option.series[0].data = [
                {value : venueChannelPipelineValues[0], name : channelNames[0]},
                {value : venueChannelPipelineValues[1], name : channelNames[1]},
                {value : venueChannelPipelineValues[2], name : channelNames[2]},
                {value : venueChannelPipelineValues[3], name : channelNames[3]}
            ];
            performanceChart4Option.series[1].data = [
                {value : venuePipelineValues[0], name : venueNames[0], selected: venueNames[0] == venueName},
                {value : venuePipelineValues[1], name : venueNames[1], selected: venueNames[1] == venueName},
                {value : venuePipelineValues[2], name : venueNames[2], selected: venueNames[2] == venueName},
                {value : venuePipelineValues[3], name : venueNames[3], selected: venueNames[3] == venueName}
            ];
            performanceChart4.setOption(performanceChart4Option);
        });
    }

    function renderIncomeValueCharts(venueNames, channelNames, venueChannelData, venueData) {
        var cnIndex, vnIndex, vdIndex, vcdIndex;

        var incomeValues = [];
        for (cnIndex = 0; cnIndex < channelNames.length; cnIndex++) {
            var incomeValueChannels = [];
            for (vnIndex = 0; vnIndex < venueNames.length; vnIndex++) {
                incomeValueChannels.push(0);
                for (vcdIndex = 0; vcdIndex < venueChannelData.length; vcdIndex++) {
                    if (venueChannelData[vcdIndex].venueName == venueNames[vnIndex] && venueChannelData[vcdIndex].channelName == channelNames[cnIndex]) {
                        incomeValueChannels[vnIndex] += (venueChannelData[vcdIndex].incomeValue);
                        break;
                    }
                }
            }
            incomeValues.push(incomeValueChannels);
        }

        var venueIncomeValues = [];
        var venueIncomeTargets = [];
        var venueIncomeChallenges = [];
        for (vnIndex = 0; vnIndex < venueNames.length; vnIndex++) {
            venueIncomeValues.push(0);
            venueIncomeTargets.push(0);
            venueIncomeChallenges.push(0);
            for (vdIndex = 0; vdIndex < venueData.length; vdIndex++) {
                if (venueData[vdIndex].venueName == venueNames[vnIndex]) {
                    venueIncomeValues[vnIndex] += venueData[vdIndex].incomeValue;
                    venueIncomeTargets[vnIndex] += venueData[vdIndex].incomeTarget;
                    venueIncomeChallenges[vnIndex] += venueData[vdIndex].incomeChallenge;
                    break;
                }
            }
        }

        var channelIncomeValues = [];
        for (cnIndex = 0; cnIndex < channelNames.length; cnIndex++) {
            channelIncomeValues.push(0);
            for (vcdIndex = 0; vcdIndex < venueChannelData.length; vcdIndex++) {
                if (venueChannelData[vcdIndex].channelName == channelNames[cnIndex]) {
                    channelIncomeValues[cnIndex] += venueChannelData[vcdIndex].incomeValue;
                    break;
                }
            }
        }

        // console.log("===== 确认收入情况 =====");
        // console.log(venueNames);
        // console.log(channelNames);
        // console.log(incomeValues);
        // console.log(venueIncomeValues);
        // console.log(venueIncomeTargets);
        // console.log(venueIncomeChallenges);
        // console.log(channelIncomeValues);

        var performanceChart2 = echarts.init(document.getElementById('finance_performance_chart2'), "walden");
        var performanceChart2Option = {
            title  : chartsOption.setTitle("确认收入情况排名（元）"),
            legend : chartsOption.setLegend(channelNames.concat["目标值", "挑战值"]),
            grid   : chartsOption.setGrid(),
            xAxis  : chartsOption.setXAxis(),
            yAxis  : chartsOption.setYAxis(venueNames),
            series : chartsOption.setSeries(
                [{
                    name: channelNames[0],
                    type: 'bar',
                    stack: '总量',
                    data: incomeValues[0]
                }, {
                    name: channelNames[1],
                    type: 'bar',
                    stack: '总量',
                    data: incomeValues[1]
                }, {
                    name: channelNames[2],
                    type: 'bar',
                    stack: '总量',
                    data: incomeValues[2]
                }, {
                    name: channelNames[3],
                    type: 'bar',
                    stack: '总量',
                    data: incomeValues[3]
                }, {
                    name: "目标值",
                    type: 'line',
                    data: venueIncomeTargets
                }, {
                    name: "挑战值",
                    type: 'line',
                    data: venueIncomeChallenges
                }]
            ),
            tooltip : {
                trigger     : 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                },
                formatter   : function (items) {
                    var showFormatter = "";

                    showFormatter += '<div class="charts-title">' + items[0].name + '</div>';
                    showFormatter += '<ul class="charts-content">' +
                        '<li><label>' + items[0].marker + items[0].seriesName + '</label>: ' + $.moneyFormat(items[0].value, 0, ".", ",") + '元</li>' +
                        '<li><label>' + items[1].marker + items[1].seriesName + '</label>: ' + $.moneyFormat(items[1].value, 0, ".", ",") + '元</li>' +
                        '<li><label>' + items[2].marker + items[2].seriesName + '</label>: ' + $.moneyFormat(items[2].value, 0, ".", ",") + '元</li>' +
                        '<li><label>' + items[3].marker + items[3].seriesName + '</label>: ' + $.moneyFormat(items[3].value, 0, ".", ",") + '元</li>' +
                        '<li><label>' + '收入合计</label>: ' + $.moneyFormat((items[0].value + items[1].value + items[2].value + items[3].value), 0, ".", ",") + '元</li>' +
                        '<li><label>' + '目标值</label>: ' + $.moneyFormat((items[4].value), 0, ".", ",") + '元</li>' +
                        '<li><label>' + '挑战值</label>: ' + $.moneyFormat((items[5].value), 0, ".", ",") + '元</li>' +
                        '</ul>';

                    return showFormatter;
                }
            }
        };
        performanceChart2.setOption(performanceChart2Option);

        var performanceChart3 = echarts.init(document.getElementById('finance_performance_chart3'), "walden");
        var performanceChart3Option = {
            title  : chartsOption.setTitle("确认收入情况占比", "基地/渠道（百分比）"),
            legend : chartsOption.setLegend(venueNames.concat(channelNames), 'vertical'),
            grid   : chartsOption.setGrid(),
            series : [{
                name :'渠道收入',
                type :'pie',
                radius : ['60%', '80%'],
                data : [
                    {value : channelIncomeValues[0], name : channelNames[0]},
                    {value : channelIncomeValues[1], name : channelNames[1]},
                    {value : channelIncomeValues[2], name : channelNames[2]},
                    {value : channelIncomeValues[3], name : channelNames[3]}
                ]
            }, {
                name :'基地收入',
                type :'pie',
                selectedMode : false,
                radius : [0, '50%'],
                label  : {
                    normal : {
                        position : 'inner'
                    }
                },
                data : [
                    {value : venueIncomeValues[0], name : venueNames[0]},
                    {value : venueIncomeValues[1], name : venueNames[1]},
                    {value : venueIncomeValues[2], name : venueNames[2]},
                    {value : venueIncomeValues[3], name : venueNames[3]}
                ]
            }],
            tooltip : {
                trigger: 'item',
                formatter: function (items) {
                    return items.seriesName + "<br />" + items.marker + items.name + ": " + items.value + " (" + items.percent + "%)";
                }
            }
        };
        performanceChart3.setOption(performanceChart3Option);
        performanceChart3.on('click', function (params) {
            var venueName = params.name;
            if (params.data.selected) {
                venueName = "";
            }

            var venueChannelIncomeValues = [];
            for (cnIndex = 0; cnIndex < channelNames.length; cnIndex++) {
                venueChannelIncomeValues.push(0);
                for (vcdIndex = 0; vcdIndex < venueChannelData.length; vcdIndex++) {
                    if (venueChannelData[vcdIndex].channelName == channelNames[cnIndex] && (!venueName || venueChannelData[vcdIndex].venueName == venueName)) {
                        venueChannelIncomeValues[cnIndex] += venueChannelData[vcdIndex].incomeValue;
                        break;
                    }
                }
            }

            performanceChart3Option.series[0].data = [
                {value : venueChannelIncomeValues[0], name : channelNames[0]},
                {value : venueChannelIncomeValues[1], name : channelNames[1]},
                {value : venueChannelIncomeValues[2], name : channelNames[2]},
                {value : venueChannelIncomeValues[3], name : channelNames[3]}
            ];
            performanceChart3Option.series[1].data = [
                {value : venueIncomeValues[0], name : venueNames[0], selected: venueNames[0] == venueName},
                {value : venueIncomeValues[1], name : venueNames[1], selected: venueNames[1] == venueName},
                {value : venueIncomeValues[2], name : venueNames[2], selected: venueNames[2] == venueName},
                {value : venueIncomeValues[3], name : venueNames[3], selected: venueNames[3] == venueName}
            ];
            performanceChart3.setOption(performanceChart3Option);
        });
    }

    function renderRegisterCountCharts(venueNames, venueData) {
        var vnIndex, vdIndex;

        var classCounts = [], classNoCounts = [];
        for (vnIndex = 0; vnIndex < venueNames.length; vnIndex++) {
            classCounts.push(0);
            classNoCounts.push(0);
            for (vdIndex = 0; vdIndex < venueData.length; vdIndex++) {
                if (venueData[vdIndex].venueName == venueNames[vnIndex]) {
                    classCounts[vnIndex] += (venueData[vdIndex].classCount);
                    classNoCounts[vnIndex] += (venueData[vdIndex].accessCount - venueData[vdIndex].classCount);
                    break;
                }
            }
        }

        // console.log("===== 在册人数情况 =====");
        // console.log(venueNames);
        // console.log(classCounts);
        // console.log(classNoCounts);

        var performanceChart5 = echarts.init(document.getElementById('finance_performance_chart5'), "walden");
        var performanceChart5Option = {
            title  : chartsOption.setTitle("到课率排名（百分比）"),
            legend : chartsOption.setLegend(["到课", "未到课"]),
            grid   : chartsOption.setGrid(),
            xAxis  : chartsOption.setXAxis(),
            yAxis  : chartsOption.setYAxis(venueNames),
            series : chartsOption.setSeries(
                [{
                    name: "到课",
                    type: 'bar',
                    stack: '总量',
                    data: classCounts
                }, {
                    name: "未到课",
                    type: 'bar',
                    stack: '总量',
                    data: classNoCounts
                }]
            ),
            tooltip : {
                trigger     : 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                },
                formatter   : function (items) {
                    var showFormatter = "";

                    showFormatter += '<div class="charts-title">' + items[0].name + '</div>';
                    showFormatter += '<ul class="charts-content">' +
                        '<li><label>' + items[0].marker + items[0].seriesName + '</label>: ' + $.moneyFormat(items[0].value, 0, ".", ",") + '人</li>' +
                        '<li><label>' + items[1].marker + items[1].seriesName + '</label>: ' + $.moneyFormat(items[1].value, 0, ".", ",") + '人</li>' +
                        '<li><label>' + '到课率</label>: ' + $.moneyFormat(((items[0].value * 100) / (items[0].value + items[1].value)), 0, ".", ",") + '%</li>' +
                        '</ul>';

                    return showFormatter;
                }
            }
        };
        performanceChart5.setOption(performanceChart5Option);
    }

    function renderAccessCountCharts(venueNames, venueData) {
        var vnIndex, vdIndex;

        var businessCounts = [], businessNoCounts = [];
        for (vnIndex = 0; vnIndex < venueNames.length; vnIndex++) {
            businessCounts.push(0);
            businessNoCounts.push(0);
            for (vdIndex = 0; vdIndex < venueData.length; vdIndex++) {
                if (venueData[vdIndex].venueName == venueNames[vnIndex]) {
                    businessCounts[vnIndex] += (venueData[vdIndex].businessCount);
                    businessNoCounts[vnIndex] += (venueData[vdIndex].accessCount - venueData[vdIndex].businessCount);
                    break;
                }
            }
        }

        // console.log("===== 访问人数情况 =====");
        // console.log(venueNames);
        // console.log(businessCounts);
        // console.log(businessNoCounts);

        var performanceChart6 = echarts.init(document.getElementById('finance_performance_chart6'), "walden");
        var performanceChart6Option = {
            title  : chartsOption.setTitle("成交率排名（百分比）"),
            legend : chartsOption.setLegend(["成交数", "未成交数"]),
            grid   : chartsOption.setGrid(),
            xAxis  : chartsOption.setXAxis(),
            yAxis  : chartsOption.setYAxis(venueNames),
            series : chartsOption.setSeries(
                [{
                    name: "成交数",
                    type: 'bar',
                    stack: '总量',
                    data: businessCounts
                }, {
                    name: "未成交数",
                    type: 'bar',
                    stack: '总量',
                    data: businessNoCounts
                }]
            ),
            tooltip : {
                trigger     : 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                },
                formatter   : function (items) {
                    var showFormatter = "";

                    showFormatter += '<div class="charts-title">' + items[0].name + '</div>';
                    showFormatter += '<ul class="charts-content">' +
                        '<li><label>' + items[0].marker + items[0].seriesName + '</label>: ' + $.moneyFormat(items[0].value, 0, ".", ",") + '人</li>' +
                        '<li><label>' + items[1].marker + items[1].seriesName + '</label>: ' + $.moneyFormat(items[1].value, 0, ".", ",") + '人</li>' +
                        '<li><label>' + '成交率</label>: ' + $.moneyFormat(((items[0].value * 100) / (items[0].value + items[1].value)), 0, ".", ",") + '%</li>' +
                        '</ul>';

                    return showFormatter;
                }
            }
        };
        performanceChart6.setOption(performanceChart6Option);
    }

    function renderNullCountCharts(venueNames, venueData) {
        var vnIndex, vdIndex;

        var nullCounts = [], nullNoCounts = [];
        for (vnIndex = 0; vnIndex < venueNames.length; vnIndex++) {
            nullCounts.push(0);
            nullNoCounts.push(0);
            for (vdIndex = 0; vdIndex < venueData.length; vdIndex++) {
                if (venueData[vdIndex].venueName == venueNames[vnIndex]) {
                    nullCounts[vnIndex] += (venueData[vdIndex].nullCount);
                    nullNoCounts[vnIndex] += (venueData[vdIndex].nullTotalCount - venueData[vdIndex].nullCount);
                    break;
                }
            }
        }

        // console.log("===== 空闲场地情况 =====");
        // console.log(venueNames);
        // console.log(nullCounts);
        // console.log(nullNoCounts);

        var performanceChart5 = echarts.init(document.getElementById('finance_performance_chart5'), "walden");
        var performanceChart5Option = {
            title  : chartsOption.setTitle("闲时段占用率排名（百分比）"),
            legend : chartsOption.setLegend(["占用", "未占用"]),
            grid   : chartsOption.setGrid(),
            xAxis  : chartsOption.setXAxis(),
            yAxis  : chartsOption.setYAxis(venueNames),
            series : chartsOption.setSeries(
                [{
                    name: "占用",
                    type: 'bar',
                    stack: '总量',
                    data: nullCounts
                }, {
                    name: "未占用",
                    type: 'bar',
                    stack: '总量',
                    data: nullNoCounts
                }]
            ),
            tooltip : {
                trigger     : 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                },
                formatter   : function (items) {
                    var showFormatter = "";

                    showFormatter += '<div class="charts-title">' + items[0].name + '</div>';
                    showFormatter += '<ul class="charts-content">' +
                        '<li><label>' + items[0].marker + items[0].seriesName + '</label>: ' + $.moneyFormat(items[0].value, 0, ".", ",") + '时</li>' +
                        '<li><label>' + items[1].marker + items[1].seriesName + '</label>: ' + $.moneyFormat(items[1].value, 0, ".", ",") + '时</li>' +
                        '<li><label>' + '占用率</label>: ' + $.moneyFormat(((items[0].value * 100) / (items[0].value + items[1].value)), 0, ".", ",") + '%</li>' +
                        '</ul>';

                    return showFormatter;
                }
            }
        };
        performanceChart5.setOption(performanceChart5Option);
    }

    function renderHotCountCharts(venueNames, venueData) {
        var vnIndex, vdIndex;

        var hotCounts = [], hotNoCounts = [];
        for (vnIndex = 0; vnIndex < venueNames.length; vnIndex++) {
            hotCounts.push(0);
            hotNoCounts.push(0);
            for (vdIndex = 0; vdIndex < venueData.length; vdIndex++) {
                if (venueData[vdIndex].venueName == venueNames[vnIndex]) {
                    hotCounts[vnIndex] += (venueData[vdIndex].hotCount);
                    hotNoCounts[vnIndex] += (venueData[vdIndex].hotTotalCount - venueData[vdIndex].hotCount);
                    break;
                }
            }
        }

        // console.log("===== 访问人数情况 =====");
        // console.log(venueNames);
        // console.log(hotCounts);
        // console.log(hotNoCounts);

        var performanceChart6 = echarts.init(document.getElementById('finance_performance_chart6'), "walden");
        var performanceChart6Option = {
            title  : chartsOption.setTitle("忙时段占用率排名（百分比）"),
            legend : chartsOption.setLegend(["占用", "未占用"]),
            grid   : chartsOption.setGrid(),
            xAxis  : chartsOption.setXAxis(),
            yAxis  : chartsOption.setYAxis(venueNames),
            series : chartsOption.setSeries(
                [{
                    name: "占用",
                    type: 'bar',
                    stack: '总量',
                    data: hotCounts
                }, {
                    name: "未占用",
                    type: 'bar',
                    stack: '总量',
                    data: hotNoCounts
                }]
            ),
            tooltip : {
                trigger     : 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                },
                formatter   : function (items) {
                    var showFormatter = "";

                    showFormatter += '<div class="charts-title">' + items[0].name + '</div>';
                    showFormatter += '<ul class="charts-content">' +
                        '<li><label>' + items[0].marker + items[0].seriesName + '</label>: ' + $.moneyFormat(items[0].value, 0, ".", ",") + '时</li>' +
                        '<li><label>' + items[1].marker + items[1].seriesName + '</label>: ' + $.moneyFormat(items[1].value, 0, ".", ",") + '时</li>' +
                        '<li><label>' + '占用率</label>: ' + $.moneyFormat(((items[0].value * 100) / (items[0].value + items[1].value)), 0, ".", ",") + '%</li>' +
                        '</ul>';

                    return showFormatter;
                }
            }
        };
        performanceChart6.setOption(performanceChart6Option);
    }
    
    function loadFinanceVenueData() {
        var typeTime = $("[name='total_students_type']:checked").val();
        var busType = $("[name='total_bus_type']:checked").val();

        $.getJSON('/admin/finance/getFinancePerformanceForVenues', {typeTime : typeTime, busType : busType}, function (res) {
            var data = res.data;

            if (res.code == 1) {
                if (!data.orgFinanceVenuesList || !data.orgFinanceChannelList || !data.orgFinanceDataChannelList || !data.orgFinanceDataVenueList) {
                    return jqueryAlert({
                        'icon'      : '/Content/images/icon-error.png',
                        'content'   : "加载场馆基地数据失败，请重试",
                        'closeTime' : 2000,
                        'modal'        : true,
                        'isModalClose' : true
                    });
                }

                var venueNames = [];
                !!data.orgFinanceVenuesList && data.orgFinanceVenuesList.forEach(function (item) {
                    venueNames.push(item.venueName);
                });
                var channelNames = [];
                !!data.orgFinanceChannelList && data.orgFinanceChannelList.forEach(function (item) {
                    channelNames.push(item);
                });

                renderPipelineValueCharts(venueNames, channelNames, data.orgFinanceDataChannelList, data.orgFinanceDataVenueList);
                renderIncomeValueCharts(venueNames, channelNames, data.orgFinanceDataChannelList, data.orgFinanceDataVenueList);

                if (busType == 1) {
                    renderRegisterCountCharts(venueNames, data.orgFinanceDataVenueList);
                    renderAccessCountCharts(venueNames, data.orgFinanceDataVenueList);
                } else {
                    renderNullCountCharts(venueNames, data.orgFinanceDataVenueList);
                    renderHotCountCharts(venueNames, data.orgFinanceDataVenueList);
                }
            } else {
                jqueryAlert({
                    'icon'      : '/Content/images/icon-error.png',
                    'content'   : "加载场馆基地数据错误，请重试",
                    'closeTime' : 2000,
                    'modal'        : true,
                    'isModalClose' : true
                });
            }
        });
    }
    loadFinanceVenueData();

    $("[name='total_students_type']").on("change", function (e) {
        e.preventDefault();

        window.location.href = "/admin/finance/performance?typeTime=" +  $("[name='total_students_type']:checked").val() +
            "&busType=" + $("#current_bus_type").val() + "&venueId=-1";
    });

    $("[name='total_bus_type']").on("change", function (e) {
        e.preventDefault();

        window.location.href = "/admin/finance/performance?busType=" + $("[name='total_bus_type']:checked").val() + "&venueId=-1";
    });
});
