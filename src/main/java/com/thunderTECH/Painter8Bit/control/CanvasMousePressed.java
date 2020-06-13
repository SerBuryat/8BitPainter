package com.thunderTECH.Painter8Bit.control;

import com.thunderTECH.Painter8Bit.model.Pixel;
import com.thunderTECH.Painter8Bit.view.panels.PainterCanvas;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


public class CanvasMousePressed implements EventHandler<MouseEvent> {
    private final PainterCanvas painterCanvas;

    public CanvasMousePressed(PainterCanvas painterCanvas) {
        this.painterCanvas = painterCanvas;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        Pixel pixel = painterCanvas.getPixel((int)mouseEvent.getX(), (int)mouseEvent.getY());

        if(pixel != null) {
            if (mouseEvent.getButton() == MouseButton.PRIMARY)
                painterCanvas.paint(pixel.getRectangle(), painterCanvas.getCurrentRectangleColor());

            if (mouseEvent.getButton() == MouseButton.SECONDARY)
                painterCanvas.setCurrentRectangleColor(pixel.getRectangle().getColor());
        }
    }
}
