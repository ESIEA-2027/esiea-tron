package com.jad.esieatron.model;

import com.jad.esieatron.domain.Player;
import com.jad.esieatron.domain.Sprite;

import java.awt.*;

class Grid {
    private final Dimension dimension;
    private final Tile[][] tiles;

    Grid(final Dimension dimension) {
        this.dimension = dimension;
        this.tiles = new Tile[dimension.width][dimension.height];
        for (int row = 0; row < this.dimension.height; row++) {
            for (int column = 0; column < this.dimension.width; column++) {
                this.tiles[column][row] = Tile.EMPTY;
            }
        }
    }

    Sprite[][] getSprites() {
        final Sprite[][] sprites = new Sprite[this.dimension.width][this.dimension.height];
        for (int row = 0; row < this.dimension.height; row++) {
            for (int column = 0; column < this.dimension.width; column++) {
                sprites[column][row] = this.tiles[column][row].getSprite();
            }
        }
        return sprites;
    }

    Boolean tryPlaceWallAt(final Point position, final Player player) {
        if (!this.isEmpty(position)) return false;
        this.putWallAt(position, player);
        return true;
    }

    final Boolean isEmpty(final Point position) {
        final Point pointNormalized = this.normalize(position);
        return this.tiles[pointNormalized.x][pointNormalized.y] == Tile.EMPTY;
    }

    final void putWallAt(final Point position, final Player player) {
        final Point pointNormalized = this.normalize(position);
        this.tiles[pointNormalized.x][pointNormalized.y] = Tile.get(player);
    }

    final Point normalize(final Point point) {
        return new Point((point.x + this.dimension.width) % this.dimension.width,
                         (point.y + this.dimension.height) % this.dimension.height);
    }
}
