package com.jad.esieatron.model;

import com.jad.esieatron.domain.CardinalPoint;
import com.jad.esieatron.domain.Player;
import com.jad.esieatron.utils.EsieaTronUtils;

import java.awt.*;
import java.util.function.UnaryOperator;

class LightCycle {
    private final Player player;
    private Point position;
    private CardinalPoint direction;
    private Boolean alive;

    LightCycle(final Player player, final Point position, final CardinalPoint direction) {
        this.player = player;
        this.position = position;
        this.direction = direction;
        this.alive = true;
    }


    Boolean isAlive() {
        return this.alive;
    }

    void moveForward(final UnaryOperator<Point> normalizer) {
        this.position = normalizer.apply(EsieaTronUtils.getNextPosition(this.direction, this.position));
    }

    void turnLeft() {
        this.direction = this.direction.turnLeft();
    }

    void turnRight() {
        this.direction = this.direction.turnRight();
    }

    final Point getPosition() {
        return this.position;
    }

    Player getPlayer() {
        return this.player;
    }

    void crash() {
        this.alive = false;
    }

    public PlayerState getState() {
        return new PlayerState(this.player, this.position, this.direction);
    }
}
