package com.jad.esieatron.domain;

import java.util.List;

public record GameState(
        int width,
        int height,
        Sprite[][] board,
        List<LightCycleState> players,
        long turn
) {
}