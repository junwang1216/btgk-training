requirejs.config({
    baseUrl: 'Content/',
    paths: {
        "jquery"    : 'bower_components/jquery/dist/jquery',
        "tether"    : 'bower_components/tether/dist/js/tether',
        "bootstrap" : 'bower_components/bootstrap/dist/js/bootstrap',
        "pace"      : 'bower_components/pace/pace',
        "chart"     : 'bower_components/chart.js/dist/Chart',

        "jquery.steps" : 'bower_components/jquery.steps/build/jquery.steps',
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
        "jquery.steps": {
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

require(['jquery', 'alert', 'override', 'bootstrap', 'base', "jquery.steps", 'jquery.validate', 'jquery.validate.unobtrusive'], function ($, jqueryAlert) {
    'use strict';

    // 表单校验配置
    $(document).ready(function () {
        $('#students_form').validate({
            ignore: ":hidden"
        });
    });

    $("#students-steps").steps({
        headerTag: "h3",
        bodyTag: "section",
        transitionEffect: "slideLeft",
        autoFocus: false,
        enableKeyNavigation: false,
        enablePagination: false,
        startIndex: 0
    });

    // 去分班
    $("#class_to_tip .class-goto").on("click", function (e) {
        e.preventDefault();

        $("#students-steps").steps("next");
        $("#class_to_tip").modal("hide");
    });

    // 不分班
    $("#class_to_tip .student-goto").on("click", function (e) {
        e.preventDefault();

        window.location.assign("/admin/students/list");
    });

    // 保存学生
    $(".save-students").on("click", function (e) {
        e.preventDefault();

        var $form= $("#students_form");
        var conditions = $form.serialize();

        if ($form.attr("submitting") == "submitting" || !$form.valid()) {
            return false;
        }
        $form.attr("submitting", "submitting");

        $.post('/admin/students/saveStudents', conditions, function (res) {
            $form.attr("submitting", "");

            if (res.code == 1) {
                $("#class_to_tip").modal("show");
                $("#class_student_id").val(res.data.studentId);
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

    // 选择班级
    $(".class-list").on("click", ".class-item", function (e) {
        e.preventDefault();

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
                $("#students-steps").steps("finish");

                window.setTimeout(function () {
                    window.location.href = '/admin/students/list';
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
});
