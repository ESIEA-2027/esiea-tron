package com.jad.esieatron.view;

import com.jad.esieatron.domain.GameIntent;
import com.jad.esieatron.domain.Player;
import com.jad.esieatron.domain.PlayerType;
import com.jad.esieatron.model.GameState;
import com.jad.esieatron.model.PlayerState;
import com.jad.esieatron.utils.EsieaTronUtils;
import com.jad.textwindow.TextWindowSettings;

import java.awt.*;
import java.util.HashMap;

public class ViewIA extends AbstractView {
    private static final String VIEW_PROPERTIES = "view-ia.properties";
    private final HashMap<Player, GameIntent> gameIntents = new HashMap<>();

    public ViewIA() {
        super(false);
    }

    @Override
    public void render(final GameState gameState) {
        for (PlayerState playerState : gameState.playerStates()) {
            if (playerState.player().playerType() == PlayerType.IA) {
                Point nextPosition = EsieaTronUtils.normalize(
                        EsieaTronUtils.getNextPosition(playerState.direction(), playerState.position()),
                        gameState.dimension());
                if (gameState.sprites()[nextPosition.x][nextPosition.y].pixel() != ' ') {
                    this.gameIntents.put(playerState.player(), GameIntent.TURN_LEFT);
                }
            }
        }
    }

    @Override
    protected void initializeWindowExtended(final TextWindowSettings settings) {

    }


    @Override
    public void handleActiveGameIntent() {
        for (Player player : this.gameIntents.keySet()) {
            this.getController().handleGameIntent(player, this.gameIntents.get(player));
        }
        this.gameIntents.clear();
    }
}
