package com.jad.esieatron.controller;

import com.jad.esieatron.domain.GameIntent;
import com.jad.esieatron.domain.Player;
import com.jad.esieatron.model.IModel;
import com.jad.esieatron.view.IView;

public interface IController {
    void setView(IView view);

    void setModel(IModel model);

    void proceed();

    void handleGameIntent(Player player, GameIntent gameIntent);
}
