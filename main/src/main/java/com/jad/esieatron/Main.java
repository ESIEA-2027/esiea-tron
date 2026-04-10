package com.jad.esieatron;

import com.jad.esieatron.controller.Controller;
import com.jad.esieatron.controller.IController;
import com.jad.esieatron.model.IModel;
import com.jad.esieatron.model.Model;
import com.jad.esieatron.view.IView;
import com.jad.esieatron.view.ViewHuman;
import com.jad.esieatron.view.ViewIA;

public class Main {
    public static void main(String[] args) {
        IController controller = new Controller();
        IModel model = new Model();
        IView viewHuman = new ViewHuman();
        IView viewIA = new ViewIA();

        controller.setModel(model);
        viewHuman.setController(controller);
        viewIA.setController(controller);
        viewHuman.setModel(model);
        viewIA.setModel(model);

        controller.proceed();
    }
}