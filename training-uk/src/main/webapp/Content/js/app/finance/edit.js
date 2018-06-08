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
        /**
         * <input type="text" value="" name="cus" id="cus" data-val="true" data-val-reqgroup="注册人数和到课人数必须同时输入" data-val-reqgroup-element="123"/>
         * <span data-valmsg-for="cus" data-valmsg-replace="true"></span>
         */
        $.validator.addMethod('reqgroup', function (value, element, params) {
            return (value == "" && document.getElementsByName(params["element"])[0].value == "") ||
                (value != "" && document.getElementsByName(params["element"])[0].value != "");
        });

        $.validator.unobtrusive.adapters.add("reqgroup", ["element"], function (options) {
            options.rules["reqgroup"] = {
                element: options.params.element
            };
            options.messages["reqgroup"] = options.message;
        });

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

    if ($('input[name="businessDate"]').val() == "") {
        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        month = (month < 10 ? "0" : "") + month;
        var day = date.getDate();
        day = (day < 10 ? "0" : "") + day;

        $('input[name="businessDate"]').val(year + "-" + month + "-" + day);
    }

    // 业务类型
    $('[name="businessType"]').on("change", function (e) {
        e.preventDefault();

        var val = $(this).val();

        if (val == 1) {
            $(".block-training").show().find("input").val("0");
            $(".block-venue").hide().find("input").val("0");

            $(".business-list").show();
            $(".venue-list").hide();
        } else {
            $(".block-training").hide().find("input").val("0");
            $(".block-venue").show().find("input").val("0");

            $(".business-list").hide();
            $(".venue-list").show();
        }
    });
    $('[name="businessType"]').trigger("change");

    function appendList(businessNo) {
        $.getJSON('/admin/finance/getFinance', {businessNo: businessNo}, function (res) {
            var data = res.data;
            if (res.code == 1 && data.orgFinanceData) {
                if ($('[name="businessType"]').val() == 1) {
                    $(".business-list tbody").append(
                        '<tr><td>' + data.orgFinanceData.businessNo +'</td>' +
                        '<td>' + data.orgFinanceData.businessDate +'</td>' +
                        '<td>' + data.orgFinanceData.businessTitle +'</td>' +
                        '<td>' + data.orgFinanceData.venueName +'</td>' +
                        '<td>' + data.orgFinanceData.realName +'</td>' +
                        '<td>' + data.orgFinanceData.channelName +'</td>' +
                        '<td>' + (data.orgFinanceData.pipelineValue || "--") +'</td>' +
                        '<td>' + (data.orgFinanceData.incomeValue || "--") +'</td>' +
                        '<td>' + (data.orgFinanceData.registerCount || "--") +'</td>' +
                        '<td>' + (data.orgFinanceData.classCount || "--") +'</td>' +
                        '<td>' + (data.orgFinanceData.accessCount || "--") +'</td>' +
                        '<td>' + (data.orgFinanceData.businessCount || "--") +'</td>' +
                        '<td><a href="javascript:;" class="btn btn-danger btn-sm" title="删除" data-business="' + data.orgFinanceData.businessNo + '"><i class="fa fa-trash"></i> 删除</a></td></tr>'
                    );
                }
                else {
                    $(".venue-list tbody").append(
                        '<tr><td>' + data.orgFinanceData.businessNo +'</td>' +
                        '<td>' + data.orgFinanceData.businessDate +'</td>' +
                        '<td>' + data.orgFinanceData.businessTitle +'</td>' +
                        '<td>' + data.orgFinanceData.venueName +'</td>' +
                        '<td>' + data.orgFinanceData.realName +'</td>' +
                        '<td>' + data.orgFinanceData.channelName +'</td>' +
                        '<td>' + (data.orgFinanceData.pipelineValue || "--") +'</td>' +
                        '<td>' + (data.orgFinanceData.incomeValue || "--") +'</td>' +
                        '<td>' + (data.orgFinanceData.nullCount || "--") +'</td>' +
                        '<td>' + (data.orgFinanceData.nullTotalCount || "--") +'</td>' +
                        '<td>' + (data.orgFinanceData.hotCount || "--") +'</td>' +
                        '<td>' + (data.orgFinanceData.hotTotalCount || "--") +'</td>' +
                        '<td><a href="javascript:;" class="btn btn-danger btn-sm" title="删除" data-business="' + data.orgFinanceData.businessNo + '"><i class="fa fa-trash"></i> 删除</a></td></tr>'
                    );
                }
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
    var isFirstLoad = true;
    $("[name='venueId']").on("change", function (e) {
        e.preventDefault();

        var venueId = $(this).val();
        var $this = $(this);

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

                if (isFirstLoad && $("[name='userId']").attr("data-default")) {
                    isFirstLoad = false;
                    $("[name='userId']").val($("[name='userId']").attr("data-default"));
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
                    $("#business_form").find('[name="pipelineValue"]').val("0");
                    $("#business_form").find('[name="incomeValue"]').val("0");
                    $("#business_form").find('[name="registerCount"]').val("0");
                    $("#business_form").find('[name="classCount"]').val("0");
                    $("#business_form").find('[name="accessCount"]').val("0");
                    $("#business_form").find('[name="businessCount"]').val("0");
                    $("#business_form").find('[name="nullTotalCount"]').val("0");
                    $("#business_form").find('[name="nullCount"]').val("0");
                    $("#business_form").find('[name="hotTotalCount"]').val("0");
                    $("#business_form").find('[name="hotCount"]').val("0");

                    appendList(res.data.businessNo);
                } else {
                    window.setTimeout(function () {
                        window.location.href = '/admin/finance/log?busType=' + $("#business_form").find('[name="channelType"]').val();
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
