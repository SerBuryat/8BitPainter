package com.thunderTECH.Painter8Bit.view.panels;


import com.thunderTECH.Painter8Bit.Painter;
import com.thunderTECH.Painter8Bit.control.CanvasMouseDragged;
import com.thunderTECH.Painter8Bit.control.CanvasMousePressed;
import com.thunderTECH.Painter8Bit.control.CanvasMouseScroll;
import com.thunderTECH.Painter8Bit.model.PixelGrid;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PainterCanvas {
    private final Canvas canvas;
    private final GraphicsContext graphic;
    private PixelGrid pixelGrid;

    private Color currentColor;

    public PainterCanvas(int width, int height) {
        canvas = new Canvas(width,height);
        graphic = canvas.getGraphicsContext2D();
        pixelGrid = createPixelGrid();
        currentColor = Color.ORANGE;

        canvas.setOnMousePressed(new CanvasMousePressed(this));
        canvas.setOnMouseDragged(new CanvasMouseDragged(this));
        canvas.setOnScroll(new CanvasMouseScroll(this));

        repaint();
    }

    private PixelGrid createPixelGrid() {
        int gridWidth = Painter.GET_CANVAS_WIDTH() / Painter.GET_PIXEL_SIZE();
        int gridHeight = Painter.GET_CANVAS_HEIGHT() / Painter.GET_PIXEL_SIZE();
        int pixelSize = Painter.GET_PIXEL_SIZE();
        return pixelGrid = new PixelGrid(gridWidth, gridHeight, pixelSize);
    }

    public void repaint() {
        pixelGrid.paint(graphic);
    }

    public void clearCanvas() {
        graphic.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public PixelGrid getPixelGrid() {
        return pixelGrid;
    }
}
