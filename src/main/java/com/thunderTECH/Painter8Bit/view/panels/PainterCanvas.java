package com.thunderTECH.Painter8Bit.view.panels;

import com.thunderTECH.Painter8Bit.Painter;
import com.thunderTECH.Painter8Bit.control.CanvasMouseDragged;
import com.thunderTECH.Painter8Bit.control.CanvasMousePressed;
import com.thunderTECH.Painter8Bit.control.CanvasMouseScroll;
import com.thunderTECH.Painter8Bit.model.Pixel;
import com.thunderTECH.Painter8Bit.model.Rectangle;
import com.thunderTECH.Painter8Bit.model.RectangleGrid;
import com.thunderTECH.Painter8Bit.view.panels.instruments.ColorsPalette;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.image.RenderedImage;

public class PainterCanvas {
    private final Canvas canvas;
    private final PixelWriter pixelGraphicWriter;
    private final RectangleGrid rectangleGrid;
    private final Pixel[][] pixels;
    private Color currentRectangleColor;

    public PainterCanvas(int width, int height) {
        canvas = new Canvas(width, height);
        rectangleGrid = createPixelGrid();
        pixels = createPixels();
        pixelGraphicWriter = canvas.getGraphicsContext2D().getPixelWriter();
        currentRectangleColor = Color.BLACK;

        canvas.setOnMousePressed(new CanvasMousePressed(this));
        canvas.setOnMouseDragged(new CanvasMouseDragged(this));
        canvas.setOnScroll(new CanvasMouseScroll(this));

        paint();
    }

    /** Paint single rectangle in given color **/
    public void paint(Rectangle rectangle, Color color) {
        rectangle.paint(pixelGraphicWriter, color);

        if (rectangleGrid.isGridLineVisible())
            rectangle.paintBorders(pixelGraphicWriter, rectangleGrid.getLinesColor());
    }
    /** Paint or repaint given rectangles **/
    public void paint(Rectangle[] rectangles) {
        for (Rectangle rectangle : rectangles)
            paint(rectangle, rectangle.getColor());
    }
    /** Paint or repaint all canvas **/
    public void paint() {
        for(Rectangle[] rectangles : rectangleGrid.getRectangles()) {
            paint(rectangles);
        }
        if(rectangleGrid.isGridLineVisible())
            paintGridLines();
    }
    /** Clear all canvas **/
    public void clear() {
        for(Rectangle[] rectangles : rectangleGrid.getRectangles()) {
            for (Rectangle rectangle : rectangles)
                rectangle.setColor(Color.TRANSPARENT);
        }
        paint();
    }


    public void paintGridLines() {
        rectangleGrid.paint(pixelGraphicWriter, rectangleGrid.getLinesColor());
    }

    public void clearGridLines() {
        rectangleGrid.setGridLineVisible(false);
        paint();
    }

    /** makes canvas snapshot and save it like .png **/
    public RenderedImage getSnapshotImage() {
        clearGridLines();

        //Pad the capture area
        WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());

        // parameters for remove background
        SnapshotParameters sp = new SnapshotParameters();
        sp.setFill(Color.TRANSPARENT);

        WritableImage snapshot = canvas.snapshot(sp, writableImage);
        removeAntialiasingFromSnapshot(snapshot);

        paintGridLines();

        return SwingFXUtils.fromFXImage(snapshot, null);
    }
    /** remove blur effect from rectangles borders **/
    private void removeAntialiasingFromSnapshot(WritableImage snapshot) {
        PixelWriter writer = snapshot.getPixelWriter();

        for(int x = 0; x < snapshot.getWidth(); x++) {
            for(int y = 0; y < snapshot.getHeight(); y++) {
                writer.setColor(x,y,pixels[x][y].getColor());
            }
        }
    }


    public void setCurrentRectangleColor(Color currentRectangleColor) {
        this.currentRectangleColor = currentRectangleColor;
        ColorsPalette.showCurrentColorOnPalette(currentRectangleColor);
    }

    public Color getCurrentRectangleColor() {
        return currentRectangleColor;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public RectangleGrid getRectangleGrid() {
        return rectangleGrid;
    }

    public Rectangle getRectangle(int x, int y) {
        if(checkCanvasBounds(x,y))
            return pixels[x][y].getRectangle();
        return null;
    }

    private boolean checkCanvasBounds(int x, int y) {
        return (x >= 0 && x < canvas.getWidth()) && (y >= 0 && y < canvas.getHeight());
    }

    private RectangleGrid createPixelGrid() {
        int gridWidth = Painter.GET_CANVAS_WIDTH() / Painter.GET_RECTANGLE_WIDTH();
        int gridHeight = Painter.GET_CANVAS_HEIGHT() / Painter.GET_RECTANGLE_HEIGHT();
        int rectangleWidth = Painter.GET_RECTANGLE_WIDTH();
        int rectangleHeight = Painter.GET_RECTANGLE_HEIGHT();
        Color gridLinesColor = Painter.GET_GRID_LINES_COLOR();
        Color rectangleColor = Painter.GET_RECTANGLE_COLOR();

        return new RectangleGrid(gridWidth, gridHeight, rectangleWidth, rectangleHeight,gridLinesColor, rectangleColor);
    }

    private Pixel[][] createPixels() {
        Pixel[][] pixels = new Pixel[(int) canvas.getWidth()][(int) canvas.getHeight()];

        for(int x = 0; x < canvas.getWidth(); x++) {
            for(int y = 0; y < canvas.getHeight(); y++) {
                pixels[x][y] = rectangleGrid.getRectangle(x,y).getPixel(x,y);
            }
        }

        return pixels;
    }
}
