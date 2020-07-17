package com.thunderTECH.Painter8Bit.control;

import com.thunderTECH.Painter8Bit.view.panels.PainterCanvas;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class CanvasMouseMoved implements EventHandler<MouseEvent> {
    private final PainterCanvas painterCanvas;

    public CanvasMouseMoved(PainterCanvas painterCanvas) {
        this.painterCanvas = painterCanvas;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if(painterCanvas.getCanvas().contains(mouseEvent.getX(),mouseEvent.getSceneY()))
            painterCanvas.getCanvas().requestFocus();
    }
}
