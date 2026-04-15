package ru.itmo.framework.config;

import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public final class TestConfig {
    private static final Properties PROPERTIES = new Properties();

    static {
        load("application.local.properties");
    }

    private TestConfig() {
    }

    private static void load(String fileName) {
        try (InputStream is = TestConfig.class.getClassLoader().getResourceAsStream(fileName)) {
            if (is == null) {
                throw new IllegalStateException(fileName + " not found");
            }

            PROPERTIES.load(is);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load " + fileName, e);
        }
    }

    public static String get(String key) {
        String value = PROPERTIES.getProperty(key);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing config: " + key);
        }

        return value;
    }

    public static String getOrDefault(String key, String defaultValue) {
        return PROPERTIES.getProperty(key, defaultValue);
    }

    public static Duration getDurationSeconds(String key, long defaultValue) {
        String rawValue = PROPERTIES.getProperty(key);

        if (rawValue == null || rawValue.isBlank()) {
            return Duration.ofSeconds(defaultValue);
        }

        return Duration.ofSeconds(Long.parseLong(rawValue));
    }
}
