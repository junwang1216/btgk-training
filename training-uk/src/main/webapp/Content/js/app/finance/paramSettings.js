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

    $("#income_form").on("click", ".del-settings", function (e) {
        e.preventDefault();

        var $item = $(this).parents(".form-group");
        var size = $("#income_form").find(".form-group").length;

        if (size <= 1) {
            alert("不能删除仅剩下的最后一项！");
            return false;
        }

        $item.remove();
    });

    $("#income_form").on("click", ".add-settings", function (e) {
        e.preventDefault();

        var $item = $(this).parents(".form-group");
        var $nextItem = $item.clone();

        $("#income_form").append($nextItem);
        $(this).hide();
    });

    // 保存收入项
    $(".save-income-settings").on("click", function (e) {
        e.preventDefault();

        var $form = $("#income_form");
        var conditions = $form.serializeArray();

        if ($form.attr("submitting") == "submitting") {
            return false;
        }
        $form.attr("submitting", "submitting");

        var submitData = {};
        submitData.paramType = $('input[name="incomeGroup"]').val();
        submitData.orgFinanceEnumsList = [];
        for (var i = 0; i < conditions.length; i++) {
            var item = conditions[i];

            if (item.name == "incomeType") {
                submitData.orgFinanceEnumsList.push({
                    enumNote  : item.value,
                    enumGroup : submitData.paramType,
                    enumPriority : i + 1
                });
            }
        }

        $.postJSON('/admin/finance/saveParamsSettings', submitData, function (res) {
            $form.attr("submitting", "");

            if (res.code == 1) {
                jqueryAlert({
                    'icon'      : '/Content/images/icon-ok.png',
                    'content'   : "设置收入类型成功",
                    'closeTime' : 2000,
                    'modal'        : true,
                    'isModalClose' : true
                });
            } else {
                jqueryAlert({
                    'icon'      : '/Content/images/icon-error.png',
                    'content'   : res.message || "设置收入类型失败, 请稍后重试",
                    'closeTime' : 2000,
                    'modal'        : true,
                    'isModalClose' : true
                });
            }
        });
    });

    $("#expend_form").on("click", ".del-settings", function (e) {
        e.preventDefault();

        var $item = $(this).parents(".form-group");
        var size = $("#expend_form").find(".form-group").length;

        if (size <= 1) {
            alert("不能删除仅剩下的最后一项！");
            return false;
        }

        $item.remove();
    });

    $("#expend_form").on("click", ".add-settings", function (e) {
        e.preventDefault();

        var $item = $(this).parents(".form-group");
        var $nextItem = $item.clone();

        $("#expend_form").append($nextItem);
        $(this).hide();
    });

    // 保存支出项
    $(".save-expend-settings").on("click", function (e) {
        e.preventDefault();

        var $form = $("#expend_form");
        var conditions = $form.serializeArray();

        if ($form.attr("submitting") == "submitting") {
            return false;
        }
        $form.attr("submitting", "submitting");

        var submitData = {};
        submitData.paramType = $('input[name="channelGroup"]').val();
        submitData.orgFinanceEnumsList = [];
        for (var i = 0; i < conditions.length; i++) {
            var item = conditions[i];

            if (item.name == "flowChannel") {
                submitData.orgFinanceEnumsList.push({
                    enumNote  : item.value,
                    enumGroup : submitData.paramType,
                    enumPriority : i + 1
                });
            }
        }

        $.postJSON('/admin/finance/saveParamsSettings', submitData, function (res) {
            $form.attr("submitting", "");

            if (res.code == 1) {
                jqueryAlert({
                    'icon'      : '/Content/images/icon-ok.png',
                    'content'   : "设置流水渠道成功",
                    'closeTime' : 2000,
                    'modal'        : true,
                    'isModalClose' : true
                });
            } else {
                jqueryAlert({
                    'icon'      : '/Content/images/icon-error.png',
                    'content'   : res.message || "设置流水渠道失败, 请稍后重试",
                    'closeTime' : 2000,
                    'modal'        : true,
                    'isModalClose' : true
                });
            }
        });
    });
});
