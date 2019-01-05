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

    // 时间日期设置
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
            $(".block-training").show();
            $(".block-venue").hide();
        } else {
            $(".block-training").hide();
            $(".block-venue").show();
        }

        $(".block-training-venue").find("input").val("");
    });
    $('[name="businessType"]').trigger("change");

    // 流水
    $(".flow-list").on("click", ".btn-delete", function (e) {
        e.preventDefault();

        var content = this;
        var businessNo = $(content).attr("data-business");
        var dialog;

        if (dialog) {
            return dialog.show();
        }
        dialog = jqueryAlert({
            'title'   : '警告',
            'content' : '您确定要删除数据？',
            'modal'   : true,
            'buttons' : {
                '确定' : function () {
                    dialog.close();

                    $.post('/admin/finance/deleteOrgFinanceDataFlow', {businessNo: businessNo}, function (res) {
                        if (res.code == 1) {
                            jqueryAlert({
                                'icon'      : '/Content/images/icon-ok.png',
                                'content'   : "删除流水数据成功",
                                'closeTime' : 2000,
                                'modal'        : true,
                                'isModalClose' : true
                            });

                            $(content).parents("tr").remove();
                        } else {
                            jqueryAlert({
                                'icon'      : '/Content/images/icon-error.png',
                                'content'   : "删除流水数据失败，请稍后重试",
                                'closeTime' : 2000,
                                'modal'        : true,
                                'isModalClose' : true
                            });
                        }
                    });
                },
                '取消' : function () {
                    dialog.close();
                }
            }
        });
    });

    function getOrgFinanceDataFlowList(businessNo) {
        $.getJSON('/admin/finance/getOrgFinanceDataFlow', {businessNo: businessNo}, function (res) {
            var data = res.data;
            if (res.code == 1 && data.orgFinanceDataFlow) {
                var business = data.orgFinanceDataFlow;
                    $(".flow-list tbody").append(
                        '<tr><td>' + business.businessNo +'</td>' +
                        '<td>' + business.businessDate +'</td>' +
                        '<td>' + business.businessTitle +'</td>' +
                        '<td>' + business.venueName +'</td>' +
                        '<td>' + business.realName +'</td>' +
                        '<td>' + business.channelName +'</td>' +
                        '<td>$' + $.moneyFormat(business.pipelineValue, 0, ".", ",") +'</td>' +
                        '<td>' +
                        '<a href="javascript:;" class="btn btn-danger btn-sm btn-delete" title="删除" data-business="' + business.businessNo + '">' +
                        '<i class="fa fa-trash"></i> 删除</a>' +
                        '</td></tr>'
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

    // 体验数
    $(".business-list").on("click", ".btn-delete", function (e) {
        e.preventDefault();

        var content = this;
        var businessNo = $(content).attr("data-business");
        var dialog;

        if (dialog) {
            return dialog.show();
        }
        dialog = jqueryAlert({
            'title'   : '警告',
            'content' : '您确定要删除数据？',
            'modal'   : true,
            'buttons' : {
                '确定' : function () {
                    dialog.close();

                    $.post('/admin/finance/deleteOrgFinanceDataBusiness', {businessNo: businessNo}, function (res) {
                        if (res.code == 1) {
                            jqueryAlert({
                                'icon'      : '/Content/images/icon-ok.png',
                                'content'   : "删除体验数据成功",
                                'closeTime' : 2000,
                                'modal'        : true,
                                'isModalClose' : true
                            });

                            $(content).parents("tr").remove();
                        } else {
                            jqueryAlert({
                                'icon'      : '/Content/images/icon-error.png',
                                'content'   : "删除体验数据失败，请稍后重试",
                                'closeTime' : 2000,
                                'modal'        : true,
                                'isModalClose' : true
                            });
                        }
                    });
                },
                '取消' : function () {
                    dialog.close();
                }
            }
        });
    });

    function getOrgFinanceDataBusinessList(businessNo) {
        $.getJSON('/admin/finance/getOrgFinanceDataBusiness', {businessNo: businessNo}, function (res) {
            var data = res.data;
            if (res.code == 1 && data.orgFinanceDataBusiness) {
                var business = data.orgFinanceDataBusiness;
                $(".business-list tbody").append(
                    '<tr><td>' + business.businessNo +'</td>' +
                    '<td>' + business.businessDate +'</td>' +
                    '<td>' + business.businessTitle +'</td>' +
                    '<td>' + business.venueName +'</td>' +
                    '<td>' + business.realName +'</td>' +
                    '<td>' + business.channelName +'</td>' +
                    '<td>' + business.accessCount +'</td>' +
                    '<td>' + business.businessCount +'</td>' +
                    '<td>' +
                    '<a href="javascript:;" class="btn btn-danger btn-sm btn-delete" title="删除" data-business="' + business.businessNo + '">' +
                    '<i class="fa fa-trash"></i> 删除</a>' +
                    '</td></tr>'
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

    // 收入
    $(".income-list").on("click", ".btn-delete", function (e) {
        e.preventDefault();

        var content = this;
        var businessNo = $(content).attr("data-business");
        var dialog;

        if (dialog) {
            return dialog.show();
        }
        dialog = jqueryAlert({
            'title'   : '警告',
            'content' : '您确定要删除数据？',
            'modal'   : true,
            'buttons' : {
                '确定' : function () {
                    dialog.close();

                    $.post('/admin/finance/deleteOrgFinanceDataIncome', {businessNo: businessNo}, function (res) {
                        if (res.code == 1) {
                            jqueryAlert({
                                'icon'      : '/Content/images/icon-ok.png',
                                'content'   : "删除收入数据成功",
                                'closeTime' : 2000,
                                'modal'        : true,
                                'isModalClose' : true
                            });

                            $(content).parents("tr").remove();
                        } else {
                            jqueryAlert({
                                'icon'      : '/Content/images/icon-error.png',
                                'content'   : "删除收入数据失败，请稍后重试",
                                'closeTime' : 2000,
                                'modal'        : true,
                                'isModalClose' : true
                            });
                        }
                    });
                },
                '取消' : function () {
                    dialog.close();
                }
            }
        });
    });

    function getOrgFinanceDataIncomeList(businessNo) {
        $.getJSON('/admin/finance/getOrgFinanceDataIncome', {businessNo: businessNo}, function (res) {
            var data = res.data;
            if (res.code == 1 && data.orgFinanceDataIncome) {
                var business = data.orgFinanceDataIncome;
                $(".income-list tbody").append(
                    '<tr><td>' + business.businessNo +'</td>' +
                    '<td>' + business.businessDate +'</td>' +
                    '<td>' + business.businessTitle +'</td>' +
                    '<td>' + business.venueName +'</td>' +
                    '<td>' + business.realName +'</td>' +
                    '<td>' + business.incomeType +'</td>' +
                    '<td>$' + (business.incomePerValue || "--") +'</td>' +
                    '<td>$' + $.moneyFormat(business.incomeValue, 0, ".", ",") + '</td>' +
                    '<td>' +
                    '<a href="javascript:;" class="btn btn-danger btn-sm btn-delete" title="删除" data-business="' + business.businessNo + '">' +
                    '<i class="fa fa-trash"></i> 删除</a>' +
                    '</td></tr>'
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

    // 签到
    $(".attendance-list").on("click", ".btn-delete", function (e) {
        e.preventDefault();

        var content = this;
        var businessNo = $(content).attr("data-business");
        var dialog;

        if (dialog) {
            return dialog.show();
        }
        dialog = jqueryAlert({
            'title'   : '警告',
            'content' : '您确定要删除数据？',
            'modal'   : true,
            'buttons' : {
                '确定' : function () {
                    dialog.close();

                    $.post('/admin/finance/deleteOrgFinanceDataAttendance', {businessNo: businessNo}, function (res) {
                        if (res.code == 1) {
                            jqueryAlert({
                                'icon'      : '/Content/images/icon-ok.png',
                                'content'   : "删除到课数据成功",
                                'closeTime' : 2000,
                                'modal'        : true,
                                'isModalClose' : true
                            });

                            $(content).parents("tr").remove();
                        } else {
                            jqueryAlert({
                                'icon'      : '/Content/images/icon-error.png',
                                'content'   : "删除到课数据失败，请稍后重试",
                                'closeTime' : 2000,
                                'modal'        : true,
                                'isModalClose' : true
                            });
                        }
                    });
                },
                '取消' : function () {
                    dialog.close();
                }
            }
        });
    });

    function getOrgFinanceDataAttendanceList(businessNo) {
        $.getJSON('/admin/finance/getOrgFinanceDataAttendance', {businessNo: businessNo}, function (res) {
            var data = res.data;
            if (res.code == 1 && data.orgFinanceDataAttendance) {
                var business = data.orgFinanceDataAttendance;
                $(".attendance-list tbody").append(
                    '<tr><td>' + business.businessNo +'</td>' +
                    '<td>' + business.businessDate +'</td>' +
                    '<td>' + business.businessTitle +'</td>' +
                    '<td>' + business.venueName +'</td>' +
                    '<td>' + business.realName +'</td>' +
                    '<td>' + business.incomeType +'</td>' +
                    '<td>' + business.registerCount +'</td>' +
                    '<td>' + business.classCount +'</td>' +
                    '<td>' +
                    '<a href="javascript:;" class="btn btn-danger btn-sm btn-delete" title="删除" data-business="' + business.businessNo + '">' +
                    '<i class="fa fa-trash"></i> 删除</a>' +
                    '</td></tr>'
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

    // 闲忙时
    $(".times-list").on("click", ".btn-delete", function (e) {
        e.preventDefault();

        var content = this;
        var businessNo = $(content).attr("data-business");
        var dialog;

        if (dialog) {
            return dialog.show();
        }
        dialog = jqueryAlert({
            'title'   : '警告',
            'content' : '您确定要删除数据？',
            'modal'   : true,
            'buttons' : {
                '确定' : function () {
                    dialog.close();

                    $.post('/admin/finance/deleteOrgFinanceDataTimes', {businessNo: businessNo}, function (res) {
                        if (res.code == 1) {
                            jqueryAlert({
                                'icon'      : '/Content/images/icon-ok.png',
                                'content'   : "删除闲忙时段数据成功",
                                'closeTime' : 2000,
                                'modal'        : true,
                                'isModalClose' : true
                            });

                            $(content).parents("tr").remove();
                        } else {
                            jqueryAlert({
                                'icon'      : '/Content/images/icon-error.png',
                                'content'   : "删除闲忙时段数据失败，请稍后重试",
                                'closeTime' : 2000,
                                'modal'        : true,
                                'isModalClose' : true
                            });
                        }
                    });
                },
                '取消' : function () {
                    dialog.close();
                }
            }
        });
    });

    function getOrgFinanceDataTimesList(businessNo) {
        $.getJSON('/admin/finance/getOrgFinanceDataTimes', {businessNo: businessNo}, function (res) {
            var data = res.data;
            if (res.code == 1 && data.orgFinanceDataTimes) {
                var business = data.orgFinanceDataTimes;
                $(".times-list tbody").append(
                    '<tr><td>' + business.businessNo +'</td>' +
                    '<td>' + business.businessDate +'</td>' +
                    '<td>' + business.businessTitle +'</td>' +
                    '<td>' + business.venueName +'</td>' +
                    '<td>' + business.realName +'</td>' +
                    '<td>' + business.nullTotalCount +'</td>' +
                    '<td>' + business.nullCount +'</td>' +
                    '<td>' + business.hotTotalCount +'</td>' +
                    '<td>' + business.hotCount +'</td>' +
                    '<td>' +
                    '<a href="javascript:;" class="btn btn-danger btn-sm btn-delete" title="删除" data-business="' + business.businessNo + '">' +
                    '<i class="fa fa-trash"></i> 删除</a>' +
                    '</td></tr>'
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
    var isFirstLoad = true;
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

        $.post('/admin/finance/saveOrgFinanceLogResponse', conditions, function (res) {
            $form.attr("submitting", "");

            var data = res.data;
            if (res.code == 1) {
                jqueryAlert({
                    'icon'      : '/Content/images/icon-ok.png',
                    'content'   : "保存运营财务数据成功",
                    'closeTime' : 2000,
                    'modal'        : true,
                    'isModalClose' : true
                });

                if (data.resultFlow > 0) {
                    getOrgFinanceDataFlowList(data.businessNo);
                }
                if (data.resultBusiness > 0) {
                    getOrgFinanceDataBusinessList(data.businessNo)
                }
                if (data.resultIncome > 0) {
                    getOrgFinanceDataIncomeList(data.businessNo)
                }
                if (data.resultAttendance > 0) {
                    getOrgFinanceDataAttendanceList(data.businessNo)
                }
                if (data.resultTimes > 0) {
                    getOrgFinanceDataTimesList(data.businessNo)
                }
                $(".block-training-venue").find("input").val("");
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
