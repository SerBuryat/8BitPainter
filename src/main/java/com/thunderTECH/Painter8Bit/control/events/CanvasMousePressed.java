package com.thunderTECH.Painter8Bit.control.events;

import com.thunderTECH.Painter8Bit.ActionBuffer;
import com.thunderTECH.Painter8Bit.control.Painter;
import com.thunderTECH.Painter8Bit.model.Pixel;
import com.thunderTECH.Painter8Bit.view.panels.instruments.InstrumentPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


public class CanvasMousePressed implements EventHandler<MouseEvent> {
    private final Painter painter;

    public CanvasMousePressed(Painter painter) {
        this.painter = painter;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        painter.getPainterCanvas().getCanvas().requestFocus();

        Pixel pixel = painter.getCanvasPixel((int)mouseEvent.getX(), (int)mouseEvent.getY());

        if(pixel != null) {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {

                painter.paint(pixel.getRectangle(), painter.getCurrentColor());
            }

            if (mouseEvent.getButton() == MouseButton.SECONDARY)
                painter.setCurrentColor(pixel.getRectangle().getColor());

            if(mouseEvent.getButton() == MouseButton.MIDDLE) {
                painter.getPainterCanvas().setCanvasDragX(mouseEvent.getSceneX());
                painter.getPainterCanvas().setCanvasDragY(mouseEvent.getSceneY());

                painter.getPainterCanvas()
                        .setCanvasTranslateX(painter.getPainterCanvas().getCanvas().getTranslateX());
                painter.getPainterCanvas()
                        .setCanvasTranslateY(painter.getPainterCanvas().getCanvas().getTranslateY());
            }

        }
    }
}
