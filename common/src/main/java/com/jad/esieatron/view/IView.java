package com.jad.esieatron.view;

import com.jad.esieatron.controller.HandleActiveGameIntent;
import com.jad.esieatron.controller.IController;
import com.jad.esieatron.model.IModel;

public interface IView extends HandleActiveGameIntent {
    void setModel(IModel model);

    void setController(IController controller);
}
