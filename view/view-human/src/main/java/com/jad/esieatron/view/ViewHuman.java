package com.jad.esieatron.view;

import com.jad.esieatron.domain.GameIntent;
import com.jad.esieatron.domain.Player;
import com.jad.esieatron.domain.PlayerType;
import com.jad.esieatron.model.GameState;
import com.jad.esieatron.utils.EsieaTronUtils;
import com.jad.textwindow.TextWindowSettings;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ViewHuman extends AbstractView {
    private static final String VIEW_PROPERTIES = "view-human.properties";
    private final Map<String, Boolean> previousKeyStates = new HashMap<>();

    public ViewHuman() {
        super(true);
    }

    @Override
    public void render(final GameState gameState) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int row = 0; row < gameState.getHeight(); row++) {
            for (int column = 0; column < gameState.getWidth(); column++) {
                stringBuilder.append(gameState.sprites()[column][row].pixel());
            }
            stringBuilder.append("\n");
        }
        this.getWindow().display(stringBuilder.toString());
    }

    @Override
    protected void initializeWindowExtended(final TextWindowSettings settings) {
        final Properties properties = EsieaTronUtils.loadProperties(this.getClass(), ViewHuman.VIEW_PROPERTIES);
        List<Player> players = this.getModel().getPlayers();
        for (Player player : players) {
            if (player.playerType() == PlayerType.HUMAN) {
                for (GameIntent intent : GameIntent.values()) {
                    settings.addKeyboardListener(
                            ViewHuman.parseKeyCode(
                                    properties.getProperty("keyBinding." + player.id() + "." + intent.getName())),
                            player.id() + "-" + intent.getName());
                }
            }
        }
    }

    private static int parseKeyCode(final String keyCodeString) {
        final String key = keyCodeString.trim().toUpperCase();
        if (key.length() == 1) {
            return KeyEvent.getExtendedKeyCodeForChar(key.charAt(0));
        } else {
            return switch (key) {
                case "LEFT" -> KeyEvent.VK_LEFT;
                case "RIGHT" -> KeyEvent.VK_RIGHT;
                case "UP" -> KeyEvent.VK_UP;
                case "DOWN" -> KeyEvent.VK_DOWN;
                case "SPACE" -> KeyEvent.VK_SPACE;
                case "ENTER" -> KeyEvent.VK_ENTER;
                case "ESCAPE" -> KeyEvent.VK_ESCAPE;
                default -> throw new IllegalArgumentException("Unsupported key: " + key);
            };
        }
    }

    @Override
    public void handleActiveGameIntent() {
        final List<Player> players = this.getModel().getPlayers();
        for (Player player : players) {
            if (player.playerType() == PlayerType.HUMAN) {
                for (GameIntent gameIntent : GameIntent.values()) {
                    final String intent = player.id() + "-" + gameIntent.getName();
                    final boolean isPressed = this.getWindow().isOn(intent);
                    final boolean previousState = this.previousKeyStates.getOrDefault(intent, false);
                    if (isPressed && !previousState) this.getController().handleGameIntent(player, gameIntent);
                    this.previousKeyStates.put(intent, isPressed);
                }
            }
        }
    }
}
