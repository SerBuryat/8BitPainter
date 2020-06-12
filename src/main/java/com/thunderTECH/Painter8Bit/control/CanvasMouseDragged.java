package com.thunderTECH.Painter8Bit.control;

import com.thunderTECH.Painter8Bit.model.Rectangle;
import com.thunderTECH.Painter8Bit.view.panels.PainterCanvas;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class CanvasMouseDragged implements EventHandler<MouseEvent> {
    private final PainterCanvas painterCanvas;

    public CanvasMouseDragged(PainterCanvas painterCanvas) {
        this.painterCanvas = painterCanvas;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        Rectangle rectangle =
                painterCanvas.getRectangleGrid().getRectangle((int)mouseEvent.getX(),(int)mouseEvent.getY());

        if(rectangle != null) {
            if (mouseEvent.getButton() == MouseButton.PRIMARY)
                painterCanvas.paint(rectangle, painterCanvas.getCurrentRectangleColor());
        }
    }
}
