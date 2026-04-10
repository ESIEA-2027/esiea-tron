package com.jad.esieatron.model;

import com.jad.esieatron.domain.CardinalPoint;
import com.jad.esieatron.domain.Player;

import java.awt.*;

public record PlayerState(Player player,
                          Point position,
                          CardinalPoint direction) {
}
