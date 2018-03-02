var Vue = require('vue');
Vue.directive('validate', {
    params: ['dataReg', 'pattern', 'name'],
    bind: function () {
        this.test = function (val) {
            var reg = new RegExp(this.params.pattern || '', 'ig');
            if (reg.test(val)) {
                this.vm.$emit('validate', true, {name: this.params.name, message: this.params.dataReg, zIndex: this.el.tabIndex});
            } else {
                this.vm.$emit('validate', false, {name: this.params.name, message: this.params.dataReg, zIndex: this.el.tabIndex});
            }
        };
    },
    update: function (val, oldVal) {
        this.test(val, oldVal);
    },
    unbind: function () {

    }
});