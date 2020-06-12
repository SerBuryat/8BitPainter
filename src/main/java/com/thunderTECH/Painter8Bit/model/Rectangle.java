package com.thunderTECH.Painter8Bit.model;

import javafx.scene.paint.Color;

public class Rectangle {
    private int width;
    private int height;
    private final int x;
    private final int y;
    private final Pixel[][] pixels;
    private Color color = Color.TRANSPARENT; // default is Transparent

    public Rectangle(int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        this.x = x * this.width;
        this.y = y * this.height;
        pixels = new Pixel[width][height];

        // fill rectangle with pixels
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                pixels[i][j] = new Pixel(i+this.x,j+this.y,color);
            }
        }
    }

    /** Return true if rectangle contains x and y coordinates in it**/
    public boolean contains(int x, int y) {
        int maxX = this.x + this.width - 1;
        int maxY = this.y + this.height - 1;

        return ((x >= this.x && x <= maxX) && (y >= this.y && y <= maxY));
    }

    public void setColor(Color color) {
        this.color = color;

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++)
                pixels[i][j].setColor(color);
        }
    }

    public Color getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Pixel[][] getPixels() {
        return pixels;
    }

    public Pixel getPixel(int x, int y) {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                if(pixels[i][j].getX() == x && pixels[i][j].getY() == y)
                    return pixels[i][j];
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "x=" + x +
                ", y=" + y +
                ", color=" + color +
                '}';
    }
}
