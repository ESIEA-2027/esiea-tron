package com.jad.esieatron.view;

import com.jad.esieatron.domain.Order;
import com.jad.esieatron.domain.Player;
import com.jad.textwindow.TextWindowSettings;

import java.util.List;
import java.util.Properties;
import java.util.function.BiConsumer;


public final class AiView extends AbstractView {
    private static final String CONFIG_FILE = "view-ia.properties";

    public AiView() {
        super(AiView.CONFIG_FILE);
    }

    @Override
    public void handleActiveInputs(final BiConsumer<Order, Player> handler) {

    }

    @Override
    protected void configureInputBindings(final TextWindowSettings settings, final Properties properties,
                                          final List<Player> players) {
    }
}

