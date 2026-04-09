package com.jad.esieatron.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.UnaryOperator;

public class LightCycles extends ArrayList<LightCycle> {
    final UnaryOperator<Point> normalizer;

    public LightCycles(final UnaryOperator<Point> normalizer) {
        this.normalizer = normalizer;
    }

    public void moveForwardAll() {
        for (LightCycle lightCycle : this) {
            lightCycle.moveForward(this.normalizer);
        }
    }
}
