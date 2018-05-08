package com.training.core.common.enums;

public enum BusinessTypeEnum {
    TRAINING_YOUNG(1, "青少年培训"),
    VENUE_LEASE(2, "场地租赁"),
    ;
    int code;
    String desc;

    BusinessTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static BusinessTypeEnum forValue(int value) {
        for (BusinessTypeEnum busi : BusinessTypeEnum.values()) {
            if (value == busi.code) {
                return busi;
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
