module.exports = (function () {
    var Vue = require('vue'), tpl = require('./template.html');

    Vue.component('step', {
        template: tpl,
        props: ['step', 'show'],
        data: function () {
            return {
                stepsA: {
                    show  : false,
                    items : ["银行卡验证", "补充信息", "查看结果"]
                },
                stepsB: {
                    show  : false,
                    items : ["实名验证", "银行卡验证", "补充信息", "查看结果"]
                }
            };
        },
        ready: function () {
            this.stepsA.show = (this.show == "A");
            this.stepsB.show = (this.show == "B");
        },
        methods: {}
    });
})();
