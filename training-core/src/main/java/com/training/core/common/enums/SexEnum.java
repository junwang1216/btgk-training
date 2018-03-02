package com.training.core.common.enums;

public enum SexEnum {
    LOG_TYPE_MALE(1, "男"),
    LOG_TYPE_FEMALE(2, "女"),
    ;
    int value;
    String text;

    SexEnum(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
