var Vue = require('vue'),
    VueResource = require('resource'),
    Loading = require('loading');

Vue.use(VueResource);
var loading = new Loading().$mount().$appendTo('body');

Vue.http.interceptors.push(function (request, next) {
    if (request.timeout === 0) {
        request.timeout = 30 * 1000; // 30秒超时
    }
    if (!request.notShowLoading) {
        loading.show();
    }
    next(function (response) {
        if (!request.notShowLoading) {
            loading.hide();
        }
        if (response.status == 0) {
            response.statusText = '连接超时';
        }
        if (response.status !== 200) {
            var status = response.status;
            return request.respondWith(JSON.stringify({
                status: status,
                message: response.statusText
            }), {
                status: 200,
                statusText: response.statusText
            });
        }
        return response;
    });
});

Vue.trim = function (str) {
    return (str + '').replace(/^\s+|\s+$/ig, '');
};

// 页面跳转
Vue.assignUrl = function (url) {
    var ch = url.indexOf("?") > -1 ? "&" : "?";

    window.location.href = url + ch + '__t' + (new Date().getTime());
};

// 格式化金额，金额+精度
Vue.formatMoney = function (amount, n) {
    n = n > 0 && n <= 20 ? n : 2;

    amount = parseFloat((amount + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";

    var l = amount.split(".")[0].split("").reverse(),
        r = amount.split(".")[1],
        t = "";
    for(var i = 0; i < l.length; i ++ ) {
        t += l[i] + ((i + 1) % 3 === 0 && (i + 1) != l.length ? "," : "");
    }

    return t.split("").reverse().join("") + "." + r;
};

// 格式化时间
Vue.formatDate = function (value) {
    function add0(m) {
        return m < 10 ? '0' + m : m;
    }

    var time = new Date(parseInt(value));
    var y = time.getFullYear();
    var m = time.getMonth() + 1;
    var d = time.getDate();

    return y + '-' + add0(m) + '-' + add0(d);
};

// 格式化时间
Vue.formatDateTime = function (value) {
    function add0(m) {
        return m < 10 ? '0' + m : m;
    }

    var time = new Date(parseInt(value));
    var y = time.getFullYear();
    var m = time.getMonth() + 1;
    var d = time.getDate();
    var h = time.getHours();
    var mm = time.getMinutes();
    var s = time.getSeconds();

    return y + '-' + add0(m) + '-' + add0(d) + " " + add0(h) + ":" + add0(mm) + ":" + add0(s);
};

Vue.qs = {
    parse: function (qs) {
        if (typeof qs !== 'string') {
            return qs;
        }

        var pairs = qs.split('&'), result = {};
        for(var i = 0; i < pairs.length; i++) {
            var line = pairs.split('=');
            if (line.length > 1) {
                result[line[0]] = line.slice(1).join('=');
            }
        }
        return result;
    },
    stringify: function (obj) {
        if (typeof obj !== 'object') {
            return obj;
        }

        var qs = [];
        for (var i in obj) {
            if (typeof obj[i] !== 'object') {
                qs.push(i + '=' + encodeURIComponent(obj[i]));
            }
        }
        return qs.join('&');
    }
};

Vue.fn = {
    extend: function (parent, child) {
        for(var i in child) {
            parent[i] = child[i];
        }
        return parent;
    }
};

// 筛选器
// 日期格式化
Vue.filter('date', function (value) { //value为13位的时间戳
    return Vue.formatDate(value);
});

// 日期时间格式化
Vue.filter('datetime', function (value) { //value为13位的时间戳
    return Vue.formatDateTime(value);
});

// 格式化金额，精度
Vue.filter('money', function (value, n) { //value数字
    return Vue.formatMoney(value, n);
});

// 全局loading
Vue.loading = loading;

module.exports = Vue;
