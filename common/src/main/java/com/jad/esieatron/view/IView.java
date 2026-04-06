package com.jad.esieatron.view;

import com.jad.esieatron.domain.Order;
import com.jad.esieatron.domain.Player;
import com.jad.esieatron.model.IModel;

import java.util.function.BiConsumer;

public interface IView {
    void load();

    void setModel(IModel model);

    void display();

    void onModelChanged();

    void handleActiveInputs(BiConsumer<Order, Player> handler);
}
