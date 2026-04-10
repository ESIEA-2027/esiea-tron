package com.jad.esieatron.controller;

import com.jad.esieatron.domain.GameIntent;
import com.jad.esieatron.domain.Player;
import com.jad.esieatron.model.IModel;

public interface IController {
    void setModel(final IModel model);

    void addHandleActiveGameIntents(final HandleActiveGameIntent handleActiveGameIntent);

    void proceed();

    void handleGameIntent(Player player, GameIntent gameIntent);
}
