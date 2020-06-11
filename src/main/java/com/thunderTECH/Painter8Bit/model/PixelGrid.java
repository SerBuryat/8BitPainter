package com.thunderTECH.Painter8Bit.model;


import javafx.scene.paint.Color;

public class PixelGrid {
    private final int width;
    private final int height;
    private final Pixel[][] pixelsGrid;
    private Color gridLinesColor = Color.LIGHTGRAY;
    private boolean isGridLineVisible = true;

    public PixelGrid(int width, int height, int pixelSize) {
        this.width = width;
        this.height = height;
        pixelsGrid = new Pixel[width][height];
        addPixelsToGrid(width, height, pixelSize);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Pixel[][] getGrid() {
        return pixelsGrid;
    }

    public void setGridLineVisible(boolean gridLineVisible) {
        isGridLineVisible = gridLineVisible;
    }

    public boolean isGridLineVisible() {
        return isGridLineVisible;
    }

    public Color getGridLinesColor() {
        return gridLinesColor;
    }

    public boolean isCorrectGridBorders(int i, int j) {
        return (i >= 0 && i <= (width - 1)) && (j >= 0 && j <= (height - 1));
    }

    /** return pixel which contains this x and y coordinates ON SCREEN NOT IN GRID  **/
    public Pixel getPixel(double x, double y) {
        for(Pixel[] pixels : pixelsGrid) {
            for (Pixel pixel : pixels) {
                if(pixel.contains(x, y))
                    return pixel;
            }
        }
        return null;
    }

    /** Load this grid with pixels **/
    private void addPixelsToGrid(int width, int height, int pixelSize) {
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++)
                pixelsGrid[x][y] = new Pixel(x, y, pixelSize);
        }
    }
}
