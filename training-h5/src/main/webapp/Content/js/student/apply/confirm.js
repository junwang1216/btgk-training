(function ($) {

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

    // 提交信息
    $("#confirm_apply").on("click", function (e) {
        e.preventDefault();

        var $this = $(this);
        var conditions = {
            classId: $('input[name="classId"]').val(),
            realName: $('input[name="realName"]').val(),
            mobile: $('input[name="mobile"]').val(),
            sex: $('input[name="sex"]').val(),
            birthday: $('input[name="birthday"]').val(),
            weight: $('input[name="weight"]').val(),
            height: $('input[name="height"]').val()
        };

        if ($('input[name="studentId"]').length > 0) {
            conditions.studentId = $('input[name="studentId"]').val();
        }

        if ($this.attr("submitting") == "submitting") {
            return false;
        }
        $this.attr("submitting", "submitting");

        if (conditions.studentId) {
            $.post('/student/apply/addClassStudents', conditions, function (res) {
                $this.attr("submitting", "");

                if (res.code == 1) {
                    $("input[name='orderNo']").val(res.data.orderNo);
                    $("input[name='payAmount']").val(res.data.orderAmount);
                    $(".pay-amount").text(res.data.orderAmount + "元");

                    $.toast("报名成功！请支付");
                    $("#pay_confirm").popup();
                    //window.location.assign('/student/apply/state?studentId=' + conditions.studentId + '&classId=' + conditions.classId);
                } else {
                    console.log(res.message || "学员报名失败, 请稍后重试");
                    $.toast(res.message || "学员报名失败, 请稍后重试", "cancel");
                    //alert(res.message || "学员报名失败, 请稍后重试");
                }
            });
        } else {
            $.post('/student/apply/addStudents', conditions, function (stu) {
                $this.attr("submitting", "");

                if (stu.code == 1) {
                    conditions.studentId = stu.data.studentId;
                    $.post('/student/apply/addClassStudents', conditions, function (res) {
                        if (res.code == 1) {
                            $("input[name='orderNo']").val(res.data.orderNo);
                            $("input[name='payAmount']").val(res.data.orderAmount);
                            $(".pay-amount").text(res.data.orderAmount + "元");

                            $.toast("报名成功！请支付");
                            $("#pay_confirm").popup();
                            //window.location.assign('/student/apply/state?studentId=' + conditions.studentId + '&classId=' + conditions.classId);
                        } else {
                            console.log(res.message || "学员报名失败, 请稍后重试");
                            $.toast(res.message || "学员报名失败, 请稍后重试", "cancel");
                            //alert(res.message || "学员报名失败, 请稍后重试");
                        }
                    });
                } else {
                    console.log(stu.message || "学员报名失败, 请稍后重试");
                    $.toast(res.message || "学员报名失败, 请稍后重试", "cancel");
                    //alert(res.message || "学员报名失败, 请稍后重试");
                }
            });
        }
    });

    // 提交支付
    $("#confirm_pay").on("click", function (e) {
        e.preventDefault();

        var $this = $(this);
        var conditions = {
            classId: $('input[name="classId"]').val(),
            orderNo: $('input[name="orderNo"]').val(),
            payType: $('.js_grid.active').attr("data-type"),
            payAmount: $('input[name="payAmount"]').val()
        };

        if ($this.attr("submitting") == "submitting") {
            return false;
        }
        $this.attr("submitting", "submitting");

        $.post('/student/apply/pay', conditions, function (res) {
            $this.attr("submitting", "");

            if (res.code == 1) {
                $.toast("报名成功！");
                window.location.assign('/student/apply/state?classId=' + conditions.classId);
            } else {
                console.log(res.message || "学员报名失败, 请稍后重试");
                $.toast(res.message || "学员报名失败, 请稍后重试", "cancel");
                //alert(res.message || "学员报名失败, 请稍后重试");
            }
        });
    });

    $("#pay_confirm .js_grid").on("click", function (e) {
        e.preventDefault();

        $(this).addClass("active").siblings().removeClass("active");
    });
})(jQuery);
