package com.jad.esieatron.model;

import java.awt.*;

public interface IModel {
    Dimension getGridDimension();

    GameState getState();

    void playTurn();

    void setOnChange(Runnable onChange);
}
