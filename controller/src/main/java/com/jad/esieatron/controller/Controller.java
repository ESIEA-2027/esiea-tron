package com.jad.esieatron.controller;

import com.jad.esieatron.model.IModel;

public class Controller implements IController {
    private IModel model;

    @Override
    public void setModel(final IModel model) {
        this.model = model;
    }

    @Override
    public void proceed() {
        for (; ; ) {
            this.model.playTurn();
            try {
                Thread.sleep(50);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
