package com.training.core.common.enums;

public enum ClassStatusEnum {
    STATUS_START(1, "已开班"),
    STATUS_WORKING(2, "上课中"),
    STATUS_END(3, "已完结"),
    ;
    int code;
    String desc;

    ClassStatusEnum(int code, String desc) {
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
