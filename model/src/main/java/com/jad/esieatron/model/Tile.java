package com.jad.esieatron.model;

import com.jad.esieatron.domain.Sprite;

enum Tile {
    EMPTY(' '),
    WALL('#');

    private Sprite sprite;

    Tile(char symbol) {
        this.sprite = new Sprite(symbol);
    }

    public Sprite getSprite() {
        return this.sprite;
    }
}
