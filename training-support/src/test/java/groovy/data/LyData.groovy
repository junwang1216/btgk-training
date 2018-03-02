package groovy.data

/**
 * Created by bcl on 2017/6/7.
 */
class LyData {
    static def FRESH_CREDIT_DATA = [
            freshCreditData: [
                    "partnerUserId"          : System.currentTimeMillis(),
                    "idCard"                 : GenerateIdCard.generate(),
                    "mobile"                 : GenerateCellPhone.generate(),
                    "withholdCard"           : BankCardNoGenerator.createCCBCardNo(),
                    "withholdSmsTime"        : "2017-06-06 00:00:00",
                    "withholdSmsCallbackTime": "2017-06-06 00:00:00",
                    "withholdSmsSendStatus"  : "withholdSmsSendStatus",
                    "withholdAuthTime"       : "2017-06-06 00:00:00",
                    "deviceInfo"             : "deviceInfo",
                    "ip"                     : "ip",
                    "terminalType"           : null,
                    "registerTime"           : "2017-06-06 00:00:00",
                    "emName"                 : "emName",
                    "emMobile"               : GenerateCellPhone.generate(),
                    "emAddress"              : "emAddress"
            ]
    ]
    static def PREVIOUS_CREDIT_DATA = [
            previousCreditData: [
                    "partnerUserId"              : System.currentTimeMillis(),
                    "idCard"                     : GenerateIdCard.generate(),
                    "mobile"                     : GenerateCellPhone.generate(),
                    "deviceInfo"                 : "deviceInfo",
                    "ip"                         : "deviceInfo",
                    "terminalType"               : "terminalType",
                    "withholdCard"               : BankCardNoGenerator.createCCBCardNo(),
                    "withholdSmsTime"            : "2017-06-06 00:00:00",
                    "withholdSmsCallbackTime"    : "2017-06-06 00:00:00",
                    "withholdSmsSendStatus"      : "withholdSmsSendStatus",
                    "withholdAuthTime"           : "2017-06-06 00:00:00",
                    "registerTime"               : "2017-06-06 00:00:00",
                    "registerIp"                 : "registerIp",
                    "firstOrderSuccessTime"      : "2017-06-06 00:00:00",
                    "bindCardNum"                : 0,
                    "mainCardType"               : "mainCardType",
                    "mainCardBank"               : "CCB",
                    "totalAmtL12M"               : 0,
                    "totalL12M"                  : 0,
                    "totalAmtL3M"                : 0,
                    "totalL3M"                   : 0,
                    "refundRateL12M"             : 0,
                    "refundRateL3M"              : 0,
                    "refundL12M"                 : 0,
                    "maxAmtL12M"                 : 0,
                    "lastOrderTime"              : "2017-06-06 00:00:00",
                    "lastOrderAmt"               : 0,
                    "lastRefundTime"             : "2017-06-06 00:00:00",
                    "totalFlightAmtL12M"         : 0,
                    "totalTrainAmtL12M"          : 0,
                    "totalHotelAmtL12M"          : 0,
                    "totalAbroadVacationAmtL12M" : 0,
                    "totalInbroadVacationAmtL12M": 0,
                    "totalAroundVacationAmtL12M" : 0,
                    "totalShipVacationAmtL12M"   : 0,
                    "totalFlightL12M"            : 0,
                    "totalTrainL12M"             : 0,
                    "totalHotelL12M"             : 0,
                    "totalAbroadVacationL12M"    : 0,
                    "totalInbroadVacationL12M"   : 0,
                    "totalAroundVacationL12M"    : 0,
                    "totalShipVacationL12M"      : 0,
                    "memberLevel"                : 1
            ]
    ]

    static def FREEZE_FIGHT_DATA = [
            "orderNo"           : System.currentTimeMillis(),
            "partnerUserId"     : System.currentTimeMillis(),
            "consumTime"        : "2017-06-06 00:00:00",
            "consumFirstType"   : "机票",
            "consumSecondType"  : "机票",
            "orderAmt"          : 1000,
            "isSelfUsed"        : "true",
            "orderContactMobile": GenerateCellPhone.generate(),
            "orderContactName"  : "orderContactName",
            "deviceInfo"        : "deviceInfo",
            "ip"                : "ip",
            "terminalType"      : "terminalType",
            "consumeData"       : [
                    "fight": [
                            "departure"    : "departure",
                            "arrival"      : "arrival",
                            "dateStart"    : "2017-06-06 00:00:00",
                            "flightCompany": "flightCompany"
                    ]
            ]
    ]
    static def FREEZE_TRAIN_DATA = [
            "orderNo"           : System.currentTimeMillis(),
            "partnerUserId"     : System.currentTimeMillis(),
            "consumTime"        : "2017-06-06 00:00:00",
            "consumFirstType"   : "火车票",
            "consumSecondType"  : "火车票",
            "orderAmt"          : 1000,
            "isSelfUsed"        : "true",
            "orderContactMobile": GenerateCellPhone.generate(),
            "orderContactName"  : "orderContactName",
            "deviceInfo"        : "deviceInfo",
            "ip"                : "ip",
            "terminalType"      : "terminalType",
            "consumeData"       : [
                    "train": [
                            "departure": "departure",
                            "arrival"  : "arrival",
                            "dateStart": "2017-06-06 00:00:00",
                    ]
            ]
    ]
    static def FREEZE_HOTEL_DATA = [
            "orderNo"           : System.currentTimeMillis(),
            "partnerUserId"     : System.currentTimeMillis(),
            "consumTime"        : "2017-06-06 00:00:00",
            "consumFirstType"   : "酒店",
            "consumSecondType"  : "酒店",
            "orderAmt"          : 1000,
            "isSelfUsed"        : "true",
            "orderContactMobile": GenerateCellPhone.generate(),
            "orderContactName"  : "orderContactName",
            "deviceInfo"        : "deviceInfo",
            "ip"                : "ip",
            "terminalType"      : "terminalType",
            "consumeData"       : [
                    "hotel": [
                            "hotelName"   : "如家",
                            "checkInDate" : "2017-06-06 00:00:00",
                            "checkOutDate": "2017-06-06 00:00:00",
                            "province"    : "北京",
                            "city"        : "北京"
                    ]
            ]
    ]
    static def FREEZE_VACATION_DATA = [
            "orderNo"           : System.currentTimeMillis(),
            "partnerUserId"     : System.currentTimeMillis(),
            "consumTime"        : "2017-06-06 00:00:00",
            "consumFirstType"   : "度假",
            "consumSecondType"  : "度假",
            "orderAmt"          : 1000,
            "isSelfUsed"        : "true",
            "orderContactMobile": GenerateCellPhone.generate(),
            "orderContactName"  : "orderContactName",
            "deviceInfo"        : "deviceInfo",
            "ip"                : "ip",
            "terminalType"      : "terminalType",
            "consumeData"       : [
                    "hotel": [
                            "dateStart": "2017-06-06 00:00:00",
                            "dateEnd"  : "2017-06-06 00:00:00",
                            "location" : "location",
                            "detail"   : "detail"
                    ]
            ]
    ]
    static def BLOCK_DATA = [
            downPayment: 100,
            downTime   : "2017-06-06 00:00:00"
    ]
}
