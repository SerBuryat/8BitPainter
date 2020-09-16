package com.thunderTECH.Painter8Bit.control.events;

import com.thunderTECH.Painter8Bit.ActionBuffer;
import com.thunderTECH.Painter8Bit.control.Painter;
import com.thunderTECH.Painter8Bit.model.Pixel;
import com.thunderTECH.Painter8Bit.view.panels.PainterCanvas;
import com.thunderTECH.Painter8Bit.view.panels.instruments.InstrumentPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class CanvasMouseDragged implements EventHandler<MouseEvent> {
    private final Painter painter;


    public CanvasMouseDragged(Painter painter) {
        this.painter = painter;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        Pixel pixel = painter.getCanvasPixel((int)mouseEvent.getX(), (int)mouseEvent.getY());

        if(pixel != null) {
            if (mouseEvent.getButton() == MouseButton.PRIMARY)
                painter.paint(pixel.getRectangle(), painter.getCurrentColor());
        }

        if(mouseEvent.getButton() == MouseButton.MIDDLE) {
            double offsetX = mouseEvent.getSceneX() - painter.getPainterCanvas().getCanvasDragX();
            double offsetY = mouseEvent.getSceneY() - painter.getPainterCanvas().getCanvasDragY();

            painter.getPainterCanvas()
                    .getCanvas().setTranslateX(painter.getPainterCanvas().getCanvasTranslateX() + offsetX);
            painter.getPainterCanvas()
                    .getCanvas().setTranslateY(painter.getPainterCanvas().getCanvasTranslateY() + offsetY);
        }
    }
}
