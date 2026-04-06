package com.jad.esieatron.view;

import com.jad.esieatron.domain.Order;
import com.jad.esieatron.domain.Player;

import java.util.function.BiConsumer;


public final class AiView extends AbstractView {
    private static final String CONFIG_FILE = "view-ia.properties";

    public AiView() {
        super(AiView.CONFIG_FILE);
    }

    @Override
    public void handleActiveInputs(final BiConsumer<Order, Player> handler) {

    }
}

