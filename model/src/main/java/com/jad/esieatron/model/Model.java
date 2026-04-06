package com.jad.esieatron.model;

import com.jad.esieatron.domain.CardinalPoint;
import com.jad.esieatron.domain.GameState;
import com.jad.esieatron.domain.Player;
import com.jad.esieatron.domain.Sprite;
import com.jad.esieatron.view.IView;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class Model implements IModel {
    private static final String DEFAULT_CONFIG_FILE = "model.properties";
    private static final String GRID_WIDTH_KEY = "grid.width";
    private static final String GRID_HEIGHT_KEY = "grid.height";
    private static final String NUMBER_OF_LIGHT_CYCLES_KEY = "numberOfLightCycles";
    private static final String MODEL_LIGHT_CYCLE = "lightCycle";
    private final List<Player> players;
    private Grid grid;
    private LightCycles lightCycles;
    private IView view;
    @SuppressWarnings("FieldCanBeLocal")
    private int numberOfLightCycles;
    private Dimension gridDimension;
    private long turn;

    public Model() {
        this.players = new ArrayList<>();
    }

    @Override
    public final void load() {
        final Properties properties = Model.loadProperties();
        final ModelProperties modelProperties = Model.loadModelProperties(properties);
        this.gridDimension = modelProperties.gridDimension();
        this.grid = new Grid(this.gridDimension);
        this.lightCycles = new LightCycles(modelProperties.gridDimension(), this.grid::placeSprite);
        this.turn = 0;
        this.numberOfLightCycles = modelProperties.numberOfLightCycles();
        for (int i = 1; i <= this.numberOfLightCycles; i++) {
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
        this.turn++;
        this.lightCycles.moveAllForward();
        this.view.onModelChanged();
    }

    @Override
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(this.players);
    }

    @Override
    public GameState getState() {
        return new GameState(this.gridDimension.width,
                             this.gridDimension.height,
                             this.grid.getSprites(),
                             this.lightCycles.getPlayerStates(),
                             this.turn);
    }


    private record ModelProperties(Dimension gridDimension, int numberOfLightCycles) {
    }
}
