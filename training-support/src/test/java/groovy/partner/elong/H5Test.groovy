package groovy.partner.elong

import geb.spock.GebSpec
import groovy.Testable
import groovy.data.ElongData


class H5Test extends GebSpec implements Testable, ElongBase {

    def testFrontEndUrl() {
        when:
        def partnerUserId = System.currentTimeMillis()

        println postAsMap("http://instalment-api.qa-01.jimu.com/credit/v1/elong/apply", token([outTradeNo   : System.currentTimeMillis(),
                                                                                               creditData   : asString(ElongData.CREDIT_DATA),
                                                                                               partnerUserId: partnerUserId]))["data"]["redirectUrl"]

        println getAsMap("http://instalment-api.qa-01.jimu.com/redirect/v1/elong/loan", token([partnerUserId: partnerUserId]))["data"]["redirectUrl"]


        then:

        true
    }

    //转分期
    def testTransfer() {

        def partnerUserId = System.currentTimeMillis()
        def outTradeNo = System.currentTimeMillis()


        when:
        finishApply(partnerUserId, outTradeNo)
        def (url, result) = getApplyUrl(partnerUserId, outTradeNo)
        then:
        result == 3

        when:
        def freezeOrderId = System.currentTimeMillis()
        //冻结
        def freezeQuotaResult = freeze(partnerUserId, freezeOrderId, 6000, 0)

        then:
        freezeQuotaResult["status"] == 200
        freezeQuotaResult["data"]["resultCode"] == "1"

        when:
        //占用
        def blockQuotaResult = block(partnerUserId, freezeOrderId, 6000)

        def queryQuota = "/quota/v1/query"

        then:
        blockQuotaResult["status"] == 200
        blockQuotaResult["data"]["amount"] == 6000
        getAsMap("/quota/v1/blockStatus", token([outTradeNo: freezeOrderId, partnerUserId: partnerUserId]))["data"]["resultCode"] == "1"


        getAsMap(queryQuota, token([
                partnerUserId: partnerUserId]))["data"]["credit"] == 10000
        getAsMap(queryQuota, token([
                partnerUserId: partnerUserId]))["data"]["freeze"] == 0

        getAsMap(queryQuota, token([
                partnerUserId: partnerUserId]))["data"]["available"] == 4000

        getAsMap(queryQuota, token([
                partnerUserId: partnerUserId]))["data"]["block"] == 6000


        when:

        def loanUrl = getLoanUrl(partnerUserId)
        println loanUrl

        go loanUrl


        $("a", href: "/elong/order/unpaid?tab=0")[0].click()

        Thread.sleep(500)

        then:

        title == "读秒分期"


        when:
        go loanUrl
        $("a", href: "/elong/order/all").click()

        Thread.sleep(500)

        then:

        title == "我的订单"


        when:

        $("a")[0].click()

        $("span", class: "text", text: "6,000元").size() == 1

        Thread.sleep(500)


        then:

        title == "订单详情"

        when:

        $("div", class: "plan-item")[0].click()
        $("button", class: "to-installment").click()

        Thread.sleep(500)

        then:

        title == "选择分期方案"

        when:

        $("button", class: "confirm-installment").click()
        $("input", id: "smsCode").value("952700")
        Thread.sleep(500)
        $("a", class: ".sms-ok").click()
        Thread.sleep(500)

        then:

        title == "选择分期方案"

    }

    def testApply() {

        def partnerUserId = System.currentTimeMillis()
        def outTradeNo = System.currentTimeMillis()


        when:
        finishApply(partnerUserId, outTradeNo)
        def (url, result) = getApplyUrl(partnerUserId, outTradeNo)
        then:
        result == 3

        when:


        def queryQuota = "/quota/v1/query"


        def applyStatus = getAsMap("/credit/v1/elong/applyStatus", token([outTradeNo: outTradeNo, partnerUserId: partnerUserId]))

        def creditStatus = getAsMap("/credit/v1/status", token([partnerUserId: partnerUserId]))


        def queryQuotaResult = getAsMap(queryQuota, token([
                partnerUserId: partnerUserId]))

        then:

        applyStatus["data"]["applyResult"] == 3
        creditStatus["data"]["creditStatus"] == 1


        queryQuotaResult["status"] == 200
        queryQuotaResult["data"]["credit"] == 10000

        when:

        def (queryUrl, applyResult) = getApplyUrl(partnerUserId, outTradeNo)
        println queryUrl
        go queryUrl

        then:

        applyResult == 3
        $("h3", text: "可用额度（元）")[0] != null


        when:

        def freezeOrderId = System.currentTimeMillis()

        //冻结
        def freezeQuotaResult = freeze(partnerUserId, freezeOrderId, 3000, 3)

        then:
        freezeQuotaResult["status"] == 200
        freezeQuotaResult["data"]["resultCode"] == "1"

        getAsMap("/quota/v1/freezeStatus", token([outTradeNo: freezeOrderId, partnerUserId: partnerUserId]))["data"]["resultCode"] == "1"

        postAsMap("/quota/v1/unfreeze", token([outTradeNo: freezeOrderId, partnerUserId: partnerUserId, reqTransID: System.currentTimeMillis()]))["data"]["resultCode"] == "1"
        getAsMap("/quota/v1/unfreezeStatus", token([outTradeNo: freezeOrderId, partnerUserId: partnerUserId]))["data"]["resultCode"] == "1"


        when:


        freezeOrderId = System.currentTimeMillis()

        //冻结
        freezeQuotaResult = freeze(partnerUserId, freezeOrderId, 3000, 3)


        then:

        freezeQuotaResult["status"] == 200
        freezeQuotaResult["data"]["resultCode"] == "1"

        getAsMap(queryQuota, token([
                partnerUserId: partnerUserId]))["data"]["credit"] == 10000
        getAsMap(queryQuota, token([
                partnerUserId: partnerUserId]))["data"]["freeze"] == 3000

        getAsMap(queryQuota, token([
                partnerUserId: partnerUserId]))["data"]["available"] == 7000

        getAsMap(queryQuota, token([
                partnerUserId: partnerUserId]))["data"]["block"] == 0

        when:

        //占用
        def blockQuotaResult = block(partnerUserId, freezeOrderId, 3000)

        then:
        blockQuotaResult["status"] == 200
        blockQuotaResult["data"]["amount"] == 3000
        getAsMap("/quota/v1/blockStatus", token([outTradeNo: freezeOrderId, partnerUserId: partnerUserId]))["data"]["resultCode"] == "1"


        getAsMap(queryQuota, token([
                partnerUserId: partnerUserId]))["data"]["credit"] == 10000
        getAsMap(queryQuota, token([
                partnerUserId: partnerUserId]))["data"]["freeze"] == 0

        getAsMap(queryQuota, token([
                partnerUserId: partnerUserId]))["data"]["available"] == 7000

        getAsMap(queryQuota, token([
                partnerUserId: partnerUserId]))["data"]["block"] == 3000


        when:

        def loanUrl = getLoanUrl(partnerUserId)
        println loanUrl

        go loanUrl


        $("a", href: "/elong/order/unpaid?tab=0")[0].click()

        Thread.sleep(500)

        then:

        title == "读秒分期"


        when:
        go loanUrl

        Thread.sleep(500)

        $("a", href: "/elong/order/all").click()

        Thread.sleep(500)

        then:

        title == "我的订单"


        when:

        $("a")[0].click()

        $("span", class: "text", text: "3,000元").size() == 1
        Thread.sleep(500)


        then:

        title == "订单详情"

    }

}
