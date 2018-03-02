(function ($) {
    // 切换
    var $iosActionsheet = $('#iosActionsheet');
    var $iosMask = $('#iosMask');

    function hideActionSheet() {
        $iosActionsheet.removeClass('weui-actionsheet_toggle');
        $iosMask.fadeOut(200);
    }

    $iosMask.on('click', hideActionSheet);
    $('#iosActionsheetCancel').on('click', hideActionSheet);
    $("#select_class").on("click", function(){
        $iosActionsheet.addClass('weui-actionsheet_toggle');
        $iosMask.fadeIn(200);
    });

    // 签到
    $(".class-list").on("click", ".class-item", function (e) {
        e.preventDefault();

        var $this = $(this);

        if ($this.attr("data-sign") == 1) return;

        var conditions = {
            inDate: $this.attr("data-date"),
            inScheduleId: $this.attr("data-schedule")
        };

        if ($this.attr("submitting") == "submitting") {
            return false;
        }
        $this.attr("submitting", "submitting");

        $.post('/student/center/signClass', conditions, function (res) {
            $this.attr("submitting", "");

            if (res.code == 1) {
                //alert("签到成功");
                window.location.reload();
            } else {
                console.log(res.message || "签到失败, 请稍后重试");
                alert(res.message || "签到失败, 请稍后重试");
            }
        });
    });
})(jQuery);
