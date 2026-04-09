package com.jad.esieatron.model;

import com.jad.esieatron.domain.Sprite;

import java.awt.*;

public class Grid {
    private final Dimension dimension;
    private final Tile[][] tiles;

    public Grid(final Dimension dimension) {
        this.dimension = dimension;
        this.tiles = new Tile[dimension.width][dimension.height];
        for (int row = 0; row < this.dimension.height; row++) {
            for (int column = 0; column < this.dimension.width; column++) {
                this.tiles[column][row] = Tile.EMPTY;
            }
        }
    }

    public Sprite[][] getSprites() {
        final Sprite[][] sprites = new Sprite[this.dimension.width][this.dimension.height];
        for (int row = 0; row < this.dimension.height; row++) {
            for (int column = 0; column < this.dimension.width; column++) {
                sprites[column][row] = this.tiles[column][row].getSprite();
            }
        }
        return sprites;
    }

    public final Point normalize(final Point point) {
        return new Point((point.x + this.dimension.width) % this.dimension.width,
                         (point.y + this.dimension.height) % this.dimension.height);
    }
}
