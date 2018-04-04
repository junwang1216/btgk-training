package com.training.core.common.enums;

public enum OrderTypeEnum {
    ORDER_TYPE_CLASS(1, "培训班费用"),
    ORDER_TYPE_REFUND(2, "培训班退费"),
    ;
    int code;
    String desc;

    OrderTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static OrderTypeEnum forValue(int value) {
        for (OrderTypeEnum type : OrderTypeEnum.values()) {
            if (value == type.code) {
                return type;
            }
        }
        return null;
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
