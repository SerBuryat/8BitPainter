package com.thunderTECH.Painter8Bit.model;

import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class Rectangle {
    private int width;
    private int height;
    private final int x;
    private final int y;
    private Pixel[][] pixels;
    private Color color;

    public Rectangle(int x, int y, int width, int height, Color color) {
        this.width = width;
        this.height = height;
        this.x = x * this.width;
        this.y = y * this.height;
        this.color = color;
        pixels = new Pixel[width][height];

        // fill rectangle with pixels
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                pixels[i][j] = new Pixel(i+this.x,j+this.y,this.color, this);
            }
        }
    }

    public void paint(PixelWriter pixelGraphicWriter, Color color) {
        setColor(color);

        for (Pixel[] pixels : this.pixels) {
            for (Pixel pixel : pixels)
                pixel.paint(pixelGraphicWriter, this.color);
        }
    }

    public void paintBorders(PixelWriter pixelGraphicWriter, Color color) {
        // paint top rectangle border
        for (int i = getX(); i < getX() + getWidth(); i++)
            pixelGraphicWriter.setColor(i, getY(), color);
        // paint right rectangle border
        for (int j = getY(); j < getY() + getHeight(); j++)
            pixelGraphicWriter.setColor(getWidth(), j, color);
        // paint bottom rectangle border
        for (int i = getX(); i < getX() + getWidth(); i++)
            pixelGraphicWriter.setColor(i, getHeight(), color);
        // paint left rectangle border
        for (int j = getY(); j < getY() + getWidth(); j++)
            pixelGraphicWriter.setColor(getX(), j, color);
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

    /** return Pixel which has x and y coordinates **/
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
