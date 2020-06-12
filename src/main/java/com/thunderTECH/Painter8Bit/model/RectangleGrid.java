package com.thunderTECH.Painter8Bit.model;


import javafx.scene.paint.Color;

public class RectangleGrid {
    private final int width;
    private final int height;
    private final Rectangle[][] rectangleGrid;
    private Color gridLinesColor = Color.LIGHTGRAY;
    private boolean isGridLineVisible = true;

    public RectangleGrid(int width, int height, int rectangleWidth, int rectangleHeight) {
        this.width = width;
        this.height = height;
        rectangleGrid = new Rectangle[width][height];

        // fill grid with rectangles
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                rectangleGrid[x][y] = new Rectangle(x, y, rectangleWidth, rectangleHeight);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle[][] getGrid() {
        return rectangleGrid;
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

    /** return rectangle which contains this x and y coordinates **/
    public Rectangle getRectangle(int x, int y) {
        for(Rectangle[] rectangles : rectangleGrid) {
            for (Rectangle rectangle : rectangles) {
                if(rectangle.contains(x, y))
                    return rectangle;
            }
        }
        return null;
    }
}
