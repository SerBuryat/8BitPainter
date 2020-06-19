package com.thunderTECH.Painter8Bit.model;

import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class Rectangle {
    private final int width;
    private final int height;
    private final int x;
    private final int y;
    private final Pixel[][] pixels;
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

        for (int i = getX(); i < getX() + getWidth(); i++) {
            // top border
            getPixel(i, getY()).paint(pixelGraphicWriter, color);
            //bottom border
            getPixel(i, getY() + getHeight()-1).paint(pixelGraphicWriter, color);
        }

        for (int j = getY(); j < getY() + getHeight(); j++) {
            // left border
            getPixel(getX(), j).paint(pixelGraphicWriter, color);
            // right border
            getPixel(getX()+ getWidth()-1, j).paint(pixelGraphicWriter,color);
        }
    }

    public void setColor(Color color) {
        this.color = color;

        for(Pixel[] pixels : this.pixels) {
            for(Pixel pixel : pixels)
                pixel.setColor(color);
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
                "width=" + width +
                ", height=" + height +
                ", x=" + x +
                ", y=" + y +
                ", color=" + color +
                '}';
    }
}
