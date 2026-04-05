package com.jad.esieatron.model;

import com.jad.esieatron.domain.Player;
import com.jad.esieatron.domain.Sprite;

import java.awt.*;
import java.util.HashMap;
import java.util.function.BiConsumer;

public class LightCycles {
    final Dimension gridSize;
    final BiConsumer<Point, Sprite> onCycleMoved;
    private final HashMap<Player, LightCycle> lightCycles;

    public LightCycles(final Dimension gridSize, final BiConsumer<Point, Sprite> onCycleMoved) {
        this.gridSize = gridSize;
        this.onCycleMoved = onCycleMoved;
        this.lightCycles = new HashMap<>();
    }

    public final void turnLeft(final Player player) {
        this.lightCycles.get(player).turnLeft();
    }

    public final void turnRight(final Player player) {
        this.lightCycles.get(player).turnRight();
    }

    public final void moveAllForward() {
        this.lightCycles.forEach((player, lightCycle) -> {
            this.onCycleMoved.accept(lightCycle.getPosition(), player.sprite());
            lightCycle.moveForward(this::makeRealPoint);
        });
    }

    public final Point makeRealPoint(final Point point) {
        return new Point((point.x + this.gridSize.width) % this.gridSize.width,
                         (point.y + this.gridSize.height) % this.gridSize.height);
    }

    public void addPlayer(final Player player) {
        if (this.has(player)) return;
        final Point position = this.getRandomPosition();
        final CardinalPoint direction = CardinalPoint.getRandom();
        this.lightCycles.put(player, new LightCycle(player, position, direction));
    }

    public final boolean has(final Player player) {
        return this.lightCycles.containsKey(player);
    }

    public final Point getRandomPosition() {
        final int x = (int) (Math.random() * this.gridSize.width);
        final int y = (int) (Math.random() * this.gridSize.height);
        return new Point(x, y);
    }
}
