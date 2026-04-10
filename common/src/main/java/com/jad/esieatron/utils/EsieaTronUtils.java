package com.jad.esieatron.utils;

import com.jad.esieatron.domain.CardinalPoint;

import java.awt.*;
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

    public static Point getNextPosition(final CardinalPoint direction, final Point position) {
        return switch (direction) {
            case NORTH -> new Point(position.x, position.y - 1);
            case EAST -> new Point(position.x + 1, position.y);
            case SOUTH -> new Point(position.x, position.y + 1);
            case WEST -> new Point(position.x - 1, position.y);
        };
    }

    public static Point normalize(final Point point, final Dimension dimension) {
        return new Point((point.x + dimension.width) % dimension.width,
                         (point.y + dimension.height) % dimension.height);
    }
}
