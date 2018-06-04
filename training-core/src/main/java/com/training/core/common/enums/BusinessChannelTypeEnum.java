package com.training.core.common.enums;

import java.util.ArrayList;
import java.util.List;

public enum BusinessChannelTypeEnum {
    CHANNEL_COMPANY(1, "公司"),
    CHANNEL_PERSON(2, "个人"),
    CHANNEL_FRIEND(3, "转介绍"),
    CHANNEL_MEMBER(4, "续费"),
    ;
    int code;
    String desc;

    BusinessChannelTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static BusinessChannelTypeEnum forValue(int value) {
        for (BusinessChannelTypeEnum busi : BusinessChannelTypeEnum.values()) {
            if (value == busi.code) {
                return busi;
            }
        }
        return BusinessChannelTypeEnum.CHANNEL_COMPANY;
    }

    public static List<String> getEnumList () {

        List<String> businessChannelTypeEnumList = new ArrayList<>();

        businessChannelTypeEnumList.add(BusinessChannelTypeEnum.CHANNEL_COMPANY.getDesc());
        businessChannelTypeEnumList.add(BusinessChannelTypeEnum.CHANNEL_PERSON.getDesc());
        businessChannelTypeEnumList.add(BusinessChannelTypeEnum.CHANNEL_FRIEND.getDesc());
        businessChannelTypeEnumList.add(BusinessChannelTypeEnum.CHANNEL_MEMBER.getDesc());

        return businessChannelTypeEnumList;
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
