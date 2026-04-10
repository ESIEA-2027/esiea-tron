package com.jad.esieatron.view;

import com.jad.esieatron.controller.IController;
import com.jad.esieatron.domain.GameIntent;
import com.jad.esieatron.domain.Player;
import com.jad.esieatron.model.GameState;
import com.jad.esieatron.model.IModel;
import com.jad.textwindow.TextWindow;
import com.jad.textwindow.TextWindowSettings;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class View implements IView {
    private static final String VIEW_PROPERTIES = "view.properties";
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
        final Properties properties = this.loadProperties(View.VIEW_PROPERTIES);
        List<Player> players = this.model.getPlayers();
        for (Player player : players) {
            for (GameIntent intent : GameIntent.values()) {
                settings.addKeyboardListener(
                        View.parseKeyCode(properties.getProperty("keyBinding." + player.id() + "." + intent.getName())),
                        player.id() + "-" + intent.getName());
            }
        }
        this.window = new TextWindow(settings);
        this.window.setVisible(true);
    }

    @Override
    public void setController(final IController controller) {
        this.controller = controller;
    }

    @Override
    public void handleActiveGameIntent() {
        final List<Player> players = this.model.getPlayers();
        for (Player player : players) {
            for (GameIntent gameIntent : GameIntent.values()) {
                final String intent = player.id() + "-" + gameIntent.getName();
                final boolean isPressed = this.window.isOn(intent);
                final boolean previousState = this.previousKeyStates.getOrDefault(intent, false);
                if (isPressed && !previousState) this.controller.handleGameIntent(player, gameIntent);
                this.previousKeyStates.put(intent, isPressed);
            }
        }
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

    private Properties loadProperties(final String fileName) {
        final Properties properties = new Properties();
        try (InputStream input = this.getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (input != null) properties.load(input);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to load view properties", exception);
        }
        return properties;
    }

    private static int parseKeyCode(final String keyCodeString) {
        final String key = keyCodeString.trim().toUpperCase();
        if (key.length() == 1) {
            return KeyEvent.getExtendedKeyCodeForChar(key.charAt(0));
        } else {
            return switch (key) {
                case "LEFT" -> KeyEvent.VK_LEFT;
                case "RIGHT" -> KeyEvent.VK_RIGHT;
                case "UP" -> KeyEvent.VK_UP;
                case "DOWN" -> KeyEvent.VK_DOWN;
                case "SPACE" -> KeyEvent.VK_SPACE;
                case "ENTER" -> KeyEvent.VK_ENTER;
                case "ESCAPE" -> KeyEvent.VK_ESCAPE;
                default -> throw new IllegalArgumentException("Unsupported key: " + key);
            };
        }
    }
}
