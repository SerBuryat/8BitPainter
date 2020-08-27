package com.thunderTECH.Painter8Bit.control.events;

import com.thunderTECH.Painter8Bit.view.panels.PainterCanvas;
import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;
import javafx.scene.transform.Scale;

public class CanvasMouseScroll implements EventHandler<ScrollEvent> {
    private final PainterCanvas painterCanvas;

    public CanvasMouseScroll(PainterCanvas painterCanvas) {
        this.painterCanvas = painterCanvas;
    }

    @Override
    public void handle(ScrollEvent scrollEvent) {
        scrollEvent.consume();

        if (scrollEvent.getDeltaY() == 0)
            return;

        Scale paintPaneScale = new Scale();

        double scaleDelta = 1.1;
        double scaleFactor = (scrollEvent.getDeltaY() > 0) ? scaleDelta : 1/ scaleDelta;

        paintPaneScale.setPivotX(scrollEvent.getX());
        paintPaneScale.setPivotY(scrollEvent.getY());
        paintPaneScale.setX(painterCanvas.getCanvas().getScaleX() * scaleFactor);
        paintPaneScale.setY(painterCanvas.getCanvas().getScaleY() * scaleFactor);

        painterCanvas.getCanvas().getTransforms().addAll(paintPaneScale);
    }
}
