package com.jad.esieatron.model;

import com.jad.esieatron.domain.Sprite;

enum Tile {
    EMPTY(' '),
    WALL('#');

    private final Sprite sprite;

    Tile(char symbol) {
        this.sprite = new Sprite(symbol);
    }

    public static char symbolOf(final Tile tile) {
        return tile.getSprite().symbol();
    }

    public Sprite getSprite() {
        return this.sprite;
    }
}
