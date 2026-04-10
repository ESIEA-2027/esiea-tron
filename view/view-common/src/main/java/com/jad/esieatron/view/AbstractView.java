package com.jad.esieatron.view;

import com.jad.esieatron.controller.IController;
import com.jad.esieatron.model.GameState;
import com.jad.esieatron.model.IModel;
import com.jad.textwindow.TextWindow;
import com.jad.textwindow.TextWindowSettings;

abstract class AbstractView implements IView {
    private final boolean activeWindow;
    private TextWindow window;
    private IModel model;
    private IController controller;

    protected AbstractView(final boolean activeWindow) {
        this.activeWindow = activeWindow;
    }

    protected final TextWindow getWindow() {
        return this.window;
    }

    protected final IModel getModel() {
        return this.model;
    }

    @Override
    public final void setModel(final IModel model) {
        this.model = model;
        this.model.addOnChange(this::onModelChanged);
        if (this.activeWindow) this.initializeWindow();
    }

    public void onModelChanged() {
        this.render(this.model.getState());
    }

    private void initializeWindow() {
        TextWindowSettings settings = new TextWindowSettings();
        settings.setTitle("Ma fenêtre à moi");
        settings.setScreenDimension(this.model.getGridDimension());

        this.initializeWindowExtended(settings);

        this.window = new TextWindow(settings);
        this.window.setVisible(true);
    }

    public abstract void render(final GameState gameState);

    protected abstract void initializeWindowExtended(TextWindowSettings settings);

    public final IController getController() {
        return this.controller;
    }

    @Override
    public final void setController(final IController controller) {
        this.controller = controller;
        this.controller.addHandleActiveGameIntents(this);
    }

    @Override
    public abstract void handleActiveGameIntent();
}
