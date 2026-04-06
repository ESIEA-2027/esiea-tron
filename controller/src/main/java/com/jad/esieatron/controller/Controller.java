package com.jad.esieatron.controller;

import com.jad.esieatron.domain.Order;
import com.jad.esieatron.domain.Player;
import com.jad.esieatron.model.IModel;
import com.jad.esieatron.view.IView;

public class Controller implements IController {
    private IModel model;
    private IView view;

    @Override
    public final void setView(final IView view) {
        this.view = view;
    }

    @Override
    public final void setModel(final IModel model) {
        this.model = model;
    }

    @Override
    public final void proceed() {
        if (this.model == null) throw new IllegalStateException("Model is not set");
        if (this.view == null) throw new IllegalStateException("View is not set");
        for (; ; ) {
            this.view.handleActiveInputs(this::handleOrder);
            this.model.playTurn();
            try {
                Thread.sleep(50); // ms
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    @Override
    public final void handleOrder(final Order order, final Player player) {
        switch (order) {
            case TURN_LEFT -> this.model.turnLeft(player);
            case TURN_RIGHT -> this.model.turnRight(player);
        }
    }
}
