package com.jad.esieatron.model;

import com.jad.esieatron.view.IView;

import java.awt.*;

public interface IModel {
    void setView(IView view);

    Dimension getGridDimension();
}
