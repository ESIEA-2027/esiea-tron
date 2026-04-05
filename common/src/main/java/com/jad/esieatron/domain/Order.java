package com.jad.esieatron.domain;

public enum Order {
    TURN_LEFT("turn-left"),
    TURN_RIGHT("turn-right");

    private final String name;

    Order(final String name) {
        this.name = name;
    }

    public final String getName() {
        return this.name;
    }
}
