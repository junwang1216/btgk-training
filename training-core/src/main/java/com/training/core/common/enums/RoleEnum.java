package com.training.core.common.enums;

import java.util.ArrayList;
import java.util.List;

public enum RoleEnum {
    ROLE_COACH(1, "教练"),
    ROLE_CHIEF(2, "班主任"),
    ROLE_STUDENT(3, "学生"),
    ROLE_OPERATOR(98, "操作员"),
    ROLE_ADMIN(99, "管理员"),
    ROLE_SUPER_ADMIN(999, "超级管理员"),
    ROLE_SUPER_FINANCE_ADMIN(9999, "财务超级管理员"),
    ROLE_SUPER_FINANCE_INPUT(9998, "财务数据输入者"),
    ;
    int code;
    String desc;

    RoleEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static List<RoleEnum> getCoachList() {
        List<RoleEnum> roleEnumList = new ArrayList<>();

        roleEnumList.add(RoleEnum.ROLE_COACH);
        roleEnumList.add(RoleEnum.ROLE_CHIEF);

        return roleEnumList;
    }

    public static RoleEnum forValue(int value) {
        for (RoleEnum role : RoleEnum.values()) {
            if (value == role.code) {
                return role;
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
