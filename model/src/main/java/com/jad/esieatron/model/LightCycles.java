package com.jad.esieatron.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class LightCycles extends ArrayList<LightCycle> {
    private final UnaryOperator<Point> normalizer;
    private final Consumer<Point> onMoved;

    public LightCycles(final UnaryOperator<Point> normalizer, final Consumer<Point> onMoved) {
        this.normalizer = normalizer;
        this.onMoved = onMoved;
    }

    public void moveForwardAll() {
        for (LightCycle lightCycle : this) {
            this.onMoved.accept(lightCycle.getPosition());
            lightCycle.moveForward(this.normalizer);
        }
    }
}
