package com.jad.esieatron.domain;

import java.util.Random;

public enum CardinalPoint {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public static CardinalPoint getRandom() {
        return CardinalPoint.values()[new Random().nextInt(CardinalPoint.values().length)];
    }

    public CardinalPoint turnLeft() {
        return CardinalPoint.values()[(this.ordinal() - 1 + CardinalPoint.values().length) % CardinalPoint.values().length];
    }

    public CardinalPoint turnRight() {
        return CardinalPoint.values()[(this.ordinal() + 1 + CardinalPoint.values().length) % CardinalPoint.values().length];
    }
}
