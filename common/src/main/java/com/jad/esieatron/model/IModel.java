package com.jad.esieatron.model;

import com.jad.esieatron.domain.Player;
import com.jad.esieatron.view.IView;

public interface IModel {
    void load();

    void setView(IView view);

    void turnLeft(Player player);

    void turnRight(Player player);

    void playTurn();
}
