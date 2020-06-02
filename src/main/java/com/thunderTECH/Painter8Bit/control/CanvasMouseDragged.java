package com.thunderTECH.Painter8Bit.control;

import com.thunderTECH.Painter8Bit.model.Pixel;
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
        Pixel pixel = painterCanvas.getPixelGrid().getPixelByCoordinates(mouseEvent.getX(), mouseEvent.getY());

        if(pixel != null) {
            if (mouseEvent.getButton() == MouseButton.PRIMARY)
                painterCanvas.paintPixel(pixel, painterCanvas.getCurrentPixelColor());
        }
    }
}