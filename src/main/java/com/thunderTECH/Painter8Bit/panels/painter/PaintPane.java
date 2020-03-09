package com.thunderTECH.Painter8Bit.panels.painter;

import com.thunderTECH.Painter8Bit.Painter;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class PaintPane extends GridPane {
    private final ArrayList<Rectangle> rectanglesGrid;

    public PaintPane(int paneWidth, int paneHeight) {
        this.setWidth(paneWidth);
        this.setHeight(paneHeight);

        rectanglesGrid = new ArrayList<>();
        final int gridWidth = paneWidth / 40; // test
        final int gridHeight = paneHeight / 40; // test
        this.loadPaintGridWithRectangles(gridWidth, gridHeight);
    }

    private void loadPaintGridWithRectangles(int gridWidth, int gridHeight) {
        final int rectWidth = Painter.WIDTH / gridWidth;
        final int rectHeight = Painter.HEIGHT / gridHeight;
        for(int x = 0; x < gridWidth; x++) {
            for(int y = 0; y < gridHeight; y++) {
                Rectangle rect = createRect(x, y, rectWidth, rectHeight);

                rectanglesGrid.add(rect);
                this.add(rect, x, y);
            }
        }
    }

    private Rectangle createRect(int x, int y, int rectWidth, int rectHeight) {
        Color defaultRectColor = Color.WHITE;
        Color defaultRectStrokeColor = Color.LIGHTGRAY;

        Rectangle rect =
                new Rectangle(x * rectWidth, y * rectHeight,
                        rectWidth, rectHeight);
        rect.setFill(defaultRectColor);
        rect.setStroke(defaultRectStrokeColor);

        return rect;
    }
}
