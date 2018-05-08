package com.training.core.common.enums;

public enum BusinessGoalTypeEnum {
    FLOW(1, "流水目标"),
    INCOME(2, "确认收入目标"),
    ;
    int code;
    String desc;

    BusinessGoalTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static BusinessGoalTypeEnum forValue(int value) {
        for (BusinessGoalTypeEnum busi : BusinessGoalTypeEnum.values()) {
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
