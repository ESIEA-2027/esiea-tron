package com.jad.esieatron.view;

import com.jad.esieatron.controller.IController;
import com.jad.esieatron.domain.GameIntent;
import com.jad.esieatron.model.GameState;
import com.jad.esieatron.model.IModel;
import com.jad.textwindow.TextWindow;
import com.jad.textwindow.TextWindowSettings;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class View implements IView {
    private final Map<String, Boolean> previousKeyStates = new HashMap<>();
    private TextWindow window;
    private IModel model;
    private IController controller;

    @Override
    public void setModel(final IModel model) {
        this.model = model;
        final Dimension gridDimension = this.model.getGridDimension();
        this.model.setOnChange(this::display);
        TextWindowSettings settings = new TextWindowSettings();
        settings.setTitle("Ma fenêtre à moi");
        settings.setScreenDimension(gridDimension);
        settings.addKeyboardListener(KeyEvent.VK_Q, "1-turn-left");
        settings.addKeyboardListener(KeyEvent.VK_D, "1-turn-right");
        settings.addKeyboardListener(KeyEvent.VK_LEFT, "2-turn-left");
        settings.addKeyboardListener(KeyEvent.VK_RIGHT, "2-turn-right");
        this.window = new TextWindow(settings);
        this.window.setVisible(true);
    }

    @Override
    public void setController(final IController controller) {
        this.controller = controller;
    }

    @Override
    public void handleActiveGameIntent() {
        boolean isPressed = this.window.isOn("1-turn-left");
        boolean previousState = this.previousKeyStates.getOrDefault("1-turn-left", false);
        if (isPressed && !previousState) {
            this.controller.handleGameIntent(this.model.getPlayers().getFirst(),
                                             GameIntent.TURN_LEFT);
        }
        this.previousKeyStates.put("1-turn-left", isPressed);

        isPressed = this.window.isOn("1-turn-right");
        previousState = this.previousKeyStates.getOrDefault("1-turn-right", false);
        if (isPressed && !previousState) {
            this.controller.handleGameIntent(this.model.getPlayers().getFirst(),
                                             GameIntent.TURN_RIGHT);
        }
        this.previousKeyStates.put("1-turn-right", isPressed);

        isPressed = this.window.isOn("2-turn-right");
        previousState = this.previousKeyStates.getOrDefault("2-turn-right", false);
        if (isPressed && !previousState) {
            this.controller.handleGameIntent(this.model.getPlayers().get(1),
                                             GameIntent.TURN_RIGHT);
        }
        this.previousKeyStates.put("2-turn-right", isPressed);

        isPressed = this.window.isOn("2-turn-right");
        previousState = this.previousKeyStates.getOrDefault("2-turn-right", false);
        if (isPressed && !previousState) {
            this.controller.handleGameIntent(this.model.getPlayers().get(1),
                                             GameIntent.TURN_RIGHT);
        }
        this.previousKeyStates.put("2-turn-right", isPressed);
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
