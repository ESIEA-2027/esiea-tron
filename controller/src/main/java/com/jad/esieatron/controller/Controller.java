package com.jad.esieatron.controller;

import com.jad.esieatron.domain.GameIntent;
import com.jad.esieatron.domain.Player;
import com.jad.esieatron.model.IModel;

import java.util.ArrayList;
import java.util.List;

public class Controller implements IController {
    private final List<HandleActiveGameIntent> handleActiveGameIntents = new ArrayList<>();
    private IModel model;

    @Override
    public void addHandleActiveGameIntents(final HandleActiveGameIntent handleActiveGameIntent) {
        this.handleActiveGameIntents.add(handleActiveGameIntent);
    }

    @Override
    public void proceed() {
        for (; ; ) {
            this.model.playTurn();
            this.handleActiveGameIntents.forEach(HandleActiveGameIntent::handleActiveGameIntent);
            try {
                Thread.sleep(50);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void handleGameIntent(final Player player, final GameIntent gameIntent) {
        switch (gameIntent) {
            case TURN_LEFT -> this.model.turnLeft(player);
            case TURN_RIGHT -> this.model.turnRight(player);
        }
    }

    @Override
    public void setModel(final IModel model) {
        this.model = model;
    }
}
