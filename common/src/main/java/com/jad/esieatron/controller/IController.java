package com.jad.esieatron.controller;

import com.jad.esieatron.domain.Order;
import com.jad.esieatron.domain.Player;
import com.jad.esieatron.model.IModel;
import com.jad.esieatron.view.IView;

public interface IController {
    void setView(final IView view);

    void setModel(final IModel model);

    void proceed();

    void handleOrder(final Order order, final Player player);
}
