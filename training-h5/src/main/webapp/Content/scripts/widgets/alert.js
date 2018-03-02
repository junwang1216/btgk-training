module.exports = (function () {
    var Vue = require('vue');
    var Alert = Vue.component('alert', {
        template: '<div class="alert-bg" v-show="show"></div><div class="alert alert-common" v-show="show"><div class="alert-body"><p v-html="content"></p></div><div class="alert-footer btn-alert" v-show="isAlert"><a class="alert-iknow" href="javascript:;" v-on:click.stop.prevent="iknow">知道了</a></div><div class="alert-footer btn-confirm" v-show="isConfirm"><a href="javascript:;">确定</a><a href="javascript:;">取消</a></div></div>',
        data: function () {
            return {
                show: false,
                isAlert: true,
                isConfirm: false,
                content: '',
                cb: function () {}
            };
        },
        methods: {
            alert: function (text, cb) {
                this.show = true;
                this.content = text;
                if (cb) {
                    this.cb  = cb;
                }
                this.$dispatch('alert-show');
            },
            iknow: function () {
                this.content = '';
                this.show = false;
                if (this.cb) {
                    this.cb();
                }
                this.$dispatch('alert-hide');
            }
        }
    });

    return Alert;
})();