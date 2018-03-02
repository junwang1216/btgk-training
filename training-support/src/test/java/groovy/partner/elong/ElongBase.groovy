package groovy.partner.elong

import groovy.Testable
import groovy.data.ElongData

/**
 * Created by bcl on 2017/6/7.
 */
trait ElongBase implements Testable {
    def appKey = "ELONG"
    def appSec = "51A1A92D917EF5B0E4D665BE62903E2B"

    def timeStamp = System.currentTimeMillis()


    def token(def param) {
        timeStamp = System.currentTimeMillis()
        param["token"] = Sign.getSign(appKey, appSec, timeStamp, param)
        param["partner"] = appKey
        param["timestamp"] = timeStamp
        param << [
                partner  : appKey,
                timestamp: timeStamp
        ]

    }

    def freeze(partnerUserId, outTradeNo, amount, term) {
        def date = new Date().plus(1)
        def freezeTimeout = date.format("yyyy-MM-dd HH:mm:ss")
        def extraData = asString(ElongData.FREEZE_DATA)
        def reqTransID = System.currentTimeMillis()
        def freezeQuotaResult = postAsMap("/quota/v1/freeze", token([
                outTradeNo      : outTradeNo,
                reqTransID      : reqTransID,
                amount          : amount,
                term            : term,
                freezeTimeout   : freezeTimeout,
                consumeDate     : freezeTimeout,
                orderType       : "category",
                extraData       : extraData,
                orderDisplayDesc: "订单描述",
                partnerUserId   : partnerUserId
        ]))
        return freezeQuotaResult
    }

    def block(partnerUserId, outTradeNo, amount) {
        return postAsMap("/quota/v1/block", token([
                outTradeNo   : outTradeNo,
                reqTransID   : System.currentTimeMillis(),
                amount       : amount,
                extraData    : asString(ElongData.BLOCK_DATA),
                partnerUserId: partnerUserId
        ]))
    }

    def apply(partnerUserId, outTradeNo) {
        def path = "/credit/v1/elong/apply"

        return postAsMap(path, token([outTradeNo   : outTradeNo,
                                      creditData   : asString(ElongData.CREDIT_DATA),
                                      partnerUserId: partnerUserId]))
    }

    def getApplyUrl(partnerUserId, outTradeNo) {


        def path = "/credit/v1/elong/apply"



        def urlResponse = postAsMap(path, token([outTradeNo   : outTradeNo,
                                                 creditData   : asString(ElongData.CREDIT_DATA),
                                                 partnerUserId: partnerUserId]))

        [urlResponse["data"]["redirectUrl"], urlResponse["data"]["applyResult"]]
    }

    def finishApply(partnerUserId, outTradeNo) {
        def (url, result) = getApplyUrl(partnerUserId, outTradeNo)
        println url
        go url

        $("input", placeholder: "请输入持卡人真实姓名").value("王军")

        $("input", placeholder: "请输入持卡人身份证号").value(GenerateIdCard.generate())

        $("input", placeholder: "请输入卡号").value(BankCardNoGenerator.createCCBCardNo())
        Thread.sleep(2000)
        $("select", name: "bankNo").value("CCB")
        Thread.sleep(2000)
        $("input", placeholder: "请输入预留手机号").value(GenerateCellPhone.generate())

        $("label", name: "getCode").click()


        Thread.sleep(1000)



        $("input", placeholder: "请输入短信验证码").value("952700")
        Thread.sleep(1000)
        $("a", class: "btn btn-primary").click()

        Thread.sleep(2000)

        go url

        Thread.sleep(2000)


        $("select", name: "emRelationship").value("4")
        $("input", placeholder: "请输入联系人姓名").value("谢中山")
        $("input", placeholder: "请输入联系人手机号").value(GenerateCellPhone.generate())

        $("a", class: "btn btn-primary").click()


        Thread.sleep(2000)

        go url


        Thread.sleep(2000)



        $("a", class: "btn btn-primary").click()

        Thread.sleep(5000)


        def applyStatus = getAsMap("/credit/v1/elong/applyStatus", token([outTradeNo: outTradeNo, partnerUserId: partnerUserId]))

        while (applyStatus["data"]["applyResult"] == 2) {
            Thread.sleep(5000)
            applyStatus = getAsMap("/credit/v1/elong/applyStatus", token([outTradeNo: outTradeNo, partnerUserId: partnerUserId]))
        }

    }
    def getLoanUrl(partnerUserId) {


        def path = "/redirect/v1/elong/loan"



        def urlResponse = getAsMap(path, token([partnerUserId: partnerUserId]))


        urlResponse["data"]["redirectUrl"]
    }

}
