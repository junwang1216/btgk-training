define(["jquery"], function ($) {
    'use strict';

    // 检索
    $(".btn-search").on("click", function (e) {
        e.preventDefault();

        var $form = $(".form-search");
        var $txt = $(".txt-search");

        if ($txt.val().trim().length === 0) {
            return alert("请输入检索的关键字！");
        }
        $(".txt-search").val($txt.val().trim());

        window.location.href = '/document/search?' + $form.serialize();
    });

    // 登录
    $(".link-login").on("click", function (e) {
        e.preventDefault();

        $("#login_modal").fadeIn().show();
    });
    $(".btn-login").on("click", function (e) {
        e.preventDefault();

        var $form = $(".form-login");

        $("#login_modal").fadeOut().hide();
        window.location.reload();
    });
});
