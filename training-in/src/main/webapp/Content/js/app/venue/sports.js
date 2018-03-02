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
        },
        "alert": {
            deps: ["jquery"]
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

    $("#skill_form").on("click", ".delete-skill", function (e) {
        e.preventDefault();

        var $item = $(this).parents(".form-group");
        var size = $("#skill_form").find(".form-group").length;

        if (size <= 1) {
            jqueryAlert({
                'icon'      : '/Content/images/icon-info.png',
                'content'   : "不能删除仅剩下的最后一项",
                'closeTime' : 2000,
                'modal'        : true,
                'isModalClose' : true
            });
            return false;
        }

        $item.remove();
        $("#skill_form").find(".form-group:last-child .add-skill").show();
    });

    $("#skill_form").on("click", ".add-skill", function (e) {
        e.preventDefault();

        var $item = $(this).parents(".form-group");
        var $nextItem = $item.clone();

        $("#skill_form").append($nextItem);
        $(this).hide();
    });

    // 保存技能
    $(".save-skill").on("click", function (e) {
        e.preventDefault();

        var $form = $("#skill_form");
        var conditions = $form.serializeArray();

        if ($form.attr("submitting") == "submitting") {
            return false;
        }

        var submitData = {};
        submitData.sportId = $("#id_skill_sportId").val();
        submitData.orgSportsSkillsList = [];
        for (var i = 0; i < conditions.length; i++) {
            var item = conditions[i];
            if (item.name === "sportId") {
                continue;
            }
            if (item.name === "skillName" && $.trim(item.value) != "") {
                submitData.orgSportsSkillsList.push({
                    sportId: submitData.sportId,
                    skillName: item.value,
                    maxValue: conditions[i + 1].value
                });
                i++;
            }
        }
        if (submitData.sportId == "") {
            jqueryAlert({
                'icon'      : '/Content/images/icon-info.png',
                'content'   : "请先选择一项体育项目",
                'closeTime' : 2000,
                'modal'        : true,
                'isModalClose' : true
            });
            return false;
        }
        if (submitData.orgSportsSkillsList.length == 0) {
            jqueryAlert({
                'icon'      : '/Content/images/icon-info.png',
                'content'   : "请输入至少一项评测技能",
                'closeTime' : 2000,
                'modal'        : true,
                'isModalClose' : true
            });
            return false;
        }

        $form.attr("submitting", "submitting");

        $.postJSON('/admin/venue/saveSportsSkills', submitData, function (res) {
            $form.attr("submitting", "");

            if (res.code == 1) {
                jqueryAlert({
                    'icon'      : '/Content/images/icon-ok.png',
                    'content'   : "保存评测技能成功",
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
                    'content'   : "保存评测技能失败，请稍后重试",
                    'closeTime' : 2000,
                    'modal'        : true,
                    'isModalClose' : true
                });
            }
        });
    });
});
