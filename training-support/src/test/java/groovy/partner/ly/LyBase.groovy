package groovy.partner.ly

import groovy.Testable
import groovy.data.LyData

/**
 * Created by bcl on 2017/6/7.
 */
trait LyBase implements Testable {

    def zhimaSuccess = "http://localhost:8081/third/callback?args=oQKakLx7WJiLcDWwLeb54EYYWkFV3opFq7YliCcvv0GSoottB1lNNONSWmhi%2FUsbG2QSrpOO6h9%2Fu7KZ3hoxIEuJc29Yrq%2FpqEMZUiSoqhI%3D";
    def zhimaFail = "http://localhost:8081/third/callback?args=SXBLfJpvCP8cgPhk1aW6qYLC%2FXTn3C5uRjxSCRkvlEgI9kbqaEMTlsK2JnptgAs4k%2FNWNiPfIDekXJ%2BL2AKGFqdVfc7YgoGQifHSf2tvXns%3D";

    def appKey = "LY"
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

    def freeze(partnerUserId, outTradeNo, amount, term, freezeData) {
        def date = new Date().plus(1)
        def freezeTimeout = date.format("yyyy-MM-dd HH:mm:ss")
        def extraData = asString(freezeData)
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
                extraData    : asString(LyData.BLOCK_DATA),
                partnerUserId: partnerUserId
        ]))
    }

    def apply(partnerUserId, outTradeNo, creditData, idCard, mobile) {
        postAsMap("/credit/v1/ly/apply", token([
                outTradeNo        : outTradeNo,
                partnerUserId     : partnerUserId,
                userName          : "谢中生",
                idCard            : idCard,
                mobile            : mobile,
                withholdCard      : BankCardNoGenerator.createCCBCardNo(),
                withholdCardBankNo: "CCB",
                creditData        : asString(creditData),
                zhimaSuccessCallback: URLEncoder.encode(zhimaSuccess, "UTF-8"),
                zhimaFailCallback   : URLEncoder.encode(zhimaFail, "UTF-8")]
        ))
    }

    def waitApply(partnerUserId, outTradeNo) {
        def applyStatus = getAsMap("/credit/v1/ly/applyStatus", token([outTradeNo: outTradeNo, partnerUserId: partnerUserId]))

        while (applyStatus["data"]["applyResult"] != 3) {
            Thread.sleep(5000)
            applyStatus = getAsMap("/credit/v1/ly/applyStatus", token([outTradeNo: outTradeNo, partnerUserId: partnerUserId]))
        }
    }

}
