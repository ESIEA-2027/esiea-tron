package com.jad.esieatron.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum EsieaTronUtils {
    ;

    public static Properties loadProperties(final Class<?> clazz, final String fileName) {
        final Properties properties = new Properties();
        try (InputStream input = clazz.getClassLoader().getResourceAsStream(fileName)) {
            if (input != null) properties.load(input);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to load properties", exception);
        }
        return properties;
    }
}
