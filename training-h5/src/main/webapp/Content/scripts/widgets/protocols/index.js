module.exports = (function () {
    var Vue = require('vue'), tpl = require('./template.html');

    Vue.component('protocols', {
        template: tpl,
        props: ['partner', 'repay'],
        data: function () {
            return {
                isShow: false,
                protocolUrls: [],
                protocols: {
                    elong: {
                        "800101" : {
                            name: "《读秒注册及使用协议》",
                            id: "800101",
                            redirect: "/elong/credit/contract/getLink?cid=800101",
                            status: false
                        },
                        "800103" : {
                            name: "《还款管理服务协议》",
                            id: "800103",
                            redirect: "/elong/credit/contract/getLink?cid=800103",
                            status: false
                        },
                        "800102" : {
                            name: "《个人信息采集授权声明》",
                            id: "800102",
                            redirect: "/elong/credit/contract/getLink?cid=800102",
                            status: false
                        },
                        "800104" : {
                            name: "《额度服务协议》",
                            id: "800104",
                            redirect: "/elong/credit/contract/getLink?cid=800104",
                            status: false
                        }
                    },
                    zhenpin: {
                        "800301" : {
                            name: "《读秒注册及使用协议》",
                            id: "800301",
                            redirect: "/zhenpin/credit/contract/getLink?cid=800301",
                            status: false
                        },
                        "800303" : {
                            name: "《还款管理服务协议》",
                            id: "800303",
                            redirect: "/zhenpin/credit/contract/getLink?cid=800303",
                            status: false
                        },
                        "800302" : {
                            name: "《个人信息采集授权声明》",
                            id: "800302",
                            redirect: "/zhenpin/credit/contract/getLink?cid=800302",
                            status: false
                        },
                        "800304" : {
                            name: "《额度服务协议》",
                            id: "800304",
                            redirect: "/zhenpin/credit/contract/getLink?cid=800304",
                            status: false
                        }
                    }
                }
            };
        },
        ready: function () {
            var pro = this.protocols[this.partner];

            this.protocolUrls.length = 0;
            if (this.partner === "zhenpin") {
                this.protocolUrls.push(pro["800301"]);
                if (this.repay === "show") {
                    this.protocolUrls.push(pro["800303"]);
                }
                this.protocolUrls.push(pro["800302"]);
                this.protocolUrls.push(pro["800304"]);
            } else {
                this.protocolUrls.push(pro["800101"]);
                if (this.repay === "show") {
                    this.protocolUrls.push(pro["800103"]);
                }
                this.protocolUrls.push(pro["800102"]);
                this.protocolUrls.push(pro["800104"]);
            }
        },
        methods: {
            show: function () {
                this.isShow = true;
            },
            hide: function () {
                this.isShow = false;
            }
        }
    });
})();
