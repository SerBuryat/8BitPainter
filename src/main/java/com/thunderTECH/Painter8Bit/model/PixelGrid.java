package com.thunderTECH.Painter8Bit.model;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PixelGrid {
    private int width;
    private int height;
    private Pixel[][] grid;
    private Color gridLinesColor = Color.GRAY;
    private boolean isGridLineVisible = true;

    public PixelGrid(int width, int height, int pixelSize) {
        this.width = width;
        this.height = height;
        grid = new Pixel[width][height];
        addPixelsToGrid(width, height, pixelSize);
    }


    public void paint(GraphicsContext graphic) {
        if(isGridLineVisible) {
            paintPixels(graphic);
            paintGridLines(graphic);
        } else
            paintPixels(graphic);
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Pixel[][] getGrid() {
        return grid;
    }

    public void setGridLineVisible(boolean gridLineVisible) {
        isGridLineVisible = gridLineVisible;
    }

    public Color getGridLinesColor() {
        return gridLinesColor;
    }

    /** Load this grid with pixels **/
    private void addPixelsToGrid(int width, int height, int pixelSize) {
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++)
                grid[x][y] = new Pixel(x, y, pixelSize);
        }
    }

    private void paintPixels(GraphicsContext graphic) {
        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++)
                grid[x][y].paint(graphic);
        }
    }

    private void paintGridLines(GraphicsContext graphic) {
        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pixel pixel = grid[x][y];
                graphic.setStroke(gridLinesColor);
                graphic.setLineWidth(0.5);
                graphic.strokeRect(
                        x * pixel.getWidth(), y * pixel.getHeight(),pixel.getWidth(),pixel.getHeight());
            }
        }
    }
}
