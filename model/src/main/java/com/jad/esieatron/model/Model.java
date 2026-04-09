package com.jad.esieatron.model;

import com.jad.esieatron.domain.CardinalPoint;
import com.jad.esieatron.domain.Counter;
import com.jad.esieatron.domain.Player;
import com.jad.esieatron.domain.Sprite;

import java.awt.*;

public class Model implements IModel {
    static final Dimension GRID_DIMENSION = new Dimension(200, 50);

    private final Grid grid;
    private final LightCycles lightCycles;
    private final Counter nbTurns = new Counter();
    private Runnable onChange;

    public Model() {
        this.grid = new Grid(Model.GRID_DIMENSION);
        this.lightCycles = new LightCycles(this.grid::normalize, this.grid::tryPlaceWallAt);
        this.lightCycles.add(new LightCycle(new Player(1, new Sprite('#')),
                                            new Point(10, 10),
                                            CardinalPoint.EAST));
        this.lightCycles.add(new LightCycle(new Player(2, new Sprite('@')),
                                            new Point(20, 20),
                                            CardinalPoint.WEST));
        this.lightCycles.add(new LightCycle(new Player(3, new Sprite('&')),
                                            new Point(30, 30),
                                            CardinalPoint.SOUTH));
        this.lightCycles.add(new LightCycle(new Player(3, new Sprite('?')),
                                            new Point(50, 40),
                                            CardinalPoint.SOUTH));
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
}
