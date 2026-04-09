package com.jad.esieatron.controller;

import com.jad.esieatron.model.IModel;
import com.jad.esieatron.view.IView;

public class Controller implements IController {
    private IModel model;
    private IView view;

    @Override
    public void setView(final IView view) {
        this.view = view;
    }

    @Override
    public void setModel(final IModel model) {
        this.model = model;
    }

    @Override
    public void proceed() {
        for (; ; ) {
            this.model.playTurn();
            this.view.display();
            try {
                Thread.sleep(50);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
