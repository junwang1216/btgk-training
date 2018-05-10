requirejs.config({
    baseUrl: 'Content/',
    paths: {
        "jquery"    : 'bower_components/jquery/dist/jquery',
        "tether"    : 'bower_components/tether/dist/js/tether',
        "bootstrap" : 'bower_components/bootstrap/dist/js/bootstrap',
        "pace"      : 'bower_components/pace/pace',

        "alert"     : 'utils/jqueryAlert/alert/alert',

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

require(['jquery', 'alert', 'override', 'bootstrap', 'base'], function ($, jqueryAlert) {
    'use strict';

    // 基地变化，联动用户变化
    $("#flow_venue_id, #income_venue_id").on("change", function (e) {
        e.preventDefault();

        var isFlow = ($(this).attr("id") == "flow_venue_id");
        var venueId = $(this).val();

        $.getJSON('/admin/finance/getEmployeeByVenue', {venueId: venueId}, function (res) {
            var data = res.data;
            var tpl = '<option value="$userId">$userName</option>';
            var html = [];

            if (res.code == 1) {
                html.push(
                    tpl.replace("$userId", "0")
                        .replace("$userName", "所有用户")
                );

                !!data.orgFinanceUsersList && data.orgFinanceUsersList.forEach(function (item) {
                    html.push(
                        tpl.replace("$userId", item.id)
                            .replace("$userName", item.realName)
                    );
                });

                if (isFlow) {
                    $("#flow_user_id").html(html.join(""));
                } else {
                    $("#income_user_id").html(html.join(""));
                }
            } else {
                jqueryAlert({
                    'icon'      : '/Content/images/icon-error.png',
                    'content'   : "查询基地用户失败，请稍后重试",
                    'closeTime' : 2000,
                    'modal'        : true,
                    'isModalClose' : true
                });
            }
        });
    });
    $("#flow_venue_id, #income_venue_id").trigger("change");

    // 保存指标
    $(".save-finance-flow, .save-finance-income").on("click", function (e) {
        e.preventDefault();

        var $form = $("#finance_flow_form");

        if ($(this).hasClass("save-finance-income")) {
            $form = $("#finance_income_form");
        }

        var conditions = $form.serialize();

        if ($form.attr("submitting") == "submitting") {
            return false;
        }
        $form.attr("submitting", "submitting");

        $.post('/admin/finance/saveGoals', conditions, function (res) {
            $form.attr("submitting", "");

            if (res.code == 1) {
                jqueryAlert({
                    'icon'      : '/Content/images/icon-ok.png',
                    'content'   : "设置目标成功",
                    'closeTime' : 2000,
                    'modal'        : true,
                    'isModalClose' : true
                });

                window.setTimeout(function () {
                    var busType = $form.find("[name='busType']").val();
                    var venueId = $form.find("[name='venueId']").val();
                    var userId = $form.find("[name='userId']").val();
                    var year = $form.find("[name='year']").val();

                    window.location.assign("/admin/finance/settings?busType=" + busType + "&venueId=" + venueId + "&userId=" + userId + "&year=" + year);
                }, 1500);
            } else {
                jqueryAlert({
                    'icon'      : '/Content/images/icon-error.png',
                    'content'   : "设置目标失败, 请稍后重试",
                    'closeTime' : 2000,
                    'modal'        : true,
                    'isModalClose' : true
                });
            }
        });
    });

    // 查询日周详情
    $(".flow-user").on("click", function () {

        var id = $(this).attr("data-id");
        var venueId = $(this).attr("data-venue");
        var busType = $(this).attr("data-busType");
        var goalType = $(this).attr("data-goalType");
        var year = $(this).attr("data-year");
        var month = $(this).attr("data-month");

        $.getJSON('/admin/finance/getGoalsByMonth', {
            goalId: id,
            venueId: venueId,
            busType: busType,
            goalType: goalType,
            year: year,
            month: month
        }, function (res) {
            var data = res.data;
            var tpl = '<tr><td>$goalName</td><td>$startDate 至 $endDate</td><td>$minValue元</td> <td>$maxValue元</td></tr>';
            var html = [];

            if (res.code == 1 && data.weekDateList && data.weekDateList.length > 0) {
                html.push(
                    tpl.replace("$goalName", "月合计 (" + data.monthDay + "天)")
                        .replace("$startDate", data.monthStart)
                        .replace("$endDate", data.monthEnd)
                        .replace("$minValue", data.minValue)
                        .replace("$maxValue", data.maxValue)
                );
                html.push(
                    tpl.replace("$goalName", "月平均 (每天)")
                        .replace("$startDate", data.monthStart)
                        .replace("$endDate", data.monthEnd)
                        .replace("$minValue", data.dayMinValue)
                        .replace("$maxValue", data.dayMaxValue)
                );
                !!data.weekDateList && data.weekDateList.forEach(function (item) {
                    html.push(
                        tpl.replace("$goalName", "第" + item.index + "周 (" + item.weekDate.length + "天)")
                            .replace("$startDate", item.weekDate[0])
                            .replace("$endDate", item.weekDate[item.weekDate.length - 1])
                            .replace("$minValue", item.weekMinValue)
                            .replace("$maxValue", item.weekMaxValue)
                    );
                });

                $(".month-detail-list tbody").html(html.join(""));
            } else {
                jqueryAlert({
                    'icon'      : '/Content/images/icon-error.png',
                    'content'   : "查询月详情失败，请稍后重试",
                    'closeTime' : 2000,
                    'modal'        : true,
                    'isModalClose' : true
                });
            }
        });
    });

    // 检索
    $(".search-finance-flow, .search-finance-income").on("click", function (e) {
        e.preventDefault();

        var $form = $("#finance_flow_query_form");

        if ($(this).hasClass("search-finance-income")) {
            $form = $("#finance_income_query_form");
        }

        var conditions = $form.serialize();

        var queryString = window.location.search;
        if (queryString) {
            if (/userId=\d+/.test(queryString)) {
                queryString = queryString.replace(/userId=\d+/, "userId=" + $form.find("[name='userId']").val());
            } else {
                queryString += "&userId=" + $form.find("[name='userId']").val();
            }
            if (/year=\d+/.test(queryString)) {
                queryString = queryString.replace(/year=\d+/, "year=" + $form.find("[name='year']").val());
            } else {
                queryString += "&year=" + $form.find("[name='year']").val();
            }
            window.location.assign("/admin/finance/settings" + queryString);
        } else {
            window.location.assign("/admin/finance/settings?" + conditions);
        }
    });
});
