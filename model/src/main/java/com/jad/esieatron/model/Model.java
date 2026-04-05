package com.jad.esieatron.model;

import com.jad.esieatron.domain.Player;
import com.jad.esieatron.domain.Sprite;
import com.jad.esieatron.view.IView;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Model implements IModel {
    private static final String DEFAULT_CONFIG_FILE = "application.properties";
    private static final String GRID_WIDTH_KEY = "model.grid.width";
    private static final String GRID_HEIGHT_KEY = "model.grid.height";
    private static final String NUMBER_OF_LIGHT_CYCLES_KEY = "model.numberOfLightCycles";
    private static final String MODEL_LIGHT_CYCLE = "model.lightCycle";
    @SuppressWarnings({"FieldCanBeLocal", "MismatchedQueryAndUpdateOfCollection"})
    private final List<Player> players;
    @SuppressWarnings("FieldCanBeLocal")
    private Grid grid;
    private LightCycles lightCycles;
    private IView view;

    public Model() {
        this.players = new ArrayList<>();
    }

    @Override
    public final void load() {
        final Properties properties = Model.loadProperties();
        final ModelProperties modelProperties = Model.loadModelProperties(properties);
        this.grid = new Grid(modelProperties.gridDimension());
        this.lightCycles = new LightCycles(modelProperties.gridDimension(), this.grid::placeSprite);

        final int numberOfLightCycles = modelProperties.numberOfLightCycles();
        for (int i = 0; i < numberOfLightCycles; i++) {
            final String config = properties.getProperty(Model.MODEL_LIGHT_CYCLE + "." + i);
            final String[] configParts = config.split(",");
            final Sprite sprite = new Sprite(configParts[0].charAt(0));
            final Point start = new Point(Integer.parseInt(configParts[1]), Integer.parseInt(configParts[2]));
            final CardinalPoint startDirection = CardinalPoint.valueOf(configParts[3]);
            final Player player = new Player(i, sprite);
            this.players.add(player);
            this.lightCycles.add(player, start, startDirection);
        }
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

    private static ModelProperties loadModelProperties(final Properties properties) {
        final int width = Integer.parseInt(properties.getProperty(Model.GRID_WIDTH_KEY));
        final int height = Integer.parseInt(properties.getProperty(Model.GRID_HEIGHT_KEY));
        final int numberOfLightCycles = Integer.parseInt(properties.getProperty(Model.NUMBER_OF_LIGHT_CYCLES_KEY));
        return new ModelProperties(new Dimension(width, height), numberOfLightCycles);
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

    private record ModelProperties(Dimension gridDimension, int numberOfLightCycles) {
    }
}
