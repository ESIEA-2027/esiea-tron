package com.jad.esieatron.model;

import com.jad.esieatron.domain.Player;
import com.jad.esieatron.domain.Sprite;

import java.util.ArrayList;
import java.util.List;

final class Tile {
    public static final Tile EMPTY = new Tile(new Player(0, new Sprite(' '), null));

    private static final List<Tile> tiles = new ArrayList<>();
    private final Player player;

    private Tile(final Player player) {
        this.player = player;
    }

    static Tile get(final Player player) {
        for (Tile tile : Tile.tiles) {
            if (tile.player == player) return tile;
        }
        Tile tile = new Tile(player);
        Tile.tiles.add(tile);
        return tile;
    }

    Sprite getSprite() {
        return this.player.sprite();
    }
}
