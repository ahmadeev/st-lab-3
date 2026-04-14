package ru.itmo;

import java.io.InputStream;
import java.util.Properties;

public final class Config {
    private static final Properties PROPERTIES = new Properties();

    static {
        load("application.local.properties");
    }

    private Config() {
    }

    private static void load(String fileName) {
        try (InputStream is = Config.class.getClassLoader().getResourceAsStream(fileName)) {
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
}
