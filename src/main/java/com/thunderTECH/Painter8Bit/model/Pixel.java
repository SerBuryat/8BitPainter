package com.thunderTECH.Painter8Bit.model;

import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class Pixel {
    private final int x;
    private final int y;
    private final Rectangle rectOwner;
    private Color color;

    public Pixel(int x, int y, Color color, Rectangle rectOwner) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.rectOwner = rectOwner;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void paint(PixelWriter pixelGraphicWriter, Color color) {
        setColor(color);
        pixelGraphicWriter.setColor(x,y,this.color);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    /** gives rectangle which has this pixel **/
    public Rectangle getRectangle() {
        return rectOwner;
    }

    @Override
    public String toString() {
        return "Pixel{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
