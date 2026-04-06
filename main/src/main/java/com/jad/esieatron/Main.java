package com.jad.esieatron;

import com.jad.esieatron.controller.Controller;
import com.jad.esieatron.controller.IController;
import com.jad.esieatron.model.IModel;
import com.jad.esieatron.model.Model;
import com.jad.esieatron.view.HumanView;
import com.jad.esieatron.view.IView;

public enum Main {
    ;

    public static void main(String[] args) {
        IController controller = new Controller();
        IModel model = new Model();
        IView view = new HumanView();

        controller.setModel(model);
        controller.setView(view);

        model.setView(view);

        view.setModel(model);

        model.load();
        view.load();
        controller.proceed();
    }
}