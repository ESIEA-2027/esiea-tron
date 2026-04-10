package com.jad.esieatron.model;

import com.jad.esieatron.domain.Sprite;

import java.awt.*;
import java.util.List;

public record GameState(Dimension dimension,
                        Sprite[][] sprites,
                        List<PlayerState> playerStates) {
    public int getHeight() {
        return this.dimension.height;
    }

    public int getWidth() {
        return this.dimension.width;
    }
}

