requirejs.config({
    baseUrl: 'Content/',
    paths: {
        "jquery"    : 'bower_components/jquery/dist/jquery',
        "tether"    : 'bower_components/tether/dist/js/tether',
        "bootstrap" : 'bower_components/bootstrap/dist/js/bootstrap',
        "pace"      : 'bower_components/pace/pace',
        "echart"    : 'bower_components/echarts/dist/echarts.min',

        "base"      : 'js/widgets/base',
        "override"  : 'js/widgets/override'
    },
    shim: {
        "bootstrap": {
            deps: ["jquery", "override"]
        }
    },  // 依赖关系
    waitSeconds: 0,
    urlArgs: '_=' + new Date().getTime()
});

require(['jquery', 'echart', 'bootstrap', 'pace', 'base', 'override'], function ($, echarts) {
    'use strict';

    var flowChart = echarts.init(document.getElementById('data_flow_chart'));
    flowChart.setOption({
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                crossStyle: {
                    color: '#999'
                }
            }
        },
        toolbox: {
            feature: {
                saveAsImage: {show: true}
            }
        },
        legend: {
            data:['流水完成情况', '流水最低目标','流水挑战目标']
        },
        xAxis: [
            {
                type: 'category',
                data: ['01月','02月','03月','04月','05月','06月','07月','08月','09月','10月','11月','12月'],
                axisPointer: {
                    type: 'shadow'
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '流水(元)'
            }
        ],
        series: [
            {
                name: '流水完成情况',
                type: 'bar',
                color: "#EEA2AD",
                data: [1000, 1234, 657, 1567, 1879, 2100, 1275, 765, 931, 1800, 390, 1489]
            },
            {
                name: '流水最低目标',
                type: 'line',
                color: "#CCFFCC",
                data: [1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000]
            },
            {
                name: '流水挑战目标',
                type: 'line',
                color: "#FF0000",
                data: [2000, 2000, 2000, 2000, 2000, 2000, 2000, 2000, 2000, 2000, 2000, 2000]
            }
        ]
    });

    var incomeChart = echarts.init(document.getElementById('data_income_chart'));
    incomeChart.setOption({
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                crossStyle: {
                    color: '#999'
                }
            }
        },
        toolbox: {
            feature: {
                saveAsImage: {show: true}
            }
        },
        legend: {
            data:['确认收入完成情况', '确认收入最低目标','确认收入挑战目标']
        },
        xAxis: [
            {
                type: 'category',
                data: ['01月','02月','03月','04月','05月','06月','07月','08月','09月','10月','11月','12月'],
                axisPointer: {
                    type: 'shadow'
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '确认收入(元)'
            }
        ],
        series: [
            {
                name: '确认收入完成情况',
                type: 'bar',
                color: "#EEA2AD",
                data: [2100, 1489, 1234, 1275, 765, 1000, 1879, 931, 1800, 390, 657, 1567]
            },
            {
                name: '确认收入最低目标',
                type: 'line',
                color: "#CCFFCC",
                data: [1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000]
            },
            {
                name: '确认收入挑战目标',
                type: 'line',
                color: "#FF0000",
                data: [2000, 2000, 2000, 2000, 2000, 2000, 2000, 2000, 2000, 2000, 2000, 2000]
            }
        ]
    });
});
