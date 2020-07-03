package com.thunderTECH.Painter8Bit.model;

import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Rectangle implements Serializable {
    private int width;
    private int height;
    private int x;
    private int y;
    private transient Pixel[][] pixels;
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
                pixels[i][j] = new Pixel(i+this.x,j+this.y,color, this);
            }
        }
    }

    public void paint(PixelWriter pixelGraphicWriter, Color color) {
        setColor(color);

        for (Pixel[] pixels : this.pixels) {
            for (Pixel pixel : pixels)
                pixel.paint(pixelGraphicWriter, color);
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

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
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

    private void writeObject(ObjectOutputStream obj) throws IOException {
        obj.writeInt(getWidth());
        obj.writeInt(getHeight());

        obj.writeInt(getX());
        obj.writeInt(getY());

        obj.writeDouble(color.getRed());
        obj.writeDouble(color.getGreen());
        obj.writeDouble(color.getBlue());
        obj.writeDouble(color.getOpacity());
    }

    private void readObject(ObjectInputStream obj) throws IOException, ClassNotFoundException {
        setWidth(obj.readInt());
        setHeight(obj.readInt());

        setX(obj.readInt());
        setY(obj.readInt());

        Color color = Color.color(obj.readDouble(), obj.readDouble(), obj.readDouble(), obj.readDouble());

        pixels = new Pixel[width][height];
        // fill rectangle with pixels
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                pixels[i][j] = new Pixel(i+this.x,j+this.y,color, this);
            }
        }

        setColor(color);
    }
}
