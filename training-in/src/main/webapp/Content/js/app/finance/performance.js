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

    var performanceChart = echarts.init(document.getElementById('finance_performance_chart1'));
    performanceChart.setOption({
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            data: ['公司', '个人', '转介绍', '续费']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis:  {
            type: 'value'
        },
        yAxis: {
            type: 'category',
            data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
        },
        series: [
            {
                name: '公司',
                type: 'bar',
                stack: '总量',
                label: {
                    normal: {
                        show: true,
                        position: 'insideRight'
                    }
                },
                data: [320, 302, 301, 334, 390, 330, 320]
            },
            {
                name: '个人',
                type: 'bar',
                stack: '总量',
                label: {
                    normal: {
                        show: true,
                        position: 'insideRight'
                    }
                },
                data: [120, 132, 101, 134, 90, 230, 210]
            },
            {
                name: '转介绍',
                type: 'bar',
                stack: '总量',
                label: {
                    normal: {
                        show: true,
                        position: 'insideRight'
                    }
                },
                data: [220, 182, 191, 234, 290, 330, 310]
            },
            {
                name: '续费',
                type: 'bar',
                stack: '总量',
                label: {
                    normal: {
                        show: true,
                        position: 'insideRight'
                    }
                },
                data: [150, 212, 201, 154, 190, 330, 410]
            }
        ]
    });

    var performanceChart2 = echarts.init(document.getElementById('finance_performance_chart2'));
    performanceChart2.setOption({
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            },
            formatter: function (params) {
                return params[0].seriesName + ": " + params[0].data + "元<br>" +
                    params[1].seriesName + ": " + params[1].data + "元<br>" +
                    params[2].seriesName + ": " + params[2].data + "元<br>" +
                    params[3].seriesName + ": " + params[3].data + "元<br>" +
                    "完成汇总: " + (params[0].data + params[1].data + params[2].data + params[3].data) + "元<br>" +
                    params[4].seriesName + ": " + params[4].data + "元<br>" +
                    params[5].seriesName + ": " + params[5].data + "元";
            }
        },
        legend: {
            data: ['公司', '个人', '转介绍', '续费', '最低目标', '最高目标']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis:  {
            type: 'value'
        },
        yAxis: {
            type: 'category',
            data: ['丁承平', '程沛槐', '赵才哲', '张痴海', '孔谷云', '余含芙', '冯俊哲', '毛雅懿', '龚尔丝', '莫尔烟']
        },
        series: [
            {
                name: '公司',
                type: 'bar',
                stack: '总量',
                label: {
                    normal: {
                        show: true,
                        position: 'insideRight'
                    }
                },
                data: [320, 302, 301, 334, 390, 330, 320, 301, 334, 390]
            },
            {
                name: '个人',
                type: 'bar',
                stack: '总量',
                label: {
                    normal: {
                        show: true,
                        position: 'insideRight'
                    }
                },
                data: [120, 132, 101, 134, 90, 230, 210, 120, 132, 101]
            },
            {
                name: '转介绍',
                type: 'bar',
                stack: '总量',
                label: {
                    normal: {
                        show: true,
                        position: 'insideRight'
                    }
                },
                data: [220, 182, 191, 234, 290, 330, 310, 290, 330, 310]
            },
            {
                name: '续费',
                type: 'bar',
                stack: '总量',
                label: {
                    normal: {
                        show: true,
                        position: 'insideRight'
                    }
                },
                data: [150, 212, 201, 154, 190, 330, 410, 154, 190, 330]
            },
            {
                name: '最低目标',
                type: 'line',
                data: [900, 900, 900, 900, 900, 900, 900, 900, 900, 900]
            },
            {
                name: '挑战目标',
                type: 'line',
                data: [1800, 1800, 1800, 1800, 1800, 1800, 1800, 1800, 1800, 1800]
            }
        ]
    });

    var performanceChart3 = echarts.init(document.getElementById('finance_performance_chart3'));
    performanceChart3.setOption({
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            data: ['公司', '个人', '转介绍', '续费']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis:  {
            type: 'value'
        },
        yAxis: {
            type: 'category',
            data: ['方庄基地', '天秀基地', '万柳基地', '京西基地']
        },
        series: [
            {
                name: '公司',
                type: 'bar',
                stack: '总量',
                label: {
                    normal: {
                        show: true,
                        position: 'insideRight'
                    }
                },
                data: [320, 302, 301, 334]
            },
            {
                name: '个人',
                type: 'bar',
                stack: '总量',
                label: {
                    normal: {
                        show: true,
                        position: 'insideRight'
                    }
                },
                data: [120, 132, 101, 134]
            },
            {
                name: '转介绍',
                type: 'bar',
                stack: '总量',
                label: {
                    normal: {
                        show: true,
                        position: 'insideRight'
                    }
                },
                data: [220, 182, 191, 234]
            },
            {
                name: '续费',
                type: 'bar',
                stack: '总量',
                label: {
                    normal: {
                        show: true,
                        position: 'insideRight'
                    }
                },
                data: [150, 212, 201, 154]
            }
        ]
    });

    var performanceChart4 = echarts.init(document.getElementById('finance_performance_chart4'));
    performanceChart4.setOption({
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            left: 'center',
            data: ['公司', '个人', '转介绍', '续费']
        },
        series : [
            {
                name: '青训基地',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    {value : 335, name: '方庄基地'},
                    {value : 310, name: '天秀基地'},
                    {value : 234, name: '万柳基地'},
                    {value : 135, name: '京西基地'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    });
});