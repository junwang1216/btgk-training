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

    // 表单校验配置
    /*$(document).ready(function () {
        $('#login_form').validate({
            ignore: ":hidden"
        });
    });*/

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

    $(".user-list").on("click", ".user-class", function () {
        var classId = $(this).parents("tr").attr("data-class");

        $("#class_student_id").val($(this).parents("tr").attr("data-id"));

        if (!!classId) {
            $(".class-item").removeClass("class-item-selected");
            $(".class-item[data-id='" + classId + "']").addClass("class-item-selected");
        }
    });

    // 选择班级
    $(".class-list").on("click", ".class-item", function (e) {
        e.preventDefault();

        if ($(this).hasClass("class-item-selected")) {
            $(this).removeClass("class-item-selected");
        } else {
            $(".class-item").removeClass("class-item-selected");
            $(this).addClass("class-item-selected");
        }

        var classId = $(this).attr("data-id");
        var className = $(this).attr("data-name");
        var classPrice = $(this).attr("data-price");

        $("#class_id").val(classId);
        $("#class_name").val(className);
        $("#class_balance").val(classPrice);
    });

    // 保存分班
    $(".save-class").on("click", function (e) {
        e.preventDefault();

        var $form = $("#class_form");
        var conditions = $form.serialize();

        if (!$("#class_id").val().trim()) {
            jqueryAlert({
                'icon'      : '/Content/images/icon-error.png',
                'content'   : "请选择班级",
                'closeTime' : 2000,
                'modal'        : true,
                'isModalClose' : true
            });
            return false;
        }

        $.post('/admin/students/saveClassStudents', conditions, function (res) {
            $form.attr("submitting", "");

            if (res.code == 1) {
                jqueryAlert({
                    'icon'      : '/Content/images/icon-ok.png',
                    'content'   : "学员分班成功",
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
                    'content'   : "保存学生失败, 请稍后重试",
                    'closeTime' : 2000,
                    'modal'        : true,
                    'isModalClose' : true
                });
            }
        });
    });

    // 检索
    $(".search-students").on("click", function (e) {
        e.preventDefault();

        var $form = $("#students_form");
        var conditions = $form.serialize();

        window.location.assign("/admin/students/list?" + conditions);
    });
});
