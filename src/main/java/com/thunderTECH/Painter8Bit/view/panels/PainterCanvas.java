package com.thunderTECH.Painter8Bit.view.panels;

import com.thunderTECH.Painter8Bit.Painter;
import com.thunderTECH.Painter8Bit.control.CanvasMouseDragged;
import com.thunderTECH.Painter8Bit.control.CanvasMousePressed;
import com.thunderTECH.Painter8Bit.control.CanvasMouseScroll;
import com.thunderTECH.Painter8Bit.model.Pixel;
import com.thunderTECH.Painter8Bit.model.PixelGrid;
import com.thunderTECH.Painter8Bit.view.panels.instruments.ColorsPalette;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.image.RenderedImage;
import java.util.ArrayList;

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

        paint();
    }

    /** Paint single pixel in given color **/
    public void paint(Pixel pixel, Color color) {
        pixel.setColor(color);

        if(color.equals(Color.TRANSPARENT))
            clear(pixel);
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
    /** Paint or repaint given pixels **/
    public void paint(Pixel[] pixels) {
        for (Pixel pixel : pixels)
            paint(pixel, pixel.getColor());
    }
    /** Paint or repaint all canvas **/
    public void paint() {
        for(Pixel[] pixels : pixelGrid.getGrid()) {
            paint(pixels);
        }
        if(pixelGrid.isGridLineVisible())
            paintGridLines();
    }

    /** Clear all canvas **/
    public void clear() {
        for(Pixel[] pixels : pixelGrid.getGrid()) {
            for (Pixel pixel : pixels)
                clear(pixel);
        }
        paint();
    }
    /** Clear single pixel **/
    public void clear(Pixel pixel) {
        pixel.setColor(Color.TRANSPARENT);
        graphic.setFill(pixel.getColor());
        graphic.clearRect(
                pixel.getX() * pixel.getWidth(),
                pixel.getY() * pixel.getHeight(),
                pixel.getWidth(),
                pixel.getHeight()
        );
        // this code string fixing native java code problem with clearRect() which clears near colored pixels borders
        paint(getPixelNeighbours(pixel).toArray(Pixel[]::new));
    }


    public void paintGridLines() {
        pixelGrid.setGridLineVisible(true);
        for(int x = 0; x < pixelGrid.getWidth(); x++) {
            for (int y = 0; y < pixelGrid.getHeight(); y++) {
                Pixel pixel = pixelGrid.getGrid()[x][y];
                graphic.setStroke(pixelGrid.getGridLinesColor());
                graphic.setLineWidth(0.5);
                graphic.strokeRect(
                        x * pixel.getWidth(), y * pixel.getHeight(),pixel.getWidth(),pixel.getHeight());
            }
        }
    }

    public void clearGridLines() {
        pixelGrid.setGridLineVisible(false);
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
    /** remove blur effect from pixels borders **/
    private void removeAntialiasingFromSnapshot(WritableImage snapshot) {
        PixelWriter writer = snapshot.getPixelWriter();

        for(int x = 0; x < snapshot.getWidth(); x++) {
            for(int y = 0; y < snapshot.getHeight(); y++) {
                Color color;
                Pixel pixel = getPixelGrid().getPixel(x,y);

                if(pixel == null)
                    color = Color.TRANSPARENT;
                else
                    color = pixel.getColor();

                writer.setColor(x,y,color);
            }
        }
    }


    public void setCurrentPixelColor(Color currentPixelColor) {
        this.currentPixelColor = currentPixelColor;
        ColorsPalette.showCurrentColorOnPalette(currentPixelColor);
    }

    public Color getCurrentPixelColor() {
        return currentPixelColor;
    }

    public Canvas getCanvas() {
        return canvas;
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

    private ArrayList<Pixel> getPixelNeighbours(Pixel pixel) {
        ArrayList<Pixel> pixels = new ArrayList<>();
        int x = pixel.getX();
        int y = pixel.getY();

        // from TOP-LEFT pixel to BOTTOM-RIGHT pixel
        for (int i = x-1;i < x+2;i++) {
            for (int j = y-1;j < y+2;j++) {
                if (pixelGrid.isCorrectGridBorders(i,j)) {
                    if(!pixelGrid.getGrid()[i][j].getColor().equals(Color.TRANSPARENT)) // don't add Transparent pixel
                        pixels.add(pixelGrid.getGrid()[i][j]);
                }
            }
        }

        return pixels;
    }

}
