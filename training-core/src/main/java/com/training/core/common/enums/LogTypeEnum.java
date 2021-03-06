package com.training.core.common.enums;

public enum LogTypeEnum {
    LOG_TYPE_LOGIN(1, "运营账号登录"),
    LOG_TYPE_LOGOUT(2, "运营账号退出"),
    LOG_TYPE_VENUE_SETTINGS(3, "机构设置"),
    LOG_TYPE_CLASS_SETTINGS(4, "班级管理"),
    LOG_TYPE_STUDENTS_SETTINGS(5, "学员管理"),

    LOG_TYPE_EXCEPTION(99, "日志类型错误"),

    LOG_TYPE_FINANCE_REPORTS(999, "财务报表"),
    ;
    int code;
    String desc;

    LogTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static LogTypeEnum forValue(int value) {
        for (LogTypeEnum log : LogTypeEnum.values()) {
            if (value == log.code) {
                return log;
            }
        }
        return LogTypeEnum.LOG_TYPE_EXCEPTION;
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
