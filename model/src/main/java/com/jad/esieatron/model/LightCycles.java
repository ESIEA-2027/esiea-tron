package com.jad.esieatron.model;

import com.jad.esieatron.domain.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

class LightCycles extends ArrayList<LightCycle> {
    private final UnaryOperator<Point> normalizer;
    private final BiFunction<Point, Player, Boolean> onMoved;

    LightCycles(final UnaryOperator<Point> normalizer, final BiFunction<Point, Player, Boolean> onMoved) {
        this.normalizer = normalizer;
        this.onMoved = onMoved;
    }

    void moveForwardAll() {
        for (LightCycle lightCycle : this) {
            if (!this.onMoved.apply(lightCycle.getPosition(), lightCycle.getPlayer())) lightCycle.crash();
            if (lightCycle.isAlive()) lightCycle.moveForward(this.normalizer);
        }
    }
}
