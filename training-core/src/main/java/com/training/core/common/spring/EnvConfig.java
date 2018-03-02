package com.training.core.common.spring;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurablePropertyResolver;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EnvConfig extends PropertySourcesPlaceholderConfigurer {


    private static final String SYSTEM_ENV_CONFIG_PATH = System.getenv("ENVIRONMENTS_FILE");

    static {
        Resource[] resources = selectEnvPath();
        try {
            propertiesMap = new Properties();
            EnvConfig.propertiesMap.load(resources[0].getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Map that hold all the properties.
     */
    private static Properties propertiesMap;


    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess,
                                     final ConfigurablePropertyResolver propertyResolver) throws BeansException {
        try {
            propertiesMap = mergeProperties();
            super.processProperties(beanFactoryToProcess, propertyResolver);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static int getInteger(String key, int defaultValue) {
        try {
            return Integer.parseInt(getProperty(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }


    public static boolean getBoolean(String key, boolean defaultValue) {
        try {
            return getProperty(key).toLowerCase().equals("true");
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static List<String> getList(String key, String separator) {
        List<String> valueList = new ArrayList<>();
        String valueString = getProperty(key);
        if (org.apache.commons.lang3.StringUtils.isNotBlank(valueString)) {
            for (String value : valueString.split(separator)) {
                if (org.apache.commons.lang3.StringUtils.isNotBlank(value)) {
                    valueList.add(value);
                }
            }
        }
        return valueList;
    }


    /**
     * This method gets the String value for a given String key for the property files.
     *
     * @param name - Key for which the value needs to be reterieved.
     * @return Value
     */
    public static String getProperty(String name) {
        return propertiesMap.getProperty(name);
    }


    //测试时可设置自定义值
    public static void setProperty(String name, String value) {
        propertiesMap.setProperty(name, value);
    }

    @Override
    public void setLocations(Resource[] locations) {
        super.setLocations(selectEnvPath());
    }


    private static Resource[] selectEnvPath() {
        if (StringUtils.isNotBlank(SYSTEM_ENV_CONFIG_PATH)) {
            File file = new File(SYSTEM_ENV_CONFIG_PATH);
            if (file.exists()) {
                return new Resource[]{new PathResource(file.getPath())};
            } else {
                throw new RuntimeException("环境配置文件不存在");
            }

        } else {
            return new Resource[]{new ClassPathResource("/env.properties")};
        }

    }


    public static boolean isProd() {
        return getProperty("env.name").equals("production");
    }


}