package com.jad.esieatron.view;

import com.jad.esieatron.controller.IController;
import com.jad.esieatron.model.IModel;

public interface IView {
    void setModel(IModel model);

    void setController(IController controller);
}
