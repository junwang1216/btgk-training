var Vue = require('common');
var tpl = require('./sms.html');
Vue.http.options.emulateJSON = true; // 发送formData
Vue.component('sms', {
    template: tpl,
    props: {
        mobile: {
            type: String,
            require: true
        },
        url: {
            type: String,
            require: true
        },
        validMobile: {
            type: Boolean,
            require: true,
            default: true
        }
    },
    data: function () {
        return {
            isTimer: false,
            count: 60,  // 重新发送短信间隔时间
            btnText: '获取验证码'
        };
    },
    methods: {
        sendSms: function () {
            if (this.isTimer) {
                return;
            }
            var that = this;
            if (!this.validMobile || this.mobile && /^[1][3,4,5,6,7,8, 9]\d{9}$/ig.test(this.mobile)) {
                this.timer();
                this.$http.get(that.url + '?' + Vue.qs.stringify({
                    mobile: that.mobile
                }))
                .then(function (res) {
                    return res.json();
                })
                .then(function (result) {
                    if (result.status !== 200) {
                        that.clearTimer();
                        that.$dispatch('error', new Error(result.message));
                    }
                }, function (err) {
                    if (err) {
                        that.$dispatch('error', new Error('服务器错误，请稍后重试！'));
                        that.clearTimer();
                    }
                });
            } else {
                this.$dispatch('error', new Error('手机号码格式不正确'));
            }
        },
        timer: function () {
            this.timer.count = this.count;
            this.isTimer = true;
            this.$dispatch('start-timer');
            var that = this;
            this.btnText = --this.count + 's';
            this.timer._t = setTimeout(function () {
                if (that.count === 0) {
                    that.clearTimer();
                    return;
                }
                that.btnText = --that.count + 's';
                that.timer._t = setTimeout(arguments.callee, 1000);
            }, 1000);
        },
        clearTimer: function () {
            this.isTimer = false;
            this.$dispatch('end-timer');
            this.count = this.timer.count;
            this.btnText = '重新获取';
            clearTimeout(this.timer._t);
        }
    }
});
