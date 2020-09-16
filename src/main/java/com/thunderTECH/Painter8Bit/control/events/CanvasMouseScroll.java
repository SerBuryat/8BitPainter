package com.thunderTECH.Painter8Bit.control.events;

import com.thunderTECH.Painter8Bit.control.Painter;
import com.thunderTECH.Painter8Bit.view.panels.PainterCanvas;
import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;
import javafx.scene.transform.Scale;

public class CanvasMouseScroll implements EventHandler<ScrollEvent> {
    private final Painter painter;

    public CanvasMouseScroll(Painter painter) {
        this.painter = painter;
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
        paintPaneScale.setX(painter.getPainterCanvas().getCanvas().getScaleX() * scaleFactor);
        paintPaneScale.setY(painter.getPainterCanvas().getCanvas().getScaleY() * scaleFactor);

        painter.getPainterCanvas().getCanvas().getTransforms().addAll(paintPaneScale);
    }
}
