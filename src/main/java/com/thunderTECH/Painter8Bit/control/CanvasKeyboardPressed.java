package com.thunderTECH.Painter8Bit.control;

import com.thunderTECH.Painter8Bit.ActionBuffer;
import com.thunderTECH.Painter8Bit.model.Rectangle;
import com.thunderTECH.Painter8Bit.view.panels.PainterCanvas;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class CanvasKeyboardPressed implements EventHandler<KeyEvent> {
    private final PainterCanvas painterCanvas;

    public CanvasKeyboardPressed(PainterCanvas painterCanvas) {
        this.painterCanvas = painterCanvas;
    }
    @Override
    public void handle(KeyEvent keyEvent) {
        if(keyEvent.isControlDown() && keyEvent.getCode().equals(KeyCode.Z)) {
            Rectangle rect = ActionBuffer.GET_LAST_ACTIONED_RECTANGLE();
            if(rect != null) {
                painterCanvas.paint(rect, rect.getColor());
            }
        }
    }
}
