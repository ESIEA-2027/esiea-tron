package com.jad.esieatron;

import com.jad.esieatron.controller.Controller;
import com.jad.esieatron.controller.IController;
import com.jad.esieatron.model.IModel;
import com.jad.esieatron.model.Model;
import com.jad.esieatron.view.IView;
import com.jad.esieatron.view.View;

public class Main {
    public static void main(String[] args) {
        IController controller = new Controller();
        IModel model = new Model();
        IView view = new View();
        controller.setModel(model);
        controller.setView(view);
        model.setView(view);
        view.setController(controller);
        view.setModel(model);
    }
}