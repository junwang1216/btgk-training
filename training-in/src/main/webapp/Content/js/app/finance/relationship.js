requirejs.config({
    baseUrl: 'Content/',
    paths: {
        "jquery"    : 'bower_components/jquery/dist/jquery',
        "tether"    : 'bower_components/tether/dist/js/tether',
        "bootstrap" : 'bower_components/bootstrap/dist/js/bootstrap',
        "pace"      : 'bower_components/pace/pace',

        "alert"     : 'utils/jqueryAlert/alert/alert',

        "jquery.validate"              : 'bower_components/jquery.validation/dist/jquery.validate',
        "jquery.validate.unobtrusive"  : 'bower_components/Microsoft.jQuery.Unobtrusive.Validation/jquery.validate.unobtrusive',

        "base"      : 'js/widgets/base',
        "override"  : 'js/widgets/override'
    },
    shim: {
        "bootstrap": {
            deps: ["jquery", "override"]
        },
        "alert": {
            deps: ["jquery"]
        },
        "jquery.validate": {
            deps: ["jquery", "override"]
        },
        "jquery.validate.unobtrusive": {
            deps: ["jquery", "jquery.validate"]
        }
    },  // 依赖关系
    waitSeconds: 0,
    urlArgs: '_=' + new Date().getTime()
});

require(['jquery', 'alert', 'override', 'bootstrap', 'base', 'jquery.validate', 'jquery.validate.unobtrusive'], function ($, jqueryAlert) {
    'use strict';

    // 表单校验配置
    $(document).ready(function () {
        $('#relationship_user_form, #relationship_venue_form').validate({
            ignore: ":hidden"
        });
    });

    $.postJSON = function(url, data, callback) {
        return $.ajax({
            'type' : 'POST',
            'url' : url,
            'contentType' : 'application/json',
            'data' : JSON.stringify(data),
            'dataType' : 'json',
            'success' : callback
        });
    };

    // 基地弹窗
    $(".venue-relationship").on("click", function (e) {
        e.preventDefault();

        var venueId = $(this).attr("data-venueId");
        var venueName = $(this).attr("data-venueName");

        $("#relationship_venue_id").val(venueId || "");
        $("#relationship_venue_name").val(venueName || "");
    });

    // 保存基地
    $(".save-relationship-venue").on("click", function (e) {
        e.preventDefault();

        var $form = $("#relationship_venue_form");
        var conditions = $form.serialize();

        if ($form.attr("submitting") == "submitting" || !$form.valid()) {
            return false;
        }
        $form.attr("submitting", "submitting");

        $.post('/admin/finance/saveVenues', conditions, function (res) {
            $form.attr("submitting", "");

            if (res.code == 1) {
                jqueryAlert({
                    'icon'      : '/Content/images/icon-ok.png',
                    'content'   : "保存基地成功",
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
                    'content'   : "保存基地失败, 请稍后重试",
                    'closeTime' : 2000,
                    'modal'        : true,
                    'isModalClose' : true
                });
            }
        });
    });

    // 闭馆
    $(".delete-relationship-venue").on("click", function (e) {
        e.preventDefault();

        jqueryAlert({
            'icon'      : '/Content/images/icon-ok.png',
            'content'   : "TODO 场馆闭馆未做",
            'closeTime' : 2000,
            'modal'        : true,
            'isModalClose' : true
        });
    });

    // 用户弹窗
    $(".user-relationship").on("click", function (e) {
        e.preventDefault();

        var userId = $(this).attr("data-userId");
        var mobile = $(this).attr("data-mobile");
        var userName = $(this).attr("data-userName");
        var venueId = $(this).attr("data-venueId");

        $("#relationship_user_id").val(userId || "");
        $("#relationship_user_name").val(userName || "");
        $("#relationship_user_mobile").val(mobile || "");
        $("#info_venue_id").val(venueId || "");
    });

    // 保存用户
    $(".save-relationship-user").on("click", function (e) {
        e.preventDefault();

        var $form = $("#relationship_user_form");
        var conditions = $form.serialize();

        if ($form.attr("submitting") == "submitting" || !$form.valid()) {
            return false;
        }
        $form.attr("submitting", "submitting");

        $.post('/admin/finance/saveEmployee', conditions, function (res) {
            $form.attr("submitting", "");

            if (res.code == 1) {
                jqueryAlert({
                    'icon'      : '/Content/images/icon-ok.png',
                    'content'   : "保存员工成功",
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
                    'content'   : "保存员工失败, 请稍后重试",
                    'closeTime' : 2000,
                    'modal'        : true,
                    'isModalClose' : true
                });
            }
        });
    });

    // 离职用户
    $(".delete-relationship-user").on("click", function (e) {
        e.preventDefault();

        jqueryAlert({
            'icon'      : '/Content/images/icon-ok.png',
            'content'   : "TODO 用户离职未做",
            'closeTime' : 2000,
            'modal'        : true,
            'isModalClose' : true
        });
    });
});
