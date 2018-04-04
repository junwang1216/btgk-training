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

    // 获取数据库
    public static String getDatabaseUrl() {
        return EnvConfig.getProperty("training_db_url");
    }

    public static String getDatabaseName() {
        return EnvConfig.getProperty("training_db_name");
    }

    public static String getDatabaseUserName() {
        return EnvConfig.getProperty("training_db_username");
    }

    public static String getDatabasePassword() {
        return EnvConfig.getProperty("training_db_password");
    }

}
