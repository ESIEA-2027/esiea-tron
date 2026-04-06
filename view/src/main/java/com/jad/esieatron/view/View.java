package com.jad.esieatron.view;

import com.jad.esieatron.domain.GameState;
import com.jad.esieatron.domain.Order;
import com.jad.esieatron.domain.Player;
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
import java.util.function.BiConsumer;

public class View implements IView {
    private static final String DEFAULT_CONFIG_FILE = "view.properties";
    private static final String WINDOW_WIDTH_KEY = "window.width";
    private static final String WINDOW_HEIGHT_KEY = "window.height";
    private static final String WINDOW_TITLE_KEY = "window.title";
    private static final String KEY_BINDING_KEY = "keyBinding";

    private final KeyBindings keyBindings = new KeyBindings();
    private final Map<String, Boolean> previousKeyStates = new HashMap<>();
    private TextWindow window;
    private IModel model;

    @Override
    public void load() {
        if (this.model == null) throw new IllegalStateException("Model is not set");
        TextWindowSettings windowSettings = new TextWindowSettings();
        final Properties properties = View.loadProperties();
        final ViewProperties viewProperties = View.loadViewProperties(properties);
        windowSettings.setTitle(viewProperties.windowTitle());
        windowSettings.setScreenHeight(viewProperties.windowDimension().height);
        windowSettings.setScreenWidth(viewProperties.windowDimension().width);
        windowSettings.setListenKeyboard(true);
        final List<Player> players = this.model.getPlayers();
        for (Player player : players) {
            final String prefix = View.KEY_BINDING_KEY + "." + player.id() + ".";
            for (Order order : Order.values()) {
                final String config = properties.getProperty(prefix + order.getName());
                if (config == null) {
                    throw new IllegalStateException(
                            "Missing key binding for player " + player.id() + " and order " + order);
                }
                final int keyCode = View.parseKeyCode(config);
                final String action = player.id() + "." + order.name();
                windowSettings.addKeyboardListener(keyCode, action);
            }
        }
        this.window = new TextWindow(windowSettings);
        this.window.setVisible(true);
    }

    private static Properties loadProperties() {
        final Properties properties = new Properties();
        try (InputStream stream = View.class.getClassLoader().getResourceAsStream(View.DEFAULT_CONFIG_FILE)) {
            if (stream != null) properties.load(stream);
        } catch (IOException ignored) {
            throw new RuntimeException("Failed to load configuration file: " + View.DEFAULT_CONFIG_FILE);
        }
        return properties;
    }

    private static ViewProperties loadViewProperties(final Properties properties) {
        final int width = Integer.parseInt(properties.getProperty(View.WINDOW_WIDTH_KEY));
        final int height = Integer.parseInt(properties.getProperty(View.WINDOW_HEIGHT_KEY));
        final String windowTitle = properties.getProperty(View.WINDOW_TITLE_KEY);
        return new ViewProperties(new Dimension(width, height), windowTitle);
    }

    private static int parseKeyCode(final String raw) {
        final String key = raw.trim().toUpperCase();
        if (key.length() == 1) {
            return KeyEvent.getExtendedKeyCodeForChar(key.charAt(0));
        }
        return switch (key) {
            case "LEFT" -> KeyEvent.VK_LEFT;
            case "RIGHT" -> KeyEvent.VK_RIGHT;
            case "UP" -> KeyEvent.VK_UP;
            case "DOWN" -> KeyEvent.VK_DOWN;
            case "SPACE" -> KeyEvent.VK_SPACE;
            case "ENTER" -> KeyEvent.VK_ENTER;
            case "ESC", "ESCAPE" -> KeyEvent.VK_ESCAPE;
            default -> throw new IllegalArgumentException("Unknown key: \"" + raw + "\"");
        };
    }

    @Override
    public final void setModel(final IModel model) {
        this.model = model;
    }

    @Override
    public final void display() {
        final GameState gameState = this.model.getState();
        final StringBuilder builder = View.render(gameState);
        this.window.display(builder.toString());
    }

    @Override
    public void onModelChanged() {
        this.display();
    }

    private static StringBuilder render(final GameState gameState) {
        final StringBuilder builder = new StringBuilder();
        for (int row = 0; row < gameState.height(); row++) {
            for (int column = 0; column < gameState.width(); column++) {
                builder.append(gameState.board()[column][row].symbol());
            }
            builder.append('\n');
        }
        return builder;
    }

    @Override
    public void handleActiveInputs(final BiConsumer<Order, Player> handler) {
        final List<Player> players = this.model.getPlayers();
        for (Player player : players) {
            for (Order order : Order.values()) {
                final String action = player.id() + "." + order.name();
                final boolean isPressed = this.window.isOn(action);
                final boolean wasPressedBefore = this.previousKeyStates.getOrDefault(action, false);
                if (isPressed && !wasPressedBefore) handler.accept(order, player);
                this.previousKeyStates.put(action, isPressed);
            }
        }
    }

    private record ViewProperties(Dimension windowDimension, String windowTitle) {
    }

}
