package com.jad.esieatron.view;

import com.jad.esieatron.controller.IController;
import com.jad.esieatron.domain.GameIntent;
import com.jad.esieatron.model.GameState;
import com.jad.esieatron.model.IModel;
import com.jad.textwindow.TextWindow;
import com.jad.textwindow.TextWindowSettings;

import java.awt.*;
import java.awt.event.KeyEvent;

public class View implements IView {
    private final KeysPlayerBinding keysPlayerBinding = new KeysPlayerBinding();

    private TextWindow window;
    private IModel model;
    private IController controller;

    public View() {
        this.keysPlayerBinding.put(KeyEvent.VK_Q, null, GameIntent.TURN_LEFT);
        this.keysPlayerBinding.put(KeyEvent.VK_D, null, GameIntent.TURN_RIGHT);
    }

    @Override
    public void setModel(final IModel model) {
        this.model = model;
        final Dimension gridDimension = this.model.getGridDimension();
        this.model.setOnChange(this::display);
        TextWindowSettings settings = new TextWindowSettings();
        settings.setTitle("Ma fenêtre à moi");
        settings.setScreenDimension(gridDimension);
        for (Gam : this.keysPlayerBinding.keySet()) {
            settings.addKeyboardListener(keyboardInput, this.keysPlayerBinding.get(keyboardInput).gameIntent().name());
        }
        this.window = new TextWindow(settings);
        this.window.setVisible(true);
    }

    @Override
    public void setController(final IController controller) {
        this.controller = controller;
    }

    public void display() {
        GameState gameState = this.model.getState();
        StringBuilder stringBuilder = new StringBuilder();
        for (int row = 0; row < gameState.dimension().height; row++) {
            for (int column = 0; column < gameState.dimension().width; column++) {
                stringBuilder.append(gameState.sprites()[column][row].pixel());
            }
            stringBuilder.append("\n");
        }
        this.window.display(stringBuilder.toString());
    }
}
