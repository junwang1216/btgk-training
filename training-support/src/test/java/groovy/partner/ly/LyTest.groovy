package groovy.partner.ly

import geb.spock.GebSpec
import groovy.Testable
import groovy.data.LyData
import org.apache.commons.lang3.StringUtils

class LyTest extends GebSpec implements Testable, LyBase {

    def repay = "/loan/v1/repay/withhold"


    def refund = "/loan/v1/refund"


    def correct(results) {
        !results.any { it["status"] != 200 }
    }


    def testApply() {
        def partnerUserId = System.currentTimeMillis()
        def outTradeNo = System.currentTimeMillis()
        def idCard = GenerateIdCard.generate()
        def mobile = GenerateCellPhone.generate()
        //授信状态
        when:
        def creditStatus = getAsMap("/credit/v1/ly/status", token([partnerUserId: partnerUserId]))
        then:
        creditStatus["data"]["creditStatus"] == 0
        //绑卡验证
        when:
        def applyResult = postAsMap("/credit/v1/ly/apply", token([
                outTradeNo          : outTradeNo,
                partnerUserId       : partnerUserId,
                userName            : "谢中生",
                idCard              : GenerateIdCard.generate(),
                mobile              : GenerateCellPhone.generate(),
                withholdCard        : "1111",
                withholdCardBankNo  : "CCB",
                creditData          : asString(LyData.FRESH_CREDIT_DATA),
                zhimaSuccessCallback: URLEncoder.encode(zhimaSuccess, "UTF-8"),
                zhimaFailCallback   : URLEncoder.encode(zhimaFail, "UTF-8")
        ]))
        then:
        applyResult["status"] == 1001204

        when:
        //新用户
        //重入
        apply(partnerUserId, outTradeNo, LyData.FRESH_CREDIT_DATA, idCard, mobile)
        applyResult = apply(partnerUserId, outTradeNo, LyData.FRESH_CREDIT_DATA, idCard, mobile)
        then:
        StringUtils.isNotEmpty(applyResult['data']['redirectUrl'])
        applyResult['data']['applyResult'] == 1

        when:
        go applyResult['data']['redirectUrl']
        print applyResult['data']['redirectUrl']
        then:
        title == "芝麻信用授权"

        when:
        waitApply(partnerUserId, outTradeNo)
        def queryQuotaResult2 = getAsMap("/quota/v1/query", token([
                partnerUserId: partnerUserId]))

        then:
        queryQuotaResult2["status"] == 200
        queryQuotaResult2["data"]["credit"] == 10000

        //授信状态
        when:
        creditStatus = getAsMap("/credit/v1/ly/status", token([partnerUserId: partnerUserId]))
        then:
        creditStatus["data"]["creditStatus"] == 1
        !creditStatus["data"]["rateList"].asType(List.class).isEmpty()

        //信息占用
        when:
        applyResult = apply(System.currentTimeMillis(), System.currentTimeMillis(), LyData.FRESH_CREDIT_DATA, idCard, mobile)

        then:
        applyResult['status'] == 1002007

        when:
        applyResult = apply(System.currentTimeMillis(), System.currentTimeMillis(), LyData.FRESH_CREDIT_DATA, GenerateIdCard.generate(), mobile)

        then:
        applyResult['status'] == 1002009
        //老用户
        when:
        applyResult = apply(System.currentTimeMillis(), System.currentTimeMillis(), LyData.PREVIOUS_CREDIT_DATA, GenerateIdCard.generate(), GenerateCellPhone.generate())
        then:
        applyResult['data']['applyResult'] == 2
    }

    def allCase() {


        def partnerUserId = System.currentTimeMillis()
        def outTradeNo = System.currentTimeMillis()

        when:

        def queryQuota = "/quota/v1/query"

        //查询额度
        def queryQuotaResult = getAsMap(queryQuota, token([
                partnerUserId: partnerUserId]))


        then:
        queryQuotaResult["status"] == 1109


        when:

        //申请额度
        def applyResult = apply(partnerUserId, outTradeNo, LyData.PREVIOUS_CREDIT_DATA, GenerateIdCard.generate(), GenerateCellPhone.generate())
        waitApply(partnerUserId, outTradeNo)

        def queryQuotaResult2 = getAsMap(queryQuota, token([
                partnerUserId: partnerUserId]))

        then:
        queryQuotaResult2["status"] == 200
        queryQuotaResult2["data"]["credit"] == 10000

        when:

        def preview = "/loan/v1/preview"

        //试算
        def previewResult = getAsMap(preview, token([
                amount       : "1000",
                partnerUserId: partnerUserId
        ]))

        then:
        previewResult["status"] == 200



        when:

        def freezeOrderId = System.currentTimeMillis()

        //冻结
        def freezeQuotaResult = freeze(partnerUserId, freezeOrderId, 5000, 3, LyData.FREEZE_HOTEL_DATA)

        then:
        freezeQuotaResult["status"] == 200
        freezeQuotaResult["data"]["resultCode"] == "1"

        getAsMap("/quota/v1/freezeStatus", token([outTradeNo: freezeOrderId, partnerUserId: partnerUserId]))["data"]["resultCode"] == "1"

        //占用
        def blockQuotaResult = block(partnerUserId, freezeOrderId, 5000)

        then:
        blockQuotaResult["status"] == 200
        blockQuotaResult["data"]["amount"] == 5000
        getAsMap("/quota/v1/blockStatus", token([outTradeNo: freezeOrderId, partnerUserId: partnerUserId]))["data"]["resultCode"] == "1"

        //查询还款计划
        def queryRepaymentPlan = "/loan/v1/queryRepaymentPlan"

        def queryRepaymentPlanResult = getAsMap(queryRepaymentPlan, token([
                outTradeNo   : freezeOrderId,
                partnerUserId: partnerUserId
        ]))


        then:

        queryRepaymentPlanResult["status"] == 200


        when:
        //还款
        def repayResult = postAsMap(repay, token([
                outTradeNo   : freezeOrderId,
                reqTransID   : System.currentTimeMillis(),
                amount       : "1000",
                term         : 1,
                partnerUserId: partnerUserId
        ]))

        then:
        repayResult["status"] == 200

        when:
        //退款
        def refundResult = postAsMap(refund, token([
                outTradeNo   : freezeOrderId,
                reqTransID   : System.currentTimeMillis(),
                amount       : "1000",
                partnerUserId: partnerUserId
        ]))

        then:

        refundResult["status"] == 200

        //转分期
        when:
        freezeOrderId = System.currentTimeMillis()
        freeze(partnerUserId, freezeOrderId, 500, 0, LyData.FREEZE_HOTEL_DATA)
        def blockResult = block(partnerUserId, freezeOrderId, 500)
        def transferResult = postAsMap("/loan/v1/transfer", token([
                outTradeNo   : System.currentTimeMillis(),
                reqTransID   : System.currentTimeMillis(),
                loanNo       : blockResult['data']['loanNo'],
                partnerUserId: partnerUserId,
                term         : 3
        ]))
        then:
        transferResult['status'] == 200

    }


}
