package com.thunderTECH.Painter8Bit;

import com.thunderTECH.Painter8Bit.model.Rectangle;
import javafx.scene.paint.Color;

import java.util.Stack;

public class ActionBuffer {
    private static final int MAX_STACK_SIZE = 100;
    private final static Stack<Rectangle> RECTANGLE_STACK = new Stack<>();
    private final static Stack<Color> COLOR_STACK = new Stack<>();

    public static void ADD_TO_BUFFER(Rectangle rect) {
        if(RECTANGLE_STACK.size() < MAX_STACK_SIZE) {
            if (!RECTANGLE_STACK.contains(rect)) {
                RECTANGLE_STACK.push(rect);
                COLOR_STACK.push(rect.getColor());
            }
        } else {
            REMOVE_LAST_STACKS_ELEMENT();
        }
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

    public static void CLEAR_BUFFER() {
        RECTANGLE_STACK.clear();
        COLOR_STACK.clear();
    }

    private static void REMOVE_LAST_STACKS_ELEMENT() {
        RECTANGLE_STACK.remove(1);
        COLOR_STACK.remove(1);
    }
}
