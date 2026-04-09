package com.jad.esieatron.model;

import com.jad.esieatron.domain.CardinalPoint;
import com.jad.esieatron.domain.Player;

import java.awt.*;
import java.util.function.UnaryOperator;

public class LightCycle {
    private final Player player;
    private Point position;
    private CardinalPoint direction;

    public LightCycle(final Player player, final Point position, final CardinalPoint direction) {
        this.player = player;
        this.position = position;
        this.direction = direction;
    }

    public void turnLeft() {
        this.direction = this.direction.turnLeft();
    }

    public void turnRight() {
        this.direction = this.direction.turnRight();
    }

    public void moveForward(final UnaryOperator<Point> normalizer) {
        this.position = normalizer.apply(switch (this.direction) {
            case NORTH -> new Point(this.position.x, this.position.y - 1);
            case EAST -> new Point(this.position.x + 1, this.position.y);
            case SOUTH -> new Point(this.position.x, this.position.y + 1);
            case WEST -> new Point(this.position.x - 1, this.position.y);
        });
    }
}
