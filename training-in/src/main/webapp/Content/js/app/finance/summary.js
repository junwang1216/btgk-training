requirejs.config({
    baseUrl: 'Content/',
    paths: {
        "jquery"    : 'bower_components/jquery/dist/jquery',
        "tether"    : 'bower_components/tether/dist/js/tether',
        "bootstrap" : 'bower_components/bootstrap/dist/js/bootstrap',
        "pace"      : 'bower_components/pace/pace',

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

require(['jquery', 'override', 'bootstrap', 'base'], function ($) {
    'use strict';

    function setOverflow() {
        var bodyWidth = $("body").width();

        var sideBarWidth = $(".sidebar").width();
        if ($("body.sidebar-hidden").length > 0) {
            sideBarWidth = 0;
        }
        var paddingWidth = 15;

        $(".table-overflow").width((bodyWidth - sideBarWidth - paddingWidth * 4) + "px");
        $(".table-children").width((bodyWidth - sideBarWidth - paddingWidth * 6) + "px");
    }
    setOverflow();

    $(".table-overflow").on("scroll", function (e) {
       e.preventDefault();

        $(".table-children").css({
            "margin-left": $(this).scrollLeft() + "px"
        });
    });

    window.addEventListener("resize", function (e) {
        e.preventDefault();

        setOverflow();
    });

    $("[name='total_students_type']").on("change", function (e) {
        e.preventDefault();

        window.location.href = "/admin/finance/summary?typeTime=" +  $("[name='total_students_type']:checked").val() +
            "&busType=" + $("#current_bus_type").val();
    });
});
