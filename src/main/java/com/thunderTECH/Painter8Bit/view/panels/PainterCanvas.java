package com.thunderTECH.Painter8Bit.view.panels;


import com.thunderTECH.Painter8Bit.Painter;
import com.thunderTECH.Painter8Bit.control.CanvasMouseDragged;
import com.thunderTECH.Painter8Bit.control.CanvasMousePressed;
import com.thunderTECH.Painter8Bit.control.CanvasMouseScroll;
import com.thunderTECH.Painter8Bit.model.Pixel;
import com.thunderTECH.Painter8Bit.model.PixelGrid;
import com.thunderTECH.Painter8Bit.view.panels.instruments.ColorsPalette;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

public class PainterCanvas {
    private final Canvas canvas;
    private final GraphicsContext graphic;
    private PixelGrid pixelGrid;
    private Color currentPixelColor;

    public PainterCanvas(int width, int height) {
        canvas = new Canvas(width, height);
        graphic = canvas.getGraphicsContext2D();
        pixelGrid = createPixelGrid();
        currentPixelColor = Color.BLACK;

        canvas.setOnMousePressed(new CanvasMousePressed(this));
        canvas.setOnMouseDragged(new CanvasMouseDragged(this));
        canvas.setOnScroll(new CanvasMouseScroll(this));

        repaint();
    }

    public void paintPixel(Pixel pixel, Color color) {
        pixel.setColor(color);

        if(color.equals(Color.TRANSPARENT))
            clearPixel(pixel);
        else {
            graphic.setFill(pixel.getColor());
            graphic.fillRect(
                    pixel.getX() * pixel.getWidth(),
                    pixel.getY() * pixel.getHeight(),
                    pixel.getWidth(),
                    pixel.getHeight()
            );
        }

        if(pixelGrid.isGridLineVisible()) {
            graphic.setFill(pixelGrid.getGridLinesColor());
            graphic.strokeRect(
                    pixel.getX() * pixel.getWidth(),
                    pixel.getY() * pixel.getHeight(),
                    pixel.getWidth(),
                    pixel.getHeight()
            );
        }

    }

    public void repaint() {
        paintAllPixels();
        if(pixelGrid.isGridLineVisible()) {
            paintGrid();
        }

    }

    public void clear() {
        graphic.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCurrentPixelColor(Color currentPixelColor) {
        this.currentPixelColor = currentPixelColor;
        ColorsPalette.showCurrentColorOnPalette(currentPixelColor);
    }

    public Color getCurrentPixelColor() {
        return currentPixelColor;
    }

    public PixelGrid getPixelGrid() {
        return pixelGrid;
    }


    private PixelGrid createPixelGrid() {
        int gridWidth = Painter.GET_CANVAS_WIDTH() / Painter.GET_PIXEL_SIZE();
        int gridHeight = Painter.GET_CANVAS_HEIGHT() / Painter.GET_PIXEL_SIZE();
        int pixelSize = Painter.GET_PIXEL_SIZE();
        return pixelGrid = new PixelGrid(gridWidth, gridHeight, pixelSize);
    }

    private void paintAllPixels() {
        for(int x = 0; x < pixelGrid.getWidth(); x++) {
            for (int y = 0; y < pixelGrid.getHeight(); y++) {
                paintPixel(pixelGrid.getPixelsGrid()[x][y], pixelGrid.getPixelsGrid()[x][y].getColor());
            }
        }
    }

    private void paintGrid() {
        for(int x = 0; x < pixelGrid.getWidth(); x++) {
            for (int y = 0; y < pixelGrid.getHeight(); y++) {
                Pixel pixel = pixelGrid.getPixelsGrid()[x][y];
                graphic.setStroke(pixelGrid.getGridLinesColor());
                graphic.setLineWidth(0.5);
                graphic.strokeRect(
                        x * pixel.getWidth(), y * pixel.getHeight(),pixel.getWidth(),pixel.getHeight());
            }
        }
    }

    /** using if pixel color is transparent **/
    private void clearPixel(Pixel pixel) {
        graphic.setFill(pixel.getColor());
        graphic.clearRect(
                pixel.getX() * pixel.getWidth()+1,
                pixel.getY() * pixel.getHeight()+1,
                pixel.getWidth()-1,
                pixel.getHeight()-1
        );
    }

}
