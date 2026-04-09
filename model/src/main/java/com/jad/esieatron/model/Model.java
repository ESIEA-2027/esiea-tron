package com.jad.esieatron.model;

import com.jad.esieatron.domain.CardinalPoint;
import com.jad.esieatron.domain.Counter;
import com.jad.esieatron.domain.Player;
import com.jad.esieatron.domain.Sprite;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Model implements IModel {
    static final Dimension GRID_DIMENSION = new Dimension(200, 50);

    private final Grid grid;
    private final LightCycles lightCycles;
    private final Counter nbTurns = new Counter();
    private Runnable onChange;
    private List<Player> players = new ArrayList<>();

    public Model() {
        this.grid = new Grid(Model.GRID_DIMENSION);
        this.lightCycles = new LightCycles(this.grid::normalize, this.grid::tryPlaceWallAt);
        this.players.add(new Player(1, new Sprite('#')));
        this.lightCycles.add(new LightCycle(this.players.getFirst(),
                                            new Point(10, 10),
                                            CardinalPoint.EAST));
    }

    @Override
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(this.players);
    }

    @Override
    public Dimension getGridDimension() {
        return Model.GRID_DIMENSION;
    }

    @Override
    public GameState getState() {
        return new GameState(Model.GRID_DIMENSION, this.grid.getSprites());
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
