package com.thunderTECH.Painter8Bit.view.panels;

import com.thunderTECH.Painter8Bit.Painter;
import com.thunderTECH.Painter8Bit.control.CanvasMouseDragged;
import com.thunderTECH.Painter8Bit.control.CanvasMousePressed;
import com.thunderTECH.Painter8Bit.control.CanvasMouseScroll;
import com.thunderTECH.Painter8Bit.model.Pixel;
import com.thunderTECH.Painter8Bit.model.Rectangle;
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
    private final Rectangle[][] rectangles;
    private final Pixel[][] pixels;
    private boolean isGridLineVisible;
    private Color gridLinesColor;
    private Color currentRectangleColor;


    public PainterCanvas(int width, int height) {
        canvas = createCanvas(width,height);
        rectangles = createRectangles();
        pixels = createPixels();

        pixelGraphicWriter = canvas.getGraphicsContext2D().getPixelWriter();

        isGridLineVisible = true;
        gridLinesColor = Painter.GET_GRID_LINES_COLOR();
        currentRectangleColor = Color.BLACK;

        canvas.setOnMousePressed(new CanvasMousePressed(this));
        canvas.setOnMouseDragged(new CanvasMouseDragged(this));
        canvas.setOnScroll(new CanvasMouseScroll(this));

        paint();
    }

    /** Paint single rectangle in given color **/
    public void paint(Rectangle rectangle, Color color) {
        rectangle.paint(pixelGraphicWriter, color);

        if (isGridLineVisible)
            rectangle.paintBorders(pixelGraphicWriter, gridLinesColor);
    }
    /** Paint or repaint all canvas **/
    public void paint() {
        for(Rectangle[] rectangles : this.rectangles) {
            for(Rectangle rectangle : rectangles)
                paint(rectangle, rectangle.getColor());
        }
    }
    /** Clear all canvas **/
    public void clear() {
        for(Rectangle[] rectangles : this.rectangles) {
            for (Rectangle rectangle : rectangles) {
                paint(rectangle, Color.TRANSPARENT);
            }
        }
    }


    public void paintGridLines() {
        setGridLineVisible(true);
        paint();
    }

    public void clearGridLines() {
        setGridLineVisible(false);
        paint();
    }


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


    public void setGridLineVisible(boolean isGridLineVisible) {
        this.isGridLineVisible = isGridLineVisible;
    }

    public boolean isGridLineVisible() {
        return isGridLineVisible;
    }


    public Canvas getCanvas() {
        return canvas;
    }

    public Rectangle getRectangle(int x, int y) {
        int posX = x / Painter.GET_RECT_WIDTH();
        int posY = y / Painter.GET_RECT_HEIGHT();

        if(isCorrectCanvasBounds(posX,posY))
            return rectangles[posX][posY];

        return null;
    }

    public Pixel getPixel(int x, int y) {
        if(isCorrectCanvasBounds(x,y))
            return pixels[x][y];
        return null;
    }

    private boolean isCorrectCanvasBounds(int x, int y) {
        return (x >= 0 && x < canvas.getWidth()) && (y >= 0 && y < canvas.getHeight());
    }


    private Canvas createCanvas(double width, double height) {
        Canvas canvas = new Canvas(width,height);

        //change canvas size if it can't divide size completely(with reminder)
        if(canvas.getWidth() % Painter.GET_RECT_WIDTH() != 0 || canvas.getHeight() % Painter.GET_RECT_HEIGHT() != 0) {
            canvas.setWidth(canvas.getWidth() - (canvas.getWidth() % Painter.GET_RECT_WIDTH()));
            canvas.setHeight(canvas.getHeight() - (canvas.getHeight() % Painter.GET_RECT_HEIGHT()));
        }

        return canvas;
    }

    private Rectangle[][] createRectangles() {
        int width = Painter.GET_CANVAS_WIDTH() / Painter.GET_RECT_WIDTH();
        int height = Painter.GET_CANVAS_HEIGHT() / Painter.GET_RECT_HEIGHT();
        Rectangle[][] rectangles = new Rectangle[width][height];

        int rectWidth = Painter.GET_RECT_WIDTH();
        int rectHeight = Painter.GET_RECT_HEIGHT();
        Color rectColor = Painter.GET_RECT_COLOR();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                rectangles[x][y] = new Rectangle(x,y,rectWidth,rectHeight,rectColor);
            }
        }

        return rectangles;
    }

    private Pixel[][] createPixels() {
        Pixel[][] pixels = new Pixel[(int) canvas.getWidth()][(int) canvas.getHeight()];

        for(int x = 0; x < canvas.getWidth(); x++) {
            for(int y = 0; y < canvas.getHeight(); y++) {
                pixels[x][y] = getRectangle(x,y).getPixel(x,y);
            }
        }

        return pixels;
    }
}
