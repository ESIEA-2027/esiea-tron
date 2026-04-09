package com.jad.esieatron.view;

import com.jad.esieatron.controller.IController;
import com.jad.esieatron.model.GameState;
import com.jad.esieatron.model.IModel;
import com.jad.textwindow.TextWindow;
import com.jad.textwindow.TextWindowSettings;

import java.awt.*;

public class View implements IView {
    private TextWindow window;
    private IModel model;
    private IController controller;
    private Dimension gridDimension;

    public View() {
    }

    @Override
    public void setModel(final IModel model) {
        this.model = model;
        this.gridDimension = this.model.getGridDimension();
        TextWindowSettings settings = new TextWindowSettings();
        settings.setTitle("Ma fenêtre à moi");
        settings.setScreenDimension(this.gridDimension);
        this.window = new TextWindow(settings);
        this.window.setVisible(true);
    }

    @Override
    public void setController(final IController controller) {
        this.controller = controller;
    }

    @Override
    public void display() {
        GameState gridState = this.model.getState();
    }
}
