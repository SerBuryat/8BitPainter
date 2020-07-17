package com.thunderTECH.Painter8Bit.control;

import com.thunderTECH.Painter8Bit.ActionBuffer;
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
        Pixel pixel = painterCanvas.getPixel((int)mouseEvent.getX(), (int)mouseEvent.getY());

        if(pixel != null) {
            if (mouseEvent.getButton() == MouseButton.PRIMARY)
                ActionBuffer.ADD_TO_BUFFER(pixel.getRectangle());
                painterCanvas.paint(pixel.getRectangle(), painterCanvas.getCurrentRectangleColor());
        }

        if(mouseEvent.getButton() == MouseButton.MIDDLE) {
            double offsetX = mouseEvent.getSceneX() - painterCanvas.getCanvasDragX();
            double offsetY = mouseEvent.getSceneY() - painterCanvas.getCanvasDragY();

            painterCanvas.getCanvas().setTranslateX(painterCanvas.getCanvasTranslateX() + offsetX);
            painterCanvas.getCanvas().setTranslateY(painterCanvas.getCanvasTranslateY() + offsetY);
        }
    }
}
