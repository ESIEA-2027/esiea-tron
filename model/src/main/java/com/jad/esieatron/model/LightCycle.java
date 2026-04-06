package com.jad.esieatron.model;

import com.jad.esieatron.domain.CardinalPoint;
import com.jad.esieatron.domain.Player;

import java.awt.*;
import java.util.function.UnaryOperator;

class LightCycle {
    private Point position;
    private CardinalPoint direction;

    public LightCycle(final Player player,
                      final Point position,
                      final CardinalPoint direction) {
        this.position = position;
        this.direction = direction;
    }

    public CardinalPoint getDirection() {
        return this.direction;
    }

    public Point getPosition() {
        return this.position;
    }

    public final void turnLeft() {
        this.direction = this.direction.turnLeft();
    }

    public final void turnRight() {
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
