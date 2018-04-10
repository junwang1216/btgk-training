package com.training.core.common.enums;

public enum PayTypeEnum {
    PAY_TYPE_INIT(99, "--"),
    PAY_TYPE_MONEY(1, "现金"),
    PAY_TYPE_WX(2, "微信"),
    PAY_TYPE_ZFB(3, "支付宝"),
    PAY_TYPE_YL(4, "银联"),
    ;
    int code;
    String desc;

    PayTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static PayTypeEnum forValue(int value) {
        for (PayTypeEnum type : PayTypeEnum.values()) {
            if (value == type.code) {
                return type;
            }
        }
        return PAY_TYPE_INIT;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
