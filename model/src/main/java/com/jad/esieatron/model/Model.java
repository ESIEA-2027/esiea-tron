package com.jad.esieatron.model;

import com.jad.esieatron.domain.CardinalPoint;
import com.jad.esieatron.domain.Counter;
import com.jad.esieatron.domain.Player;
import com.jad.esieatron.domain.Sprite;
import com.jad.esieatron.utils.EsieaTronUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class Model implements IModel {
    private final Dimension gridDimension;
    private final Grid grid;
    private final LightCycles lightCycles;
    private final Counter nbTurns = new Counter();
    private Runnable onChange;
    private List<Player> players = new ArrayList<>();

    public Model() {
        final Properties properties = EsieaTronUtils.loadProperties(this.getClass(), "model.properties");
        this.gridDimension = new Dimension(Integer.parseInt(properties.getProperty("grid-width")),
                                           Integer.parseInt(properties.getProperty("grid-height")));
        this.grid = new Grid(this.gridDimension);
        this.lightCycles = new LightCycles(this.grid::normalize, this.grid::tryPlaceWallAt);
        final Integer nbPlayers = Integer.parseInt(properties.getProperty("numberOfLightCycles"));
        for (int numPlayer = 1; numPlayer <= nbPlayers; numPlayer++) {
            final String[] playerConfig = properties.getProperty("lightcycle." + numPlayer).split(",");
            final Character pixel = playerConfig[0].charAt(0);
            final Player player = new Player(numPlayer, new Sprite(pixel));
            final Point start = new Point(Integer.parseInt(playerConfig[1]), Integer.parseInt(playerConfig[2]));
            final CardinalPoint direction = CardinalPoint.valueOf(playerConfig[3]);
            this.players.add(player);
            this.lightCycles.add(new LightCycle(player, start, direction));
        }
    }

    @Override
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(this.players);
    }

    @Override
    public Dimension getGridDimension() {
        return this.gridDimension;
    }

    @Override
    public GameState getState() {
        return new GameState(this.gridDimension, this.grid.getSprites());
    }

    @Override
    public void playTurn() {
        this.nbTurns.increment();
        this.grid.resetChanged();
        this.lightCycles.moveForwardAll();
        if (this.grid.hasChanged() && this.onChange != null) this.onChange.run();
    }

    @Override
    public void setOnChange(final Runnable onChange) {
        this.onChange = onChange;
    }

    @Override
    public void turnLeft(final Player player) {
        this.lightCycles.turnLeft(player);
    }

    @Override
    public void turnRight(final Player player) {
        this.lightCycles.turnRight(player);
    }
}
