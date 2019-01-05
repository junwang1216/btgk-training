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
        }
    },  // 依赖关系
    waitSeconds: 0,
    urlArgs: '_=' + new Date().getTime()
});

require(['jquery', 'alert', 'override', 'bootstrap', 'base', "datepicker", "datepicker-zh"], function ($, jqueryAlert) {
    'use strict';

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
                            window.setTimeout(function () {
                                window.location.reload();
                            }, 1000);
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

    // 检索
    $(".search-business").on("click", function (e) {
        e.preventDefault();

        var $form = $("#business_form");
        var conditions = $form.serialize();

        window.location.assign("/admin/finance/list?" + conditions);
    });
});
