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

    // 基地变化
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
                    'content'   : "查询用户失败，请稍后重试",
                    'closeTime' : 2000,
                    'modal'        : true,
                    'isModalClose' : true
                });
            }
        });
    });
    $("#flow_venue_id, #income_venue_id").trigger("change");

    // 保存用户
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
                    'content'   : "保存目标成功",
                    'closeTime' : 2000,
                    'modal'        : true,
                    'isModalClose' : true
                });

                window.setTimeout(function () {
                    window.location.reload();
                }, 1500);
            } else {
                jqueryAlert({
                    'icon'      : '/Content/images/icon-error.png',
                    'content'   : "保存目标成功失败, 请稍后重试",
                    'closeTime' : 2000,
                    'modal'        : true,
                    'isModalClose' : true
                });
            }
        });
    });
});
