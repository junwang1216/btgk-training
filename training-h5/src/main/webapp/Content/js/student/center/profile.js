(function ($) {

    // 提交信息
    $(".save-user").on("click", function (e) {
        e.preventDefault();

        var $this = $(this);
        var conditions = {
            id: $('input[name="id"]').val(),
            realName: $('input[name="realName"]').val(),
            mobile: $('input[name="mobile"]').val(),
            sex: $('input[name="sex"]').val(),
            birthday: $('input[name="birthday"]').val(),
            weight: $('input[name="weight"]').val(),
            height: $('input[name="height"]').val()
        };

        if ($this.attr("submitting") == "submitting") {
            return false;
        }
        $this.attr("submitting", "submitting");

        $.post('/student/center/saveProfile', conditions, function (res) {
            $this.attr("submitting", "");

            if (res.code == 1) {
                $.toast("用户保存成功");
            } else {
                console.log(res.message || "保存信息失败, 请稍后重试");
                $.toast(res.message || "保存信息失败, 请稍后重试", "cancel");
            }
        });
    });
})(jQuery);
