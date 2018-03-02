package groovy.data

/**
 * Created by bcl on 2017/6/7.
 */
class ElongData {
    static def CREDIT_DATA = [
            gps           : "200",
            terminalType  : "terminalType",
            ip            : "ip",
            isCommonDevice: "true",
    ]
    static def FREEZE_DATA = [
            "consumeData"    : [
                    "hotel"   : "如家",
                    "province": "北京",
                    "city"    : "北京",
                    "checkIn" : "2017-06-06 00:00:00",
                    "checkOut": "2017-06-09 00:00:00"
            ],
            "isSelfUsed"     : "true",
            "tel"            : "tel",
            "name"           : "name",
            "isCommonDevice" : "true",
            "ip"             : "ip",
            "gps"            : "gps",
            "terminal"       : "terminal",
            "consumTime"     : "2017-06-06 00:00:00",
            "consumFirstType": "酒店"
    ]
    static def BLOCK_DATA = [
            "feeDataList": [
                    [feeType: "杂费",
                     feeAmt : "100"
                    ]
            ],
            "checkIn"    : "2017-06-06 00:00:00",
            "checkOut"   : "2017-06-08 00:00:00"
    ]
}
