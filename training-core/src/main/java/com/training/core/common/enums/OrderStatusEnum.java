package com.training.core.common.enums;

public enum OrderStatusEnum {
    ORDER_STATUS_PAID(1, "已支付"),
    ORDER_STATUS_UNPAID(2, "未支付"),
    ORDER_STATUS_CANCEL(3, "已取消"),
    ORDER_STATUS_REFUND(4, "已退费"),
    ;
    int code;
    String desc;

    OrderStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
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
