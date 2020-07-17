package com.thunderTECH.Painter8Bit;

import com.thunderTECH.Painter8Bit.model.Rectangle;
import javafx.scene.paint.Color;

import java.util.Stack;

public class ActionBuffer {
    private final static Stack<Rectangle> RECTANGLE_STACK = new Stack<>();
    private final static Stack<Color> COLOR_STACK = new Stack<>();

    public static void ADD_TO_BUFFER(Rectangle rect) {
        RECTANGLE_STACK.push(rect);
        COLOR_STACK.push(rect.getColor());
    }

    public static Rectangle GET_LAST_ACTIONED_RECTANGLE() {
        if(RECTANGLE_STACK.size() > 0 && COLOR_STACK.size()>0) {
            Rectangle rect = RECTANGLE_STACK.pop();
            Color color = COLOR_STACK.pop();
            rect.setColor(color);
            return rect;
        }
        return null;
    }
}
