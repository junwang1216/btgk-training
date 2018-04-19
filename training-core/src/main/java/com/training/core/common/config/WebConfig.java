package com.training.core.common.config;

import com.training.core.common.spring.EnvConfig;
import com.training.core.common.util.StringUtil;

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

    // 配置项
    public static String getUserDefaultPassword() {
        return EnvConfig.getProperty("training_user_password");
    }

    // 邮箱
    public static String getMailHost() {
        return EnvConfig.getProperty("training_mail_host");
    }

    public static Integer getMailPort() {
        return Integer.getInteger(EnvConfig.getProperty("training_mail_port"));
    }

    public static String getMailUserName() {
        return EnvConfig.getProperty("training_mail_username");
    }

    public static String getMailPassword() {
        return EnvConfig.getProperty("training_mail_password");
    }

    public static Integer getMailTimeout() {
        return Integer.getInteger(EnvConfig.getProperty("training_mail_timeout"));
    }

    public static String getMailFrom() {
        return EnvConfig.getProperty("training_mail_from");
    }
}
