package com.jad.esieatron.model;

enum CardinalPoint {
    NORTH("north"),
    EAST("east"),
    SOUTH("south"),
    WEST("west");

    private final String name;

    CardinalPoint(final String name) {
        this.name = name;
    }

    public static CardinalPoint getRandom() {
        return CardinalPoint.values()[(int) (Math.random() * CardinalPoint.values().length)];
    }

    public final String getName() {
        return this.name;
    }

    public final CardinalPoint turnLeft() {
        return CardinalPoint.values()[(this.ordinal() - 1 + CardinalPoint.values().length) % CardinalPoint.values().length];
    }

    public final CardinalPoint turnRight() {
        return CardinalPoint.values()[(this.ordinal() + 1 + CardinalPoint.values().length) % CardinalPoint.values().length];
    }
}

