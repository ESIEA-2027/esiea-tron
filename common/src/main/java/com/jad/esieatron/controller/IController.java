package com.jad.esieatron.controller;

import com.jad.esieatron.model.IModel;

public interface IController {
    void setModel(IModel model);

    void proceed();
}
