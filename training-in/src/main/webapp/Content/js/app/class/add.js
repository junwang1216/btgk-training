requirejs.config({
    baseUrl: 'Content/',
    paths: {
        "jquery"    : 'bower_components/jquery/dist/jquery',
        "tether"    : 'bower_components/tether/dist/js/tether',
        "bootstrap" : 'bower_components/bootstrap/dist/js/bootstrap',
        "pace"      : 'bower_components/pace/pace',

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
        "jquery.steps": {
            deps: ["jquery"]
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

require(['jquery', 'alert', 'override', 'bootstrap', 'base', "jquery.steps", 'jquery.validate', 'jquery.validate.unobtrusive'], function ($, jqueryAlert) {
    'use strict';

    // 表单校验配置
    $(document).ready(function () {
        $('#class_form').validate({
            ignore: ":hidden"
        });
    });

    $("#class-steps").steps({
        headerTag: "h3",
        bodyTag: "section",
        transitionEffect: "slideLeft",
        autoFocus: false,
        enableKeyNavigation: false,
        enablePagination: false,
        startIndex: 0
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

    $("[name='classSchedule']").on("change", function (e) {
        e.preventDefault();

        var item = $("[name='classSchedule']:checked").val();

        $(".class-week-list").hide();
        $(".class-date-list").hide();
        $(".class-" + item + "-list").show();
    });

    $(".class-week-list").on("click", ".class-week-del", function (e) {
        e.preventDefault();

        var $item = $(this).parents(".form-group");
        var size = $(".class-week-list").find(".form-group").length;

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
        $(".class-week-list").find(".form-group:last-child .class-week-add").show();
    });

    $(".class-week-list").on("click", ".class-week-add", function (e) {
        e.preventDefault();

        var $item = $(this).parents(".form-group");
        var $nextItem = $item.clone();

        $(".class-week-list").append($nextItem);
        $(this).hide();
    });

    $(".class-date-list").on("click", ".class-date-del", function (e) {
        e.preventDefault();

        var $item = $(this).parents(".form-group");
        var size = $(".class-date-list").find(".form-group").length;

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
        $(".class-date-list").find(".form-group:last-child .class-date-add").show();
    });

    $(".class-date-list").on("click", ".class-date-add", function (e) {
        e.preventDefault();

        var $item = $(this).parents(".form-group");
        var $nextItem = $item.clone();

        $(".class-date-list").append($nextItem);
        $(this).hide();
    });

    // 保存班级排期
    $(".save-class-schedule").on("click", function (e) {
        e.preventDefault();

        var $form = $("#class_schedule_form");
        var conditions = $form.serializeArray();

        if ($form.attr("submitting") == "submitting") {
            return false;
        }

        var type = $("[name='classSchedule']:checked").val();
        var classId = $("#class_schedule_id").val();
        var startDate = $("#class_schedule_startDate").val();
        var endDate = $("#class_schedule_endDate").val();

        var submitData = {};
        submitData.classId = classId;
        submitData.orgClassScheduleList = [];
        for (var i = 0; i < conditions.length; i++) {
            var item = conditions[i];
            if (item.name === "classWeek" &&
                type === "week" &&
                $.trim(item.value) != "" &&
                $.trim(conditions[i + 1].value) != "" &&
                $.trim(conditions[i + 2].value) != "" &&
                $.trim(conditions[i + 3].value) != "") {
                submitData.orgClassScheduleList.push({
                    classWeek: item.value,
                    startDate: startDate,
                    endDate: endDate,
                    startTime: conditions[i + 1].value,
                    endTime: conditions[i + 2].value,
                    coachId: conditions[i + 3].value
                });
                i += 3;
            }
            if (item.name === "classDate" &&
                type === "date" &&
                $.trim(item.value) != "" &&
                $.trim(conditions[i + 1].value) != "" &&
                $.trim(conditions[i + 2].value) != "" &&
                $.trim(conditions[i + 3].value) != "") {
                submitData.orgClassScheduleList.push({
                    classDate: item.value,
                    startTime: conditions[i + 1].value,
                    endTime: conditions[i + 2].value,
                    coachId: conditions[i + 3].value
                });
                i += 3;
            }
        }

        if (submitData.orgClassScheduleList.length == 0) {
            jqueryAlert({
                'icon'      : '/Content/images/icon-info.png',
                'content'   : "请输入至少一项排期项",
                'closeTime' : 2000,
                'modal'        : true,
                'isModalClose' : true
            });
            return false;
        }

        $form.attr("submitting", "submitting");

        $.postJSON('/admin/class/saveClassSchedule', submitData, function (res) {
            $form.attr("submitting", "");

            if (res.code == 1) {
                jqueryAlert({
                    'icon'      : '/Content/images/icon-ok.png',
                    'content'   : "保存排班成功",
                    'closeTime' : 2000,
                    'modal'        : true,
                    'isModalClose' : true
                });
                $("#class-steps").steps("finish");

                window.setTimeout(function () {
                    window.location.href = '/admin/class/list';
                }, 1500);
            } else {
                jqueryAlert({
                    'icon'      : '/Content/images/icon-ok.png',
                    'content'   : "保存排班失败, 请稍后重试",
                    'closeTime' : 2000,
                    'modal'        : true,
                    'isModalClose' : true
                });
            }
        });
    });

    // 课程变化
    $("#info_course_id").on("change", function (e) {
        e.preventDefault();

        var courseId = $(this).val();

        $.getJSON('/admin/class/getCoachByCourse', {courseId: courseId}, function (res) {
            var data = res.data;
            var tpl = '<option value="$coachId">$coachName</option>';
            var html = [];

            if (res.code == 1) {
                html.push(
                    tpl.replace("$coachId", "")
                        .replace("$coachName", "选择教练")
                );

                !!data.orgCoachesList && data.orgCoachesList.forEach(function (item) {
                    html.push(
                        tpl.replace("$coachId", item.id)
                            .replace("$coachName", item.realName)
                    );
                });

                $("#info_coach_id").html(html.join(""));
            } else {
                jqueryAlert({
                    'icon'      : '/Content/images/icon-error.png',
                    'content'   : "查询教练失败，请稍后重试",
                    'closeTime' : 2000,
                    'modal'        : true,
                    'isModalClose' : true
                });
            }
        });
    });

    // 保存班级
    $(".save-class").on("click", function (e) {
        e.preventDefault();

        var $form= $("#class_form");
        var conditions = $form.serialize();

        if ($form.attr("submitting") == "submitting" || !$form.valid()) {
            return false;
        }
        $form.attr("submitting", "submitting");

        $.post('/admin/class/saveClass', conditions, function (res) {
            $form.attr("submitting", "");

            if (res.code == 1) {
                $("#class_id").val(res.data.classId);
                $("#class_schedule_id").val(res.data.classId);

                jqueryAlert({
                    'icon'      : '/Content/images/icon-ok.png',
                    'content'   : "保存班级成功",
                    'closeTime' : 2000,
                    'modal'        : true,
                    'isModalClose' : true
                });

                $("#class-steps").steps("next");
            } else {
                jqueryAlert({
                    'icon'      : '/Content/images/icon-error.png',
                    'content'   : "保存班级失败, 请稍后重试",
                    'closeTime' : 2000,
                    'modal'        : true,
                    'isModalClose' : true
                });
            }
        });
    });
});
