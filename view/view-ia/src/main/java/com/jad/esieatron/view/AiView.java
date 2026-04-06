package com.jad.esieatron.view;

/**
 * Minimal IA view scaffold: it can display a dedicated window when enabled,
 * and currently does not emit control inputs.
 */
public final class AiView extends AbstractView {
    private static final String CONFIG_FILE = "view-ia.properties";

    public AiView() {
        super(AiView.CONFIG_FILE);
    }
}

