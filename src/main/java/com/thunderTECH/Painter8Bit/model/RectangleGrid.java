package com.thunderTECH.Painter8Bit.model;


import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class RectangleGrid {
    private final int width;
    private final int height;
    private final Rectangle[][] rectangles;
    private Color linesColor;
    private boolean isGridLineVisible = true;

    public RectangleGrid(int width, int height, int rectWidth, int rectHeight,Color linesColor, Color rectColor) {
        this.width = width;
        this.height = height;
        this.linesColor = linesColor;
        rectangles = new Rectangle[width][height];

        // fill grid with rectangles
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                rectangles[x][y] = new Rectangle(x, y, rectWidth, rectHeight, rectColor);
            }
        }
    }

    public void paint(PixelWriter pixelGraphicWriter, Color color) {
        setGridLineVisible(true);
        setLinesColor(color);
        for(int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                getRectangles()[x][y].paintBorders(pixelGraphicWriter, color);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle[][] getRectangles() {
        return rectangles;
    }

    public void setGridLineVisible(boolean gridLineVisible) {
        isGridLineVisible = gridLineVisible;
    }

    public boolean isGridLineVisible() {
        return isGridLineVisible;
    }

    public void setLinesColor(Color linesColor) {
        this.linesColor = linesColor;
    }

    public Color getLinesColor() {
        return linesColor;
    }

    /** return rectangle which contains this x and y coordinates **/
    public Rectangle getRectangle(int x, int y) {
        for(Rectangle[] rectangles : rectangles) {
            for (Rectangle rectangle : rectangles) {
                if(rectangle.contains(x, y))
                    return rectangle;
            }
        }
        return null;
    }
}
