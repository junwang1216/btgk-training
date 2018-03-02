(function () {
    var Vue = require('common'), Alert = require('alert');
    var alt = new Alert().$mount().$appendTo('.container');

    Vue.component('main', {
        template: '#main',
        props: [],
        data: function () {
            return {
                mobile : '',
                password: ''
            };
        },
        ready: function () {},
        events: {},
        methods: {
            submit: function () {
                var content = this;

                var conditions = {};
                conditions.mobile = content.mobile;
                conditions.password = content.password;

                content.$http.post('/student/passport/submitLogin', conditions)
                    .then(function (res) {
                        return res.json();
                    })
                    .then(function (result) {
                        console.log(result);
                    }, function (err) {
                        alt.alert(err.message);
                    });
            }
        }
    });

    new Vue({
        el: '.container',
        events: {}
    });

})();
