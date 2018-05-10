requirejs.config({
    baseUrl: 'Content/',
    paths: {
        "jquery"    : 'bower_components/jquery/dist/jquery',
        "tether"    : 'bower_components/tether/dist/js/tether',
        "bootstrap" : 'bower_components/bootstrap/dist/js/bootstrap',
        "pace"      : 'bower_components/pace/pace',

        "datepicker"    : "bower_components/bootstrap-datepicker/dist/js/bootstrap-datepicker",
        "datepicker-zh" : "bower_components/bootstrap-datepicker/dist/locales/bootstrap-datepicker.zh-CN.min",
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
        "datepicker": {
            deps: ["jquery", "bootstrap"]
        },
        "datepicker-zh": {
            deps: ["jquery", "datepicker"]
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

require(['jquery', 'alert', 'override', 'bootstrap', 'base', 'jquery.validate', 'jquery.validate.unobtrusive', "datepicker", "datepicker-zh"], function ($, jqueryAlert) {
    'use strict';

    // 表单校验配置
    $(document).ready(function () {
        $('#business_form').validate({
            ignore: ":hidden"
        });
    });

    function setDatePicker() {
        $('input.datepicker').datepicker({
            format: 'yyyy-mm-dd',
            autoclose: true,
            todayBtn: true,
            todayHighlight: true,
            toggleActive: true,
            language: "zh-CN",
            daysOfWeekHighlighted: "0,6",
            weekStart: 0
        });
    }
    setDatePicker();

    $('[name="businessType"]').on("change", function (e) {
        e.preventDefault();

        var val = $(this).val();

        if (val == 1) {
            $(".block-training").show();
            $(".block-venue").hide();
        } else {
            $(".block-training").hide();
            $(".block-venue").show();
        }
    });
    $('[name="businessType"]').trigger("change");

    function appendList(businessNo) {
        $.getJSON('/admin/finance/getFinance', {businessNo: businessNo}, function (res) {
            var data = res.data;
            if (res.code == 1 && data.orgFinanceData) {
                $(".business-list tbody").append(
                    '<tr><td>' + data.orgFinanceData.businessNo +'</td>' +
                    '<td>' + data.orgFinanceData.businessDate +'</td>' +
                    '<td>' + data.orgFinanceData.businessType +'</td>' +
                    '<td>' + data.orgFinanceData.venueId +'</td>' +
                    '<td>' + data.orgFinanceData.userId +'</td>' +
                    '<td>' + data.orgFinanceData.channelType +'</td>' +
                    '<td>' + data.orgFinanceData.pipelineValue +'</td>' +
                    '<td>' + data.orgFinanceData.incomeValue +'</td>' +
                    '<td>' + data.orgFinanceData.registerCount +'</td>' +
                    '<td>' + data.orgFinanceData.classCount +'</td>' +
                    '<td>' + data.orgFinanceData.accessCount +'</td>' +
                    '<td>' + data.orgFinanceData.businessCount +'</td>' +
                    '</tr>'
                );
            } else {
                jqueryAlert({
                    'icon'      : '/Content/images/icon-error.png',
                    'content'   : "查询数据失败，请稍后重试",
                    'closeTime' : 2000,
                    'modal'        : true,
                    'isModalClose' : true
                });
            }
        });
    }

    // 基地变化，联动用户变化
    $("[name='venueId']").on("change", function (e) {
        e.preventDefault();

        var venueId = $(this).val();

        $.getJSON('/admin/finance/getEmployeeByVenue', {venueId: venueId}, function (res) {
            var data = res.data;
            var tpl = '<option value="$userId">$userName</option>';
            var html = [];

            if (res.code == 1) {
                !!data.orgFinanceUsersList && data.orgFinanceUsersList.forEach(function (item) {
                    html.push(
                        tpl.replace("$userId", item.id)
                            .replace("$userName", item.realName)
                    );
                });
                $("[name='userId']").html(html.join(""));
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
    $("[name='venueId']").trigger("change");

    // 保存运营数据
    $(".save-business").on("click", function (e) {
        e.preventDefault();

        var $form = $("#business_form");
        var conditions = $form.serialize();

        if ($form.attr("submitting") == "submitting" || !$form.valid()) {
            return false;
        }
        $form.attr("submitting", "submitting");

        $.post('/admin/finance/saveFinance', conditions, function (res) {
            $form.attr("submitting", "");

            if (res.code == 1) {
                jqueryAlert({
                    'icon'      : '/Content/images/icon-ok.png',
                    'content'   : "保存运营财务数据成功",
                    'closeTime' : 2000,
                    'modal'        : true,
                    'isModalClose' : true
                });

                if ($("#business_form").find('[name="businessNo"]').val() == "") {
                    $("#business_form").find('[name="pipelineValue"]').val("");
                    $("#business_form").find('[name="incomeValue"]').val("");
                    $("#business_form").find('[name="registerCount"]').val("");
                    $("#business_form").find('[name="classCount"]').val("");
                    $("#business_form").find('[name="accessCount"]').val("");
                    $("#business_form").find('[name="businessCount"]').val("");
                    $("#business_form").find('[name="nullTotalCount"]').val("");
                    $("#business_form").find('[name="nullCount"]').val("");
                    $("#business_form").find('[name="hotTotalCount"]').val("");
                    $("#business_form").find('[name="hotCount"]').val("");

                    appendList(res.data.businessNo);
                } else {
                    window.setTimeout(function () {
                        window.location.href = '/admin/finance/log';
                    }, 1500);
                }
            } else {
                jqueryAlert({
                    'icon'      : '/Content/images/icon-error.png',
                    'content'   : "保存运营财务数据失败, 请稍后重试",
                    'closeTime' : 2000,
                    'modal'        : true,
                    'isModalClose' : true
                });
            }
        });
    });
});
