package com.thunderTECH.Painter8Bit.panels.painter;

import com.thunderTECH.Painter8Bit.Painter;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;

public class PaintPane extends BorderPane {
    private final int canvasWidth;
    private final int canvasHeight;

    private final Canvas canvas;
    private final GraphicsContext graphics;

    private final ArrayList<CanvasCell> paintGrid;

    public PaintPane(int canvasWidth, int canvasHeight) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        this.canvas = new Canvas(this.canvasWidth,this.canvasHeight);
        this.graphics = this.canvas.getGraphicsContext2D();


        paintGrid = new ArrayList<>();
        final int gridWidth = canvasWidth / 40; // test
        final int gridHeight = canvasHeight / 40; // test
        this.loadPaintGridWithPaintCells(gridWidth, gridHeight);

        this.setCenter(this.canvas);
    }

    private void loadPaintGridWithPaintCells(int gridWidth, int gridHeight) {
        final int cellWidth = Painter.WIDTH / gridWidth;
        final int cellHeight = Painter.HEIGHT / gridHeight;
        for(int x = 0; x < gridWidth; x++) {
            for(int y = 0; y < gridHeight; y++) {
                CanvasCell cell = new CanvasCell(x, y, cellWidth, cellHeight);
                paintGrid.add(cell);
                cell.draw(graphics);
            }
        }
    }

    public int getCanvasWidth() {
        return canvasWidth;
    }

    public int getCanvasHeight() {
        return canvasHeight;
    }
}
