requirejs.config({
    baseUrl: 'Content/',
    paths: {
        "jquery"    : 'bower_components/jquery/dist/jquery',
        "tether"    : 'bower_components/tether/dist/js/tether',
        "bootstrap" : 'bower_components/bootstrap/dist/js/bootstrap',
        "pace"      : 'bower_components/pace/pace',

        "timepicker"    : "bower_components/jquery-timepicker-wvega/jquery.timepicker",
        "datepicker"    : "bower_components/bootstrap-datepicker/dist/js/bootstrap-datepicker",
        "datepicker-zh" : "bower_components/bootstrap-datepicker/dist/locales/bootstrap-datepicker.zh-CN.min",
        "alert"         : 'utils/jqueryAlert/alert/alert',

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
        "timepicker": {
            deps: ["jquery"]
        },
        "datepicker": {
            deps: ["jquery", "bootstrap", "datepicker-zh"]
        },
        "datepicker-zh": {
            deps: ["jquery"]
        }
    },  // 依赖关系
    waitSeconds: 0,
    urlArgs: '_=' + new Date().getTime()
});

require(['jquery', 'alert', 'override', 'bootstrap', 'base', "timepicker", "datepicker-zh", "datepicker"], function ($, jqueryAlert) {
    'use strict';

    function setDatePicker() {
        $('input.datepicker').datepicker({
            format: 'yyyy-mm-dd',
            autoclose: true,
            todayBtn: true,
            todayHighlight: true,
            toggleActive: true,
            language: "zh-CN",
            daysOfWeekHighlighted: "0,6"
        });
        $('input.timepicker').timepicker({
            timeFormat: 'HH:mm',
            interval: 30
        });
    }
    setDatePicker();

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
        setDatePicker();
    });

    $(".class-week-list").on("click", ".class-week-add", function (e) {
        e.preventDefault();

        var $item = $(this).parents(".form-group");
        var $nextItem = $item.clone();

        $(".class-week-list").append($nextItem);
        $(this).hide();
        setDatePicker()
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
        setDatePicker();
    });

    $(".class-date-list").on("click", ".class-date-add", function (e) {
        e.preventDefault();

        var $item = $(this).parents(".form-group");
        var $nextItem = $item.clone();

        $(".class-date-list").append($nextItem);
        $(this).hide();
        setDatePicker();
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
            if (item.name === "classWeek" && type === "week") {
                if (item.value.length > 0 &&
                    conditions[i + 1].value.length > 0 &&
                    conditions[i + 2].value.length > 0 &&
                    conditions[i + 3].value.length > 0) {

                    submitData.orgClassScheduleList.push({
                        classWeek: item.value,
                        startDate: startDate,
                        endDate: endDate,
                        startTime: conditions[i + 1].value,
                        endTime: conditions[i + 2].value,
                        coachId: conditions[i + 3].value
                    });
                    i += 3;

                } else {
                    jqueryAlert({
                        'icon'      : '/Content/images/icon-info.png',
                        'content'   : "每个排期项不能为空",
                        'closeTime' : 2000,
                        'modal'        : true,
                        'isModalClose' : true
                    });
                    return false;
                }
            }
            if (item.name === "classDate" && type === "date") {
                if (item.value.length > 0 &&
                    conditions[i + 1].value.length > 0 &&
                    conditions[i + 2].value.length > 0 &&
                    conditions[i + 3].value.length > 0) {

                    submitData.orgClassScheduleList.push({
                        classDate: item.value,
                        startTime: conditions[i + 1].value,
                        endTime: conditions[i + 2].value,
                        coachId: conditions[i + 3].value
                    });
                    i += 3;

                } else {
                    jqueryAlert({
                        'icon'      : '/Content/images/icon-info.png',
                        'content'   : "每个排期项不能为空",
                        'closeTime' : 2000,
                        'modal'        : true,
                        'isModalClose' : true
                    });
                    return false;
                }
            }
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

                window.setTimeout(function () {
                    window.location.assign("/admin/class/list");
                }, 1500);
            } else {
                jqueryAlert({
                    'icon'      : '/Content/images/icon-error.png',
                    'content'   : "保存排班失败，请稍后重试",
                    'closeTime' : 2000,
                    'modal'        : true,
                    'isModalClose' : true
                });
            }
        });
    });
});
