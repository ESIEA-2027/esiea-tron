package com.jad.esieatron.model;

import com.jad.esieatron.domain.Sprite;

import java.awt.*;

class Grid {
    private final Dimension size;
    private final Tile[][] tiles;

    Grid(final Dimension size) {
        this.size = size;
        this.tiles = new Tile[size.width][size.height];

        this.initializeWithEmptyTiles(size);
    }

    private void initializeWithEmptyTiles(final Dimension size) {
        for (int row = 0; row < size.height; row++) {
            for (int column = 0; column < size.width; column++) {
                this.tiles[column][row] = Tile.EMPTY;
            }
        }
    }

    public final Dimension getSize() {
        return new Dimension(this.size);
    }

    public final void placeSprite(final Point position, final Sprite sprite) {
        this.tiles[position.x][position.y] = Tile.WALL;
    }

    public final Tile getTileAt(final Point position) {
        return this.tiles[position.x][position.y];
    }

    public Sprite[][] getSprites() {
        final Sprite[][] sprites = new Sprite[this.size.width][this.size.height];
        for (int row = 0; row < this.size.height; row++) {
            for (int column = 0; column < this.size.width; column++) {
                sprites[column][row] = this.tiles[column][row].getSprite();
            }
        }
        return sprites;
    }
}
