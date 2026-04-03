package com.jad.esieatron.view;

import com.jad.esieatron.controller.IController;
import com.jad.esieatron.model.IModel;
import com.jad.textwindow.TextWindow;
import com.jad.textwindow.TextWindowSettings;

import java.awt.event.KeyEvent;

public class View implements IView {
    private final TextWindow window;
    private IModel model;
    private IController controller;

    public View() {
        TextWindowSettings windowSettings = new TextWindowSettings();
        windowSettings.setTitle("Ma fenêtre à moi");
        windowSettings.setScreenHeight(10);
        windowSettings.setScreenWidth(40);
        windowSettings.setListenKeyboard(true);
        windowSettings.addKeyboardListener(KeyEvent.VK_Q, "plop");
        this.window = new TextWindow(windowSettings);
    }

    @Override
    public void setModel(final IModel model) {
        this.model = model;
    }

    @Override
    public void setController(final IController controller) {
        this.controller = controller;
    }

    @Override
    public void display() {
        this.window.setVisible(true);
        this.window.display(this.window.isOn("plop") ? "plop" : "Pas plop");
    }
}
