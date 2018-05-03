package com.training.core.repo.po;

import java.io.Serializable;

/**
 * @author 
 */
public class OrgFinanceEnums implements Serializable {
    private Integer id;

    /**
     * 枚举名称
     */
    private String enumName;

    /**
     * 枚举描述
     */
    private String enumNote;

    /**
     * 枚举值
     */
    private Integer enumValue;

    /**
     * 枚举分组
     */
    private String enumGroup;

    /**
     * 优先排序
     */
    private Integer enumPriority;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEnumName() {
        return enumName;
    }

    public void setEnumName(String enumName) {
        this.enumName = enumName;
    }

    public String getEnumNote() {
        return enumNote;
    }

    public void setEnumNote(String enumNote) {
        this.enumNote = enumNote;
    }

    public Integer getEnumValue() {
        return enumValue;
    }

    public void setEnumValue(Integer enumValue) {
        this.enumValue = enumValue;
    }

    public String getEnumGroup() {
        return enumGroup;
    }

    public void setEnumGroup(String enumGroup) {
        this.enumGroup = enumGroup;
    }

    public Integer getEnumPriority() {
        return enumPriority;
    }

    public void setEnumPriority(Integer enumPriority) {
        this.enumPriority = enumPriority;
    }
}