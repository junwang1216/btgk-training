package com.training.core.common.config;

import com.training.core.common.spring.EnvConfig;

public final class WebConfig {
    //encrypt keys
    public static String getDataEncryptKey() {
        return EnvConfig.getProperty("SECURITY_ENCRYPT_KEY");
    }

    public static String getDataEncryptIV() {
        return EnvConfig.getProperty("SECURITY_ENCRYPT_IV");
    }

    public static String getDataEncryptSalt() {
        return EnvConfig.getProperty("SECURITY_ENCRYPT_SALT");
    }

}
