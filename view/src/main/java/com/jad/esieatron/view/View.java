package com.jad.esieatron.view;

import com.jad.esieatron.controller.IController;
import com.jad.esieatron.model.IModel;
import com.jad.textwindow.TextWindow;
import com.jad.textwindow.TextWindowSettings;

public class View implements IView {
    private final TextWindow window;
    private IModel model;
    private IController controller;

    public View() {
        TextWindowSettings windowSettings = new TextWindowSettings();
        windowSettings.setFontSize(12f);
        this.window = new TextWindow();
    }

    @Override
    public void setModel(final IModel model) {
        this.model = model;
    }

    @Override
    public void setController(final IController controller) {
        this.controller = controller;
    }
}
