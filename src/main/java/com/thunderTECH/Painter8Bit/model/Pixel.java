package com.thunderTECH.Painter8Bit.model;

import javafx.scene.paint.Color;

public class Pixel {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private Color color = Color.TRANSPARENT;

    public Pixel(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.width = size;
        this.height = size;
    }

    /** Return true if pixel contains x and y coordinates in it**/
    public boolean contains(double x, double y) {
        double minX = this.x * this.width;
        double minY = this.y * this.height;
        double maxX = this.x * this.width + this.width;
        double maxY = this.y * this.height + this.height;
        return ((x > minX && x < maxX) && (y > minY && y < maxY));
    }

    public void setColor(Color color) {
        this.color = color;
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
