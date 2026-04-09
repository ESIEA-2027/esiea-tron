package com.jad.esieatron.view;

import com.jad.esieatron.domain.GameIntent;
import com.jad.esieatron.domain.Player;

import java.util.HashMap;

class KeysPlayerBinding extends HashMap<GameIntent, PlayerKeyEvent> {
    public void put(final Integer keyEvent,
                    final Player player,
                    final GameIntent gameIntent) {
        this.put(gameIntent, new PlayerKeyEvent(player, keyEvent));
    }
}
