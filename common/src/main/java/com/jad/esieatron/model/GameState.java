package com.jad.esieatron.model;

import com.jad.esieatron.domain.Sprite;

import java.awt.*;

public record GameState(Dimension dimension,
                        Sprite[][] sprites) {
}

