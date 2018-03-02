package com.training.h5.layout;

public class TagUtils {

    public static String BLOCK = "__jsp_override__";

    static String getOverrideVariableName(String name) {
        return BLOCK + name;
    }
}
