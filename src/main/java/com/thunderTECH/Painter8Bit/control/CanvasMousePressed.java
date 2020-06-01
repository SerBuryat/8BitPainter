package com.thunderTECH.Painter8Bit.control;

import com.thunderTECH.Painter8Bit.model.Pixel;
import com.thunderTECH.Painter8Bit.view.panels.PainterCanvas;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


public class CanvasMousePressed implements EventHandler<MouseEvent> {
    private final PainterCanvas painterCanvas;

    public CanvasMousePressed(PainterCanvas painterCanvas) {
        this.painterCanvas = painterCanvas;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseButton.PRIMARY) {
            for(Pixel[] pixels : painterCanvas.getPixelGrid().getGrid()) {
                for (Pixel pixel : pixels) {
                    if(pixel.contains(mouseEvent.getX(), mouseEvent.getY())) {
                        pixel.setColor(painterCanvas.getCurrentColor());
                        painterCanvas.repaint();
                    }
                }
            }
        }
        if(mouseEvent.getButton() == MouseButton.MIDDLE) {
            double paneDragX = mouseEvent.getSceneX();
            double paneDragY = mouseEvent.getSceneY();

            double paneTranslateX = painterCanvas.getCanvas().getTranslateX();
            double paneTranslateY = painterCanvas.getCanvas().getTranslateY();
        }
    }
}
