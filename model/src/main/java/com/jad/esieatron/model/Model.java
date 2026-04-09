package com.jad.esieatron.model;

import com.jad.esieatron.view.IView;

import java.awt.*;

public class Model implements IModel {
    static final Dimension GRID_DIMENSION = new Dimension(200, 50);
    private final Grid grid;
    private IView view;

    public Model() {
        this.grid = new Grid(Model.GRID_DIMENSION);
    }

    @Override
    public void setView(final IView view) {
        this.view = view;
    }

    @Override
    public Dimension getGridDimension() {
        return Model.GRID_DIMENSION;
    }
}
