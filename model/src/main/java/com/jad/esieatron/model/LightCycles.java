package com.jad.esieatron.model;

import com.jad.esieatron.domain.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.UnaryOperator;

public class LightCycles extends ArrayList<LightCycle> {
    private final UnaryOperator<Point> normalizer;
    private final BiConsumer<Point, Player> onMoved;

    public LightCycles(final UnaryOperator<Point> normalizer, final BiConsumer<Point, Player> onMoved) {
        this.normalizer = normalizer;
        this.onMoved = onMoved;
    }

    public void moveForwardAll() {
        for (LightCycle lightCycle : this) {
            this.onMoved.accept(lightCycle.getPosition(), lightCycle.getPlayer());
            lightCycle.moveForward(this.normalizer);
        }
    }
}
