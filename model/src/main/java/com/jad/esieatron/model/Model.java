package com.jad.esieatron.model;

import com.jad.esieatron.domain.CardinalPoint;
import com.jad.esieatron.domain.Player;
import com.jad.esieatron.domain.Sprite;
import com.jad.esieatron.view.IView;

import java.awt.*;

public class Model implements IModel {
    static final Dimension GRID_DIMENSION = new Dimension(200, 50);

    private final Grid grid;
    private final LightCycles lightCycles;
    private IView view;

    public Model() {
        this.grid = new Grid(Model.GRID_DIMENSION);
        this.lightCycles = new LightCycles(this.grid::normalize, this.grid::putWallAt);
        this.lightCycles.add(new LightCycle(new Player(1, new Sprite('#')),
                                            new Point(10, 10),
                                            CardinalPoint.EAST));
        this.lightCycles.add(new LightCycle(new Player(1, new Sprite('#')),
                                            new Point(20, 20),
                                            CardinalPoint.WEST));
        this.lightCycles.add(new LightCycle(new Player(1, new Sprite('#')),
                                            new Point(30, 30),
                                            CardinalPoint.SOUTH));
    }

    @Override
    public void setView(final IView view) {
        this.view = view;
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
        this.lightCycles.moveForwardAll();
    }
}
