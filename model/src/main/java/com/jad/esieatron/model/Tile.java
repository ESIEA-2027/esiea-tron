package com.jad.esieatron.model;

public enum Tile {
    WALL(new com.jad.esieatron.domain.Sprite('#')),
    EMPTY(new com.jad.esieatron.domain.Sprite(' '));

    private final com.jad.esieatron.domain.Sprite sprite;

    Tile(final com.jad.esieatron.domain.Sprite sprite) {
        this.sprite = sprite;
    }

    public com.jad.esieatron.domain.Sprite getSprite() {
        return this.sprite;
    }
}
