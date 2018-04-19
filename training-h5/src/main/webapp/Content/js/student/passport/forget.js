(function ($) {

    // 发送
    $(".to-send").on("click", function (e) {
        e.preventDefault();

        var $this = $(this);
        var conditions = {
            mobile: $('input[name="mobile"]').val(),
            email: $('input[name="email"]').val()
        };

        if ($this.attr("submitting") == "submitting") {
            return false;
        }
        $this.attr("submitting", "submitting");

        $.post('/student/passport/sendForgetEmail', conditions, function (res) {
            $this.attr("submitting", "");

            if (res.code == 1) {

            } else {
                console.log(res.message || "用户登录失败, 请稍后重试");
                alert(res.message || "用户登录失败, 请稍后重试");
            }
        });
    });

    // 保存
    $(".to-save").on("click", function (e) {
        e.preventDefault();

        var $this = $(this);
        var conditions = {
            mobile: $('input[name="mobile"]').val(),
            password: $('input[name="password"]').val()
        };

        if ($this.attr("submitting") == "submitting") {
            return false;
        }
        $this.attr("submitting", "submitting");

        $.post('/student/passport/saveForget', conditions, function (res) {
            $this.attr("submitting", "");

            if (res.code == 1) {

            } else {
                console.log(res.message || "用户登录失败, 请稍后重试");
                alert(res.message || "用户登录失败, 请稍后重试");
            }
        });
    });
})(jQuery);
