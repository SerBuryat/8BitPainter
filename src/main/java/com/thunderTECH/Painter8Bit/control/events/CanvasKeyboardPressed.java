package com.thunderTECH.Painter8Bit.control.events;

import com.thunderTECH.Painter8Bit.control.Painter;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class CanvasKeyboardPressed implements EventHandler<KeyEvent> {
    private final Painter painter;

    public CanvasKeyboardPressed(Painter painter) {
        this.painter = painter;
    }
    @Override
    public void handle(KeyEvent keyEvent) {
        if(keyEvent.isControlDown() && keyEvent.getCode().equals(KeyCode.Z)) {
            painter.undoPaint();
        }
    }
}
