package groovy.partner.elong

import geb.spock.GebSpec
import groovy.Testable

class ELongTest extends GebSpec implements Testable, ElongBase {

    def testRefund() {
        def partnerUserId = System.currentTimeMillis()
        def outTradeNo = System.currentTimeMillis()

        when:
        finishApply(partnerUserId, outTradeNo)
        def (url, result) = getApplyUrl(partnerUserId, outTradeNo)
        then:
        result == 3

        when:
        outTradeNo = System.currentTimeMillis()
        def freezeQuotaResult = freeze(partnerUserId, outTradeNo, 4000, 0)
        then:
        freezeQuotaResult['status'] == 200
        freezeQuotaResult["data"]["resultCode"] == "1"
        when:
        def blockQuotaResult = block(partnerUserId, outTradeNo, 4000)
        then:
        blockQuotaResult['status'] == 200
        blockQuotaResult['data']['resultCode'] == "1"


        when:
        def refundPreview = getAsMap("/loan/v1/refundCalculate", token([partnerUserId: partnerUserId, outTradeNo: outTradeNo, amount: 500]))
        then:
        refundPreview['status'] == 200

        when:
        def reqTransId = System.currentTimeMillis()
        def refundResult = postAsMap("/loan/v1/refund", token([partnerUserId: partnerUserId, outTradeNo: outTradeNo, amount: 500, reqTransID: reqTransId]))
        then:
        refundResult['status'] == 200
        refundResult['data']['resultCode'] == "1"

        when:
        refundResult = getAsMap("/loan/v1/refundStatus", token([partnerUserId: partnerUserId, outTradeNo: outTradeNo, reqTransID: reqTransId]))
        then:
        refundResult['status'] == 200
        refundResult['data']['resultCode'] == "1"
    }

    def testQuotaCase() {
        def amount = 4000
        def term = 0

        def partnerUserId = System.currentTimeMillis()
        def outTradeNo = System.currentTimeMillis()
        //无额度
        when:
        def creditStatus = getAsMap("/credit/v1/status", token([partnerUserId: partnerUserId]))
        then:
        creditStatus["data"]["creditStatus"] == 0
        //未申请
        when:
        def queryQuotaResult = getAsMap("/quota/v1/query", token([
                partnerUserId: partnerUserId]))
        then:
        queryQuotaResult['status'] == 1109

        when:
        def freezeQuotaResult = freeze(partnerUserId, outTradeNo, amount, term)
        then:
        freezeQuotaResult['status'] == 1109
        //申请无额度
        when:
        getApplyUrl(partnerUserId, outTradeNo)
        freezeQuotaResult = freeze(partnerUserId, outTradeNo, amount, term)
        then:
        freezeQuotaResult['status'] == 1109


        when:
        //获取额度
        finishApply(partnerUserId, outTradeNo)
        //期数检查
        freezeQuotaResult = freeze(partnerUserId, outTradeNo, amount, 4)
        then:
        freezeQuotaResult['status'] == 400

        //额度检查
        when:
        freezeQuotaResult = freeze(partnerUserId, outTradeNo, 100001, term)
        then:
        freezeQuotaResult['status'] == 1102

        //冻结幂等
        when:
        freeze(partnerUserId, outTradeNo, amount, term)
        freezeQuotaResult = freeze(partnerUserId, outTradeNo, amount, term)
        then:
        freezeQuotaResult['status'] == 200
        freezeQuotaResult["data"]["resultCode"] == "1"

        //解冻
        when:
        def unfreezeResult = postAsMap("/quota/v1/unfreeze", token([outTradeNo: System.currentTimeMillis(), partnerUserId: partnerUserId, reqTransID: System.currentTimeMillis()]))
        then:
        unfreezeResult['status'] == 1139

        when:
        postAsMap("/quota/v1/unfreeze", token([outTradeNo: outTradeNo, partnerUserId: partnerUserId, reqTransID: System.currentTimeMillis()]))
        unfreezeResult = postAsMap("/quota/v1/unfreeze", token([outTradeNo: outTradeNo, partnerUserId: partnerUserId, reqTransID: System.currentTimeMillis()]))
        then:
        unfreezeResult["data"]["resultCode"] == "1"

        //占用
        //冻结
        when:
        outTradeNo = System.currentTimeMillis()
        freezeQuotaResult = freeze(partnerUserId, outTradeNo, amount, term)
        then:
        freezeQuotaResult['status'] == 200
        freezeQuotaResult["data"]["resultCode"] == "1"

        //无冻结
        when:
        def blockQuotaResult = block(partnerUserId, System.currentTimeMillis(), amount)
        then:
        blockQuotaResult['status'] == 1139
        //占用超额
        when:
        blockQuotaResult = block(partnerUserId, outTradeNo, 4001)
        then:
        blockQuotaResult['status'] == 1137
        //占用幂等
        when:
        block(partnerUserId, outTradeNo, 400)
        blockQuotaResult = block(partnerUserId, outTradeNo, 400)
        then:
        blockQuotaResult["status"] == 200

        //0/3/6/9/12 期
        for (int i = 0; i <= 4; i++) {
            when:
            outTradeNo = System.currentTimeMillis()
            freeze(partnerUserId, outTradeNo, 400, i * 3)
            blockQuotaResult = block(partnerUserId, outTradeNo, 400)
            then:
            blockQuotaResult["data"]["resultCode"] == "1"
        }

        when:
        def url = getLoanUrl(partnerUserId)
        print url
        then:
        url != null

    }

    def getLoanUrl(partnerUserId) {


        def path = "/redirect/v1/elong/loan"



        def urlResponse = getAsMap(path, token([partnerUserId: partnerUserId]))


        urlResponse["data"]["redirectUrl"]
    }

    def testApplyCase() {
        //重复申请
        def partnerUserId = System.currentTimeMillis()
        def outTradeNo = System.currentTimeMillis()

        //授信状态
        when:
        def creditStatus = getAsMap("/credit/v1/status", token([partnerUserId: partnerUserId]))
        then:
        creditStatus["data"]["creditStatus"] == 0

        when:
        def (url, result) = getApplyUrl(partnerUserId, outTradeNo)
        (url, result) = getApplyUrl(partnerUserId, outTradeNo)

        then:
        result == 1

        //申请中拒绝
        when:
        def response = apply(partnerUserId, System.currentTimeMillis())
        then:
        response['status'] == 1122

        //完成申请
        //申请通过不能申请
        when:
        finishApply(partnerUserId, outTradeNo)
        response = apply(partnerUserId, System.currentTimeMillis())
        then:
        response['status'] == 1101

        //授信状态
        when:
        creditStatus = getAsMap("/credit/v1/status", token([partnerUserId: partnerUserId]))
        then:
        creditStatus["data"]["creditStatus"] == 1
        creditStatus["data"]["credit"] > 0

        //拒绝 ban days
        when:
        def rows = sql.rows "select * from apply where partner='elong' and out_apply_no=$outTradeNo"
        def passport_user_id = rows[0]['passport_user_id']
        sql.executeUpdate "update credit_result set result=2 where  partner='elong' and passport_user_id=$passport_user_id"
        response = apply(partnerUserId, System.currentTimeMillis())
        then:
        response['status'] == 1122

        //授信状态
        when:
        creditStatus = getAsMap("/credit/v1/status", token([partnerUserId: partnerUserId]))
        then:
        creditStatus["data"]["creditStatus"] == 2
        creditStatus["data"]["blockTimeout"] != null
    }



}
