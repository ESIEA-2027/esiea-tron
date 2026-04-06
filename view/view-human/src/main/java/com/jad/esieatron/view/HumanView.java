package com.jad.esieatron.view;

import com.jad.esieatron.domain.Order;
import com.jad.esieatron.domain.Player;
import com.jad.textwindow.TextWindowSettings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiConsumer;

public final class HumanView extends AbstractView {
    private static final String CONFIG_FILE = "view-human.properties";
    private static final String KEY_BINDING_KEY = "keyBinding";

    private final Map<String, Boolean> previousKeyStates = new HashMap<>();

    public HumanView() {
        super(HumanView.CONFIG_FILE);
    }

    @Override
    protected void configureInputBindings(final TextWindowSettings settings,
                                          final Properties properties,
                                          final List<Player> players) {
        for (Player player : players) {
            final String prefix = HumanView.KEY_BINDING_KEY + "." + player.id() + ".";
            for (Order order : Order.values()) {
                final String config = properties.getProperty(prefix + order.getName());
                if (config == null) {
                    throw new IllegalStateException(
                            "Missing key binding for player " + player.id() + " and order " + order);
                }
                final int keyCode = HumanView.parseKeyCode(config);
                final String action = player.id() + "." + order.name();
                settings.addKeyboardListener(keyCode, action);
            }
        }
    }

    @Override
    public void handleActiveInputs(final BiConsumer<Order, Player> handler) {
        final List<Player> players = this.getPlayers();
        for (Player player : players) {
            for (Order order : Order.values()) {
                final String action = player.id() + "." + order.name();
                final boolean isPressed = this.isActionActive(action);
                final boolean wasPressedBefore = this.previousKeyStates.getOrDefault(action, false);
                if (isPressed && !wasPressedBefore) handler.accept(order, player);
                this.previousKeyStates.put(action, isPressed);
            }
        }
    }
}

