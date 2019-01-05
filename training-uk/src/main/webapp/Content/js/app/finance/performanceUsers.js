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
                type: 'value',
                silent: false,
                axisLine: {onZero: true}
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

    function loadOrgFinanceDataChannelData() {
        var typeTime = $("[name='total_students_type']:checked").val();
        var busType = $("[name='total_bus_type']:checked").val();
        var venueId = $("#current_venue_id").val();

        $.getJSON('/admin/finance/getFinancePerformanceChannelForUsers', {typeTime : typeTime, busType : busType, venueId: venueId}, function (res) {
            var data = res.data;

            if (res.code == 1) {
                $(".venue-title").text(data.venueName || "所有基地");

                if (!data.orgFinanceUsersList || !data.orgFinanceChannelList || !data.orgFinanceDataChannelList || !data.orgFinanceDataVenueList) {
                    return jqueryAlert({
                        'icon'      : '/Content/images/icon-error.png',
                        'content'   : "加载场馆基地数据失败，请重试",
                        'closeTime' : 2000,
                        'modal'        : true,
                        'isModalClose' : true
                    });
                }

                var realNames = [];
                !!data.orgFinanceUsersList && data.orgFinanceUsersList.forEach(function (item) {
                    realNames.push(item.realName);
                });
                var channelNames = [];
                !!data.orgFinanceChannelList && data.orgFinanceChannelList.forEach(function (item) {
                    channelNames.push(item.enumNote);
                });
                channelNames.push("无");

                renderPipelineValueCharts(realNames, channelNames, data.orgFinanceDataChannelList, data.orgFinanceDataVenueList);

                renderAccessCountCharts(realNames, data.orgFinanceDataVenueList);
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
    loadOrgFinanceDataChannelData();

    function loadOrgFinanceIncomeData() {
        var typeTime = $("[name='total_students_type']:checked").val();
        var busType = $("[name='total_bus_type']:checked").val();
        var venueId = $("#current_venue_id").val();

        $.getJSON('/admin/finance/getFinancePerformanceIncomeForUsers', {typeTime : typeTime, busType : busType, venueId: venueId}, function (res) {
            var data = res.data;

            if (res.code == 1) {
                if (!data.orgFinanceUsersList || !data.orgFinanceIncomeList || !data.orgFinanceDataIncomeTypeList || !data.orgFinanceDataVenueList) {
                    return jqueryAlert({
                        'icon'      : '/Content/images/icon-error.png',
                        'content'   : "加载场馆基地数据失败，请重试",
                        'closeTime' : 2000,
                        'modal'        : true,
                        'isModalClose' : true
                    });
                }

                var realNames = [];
                !!data.orgFinanceUsersList && data.orgFinanceUsersList.forEach(function (item) {
                    realNames.push(item.realName);
                });
                var incomeTypes = [];
                !!data.orgFinanceIncomeList && data.orgFinanceIncomeList.forEach(function (item) {
                    incomeTypes.push(item.enumNote);
                });
                incomeTypes.push("无");

                renderIncomeValueCharts(realNames, incomeTypes, data.orgFinanceDataIncomeTypeList, data.orgFinanceDataVenueList);

                renderRegisterCountCharts(realNames, data.orgFinanceDataVenueList);
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
    loadOrgFinanceIncomeData();

    function loadOrgFinanceTimesData() {
        var typeTime = $("[name='total_students_type']:checked").val();
        var busType = $("[name='total_bus_type']:checked").val();
        var venueId = $("#current_venue_id").val();

        $.getJSON('/admin/finance/getFinancePerformanceTimesForUsers', {typeTime : typeTime, busType : busType, venueId: venueId}, function (res) {
            var data = res.data;

            if (res.code == 1) {
                if (!data.orgFinanceUsersList || !data.orgFinanceDataVenueList) {
                    return jqueryAlert({
                        'icon'      : '/Content/images/icon-error.png',
                        'content'   : "加载场馆基地数据失败，请重试",
                        'closeTime' : 2000,
                        'modal'        : true,
                        'isModalClose' : true
                    });
                }

                var realNames = [];
                !!data.orgFinanceUsersList && data.orgFinanceUsersList.forEach(function (item) {
                    realNames.push(item.realName);
                });

                renderNullCountCharts(realNames, data.orgFinanceDataVenueList);
                renderHotCountCharts(realNames, data.orgFinanceDataVenueList);
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
    loadOrgFinanceTimesData();

    // 流水
    function renderPipelineValueCharts(realNames, channelNames, userChannelData, userData) {
        var cnIndex, rnIndex, ucdIndex, udIndex;

        var pipelineValues = [];
        for (cnIndex = 0; cnIndex < channelNames.length; cnIndex++) {
            var pipelineValueChannels = [];
            for (rnIndex = 0; rnIndex < realNames.length; rnIndex++) {
                pipelineValueChannels.push(0);
                for (ucdIndex = 0; ucdIndex < userChannelData.length; ucdIndex++) {
                    if (userChannelData[ucdIndex].realName == realNames[rnIndex] && userChannelData[ucdIndex].channelName == channelNames[cnIndex]) {
                        pipelineValueChannels[rnIndex] += (userChannelData[ucdIndex].pipelineValue);
                        break;
                    }
                }
            }
            pipelineValues.push(pipelineValueChannels);
        }

        var userPipelineTargets = [];
        var userPipelineChallenges = [];
        for (rnIndex = 0; rnIndex < realNames.length; rnIndex++) {
            userPipelineTargets.push(0);
            userPipelineChallenges.push(0);
            for (udIndex = 0; udIndex < userData.length; udIndex++) {
                if (userData[udIndex].realName == realNames[rnIndex]) {
                    userPipelineTargets[rnIndex] += userData[udIndex].pipelineTarget;
                    userPipelineChallenges[rnIndex] += userData[udIndex].pipelineChallenge;
                    break;
                }
            }
        }

        var channelPipelineValues = [];
        for (cnIndex = 0; cnIndex < channelNames.length; cnIndex++) {
            channelPipelineValues.push(0);
            for (ucdIndex = 0; ucdIndex < userChannelData.length; ucdIndex++) {
                if (userChannelData[ucdIndex].channelName == channelNames[cnIndex]) {
                    channelPipelineValues[cnIndex] += userChannelData[ucdIndex].pipelineValue;
                    break;
                }
            }
        }

        // console.log("===== 流水情况 =====");
        // console.log(realNames);
        // console.log(channelNames);
        // console.log(pipelineValues);
        // console.log(userPipelineTargets);
        // console.log(userPipelineChallenges);
        // console.log(channelPipelineValues);

        var channelTargetNames = ["目标值", "挑战值"];
        var channelTargetValues = [userPipelineTargets, userPipelineChallenges];
        var channelSeries = [];
        for (var cn = 0; cn < channelNames.length; cn++) {
            channelSeries.push({
                name: channelNames[cn],
                type: 'bar',
                stack: '总量',
                data: pipelineValues[cn]
            });
        }
        for (var ctn = 0; ctn < channelTargetNames.length; ctn++) {
            channelSeries.push({
                name: channelTargetNames[ctn],
                type: 'line',
                data: channelTargetValues[ctn]
            });
        }
        var performanceChart1 = echarts.init(document.getElementById('finance_performance_chart1'), "walden");
        var performanceChart1Option = {
            title  : chartsOption.setTitle("流水情况（元）"),
            legend : chartsOption.setLegend(channelNames.concat(channelTargetNames)),
            grid   : chartsOption.setGrid(),
            xAxis  : chartsOption.setXAxis(),
            yAxis  : chartsOption.setYAxis(realNames),
            series : chartsOption.setSeries(channelSeries),
            tooltip : {
                trigger     : 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                },
                formatter   : function (items) {
                    var showFormatter = "";
                    var total = 0;

                    showFormatter += '<div class="charts-title">' + items[0].name + '</div>';
                    showFormatter += '<ul class="charts-content">';
                    for (var cn = 0; cn < items.length - 2; cn++) {
                        showFormatter += '<li><label>' + items[cn].marker + items[cn].seriesName + '</label>: ' + $.moneyFormat(items[cn].value, 0, ".", ",") + '元</li>';
                        total += items[cn].value;
                    }
                    showFormatter += '<li><label>' + '<span style="display:inline-block;margin-right:5px;border-radius:10px;width:10px;height:10px;background-color:#FF0000;"></span>' + '合计</label>: ' + $.moneyFormat(total, 0, ".", ",") + '元</li>';
                    showFormatter += '<li><label>' + items[items.length - 2].marker + items[items.length - 2].seriesName + '</label>: ' + $.moneyFormat((items[items.length - 2].value), 0, ".", ",") + '元</li>' +
                        '<li><label>' + items[items.length - 1].marker + items[items.length - 1].seriesName + '</label>: ' + $.moneyFormat((items[items.length - 1].value), 0, ".", ",") + '元</li>' +
                        '</ul>';

                    return showFormatter;
                }
            }
        };
        performanceChart1.setOption(performanceChart1Option);

        var channelSeriesData = [];
        for (var dcn = 0; dcn < channelNames.length; dcn++) {
            channelSeriesData.push({value : channelPipelineValues[dcn], name : channelNames[dcn]});
        }
        var performanceChart4 = echarts.init(document.getElementById('finance_performance_chart4'), "walden");
        var performanceChart4Option = {
            title  : chartsOption.setTitle("流水情况占比", "来源渠道（百分比）"),
            legend : chartsOption.setLegend(channelNames, 'vertical'),
            series : [{
                name :'渠道流水',
                type :'pie',
                radius : ['50%', '80%'],
                data : channelSeriesData
            }],
            tooltip : {
                trigger: 'item',
                formatter: function (items) {
                    return items.seriesName + "<br />" + items.marker + items.name + ": " + $.moneyFormat(items.value, 0, ".", ",") + "元 (" + items.percent + "%)";
                }
            }
        };
        performanceChart4.setOption(performanceChart4Option);
    }

    // 确认收入
    function renderIncomeValueCharts(realNames, channelNames, userChannelData, userData) {
        var cnIndex, rnIndex, ucdIndex, udIndex;

        var incomeValues = [];
        for (cnIndex = 0; cnIndex < channelNames.length; cnIndex++) {
            var incomeValueChannels = [];
            for (rnIndex = 0; rnIndex < realNames.length; rnIndex++) {
                incomeValueChannels.push(0);
                for (ucdIndex = 0; ucdIndex < userChannelData.length; ucdIndex++) {
                    if (userChannelData[ucdIndex].realName == realNames[rnIndex] && userChannelData[ucdIndex].incomeType == channelNames[cnIndex]) {
                        incomeValueChannels[rnIndex] += (userChannelData[ucdIndex].incomeValue);
                        break;
                    }
                }
            }
            incomeValues.push(incomeValueChannels);
        }

        var userIncomeTargets = [];
        var userIncomeChallenges = [];
        for (rnIndex = 0; rnIndex < realNames.length; rnIndex++) {
            userIncomeTargets.push(0);
            userIncomeChallenges.push(0);
            for (udIndex = 0; udIndex < userData.length; udIndex++) {
                if (userData[udIndex].realName == realNames[rnIndex]) {
                    userIncomeTargets[rnIndex] += userData[udIndex].incomeTarget;
                    userIncomeChallenges[rnIndex] += userData[udIndex].incomeChallenge;
                    break;
                }
            }
        }

        var channelIncomeValues = [];
        for (cnIndex = 0; cnIndex < channelNames.length; cnIndex++) {
            channelIncomeValues.push(0);
            for (ucdIndex = 0; ucdIndex < userChannelData.length; ucdIndex++) {
                if (userChannelData[ucdIndex].incomeType == channelNames[cnIndex]) {
                    channelIncomeValues[cnIndex] += userChannelData[ucdIndex].incomeValue;
                    break;
                }
            }
        }

        // console.log("===== 确认收入情况 =====");
        // console.log(realNames);
        // console.log(channelNames);
        // console.log(incomeValues);
        // console.log(userIncomeTargets);
        // console.log(userIncomeChallenges);
        // console.log(channelIncomeValues);

        var channelTargetNames = ["目标值", "挑战值"];
        var channelTargetValues = [userIncomeTargets, userIncomeChallenges];
        var channelSeries = [];
        for (var cn = 0; cn < channelNames.length; cn++) {
            channelSeries.push({
                name: channelNames[cn],
                type: 'bar',
                stack: '总量',
                data: incomeValues[cn]
            });
        }
        for (var ctn = 0; ctn < channelTargetNames.length; ctn++) {
            channelSeries.push({
                name: channelTargetNames[ctn],
                type: 'line',
                data: channelTargetValues[ctn]
            });
        }
        var performanceChart2 = echarts.init(document.getElementById('finance_performance_chart2'), "walden");
        var performanceChart2Option = {
            title  : chartsOption.setTitle("确认收入情况（元）"),
            legend : chartsOption.setLegend(channelNames.concat(channelTargetNames)),
            grid   : chartsOption.setGrid(),
            xAxis  : chartsOption.setXAxis(),
            yAxis  : chartsOption.setYAxis(realNames),
            series : chartsOption.setSeries(channelSeries),
            tooltip : {
                trigger     : 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                },
                formatter   : function (items) {
                    var showFormatter = "";
                    var total = 0;

                    showFormatter += '<div class="charts-title">' + items[0].name + '</div>';
                    showFormatter += '<ul class="charts-content">';
                    for (var cn = 0; cn < items.length - 2; cn++) {
                        showFormatter += '<li><label>' + items[cn].marker + items[cn].seriesName + '</label>: ' + $.moneyFormat(items[cn].value, 0, ".", ",") + '元</li>';
                        total += items[cn].value;
                    }
                    showFormatter += '<li><label>' + '<span style="display:inline-block;margin-right:5px;border-radius:10px;width:10px;height:10px;background-color:#FF0000;"></span>' + '合计</label>: ' + $.moneyFormat(total, 0, ".", ",") + '元</li>';
                    showFormatter += '<li><label>' + items[items.length - 2].marker + items[items.length - 2].seriesName + '</label>: ' + $.moneyFormat((items[items.length - 2].value), 0, ".", ",") + '元</li>' +
                        '<li><label>' + items[items.length - 1].marker + items[items.length - 1].seriesName + '</label>: ' + $.moneyFormat((items[items.length - 1].value), 0, ".", ",") + '元</li>' +
                        '</ul>';

                    return showFormatter;
                }
            }
        };
        performanceChart2.setOption(performanceChart2Option);

        var channelSeriesData = [];
        for (var dcn = 0; dcn < channelNames.length; dcn++) {
            channelSeriesData.push({value : channelIncomeValues[dcn], name : channelNames[dcn]});
        }
        var performanceChart3 = echarts.init(document.getElementById('finance_performance_chart3'), "walden");
        var performanceChart3Option = {
            title  : chartsOption.setTitle("确认收入情况占比", "来源渠道（百分比）"),
            legend : chartsOption.setLegend(channelNames, 'vertical'),
            series : [{
                name :'渠道收入',
                type :'pie',
                radius : ['50%', '80%'],
                data : channelSeriesData
            }],
            tooltip : {
                trigger: 'item',
                formatter: function (items) {
                    return items.seriesName + "<br />" + items.marker + items.name + ": " + items.value + " (" + items.percent + "%)";
                }
            }
        };
        performanceChart3.setOption(performanceChart3Option);
    }

    // 注册人数
    function renderRegisterCountCharts(realNames, userData) {
        var rnIndex, udIndex;

        var classCounts = [], classNoCounts = [], registerCounts = [];
        for (rnIndex = 0; rnIndex < realNames.length; rnIndex++) {
            classCounts.push(0);
            classNoCounts.push(0);
            registerCounts.push(0);
            for (udIndex = 0; udIndex < userData.length; udIndex++) {
                if (userData[udIndex].realName == realNames[rnIndex]) {
                    classCounts[rnIndex] += (userData[udIndex].classCount);
                    classNoCounts[rnIndex] += -((userData[udIndex].registerCount - userData[udIndex].classCount));
                    registerCounts[rnIndex] += (userData[udIndex].registerCount);
                    break;
                }
            }
        }

        // console.log("===== 在册人数情况 =====");
        // console.log(realNames);
        // console.log(classCounts);
        // console.log(classNoCounts);
        // console.log(registerCounts);

        var percentCounts = [];
        for (var i = 0; i < registerCounts.length; i++) {
            var percent;
            if (registerCounts[i] > 0) {
                percent = ((classCounts[i] / registerCounts[i]) * 100).toFixed(2);
            } else {
                percent = "0.00";
            }
            percentCounts.push(percent);
        }

        $.sortFloatArrays(percentCounts, [realNames, classCounts, classNoCounts]);

        var performanceChart5 = echarts.init(document.getElementById('finance_performance_chart5'), "walden");
        var performanceChart5Option = {
            title  : chartsOption.setTitle("到课率排比（人）"),
            legend : chartsOption.setLegend(["到课", "未到课"]),
            grid   : chartsOption.setGrid(),
            xAxis  : chartsOption.setXAxis(),
            yAxis  : chartsOption.setYAxis(realNames),
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
                        '<li><label>' + '到课率</label>: ' + $.moneyFormat(((items[0].value * 100) / (items[0].value - items[1].value)), 0, ".", ",") + '%</li>' +
                        '</ul>';

                    return showFormatter;
                }
            }
        };
        performanceChart5.setOption(performanceChart5Option);
    }

    // 访问人数
    function renderAccessCountCharts(realNames, userData) {
        var rnIndex, udIndex;

        var businessCounts = [], businessNoCounts = [], accessCounts = [];
        for (rnIndex = 0; rnIndex < realNames.length; rnIndex++) {
            businessCounts.push(0);
            businessNoCounts.push(0);accessCounts.push(0);

            for (udIndex = 0; udIndex < userData.length; udIndex++) {
                if (userData[udIndex].realName == realNames[rnIndex]) {
                    businessCounts[rnIndex] += (userData[udIndex].businessCount);
                    businessNoCounts[rnIndex] += -(userData[udIndex].accessCount - userData[udIndex].businessCount);
                    accessCounts[rnIndex] += (userData[udIndex].accessCount);
                    break;
                }
            }
        }

        // console.log("===== 访问人数情况 =====");
        // console.log(realNames);
        // console.log(businessCounts);
        // console.log(businessNoCounts);
        // console.log(accessCounts);

        var percentCounts = [];
        for (var i = 0; i < accessCounts.length; i++) {
            var percent;
            if (accessCounts[i] > 0) {
                percent = ((businessCounts[i] / accessCounts[i]) * 100).toFixed(2);
            } else {
                percent = "0.00";
            }
            percentCounts.push(percent);
        }

        $.sortFloatArrays(percentCounts, [realNames, businessCounts, businessNoCounts]);

        var performanceChart6 = echarts.init(document.getElementById('finance_performance_chart6'), "walden");
        var performanceChart6Option = {
            title  : chartsOption.setTitle("成交率排名（百分比）"),
            legend : chartsOption.setLegend(["成交数", "未成交数"]),
            grid   : chartsOption.setGrid(),
            xAxis  : chartsOption.setXAxis(),
            yAxis  : chartsOption.setYAxis(realNames),
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
                        '<li><label>' + '成交率</label>: ' + $.moneyFormat(((items[0].value * 100) / (items[0].value - items[1].value)), 0, ".", ",") + '%</li>' +
                        '</ul>';

                    return showFormatter;
                }
            }
        };
        performanceChart6.setOption(performanceChart6Option);
    }

    // 闲时段占用
    function renderNullCountCharts(realNames, userData) {
        var rnIndex, udIndex;

        var nullCounts = [], nullNoCounts = [], nullTotalCounts = [];
        for (rnIndex = 0; rnIndex < realNames.length; rnIndex++) {
            nullCounts.push(0);
            nullNoCounts.push(0);
            nullTotalCounts.push(0);
            for (udIndex = 0; udIndex < userData.length; udIndex++) {
                if (userData[udIndex].realName == realNames[rnIndex]) {
                    nullCounts[rnIndex] += (userData[udIndex].nullCount);
                    nullNoCounts[rnIndex] += -(userData[udIndex].nullTotalCount - userData[udIndex].nullCount);
                    nullTotalCounts[rnIndex] += (userData[udIndex].nullTotalCount);
                    break;
                }
            }
        }

        // console.log("===== 空闲场地情况 =====");
        // console.log(realNames);
        // console.log(nullCounts);
        // console.log(nullNoCounts);
        // console.log(nullTotalCounts);

        var percentCounts = [];
        for (var i = 0; i < nullTotalCounts.length; i++) {
            var percent;
            if (nullTotalCounts[i] > 0) {
                percent = ((nullCounts[i] / nullTotalCounts[i]) * 100).toFixed(2);
            } else {
                percent = "0.00";
            }
            percentCounts.push(percent);
        }

        $.sortFloatArrays(percentCounts, [realNames, nullCounts, nullNoCounts]);

        var performanceChart5 = echarts.init(document.getElementById('finance_performance_chart7'), "walden");
        var performanceChart5Option = {
            title  : chartsOption.setTitle("闲时段占用率排名（百分比）"),
            legend : chartsOption.setLegend(["占用", "未占用"]),
            grid   : chartsOption.setGrid(),
            xAxis  : chartsOption.setXAxis(),
            yAxis  : chartsOption.setYAxis(realNames),
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
                        '<li><label>' + '占用率</label>: ' + $.moneyFormat(((items[0].value * 100) / (items[0].value - items[1].value)), 0, ".", ",") + '%</li>' +
                        '</ul>';

                    return showFormatter;
                }
            }
        };
        performanceChart5.setOption(performanceChart5Option);
    }

    // 忙时段占用
    function renderHotCountCharts(realNames, userData) {
        var rnIndex, udIndex;

        var hotCounts = [], hotNoCounts = [], hotTotalCounts = [];
        for (rnIndex = 0; rnIndex < realNames.length; rnIndex++) {
            hotCounts.push(0);
            hotNoCounts.push(0);
            hotTotalCounts.push(0);
            for (udIndex = 0; udIndex < userData.length; udIndex++) {
                if (userData[udIndex].realName == realNames[rnIndex]) {
                    hotCounts[rnIndex] += (userData[udIndex].hotCount);
                    hotNoCounts[rnIndex] += -(userData[udIndex].hotTotalCount - userData[udIndex].hotCount);
                    hotTotalCounts[rnIndex] += (userData[udIndex].hotTotalCount);
                    break;
                }
            }
        }

        // console.log("===== 访问人数情况 =====");
        // console.log(realNames);
        // console.log(hotCounts);
        // console.log(hotNoCounts);
        // console.log(hotTotalCounts);

        var percentCounts = [];
        for (var i = 0; i < hotTotalCounts.length; i++) {
            var percent;
            if (hotTotalCounts[i] > 0) {
                percent = ((hotCounts[i] / hotTotalCounts[i]) * 100).toFixed(2);
            } else {
                percent = "0.00";
            }
            percentCounts.push(percent);
        }

        $.sortFloatArrays(percentCounts, [realNames, hotCounts, hotNoCounts]);

        var performanceChart6 = echarts.init(document.getElementById('finance_performance_chart8'), "walden");
        var performanceChart6Option = {
            title  : chartsOption.setTitle("忙时段占用率排名（百分比）"),
            legend : chartsOption.setLegend(["占用", "未占用"]),
            grid   : chartsOption.setGrid(),
            xAxis  : chartsOption.setXAxis(),
            yAxis  : chartsOption.setYAxis(realNames),
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
                        '<li><label>' + '占用率</label>: ' + $.moneyFormat(((items[0].value * 100) / (items[0].value - items[1].value)), 0, ".", ",") + '%</li>' +
                        '</ul>';

                    return showFormatter;
                }
            }
        };
        performanceChart6.setOption(performanceChart6Option);
    }

    $("[name='total_students_type']").on("change", function (e) {
        e.preventDefault();

        window.location.href = "/admin/finance/performance?typeTime=" +  $("[name='total_students_type']:checked").val() +
            "&busType=" + $("[name='total_bus_type']:checked").val() + "&venueId=0";
    });

    $("[name='total_bus_type']").on("change", function (e) {
        e.preventDefault();

        window.location.href = "/admin/finance/performance?busType=" + $("[name='total_bus_type']:checked").val() + "&venueId=0";
    });
});
