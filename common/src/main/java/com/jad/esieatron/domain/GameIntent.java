package com.jad.esieatron.domain;

public enum GameIntent {
    TURN_LEFT("turn-left"),
    TURN_RIGHT("turn-right");

    private final String name;

    GameIntent(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
