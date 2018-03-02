(function ($) {
    $('.input-container input').keydown(function(){
        $(this).parent().next('.delete').show();
    });
    $('.input-container input').focus(function(){
        var Text = $(this).val();
        if(Text != ""){
            $(this).parent().next('.delete').show();
        }else{
            $(this).parent().next('.delete').hide();
        }
    });
    // $('.input-container input').blur(function(){
    //    $(this).parent().next('.delete').hide();
    // });

    $('.input-container .delete').on('click',function(){
        $(this).prev('.flex').find('input').val('');
        $(this).hide();
    });

    // 登录跳转
    $(".to-login").on("click", function (e) {
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

        $.post('/student/passport/submitLogin', conditions, function (res) {
            $this.attr("submitting", "");

            if (res.code == 1) {
                location.assign("/student/center/index");
            } else {
                console.log(res.message || "用户登录失败, 请稍后重试");
                alert(res.message || "用户登录失败, 请稍后重试");
            }
        });
    });
})(jQuery);
