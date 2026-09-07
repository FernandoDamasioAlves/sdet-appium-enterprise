package com.fernandodamasio.sdet.mobile.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigManager {

    private static final String CONFIG_FILE =
            "config/test.properties";

    private static final Properties PROPERTIES =
            loadProperties();

    private ConfigManager() {
    }

    public static String get(String key) {
        String systemValue = System.getProperty(key);

        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue;
        }

        String value = PROPERTIES.getProperty(key);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Missing configuration property: " + key
            );
        }

        return value.trim();
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static long getLong(String key) {
        return Long.parseLong(get(key));
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();

        try (InputStream input =
                     ConfigManager.class
                             .getClassLoader()
                             .getResourceAsStream(CONFIG_FILE)) {

            if (input == null) {
                throw new IllegalStateException(
                        "Configuration file not found: "
                                + CONFIG_FILE
                );
            }

            properties.load(input);
            return properties;

        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Unable to load configuration file: "
                            + CONFIG_FILE,
                    exception
            );
        }
    }
}
