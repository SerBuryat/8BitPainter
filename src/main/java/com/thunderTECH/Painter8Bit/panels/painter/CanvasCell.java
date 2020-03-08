package com.thunderTECH.Painter8Bit.panels.painter;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class CanvasCell {
    private final int cellX;
    private final int cellY;
    private final int cellWidth;
    private final int cellHeight;
    private Color cellColor;
    private Color borderCellColor = Color.BLACK;

    public CanvasCell(int cellX, int cellY, int cellWidth, int cellHeight) {
        this.cellX = cellX;
        this.cellY = cellY;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
    }

    public void draw(GraphicsContext graphics) {
        graphics.setFill(borderCellColor);
        Rectangle rectangle = new Rectangle(cellX * cellWidth, cellY * cellHeight, cellWidth, cellHeight);
        graphics.strokeRect(cellX * cellWidth, cellY * cellHeight, cellWidth, cellHeight);

    }

    public Color getCellColor() {
        return cellColor;
    }

    public void setCellColor(Color cellColor) {
        this.cellColor = cellColor;
    }
}

