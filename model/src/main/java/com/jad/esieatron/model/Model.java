package com.jad.esieatron.model;

import com.jad.esieatron.domain.Player;
import com.jad.esieatron.view.IView;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Model implements IModel {
    private static final String DEFAULT_CONFIG_FILE = "application.properties";
    private static final String GRID_WIDTH_KEY = "model.grid.width";
    private static final String GRID_HEIGHT_KEY = "model.grid.height";

    @SuppressWarnings("FieldCanBeLocal")
    private final Grid grid;
    private final LightCycles lightCycles;
    private IView view;

    public Model() {
        final Dimension gridDimension = Model.loadGridDimension();
        this.grid = new Grid(gridDimension);
        this.lightCycles = new LightCycles(gridDimension, this.grid::placeSprite);
    }

    private static Dimension loadGridDimension() {
        final Properties properties = Model.loadProperties();

        final int width = Integer.parseInt(properties.getProperty(Model.GRID_WIDTH_KEY));
        final int height = Integer.parseInt(properties.getProperty(Model.GRID_HEIGHT_KEY));
        return new Dimension(width, height);
    }

    private static Properties loadProperties() {
        final Properties properties = new Properties();
        try (InputStream stream = Model.class.getClassLoader().getResourceAsStream(Model.DEFAULT_CONFIG_FILE)) {
            if (stream != null) properties.load(stream);
        } catch (IOException ignored) {
            throw new RuntimeException("Failed to load configuration file: " + Model.DEFAULT_CONFIG_FILE);
        }
        return properties;
    }

    public final void addPlayer(final Player player) {
        this.lightCycles.addPlayer(player);
    }

    @Override
    public final void setView(final IView view) {
        this.view = view;
    }

    @Override
    public final void turnLeft(final Player player) {
        this.lightCycles.turnLeft(player);
    }

    @Override
    public final void turnRight(final Player player) {
        this.lightCycles.turnRight(player);
    }

    @Override
    public void playTurn() {
        this.lightCycles.moveAllForward();
        this.view.onModelChanged();
    }
}
