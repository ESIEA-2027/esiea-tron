package com.jad.esieatron.controller;

import com.jad.esieatron.model.IModel;
import com.jad.esieatron.view.IView;

public interface IController {
    void setView(IView view);

    void setModel(IModel model);

    void proceed();
}
