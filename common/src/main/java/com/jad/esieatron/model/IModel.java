package com.jad.esieatron.model;

import com.jad.esieatron.domain.Player;

import java.awt.*;
import java.util.List;

public interface IModel {
    List<Player> getPlayers();

    Dimension getGridDimension();

    GameState getState();

    void playTurn();

    void setOnChange(Runnable onChange);

    void turnLeft(Player player);

    void turnRight(Player player);
}
