package com.jad.esieatron.view;

import com.jad.esieatron.domain.GameState;
import com.jad.esieatron.domain.Order;
import com.jad.esieatron.domain.Player;
import com.jad.esieatron.model.IModel;
import com.jad.textwindow.TextWindow;
import com.jad.textwindow.TextWindowSettings;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.function.BiConsumer;

public abstract class AbstractView implements IView {
    private static final String WINDOW_WIDTH_KEY = "window.width";
    private static final String WINDOW_HEIGHT_KEY = "window.height";
    private static final String WINDOW_TITLE_KEY = "window.title";
    private static final String WINDOW_ENABLED_KEY = "window.enabled";

    private final String configFileName;
    private TextWindow window;
    private IModel model;

    protected AbstractView(final String configFileName) {
        this.configFileName = configFileName;
    }

    protected static int parseKeyCode(final String raw) {
        final String key = raw.trim().toUpperCase();
        if (key.length() == 1) {
            return java.awt.event.KeyEvent.getExtendedKeyCodeForChar(key.charAt(0));
        }
        return switch (key) {
            case "LEFT" -> java.awt.event.KeyEvent.VK_LEFT;
            case "RIGHT" -> java.awt.event.KeyEvent.VK_RIGHT;
            case "UP" -> java.awt.event.KeyEvent.VK_UP;
            case "DOWN" -> java.awt.event.KeyEvent.VK_DOWN;
            case "SPACE" -> java.awt.event.KeyEvent.VK_SPACE;
            case "ENTER" -> java.awt.event.KeyEvent.VK_ENTER;
            case "ESC", "ESCAPE" -> java.awt.event.KeyEvent.VK_ESCAPE;
            default -> throw new IllegalArgumentException("Unknown key: \"" + raw + "\"");
        };
    }

    @Override
    public final void load() {
        if (this.model == null) {
            throw new IllegalStateException("Model is not set");
        }

        final Properties properties = this.loadProperties();
        final ViewProperties viewProperties = this.loadViewProperties(properties);

        if (!viewProperties.windowEnabled()) {
            return;
        }

        final TextWindowSettings settings = new TextWindowSettings();
        settings.setTitle(viewProperties.windowTitle());
        settings.setScreenHeight(viewProperties.windowDimension().height);
        settings.setScreenWidth(viewProperties.windowDimension().width);
        settings.setListenKeyboard(true);

        this.configureInputBindings(settings, properties, this.model.getPlayers());

        this.window = new TextWindow(settings);
        this.window.setVisible(true);
    }

    private Properties loadProperties() {
        final Properties properties = new Properties();
        try (InputStream stream = this.getClass().getClassLoader().getResourceAsStream(this.configFileName)) {
            if (stream != null) {
                properties.load(stream);
            }
        } catch (IOException ignored) {
            throw new RuntimeException("Failed to load configuration file: " + this.configFileName);
        }
        return properties;
    }

    private ViewProperties loadViewProperties(final Properties properties) {
        final int width = Integer.parseInt(properties.getProperty(AbstractView.WINDOW_WIDTH_KEY, "200"));
        final int height = Integer.parseInt(properties.getProperty(AbstractView.WINDOW_HEIGHT_KEY, "50"));
        final String title = properties.getProperty(AbstractView.WINDOW_TITLE_KEY, "AsciiTron");
        final boolean enabled = Boolean.parseBoolean(properties.getProperty(AbstractView.WINDOW_ENABLED_KEY, "true"));
        return new ViewProperties(new Dimension(width, height), title, enabled);
    }

    protected void configureInputBindings(final TextWindowSettings settings,
                                          final Properties properties,
                                          final List<Player> players) {
    }

    protected final boolean isActionActive(final String action) {
        return this.window != null && this.window.isOn(action);
    }

    private record ViewProperties(Dimension windowDimension, String windowTitle, boolean windowEnabled) {
    }    @Override
    public final void setModel(final IModel model) {
        this.model = model;
    }




    protected final IModel getModel() {
        if (this.model == null) {
            throw new IllegalStateException("Model is not set");
        }
        return this.model;
    }

    @Override
    public final void display() {
        if (this.window == null) {
            return;
        }
        final GameState gameState = this.getModel().getState();
        this.window.display(AbstractView.render(gameState).toString());
    }

    @Override
    public final void onModelChanged() {
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
        // Default implementation for views that do not produce user inputs.
    }
}

