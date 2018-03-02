package com.training.core.common.enums;

public enum StatusEnum {
    STATUS_OK(1, "正常状态"),
    STATUS_ERROR(2, "异常状态"),
    STATUS_GOING(3, "进行中状态"),
    ;
    int code;
    String desc;

    StatusEnum(int code, String desc) {
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
